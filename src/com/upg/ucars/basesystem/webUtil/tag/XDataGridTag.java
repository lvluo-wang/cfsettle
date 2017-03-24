package com.upg.ucars.basesystem.webUtil.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.util.JsonUtils;

/**
 * xdatagrid标签class
 * 
 * @author shentuwy
 * @date 2011-12-1
 **/
public class XDataGridTag extends TagSupport {
	private static final long serialVersionUID = 6525133592501155462L;
	private static final String LF = "\n";
	private static final String XDataGrid_DEFAULT_ID = "myXdatagrid";
	private String url = "";
	private String params = "{}";
	private String id = XDataGrid_DEFAULT_ID;
	/**Xdatagrid每页记录条数*/
	private int pagesize=15;
	/**Xdatagrid绑定表单id*/
	private String form;
	/**是否自动加载，默认自动加载*/
	private boolean autoload = true;
	/**数据区高度*/
	private int height = 22 * pagesize;
	
	private boolean autoFit = false;
	
	/**是否需要分页条*/
	private boolean pagebar = true;
	/**单选**/
	private boolean singleSelect = false;
	
	private String emptyDataMessage = "";
	
	private boolean border = false;
	
	private List<Column> colums;
	
	private StringBuffer strbuf;
	
	public XDataGridTag(){super();}
	
	/** 标签初始方法 */
	public int doStartTag() throws JspTagException {
		strbuf = null;
		strbuf = new StringBuffer();
		//this.height = 22 * this.pagesize;
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	/** 标签结束方法 */
	public int doEndTag() throws JspTagException{
		colums = (List<Column>)this.pageContext.getAttribute(ColumnsTag.COLUMNS_KEY);
		strbuf.append("<DIV id='wrap_"+this.id+"' class=\"xdatagrid");
		if (border) {
			strbuf.append(" xdatagrid-border");
		}
		strbuf.append("\" ");
		strbuf.append(" autoFit=\"").append(autoFit).append("\" ");
		strbuf.append(">").append(LF);
		//strbuf.append(" <DIV style=\"WIDTH:100%;\">").append(LF);
		writeDatagridHeader();
		writeDatagridBody();
		//strbuf.append(" </DIV>").append(LF);
		//strbuf.append(" <DIV id=\"adaptHeight_"+this.id+"\" style=\"HEIGHT:0px;\"></DIV>").append(LF);
		writeDatagridPage();
		strbuf.append("</DIV>").append(LF);
		writeScript();
		
		JspWriter out = this.pageContext.getOut();
		try {
			out.println(strbuf.toString());
			strbuf = null;
		} catch (IOException e) {
			e.printStackTrace();
			strbuf = null;
		}
		return Tag.EVAL_PAGE;
	}
	
	/** 输出xdatagrid列表的head部分 */
	private void writeDatagridHeader(){
		strbuf.append("<DIV style=\"WIDTH:100%;HEIGHT:22px;\" class=\"datagrid-header\">").append(LF);
		strbuf.append("	<TABLE class=\"xdatagrid_header_table\" id='head_"+this.id+"' border=\"0\" cellSpacing=\"0\" cellPadding=\"0\">").append(LF);
		strbuf.append("		<TBODY>").append(LF);
		strbuf.append("			<TR>").append(LF);
		if(colums != null){
			for(Column column : colums){
				strbuf.append("			  <TD>");
				if(column.isCheckbox()){
					
					if(singleSelect){
						strbuf.append("<DIV class=\"datagrid-cell-check\"><INPUT id=\"allcb_"+this.id+"\" type=\"checkbox\" disabled /></DIV>");
					}else{
						strbuf.append("<DIV class=\"datagrid-cell-check\"><INPUT id=\"allcb_"+this.id+"\" type=\"checkbox\" onclick=\""+this.id+".selectAll(this);\" /></DIV>");
					}
				
				}else{
					String title = column.getTitle();
//					if(!StringUtils.isBlank(title)){
//						String i18n = LocalizedTextUtil.findDefaultText(title, this.pageContext.getRequest().getLocale());
//						if(!StringUtils.isBlank(i18n)){
//							title = i18n;
//						}
//					}
//					TagUtils.getStack(this.pageContext);
//					 title = TextProviderHelper.getText(title, "ss", Collections.EMPTY_LIST, TagUtils.getStack(this.pageContext), false);
					title = I18nUtil.getI18nText(title, this.pageContext);
					if (column.isRownumber()) {
						title = StringUtils.isBlank(title) ? "序列" : title;
						if (column.getWidth() == 0 ){
							column.setWidth(40);
						}
						strbuf.append("<div class=\"datagrid-header-rownumber\" style=\"TEXT-ALIGN:center;WIDTH:"+column.getWidth()+ "px;\">").append(title).append("</div>");
					}else{
						strbuf.append("<DIV style=\"TEXT-ALIGN:center;WIDTH:"+column.getWidth()+"px;CURSOR:default\" class=\"datagrid-cell\">"+title+"</DIV>");
					}
				}
				strbuf.append("			  </TD>").append(LF);
			}
			//strbuf.append("			  <TD><DIV style=\"TEXT-ALIGN:left;\" class=\"datagrid-cell\"></DIV></TD>").append(LF);
		}
		strbuf.append("		    </TR>").append(LF);
		strbuf.append("		</TBODY>").append(LF);
		strbuf.append("	</TABLE>").append(LF);
		strbuf.append("</DIV>").append(LF);
	}
	
	/** 输出xdatagrid列表的数据部分 */
	private void writeDatagridBody(){
		strbuf.append("<DIV id='data_"+this.id+"' style=\"WIDTH:100%;");
		if( !autoFit ){
			strbuf.append("HEIGHT:"+this.height+"px\"");
		}else{
			strbuf.append("\"");
		}
		strbuf.append(" class=\"datagrid-body\">").append(LF);
		strbuf.append("    <TABLE class=\"xdatagrid_body_table\" border=\"0\" cellSpacing=\"0\" cellPadding=\"0\">").append(LF);
		strbuf.append("      <TBODY id='body_"+this.id+"'>").append(LF);
		strbuf.append("      </TBODY>").append(LF);
		strbuf.append("    </TABLE>").append(LF);
		//strbuf.append("  </DIV>").append(LF);
		strbuf.append("</DIV>").append(LF);
	}
	
	/** 输出xdatagrid列表的分页部分 */
	private void writeDatagridPage(){
		if(this.pagebar)
			strbuf.append("<DIV id='page_"+this.id+"' class=\"pagebar datagrid-pager pagination\">").append(LF);
		else
			strbuf.append("<DIV id='page_"+this.id+"' class=\"pagebar datagrid-pager pagination\" style='display:none;'>").append(LF);
		strbuf.append("<table class=\"xdatagrid_page_table\" border='0' cellSpacing='0' cellPadding='0'><tbody><tr>").append(LF);
		//首页
		strbuf.append("<td>").append(LF);
		strbuf.append("  <A class=\"l-btn l-btn-plain l-btn-disabled first_page_btn\" href=\"javascript:"+this.id+".reqPage('first')\">").append(LF);
		strbuf.append("	   <SPAN class=\"l-btn-left\">").append(LF);
		strbuf.append("      <SPAN class=\"l-btn-text\"><SPAN class=\"l-btn-empty pagination-first\">&nbsp;</SPAN></SPAN>");
		strbuf.append("    </SPAN>").append(LF);
		strbuf.append("  </A>").append(LF);
		strbuf.append("</td>").append(LF);
		//上一页
		strbuf.append("<td>").append(LF);
		strbuf.append("  <A class=\"l-btn l-btn-plain l-btn-disabled pre_page_btn\" href=\"javascript:"+this.id+".reqPage('pre')\">").append(LF);
		strbuf.append("    <SPAN class=\"l-btn-left\">").append(LF);
		strbuf.append("      <SPAN class=\"l-btn-text\"><SPAN class=\"l-btn-empty pagination-prev\">&nbsp;</SPAN></SPAN>").append(LF);
		strbuf.append("    </SPAN>").append(LF);
		strbuf.append("  </A>").append(LF);
		strbuf.append("</td>").append(LF);
		//分隔符
		strbuf.append("<td><div class='pagination-btn-separator'></div></td>").append(LF);
		//第几页共几页
		strbuf.append("<td><SPAN id='beforePageText_"+this.id+"' style=\"PADDING-LEFT:6px;\">第</SPAN></td>").append(LF);
		strbuf.append("<td><INPUT id='toPage_"+this.id+"' class=\"pagination-num\" value=\"0\" size=\"2\" type=\"text\" onkeydown=\""+this.id+".toPage(this);\" OnKeyUp=\"this.value=this.value.replace(/\\D/g,'')\"/></td>").append(LF);
		strbuf.append("<td><SPAN id='totalPages_"+this.id+"' style=\"PADDING-RIGHT:6px;\">页，共0页</SPAN></td>").append(LF);
		//分隔符
		strbuf.append("<td><div class='pagination-btn-separator'></div></td>").append(LF);
		//下一页
		strbuf.append("<td>").append(LF);
		strbuf.append("<A class=\"l-btn l-btn-plain l-btn-disabled next_page_btn\" href=\"javascript:"+this.id+".reqPage('next')\">").append(LF);
		strbuf.append("  <SPAN class=\"l-btn-left\">").append(LF);
		strbuf.append("		<SPAN class=\"l-btn-text\"><SPAN class=\"l-btn-empty pagination-next\">&nbsp;</SPAN></SPAN>").append(LF);
		strbuf.append("  </SPAN>").append(LF);
		strbuf.append("</A>").append(LF);
		strbuf.append("</td>").append(LF);
		//最后一页
		strbuf.append("<td>").append(LF);
		strbuf.append("<A class=\"l-btn l-btn-plain l-btn-disabled last_page_btn\" href=\"javascript:"+this.id+".reqPage('last')\">").append(LF);
		strbuf.append("  <SPAN class=\"l-btn-left\">").append(LF);
		strbuf.append("    <SPAN class=\"l-btn-text\"><SPAN class=\"l-btn-empty pagination-last\">&nbsp;</SPAN></SPAN>").append(LF);
		strbuf.append("  </SPAN>").append(LF);
		strbuf.append("</A>").append(LF);
		strbuf.append("</td>").append(LF);
		//分隔符
		strbuf.append("<td><div class='pagination-btn-separator'></div></td>").append(LF);
		//刷新
		strbuf.append("<td>").append(LF);
		strbuf.append("<A class=\"l-btn l-btn-plain refresh_btn\" href=\"javascript:"+this.id+".reqPage('refresh')\">").append(LF);
		strbuf.append("  <SPAN class=\"l-btn-left\">").append(LF);
		strbuf.append("    <SPAN class=\"l-btn-text\"><SPAN class=\"l-btn-empty pagination-load\">&nbsp;</SPAN></SPAN>").append(LF);
		strbuf.append("  </SPAN>").append(LF);
		strbuf.append("</A>").append(LF);
		strbuf.append("</td>").append(LF);
		strbuf.append("</tr></tbody></table>").append(LF);
		//显示第几到第几条,共几条记录
		strbuf.append("<div id='pageInfo_"+this.id+"' class=\"pagination-info\">当前显示0到0，共0条记录</div>").append(LF);
		strbuf.append("<div style=\"margin-top: -1px; height: 1px; clear: both; overflow: hidden;\"></div>").append(LF);
		strbuf.append("</DIV>").append(LF);
	}
	
	/** 写出相关script脚本 */
	private void writeScript(){
		String jsonColums = JsonUtils.objectToJsonString(this.colums);
		Page page = new Page();
		if(this.pagebar)
			page.setPageSize(pagesize);
		else
			page.setPageSize(Integer.MAX_VALUE);
		String jsonPage = JsonUtils.objectToJsonString(page);
		strbuf.append("<script>").append(LF);
		String path = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
		strbuf.append("var "+this.id+" = new Xdatagrid('"+this.id+"',"+"'"+path+this.url+"',"+this.params+",'"+this.isAutoload()+"','"+this.form+"',"+jsonColums+","+jsonPage+",'"+this.pagebar+"','"+this.singleSelect+"','" + this.emptyDataMessage + "');").append(LF);
		//strbuf.append("alert("+this.id+".url);").append(LF);
		strbuf.append("</script>").append(LF);
	}

	/** 释放资源  */
	public void release(){
	      super.release();
	}
	
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pageSize) {
		this.pagesize = pageSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public boolean isAutoload() {
		return autoload;
	}

	public void setAutoload(boolean autoload) {
		this.autoload = autoload;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isPagebar() {
		return pagebar;
	}

	public void setPagebar(boolean pagebar) {
		this.pagebar = pagebar;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public boolean isAutoFit() {
		return autoFit;
	}

	public void setAutoFit(boolean autoFit) {
		this.autoFit = autoFit;
	}

	public String getEmptyDataMessage() {
		return emptyDataMessage;
	}

	public void setEmptyDataMessage(String emptyDataMessage) {
		this.emptyDataMessage = emptyDataMessage;
	}
	
	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean isBorder() {
		return border;
	}
	
}
