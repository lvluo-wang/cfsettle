package com.upg.ucars.basesystem.autotask;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.quartz.ee.servlet.QuartzInitializerServlet;

import com.upg.ucars.basesystem.autotask.core.AutoTaskServiceFactory;
import com.upg.ucars.basesystem.autotask.core.ScheduleHelper;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;

/**
 * 初始化自动化任务
 * 
 * @author shentuwy
 */
public class AutoTaskInitializerServlet extends QuartzInitializerServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5025297251088722214L;
	/** 是否可以跑定时任务 */
	private static boolean		isRunTask			= false;

	@Override
	public void init(ServletConfig config) throws ServletException {
		Logger log = Logger.getLogger(AutoTaskInitializerServlet.class);
		try {

			List<String> localAddress = getAllLocalAddress();

			String configIP = config.getInitParameter("autoTaskIp");// 用于集群环境下指定IP启动

			if (log.isInfoEnabled()) {
				log.info("config autotask server:" + configIP + ",current server:" + localAddress);
			}

			if (configIP == null || "".equals(configIP) || localAddress.contains(configIP)) {
				super.init(config);

				isRunTask = true;

				log.info("/***loading AutoTasks.************/");

				Iterator<AutoTask> it = AutoTaskServiceFactory.getAutoTaskService().getAutoTasks().iterator();
				while (it.hasNext()) {
					AutoTask task = it.next();
					if (AutoTask.STATUS_OPEN.equals(task.getStatus())) {
						ScheduleHelper.addJob(task);
						log.info(task.getName() + " is opened.");
					} else {
						log.warn(task.getName() + " is closing.");
					}

				}
				log.info("/***AutoTasks have loaded.********/");
			}

		} catch (Exception e) {
			log.error(" init autotask module, don't ip exception.", e);
			throw new ServletException(e);
		}

	}

	private static final List<String> getAllLocalAddress() {
		List<String> ret = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			if (n != null) {
				while (n.hasMoreElements()) {
					NetworkInterface e = n.nextElement();
					Enumeration<InetAddress> a = e.getInetAddresses();
					if (a != null) {
						while (a.hasMoreElements()) {
							InetAddress address = a.nextElement();
							if (!address.isLoopbackAddress() && !address.getHostAddress().contains(":")) {
								ret.add(address.getHostAddress());
							}
						}
					}
				}
			}
		} catch (Exception ex) {

		}
		return ret;
	}

	public static final boolean isRunTask() {
		return isRunTask;
	}

}
