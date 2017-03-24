package com.upg.ucars.framework.base;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.upg.ucars.basesystem.component.core.IAttachmentService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.QueryConfigLoader;
import com.upg.ucars.framework.base.queryComponent.QueryObject;
import com.upg.ucars.framework.base.queryComponent.SortObject;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.framework.security.ISecurityManager;
import com.upg.ucars.util.JsonUtils;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.StringUtil;

public class BaseAction extends ActionSupport implements ServletRequestAware,
		SessionAware, ServletContextAware, ServletResponseAware,
		BaseActionConstant {
	private HttpServletRequest	httpRequest		= ServletActionContext
														.getRequest();

	private HttpServletResponse	httpResponse	= ServletActionContext
														.getResponse();

	private Map					session;

	private ServletContext		sc;

	/** 分页 */
	private Page				pg;
	/** 页面命令模式,在分页表单中表示,上一页,下一页,首页,未页,跳转 */
	private String				command;
	/** 流程入口对应菜单编号 */
	private String				flow_func_id;
	/** 查询组件id */
	private String				queryComponentId;
	/** 查询组件url,与组件id不能同时使用 */
	private String				queryComponentUrl;
	private String				baseDir			= ServletActionContext
														.getRequest()
														.getContextPath();
	/** 是否显示审批相关的按钮 */
	protected String			showAudit		= "0";
	/** 是否审批页面 */
	private boolean auditView;

	@Override
	public void setActionErrors(Collection<String> errorMessages) {
		// TODO Auto-generated method stub
		super.setActionErrors(errorMessages);
	}

	// 分页组件需要的参数
	private int						rows;
	private int						page;
	// 排序组件需要的参数
	private String					sort;
	private String					order;
	// 主键id串 id:id:id
	private String					ids;
	// 版本ver串 ver1:ver2:ver3
	private String					vers;
	private String					id;
	/** 审批实体id */
	private Long					auditEntityId;
	protected static final String	VIEW_AUDIT_PROCESS	= "viewAuditProcess";

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
		if (pg == null) {
			pg = new Page();
		}
		pg.setSortName(sort);
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
		if (pg == null) {
			pg = new Page();
		}
		pg.setSortOrder(order);
	}

	public List<Long> getIdList() {
		if (StringUtil.isEmpty(ids))
			return new ArrayList<Long>(0);
		ids = ids.replace(',', ':');
		String[] idarr = ids.split(":");
		ArrayList<Long> list = new ArrayList<Long>(idarr.length);
		for (String str : idarr) {
			list.add(Long.valueOf(str));
		}
		return list;
	}

	public List<Integer> getVerList() {
		if (StringUtil.isEmpty(vers))
			return new ArrayList<Integer>(0);
		vers = vers.replace(',', ':');
		String[] arr = vers.split(":");
		ArrayList<Integer> list = new ArrayList<Integer>(arr.length);
		for (String str : arr) {
			list.add(Integer.valueOf(str));
		}
		return list;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getPKId() {
		return Long.valueOf(id);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRows() {
		return rows;
	}

	public Long getAuditEntityId() {
		return auditEntityId;
	}

	public void setAuditEntityId(Long auditEntityId) {
		this.auditEntityId = auditEntityId;
	}

	public void setRows(int rows) {
		this.rows = rows;
		if (pg == null) {
			pg = new Page();
		}
		pg.setPageSize(rows);
		if (rows == Integer.MAX_VALUE) {// 表示不分页
			pg.setQueryAll(true);
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		if (pg == null) {
			pg = new Page();
		}
		pg.setCurrentPage(page);
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getQueryComponentId() {
		return queryComponentId;
	}

	public void setQueryComponentId(String queryComponentId) {
		this.queryComponentId = queryComponentId;
	}

	public String getQueryComponentUrl() {
		return queryComponentUrl;
	}

	public void setQueryComponentUrl(String queryComponentUrl) {
		this.queryComponentUrl = queryComponentUrl;
	}

	public boolean	isRefreshMyTasks	= true; // 是否刷新我的任务列表

	/**
	 * 刷新我的任务列表
	 * 
	 */
	public void refreshMyTasks() {
		this.isRefreshMyTasks = true;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;

	}

	public boolean isAuthorized(String uri) {
		return SourceTemplate.getBean(ISecurityManager.class,
				BeanNameConstants.SECURITY_MANAGER_BEAN).isAuthorized(uri);
	}

	public String getFlow_func_id() {
		return flow_func_id;
	}

	/**
	 * 如果有菜单编号,加载菜单流程对应的流程定义,显示在导航条上
	 * 
	 * @param flow_func_id
	 */
	public void setFlow_func_id(String flow_func_id) {
		if (flow_func_id != null && !"".equals(flow_func_id)) {
			String[] ids = flow_func_id.split(",");
			flow_func_id = ids[ids.length - 1];
			this.getSession().put("current_func_id", flow_func_id);
		}
		this.flow_func_id = (String) this.getSession().get("current_func_id");
	}

	public Page getPg() {
		if (pg == null) {
			pg = new Page();
			pg.setCurrentPage(1);
			pg.setPageSize(10);
		}
		return pg;
	}

	/**
	 * 按分页对象获取集合子集
	 * 
	 * @param list
	 * @param page
	 * @return
	 */
	public static final <T> List<T> getSubListByPage(List<T> list, Page page) {

		page.afterNew(list.size());

		// 无记录
		if (page.getTotalRows() < 1) {// 为空
			return Collections.emptyList();
		}

		int startIndex = (page.getCurrentPage() - 1) * page.getPageSize();
		int endIndex = startIndex + page.getPageSize();

		if (list.size() < startIndex)
			return Collections.emptyList();
		if (list.size() < endIndex)
			return list.subList(startIndex, list.size());

		return list.subList(startIndex, endIndex);
	}

	public void setPg(Page pg) {
		this.pg = pg;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest = ServletActionContext.getRequest();
	}

	public Map getSession() {
		return session;
	}

	public ServletContext getSc() {
		return sc;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse = ServletActionContext.getResponse();
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.httpRequest = arg0;
	}

	public void setSession(Map arg0) {
		this.session = arg0;
	}

	public void setServletContext(ServletContext arg0) {
		this.sc = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.httpResponse = arg0;
	}

	/**
	 * 更新当前流程操作步骤，显示在页面导航条上
	 */
	public void refreshNav() throws Exception {

	}

	// /**
	// * 用于格式化较大的double值，避免页面显示科学计数法
	// * @param d
	// * @return
	// */
	// public String formatDouble(Double d){
	// DecimalFormat format=new DecimalFormat("###0.00");
	// return format.format(d.longValue());
	// }
	//
	//
	// public void log() {
	// String url = this.getHttpRequest().getPathInfo();
	// }
	//
	// public void log(String method_name) {
	//
	// }
	/**
	 * 根据查询组件id，从请求中获取页面中的值
	 * 
	 * @param componentId
	 * @return
	 */
	public QueryComponent getQueryComponentFormRequest() {
		QueryComponent queryComponent = QueryConfigLoader
				.getQueryComponent(queryComponentId);
		if (queryComponent != null) {
			List<QueryObject> queryList = queryComponent.getQueryList();
			Iterator<QueryObject> it = queryList.iterator();
			while (it.hasNext()) {
				QueryObject query = it.next();
				String dataName = query.getQueryName();
				String operateNmae = dataName + "_op";
				String dataValue = httpRequest.getParameter(dataName);
				String selectedOperate = httpRequest.getParameter(operateNmae);
				query.setQueryOperate(selectedOperate);
				query.setQueryValue(dataValue);
			}
			String sort = httpRequest.getParameter("sort");
			if (sort != null && sort.length() > 1) {
				String[] sorts = sort.split(",");
				SortObject so = new SortObject();
				so.setSortName(sorts[0]);
				String sortType = null;
				if (sorts.length > 1) {
					sortType = sorts[1];
				} else {
					sortType = httpRequest.getParameter("order");
				}
				so.setSortType(sortType);
				queryComponent.setSort(so);
			}
		}
		if (pg == null) {
			pg = new Page();
		}
		pg.setPageSize(rows);
		pg.setCurrentPage(page);
		return queryComponent;
	}

	/**
	 * 自定义的查询组件可以使用此方法向自己定义的组件中注入前台的数据
	 * 
	 * @param queryComponent
	 * @return
	 */
	public QueryComponent buildQueryWithHttpRequest(
			QueryComponent queryComponent) {
		try {
			if (queryComponent != null) {
				List<QueryObject> queryList = queryComponent.getQueryList();
				Iterator<QueryObject> it = queryList.iterator();
				while (it.hasNext()) {
					QueryObject query = it.next();
					String dataName = query.getQueryName();
					String operateNmae = dataName + "_op";

					String dataValue = httpRequest.getParameter(dataName);

					String selectedOperate = httpRequest
							.getParameter(operateNmae);
					// 是否可以分开判断，因为有些查询可能不用查询控件，这样的话就不会有操作符参数
					// if(dataValue!=null&&selectedOperate!=null&&dataValue.length()>0&&selectedOperate.length()>0){
					if (selectedOperate != null
							&& selectedOperate.trim().length() > 0) {
						query.setQueryOperate(selectedOperate);
					}
					if (dataValue != null && dataValue.trim().length() > 0) {
						query.setQueryValue(dataValue.trim());
					}

				}
				String sort = httpRequest.getParameter("sortList");
				if (sort != null && sort.length() > 1) {
					String[] sorts = sort.split(",");
					SortObject so = new SortObject();
					so.setSortName(sorts[0]);
					so.setSortType(sorts[1]);
					queryComponent.setSort(so);
				}
			}
		} catch (Exception e) {
			ExceptionManager.throwException(SysException.class,
					ErrorCodeConst.SYS_ERROR, e);
		}
		return queryComponent;
	}

	/**
	 * Vincent add JSON数据输出
	 * 
	 * @param response
	 * @param json
	 */
	protected InputStream outJsonUTFStream(JSONObject jsonObject) {
		return changeObjctToUTFStream(jsonObject);
	}

	protected InputStream outJsonUTFStream(JSONArray jsonObject) {
		return changeObjctToUTFStream(jsonObject);
	}

	public String getVers() {
		return vers;
	}

	public void setVers(String vers) {
		this.vers = vers;
	}

	public String getShowAudit() {
		return showAudit;
	}

	public void setShowAudit(String showAudit) {
		this.showAudit = showAudit;
	}

	// ===========================================================

	/** 流数据 */
	private InputStream			dataStream;
	/** 下载的文件名 */
	private String				fileInputFileName;
	
	private String				charset				= IAttachmentService.CHARSET_UTF8;

	public InputStream getDataStream() {
		return dataStream;
	}

	public void setDataStream(InputStream dataStream) {
		this.dataStream = dataStream;
	}

	/**
	 * 
	 * 设置流数据并返回结果名称 <br/>
	 * dataStream的result已经定义在全局result里面
	 * 
	 * @param obj
	 * @return
	 * @see #DATA_STREAM
	 * 
	 */
	protected String setInputStreamData(Object obj) {
		if (obj != null) {
			setDataStream(changeObjctToUTFStream(obj));
		}
		return DATA_STREAM;
	}

	/**
	 * 
	 * 设置分页组件数据流并返回名称
	 * 
	 * @param obj
	 * @param pg
	 * @return
	 */
	protected String setDatagridInputStreamData(List<?> obj, Page pg) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put(TOTAL, pg.getTotalRows());
		map.put(PAGE, pg);
		map.put(ROWS, obj == null ? Collections.EMPTY_LIST : obj);
		return setInputStreamData(map);
	}

	/**
	 * 对象转化成UTF编码流
	 * 
	 * @param obj
	 * @return
	 */
	protected InputStream changeObjctToUTFStream(Object obj) {
		InputStream ret = null;
		if (obj != null) {
			String jsonStr = getJsonData(obj);
			if (jsonStr != null) {
				try {
					ret = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
				} catch (Exception e) {
					ExceptionManager.throwException(SysException.class,
							ErrorCodeConst.RESOURCE_FILE_PARS_ERR);
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * 设置要返回到前端的数据
	 * 
	 * @param obj
	 * @return
	 */
	protected String getResultAndSetData(Object obj) {
		httpRequest.setAttribute(XCAS_DATA, obj);
		return XCAS_DATA;
	}

	/**
	 * 
	 * 获取json格式数据
	 * 
	 * @return
	 */
	protected String getJsonData(Object obj) {
		return JsonUtils.objectToJsonString(obj);
	}
	
	public boolean isAuditView() {
		return auditView;
	}

	public void setAuditView(boolean auditView) {
		this.auditView = auditView;
	}
	
	public void setFileInputFileName(String fileInputFileName) {
		try{
			this.fileInputFileName = URLEncoder.encode(fileInputFileName, IAttachmentService.CHARSET_UTF8);;
		}catch(Exception ex){
			throw new IllegalArgumentException("encode[" + fileInputFileName + "] with '" + IAttachmentService.CHARSET_UTF8 + "' is fail!");
		}
	}

	public String getFileInputFileName() {
		return fileInputFileName;
	}

	public String getCharset() {
		return charset;
	}

	/**
	 * 获取参数值
	 * 
	 * @param name
	 * @return
	 */
	protected String getParameter(String name){
		return getHttpRequest().getParameter(name);
	}
	
	protected void addCookie(HttpServletResponse response,
			String cookieName, String cookieValue, int maxAge) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}	
	
	public static final String getCookie(HttpServletRequest request,String cookieName){
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;

		for (int i = 0; i < cookies.length; ++i) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return cookie.getValue();
		}

		return null;
	}
}
