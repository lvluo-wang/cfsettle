package com.upg.cfsettle.organization.action;

import java.util.List;

import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.organization.core.IOrgDeptService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;

/**
 * Created by zuo on 2017/3/29.
 */
public class OrgDeptAction extends BaseAction {

    private IOrgDeptService orgDeptService;

    private CfsOrgDept searchBean;

    private List<FiCodeItem> isActiveList;

    private ICodeItemService codeItemService;

    private CfsOrgDept orgDept;

    private Long areaId;
    @Autowired
    private IUserService userService;
    
    private Long oldBuserId;
    
    private Long newBuserId;

    public String main(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        return MAIN;
    }

    public String list(){
        return setDatagridInputStreamData(orgDeptService.findByCondition(searchBean,getPg()),getPg());
    }
    
    public String getCombobox(){
    	List<CfsOrgDept> list = orgDeptService.find(searchBean,null);
    	return setInputStreamData(list);
    }
    
    public String getComboboxEdit(){
    	List<CfsOrgDept> list = orgDeptService.find(searchBean,null);
    	Buser buser = userService.getUserById(getPKId());
    	if(buser.getDeptId() != null){
    		CfsOrgDept dept = orgDeptService.getOrgDeptById(buser.getDeptId());
    		if(dept != null){
    			list.add(dept);
    		}
    	}
    	return setInputStreamData(list);
    }

    
    public String toAdd(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        return ADD;
    }

    public void doAdd(){
        orgDeptService.addOrgDept(orgDept);
    }

    public String toEdit(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        orgDept = orgDeptService.getOrgDeptById(getPKId());
        return EDIT;
    }

    public void doEdit(){
        orgDeptService.updateOrgDept(orgDept);
    }


    public String toChoose(){
        return "toChoose";
    }

    public String chooseList(){
        return setDatagridInputStreamData(orgDeptService.find(searchBean,getPg()),getPg());
    }
    
    public String toSetBuser(){
    	searchBean = this.getSearchBean();
    	return "toSetBuser";
    }
    
    public String buserList(){
    	List<Buser> list = userService.getCanSetBuser(searchBean.getPosCode());
    	Buser buser = userService.getUserByDeptIdAndPosCode(searchBean.getId(), searchBean.getPosCode());
    	if(buser != null){
    		list.add(buser);
    	}
    	return setDatagridInputStreamData(list, getPg());
    }
    
    public void setBuser(){
    	if("1".equals(searchBean.getHavBuser())){
    		CfsOrgDept dept = orgDeptService.getOrgDeptById(searchBean.getId());
    		Buser buser = userService.getUserById(newBuserId);
    		buser.setDeptId(searchBean.getId());
    		buser.setAreaId(dept.getOwnedArea());
    		if(oldBuserId != null&&!(newBuserId.equals(oldBuserId))){
    			Buser buser2 = userService.getUserById(oldBuserId);
    			buser2.setDeptId(null);
    			buser2.setAreaId(null);
    			userService.updateUser(buser2);
    		}
    		userService.updateUser(buser);
    	}else{
    		Buser buser = userService.getUserById(oldBuserId);
    		buser.setDeptId(null);
    		buser.setAreaId(null);
    		userService.updateUser(buser);
    	}
    }

    public IOrgDeptService getOrgDeptService() {
        return orgDeptService;
    }

    public void setOrgDeptService(IOrgDeptService orgDeptService) {
        this.orgDeptService = orgDeptService;
    }

    public CfsOrgDept getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(CfsOrgDept searchBean) {
        this.searchBean = searchBean;
    }

    public List<FiCodeItem> getIsActiveList() {
        return isActiveList;
    }

    public void setIsActiveList(List<FiCodeItem> isActiveList) {
        this.isActiveList = isActiveList;
    }

    public ICodeItemService getCodeItemService() {
        return codeItemService;
    }

    public void setCodeItemService(ICodeItemService codeItemService) {
        this.codeItemService = codeItemService;
    }

    public CfsOrgDept getOrgDept() {
        return orgDept;
    }

    public void setOrgDept(CfsOrgDept orgDept) {
        this.orgDept = orgDept;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

	public Long getOldBuserId() {
		return oldBuserId;
	}

	public void setOldBuserId(Long oldBuserId) {
		this.oldBuserId = oldBuserId;
	}

	public Long getNewBuserId() {
		return newBuserId;
	}

	public void setNewBuserId(Long newBuserId) {
		this.newBuserId = newBuserId;
	}
}
