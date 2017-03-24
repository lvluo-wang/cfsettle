package com.upg.ucars.struts2;

import javax.servlet.ServletContext;

import org.apache.struts2.convention.ConventionUnknownHandler;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

public class UcarsConventionUnknownHandler extends ConventionUnknownHandler {

	@Inject
	public UcarsConventionUnknownHandler(Configuration configuration, ObjectFactory objectFactory,
			ServletContext servletContext, Container container,
			@Inject("struts.convention.default.parent.package") String defaultParentPackageName,
			@Inject("struts.convention.redirect.to.slash") String redirectToSlash,
			@Inject("struts.convention.action.name.separator") String nameSeparator) {
		super(configuration, objectFactory, servletContext, container, defaultParentPackageName, redirectToSlash,
				nameSeparator);
	}

	@Override
	protected String determinePath(ActionConfig actionConfig, String namespace) {
		String path = null;
		if (actionConfig != null) {
			String className = actionConfig.getClassName();
			String packageName = className.substring(0, className.lastIndexOf("."));
			path = UcarsPackageBasedActionConfigBuilder.getActionNamespace(packageName, false);
			if (!path.endsWith("/")) {
				path += "/";
	        }
		}
		return path;
	}

}
