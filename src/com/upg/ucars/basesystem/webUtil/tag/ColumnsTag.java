package com.upg.ucars.basesystem.webUtil.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Columns标签类
 * 
 * @author shentuwy
 * @date 2011-12-1
 **/
public class ColumnsTag extends TagSupport {
	private static final long serialVersionUID = -1328130539997606232L;
	public static final String COLUMNS_KEY = "COLUMNS_KEY";
	public ColumnsTag(){super();}
	
	/** 标签初始方法 */
	public int doStartTag() throws JspTagException{
//		  List<Column> columns = (List<Column>)this.getValue(COLUMNS_KEY);
//		  if(columns == null){
//			  columns = new ArrayList<Column>();
//			  this.setValue(COLUMNS_KEY, columns);
//		  }
		  List<Column> columns = new ArrayList<Column>();
		  //this.setValue(COLUMNS_KEY, columns);
		  this.pageContext.setAttribute(COLUMNS_KEY, columns);
	      return Tag.EVAL_BODY_INCLUDE;
	}
	
	/** 标签结束方法 */
	public int doEndTag() throws JspTagException{
		//this.pageContext.setAttribute(COLUMNS_KEY, this.getValue(COLUMNS_KEY));
		return Tag.EVAL_PAGE;
	}
	

	/** 释放资源 */
	public void release(){
	      super.release();
	}
}
