package com.upg.ucars.basesystem.webUtil.tag;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.upg.ucars.framework.security.SecurityFactory;

public class Acl extends Component {
	private String uri;


	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		if(StringUtils.isBlank(uri)||SecurityFactory.getSecurityManager().isAuthorized(uri)){
			result=true;
		}else{
			result=false;
		}
		return result;
	}
	
	public Acl(ValueStack stack) {
		super(stack);
	}

}
