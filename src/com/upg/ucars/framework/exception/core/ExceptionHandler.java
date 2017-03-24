package com.upg.ucars.framework.exception.core;


import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.util.LogUtil;
/**
 * 异常处理器，记录异常日志、获取异常信息、格式化异常信息
 * 
 * @date 2010-12-17 下午12:01:37
 *
 */
public class ExceptionHandler {

	/**
	 * 记录异常日志
	 * @param cause 异常源
	 */
	public static void logException(Throwable cause){
		if(cause instanceof BaseAppRuntimeException){
			BaseAppRuntimeException ex=(BaseAppRuntimeException)cause;
			String errorCode=ex.getErrCode();
			EcError error=ErrorContextLoader.getError(errorCode);
			String ifLogStr=error.getLog();
			String errType=error.getType();

			if("true".equals(ifLogStr)){
				log(errType,ex);
				if(cause.getCause()!=null){
					logException(cause.getCause());
				}
			}
		}else{
			log(ErrorType.ERROR,cause);
		}
		LogUtil.getUserLog().error(cause.getMessage());
	}
	/**
	 * 根据异常定义级别，记录日志
	 * @param errType 异常级别
	 * @param cause  异常源
	 */
	private static void log(String errType,Throwable cause){
		if(cause==null)
			return;
		String errorClass=cause.getClass().getName();
		String errorMessage=cause.getMessage();
		if(errorMessage!=null){
			errorMessage=cause.getMessage().replace("<br>", ",");
		}else{
			errorMessage="";
		}
		if(ErrorType.ERROR.equals(errType))
		{
			LogUtil.getExceptionLog().error("[Exception]:"+errorClass+"-["+errorMessage+"]");
		}
		if(ErrorType.WARNING.equals(errType)){
			LogUtil.getExceptionLog().info("[Exception]:"+errorClass+"-["+errorMessage+"]");
		}
		for(int i=0;i<cause.getStackTrace().length;i++){
			String classname=cause.getStackTrace()[i].getClassName();
			if(regxClass(classname)){
				if(ErrorType.ERROR.equals(errType))
				{
					LogUtil.getExceptionLog().error("\t"+cause.getStackTrace()[i].getClassName()+"("+cause.getStackTrace()[i].getMethodName()+")["+cause.getStackTrace()[i].getLineNumber()+"]");
				}
				if(ErrorType.WARNING.equals(errType)){
					LogUtil.getExceptionLog().info("\t"+cause.getStackTrace()[i].getClassName()+"("+cause.getStackTrace()[i].getMethodName()+")["+cause.getStackTrace()[i].getLineNumber()+"]");
				}
			}
		}
		
	}
	private static Locale getLocale(){
		try{
			HttpSession session=ServletActionContext.getRequest().getSession();
			Locale lo=(Locale)session.getAttribute(SessionKeyConst.SESSION_LOCAL);
			if(lo==null){
				lo=ServletActionContext.getRequest().getLocale();
			}
			return lo;
		}catch(Throwable e){
			return Locale.CHINA;
		}
		
	}
	/**
	 * 根据错误码返回异常定义文件中的错误信息
	 * @param errCode 异常错误码
	 * @return 异常信息
	 */
	public static String getMessageByCode(String errCode){
		EcError error=ErrorContextLoader.getError(errCode,getLocale());
		String errMsg=error.getMessage();
		String errDetail=error.getDetail();
		String errHelp=error.getHelp();
		StringBuffer sb=new StringBuffer("");
		sb.append("<h3 class='err_title'>"+errMsg+"</h3>");
		sb.append("<h4 class='err_detail'>"+errDetail+"</h4>");
		sb.append("<h4 class='err_help'>"+errHelp+"</h4>");
		return sb.toString();
	}
	/**
	 * 根据错误码和注入的参数返回异常定义文件中的错误信息
	 * @param errCode 异常错误码
	 * @param fixMsgs 注入的参数，具体参考异常定义文件
	 * @return 异常信息
	 */
	public static String getMessageByCode(String errCode,String[] fixMsgs){
		EcError error=ErrorContextLoader.getError(errCode, getLocale());
		String errMsg=error.getMessage();
		String errDetail=error.getDetail();
		String errHelp=error.getHelp();
		StringBuffer sb=new StringBuffer("");
		sb.append("<h3 class='err_title'>"+errMsg+"</h3>");
		sb.append("<h4 class='err_detail'>"+getDetail(errDetail,fixMsgs)+"</h4>");
		sb.append("<h4 class='err_help'>"+errHelp+"</h4>");
		return sb.toString();
	}
	/**
	 * 完成异常原信息和注入参数的绑定
	 * @param src
	 * @param objects
	 * @return  String
	 */
	private static String getDetail(String src,Object[] objects){
		String ret=src;
		if(src!=null && src.length() > 0){
			if(objects==null)
				return src;
			for(int i=0;i<objects.length;i++){
				String flag="{"+i+"}";
				int pos=ret.indexOf(flag);
				String str1=ret.substring(0,pos);
				String str2=ret.substring(pos+flag.length());
				ret= str1+objects[i]+str2;
			}
		}
		return ret;
	}
	/**
	 * 根据异常配置中定义命名列表，
	 * 在记录异常轨迹的支持列表和不支持列表，只记录支持列表中包含的，但不记录不支持列表
	 * @param className 类名 例如：java.lang.String
	 * @return  boolean
	 */
	private static boolean regxClass(String className){
		boolean one=false;
		for(int i=0;i<ErrorContextLoader.getSupportList().size();i++){
			String value=ErrorContextLoader.getSupportList().get(i);
			if(className.contains(value)){
				one=true;
				break;
			}
		}
		for(int i=0;i<ErrorContextLoader.getUnsupportList().size();i++){
			String value=ErrorContextLoader.getUnsupportList().get(i);
			if(className.contains(value)){
				one=false;
				break;
			}
		}
		return one;
	}
	/**
	 * 异常对象转换为html的字符串，放入StringBuffer对象中，记录比较详细，开发模式下使用，用于前台显示
	 * @param sb 需要返回的StringBuffer对象
	 * @param cause 异常源
	 * @param time 时间
	 */
	public static void errorToHtmlInDebugMode(StringBuffer sb,Throwable cause,String time){
		String errorMsg=cause.getMessage();
		String errorClass=cause.getClass().getName();
		StringBuffer lineBuffer=new StringBuffer("<p>");
		lineBuffer.append(time);
		lineBuffer.append("<h4 style='color:red;'>").append(errorClass).append("-[").append(errorMsg).append("]</h4>");
		lineBuffer.append("<ul>");
		for(int j=0;j<cause.getStackTrace().length;j++){
			StackTraceElement ste=cause.getStackTrace()[j];
			String classname=ste.getClassName();
			if(regxClass(classname)){
				lineBuffer.append("<li>").append(ste.getClassName()).append("(").append(ste.getMethodName()).append(")[").append(ste.getLineNumber()).append("]").append("</li>");
			}
		}
		lineBuffer.append("</ul></p>");
		sb.append(lineBuffer);
		if(cause instanceof BaseAppRuntimeException){
			if(cause.getCause()!=null){
				errorToHtmlInDebugMode(sb,cause.getCause(),time);
			}
		}
	}
	/**
	 * 异常对象转换为html的字符串，放入StringBuffer对象中,只记录简单的信息，生产模式下使用,用于前台显示
	 * @param sb 需要返回的StringBuffer对象
	 * @param cause 异常源
	 * @param time 时间
	 */
	public static void errorToHtmlInNomalMode(StringBuffer sb,Throwable cause,String time){
		String errorMsg=cause.getMessage();
		String errorClass=cause.getClass().getName();
		if(cause instanceof BaseAppRuntimeException){
			if(cause instanceof UserNotConcemedException){
				int t=errorMsg.indexOf("<br>");
				errorMsg=cause.getMessage().substring(0, t);
			}
		}
		String line="<p>"+time;
		line+="<h4 style='color:red;'>"+errorClass+"-["+errorMsg+"]</h4>";
		line+="</p>";
		sb.append(line);
	}
}
