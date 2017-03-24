package com.upg.ucars.tools.imp.excel;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 
 * 功能说明：TODO(excel文件页处理器，用于xlsx文件的xml/sax方式解析)
 * @author shentuwy  
 * @date 2012-1-29 下午2:10:46 
 *
 */
public class SheetHandler extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int rownumber=0;
	private int columnnumber=0;
	private Object currentObj=null;
	private boolean flag=true;
	private ETOObject eto;
	private ParseResult result;
	public SheetHandler(SharedStringsTable sst,ETOObject eto,ParseResult result) {
		this.sst = sst;
		this.eto=eto;
		this.result=result;
	}
	
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		
		if(name.equals("row")){
			rownumber=Integer.parseInt(attributes.getValue("r"));
			columnnumber=0;
			flag=true;
			String clazz=eto.getClassName();
			try {
				currentObj=EntryUtil.getEntity(clazz);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// c => cell
		if(name.equals("c")) {
			columnnumber++;
			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue("t");
			if(cellType != null && cellType.equals("s")) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		// Clear contents cache
		lastContents = "";
	}
	
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// Process the last contents as required.
		// Do now, as characters() may be called more than once
		// v => contents of a cell
		// Output after we've seen the string contents
		if(name.equals("v")) {
			ColumnObject co=eto.getColumnList().get(columnnumber-1);
			if(co==null){
				//当前列没有对应的数据配置，则跳过
				return;
			}
			if(nextIsString) {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			}
			String fieldName=co.getFieldName();
			ColumnFormatter formater=co.getColumnFormatter();
			if(rownumber==1){
				//头部解析
				result.getHeadMap().put(fieldName, lastContents);
				flag=false;
			}else{
				//数据解析
				if(co.isRequired()){
					if("".equals(lastContents)){
						result.getErrorList().add("Row "+rownumber+" ,Column "+columnnumber+" can not be empty");
						flag=false;
						return;
					}
					
				}
				try {
					if (formater.validate(lastContents)) {
						Object fieldValue = formater.format(lastContents);
						EntryUtil.setValue(currentObj, fieldName, fieldValue);
					} else {
						result.getErrorList().add(
								"Row " + rownumber + " ,Column " + columnnumber
										+ " valid failed");
						flag = false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(name.equals("row")){
			//一行结束后
			if(flag){
				result.getDataList().add(currentObj);
			}
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		lastContents += new String(ch, start, length);
	}
}
