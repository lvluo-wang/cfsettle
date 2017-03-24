package com.upg.ucars.basesystem.webUtil.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.tools.imp.excel.ExcelUtil;
import com.upg.ucars.tools.imp.excel.IExcelImportCallBack;
import com.upg.ucars.tools.imp.excel.ParseResult;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * 功能说明：TODO(Excel的导入导出演示)
 * @author shentuwy  
 * @date 2011-7-27 上午11:20:24 
 *
 */
public class ExcelAction extends BaseAction {
	private static final String CURRENT_RESULT="CURRENT_PARSE_RESULT";
	@Override
	public void addActionError(String anErrorMessage) {
		// TODO Auto-generated method stub
		super.addActionError(anErrorMessage);
	}
	@Override
	public void addActionMessage(String aMessage) {
		// TODO Auto-generated method stub
		super.addActionMessage(aMessage);
	}
	@Override
	public void addFieldError(String fieldName, String errorMessage) {
		// TODO Auto-generated method stub
		super.addFieldError(fieldName, errorMessage);
	}
	private InputStream excelStream;
	private InputStream resultStream;
	private String dataKey;
	private File excelFile;

	private String excelCode;
	private String fileName;
	private int index;
	private String change;
	public String mainPage(){
		System.out.println(":::::::::::::::::"+excelCode);
		
		return "mainPage";
	}
	public String uploadFile()throws Exception{
		InputStream is=null;
		try{
		is=new FileInputStream(excelFile);
		ParseResult result=ExcelUtil.parseXlsxFile(is,excelCode);
		HashMap map=new HashMap();
		if(result!=null){
			if(result.getHeadMap()!=null){
				map.put("head", result.getHeadMap());
			}
			if(result.getErrorList()!=null){
				map.put("errorList", result.getErrorList());
			}
		}
		SessionTool.setAttribute(CURRENT_RESULT, result);
		JSONObject json=JSONObject.fromObject(map);
		resultStream=outJsonUTFStream(json);
		System.out.println(json.toString());
		}catch(Exception e){
			throw e;
		}finally{
			if(is!=null){
				is.close();
			}
		}
		return "uploadFile";
	}

	public String queryPage()throws Exception{
		ParseResult result=(ParseResult)SessionTool.getAttribute(CURRENT_RESULT);
		HashMap map=new HashMap();
		List<Object> data=result.getDataList();
		int total=data.size();
		int cp=getPage();
		int pgSize=getRows();
		int fromIndex=(cp-1)*pgSize;
		int toIndex=fromIndex+pgSize;
		if(toIndex>total){
			toIndex=total;
		}
		if(result!=null){
			map.put("total", data.size());
			map.put("rows", data.subList(fromIndex, toIndex));
		}
		JSONObject json=JSONObject.fromObject(map);
		resultStream=outJsonUTFStream(json);
		System.out.println(json.toString());
		return "uploadFile";
	}
	public void updateRow()throws Exception{
		ParseResult result=(ParseResult)SessionTool.getAttribute(CURRENT_RESULT);
		List<Object> data=result.getDataList();
		Object obj=data.get(index);
		if(change!=null&&change.length()>0){
			String[] changes=change.split(",");
			if(changes!=null&&changes.length>0){
				for(int i=0;i<changes.length;i++){
					String cg=changes[i];
					String[] cgs=cg.split(":");
					if(cgs!=null&&cgs.length==2){
						String key=cgs[0];
						String value=cgs[1];
						if(obj!=null){
							Field fd=obj.getClass().getDeclaredField(key);
							if(fd!=null){
								fd.setAccessible(true);
								fd.set(obj, value);
							}
						}
					}
				}
			}
		}
	}
	public void deleteRow()throws Exception{
		ParseResult result=(ParseResult)SessionTool.getAttribute(CURRENT_RESULT);
		List<Object> data=result.getDataList();
		data.remove(index);
	}
	public void dataImport()throws Exception{
		ParseResult result=(ParseResult)SessionTool.getAttribute(CURRENT_RESULT);
		if(result!=null){
			String serviceBeanName =result.getConfig().getCallBack();
			if(serviceBeanName!=null&&serviceBeanName.length()>1){
				IExcelImportCallBack cb= SourceTemplate.getBean(IExcelImportCallBack.class,serviceBeanName);
				if(cb!=null){
					cb.excelImportCallBack(dataKey, result.getDataList());
				}
			}
			SessionTool.removeAttribute(CURRENT_RESULT);
		}
		
	}
	public String dataExport()throws Exception{
		ParseResult result=(ParseResult)SessionTool.getAttribute(CURRENT_RESULT);
		fileName=result.getConfig().getCode()+".xlsx";
		excelStream=ExcelUtil.writeXlsxFile(result.getConfig().getCode(), result.getDataList());
		return "downloadFile";
	}
	public String templateExport()throws Exception{
		fileName=excelCode+".xlsx";;
		excelStream=ExcelUtil.writeExcelTemplate(excelCode);
		return "downloadFile";
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getExcelCode() {
		return excelCode;
	}
	public void setExcelCode(String excelCode) {
		this.excelCode = excelCode;
	}

	public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	public InputStream getResultStream() {
		return resultStream;
	}
	public void setResultStream(InputStream resultStream) {
		this.resultStream = resultStream;
	}
	public String getDataKey() {
		return dataKey;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
}
