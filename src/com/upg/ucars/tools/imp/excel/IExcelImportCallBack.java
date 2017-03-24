package com.upg.ucars.tools.imp.excel;

import java.util.List;
/**
 * 
 * 功能说明：TODO(excel 文件导入解析完成后的回调接口)
 * @author shentuwy  
 * @date 2012-1-29 下午2:02:31 
 *
 */
public interface IExcelImportCallBack {
	/**
	 * excel文件导入解析回调处理
	 * @param className 配置中的实体类信息
	 * @param objects 解析后的对象集合
	 */
	public void excelImportCallBack(String className,List<Object> objects);
}
