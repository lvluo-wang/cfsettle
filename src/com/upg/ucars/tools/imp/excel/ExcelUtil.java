package com.upg.ucars.tools.imp.excel;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.upg.ucars.basesystem.UcarsHelper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.XmlUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 功能说明：TODO(excel 文件处理工具类，支持文件xls/xlsx解析及文件导出，解析和导出都需要配置文件)
 * @author shentuwy  
 * @date 2012-1-29 下午1:57:02 
 *
 */
public class ExcelUtil {
	private static final String XML_ENCODING = "UTF-8";
	private static final String CONFIG_FILE="excel-config.xml";
	private static HashMap<String,ETOObject> defaultConfig=new HashMap<String,ETOObject>();
	private static HashMap<String ,String> formatMap=new HashMap<String,String>();
	static{

			init();

	}
	/**
	 * 解析大数据量的excel，xml sax的解析方式，适用于07的office
	 * @param stream 输入流
	 * @param fileCode 文件编号
	 * @return
	 * @throws SysException
	 */
	public static ParseResult parseXlsxFile(InputStream stream,String fileCode)throws Exception{
		ParseResult result=new ParseResult();
		if(fileCode==null||fileCode.length()<1){
			result.getErrorList().add("File Code can not be empty ");
			return result;
		}
		try{
			ETOObject eto=defaultConfig.get(fileCode);
			if(eto!=null){
				Object entity1=EntryUtil.getEntity(eto.getClassName());
				if(entity1==null){
					result.getErrorList().add("Configuration 'class' error ");
					return result;
				}
				result.setConfig(eto);
				OPCPackage pkg=OPCPackage.open(stream);
				XSSFReader r = new XSSFReader( pkg );
				SharedStringsTable sst = r.getSharedStringsTable();
				XMLReader parser = fetchSheetParser(sst,eto,result);
				Iterator<InputStream> sheets = r.getSheetsData();
				while(sheets.hasNext()) {
					InputStream sheet = sheets.next();
					InputSource sheetSource = new InputSource(sheet);
					parser.parse(sheetSource);
					sheet.close();
				}
			}else{
				result.getErrorList().add("No config ["+fileCode+"],in the first line of the first page in the first column");
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * 解析Excel文件，适用于小数据量的excel文件解析，支持03-07
	 * @param stream 输入流
	 * @param fileCode excel文件配置码
	 * @return 解析结果
	 * @throws SysException
	 */
	public static  ParseResult parseXlsFile(InputStream stream,String fileCode)throws Exception{
		
		ParseResult result=new ParseResult();
		if(fileCode==null||fileCode.length()<1){
			result.getErrorList().add("File Code can not be empty ");
			return result;
		}
		try{
			ETOObject eto=defaultConfig.get(fileCode);
			if(eto!=null){
				Object entity1=EntryUtil.getEntity(eto.getClassName());
				if(entity1==null){
					result.getErrorList().add("Configuration 'class' error ");
					return result;
				}
				Workbook wb=WorkbookFactory.create(stream);
				Sheet sheet=wb.getSheetAt(0);
				int rowTotal=sheet.getPhysicalNumberOfRows();
				//解析头部
				if(rowTotal>1){
					Row row=sheet.getRow(0);
					int cellTotal=row.getLastCellNum();
					for(int c=0;c<cellTotal;c++){
						int colNumber=c;
						ColumnObject column=eto.getColumnList().get(colNumber);
						Cell cell=row.getCell(c);
						if(column==null){
							if(cell==null||"".equals(getValue(cell))){
								continue;
							}else{
								result.getErrorList().add("Configuration does not match,Column head "+getValue(cell));
								break;
							}
						}
						if(cell==null||"".equals(getValue(cell))){
							result.getErrorList().add("Configuration does not match,Column head number "+c);
							break;
						}
						String value=getValue(cell).toString();
						result.getHeadMap().put(column.getFieldName(), value);
					}
				}
				//解析数据
				for(int r=1;r<rowTotal;r++){
					Row row=sheet.getRow(r);
					Object entity=EntryUtil.getEntity(eto.getClassName());
					int cellTotal=row.getLastCellNum();
					boolean flag=true;
					for(int c=0;c<cellTotal;c++){
						int colNumber=c;
						ColumnObject column=eto.getColumnList().get(colNumber);
						Cell cell=row.getCell(c);
						if(column==null){
							if(cell==null||"".equals(getValue(cell))){
								continue;
							}else{
								result.getErrorList().add("Row "+(r+1)+" ,Column "+(c+1)+" Configuration does not match");
								flag=false;
								break;
							}
						}
						ColumnFormatter format=column.getColumnFormatter();
						if(cell==null||"".equals(getValue(cell))){
							result.getErrorList().add("Row "+(r+1)+" ,Column "+(c+1)+" Configuration does not match");
							flag=false;
							break;
						}else{
							Object value=getValue(cell);
							if(value==null||"".equals(value)){
								if(column.isRequired()){
									result.getErrorList().add("Row "+(r+1)+" ,Column "+(c+1)+" can not be empty");
									flag=false;
								}
							}else{
								if(!format.validate(value)){
									result.getErrorList().add("Row "+(r+1)+" ,Column "+(c+1)+" valid failed");
									flag=false;
								}
							}
							if(value!=null&&!"".equals(value)){
								Object fieldValue=column.getColumnFormatter().format(value);
								EntryUtil.setValue(entity,column.getFieldName(),fieldValue);
							}
						}
						
					}
					if(flag){
						result.getDataList().add( entity);
					}
					
				}
		
				result.setConfig(eto);
			}else{
				result.getErrorList().add("No config ["+fileCode+"],in the first line of the first page in the first column");
			}
			
			
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * 写入excel到输入流
	 * @param fileCode excel文件配置代码
	 * @param writeObj 需要写入的对象
	 * @param inputStream 需要导入的输入流
	 * @throws SysException
	 */
	public static void writeXlsFile(String fileCode,List<Object> writeObj,InputStream inputStream)throws Exception{
		try{
			Workbook wb=getExcelFile(fileCode,writeObj);
			if(wb!=null){
				ByteArrayOutputStream bo=new ByteArrayOutputStream();
				wb.write(bo);
				byte[] bts=bo.toByteArray();
				inputStream=new ByteArrayInputStream(bts,0,bts.length);
				bo.flush();
				bo.close();
			}
		}catch(IOException e){
			throw e;
		}
	}
	/**
	 * 生成大数据量excel文件
	 * @param fileCode 文件编码
	 * @param writeObj 写入的对象
	 * @param inputStream 生成的输入流
	 * @return
	 */
	public static InputStream writeXlsxFile(String fileCode,List<Object> writeObj)throws Exception{
		try{
			ETOObject eto=defaultConfig.get(fileCode);
			if(eto==null){
				throw new Exception("No config ["+fileCode+"]");
			}
			List<ColumnObject> columnList=eto.getColumnList();
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet=wb.createSheet(fileCode);
			
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			//取sheet的全名
			String sheetRef=sheet.getPackagePart().getPartName().getName();
			
			//设置临时文件
			FileOutputStream os=new FileOutputStream("template.xlsx");
			wb.write(os);
			os.close();
			
			//生成xml 文件
			File tmp=File.createTempFile("sheet", ".xml");
			Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			generate(fw,styles,columnList,writeObj);
			fw.close();
			
			//生成文件缓存在内存中
			File outFile=new File("big_template.xlsx");
			FileOutputStream out = new FileOutputStream(outFile);
			substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
			out.close();
			//从内存中获取输入流
			return new FileInputStream(outFile);
			
			
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 写大数据量excel文件到输出流
	 * @param fileCode 文件编码
	 * @param writeObj 写入的对象
	 * @param out 输出流
	 */
	public static void writeXlsxFile(String fileCode,List<Object> writeObj,OutputStream out)throws Exception{
		try{
			ETOObject eto=defaultConfig.get(fileCode);
			if(eto==null){
				throw new Exception("No config ["+fileCode+"]");
			}
			List<ColumnObject> columnList=eto.getColumnList();
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet=wb.createSheet(fileCode);
			
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			//取sheet的全名
			String sheetRef=sheet.getPackagePart().getPartName().getName();
			
			//设置临时文件
			FileOutputStream os=new FileOutputStream("template.xlsx");
			wb.write(os);
			os.close();
			
			//生成xml 文件
			File tmp=File.createTempFile("sheet", ".xml");
			Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			generate(fw,styles,columnList,writeObj);
			fw.close();
			
			//生成文件缓存在内存中
			substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
			out.close();
			
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 生成excel模板
	 * @param fileCode 文件编号
	 * @param inputStream 输入流
	 */
	public static InputStream  writeExcelTemplate(String fileCode)throws Exception{
		try{
			Workbook wb=getExcelFile(fileCode,null);
			if(wb!=null){
				ByteArrayOutputStream bo=new ByteArrayOutputStream();
				wb.write(bo);
				bo.flush();
				byte[] bts=bo.toByteArray();
				bo.close();
				return new ByteArrayInputStream(bts,0,bts.length);
			}
		}catch(IOException e){
			throw e;
		}
		return null;
	}
	private static void substitute(File zipfile, File tmpfile, String entry,
			OutputStream out) throws ZipException, IOException {
		ZipFile zip = new ZipFile(zipfile);

	     ZipOutputStream zos = new ZipOutputStream(out);

	     @SuppressWarnings("unchecked")
	     Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
	     while (en.hasMoreElements()) {
	         ZipEntry ze = en.nextElement();
	         if(!ze.getName().equals(entry)){
	             zos.putNextEntry(new ZipEntry(ze.getName()));
	             InputStream is = zip.getInputStream(ze);
	             copyStream(is, zos);
	             is.close();
	         }
	     }
	     zos.putNextEntry(new ZipEntry(entry));
	     InputStream is = new FileInputStream(tmpfile);
	     copyStream(is, zos);
	     is.close();

	     zos.close();
		
	}
	private static void generate(Writer out, Map<String, XSSFCellStyle> styles,
			List< ColumnObject> columnList, List<Object> dataList) throws Exception {
		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();
		//写入数据头部
		writeHeadRowXml(sw,columnList,styles);
		//写入数据体
		writeDataRowXml(dataList,sw,columnList,styles);
		sw.endSheet();
		
	}
	private static void copyStream(InputStream in,OutputStream out) throws IOException{
		byte[] chunk=new byte[1024];
		int count;
		while((count=in.read(chunk))>=0){
			out.write(chunk,0,count);
		}
	}
	private static void writeDataRowXml(List<Object> dataList,SpreadsheetWriter sw,List< ColumnObject> columnList,Map<String, XSSFCellStyle> styles) throws Exception{
		for(int rownum=1;rownum<=dataList.size();rownum++){
			Object o=dataList.get(rownum-1);
			sw.insertRow(rownum);
			for(int i=0;i<columnList.size();i++){
				ColumnObject coo=columnList.get(i);
				String format=coo.getColumnFormatter().getFormat();
				int styleIndex=-2;
				if(format!=null){
					if(styles.get(format)!=null){
						styleIndex=styles.get(format).getIndex();
					}
				}
				String fieldName=coo.getFieldName();
				Object value=EntryUtil.getValue(o,fieldName);
				if(value!=null){
					if(value instanceof Double){
						if(styleIndex!=-2){
							sw.createCell(i, (Double)value,styleIndex);
						}else{
							sw.createCell(i, (Double)value,styles.get("money").getIndex());
						}
						
					}else{
						if(value instanceof Date){
							Calendar calendar = Calendar.getInstance();
							calendar.setTime((Date)value);
							if(styleIndex!=-2){
								sw.createCell(i,calendar,styleIndex);
							}else{
								sw.createCell(i,calendar,styles.get("date").getIndex());
							}
							
						}else{
							sw.createCell(i, value.toString());
						}
					} 
				}else{
					sw.createCell(i, "");
				}
			}
			sw.endRow();
		}
	}
	private static void writeHeadRowXml(SpreadsheetWriter sw,List< ColumnObject> columnList,Map<String, XSSFCellStyle> styles) throws IOException{
		int headStyleIndex=styles.get("header").getIndex();
		sw.insertRow(0);
		for(int i=0;i<columnList.size();i++){
			ColumnObject coo=columnList.get(i);
			String cName=coo.getColumnName();
			sw.createCell(i, cName,headStyleIndex);
		}
		sw.endRow();
	}
	private static Workbook getExcelFile(String fileCode,List<Object> dataList)throws Exception{
		XSSFWorkbook wb=null;
		ETOObject eto=defaultConfig.get(fileCode);
		if(eto==null){
			throw new Exception("No config ["+fileCode+"]");
		}
		try{
			
			wb = new XSSFWorkbook();
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			Sheet sheet=wb.createSheet(fileCode);
			//数据填充
			//当前页的数据列配置
			List<ColumnObject> columnList=eto.getColumnList();
			//填充头部数据
			writeHeadRow(sheet,columnList,styles);
			//填充数据
			writeDataRow(sheet,columnList,dataList,styles);
		
		}catch(Exception e){
			throw e;
		}
		return wb;
	}
	private static void writeDataRow(Sheet sheet,List<ColumnObject> columnList,List<Object> dataList, Map<String, XSSFCellStyle> styles) throws Exception{
		if(dataList==null){
			return;
		}
		for(int d=0;d<dataList.size();d++){
			//创建行
			int rowIndex=d+1;
			Row row=sheet.createRow(rowIndex);
			Object data=dataList.get(d);
			for(int i=0;i<columnList.size();i++){
				ColumnObject co=columnList.get(i);
				Cell cell=row.createCell(i);
				String fieldName=co.getFieldName();
				String format=co.getColumnFormatter().getFormat();
				Object fieldValue=EntryUtil.getValue(data,fieldName);
				if(fieldValue!=null){
					if(fieldValue instanceof Double){
						cell.setCellValue((Double)fieldValue);
					}else if(fieldValue instanceof Date){
						Calendar calendar = Calendar.getInstance();
						calendar.setTime((Date)fieldValue);
						cell.setCellValue(calendar);
					}else{
						cell.setCellValue(fieldValue.toString());
					}
				}
				if(styles.get(format)!=null){
					cell.setCellStyle(styles.get(format));
				}
			}
		}
	}
	private static void writeHeadRow(Sheet sheet,List<ColumnObject> columnList,Map<String, XSSFCellStyle> styles){
		Row row=sheet.createRow(0);
		for(int i=0;i<columnList.size();i++){
			//创建列
			ColumnObject co=columnList.get(i);
			String columnName=co.getColumnName();
			Cell cell=row.createCell(i);
			cell.setCellStyle(styles.get("header"));
			cell.setCellValue(columnName);
		}
	}
	private static void init() {
			Document doc = null;
			try {
				doc = XmlUtil.parseXmlDoc(CONFIG_FILE);
				if(doc!=null){
					Element root=doc.getRootElement();
					List formatList=root.getChildren("format");
					for(int i=0;i<formatList.size();i++){
						Element formatElement=(Element)formatList.get(i);
						String name=formatElement.getAttribute("name").getValue();
						String clazz=formatElement.getAttribute("class").getValue();
						formatMap.put(name, clazz);
					}
					List resList=root.getChildren("include");
					for(int i=0;i<resList.size();i++){
						Element resElement=(Element)resList.get(i);
						String path=resElement.getAttribute("path").getValue();
						parseConfig(path);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.getExceptionLog().error(e);
			}

	}

	private static void parseConfig(String path) {
		Document doc = null;
		try {
			doc = XmlUtil.parseXmlDoc(path);
			Element root = doc.getRootElement();
			List fileList=root.getChildren("file");
			for(int i=0;i<fileList.size();i++){
				ETOObject eto=new ETOObject();
				Element fileE=(Element)fileList.get(i);
				String fileCode=fileE.getAttribute("code").getValue();
				String clazz=fileE.getAttribute("class").getValue();
				Attribute callBackAt=fileE.getAttribute("callBack");
				String callBack=callBackAt==null?"":callBackAt.getValue();
				List columnList=fileE.getChildren("column");
				for(int k=0;k<columnList.size();k++){
					Element columnE=(Element)columnList.get(k);
					String columnName=columnE.getAttribute("name").getValue();
					String columnField=columnE.getAttribute("field").getValue();
					Attribute formatterAt=columnE.getAttribute("format");
					Attribute requiredAt=columnE.getAttribute("required");
					boolean required=requiredAt==null?false:Boolean.valueOf(requiredAt.getValue());
					String columnFormatter=formatterAt==null?DefaultFormatter.CODE:formatterAt.getValue();
					String formatClass=formatMap.get(columnFormatter);
					ColumnFormatter formatter=null;
					if(formatClass==null){
						formatter=new DefaultFormatter();
					}else{
						formatter=getFormatter(formatClass);
					}
					ColumnObject column=new ColumnObject();
					column.setColumnFormatter(formatter);
					column.setFieldName(columnField);
					column.setColumnName(columnName);
					column.setRequired(required);
					eto.getColumnList().add(k, column);
				}
				eto.setClassName(clazz);
				eto.setCode(fileCode);
				eto.setCallBack(callBack);
				defaultConfig.put(fileCode, eto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.getExceptionLog().error(e);
		}
		
	}
	private static ColumnFormatter getFormatter(String className){
		return (ColumnFormatter)EntryUtil.getEntity(className);
	}
	private static Object getValue(Cell cell){
		Object value=null;
		switch(cell.getCellType()){
		case Cell.CELL_TYPE_BLANK:
			value="";
			break;
		case Cell.CELL_TYPE_ERROR:
			value="";
			break;
		case Cell.CELL_TYPE_FORMULA:
			value=cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value=cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value=cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_STRING:
			value=cell.getStringCellValue();
			break;
		default :
			value=cell.getRichStringCellValue().toString();
		}
		return value;
	}
	 private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
	     Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
	     XSSFDataFormat fmt = wb.createDataFormat();

	     XSSFCellStyle style3 = wb.createCellStyle();
	     style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	     style3.setDataFormat(fmt.getFormat("#,##0.00"));
	     styles.put("money", style3);
	     
	     XSSFCellStyle style4 = wb.createCellStyle();
	     style4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	     style4.setDataFormat(fmt.getFormat("yyyy-MM-dd"));
	     styles.put("date", style4);
	     
	     XSSFCellStyle style5 = wb.createCellStyle();
	     XSSFFont headerFont = wb.createFont();
	     headerFont.setBold(true);
	     style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	     style5.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	     style5.setFont(headerFont);
	     styles.put("header", style5);
	     return styles;
	 }
	 private static XMLReader fetchSheetParser(SharedStringsTable sst,ETOObject eto,ParseResult result) throws SAXException {
			XMLReader parser =
				XMLReaderFactory.createXMLReader(
						"org.apache.xerces.parsers.SAXParser"
				);
			ContentHandler handler = new SheetHandler(sst,eto,result);
			parser.setContentHandler(handler);
			return parser;
		}
	 
	 public static HSSFWorkbook createHSSFWorkbook(String title, String[] headers, List<List<Object>> dataRows) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			workbook = (HSSFWorkbook) processWorkbook(workbook, title, headers, dataRows);
			return workbook;
		}

		private static Workbook processWorkbook(Workbook workbook, String title, String[] headers,
				List<List<Object>> dataRows) {
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet(title);
			sheet.setDefaultColumnWidth(23);
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);

			Row row = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				RichTextString text = null;
				if (workbook instanceof HSSFWorkbook) {
					text = new HSSFRichTextString(headers[i]);
				} else {
					text = new XSSFRichTextString(headers[i]);
				}
				cell.setCellValue(text);
			}

			int index = 0;
			for (List<Object> dataRow : dataRows) {
				index++;
				row = sheet.createRow(index);
				int cellIndex = 0;
				for (Object data : dataRow) {
					Cell cell = row.createCell(cellIndex++);
					if (data instanceof String) {
						cell.setCellValue(data == null ? null : data.toString());
					} else if (data instanceof Double) {
						cell.setCellValue(data == null ? null : (Double) data);
					} else if (data instanceof Date) {
						cell.setCellValue(data == null ? null : (Date) data);
						CellStyle dateCellStyle = workbook.createCellStyle();
						dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd"));
						cell.setCellStyle(dateCellStyle);
					} else if (data instanceof Calendar) {
						cell.setCellValue(data == null ? null : (Calendar) data);
					} else if (data instanceof RichTextString) {
						cell.setCellValue(data == null ? null : (RichTextString) data);
					} else if (data instanceof Boolean) {
						cell.setCellValue(data == null ? null : (Boolean) data);
					} else {
						cell.setCellValue(data == null ? null : data.toString());
					}
				}
			}
			return workbook;
		}

		public static XSSFWorkbook createXSSFWorkbook(String title, String[] headers, List<List<Object>> dataRows) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			workbook = (XSSFWorkbook) processWorkbook(workbook, title, headers, dataRows);
			return workbook;
		}

	/**
	 * 生成2003版EXCEL文件
	 *
	 * @param encoding
	 * @param fileName
	 * @param response
	 */
	public static void createExcel2003(String encoding, String fileName,
									   HttpServletResponse response, String sheetName,
									   Map<String, Integer> excelHeaders,
									   List<Map<String, Object>> excelDatas) {
		try {
			OutputStream outputStream = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding(encoding);
			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO_8859_1")
					+ ".xls");

			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet(sheetName);
			HSSFRow row = sheet.createRow(0);
			// 设置字体
			HSSFFont font = book.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			HSSFDataFormat format = book.createDataFormat();

			HSSFCellStyle cellStyle = book.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setWrapText(true);

			HSSFCellStyle cellStyle2 = book.createCellStyle();
			cellStyle2.setDataFormat(format.getFormat("#,##0.00"));
			cellStyle2.setWrapText(true);

			for (String title : excelHeaders.keySet()) {
				Integer columncount = excelHeaders.get(title);
				sheet.setColumnWidth(columncount, 5000);
				HSSFCell cell = row.createCell(columncount);
				cell.setCellValue(title);
				cell.setCellStyle(cellStyle);// 设置单元格样式
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 指定单元格格式：数值、公式或字符串
			}

			Integer rowNum = 1;
			for (Map<String, Object> excelData : excelDatas) {
				row = sheet.createRow(rowNum);
				for (String key : excelData.keySet()) {
					Object val = excelData.get(key);
					Integer columnNum = excelHeaders.get(key);
					if (val instanceof BigDecimal) {
						HSSFCell cell = row.createCell(columnNum);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);// 指定单元格格式：数值、公式或字符串
						DecimalFormat numFormat = new DecimalFormat("#,##0.00");
						cell.setCellValue(numFormat.format(val == null ? 0.00
								: ((BigDecimal) val).doubleValue()));
						cell.setCellValue(val == null ? 0.00
								: ((BigDecimal) val).doubleValue());
					} else {
						HSSFCell cell = row.createCell(columnNum);
						cell.setCellValue(val == null ? "" : val.toString());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 指定单元格格式：数值、公式或字符串
					}
				}
				rowNum++;
			}

			book.write(outputStream);
		} catch (IOException e) {
			UcarsHelper.throwServiceException("生成EXCEL文件异常");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成2003版EXCEL文件(数据库字段必须和EXACL的字段对应)
	 *
	 * @author AMARKY
	 * @param encoding
	 *            编码
	 * @param filePath
	 *            文件路径
	 * @param response
	 *            输入流
	 * @param fileName
	 *            文件名
	 * @param sheetName
	 *            单元格名称
	 * @param excelHeaders
	 *            文件头map
	 * @param excelDatas
	 *            数据
	 * @param temp
	 *            中间过度的map 没有通过struts的方法上传文件
	 */
	public static void createExcel2003Add(String encoding, String filePath,
										  HttpServletResponse response, String fileName, String sheetName,
										  Map<String, Integer> excelHeaders,
										  List<Map<String, Object>> excelDatas, Map<String, String> temp) {
		FileOutputStream fileOutputStream = null;
		OutputStream outputStream = null;
		try {
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet(sheetName);
			HSSFRow row = sheet.createRow(0);
			// 设置字体
			HSSFFont font = book.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			HSSFDataFormat format = book.createDataFormat();

			HSSFCellStyle cellStyle = book.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setWrapText(true);

			HSSFCellStyle cellStyle2 = book.createCellStyle();
			cellStyle2.setDataFormat(format.getFormat("#,##0.00"));
			cellStyle2.setWrapText(true);

			for (String title : excelHeaders.keySet()) {
				Integer columncount = excelHeaders.get(title);
				sheet.setColumnWidth(columncount, 5000);
				HSSFCell cell = row.createCell(columncount);
				cell.setCellValue(title);
				cell.setCellStyle(cellStyle);// 设置单元格样式
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 指定单元格格式：数值、公式或字符串
			}

			Integer rowNum = 1;
			for (Map<String, Object> excelData : excelDatas) {
				row = sheet.createRow(rowNum);
				for (String key : excelData.keySet()) {
					Object val = excelData.get(key);
					String newKey = temp.get(key);
					Integer columnNum = excelHeaders.get(newKey);
					if (val instanceof BigDecimal) {
						HSSFCell cell = row.createCell(columnNum);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);// 指定单元格格式：数值、公式或字符串
						DecimalFormat numFormat = new DecimalFormat("#,##0.00");
						cell.setCellValue(numFormat.format(val == null ? 0.00
								: ((BigDecimal) val).doubleValue()));
						cell.setCellValue(val == null ? 0.00
								: ((BigDecimal) val).doubleValue());
					} else {
						HSSFCell cell = row.createCell(columnNum);
						cell.setCellValue(val == null ? "" : val.toString());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 指定单元格格式：数值、公式或字符串
					}
				}
				rowNum++;
			}
			if (filePath != null && response == null) {
				fileOutputStream = new FileOutputStream(
						filePath
								+ (new String(fileName.getBytes("GB2312"),
								"ISO_8859_1") + ".xls"));
				book.write(outputStream);
			} else if (response != null && filePath == null) {
				outputStream = response.getOutputStream();
			}
		} catch (IOException e) {
			UcarsHelper.throwServiceException("生成EXCEL文件异常");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				} else if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}