package com.upg.ucars.framework.esb.transformer;

import java.lang.reflect.Method;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.tools.msg.AbsTransformer;

/**
 * 报文转换器
 * @author shentuwy
 * @date 2011-10-14
 **/
public class MsgTransformer extends AbstractTransformer {
	
	private String configKey;
	
	private String transformrClass;
	
	@Override
	protected Object doTransform(Object src, String enc)throws TransformerException {
		try {
			if(src == null) return null;
			if(src instanceof String){
				if("".equals(src)) return null;
			}
			Class cls = Class.forName(transformrClass);
			Class[] argclass = new Class[0];
			Object[] args = new Object[0];
			Method method = cls.getMethod("getInstance",argclass);
			AbsTransformer tfr = (AbsTransformer)method.invoke(null, args);
			return tfr.doTransform((String)src,configKey);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionManager.throwException(ServiceException.class,ErrorCodeConst.ESB_MSG_TF_ERROR,e.getMessage());
		}
		return null;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getTransformrClass() {
		return transformrClass;
	}

	public void setTransformrClass(String transformrClass) {
		this.transformrClass = transformrClass;
	}
}
