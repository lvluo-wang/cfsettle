package com.upg.ucars.basesystem.autocode.core.codeconfig;

import java.util.List;

import com.upg.ucars.basesystem.autocode.util.AutoGenCodeUtil;
import com.upg.ucars.basesystem.busidate.util.BusiDateUtil;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.AutoCodeConfig;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

public class AutoCodeConfigServiceImp implements IAutoCodeConfigService {

	private IAutoCodeConfigDAO autoCodeConfigDAO=null;
	
	

	public IAutoCodeConfigDAO getAutoCodeConfigDAO() {
		return autoCodeConfigDAO;
	}



	public void setAutoCodeConfigDAO(IAutoCodeConfigDAO autoCodeConfigDAO) {
		this.autoCodeConfigDAO = autoCodeConfigDAO;
	}



	public Long createAutoGenCode(AutoCodeConfig codeConfig) throws ServiceException,
			DAOException {
		
		return autoCodeConfigDAO.save(codeConfig);
	}

	

	public void modifyAutoGenCode(AutoCodeConfig codeConfig) throws ServiceException,
			DAOException {
		// TODO Auto-generated method stub
		autoCodeConfigDAO.update(codeConfig);

	}

	public List<AutoCodeConfig> queryCodesByMino(String miNo, Page page)
			throws ServiceException, DAOException {
		// TODO Auto-generated method stub
		return autoCodeConfigDAO.findAutoGenCodesByMiNo(miNo, page);
	}

	
	public AutoCodeConfig findUsingCodeConfig(String codeKey, String miNo)
			throws DAOException, DAOException {
		AutoCodeConfig config = null;
		config= autoCodeConfigDAO.findByKeyAndMino(codeKey, miNo);
		if(config==null)
		{
			config = autoCodeConfigDAO.findByCodeKey(codeKey);
		}
		return config;
    }
	
	
	/**
	 * 自动生成单个编码
	 * @param codeKey
	 * @param miNo
	 * @param paramValues
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public String autoGenCode(String codeKey,String miNo,String[] paramValues) throws ServiceException,DAOException
	{
		String code = "";
		AutoCodeConfig config = findUsingCodeConfig(codeKey,miNo);
		if(config!=null)
		{
			code = genCode(config,miNo, paramValues);
			modifyAutoGenCode(config);
		}
		
		return code;
	}
	private String genCode(AutoCodeConfig config,String miNo,String[] paramValues){
		String code = "";
		// --- 构造前缀编码
		if(miNo==null) miNo = config.getMiNo();
		String expreValue = AutoGenCodeUtil.replaceExpre(config.getPrefixNoRule(),miNo,paramValues);
		
		//--生成编码
		int keepBit = config.getKeepBit().intValue();
		Long curCount=config.getCurCount();
		
		String curBusiDate = DateTimeUtil.get_YYYYMMDD_Date(BusiDateUtil.getCurBusiDate(miNo));
		if(config.getCurDate()!=null&&config.getCurDate().length()==8)  // 格式为yyyyMMdd
		{
			if((AutoCodeConfig.CIRCLE_TYPE_YEAR.equals(config.getCircleType())&&!curBusiDate.substring(0, 4).equals(config.getCurDate().substring(0, 4)))
			   ||(AutoCodeConfig.CIRCLE_TYPE_MONTH.equals(config.getCircleType())&&!curBusiDate.substring(4, 6).equals(config.getCurDate().substring(4, 6)))
			   ||(AutoCodeConfig.CIRCLE_TYPE_DAY.equals(config.getCircleType())&&!curBusiDate.substring(6, 8).equals(config.getCurDate().substring(6, 8)))
			) 
			{
				curCount = new Long(0);
			}
		}
		
		config.setCurDate(curBusiDate);
		
		curCount = curCount+1;
		String countValue = StringUtil.zeroPadString(curCount.toString(), keepBit);
		code = expreValue+countValue;
		
		//----更新 config的计数器
		config.setCurCount(curCount);
		return code;
	}
	/**
	 * 批量自动生成指定个数的编码
	 * @param codeKey
	 * @param miNo
	 * @param paramValues
	 * @param length
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public String[] autoGenCodes(String codeKey,String miNo,String[] paramValues,int length) throws ServiceException,DAOException
	{
		AutoCodeConfig config = findUsingCodeConfig(codeKey,miNo);
		String[] codes = genCodes(config,miNo, paramValues,length); 
		modifyAutoGenCode(config);
		return codes;
	}
	private String[] genCodes(AutoCodeConfig config,String miNo,String[] paramValues,int length)
	{
		String[] codes = new String[length];
		
		// --- 构造前缀编码
		if(miNo==null) miNo = config.getMiNo();
		String expreValue = AutoGenCodeUtil.replaceExpre(config.getPrefixNoRule(),miNo,paramValues);
		
		 //--生成编码
		int keepBit = config.getKeepBit().intValue();
		Long curCount=config.getCurCount();
		
		String curBusiDate = DateTimeUtil.get_YYYYMMDD_Date(BusiDateUtil.getCurBusiDate());
		if(config.getCurDate()!=null&&config.getCurDate().length()==8)  // 格式为yyyyMMdd
		{
			if((AutoCodeConfig.CIRCLE_TYPE_YEAR.equals(config.getCircleType())&&!curBusiDate.substring(0, 4).equals(config.getCurDate().substring(0, 4)))
				||(AutoCodeConfig.CIRCLE_TYPE_MONTH.equals(config.getCircleType())&&!curBusiDate.substring(4, 6).equals(config.getCurDate().substring(4, 6)))
				||(AutoCodeConfig.CIRCLE_TYPE_DAY.equals(config.getCircleType())&&!curBusiDate.substring(6, 8).equals(config.getCurDate().substring(6, 8)))
			) 
			{
				curCount = new Long(0);
			}
		}
		config.setCurDate(curBusiDate);
		
		for(int i=0;i<length;i++)
		{
			curCount = curCount+1;
			codes[i] = expreValue + StringUtil.zeroPadString(curCount.toString(), keepBit);
		}
		// --- 更新 config
		config.setCurCount(curCount);
		return codes;
	}
}
