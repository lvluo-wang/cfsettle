package com.upg.ucars.framework.base;

import org.apache.commons.dbcp.BasicDataSource;

import com.upg.ucars.util.DESKeyUtil;
/**
 * 数据源配置加密
 * <br>
 * 扩展父类setPassword方法
 *
 * @author shentuwy
 */
public class ExBasicDataSource extends BasicDataSource {

	@Override
	public synchronized void setPassword(String password) {
		//将密文进行解密
		String realPassword = DESKeyUtil.dec("base-config/keydb.dat", password);
		
		super.setPassword(realPassword);
		
	}

}
