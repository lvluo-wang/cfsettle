package com.upg.ucars.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * JQuery树型数据模型
 *
 * @author shentuwy
 */
public class JQueryTreeNode {
	/**展开*/
	public final static String STATE_OPEN="open";
	/**收缩*/
	public final static String STATE_CLOSED="closed";
	
	/** ID */
	private String id;
	public static final String ID = "id";
	/** 名称 */
	private String text;
	public static final String TEXT = "text";
	
	/** 国际化KEY */
	private String textKey;
	
	
	/** 图标 */
	private String iconCls;
	public static final String ICON_CLS = "iconCls";
	/**
	 * 是否勾选中
	 */
	private boolean checked;//	
	public static final String CHECKED = "checked";
	
	/**
	 * 展开或者收缩 :open,closed
	 */
	private String state;
	public static final String STATE = "state";
	
	/**
	 * 子节点列表
	 */
	private List<JQueryTreeNode> children;
	public static final String CHILDREN = "children";
	/** 节点属性 */
	private Map<String,String> attributes;
	public static final String ATTRIBUTES = "attributes";
	/** 父节点id */
	private String parentId;
	public static final String PARENT_ID = "parentId";
	
	/**
	 * 添加子节点
	 * @param node
	 */
	public void addChild(JQueryTreeNode node){
		if (children == null){
			children = new ArrayList<JQueryTreeNode>();
		}
		
		children.add(node);
	}
	
	public JQueryTreeNode getChild(String childId){
		if (children == null) return null;//没有子节点，返回 null;
		
		for (JQueryTreeNode child : children) {
			if (child.getId().equals(childId))
				return child;
		}
		return null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<JQueryTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<JQueryTreeNode> children) {
		this.children = children;
	}
	public Map<String,String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String,String> attributes) {
		this.attributes = attributes;
	}
	public void setAttribute(String key, String text){
		if (this.attributes==null){
			this.attributes=new HashMap<String,String>(3);
		}
		attributes.put(key, text);
	}
	public String getAttribute(String key){
		if (this.attributes == null)
			return null;
		return this.attributes.get(key);
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	

	public String getTextKey() {
		return textKey;
	}

	public void setTextKey(String textKey) {
		this.textKey = textKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = result + (text == null ? 0 : text.hashCode());
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
		JQueryTreeNode other = (JQueryTreeNode) obj;
		if (!StringUtils.equals(id, other.getId()) ||  !StringUtils.equals(text, other.getText()) ) {
			return false;
		}
		return true;
	}
	

}
