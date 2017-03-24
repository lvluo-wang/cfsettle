package com.upg.ucars.basesystem.webUtil.tag;

/**
 * 对xcars的datagrid标签的列的描述
 * 
 * @author shentuwy
 * @date 2011-12-1
 **/
public class Column {
	public static final String ALIGN_LEFT = "left";
	public static final String ALIGN_CENTER = "center";
	public static final String ALIGN_RIGHT = "right";
	/**标题*/
	private String title;
	/**与数据源的绑定字段*/
	private String field;
	/**是否是checkbox*/
	private boolean isCheckbox = false;
	/**列宽*/
	private int width = 70;
	/**单元格水平对齐方式，left,right,center*/
	private String align = ALIGN_CENTER;
	/**单元格格式化js函数*/
	private String formatter;
	
	private boolean rownumber = false;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public boolean isCheckbox() {
		return isCheckbox;
	}
	public void setCheckbox(boolean isCheckbox) {
		this.isCheckbox = isCheckbox;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public boolean isRownumber() {
		return rownumber;
	}
	public void setRownumber(boolean rownumber) {
		this.rownumber = rownumber;
	}
	
	
}
