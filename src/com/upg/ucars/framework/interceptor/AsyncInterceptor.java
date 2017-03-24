package com.upg.ucars.framework.interceptor;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.SessionTool;
/**
 * 为先进来的action在session端加上锁，先进来的action执行完毕后，释放锁，
 * 后进来的action在锁未释放前没有执行权限。
 * @author cuckoo
 *
 */
public class AsyncInterceptor extends AbstractInterceptor {
	
	private static final Logger LOG = Logger.getLogger(AsyncInterceptor.class);
	
	private static final long TIME_OUT = 4 * 60 * 1000;
	
	private String uncontrolledActionNames;
	
	public String getUncontrolledActionNames() {
		return uncontrolledActionNames;
	}
	public void setUncontrolledActionNames(String uncontrolledActionNames) {
		this.uncontrolledActionNames = uncontrolledActionNames;
	}
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		String actionName = null;
		long startTime = System.currentTimeMillis();
		try{
			if(SessionTool.getLocale()==null){
				SessionTool.setDefaultLocale();
			}
			actionName=arg0.getProxy().getActionName().toUpperCase();
			if("LOGIN".equals(actionName)){
				unlockAction();
			}
			boolean lock=true;
			if(isUncontrolled(arg0))
				return arg0.invoke();
			else{
				while(lock){
					lock=actionIsLocked();
					if(!lock){
						lockAction();
						String target=arg0.invoke();
						unlockAction();
						return target;
					}
					if ((System.currentTimeMillis() - startTime) > TIME_OUT) {
						throw new RuntimeException(String.valueOf(actionName) + " is timeout.");
					}
				}
			}
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		} finally {
			LOG.info(String.valueOf(actionName) + ":speed time[" + (System.currentTimeMillis()-startTime) + "ms]");
			unlockAction();
		}
		return "";
	}
	/**
	 * 比对配置，当前请求的action是否在不受控制的范围内
	 * @param arg0
	 * @return
	 */
	private boolean isUncontrolled(ActionInvocation arg0){
		boolean flag=false;
		String currentActionName = arg0.getProxy().getActionName();
		if(uncontrolledActionNames.contains(","+currentActionName+","))
			flag=true;

		return flag;
	}
	/**
	 * 判断action是否已加锁
	 * @return
	 */
	private boolean actionIsLocked(){
		String flag=(String)SessionTool.getAttribute(SessionKeyConst.ASYNC_ACTION_LOCK);
		if(flag==null)
			return false;
		return flag.equals("true");
	}

	/**
	 * 为action加锁
	 */
	private void lockAction(){
		SessionTool.setAttribute(SessionKeyConst.ASYNC_ACTION_LOCK, "true");
	}
	/**
	 * 为action解锁
	 */
	private void unlockAction(){
		SessionTool.setAttribute(SessionKeyConst.ASYNC_ACTION_LOCK, "false");
	}
}
