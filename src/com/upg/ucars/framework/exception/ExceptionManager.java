package com.upg.ucars.framework.exception;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.exception.core.BaseAppRuntimeException;
import com.upg.ucars.framework.exception.core.ErrorContextLoader;
import com.upg.ucars.framework.exception.core.ExceptionHandler;
import com.upg.ucars.util.DateTimeUtil;


/**
 * 异常管理器，异常处理的统一入口
 * 收集异常、记录异常日志、异常封装抛出、异常封装获取、通用异常
 * 
 * 
 * @author shentuwy
 * @date 2012-7-23
 *
 */
public class ExceptionManager {
	/**
	 * 异常收集的集合，容量与异常配置一致
	 */
	private List<Throwable > errorList=new ArrayList<Throwable >();
	/**
	 * 异常发生的时间集合
	 */
	private List<String> timeList=new ArrayList<String>();
	
	public static ExceptionManager getSessionInstance(HttpServletRequest request){
		if(request==null){
			return null;
		}
		ExceptionManager manager=(ExceptionManager)request.getSession().getAttribute(SessionKeyConst.EXCEPTION_MANAGER);
		if(manager==null){
			manager=new ExceptionManager();
			request.getSession().setAttribute(SessionKeyConst.EXCEPTION_MANAGER, manager);
		}
		return manager;
	}

	/**
	 * 异常的收集，用于前台拦截器中使用
	 * @param t 异常源
	 */
	public synchronized void collectException(Throwable t ){
		
		if(errorList.size()>ErrorContextLoader.getErrorCache()){
			errorList.remove(0);
			timeList.remove(0);
		}
		errorList.add(t);
		timeList.add(getNowDateTime());
	}
	/**
	 * 清空收集的异常
	 */
	public synchronized void removeAll(){
		errorList.clear();
		timeList.clear();
	}
	/**
	 * 获取系统当前时间
	 * @return  String 时间：yyyy mm dd hhmmss
	 */
	private static String getNowDateTime(){		
		return DateTimeUtil.get_YYYY_MM_DD_Date(DateTimeUtil.getNowDateTime())+" "+DateTimeUtil.get_HHmmss_Time();
	}
	/**
	 * 获取html格式的错误信息（开发模式下包含异常发生轨迹和详细信息，非开发模式下只有异常基本信息）
	 * @return 异常信息（html）
	 */
	public String getHtmlErrors(){
		StringBuffer sb=new StringBuffer();
		int size=errorList.size();
		for(int i=0;i<size;i++){
			int v=size-i-1;
			Throwable cause=errorList.get(v);
			String time=timeList.get(v);
			if(ErrorContextLoader.DEVELOPMENTMODEL){
				ExceptionHandler.errorToHtmlInDebugMode(sb,cause,time);
			}else{
				ExceptionHandler.errorToHtmlInNomalMode(sb,cause,time);
			}
			
		}
		return sb.toString();
	}

	/**
	 * 记录异常日志
	 * @param t
	 */
	public static void logException(Throwable t){
		ExceptionHandler.logException(t);
	};
	/**
	 * 抛出指定的异常，异常信息根据错误码获取
	 * @param clazz 指定的异常类别
	 * @param errCode  错误码
	 */
	public static void throwException(Class clazz,String errCode){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setErrCode(errCode);
			cause.setErrCode(errCode);
			cause.setMessage(message);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	
	/**
	 * 抛出指定的异常
	 * @param clazz 指定的异常类别
	 * @param message 已经国际化的异常信息
	 */
	public static void throwException(String message,Class excepClazz){
		BaseAppRuntimeException cause=null;
		try {
			Class c=Class.forName(excepClazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setMessage(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	
	/**
	 * 抛出指定的异常，异常信息根据错误码和想要注入的信息参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsgs  注入的信息参数数组，与异常定义文件一致
	 */
	public static void throwException(Class clazz,String errCode,String[] fixMsgs){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,fixMsgs);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	/**
	 * 抛出指定的异常，异常信息根据错误码和想要注入的信息参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsg  注入的信息参数，与异常定义文件一致
	 */
	public static void throwException(Class clazz,String errCode,String fixMsg){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,new String[]{fixMsg});
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	/**
	 * 抛出指定异常，并记录异常的源头，异常信息根据错误码获取
	 * @param clazz 异常类别
	 * @param errCode 错误码
	 * @param t  异常发生源头
	 */
	public static void throwException(Class clazz,String errCode,Throwable t){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class,java.lang.Throwable.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message,t});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	/**
	 * 抛出指定的异常，并记录异常发生的源头，错误信息根据错误码和想要注入的信息参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsgs 注入的信息参数数组
	 * @param t   异常源头
	 */
	public static void throwException(Class clazz,String errCode,String[] fixMsgs,Throwable t){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,fixMsgs);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class,java.lang.Throwable.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message,t});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	/**
	 * 抛出指定的异常，并记录异常发生的源头，错误信息根据错误码和想要注入的信息参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsg 注入的信息参数
	 * @param t   异常源头
	 */
	public static void throwException(Class clazz,String errCode,String fixMsg,Throwable t){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,new String[]{fixMsg});
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class,java.lang.Throwable.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message,t});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		} 
		throw cause;
	}
	/**
	 * 返回指定类别异常，异常信息根据错误码获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @return  RuntimeException 返回异常
	 */
	public static RuntimeException getException(Class clazz,String errCode){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setErrCode(errCode);
			cause.setErrCode(errCode);
			cause.setMessage(message);
			
		} catch (Exception e) {
			return new RuntimeException(e);
		} 
		return cause;
	}
	/**
	 * 返回指定类别异常，异常信息根据错误码和注入参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsgs 注入参数数组，与异常定义文件配置一致
	 * @return  RuntimeException 返回异常
	 */
	public static RuntimeException getException(Class clazz,String errCode,String[] fixMsgs){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,fixMsgs);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			return new RuntimeException(e);
		} 
		return cause;
	}
	/**
	 * 返回指定类别异常，异常信息根据错误码和注入参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsg 注入参数，与异常定义文件配置一致
	 * @return  RuntimeException 返回异常
	 */
	public static RuntimeException getException(Class clazz,String errCode,String fixMsg){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,new String[]{fixMsg});
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			return new RuntimeException(e);
		} 
		return cause;
	}
	/**
	 * 返回指定类别异常，并记录异常源头，异常信息根据错误码获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param t 异常源头
	 * @return  RuntimeException
	 */
	public static RuntimeException getException(Class clazz,String errCode,Throwable t){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class,java.lang.Throwable.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message,t});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			return new RuntimeException(e);
		} 
		return cause;
	}
	/**
	 * 返回指定类别异常，并记录异常发生源头，异常信息根据错误码和注入参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsgs 注入参数数组
	 * @param t 异常发生源头
	 * @return  RuntimeException
	 */
	public static RuntimeException getException(Class clazz,String errCode,String[] fixMsgs,Throwable t){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,fixMsgs);
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class,java.lang.Throwable.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message,t});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			return new RuntimeException(e);
		} 
		return cause;
	}
	/**
	 * 返回指定类别异常，并记录异常发生源头，异常信息根据错误码和注入参数获取
	 * @param clazz 指定异常类别
	 * @param errCode 错误码
	 * @param fixMsgs 注入参数
	 * @param t 异常发生源头
	 * @return  RuntimeException
	 */
	public static RuntimeException getException(Class clazz,String errCode,String fixMsg,Throwable t){
		BaseAppRuntimeException cause=null;
		try {
			String message=ExceptionHandler.getMessageByCode(errCode,new String[]{fixMsg});
			Class c=Class.forName(clazz.getName());
			Constructor cons=c.getDeclaredConstructor(new Class[]{java.lang.String.class,java.lang.Throwable.class});
			cons.setAccessible(true);
			cause=(BaseAppRuntimeException)cons.newInstance(new Object[]{message,t});
			cause.setErrCode(errCode);
			cause.setMessage(message);
		}  catch (Exception e) {
			return new RuntimeException(e);
		} 
		return cause;
	}

	/**
	 * 根据错误码返回异常信息
	 * @param errorCode 错误码
	 * @return  String 异常信息
	 */
	public static String getErrorMessageByErrorCode(String errorCode){
		return ExceptionHandler.getMessageByCode(errorCode);
	}
	/**
	 * 根据错误码返回异常信息
	 * @param errorCode 错误码
	 * @param fixMsgs 注入的参数数组
	 * @return  String 异常信息
	 */
	public static String getErrorMessageByErrorCode(String errorCode,String[] fixMsgs){
		return ExceptionHandler.getMessageByCode(errorCode,fixMsgs);
	}
	/**
	 * 根据错误码返回异常信息
	 * @param errorCode 错误码
	 * @param fixMsg 注入的参数
	 * @return  String 异常信息
	 */
	public static String getErrorMessageByErrorCode(String errorCode,String fixMsg){
		return ExceptionHandler.getMessageByCode(errorCode,new String[]{fixMsg});
	}
	/**
	 * 获取通用的异常信息，用于非自定义的异常和用户不关心的异常信息显示
	 * @return  异常信息
	 */
	public static String getCommonExceptionMessage(){
		return ExceptionHandler.getMessageByCode(ErrorCodeConst.GLOBAL_ERROR);
	}
	


	
}
