package com.upg.ucars.tools.imp.excel;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
/**
 * 
 * 功能说明：TODO(xlsx文件生成器)
 * @author shentuwy  
 * @date 2012-1-29 下午2:59:04 
 *
 */
public class SpreadsheetWriter {
	private static final String XML_ENCODING = "UTF-8";
	private final Writer _out;
    private int _rownum;

    public SpreadsheetWriter(Writer out){
        _out = out;
    }
    /**
     * 创建页的起始部分
     * @throws IOException
     */
    public void beginSheet() throws IOException {
        _out.write("<?xml version=\"1.0\" encoding=\""+XML_ENCODING+"\"?>" +
                "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );
        _out.write("<sheetData>\n");
    }
    /**
     * 创建页的结束部分
     * @throws IOException
     */
    public void endSheet() throws IOException {
        _out.write("</sheetData>");
        _out.write("</worksheet>");
    }

    /**
     * 创建行的起始部分
     *
     * @param 行号（最小为0）
     */
    public void insertRow(int rownum) throws IOException {
        _out.write("<row r=\""+(rownum+1)+"\">\n");
        this._rownum = rownum;
    }

    /**
     * 创建行的结束部分
     */
    public void endRow() throws IOException {
        _out.write("</row>\n");
    }
    /**
     * 创建单元格
     * @param columnIndex 列索引
     * @param value 值
     * @param styleIndex 样式
     * @throws IOException
     */
    public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
        String ref = new CellReference(_rownum, columnIndex).formatAsString();
        _out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
        if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
        _out.write(">");
        _out.write("<is><t>"+value+"</t></is>");
        _out.write("</c>");
    }

    public void createCell(int columnIndex, String value) throws IOException {
        createCell(columnIndex, value, -1);
    }
    /**
     * 创建单元格
     * @param columnIndex 列索引
     * @param value 值（数字类型）
     * @param styleIndex 样式
     * @throws IOException
     */
    public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
        String ref = new CellReference(_rownum, columnIndex).formatAsString();
        _out.write("<c r=\""+ref+"\" t=\"n\"");
        if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
        _out.write(">");
        _out.write("<v>"+value+"</v>");
        _out.write("</c>");
    }

    public void createCell(int columnIndex, double value) throws IOException {
        createCell(columnIndex, value, -1);
    }
    /**
     * 创建单元格
     * @param columnIndex 列索引
     * @param value 值（日期类型）
     * @param styleIndex 样式
     * @throws IOException
     */
    public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
        createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
    }
}
