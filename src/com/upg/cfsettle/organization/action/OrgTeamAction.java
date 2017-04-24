package com.upg.cfsettle.organization.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.organization.core.IOrgTeamService;
import com.upg.cfsettle.organization.core.OrgTeamBean;
import com.upg.cfsettle.util.CfsUtils;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;

/**
 * Created by zuo on 2017/3/29.
 */
public class OrgTeamAction extends BaseAction {

    private List<FiCodeItem> isActiveList;

    private ICodeItemService codeItemService;

    private OrgTeamBean searchBean;

    private CfsOrgTeam orgTeam;

    private IOrgTeamService orgTeamService;
    @Autowired
    private IUserService userService;
    
    private Long oldBuserId;
    
    private Long newBuserId;



    public String main(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        return MAIN;
    }

    public String list(){
        return setDatagridInputStreamData(orgTeamService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toAdd(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        return ADD;
    }

    public String toEdit(){
        isActiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
        orgTeam = orgTeamService.getOrgTeam(getPKId());
        return EDIT;
    }

    public void doAdd(){
        orgTeamService.addOrgTeam(orgTeam);
    }

    public void doEdit() {
        orgTeamService.updateOrgTeam(orgTeam);
    }

    public String getCombobox(){
    	List<CfsOrgTeam> list = orgTeamService.find(searchBean,null);
    	return setInputStreamData(list);
    }
    
    public String getComboboxEdit(){
    	List<CfsOrgTeam> list = orgTeamService.find(searchBean,null);
    	 Buser buser = userService.getUserById(getPKId());
    		if(buser.getTeamId() != null){
    			CfsOrgTeam team = orgTeamService.getOrgTeamById(buser.getTeamId());
    			if(team != null){
    				list.add(team);
    			}
    		}
    	return setInputStreamData(list);
    }
    
    public String toSetBuser(){
    	searchBean = this.getSearchBean();
    	return "toSetBuser";
    }
    
    public String buserList(){
    	List<Buser> list = userService.getCanSetBuser(searchBean.getPosCode());
    	Buser buser = userService.getUserByTeamIdAndPosCode(searchBean.getTeamId(), searchBean.getPosCode());
    	if(buser != null){
    		list.add(buser);
    	}
    	return setDatagridInputStreamData(list, getPg());
    }
    
    public void setBuser(){
    	if("1".equals(searchBean.getHavBuser())){
    		Buser buser = userService.getUserById(newBuserId);
    		buser.setTeamId(searchBean.getTeamId());
    		if(oldBuserId != null){
    			Buser buser2 = userService.getUserById(oldBuserId);
    			buser2.setTeamId(null);
    			userService.updateUser(buser2);
    		}
    		userService.updateUser(buser);
    	}else{
    		Buser buser = userService.getUserById(oldBuserId);
    		buser.setTeamId(null);
    		userService.updateUser(buser);
    	}
    }
    
    public String toPartBuser(){
    	searchBean = this.getSearchBean();
    	return "toPartBuser";
    }
    
    public String partBuser(){
    	List<Buser> list = userService.getCanSetBuser(searchBean.getPosCode());
    	List<Buser> beList= userService.getUserByTeamId(searchBean.getTeamId());
    	for(Buser buser :beList){
    		list.add(buser);
    	}
    	return setDatagridInputStreamData(list, getPg());
    }
    
    public void setPartBuser(){
    	if("1".equals(searchBean.getHavBuser())){
    		List<Long> newBuserIds = CfsUtils.StringToLong(searchBean.getNewBuserStr());
    		List<Long> oldBuserIds = CfsUtils.StringToLong(searchBean.getOldBuserStr());
    		for(Long id:oldBuserIds){
    			Buser buser = userService.getUserById(id);
    			buser.setTeamId(null);
    			userService.updateUser(buser);
    		}
    		
    		for(Long id:newBuserIds){
    			Buser buser = userService.getUserById(id);
    			buser.setTeamId(searchBean.getTeamId());
    			userService.updateUser(buser);
    		}
    	}else{
    		List<Long> oldBuserIds = CfsUtils.StringToLong(searchBean.getOldBuserStr());
    		for(Long id:oldBuserIds){
    			Buser buser = userService.getUserById(id);
    			buser.setTeamId(null);
    			userService.updateUser(buser);
    		}
    	}
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

    public OrgTeamBean getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(OrgTeamBean searchBean) {
        this.searchBean = searchBean;
    }

    public CfsOrgTeam getOrgTeam() {
        return orgTeam;
    }

    public void setOrgTeam(CfsOrgTeam orgTeam) {
        this.orgTeam = orgTeam;
    }

    public IOrgTeamService getOrgTeamService() {
        return orgTeamService;
    }

    public void setOrgTeamService(IOrgTeamService orgTeamService) {
        this.orgTeamService = orgTeamService;
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