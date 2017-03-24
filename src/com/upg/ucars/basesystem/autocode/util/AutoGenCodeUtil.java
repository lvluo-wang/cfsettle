package com.upg.ucars.basesystem.autocode.util;


import com.upg.ucars.basesystem.autocode.core.codeconfig.IAutoCodeConfigService;
import com.upg.ucars.basesystem.busidate.util.BusiDateUtil;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SourceTemplate;

/**
 * 自动编码生成工具类
 * @author alw
 *
 */

public class AutoGenCodeUtil {
	
	public static String[] expre = new String[]{"${year}","${month}","${day}","${date}","${datetime}","${miNo}"};
	public static String PARAM = "param";
	
	
	/**
	 * 自动生成单个编码
	 * @param codeKey
	 * @param miNo
	 * @param paramValues
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public static String autoGenCode(String codeKey,String miNo,String[] paramValues)
	{	
		IAutoCodeConfigService configService = (IAutoCodeConfigService)SourceTemplate.getBean("autoCodeConfigService");
		
		return configService.autoGenCode(codeKey,miNo,  paramValues);
		
	}
	/**
	 * 批量自动生成指定个数的编码
	 * @param codeKey
	 * @param miNo
	 * @param paramValues
	 * @param count
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public static String[] autoGenCodes(String codeKey,String miNo,String[] paramValues,int count)
	{	
		IAutoCodeConfigService configService = (IAutoCodeConfigService)SourceTemplate.getBean("autoCodeConfigService");
		
		return configService.autoGenCodes(codeKey,miNo, paramValues,count);
		
	}
	
	
	/**
	 * 替换表达式
	 * @param src
	 * @param paramValues
	 * @return
	 */
    public static String replaceExpre(String src,String miNo,String[] paramValues)
    {
    	String dest = src;
    	
    	String[] values = new String[expre.length];
    	String dateStr = DateTimeUtil.get_YYYYMMDD_Date(BusiDateUtil.getCurBusiDate(miNo));
    	for(int i=0;i<expre.length;i++)
    	{
    		if(("${year}").equals(expre[i])) values[i]=  dateStr.substring(0, 4);
    		if(("${month}").equals(expre[i])) values[i]=  dateStr.substring(4, 6);
    		if(("${day}").equals(expre[i])) values[i]=  dateStr.substring(6, 8);
    		if(("${date}").equals(expre[i])) values[i]=  dateStr;
    		if(("${datetime}").equals(expre[i])) values[i]=DateTimeUtil.getCurDateTime();
    		if(("${miNo}").equals(expre[i])) values[i]=miNo==null?"":miNo;
    		
    	}
    	for(int i=0;i<values.length;i++)
    	{
    		
    		dest = dest.replace(expre[i], values[i]);
    	}
    	
    	if(paramValues!=null)
    	{
	    	for(int i=0;i<paramValues.length;i++)
	    	{
	    		String param="${"+PARAM+(i+1)+"}";
	    		dest=dest.replace( param, paramValues[i]);
	    	}
    	}
    	
    	return dest;
    }
    
}
