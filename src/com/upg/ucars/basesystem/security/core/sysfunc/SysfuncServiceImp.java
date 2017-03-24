package com.upg.ucars.basesystem.security.core.sysfunc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.upg.ucars.basesystem.product.core.product.IProductSysfuncDAO;
import com.upg.ucars.basesystem.security.core.branch.IBranchDAO;
import com.upg.ucars.basesystem.security.core.branch.IBrchFuncDAO;
import com.upg.ucars.basesystem.security.core.role.IRoleFuncDAO;
import com.upg.ucars.basesystem.security.core.subsystem.ISubsysFuncDAO;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.RightsException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.framework.security.ISecurityResourceService;
import com.upg.ucars.framework.security.SecurityResources;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.SpringContextManager;


/**
 * 
 * SysfuncServiceImp.java
 * 
 * @author shentuwy
 * @date 2012-7-27
 *
 */
public class SysfuncServiceImp extends BaseService implements ISysfuncService,ISecurityResourceService {
	
	/** 树节点移动的操作类型-append */
	private static final String TREE_POINT_APPEND = "append";
	/** 树节点移动的操作类型-top */
	private static final String TREE_POINT_TOP = "top";
	/** 树节点移动的操作类型-bottom */
	private static final String TREE_POINT_BOTTOM = "bottom";
	

	/** 权限 */
	ISysfuncDAO sysfuncDAO=null;
	
	/** 结构 */
	IBranchDAO branchDAO=null;
	
	/** 子系统权限关联 */
	private ISubsysFuncDAO subsysFuncDAO;
	
	/** 角色权限关联 */
	private IRoleFuncDAO roleFuncDao;
	
	/** 机构权限关联 */
	private IBrchFuncDAO brchFuncDAO;
	
	/** 产品权限关联 */
	private IProductSysfuncDAO productSysfuncDAO;
	

	public void setSubsysFuncDAO(ISubsysFuncDAO subsysFuncDAO) {
		this.subsysFuncDAO = subsysFuncDAO;
	}

	public void setBranchDAO(IBranchDAO branchDAO) {
		this.branchDAO = branchDAO;
	}

	public void setSysfuncDAO(ISysfuncDAO sysfuncDAO) {
		this.sysfuncDAO = sysfuncDAO;
	}

	public List<Map<String, Object>> getAsyncSubMenus(UserLogonInfo logonInfo,Long parentFuncId) {
		Long funcId=Sysfunc.ROOT_FUNC_ID;
		List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
			if(parentFuncId!=null){
				funcId=parentFuncId;
			}
			List<Long> roles=logonInfo.getRoleList();
			if(roles!=null&&roles.size()>0){
				List<Sysfunc> nodes=sysfuncDAO.getSubMenuByRoleIds(roles, funcId);
				for(Sysfunc node:nodes){
					Map<String,Object> item=new HashMap<String,Object>();
					item.put(JQueryTreeNode.ID, node.getFuncId());
					item.put(JQueryTreeNode.TEXT, node.getFuncName());
					Map<String,Object> attrMap=new HashMap<String,Object>();
					attrMap.put("url", node.getUrl());
					item.put(JQueryTreeNode.ATTRIBUTES, attrMap);
					if(sysfuncDAO.hasSubMenuByRoleIds(roles, node.getFuncId())){
						item.put(JQueryTreeNode.STATE, JQueryTreeNode.STATE_CLOSED);
					}
					items.add(item);
				}
			}
		return items;
	}
	
	public void createSysfunc(Sysfunc func) {
		if(func!=null){
			String funcName=func.getFuncName();
			String funcNameKey=func.getFuncNameKey();
			Long parentFuncId=func.getParentFuncId();
//			start hehl注释 这几行代码没有必要了，在
//			Sysfunc parent=sysfuncDAO.get(parentFuncId);
//			if(FuncType.FUNC.equals(parent.getFuncType())){
//				if(FuncType.MENU.equals(func.getFuncType())){
//					ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DISABLE_ADDSUB_TYPE);
//				}
//			}else{
//				if(parent.getUrl()!=null&&parent.getUrl().length()>0){
//					if(FuncType.MENU.equals(func.getFuncType())){
//						ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DISABLE_ADDSUB_URL);
//					}
//				}
//			}//end hehl注释
			if(sysfuncDAO.hasSameFuncName(funcName,parentFuncId)){
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_SAME_FUNCNAME);
			}
			if(sysfuncDAO.hasSameFuncNameKey(funcNameKey,parentFuncId)){
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_SAME_FUNCNAME_EN);
			}
			Long no=sysfuncDAO.getMaxSortNo(parentFuncId)+1;
			func.setSortNo(no);
			sysfuncDAO.save(func);
		}
		
		
	}

	public void modifySysfunc(Sysfunc func) {
		//如果当前是编辑为功能非菜单，需校验子节点是否是菜单，如果是，提示用户不允许操作
//		if(FuncType.FUNC.equals(func.getFuncType())){
//			Long parentId = func.getFuncId();
//			Assert.notNull(parentId);
//			List<Sysfunc> childs = sysfuncDAO.findFuncByParentId(parentId);
//			if(childs != null && !childs.isEmpty()){
//				for(Sysfunc c : childs){
//					if(FuncType.MENU.equals(c.getFuncType())){
//						ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_EDIT_TYPE_ERROR);
//					}
//				}
//			}
//		}
		//校验权限名称及国际化名称是否重名
		if(sysfuncDAO.hasSameFuncName(func.getFuncName(),func.getParentFuncId(),func.getFuncId())){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_EDIT_SAME_NAME);
		}
		if(sysfuncDAO.hasSameFuncNameKey(func.getFuncNameKey(),func.getParentFuncId(),func.getFuncId())){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_EDIT_SAME_NAME);
		}
		sysfuncDAO.update(func);
	}

	public List<Sysfunc> querySysfunc(QueryComponent qc, Page page) {
		
		return sysfuncDAO.queryEntity(qc, page);
	}

	public List<Sysfunc> findSubFunc(Long funcId) {
		return sysfuncDAO.findFuncByParentId(funcId);
	}

	public List<Map<String, Object>> getAsyncSubFunc(Long parentFuncId) {
		Long funcId=null;
		List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
		if(parentFuncId!=null){
			funcId=parentFuncId;
		}
		List<Sysfunc> nodes=sysfuncDAO.findFuncByParentId(funcId);
		for(Sysfunc node:nodes){
			Map<String,Object> item=new HashMap<String,Object>();
			item.put(JQueryTreeNode.ID, node.getFuncId());
			String funcName=node.getFuncName();
			item.put(JQueryTreeNode.TEXT, funcName);
			Map<String,Object> attrMap=new HashMap<String,Object>();
			attrMap.put("url", node.getUrl());
			item.put(JQueryTreeNode.ATTRIBUTES, attrMap);
			if(sysfuncDAO.hasSubFuncByPraentId(node.getFuncId())){
				item.put(JQueryTreeNode.STATE, JQueryTreeNode.STATE_CLOSED);
			}
			items.add(item);
		}
		return items;
	}

	public boolean hasSubFunc(Long parentFuncId) {
		return sysfuncDAO.hasSubFuncByPraentId(parentFuncId);
	}

	public Sysfunc getSysfunc(Long funcId) throws ServiceException {
		return sysfuncDAO.get(funcId);
	}

	public void removeSysfunc(Long funcId) throws ServiceException {
		//检查是否有子节点
		if(sysfuncDAO.hasSubFuncByPraentId(funcId)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DEL_HASSUB);
		}
		//检查是否有角色引用
		if(roleFuncDao.hasRoleRefFunc(funcId)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DEL_USING);
		}
		//检查是否有子系统引用
		if(subsysFuncDAO.hasSubSysRefFunc(funcId)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DEL_USING);
		}
		//检查是否有机构引用
		if(brchFuncDAO.hasBrchRefFunc(funcId)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DEL_USING);
		}
		//检查是否有产品引用
		if(productSysfuncDAO.hasProductRefFunc(funcId)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DEL_USING);
		}
		sysfuncDAO.delete(funcId);
		
	}

	public void regxCanAddSubFunc(Long funcId) {
		Sysfunc func=sysfuncDAO.get(funcId);
		if(func!=null){
			String url=func.getUrl();
			String funcType=func.getFuncType();
			if(url!=null&&url.length()>0){
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DISABLE_ADDSUB_URL);
			}
			if(!FuncType.MENU.equals(funcType)){
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_DISABLE_ADDSUB_TYPE);
			}
		}
		
	}

	public void moveFunc(Long sourceFunc, Long targetFunc, String point) {
		Sysfunc source=sysfuncDAO.get(sourceFunc);
		Sysfunc target=sysfuncDAO.get(targetFunc);
		
		// 菜单节点移动规则校验
		String sourceType = source.getFuncType();
		String targetType = target.getFuncType();
		if (StringUtils.equals(TREE_POINT_APPEND, point)) {
			if (StringUtils.equals(FuncType.FUNC, sourceType)) {
				if (!StringUtils.equals(FuncType.MENU, targetType)) {
					ExceptionManager.throwException(RightsException.class,
							ErrorCodeConst.SECURITY_FUNC_SYSFUNC_MOVE_ERROR);
				}
			} else if (StringUtils.equals(FuncType.MENU, sourceType)) {
				if (!StringUtils.equals(FuncType.GROUP, targetType)) {
					ExceptionManager.throwException(RightsException.class,
							ErrorCodeConst.SECURITY_MENU_SYSFUNC_MOVE_ERROR);
				}
			} else if (StringUtils.equals(FuncType.GROUP, sourceType)) {
				if (!StringUtils.equals(FuncType.GROUP, targetType)) {
					ExceptionManager.throwException(RightsException.class,
							ErrorCodeConst.SECURITY_GROUP_SYSFUNC_MOVE_ERROR);
				}
			}
		} else if (StringUtils.equals(TREE_POINT_TOP, point) || StringUtils.equals(TREE_POINT_BOTTOM, point)) {
			if (target.getParentFuncId() == null) {
				ExceptionManager.throwException(RightsException.class,
						ErrorCodeConst.SECURITY_SYSFUNC_MOVE_TOP_ERROR);
			}
			if (StringUtils.equals(FuncType.MENU, sourceType) || StringUtils.equals(FuncType.GROUP, sourceType)) {
				if (StringUtils.equals(FuncType.FUNC, targetType)) {
					ExceptionManager.throwException(RightsException.class,
							ErrorCodeConst.SECURITY_MENU_GROUP_SYSFUNC_MOVE_ERROR);
				}
			} else if (StringUtils.equals(FuncType.FUNC, sourceType)) {
				if (!StringUtils.equals(FuncType.FUNC, targetType)) {
					ExceptionManager.throwException(RightsException.class,
							ErrorCodeConst.SECURITY_FUNC_SYSFUNC_MOVE_ERROR);
				}
			}
		}
		//==========
		
		
		Long parentFuncId=targetFunc;
		if(isChild(sourceFunc,targetFunc)){
			//如果目标节点时原节点的子节点，则不能移动
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_PARENT_NOTO_CHILD);
		}
		if(StringUtils.equals(TREE_POINT_APPEND, point)){
			String targetUrl=target.getUrl();
			if(FuncType.FUNC.equals(target.getFuncType())){
				if(FuncType.MENU.equals(source.getFuncType())){
					//如果目标节点为包含url的菜单，则不能创建子菜单
					ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_MOVE_MENU_TO_FUNC);
				}
			}
			if(targetUrl!=null&&targetUrl.length()>0){
				if(FuncType.MENU.equals(source.getFuncType())){
					//如果目标节点为包含url的菜单，则不能创建子菜单
					ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_MOVE_URL);
				}
			}
			
		}else{
			parentFuncId=target.getParentFuncId();
		}
		if( parentFuncId != null && ! parentFuncId.equals(source.getParentFuncId()) ){
			//如果不是子节点间的相互移动，需要校验同名问题
			String funcName=source.getFuncName();
			String funcNameKey=source.getFuncNameKey();
			if(sysfuncDAO.hasSameFuncName(funcName,parentFuncId)){
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_SAME_FUNCNAME);
			}
			if(sysfuncDAO.hasSameFuncNameKey(funcNameKey,parentFuncId)){
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_SAME_FUNCNAME_EN);
			}
		}
		
		


		/**
		 * 移动为目标的子节点
		 */
		if("append".equals(point)){
			if( target.getFuncId() != null && target.getFuncId().equals(source.getParentFuncId()) ){
				//如果原节点的父级节点就是目标节点，则不用移动
				return;
			}
			Long maxNo=sysfuncDAO.getMaxSortNo(targetFunc)+1;
			source.setSortNo(maxNo);
			source.setParentFuncId(target.getFuncId());
			sysfuncDAO.update(source);
		}
		/**
		 * 移动为目标的同级节点
		 */
		if("top".equals(point)){
			List<Sysfunc> list=sysfuncDAO.getButtomFunc(target);
			if(list!=null&&list.size()>0){
				List<Sysfunc> list2=new ArrayList<Sysfunc>();
				Iterator<Sysfunc> it=list.iterator();
				while(it.hasNext()){
					Sysfunc func=(Sysfunc)it.next();
					if(func.getFuncId().equals(source.getFuncId())){
						continue;
					}
					Long sortNo=func.getSortNo()+1;
					func.setSortNo(sortNo);
					list2.add(func);
				}
				Long no=target.getSortNo();
				source.setSortNo(no);
				source.setParentFuncId(target.getParentFuncId());
				target.setSortNo(no+1);
				list2.add(source);
				list2.add(target);
				sysfuncDAO.saveOrUpdateAll(list2);
				return;
			}else{
				Long no=target.getSortNo();
				source.setSortNo(no);
				source.setParentFuncId(target.getParentFuncId());
				target.setSortNo(no+1);
				list=new ArrayList<Sysfunc>();
				list.add(target);
				list.add(source);
				sysfuncDAO.saveOrUpdateAll(list);
				return;
			}
		}
		if("bottom".equals(point)){
			List<Sysfunc> list=sysfuncDAO.getButtomFunc(target);
			if(list!=null&&list.size()>0){
				List<Sysfunc> list2=new ArrayList<Sysfunc>();
				Iterator<Sysfunc> it=list.iterator();
				while(it.hasNext()){
					Sysfunc func=it.next();
					if(func.getFuncId().equals(source.getFuncId())){
						continue;
					}
					Long sortNo=func.getSortNo()+1;
					func.setSortNo(sortNo);
					list2.add(func);
				}
				Long no=target.getSortNo();
				source.setSortNo(no+1);
				source.setParentFuncId(target.getParentFuncId());
				list2.add(source);
				sysfuncDAO.saveOrUpdateAll(list2);
				return;
			}else{
				Long no=target.getSortNo();
				source.setSortNo(no+1);
				source.setParentFuncId(target.getParentFuncId());
				list=new ArrayList<Sysfunc>();
				list.add(source);
				sysfuncDAO.saveOrUpdateAll(list);
				return;
			}
		}

	}
	/**
	 * 判断target是否为source的子孙节点
	 * @param sourceId
	 * @param targetId
	 * @return
	 */
	public boolean isChild(Long sourceId,Long targetId){
		Sysfunc target=sysfuncDAO.get(targetId);
		Long parentId=target.getParentFuncId();
		if(sourceId.equals(parentId)){
			return true;
		}
		if(parentId==null){
			return false;
		}
		return isChild(sourceId,parentId);
	}


	public List<Sysfunc> getSubMenu(UserLogonInfo logonInfo, Long funcId) {
		List<Sysfunc> nodes=null;
		List<Long> roles=logonInfo.getRoleList();
		if(roles!=null&&roles.size()>0){
			nodes=sysfuncDAO.getSubMenuByRoleIds(roles, funcId);
		}
		Long brchId=logonInfo.getBranchId();
		if(brchId!=null){
			List<Sysfunc> funcs=branchDAO.getAllredCheckedFunctions(brchId);
			nodes = filterSubMenu(funcs,nodes);
		}
		
		List<SysfuncFilter> filters = SpringContextManager.getBeanOfType(SysfuncFilter.class);
		if (filters != null && filters.size() > 0) {
			Collections.sort(filters, new Comparator<SysfuncFilter>() {
				@Override
				public int compare(SysfuncFilter sf1, SysfuncFilter sf2) {
					int result = 0;
					int order1 = sf1.getOrder();
					int order2 = sf2.getOrder();
					if (order1 > order2) {
						result = 1;
					} else if (order1 < order2) {
						result = -1;
					}
					return result;
				}
				
			});
			for (SysfuncFilter filter : filters) {
				filter.doFilter(nodes);
			}
		}
		
		return nodes == null ? new ArrayList<Sysfunc>(0) : nodes;
	}
	
	
	
	public List<Sysfunc> getMenu(UserLogonInfo logonInfo){
		List<Long> roles=logonInfo.getRoleList();
		List<Sysfunc> nodes=null;
		if(roles!=null&&roles.size()>0){
			nodes=sysfuncDAO.getMenuByRoleIdList(roles);
		}
		Long brchId=logonInfo.getBranchId();
		if(brchId!=null){
			List<Sysfunc> funcs=branchDAO.getAllredCheckedFunctions(brchId);
			return filterSubMenu(funcs,nodes);
		}
		return nodes;
	}
	/**
	 * 用户通过角色分配得到的权限，在使用时需要通过他当前机构的权限进行过滤
	 * @param brchFuncs 机构权限
	 * @param nodes 角色权限
	 * @return 最终的权限
	 */
	private List<Sysfunc> filterSubMenu(List<Sysfunc> brchFuncs,List<Sysfunc> nodes){
		List<Sysfunc> result=new ArrayList<Sysfunc>();
		if(nodes!=null){
			for(int i=0;i<nodes.size();i++){
				Sysfunc nFunc=nodes.get(i);
				if(brchFuncs!=null){
					if(brchFuncs.contains(nFunc)){
						result.add(nFunc);
					}
				}
				
			}
		}
		
		return result;
	}
	public List<Sysfunc> getSubFunc(Long funcId) {
		List<Sysfunc> nodes=sysfuncDAO.findFuncByParentId(funcId);
		return nodes;
	}

	public boolean hasSubMenu(UserLogonInfo logonInfo, Long funcId) {
		List<Long> roles=logonInfo.getRoleList();
		if(roles!=null&&roles.size()>0){
			return sysfuncDAO.hasSubMenuByRoleIds(roles,funcId);
		}
		return false;
	}

	public List<Sysfunc> getUserFunc(UserLogonInfo logonInfo) {
		List<Long> roles=logonInfo.getRoleList();
		if(roles!=null&&roles.size()>0){
			return sysfuncDAO.getSyfuncByRoleIdList(roles);
		}
		return null;
	}

	public boolean isAuthorized(String uri) {
		//未登录用户不能访问
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		if(logonInfo==null){
			return false;
		}
		//未绑定角色用户不能访问
		List<Long> roles=logonInfo.getRoleList();
		if(roles==null||roles.size()<1){
			return false;
		}
		Set<Long> ids=SecurityResources.getNeededRoles(uri);
		//不需要权限控制的请求放行
		if(ids==null||ids.size()<1){
			return true;
		}
		//用户拥有的角色满足权限要求则放行
		Iterator<Long> itr=roles.iterator();
		while(itr.hasNext()){
			Long roleId=itr.next();
			if(ids.contains(roleId)){
				return true;
			}
		}
		//以上都不满足这拒绝访问
		return false;
	}

	public Map<String, Set<Long>> getAllSecurityResource() {
		Map<String,Set<Long>> map=new HashMap<String,Set<Long>>();
		List<Sysfunc>  list=sysfuncDAO.findAllURLFunc();
		if(list!=null){
			Iterator<Sysfunc>  it=list.iterator();
			while(it.hasNext()){
				Sysfunc func=it.next();
				String url=func.getUrl();
				map.put(url, new HashSet<Long>());
			}
		}
		
		List<Map<String,Object>> rfs=sysfuncDAO.findCheckedRoleWithFunc();
		for(int i=0;i<rfs.size();i++){
			Map<String,Object> rfMap=rfs.get(i);
			String url=(String)rfMap.get("URL");
			Set<Long> l=map.get(url);
			if(l==null){
				l=new HashSet<Long>();
				map.put(url, l);
			}
			
			if(rfMap.get("ROLE_ID")==null){
				l.add(Long.valueOf(rfMap.get("role_id").toString()));
			}else{
				l.add(Long.valueOf(rfMap.get("ROLE_ID").toString()));
			}
		}
		
		return map;
	}
	public Map<String,Object> getTwoTree(List<Sysfunc> allFunc,List<Sysfunc> beFunc){
		HashMap<String,Object> map=new HashMap<String,Object>();
		Map<Long,Sysfunc> allFuncMap=new HashMap<Long,Sysfunc>();
		Map<Long,Sysfunc> beFuncMap=new HashMap<Long,Sysfunc>();
		List<JQueryTreeNode> allNodeList=new ArrayList<JQueryTreeNode>();
		List<JQueryTreeNode> beNodeList=new ArrayList<JQueryTreeNode>();
		if(beFunc!=null){
			//构建内存数据库
			for(Sysfunc func:beFunc){
				Long id=func.getFuncId();
				beFuncMap.put(id, func);
			}
			beNodeList=Sysfunc.buildCloseTree(beFunc);
		}
		if(allFunc!=null){
			//构建内存数据库
			for(Sysfunc func:allFunc){
				Long id=func.getFuncId();
				allFuncMap.put(id, func);
			}
			//构建树
			for(Sysfunc func:allFunc){
				Long pId=func.getParentFuncId();
				if(pId==null||!allFuncMap.containsKey(pId)){
					//找到一个根节点
					Long funcId=func.getFuncId();
					if(beFuncMap.containsKey(funcId)){
						//当前节点已分配,判断当前节点的所有子节点是否已分配
						List<Sysfunc> result=new ArrayList<Sysfunc>();
						boolean flag=regxChildNode(func,allFunc,result,beFuncMap);
						if(!flag){
							allNodeList.add(Sysfunc.buildCloseTreeNode(func, result));
						}
					}else{
						allNodeList.add(Sysfunc.buildCloseTreeNode(func, allFunc));
					}
				}
			}
		}
		for( JQueryTreeNode jtn : allNodeList ){
			jtn.setState(JQueryTreeNode.STATE_OPEN);
		}
		map.put("beList", beNodeList);
		map.put("unList", allNodeList);
		return map;
	}
	private boolean regxChildNode(Sysfunc func,List<Sysfunc> allFunc,List<Sysfunc> result,Map<Long,Sysfunc> beFuncMap){
		Long funcId=func.getFuncId();
		if(beFuncMap.containsKey(funcId)){
			//已包含
			boolean flag=true;
			int count=0;
			for(Sysfunc f:allFunc){
				if(funcId.equals(f.getParentFuncId())){
					//存在子节点
					flag= regxChildNode(f,allFunc,result,beFuncMap);
					if(!flag){
						//有一个子节点不包含,则跳出
						count++;
					}
				}
			}
			if(count>0){
				//如果当前节点的子节点中存在不包含项,则当前节点需要记录
				result.add(func);
				flag=false;
			}
			return flag;
		}else{
			//如果当前节点不包含,则需要记录
			for(Sysfunc f:allFunc){
				if(funcId.equals(f.getParentFuncId())){
					regxChildNode(f,allFunc,result,beFuncMap);
				}
			}
			result.add(func);
			return false;
		}
	}

	public PathMatcher getPathMatcher() {
		return new AntPathMatcher();
	}

	public List<Sysfunc> getAllEBankMenuSysfunc() throws ServiceException,DAOException {
		List<String> useTypeList = new ArrayList<String>(1);
		useTypeList.add(Sysfunc.USE_TYPE_WB);
		return getMenuFuncs(useTypeList);
//		return getMenuSysfuncByUseType(useTypeList);
	}
	/**
	 * 
	 * 根据使用类型和权限类型获取对应的所有菜单和功能，包括父节点
	 * @param useTypes 使用类型
	 * @param funTypes 权限类型
	 * @return
	 */
	private List<Sysfunc> getMenuFuncs(List<String> useTypes){
		List<Sysfunc> parentNodes = new ArrayList<Sysfunc>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put(Sysfunc.FUNC_TYPE, Sysfunc.FUNC_TYPE_MENU_GROUP);
		List<Sysfunc> menuGroups = sysfuncDAO.getSysfuncList(param);
		Map<Long,Sysfunc> menuGroupsMap = new HashMap<Long,Sysfunc>();
		for(Sysfunc sysfunc : menuGroups){
			menuGroupsMap.put(sysfunc.getFuncId(), sysfunc);
		}
		List<String> funTypes = new ArrayList<String>();
		funTypes.add(Sysfunc.FUNC_TYPE_FUNC);
		funTypes.add(Sysfunc.FUNC_TYPE_MENU);
		List<Sysfunc> menuFuncs = sysfuncDAO.querySysfuncs(useTypes, funTypes);
		Map<Long,Sysfunc> menuFuncsMap = new HashMap<Long,Sysfunc>();
		for(Sysfunc sysfunc : menuFuncs){
			menuFuncsMap.put(sysfunc.getFuncId(), sysfunc);
		}
		
		for(Sysfunc sysfunc : menuFuncs){
			if(menuFuncsMap.containsKey(sysfunc.getParentFuncId()))
				continue;
			else{
				addParentSysfunc(sysfunc,menuGroupsMap,parentNodes);
			}
		}
		if(!parentNodes.isEmpty())
			menuFuncs.addAll(parentNodes);
		return menuFuncs;
	}
	
	/**
	 * 
	 * 增加父权限
	 * @param func 需查找父节点的Sysfunc
	 * @param selectedMap 已查找的Sysfunc Map
	 * @param map 一般指公共的Sysfunc Map
	 * @param list 查到的父节点list
	 */
	private void addParentSysfunc(Sysfunc func,Map<Long,Sysfunc> menuGroups,List<Sysfunc> list){
		if( func != null && menuGroups != null && menuGroups.size() > 0 && list != null ){
			Sysfunc sf = menuGroups.get(func.getParentFuncId());
			if( sf != null && ! list.contains(sf) ){
				list.add(sf);
				addParentSysfunc(sf, menuGroups, list);
			}
		}
	}
	
//	/**
//	 * 
//	 * 获取所有的菜单和功能权限
//	 * @return
//	 * @throws ServiceException
//	 * @throws DAOException
//	 */
//	private List<Sysfunc> getAllMenuAndFun(List<String> useTypeList) throws ServiceException,DAOException{
//		List<Sysfunc> ret = new ArrayList<Sysfunc>();
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put(Sysfunc.FUNC_TYPE, Sysfunc.FUNC_TYPE_MENU);
//		List<Sysfunc> list_tmp = sysfuncDAO.getSysfuncList(param);
//		if(list_tmp != null && !list_tmp.isEmpty())
//			ret.addAll(list_tmp);
//		param.clear();
//		param.put(Sysfunc.FUNC_TYPE, Sysfunc.FUNC_TYPE_FUNC);
//		list_tmp = sysfuncDAO.getSysfuncList(param);
//		if(list_tmp != null && !list_tmp.isEmpty())
//			ret.addAll(list_tmp);
//		return ret == null ? new ArrayList<Sysfunc>(0):ret;
//	}
	
	
//	/**
//	 * 
//	 * 增加父权限
//	 *
//	 * @param func
//	 * @param map
//	 * @param list
//	 */
//	private void processAddSysfunc(Sysfunc func,Map<Long,Sysfunc> map,List<Sysfunc> list){
//		if( func != null && map != null && map.size() > 0 && list != null ){
//			Sysfunc sf = map.get(func.getParentFuncId());
//			if( sf != null && ! list.contains(sf) ){
//				list.add(sf);
//				processAddSysfunc(sf, map, list);
//			}
//		}
//	}

	public List<Sysfunc> getAllInnerMenuSysfunc() throws ServiceException,DAOException {
		List<String> useTypeList = new ArrayList<String>(2);
//		useTypeList.add(Sysfunc.USE_TYPE_APP);
//		useTypeList.add(Sysfunc.USE_TYPE_SAAS);
		useTypeList.add(Sysfunc.USE_TYPE_BRCH);
		useTypeList.add(Sysfunc.USE_TYPE_BUSI);
		return getMenuFuncs(useTypeList);
//		return getMenuSysfuncByUseType(useTypeList);
	}

	public IRoleFuncDAO getRoleFuncDao() {
		return roleFuncDao;
	}

	public void setRoleFuncDao(IRoleFuncDAO roleFuncDao) {
		this.roleFuncDao = roleFuncDao;
	}

	public IBrchFuncDAO getBrchFuncDAO() {
		return brchFuncDAO;
	}

	public void setBrchFuncDAO(IBrchFuncDAO brchFuncDAO) {
		this.brchFuncDAO = brchFuncDAO;
	}

	public IProductSysfuncDAO getProductSysfuncDAO() {
		return productSysfuncDAO;
	}

	public void setProductSysfuncDAO(IProductSysfuncDAO productSysfuncDAO) {
		this.productSysfuncDAO = productSysfuncDAO;
	}

	public ISysfuncDAO getSysfuncDAO() {
		return sysfuncDAO;
	}

	public IBranchDAO getBranchDAO() {
		return branchDAO;
	}

	public ISubsysFuncDAO getSubsysFuncDAO() {
		return subsysFuncDAO;
	}

	
	public boolean hasResourcePermission(List<Long> roleIds, String resourceId) {
		boolean ret = false;
		if (roleIds != null && StringUtils.isNotBlank(resourceId)) {
			ret = sysfuncDAO.hasResourcePermission(roleIds, resourceId);
		}
		return ret;
	}

	
}
