package com.upg.ucars.framework.name;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 组件名称
 * 
 * @author shentuwy
 * @date 2012-6-29
 * 
 */
public class ComponentNameGenerator extends AnnotationBeanNameGenerator {

	private static final String	SUFFIX_CLASS_NAME	= "Impl";

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String beanName = super.buildDefaultBeanName(definition);
		if (beanName.endsWith(SUFFIX_CLASS_NAME)) {
			beanName = beanName.substring(0,
					beanName.lastIndexOf(SUFFIX_CLASS_NAME));
		}
		return beanName;
	}

}
