package com.upg.cfsettle.assign.action;

import com.upg.cfsettle.assign.core.IBuserService;
import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.cfsettle.organization.core.IOrgAreaService;
import com.upg.cfsettle.organization.core.IOrgDeptService;
import com.upg.cfsettle.organization.core.IOrgTeamService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;
import org.apache.cxf.Bus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuo on 2017/4/6.
 */
public class CustAssignAction extends BaseAction{


    @Autowired
    private IBuserService buserService;
    @Autowired
    private ICfsCustService custService;

    @Autowired
    private IUserService userService;
    @Autowired
    private ICodeItemService codeItemService;
    @Autowired
    private IOrgTeamService teamService;
    @Autowired
    private IOrgDeptService deptService;
    @Autowired
    private IOrgAreaService areaService;

    private Buser searchBean;

    private String loginPosCode;
    private List<CfsCust> custList;
    private Long buserId;
    //可以多个cust
    private String custIds;
    /** json字符串 */
    private String treeJSONInfo;

    private Long selectBuser;

    private Buser buser;
    private OrganizationBean organizationBean;

    public String main(){
        loginPosCode = SessionTool.getUserLogonInfo().getPosCode();
        return SUCCESS;
    }

    public String salesMain(){
        loginPosCode = SessionTool.getUserLogonInfo().getPosCode();
        return SUCCESS;
    }

    public String list(){
        return setDatagridInputStreamData(buserService.queryBuser(searchBean,getPg()),getPg());
    }

    public String  toAssign(){
         buserId = getPKId();
        return SUCCESS;
    }

    //员工下关联的客户
    public String custList(){
        Page pg = new Page();
        pg.setCurrentPage(1);
        pg.setPageSize(30);
        return setDatagridInputStreamData(custService.findAllCustByBuserId(buserId),pg);
    }

    public String toView(){
        buserId = getPKId();
        buser = userService.getUserById(buserId);
        if(buser.getTeamId() !=null){
            organizationBean = teamService.getByTeamId(buser.getTeamId());
        }
        if(buser.getTeamId()==null && buser.getDeptId() !=null){
            organizationBean = deptService.getByDeptId(buser.getDeptId());
        }
        if(buser.getTeamId()==null && buser.getDeptId() ==null
                && buser.getAreaId() !=null){
            CfsOrgArea orgArea = areaService.getOrgAreaById(buser.getAreaId());
            if(orgArea != null){
                organizationBean = new OrganizationBean();
                organizationBean.setAreaName(orgArea.getAreaName());
            }
        }
        return SUCCESS;
    }

    public String selectSale(){
        this.initSaleInfo();
        return SUCCESS;
    }

    /**
     * 分配
     */
    public void doAssign(){
        buserService.doAssignForCustIds(custIds,selectBuser);

    }

    /**
     * 初始化已分配及未分配的角色树信息。
     *
     */
    private void initSaleInfo(){
        UserLogonInfo logoner = SessionTool.getUserLogonInfo();
        List<Buser> buserList = new ArrayList<>();
        //营业部负责人所在营业部下所有员工
        if(!StringUtil.isEmpty(logoner.getPosCode()) && logoner.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)){
            Long deptId = logoner.getDeptId();
            buserList = userService.getUserByDeptId(deptId);
        }
        //区域经理所在区域下所有员工
        if(!StringUtil.isEmpty(logoner.getPosCode()) && logoner.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)){
            Long areaId = logoner.getAreaId();
            buserList = userService.getUserByAreaId(areaId);
        }
        //超级管理员分配营业部负责人及以上人员离职的客户
        if(logoner.getUserType().equals(Buser.TYPE_BRCH_GLOBAL_MANAGER)){
            //所有员工
            buserList = buserService.queryAllSale();
        }

        ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();

        for (Buser buser : buserList) {
            JQueryTreeNode node = new JQueryTreeNode();
            node.setId(buser.getUserId().toString());
            node.setText(buser.getUserName()
                    +"("+codeItemService.getCodeItemNameByKey(UtilConstant.CFS_BUSER_POS_CODE,buser.getPosCode())+")");
            node.setIconCls("icon-ok");

            nodeList.add(node);
        }
        this.setTreeJSONInfo(getJsonData(nodeList));
    }

    public Buser getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(Buser searchBean) {
        this.searchBean = searchBean;
    }

    public String getLoginPosCode() {
        return loginPosCode;
    }

    public void setLoginPosCode(String loginPosCode) {
        this.loginPosCode = loginPosCode;
    }

    public List<CfsCust> getCustList() {
        return custList;
    }

    public void setCustList(List<CfsCust> custList) {
        this.custList = custList;
    }

    public Long getBuserId() {
        return buserId;
    }

    public void setBuserId(Long buserId) {
        this.buserId = buserId;
    }


    public String getCustIds() {
        return custIds;
    }

    public void setCustIds(String custIds) {
        this.custIds = custIds;
    }

    public Long getSelectBuser() {
        return selectBuser;
    }

    public void setSelectBuser(Long selectBuser) {
        this.selectBuser = selectBuser;
    }

    public String getTreeJSONInfo() {
        return treeJSONInfo;
    }

    public void setTreeJSONInfo(String treeJSONInfo) {
        this.treeJSONInfo = treeJSONInfo;
    }

    public Buser getBuser() {
        return buser;
    }

    public void setBuser(Buser buser) {
        this.buser = buser;
    }

    public OrganizationBean getOrganizationBean() {
        return organizationBean;
    }

    public void setOrganizationBean(OrganizationBean organizationBean) {
        this.organizationBean = organizationBean;
    }
}
