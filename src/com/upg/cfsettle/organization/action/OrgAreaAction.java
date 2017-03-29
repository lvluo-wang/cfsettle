package com.upg.cfsettle.organization.action;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.organization.core.IOrgAreaService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;

import java.util.List;

/**
 * Created by zuo on 2017/3/28.
 *  组织结构区域管理
 */
public class OrgAreaAction extends BaseAction {

    private CfsOrgArea searchBean;

    private IOrgAreaService orgAreaService;

    private List<FiCodeItem> isActiveList;

    private ICodeItemService codeItemService;

    private CfsOrgArea orgArea;


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
}
