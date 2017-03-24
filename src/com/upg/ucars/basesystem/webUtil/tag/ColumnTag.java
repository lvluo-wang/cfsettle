package com.upg.ucars.basesystem.webUtil.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * column标签实现类
 * @author shentuwy
 * @date 2011-12-1
 **/
public class ColumnTag extends TagSupport {
	private static final long serialVersionUID = 4595126856147970993L;
	private String title="";
	private String field;
	private boolean checkbox = false;
	private int width = 70;
	private String align = Column.ALIGN_CENTER;
	private String formatter;
	private boolean rownumber = false;
	
	public ColumnTag(){super();}
	
	/** 标签初始方法 */
	public int doStartTag() throws JspTagException{
	      return Tag.SKIP_BODY;
	}
	
	/** 标签结束方法 */
	public int doEndTag() throws JspTagException{
		Column column = new Column();
		column.setTitle(this.title);
		column.setField(this.field);
		column.setCheckbox(this.checkbox);
		column.setWidth(this.width);
		if(this.align != null){
			this.align = this.align.toLowerCase();
			if(!Column.ALIGN_LEFT.equals(this.align) && !Column.ALIGN_CENTER.equals(this.align) && !Column.ALIGN_RIGHT.equals(this.align))
				this.align = Column.ALIGN_CENTER;
		}else
			this.align = Column.ALIGN_CENTER;
		
		column.setAlign(this.align);
		column.setFormatter(this.formatter);
		column.setRownumber(rownumber);
		//TagSupport parentTag = (TagSupport)this.getParent();
		List<Column> columns = (List<Column>)this.pageContext.getAttribute(ColumnsTag.COLUMNS_KEY);
		//List<Column> columns = (List<Column>)parentTag.getValue(ColumnsTag.COLUMNS_KEY);
		columns.add(column);
		return Tag.EVAL_PAGE;
	}

	/** 释放资源 */
	public void release(){
	      super.release();
	}
	
	
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
		return checkbox;
	}
	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
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
