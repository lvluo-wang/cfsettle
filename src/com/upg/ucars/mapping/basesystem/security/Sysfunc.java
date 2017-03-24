package com.upg.ucars.mapping.basesystem.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.upg.ucars.model.JQueryTreeNode;


/**
 * 权限实体
 * 
 * @author shentuwy
 * @date 2011-2-13 下午06:43:30
 *
 */
public class Sysfunc implements java.io.Serializable {
	//权限根节点id
	public static final Long ROOT_FUNC_ID=1L;
	
	private static final long serialVersionUID = 595044252453403010L;
	/** 实施 */
	public static final String USE_TYPE_APP="1";
	/** saas */
	public static final String USE_TYPE_SAAS="2";
	/** 机构 */
	public static final String USE_TYPE_BRCH="3";
	/** 业务 */
	public static final String USE_TYPE_BUSI="4";
	/** 网银 */
	public static final String USE_TYPE_WB="5";
	
	public static final String CODE_FUNC_TYPE="B005";
	public static final String CODE_USE_TYPE="B008";
	
	/**
	 * 菜单组
	 */
	public static final String FUNC_TYPE_MENU_GROUP="0";
	/**
	 * 菜单
	 */
	public static final String FUNC_TYPE_MENU="1";
	/**
	 * 功能
	 */
	public static final String FUNC_TYPE_FUNC="2";
	/** ID */
	private Long funcId;
	/** 名称 */
	private String funcName;
	/** 国际化key */
	private String funcNameKey;
	/** URL */
	private String url;
	/** 权限类型 0,菜单组 1,菜单；2，功能*/
	private String funcType;
	public static final String FUNC_TYPE = "funcType";
	
	/** 父ID */
	private Long parentFuncId;
	/** 备注 */
	private String remark;
	/** 版本号 */
	private Long version;
	/** 顺序 */
	private Long sortNo;
	/** 使用类型  1、实施端，2、saas端，3、机构端，4、业务端，5、网银端
	 * 
	 */
	private String useType;
	
	
	public Sysfunc(){};

	public Sysfunc(Long funcId, String funcName, String funcNameKey,
			String url, String funcType, Long parentFuncId,
			String remark) {
		super();
		this.funcId = funcId;
		this.funcName = funcName;
		this.funcNameKey = funcNameKey;
		this.url = url;
		this.funcType = funcType;
		this.parentFuncId = parentFuncId;
		this.remark = remark;
	}

	public Sysfunc(Long funcId, String funcName, String funcNameKey, String url) {
		super();
		this.funcId = funcId;
		this.funcName = funcName;
		this.funcNameKey = funcNameKey;
		this.url = url;
	}


	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getFuncNameKey() {
		return funcNameKey;
	}
	public void setFuncNameKey(String funcNameKey) {
		this.funcNameKey = funcNameKey;
	}
	public String getFuncType() {
		return funcType;
	}
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
	public Long getParentFuncId() {
		return parentFuncId;
	}
	public void setParentFuncId(Long parentFuncId) {
		this.parentFuncId = parentFuncId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}
	public static List<JQueryTreeNode> buildOpenTree(List<Sysfunc> funcList){
		return buildTree(funcList,JQueryTreeNode.STATE_OPEN);
	}
	
	private static List<JQueryTreeNode> buildTree(List<Sysfunc> funcList,String state){
		List<JQueryTreeNode> nodeList=new ArrayList<JQueryTreeNode>();
		if(funcList!=null){
			Map<Long,JQueryTreeNode> jtnMap = getJQueryTreeNodeMap(funcList, state);
			for( Iterator<Map.Entry<Long, JQueryTreeNode>> it = jtnMap.entrySet().iterator(); it.hasNext(); ){
				JQueryTreeNode jtn = it.next().getValue();
				if( jtn.getParentId() == null ){
					jtn.setState(JQueryTreeNode.STATE_OPEN);
					nodeList.add(jtn);
				}
			}
		}
		return nodeList;
	}
	
	private static Map<Long,JQueryTreeNode> getJQueryTreeNodeMap(List<Sysfunc> funcList,String state){
		Map<Long,JQueryTreeNode> jtnMap = new HashMap<Long,JQueryTreeNode>(funcList.size());
		for(Iterator<Sysfunc> it = funcList.iterator(); it.hasNext();){
			Sysfunc func = it.next();
			JQueryTreeNode jtn = getJQueryTreeNodeBySysfunc(func,state);
			jtnMap.put(func.getFuncId(), jtn);
		}
		for(Iterator<Sysfunc> it = funcList.iterator(); it.hasNext();){
			Sysfunc func = it.next();
			Long parentId = func.getParentFuncId();
			if( parentId != null ){
				JQueryTreeNode childJtn = jtnMap.get(func.getFuncId()); 
				JQueryTreeNode parentJtn = jtnMap.get(parentId);
				if( parentJtn != null ){
					parentJtn.addChild(childJtn);
					childJtn.setParentId(parentJtn.getId());
				}
			}
		}
		return jtnMap;
	}
	
	private static JQueryTreeNode getJQueryTreeNodeBySysfunc(Sysfunc sysfunc,String state){
		JQueryTreeNode jtn = new JQueryTreeNode();
		jtn.setId(String.valueOf(sysfunc.getFuncId()));
		jtn.setText(sysfunc.getFuncName());
		String url=sysfunc.getUrl();
		if(url!=null){
			jtn.setAttribute("url", url);
		}else{			
			jtn.setState(state == null ? JQueryTreeNode.STATE_CLOSED : state);
		}
		return jtn;
	}
	
	public static List<JQueryTreeNode> buildCloseTree(List<Sysfunc> funcList){
		return buildTree(funcList,JQueryTreeNode.STATE_CLOSED);
	}
	public static JQueryTreeNode buildCloseTreeNode(Sysfunc node,List<Sysfunc> funcList){
		List<Sysfunc> tmp = null;
		if( funcList.contains(node) ){
			tmp = funcList;
		}else{
			tmp = new ArrayList<Sysfunc>(funcList.size() + 1);
			tmp.add(node);
			tmp.addAll(funcList);
		}
		Map<Long,JQueryTreeNode> jtnMap = getJQueryTreeNodeMap(tmp, JQueryTreeNode.STATE_CLOSED);
		return jtnMap.get(node.getFuncId());
	}
	
	public boolean isMenu(){
		if(FUNC_TYPE_MENU.equals(this.getFuncType()))
			return true;
		else
			return false;
	}
	
	public boolean isFunc(){
		if(FUNC_TYPE_FUNC.equals(this.getFuncType()))
			return true;
		else
			return false;
	}
	
	public boolean isMenuGroup(){
		if(FUNC_TYPE_MENU_GROUP.equals(this.getFuncType()))
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sysfunc other = (Sysfunc) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		return true;
	}
	
}