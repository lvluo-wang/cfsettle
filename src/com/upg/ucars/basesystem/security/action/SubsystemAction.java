package com.upg.ucars.basesystem.security.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.subsystem.ISubsystemService;
import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Subsystem;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;

/**
 * 功能说明：子系统控制层
 * 
 * @author shentuwy
 * @date 2012-7-3
 *
 */
public class SubsystemAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4040815223687987325L;
	
	private Subsystem subsystem;
	private String funcIds;
	private String subsysIds;
	private ISubsystemService subsystemService;
	private ISysfuncService sysfuncService;
	
	/** 子系统类型 */
	private List<Code> typeList;
	
	public List<Code> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Code> typeList) {
		this.typeList = typeList;
	}
	public Subsystem getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
	}

	public ISubsystemService getSubsystemService() {
		return subsystemService;
	}
	public void setSubsystemService(ISubsystemService subsystemService) {
		this.subsystemService = subsystemService;
	}
	
	public String getFuncIds() {
		return funcIds;
	}
	public void setFuncIds(String funcIds) {
		this.funcIds = funcIds;
	}
	
	public ISysfuncService getSysfuncService() {
		return sysfuncService;
	}
	public void setSysfuncService(ISysfuncService sysfuncService) {
		this.sysfuncService = sysfuncService;
	}
	
	public String getSubsysIds() {
		return subsysIds;
	}
	public void setSubsysIds(String subsysIds) {
		this.subsysIds = subsysIds;
	}
	/*-------------------*/
	/**
	 * 子系统管理主页面
	 */
	public String mainPage(){
		return MAIN;
	}
	/**
	 * 主页面数据查询
	 * @return
	 * @throws Exception
	 */
	public String list(){
		List<Subsystem> result=null;
		if(subsystem!=null){
			List<ConditionBean>  beanList=new ArrayList<ConditionBean>();
			String name=subsystem.getSubsysName();
			if(name!=null&&name.length()>0){
				ConditionBean bean=new ConditionBean("subsysName",ConditionBean.LIKE,name.trim());
				beanList.add(bean);
				result=subsystemService.findSubsystemByCondition(beanList,getPg());
			}else{
				result=subsystemService.findAllSubsystem(getPg());
			}
		}else{
			result=subsystemService.findAllSubsystem(getPg());
		}
		return setDatagridInputStreamData(result,getPg());
	}
	
	
	private void addEditInit(){
		typeList = DictionaryUtil.getCodesByKey(CodeKey.SUB_SYSTEM_TYPE);
	}
	
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	public String toAdd(){
		addEditInit();
		return ADD;
	}
	/**
	 * 跳转到编辑页面
	 * @return
	 */
	public String toEdit(){
		addEditInit();
		if( subsystem != null && subsystem.getSubsysId() != null ){
			subsystem=subsystemService.findById(subsystem.getSubsysId());
		}
		return EDIT;
	}
	/**
	 * 保存数据
	 */
	public void save(){
		if(subsystem!=null){
			subsystem.setSubsysStatus(Subsystem.STATUS_CLOSE);
			subsystemService.createSubsystem(subsystem);
		}
		
	}
	/**
	 * 更新数据
	 */
	public void update(){
		subsystemService.mondifySubsystem(subsystem);
	}
	/**
	 * 删除数据
	 */
	public void delete(){
		subsystemService.removeById(subsystem.getSubsysId());
	}
	public void batchDelete(){
		List<Long> idList = getIdList();
		if( idList != null && idList.size() > 0 ){
			for( Long syId : idList ){
				subsystemService.removeById(syId);
			}
		}
	}
	/**
	 * 开启
	 */
	public void open(){
		subsystemService.openSubsystem(subsystem.getSubsysId());
	}
	/**
	 * 关闭
	 */
	public void close(){
		subsystemService.closeSubsystem(subsystem.getSubsysId());
	}
	/**
	 * 权限页面
	 * @return
	 */
	public String funcMain(){
		return "funcMain";
	}
	/**
	 * 
	 * 查询待分配和已分配的权限
	 *
	 * @return
	 */
	public String funcTree(){
		Map<String,Object> map=new HashMap<String,Object>();
		if( subsystem != null && subsystem.getSubsysId() != null ){
			subsystem = subsystemService.findById(subsystem.getSubsysId());
			if(subsystem != null){
				List<Sysfunc> allFuncList = null;
				if( subsystemService.isInner(subsystem) ){ //行内子系统
					allFuncList = sysfuncService.getAllInnerMenuSysfunc();
				}else{ //网银
					allFuncList = sysfuncService.getAllEBankMenuSysfunc();
				}
				List<Sysfunc> beFuncList = subsystemService.findExistingFunc(subsystem.getSubsysId());
				map=sysfuncService.getTwoTree(allFuncList, beFuncList);
			}
		}
		return setInputStreamData(map);
	}
	/**
	 * 添加权限
	 */
	public String addFunc(){
		if(funcIds!=null){
			List<Long> funcIdList = new ArrayList<Long>();
			
			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			subsystemService.addFunc(subsystem.getSubsysId(), funcIdList);
		}
		return funcTree();
	}
	/**
	 * 删除权限
	 */
	public String deleteFunc(){
		if(funcIds!=null){
			List<Long> funcIdList = new ArrayList<Long>();
			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			subsystemService.removeFunc(subsystem.getSubsysId(), funcIdList);
		}
		return funcTree();
	}
}
