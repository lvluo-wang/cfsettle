package com.upg.ucars.util;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * Spring实例管理类
 * 
 * @author shentuwy
 * @date 2012-7-30
 * 
 */
public class SourceTemplate {

	private static ApplicationContext	ac	= null;

	/**
	 * 初始化配置
	 */
	private synchronized static void init() {
		if (ac == null) {
			ac = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		}
	}

	/**
	 * 获取Spring配置的bean实例
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		// 默认使用web.xml配置加载
		if (SpringContextManager.isInitialized()) {
			return SpringContextManager.getBean(beanName);
		} else {// 测试环境下才可能进和此处。 如果web.xml未加载，则自行加载
			if (ac == null) {
				init();
			}
			return ac.getBean(beanName);
		}

	}

	/**
	 * 根据类型获取Bean
	 * 
	 * @param cls
	 * @return
	 */
	public static final <T> T getBean(Class<T> cls) {
		T result = null;
		if (SpringContextManager.isInitialized()) {
			result = SpringContextManager.getBean(cls);
		} else {// 测试环境下才可能进和此处。 如果web.xml未加载，则自行加载
			if (ac == null) {
				init();
			}
			result = ac.getBean(cls);
		}
		return result;
	}

	/**
	 * 
	 * 获取spring配置的bean,且转化成指定的类型
	 * 
	 * @param <T>
	 * @param clazz
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz, String beanName) {
		return (T) getBean(beanName);
	}

	public static HibernateTemplate getHibernateTemplate() {
		return (HibernateTemplate) getBean("hibernateTemplate");
	}

	/**
	 * 获得数据连接
	 * 
	 * "该方式通过硬编码直接从容器中获取dataSource的方式,不推荐使用"
	 */
	@Deprecated
	public static Connection getConn() throws Exception {
		Connection conn = DataSourceUtils.getConnection((DataSource) getBean("localDataSource"));
		return conn;
	}

	private static String		DB_TYPE				= null; // 数据库类型
	public final static String	DB_TYPE_ORACLE		= "1";
	public final static String	DB_TYPE_DB2			= "2";
	public final static String	DB_TYPE_INFORMIX	= "3";
	public final static String	DB_TYPE_SYBASE		= "4";
	public final static String	DB_TYPE_SQLSERVER	= "5";
	public final static String	DB_TYPE_MYSQL		= "6";

	/**
	 * 获取数据库类型 <br>
	 * SourceTemplate.DB_TYPE_*
	 * 
	 * @return
	 */
	public static String getDBType() {
		if (DB_TYPE == null) {
			Object sessionFactory = SourceTemplate.getBean("sessionFactory");
			try {
				Object settings = MethodUtils.invokeMethod(sessionFactory, "getSettings", null);
				Object dialect = MethodUtils.invokeMethod(settings, "getDialect", null);
				String dialectStr = dialect.toString().toUpperCase();
				if (dialectStr.indexOf("oracle") > 0) {
					DB_TYPE = DB_TYPE_ORACLE;
				} else if (dialectStr.indexOf("db2") > 0) {
					DB_TYPE = DB_TYPE_DB2;
				} else if (dialectStr.indexOf("INFORMIX") > 0) {
					DB_TYPE = DB_TYPE_INFORMIX;
				} else if (dialectStr.indexOf("SYBASE") > 0) {
					DB_TYPE = DB_TYPE_SYBASE;
				} else if (dialectStr.indexOf("SQLSERVER") > 0) {
					DB_TYPE = DB_TYPE_SQLSERVER;
				} else if (dialectStr.indexOf("MYSQL") > 0) {
					DB_TYPE = DB_TYPE_MYSQL;
				}

			} catch (Exception e) {
				throw new RuntimeException("Don't get the DB Type.");
			}
		}

		return DB_TYPE;
	}

}
