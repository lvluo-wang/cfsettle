package com.upg.ucars.struts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.PackageBasedActionConfigBuilder;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

public class UcarsPackageBasedActionConfigBuilder extends PackageBasedActionConfigBuilder {

	private static final String ACTION_SUFFIX = "Action";
	
	private Configuration	struts2Configuration;

	private static String[]		baseActionPackages;

	@Inject
	public UcarsPackageBasedActionConfigBuilder(Configuration configuration, Container container,
			ObjectFactory objectFactory, @Inject("struts.convention.redirect.to.slash") String redirectToSlash,
			@Inject("struts.convention.default.parent.package") String defaultParentPackage) {
		super(configuration, container, objectFactory, redirectToSlash, defaultParentPackage);
		struts2Configuration = configuration;
	}

	@Inject(value = "struts.convention.base.action.packages")
	public void setBaseActionPackages(String baseActionPackages) {
		this.baseActionPackages = baseActionPackages.split("\\s*[,]\\s*");
	}

	@Override
	protected Set<Class> findActions() {
		Set<Class> actionClasses = super.findActions();
		Set<Class> result = new HashSet<Class>();
		if (actionClasses != null && !actionClasses.isEmpty()) {
			List<String> actionClassName = new ArrayList<String>();
			for (PackageConfig pconfig : struts2Configuration.getPackageConfigs().values()) {
				for (ActionConfig aconfig : pconfig.getActionConfigs().values()) {
					String className = aconfig.getClassName();
					actionClassName.add(className);
				}
			}

			for (Class cls : actionClasses) {
				if (!actionClassName.contains(cls.getName())) {
					result.add(cls);
				}
			}

		}
		return result;
	}

	public static final String getActionNamespace(Class<?> actionClass,boolean stripModule){
		return getActionNamespace(actionClass.getPackage().getName(), stripModule);
	}
	
	public static final String getActionNamespace(String packageName,boolean stripModule){
		String result = null;
		if (baseActionPackages != null && baseActionPackages.length > 0) {
			String namespace = packageName;
			for (String bap : baseActionPackages) {
				if (packageName.contains(bap)) {
					namespace = packageName.substring(packageName.indexOf(bap) + bap.length() + 1);
					if (namespace.indexOf(".") > -1) {
						if(stripModule){
							namespace = namespace.substring(namespace.indexOf(".") + 1);
						}
						namespace = namespace.replaceAll("\\.actions\\.", "\\.").replaceAll("\\.actions", "");
						namespace = namespace.replaceAll("\\.action\\.", "\\.").replaceAll("\\.action", "");
						break;
					}
				}
			}
			if (namespace != null) {
				namespace = namespace.replaceAll("\\.", "/");
				if (namespace.charAt(0) != '/') {
					namespace = "/" + namespace;
				}
				if (namespace.charAt(namespace.length() - 1) == '/') {
					namespace = namespace.substring(0, namespace.length() - 1);
				}
				result = namespace;
			}
		}
		
		return result;
	}

	@Override
	protected void buildConfiguration(Set<Class> classes) {
//		super.buildConfiguration(classes);
		Map<String, PackageConfig.Builder> packageConfigs = new HashMap<String, PackageConfig.Builder>();
		for (Class<?> actionClass : classes) {
			String actionPackage = actionClass.getPackage().getName();
			String namespace = getActionNamespace(actionClass,true);
			String defaultActionName = getActionName(actionClass);
			PackageConfig.Builder defaultPackageConfig = getPackageConfig(packageConfigs, namespace, actionPackage,
					actionClass, null);
			createActionConfig(defaultPackageConfig, actionClass, defaultActionName, "{1}", null);

		}
		Set<String> packageNames = packageConfigs.keySet();
        for (String packageName : packageNames) {
        	struts2Configuration.addPackageConfig(packageName, packageConfigs.get(packageName).build());
        }
	}

	private String getActionName(Class<?> actionClass) {
		String actionName = actionClass.getSimpleName();
        
        if (actionName.equals(ACTION_SUFFIX))
            throw new IllegalStateException("The action name cannot be the same as the action suffix [" + ACTION_SUFFIX + "]");

        // Truncate Action suffix if found
        if (actionName.endsWith(ACTION_SUFFIX)) {
            actionName = actionName.substring(0,1).toLowerCase() + actionName.substring(1, actionName.length() - ACTION_SUFFIX.length());
        }

       

        return actionName + "_*";
	}
}
