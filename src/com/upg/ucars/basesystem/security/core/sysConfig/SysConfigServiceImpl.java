package com.upg.ucars.basesystem.security.core.sysConfig;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.util.DESKeyUtil;


/**
 * 
 * SysConfigServiceImpl
 *
 * @author shentuwy
 *
 */
public class SysConfigServiceImpl extends BaseService implements ISysConfigService {
	/** 系统配置DAO */
	private ISysConfigDAO sysConfigDao;
	
	public Long addSysConfig(SysConfig sc) {
		if( sc != null && StringUtils.isNotBlank(sc.getPwdInit()) ){
			sc.setPwdInit(DESKeyUtil.EncryptAES(sc.getPwdInit(), null));
		}
		return sysConfigDao.save(sc);
	}
	
	public void editSysConfig(SysConfig sc){
		if( sc != null ){
			if( StringUtils.isNotBlank(sc.getPwdInit()) ){
				sc.setPwdInit(DESKeyUtil.EncryptAES(sc.getPwdInit(), null));
			}
			sysConfigDao.update(sc);
		}
	}
	
	
	/**
	 * 
	 * 默认的系统配置
	 *
	 * @return
	 */
	private SysConfig getDefaultSysConfig(){
		SysConfig config=new SysConfig();
		config.setErrAllowNum(SysConfig.DEFAULT_PWD_ALLOW_ERR_NUM);
		config.setIsMultiIp(SysConfig.DEFAULT_IS_MULTIIP_LOGON);
		config.setIsOnlineLogon(SysConfig.DEFAULT_IS_ONLINE_LOGON);
		config.setPwdEffectDays(SysConfig.DEFAULT_PWD_EFFECT_DAYS);
		config.setPwdInit(SysConfig.DEFAULT_INIT_PWD);
		config.setSysParamCheck(SysConfig.DEFAULT_SYS_PARAM_CHECK);
		return config;
	}
	
	public SysConfig getConfigByMiNo(String miNo,boolean isDefaultSc){
		SysConfig ret = null;
		if( StringUtils.isNotBlank(miNo) ){
			ret = sysConfigDao.getConfigByMiNo(miNo);
		}
		if( ret == null && isDefaultSc ){
			ret = getDefaultSysConfig();
			if(ret != null && ret.getPwdInit() != null){
				ret.setPwdInit(DESKeyUtil.EncryptAES(ret.getPwdInit(), null));
			}
		}
		return ret;
	}
	
	public boolean isAllowMultiIpLogon(String miNo) {
		boolean ret = false;
		if( miNo != null ){
			SysConfig sc = getConfigByMiNo(miNo,true);
			if( sc != null ){
				ret = SysConfig.DEFAULT_IS_MULTIIP_LOGON.equals(sc.getIsMultiIp());
			}
		}
		return ret;
	}
	
	public boolean isAllowOnlineLogon(String miNo) {
		boolean ret = false;
		if( miNo != null ){
			SysConfig sc = getConfigByMiNo(miNo,true);
			if( sc != null ){
				ret = SysConfig.DEFAULT_IS_ONLINE_LOGON.equals(sc.getIsOnlineLogon());
			}
		}
		return ret;
	}
	
	public boolean isSysparamCheck(String miNo) {
		boolean ret = false;
		if( miNo != null ){
			SysConfig sc = getConfigByMiNo(miNo,true);
			if( sc != null ){
				ret = SysConfig.DEFAULT_SYS_PARAM_CHECK.equals(sc.getSysParamCheck());
			}
		}
		return ret;
	}

	public void saveDefaultSysConfig(String miNo) {
		SysConfig config = getDefaultSysConfig();
		config.setMiNo(miNo);
		addSysConfig(config);
	}
	
	public void setSysConfigDao(ISysConfigDAO sysConfigDao) {
		this.sysConfigDao = sysConfigDao;
	}
}
