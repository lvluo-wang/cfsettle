package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserConcemedException;

/**
 * 
 * ActionException
 *
 * @author shentuwy
 *
 */
public class ActionException extends UserConcemedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3224269515604353126L;
	
	public ActionException(){
		super();
	}
	
	public ActionException(String message){
		super(message);
	}
	
	public ActionException(Throwable t){
		super(t);
	}
	
	public ActionException(String message,Throwable t){
		super(message,t);
	}
	
}
