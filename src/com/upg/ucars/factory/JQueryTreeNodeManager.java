package com.upg.ucars.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.util.JsonUtils;

/**
 * 
 * TreeManager
 * 
 * @author shentuwy
 * 
 */

public final class JQueryTreeNodeManager {

	/**
	 * 
	 * 转化成节点
	 * 
	 * @param list
	 * @return
	 */
	public static final List<Map<String, Object>> convertListToJTNMap(List<?> list) {
		List<Map<String, Object>> ret = null;
		if (list != null) {
			ret = new ArrayList<Map<String, Object>>(list.size());
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				Object obj = it.next();
				Map<String, Object> m = getJQueryTreeMap(obj);
				if (m != null) {
					ret.add(m);
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * 转化成节点
	 * 
	 * @param list
	 * @return
	 */
	public static final List<JQueryTreeNode> convertListToJTN(List<?> list) {
		List<JQueryTreeNode> ret = null;
		if (list != null && list.size() > 0) {
			ret = new ArrayList<JQueryTreeNode>(list.size());
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				Object obj = it.next();
				JQueryTreeNode jtn = getJQueryTreeNode(obj);
				if (jtn != null) {
					ret.add(jtn);
				}
			}
		}
		return getEmptListIFNull(ret);
	}

	/**
	 * 
	 * 转化成节点
	 * 
	 * @param allList
	 * @param checkedList
	 * @return
	 */
	public static final List<JQueryTreeNode> convertToJTN(List<?> allList, List<?> checkedList) {
		return convertToJTN(allList, checkedList, false);
	}
	
	public static final List<JQueryTreeNode> convertToJTN(List<?> allList, List<?> checkedList,boolean isOrder) {
		List<JQueryTreeNode> ret = new ArrayList<JQueryTreeNode>();
		if (checkedList == null) {
			checkedList = Collections.EMPTY_LIST;
		}
		if (allList != null && allList.size() > 0) {
			Map<String, JQueryTreeNode> map = new HashMap<String, JQueryTreeNode>(allList.size());
			List<JQueryTreeNode> allJtnList = new ArrayList<JQueryTreeNode>(allList.size());
			List<String> checkedIdList = new ArrayList<String>(checkedList.size());
			for (Iterator<?> it = checkedList.iterator(); it.hasNext();) {
				Object o = it.next();
				JQueryTreeNode jtn = getJQueryTreeNode(o);
				if (jtn != null) {
					checkedIdList.add(jtn.getId());
				}
			}
			for (Iterator<?> it = allList.iterator(); it.hasNext();) {
				Object o = it.next();
				JQueryTreeNode jtn = getJQueryTreeNode(o);
				if (jtn != null) {
					processChecked(jtn, checkedIdList);
					map.put(jtn.getId(), jtn);
					allJtnList.add(jtn);
				}
			}

			for (Iterator<JQueryTreeNode> it = allJtnList.iterator(); it.hasNext();) {
				JQueryTreeNode jtn = it.next();
				if ( StringUtils.isNotBlank(jtn.getParentId()) ) {
					JQueryTreeNode parent = map.get(jtn.getParentId());
					if (parent != null) {
						parent.addChild(jtn);
					} else {
						ret.add(jtn);
					}
				} else {
					ret.add(jtn);
				}
			}
		}
		if( isOrder && ret != null && ret.size() > 0  ){
			orderJqueryTreeNode(ret);
		}
		return ret;
	}
	
	private static final void orderJqueryTreeNode(List<JQueryTreeNode> list){
		if( list != null && list.size() > 0 ){
			Collections.sort(list,JQUERY_TREE_NODE_COMPARATOR);
			for( JQueryTreeNode jtn : list ){
				List<JQueryTreeNode> childs = jtn.getChildren();
				if( childs != null && childs.size() > 0 ){
					orderJqueryTreeNode(childs);
				}
			}
		}
	}
	

	/**
	 * 
	 * 处理是否被选中
	 * 
	 * @param jtn
	 * @param list
	 */
	private static void processChecked(JQueryTreeNode jtn, List<String> list) {
		if (jtn != null && list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i);
				if (StringUtils.equals(jtn.getId(), id)) {
					list.remove(i);
					jtn.setChecked(true);
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 获取节点
	 * 
	 * @param obj
	 * @return
	 */
	private static JQueryTreeNode getJQueryTreeNode(Object obj) {
		JQueryTreeNode ret = null;
		if (obj != null) {
			if (obj instanceof ProductInfo) {
				ret = getJQueryTreeNodeByProductInfo((ProductInfo) obj);
			} else if (obj instanceof Sysfunc) {
				ret = getJQueryTreeNodeBySysfunc((Sysfunc) obj);
			} else if (obj instanceof ProductAttribute) {
				ret = getJQueryTreeNodeByAttribute((ProductAttribute) obj);
			} else if (obj instanceof Branch) {
				ret = getJQueryTreeNodeByBranch((Branch) obj);
			} else if (obj instanceof MemberInfo) {
				ret = getJQueryTreeNodeByMemberInfo((MemberInfo) obj);
			}
		}
		return ret;
	}

	private static Map<String, Object> getJQueryTreeMap(Object obj) {
		Map<String, Object> ret = null;
		if (obj != null) {
			if (obj instanceof Branch) {
				ret = getJQueryTreeMapByBranch((Branch) obj);
			}
		}
		return ret;
	}

	/**
	 * 
	 * 产品节点
	 * 
	 * @param product
	 * @return
	 */
	private static JQueryTreeNode getJQueryTreeNodeByProductInfo(ProductInfo product) {
		JQueryTreeNode ret = null;
		if (product != null) {
			ret = new JQueryTreeNode();
			ret.setId(String.valueOf(product.getId()));
			ret.setText(product.getProdName());
			ret.setTextKey(product.getProdNameKey());
			if (StringUtils.equals(ProductInfo.OTHER, product.getProdType())) {
				ret.setIconCls("icon-reload");
			}
			ret.setParentId(String.valueOf(product.getParentProdId()));
			ret.setAttribute("url", product.getProdUrl());
			ret.setAttribute("prodId", String.valueOf(product.getId()));
			ret.setAttribute("prodType", product.getProdType());
			ret.setAttribute("prodNo", product.getProdNo());
			ret.setAttribute("sortNo", String.valueOf(product.getSortNo()));
			ret.setAttribute("product", JsonUtils.objectToJsonString(product));
		}
		return ret;
	}

	/**
	 * 
	 * 权限结点
	 * 
	 * @param sysfunc
	 * @return
	 */
	private static JQueryTreeNode getJQueryTreeNodeBySysfunc(Sysfunc sysfunc) {
		JQueryTreeNode ret = null;
		if (sysfunc != null) {
			ret = new JQueryTreeNode();
			ret.setId(String.valueOf(sysfunc.getFuncId()));
			ret.setText(sysfunc.getFuncName());
			ret.setTextKey(sysfunc.getFuncNameKey());
			ret.setParentId(String.valueOf(sysfunc.getParentFuncId()));
		}
		return ret;

	}

	/**
	 * 
	 * 产品属性节点
	 * 
	 * @param ab
	 * @return
	 */
	private static JQueryTreeNode getJQueryTreeNodeByAttribute(ProductAttribute pa) {
		JQueryTreeNode ret = null;
		if (pa != null) {
			ret = new JQueryTreeNode();
			ret.setId(String.valueOf(pa.getId()));
			ret.setText(pa.getName());
		}
		return ret;
	}

	/**
	 * 
	 * 机构节点
	 * 
	 * @param branch
	 * @return
	 */
	private static JQueryTreeNode getJQueryTreeNodeByBranch(Branch branch) {
		JQueryTreeNode ret = null;
		if (branch != null) {
			ret = new JQueryTreeNode();
			ret.setId(String.valueOf(branch.getBrchId()));
			ret.setText(branch.getBrchName());
			ret.setAttribute("brchId", String.valueOf(branch.getBrchId()));
			ret.setAttribute("brchName", branch.getBrchName());
			ret.setAttribute("brchNo", branch.getBrchNo());
			ret.setAttribute("treeCode", branch.getTreeCode());
		}
		return ret;
	}

	private static JQueryTreeNode getJQueryTreeNodeByMemberInfo(MemberInfo memberInfo) {
		JQueryTreeNode ret = null;
		if (memberInfo != null) {
			ret = new JQueryTreeNode();
			ret.setId(memberInfo.getMiNo());
			ret.setText(memberInfo.getMiName());
			ret.setAttribute(MemberInfo.IS_OPEN, memberInfo.getIsOpen());
		}
		return ret;
	}

	private static Map<String, Object> getJQueryTreeMapByBranch(Branch branch) {
		Map<String, Object> ret = null;
		if (branch != null) {
			ret = new HashMap<String, Object>();
			ret.put(JQueryTreeNode.ID, branch.getBrchId());
			ret.put(JQueryTreeNode.TEXT, branch.getBrchName());
			Map<String, Object> attributes = new HashMap<String, Object>();
			
			attributes.put("brchId", String.valueOf(branch.getBrchId()));
			attributes.put("brchName", branch.getBrchName());
			attributes.put("brchNo", branch.getBrchNo());
			attributes.put("treeCode", branch.getTreeCode());
			ret.put(JQueryTreeNode.ATTRIBUTES, attributes);
		}
		return ret;
	}

	public static final Map<String, Object> getBranchRootJQueryTreeMap() {
		Branch root = new Branch();
		root.setBrchId(Long.valueOf("-1"));
		root.setBrchName("机构");
		return getJQueryTreeMapByBranch(root);
	}

	@SuppressWarnings("unchecked")
	private static List<JQueryTreeNode> getEmptListIFNull(List<JQueryTreeNode> list) {
		return list == null ? Collections.EMPTY_LIST : list;
	}
	
	private static final Comparator<JQueryTreeNode> JQUERY_TREE_NODE_COMPARATOR = new Comparator<JQueryTreeNode>() {

		public int compare(JQueryTreeNode n1, JQueryTreeNode n2) {
			if( n1 != null && n2 != null ){
				String on1 = n1.getAttribute("sortNo");
				String on2 = n2.getAttribute("sortNo");
				if( StringUtils.isNotBlank(on1) && StringUtils.isNotBlank(on2) 
						&& StringUtils.isNumeric(on1) && StringUtils.isNumeric(on2)){
					long l1 = Long.valueOf(on1);
					long l2 = Long.valueOf(on2);
					return l1 < l2 ? -1 : ( l1 > l2 ? 1 : 0);
				}
			}
			return 0;
		}
		
	};

}
