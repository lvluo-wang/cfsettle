package com.upg.cfsettle.cust.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.upg.ucars.mapping.basesystem.security.Buser;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserDAO;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjOrderDaoImpl extends SysBaseDao<CfsPrjOrder,Long> implements ICfsPrjOrderDao {

    @Autowired
    private ICfsPrjRepayPlanDao prjRepayPlanDao;
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IOrgTeamDao teamDao;
    @Autowired
    private IOrgDeptDao deptDao;
    @Autowired
    private IOrgAreaDao areaDao;


    @Override
    public List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page) {
        String sql = "select prj_order.*,prj.prj_name,cust.real_name,cust.mobile from cfs_prj_order prj_order " +
                " left join cfs_prj prj on prj_order.prj_id=prj.id" +
                " left join cfs_cust cust on prj_order.cust_id=cust.id" +
                " left join cfs_cust_buser_relate relate on cust.id=relate.cust_id ";
        UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
        List<Long> buserIds = new ArrayList<>();
        //下属员工的userId
        if(searchBean != null && searchBean.isByLogonInfo()){
                buserIds = getListUser(logonInfo);
        }
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null) {
            String prjName = searchBean.getPrjName();
            if (!StringUtil.isEmpty(prjName)) {
                sqlCreater.and("prj.prj_name like :prjName", "prjName", prjName, true);
            }
            String realName = searchBean.getRealName();
            if (!StringUtil.isEmpty(realName)) {
                sqlCreater.and("cust.real_name  =:realName", "realName", realName, true);
            }
            String mobile = searchBean.getMobile();
            if (!StringUtil.isEmpty(mobile)) {
                sqlCreater.and("cust.mobile  =:mobile", "mobile", mobile, true);
            }
            Byte status = searchBean.getStatus();
            if (status != null) {
                sqlCreater.and("prj_order.status =:status", "status", status, true);
            }
            String contractNo = searchBean.getContractNo();
            if (!StringUtil.isEmpty(contractNo)) {
                sqlCreater.and("prj_order.contract_no  =:contractNo", "contractNo", contractNo, true);
            }
            Date startTime = searchBean.getStartDate();
            if (null != startTime) {
                long ctimeStartLong = startTime.getTime() / 1000;
                sqlCreater.and(" prj_order.invest_time >= :ctimeStartLong", "ctimeStartLong", ctimeStartLong, true);
            }

            Date endTime = searchBean.getEndDate();
            if (null != endTime) {
                long ctimeEndLong = DateUtils.addDays(endTime, 1).getTime() / 1000;
                sqlCreater.and(" prj_order.invest_time < :ctimeEndLong", "ctimeEndLong", ctimeEndLong, true);
            }
            String serviceSysName = searchBean.getServiceSysName();
            if (!StringUtil.isEmpty(serviceSysName)) {
                sqlCreater.and("prj_order.service_sys_name  =:serviceSysName", "serviceSysName", serviceSysName, true);
            }
            String serviceSysType = searchBean.getServiceSysType();
            if (!StringUtil.isEmpty(serviceSysType)) {
                sqlCreater.and("prj_order.service_sys_type  =:serviceSysType", "serviceSysType", serviceSysType, true);
            }
        }
        if (!logonInfo.getUserType().equals(Buser.TYPE_BRCH_GLOBAL_MANAGER)) {
            boolean myselfInfo = searchBean.isMyselfInfo();
            //只能查看自己名下的客户订单
            if (myselfInfo) {
                sqlCreater.and(" relate.sys_id in (:buserIds)", "buserIds", SessionTool.getUserLogonInfo().getSysUserId(), true);
            }
            //查看下属员工和自己的客户订单
            boolean isByLogonInfo = searchBean.isByLogonInfo();
            if (isByLogonInfo && buserIds.size() > 0) {
                sqlCreater.and(" relate.sys_id in (:buserIds)", "buserIds", buserIds, true);
            }
        }
        sqlCreater.orderBy("prj_order.invest_time",true);
        List<Map<String, Object>> result = getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
        addRepayDetail(result);
        return result;
    }

    private List<Long> getListUser(UserLogonInfo logonInfo){
        List<Long> buserIds = new ArrayList<>();
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_CUST_MANAGER)){
            //客户经理
            if(logonInfo.getTeamId() != null){
                buserIds.add(logonInfo.getSysUserId());
            }
        }
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_TEAM_MANAGER)
                && logonInfo.getTeamId() != null){
            //团队长
            //获得该团的所有员工
            buserIds = userDAO.getUserIdByTeamId(logonInfo.getTeamId());
        }
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)
                && logonInfo.getDeptId() != null){
            //营业部负责人
            //获得该营业部下的所有员工
            buserIds = userDAO.getUserIdByDeptId(logonInfo.getDeptId());
        }
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)
                && logonInfo.getAreaId() != null){
            //区域经理
            //获得该营区域下的所有员工
            buserIds = userDAO.getUserIdByAreaId(logonInfo.getAreaId());
        }
        return buserIds;
    }

    private void addRepayDetail(List<Map<String, Object>> result) {
        if(result != null && result.size() >0){
            for(Map<String, Object> map : result){
                Object prjId = (Object) map.get("PRJ_ID");
                List<CfsPrjRepayPlan> notPaidRepayPlanList = prjRepayPlanDao.findByPrjIdAndStatus(Long.valueOf(prjId.toString()),Byte.valueOf("1"));
                if(notPaidRepayPlanList != null && notPaidRepayPlanList.size() >0){
                    CfsPrjRepayPlan prjRepayPlan = notPaidRepayPlanList.get(0);
                    map.put("CURRENT_PERIOD",prjRepayPlan.getRepayPeriods());
                    map.put("CURRENT_REPAY_DATE",prjRepayPlan.getRepayDate());
                }
                List<CfsPrjRepayPlan> repayPlanList = prjRepayPlanDao.findNoRaisePlanByPrjId(Long.valueOf(prjId.toString()));
                if(repayPlanList != null && repayPlanList.size() >0){
                    map.put("TOTAL_PERIOD",repayPlanList.size());
                }
            }
        }
    }

    @Override
    public CfsPrjOrder getPrjOrderByContractNo(String contractNo) {
        String hql = "from CfsPrjOrder where contractNo =? and status=3";
        List<CfsPrjOrder> list  = this.find(hql,contractNo);
        if(list != null && list.size() >0){
            return list.get(0);
        }
        return null;
    }

	@Override
	public List<CfsPrjOrder> getPrjOrdersByPrjId(Long prjId) {
		 String hql = "from CfsPrjOrder where prjId =?";
        List<CfsPrjOrder> list  = this.find(hql,prjId);
        if(list != null && list.size() >0){
            return list;
        }
        return Collections.EMPTY_LIST;
	}

    @Override
    public List<CfsPrjOrder> getPrjOrdersByPrjIdDesc(Long prjId) {
        String hql = "from CfsPrjOrder where prjId =? order by investTime desc";
        List<CfsPrjOrder> list  = this.find(hql,prjId);
        if(list != null && list.size() >0){
            return list;
        }
        return Collections.EMPTY_LIST;
    }

	@Override
	public List<CfsPrjOrder> getOKPrjOrdersByPrjId(Long prjId) {
		 String hql = "from CfsPrjOrder where prjId =? and status=4";
	        List<CfsPrjOrder> list  = this.find(hql,prjId);
	        if(list != null && list.size() >0){
	            return list;
	        }
	        return Collections.EMPTY_LIST;
	}
}
