package com.upg.finance.icbc.interceptor;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ucars.tsc.TransportInvocation;
import com.ucars.tsc.interceptor.AbstractInterceptor;
import com.ucars.tsc.transportor.HttpTransportor;

/**
 * 增加url参数
 * 
 */
@Component
public class AppendUrlInterceptor extends AbstractInterceptor {

	@Resource
	private Map<String, String>	icbcConfig;

	/**
	 * 
	 * 
	 */
	private String				url;

	@Override
	protected boolean doIntercept(TransportInvocation invocation) {
		if (icbcConfig != null && url == null) {
			url = icbcConfig.get("url");
		}
		Map<String, Object> map = invocation.getMap();
		map.put(HttpTransportor.PARAMETER_URL, url);
		invocation.invoke();
		return true;
	}

}
