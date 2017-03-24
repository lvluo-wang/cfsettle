/*
 * 源程序名称: UperAliasToEntityMapResultTransformer.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：Hiberante基础框架
 * 
 */

package com.upg.ucars.framework.base.hibernate;

/**
 * 
 * 功能说明：扩展Hiberante的BasicTransformerAdapter，实现的自己结果集转换器
 *         主要将别名key值统一设置为大写
 * @author shentuwy  
 * @date 2011 六月 28 18:33:48 
 *
 */

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.BasicTransformerAdapter;

public class UpperAliasToEntityMapResultTransformer extends BasicTransformerAdapter {

	public static final UpperAliasToEntityMapResultTransformer	INSTANCE	= new UpperAliasToEntityMapResultTransformer();

	/**
	 * Disallow instantiation of UpperAliasToEntityMapResultTransformer.
	 */
	private UpperAliasToEntityMapResultTransformer() {
	}

	/**
	 * {@inheritDoc}
	 */
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				Object obj = tuple[i];
				if (obj != null) {
					if (obj instanceof java.sql.Date) {
						obj = new java.util.Date(((java.sql.Date) obj).getTime());
					} else if (obj instanceof Clob) {
						obj = Clob2String((Clob)obj);
					}
				}
				result.put(alias.toUpperCase(), obj);
			}
		}
		return result;
	}

	/**
	 * Serialization hook for ensuring singleton uniqueing.
	 * 
	 * @return The singleton instance : {@link #INSTANCE}
	 */
	private Object readResolve() {
		return INSTANCE;
	}

	private String Clob2String(Clob clob) {
		String result = null;
		if (clob != null) {
			try{
				StringBuilder sb = new StringBuilder();
				Reader in = clob.getCharacterStream();
				BufferedReader br = new BufferedReader(in);
				
				String line = br.readLine();
				while(line != null){
					sb.append(line);
					line = br.readLine();
				}
				
				result = sb.toString();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
}
