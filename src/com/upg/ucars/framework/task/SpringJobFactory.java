package com.upg.ucars.framework.task;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * @author shentuwy
 * 
 */
public class SpringJobFactory extends SimpleJobFactory {
	
	private static Logger log = Logger.getLogger(SpringJobFactory.class);

	@Override
	public Job newJob(TriggerFiredBundle bundle) throws SchedulerException {
		Job result = super.newJob(bundle);
		try {
			Class<?> clazz = bundle.getJobDetail().getJobClass();
			BeanInfo bi = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propDescs = bi.getPropertyDescriptors();
			StringBuilder sb = new StringBuilder();
			for (PropertyDescriptor pd : propDescs) {
				Method method = pd.getWriteMethod();
				if (method != null) {
					String propertyName = pd.getName();
					sb.append(",").append(propertyName);
					Class<?> parameterType = pd.getPropertyType();
					method.invoke(result, SourceTemplate.getBean(parameterType, propertyName));
				}
			}
			log.info(clazz.getName() + ",setProperty:[" + sb.toString() + "]");
		} catch (Exception ex) {
			throw new SchedulerException(ex);
		}

		return result;
	}

}
