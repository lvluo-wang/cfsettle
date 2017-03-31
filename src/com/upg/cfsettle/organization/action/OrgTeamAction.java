package com.upg.cfsettle.organization.action;

import java.util.List;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.organization.core.IOrgTeamService;
import com.upg.cfsettle.organization.core.OrgTeamBean;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;

/**
 * Created by zuo on 2017/3/29.
 */
public class OrgTeamAction extends BaseAction {

    private List<FiCodeItem> isActiveList;

    private ICodeItemService codeItemService;

    private OrgTeamBean searchBean;

    private CfsOrgTeam orgTeam;

    private IOrgTeamService orgTeamService;


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
}
