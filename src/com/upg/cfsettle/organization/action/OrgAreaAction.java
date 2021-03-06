package com.upg.cfsettle.organization.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.organization.core.IOrgAreaService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;

/**
 * Created by zuo on 2017/3/28.
 */
public class OrgAreaAction extends BaseAction {

    private CfsOrgArea searchBean;

    private IOrgAreaService orgAreaService;

    private List<FiCodeItem> isActiveList;

    private ICodeItemService codeItemService;

    private CfsOrgArea orgArea;
    @Autowired
    private IUserService userService;
    
    private Long oldBuserId;
    
    private Long newBuserId;


    public String main()
    {
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        return MAIN;
    }

    public String list(){
        return setDatagridInputStreamData(orgAreaService.findByCondition(searchBean, getPg()), getPg());
    }

    public String toAdd(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        return ADD;
    }

    public void doAdd(){
        orgAreaService.addOrgArea(orgArea);
    }

    public String toEdit(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        orgArea = orgAreaService.getOrgAreaById(getPKId());
        return EDIT;
    }

    public void doEdit(){
        orgAreaService.updateOrgArea(orgArea);
    }

    public String toChoose(){
        return "toChoose";
    }
    
    public String toSetBuser(){
    	searchBean = this.getSearchBean();
    	return "toSetBuser";
    }
    
    public String buserList(){
    	List<Buser> list = userService.getCanSetBuser(searchBean.getPosCode());
    	Buser buser = userService.getUserByAreaIdAndPosCode(searchBean.getId(), searchBean.getPosCode());
    	if(buser != null){
    		list.add(buser);
    	}
    	return setDatagridInputStreamData(list, getPg());
    }
    
    public void setBuser(){
    	if("1".equals(searchBean.getHavBuser())){
    		Buser buser = userService.getUserById(newBuserId);
    		buser.setAreaId(searchBean.getId());
    		if(oldBuserId != null&&!(newBuserId.equals(oldBuserId))){
    			Buser buser2 = userService.getUserById(oldBuserId);
    			buser2.setAreaId(null);
    			userService.updateUser(buser2);
    		}
    		userService.updateUser(buser);
    	}else{
    		Buser buser = userService.getUserById(oldBuserId);
    		buser.setAreaId(null);
    		userService.updateUser(buser);
    	}
    }
    
    public String getCombobox(){
    	List<CfsOrgArea> list = orgAreaService.getCombobox(searchBean);
    	return setInputStreamData(list);
    }
    
    public String getComboboxEdit(){
    	List<CfsOrgArea> list = orgAreaService.getCombobox(searchBean);
    	Buser buser = userService.getUserById(getPKId());
    	if(buser.getAreaId() != null){
    		CfsOrgArea area = orgAreaService.getOrgAreaById(buser.getAreaId());
    		if(area != null){
    			list.add(area);
    		}
    	}
    	return setInputStreamData(list);
    }
    public CfsOrgArea getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(CfsOrgArea searchBean) {
        this.searchBean = searchBean;
    }

    public IOrgAreaService getOrgAreaService() {
        return orgAreaService;
    }

    public void setOrgAreaService(IOrgAreaService orgAreaService) {
        this.orgAreaService = orgAreaService;
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

    public CfsOrgArea getOrgArea() {
        return orgArea;
    }

    public void setOrgArea(CfsOrgArea orgArea) {
        this.orgArea = orgArea;
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
