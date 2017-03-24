package com.upg.ucars.framework.annotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

public class XAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {

	private List<String>	exclude;

	public List<String> getExclude() {
		return exclude;
	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

	public void setMappingLocations(Resource[] mappingLocations) {
		List<Resource> locations = null;
		if (mappingLocations != null && mappingLocations.length > 0) {
			locations = new ArrayList<Resource>();
			for (int i = 0; i < mappingLocations.length; i++) {
				Resource resource = mappingLocations[i];
				try {
					boolean isInclude = true;
					if (exclude != null && exclude.size() > 0) {
						String resourceString = resource.getURL().toString();
						for (String ex : exclude) {
							if (resourceString.contains(ex)) {
								isInclude = false;
								break;
							}
						}
					}
					if (isInclude) {
						locations.add(resource);
					}
				} catch (IOException ex) {
					logger.error(ex);
				}
			}
		}
		if (locations != null) {
			super.setMappingLocations(locations.toArray(new Resource[locations.size()]));
		}
	}

}
