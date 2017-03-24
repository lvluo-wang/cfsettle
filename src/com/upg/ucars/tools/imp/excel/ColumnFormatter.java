package com.upg.ucars.tools.imp.excel;

import java.io.Serializable;

import com.upg.ucars.framework.exception.SysException;

/**
 * 
 * 功能说明：TODO(列格式转化器接口，用于excel导入导出时excel文件列于实际对象之前的处理)
 * @author shentuwy  
 * @date 2012-1-29 下午1:12:49 
 *
 */
public abstract class ColumnFormatter implements Serializable {
	/**
	 * 根据列的值获取字段的值
	 * @param columnValue excel单元格中的值
	 * @return
	 * @throws SysException
	 */
	public abstract Object format(Object columnValue)throws Exception;
	/**
	 * 校验列值是否合法
	 * @param columnValue excel中单元格的值
	 * @return
	 * @throws SysException
	 */
	public abstract Boolean validate(Object columnValue)throws Exception;
	/**
	 * 获取格式文本
	 * @return
	 * @throws SysException
	 */
	public abstract String getFormat()throws Exception;
}
