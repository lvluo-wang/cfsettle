package com.upg.cfsettle.org.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.org.OrgOrganization;
import com.upg.cfsettle.util.LcsConstant;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.StringUtil;

/**
 * OrgOrganization service实现
 * @author renzhuolun
 * @date 2014年8月5日 上午10:27:27
 * @version <b>1.0.0</b>
 */
@Service
public class OrgOrganizationServiceImpl implements IOrgOrganizationService{
	
	@Autowired
	private IOrgOrganizationDao organizationDao;
	@Autowired
	private IOrgAttachService attachService;

	@Override
	public List<OrgOrganization> findByCondition(OrgOrganization searchBean, Page page) {
		String hql = "from OrgOrganization orgOrganization";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String orgName = searchBean.getOrgName();
			if (!StringUtil.isEmpty(orgName) || orgName != null) {
				condition.addCondition(new ConditionBean("orgOrganization.orgName", ConditionBean.LIKE, orgName));
			}
			Byte approvalStatus = searchBean.getApprovalStatus();
			if (approvalStatus!= null) {
				condition.addCondition(new ConditionBean("orgOrganization.approvalStatus", ConditionBean.EQUAL, approvalStatus));
			}
		}
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		OrderBean registion = new OrderBean("orgOrganization.registionTime", false);
		orderList.add(registion);
		List<OrgOrganization> list =  organizationDao.queryEntity(condition.getConditionList(),orderList, page);
		for(OrgOrganization org:list){
			org.setRegCert(attachService.getOrgAttachByOrgId(org.getId(),(short) 0)==null? null:attachService.getOrgAttachByOrgId(org.getId(),(short) 0).getId());
			org.setBusLicense(attachService.getOrgAttachByOrgId(org.getId(),(short) 1)==null? null:attachService.getOrgAttachByOrgId(org.getId(),(short) 1).getId());
			org.setTaxLicense(attachService.getOrgAttachByOrgId(org.getId(),(short) 2)==null? null:attachService.getOrgAttachByOrgId(org.getId(),(short) 2).getId());
			org.setIdCard(attachService.getOrgAttachByOrgId(org.getId(),(short) 4)==null? null:attachService.getOrgAttachByOrgId(org.getId(),(short) 4).getId());
			org.setBusCard(attachService.getOrgAttachByOrgId(org.getId(),(short) 5)==null? null:attachService.getOrgAttachByOrgId(org.getId(),(short) 5).getId());
		}
		return list;
	}

	@Override
	public void doPass(List<Long> idList) {
		for(Long id :idList){
			OrgOrganization org = this.getOrgOrganizationById(id);
			org.setApprovalStatus(LcsConstant.ORG_APPROVAL_STATUS_PASSED);
			this.update(org);
		}
	}
	
	@Override
	public void update(OrgOrganization org) {
		organizationDao.updateOrg(org);
	}

	@Override
	public void doUnPass(List<Long> idList) {
		for(Long id :idList){
			OrgOrganization org = this.getOrgOrganizationById(id);
			org.setApprovalStatus(LcsConstant.ORG_APPROVAL_STATUS_UNPASS);
			this.update(org);
		}
	}

	@Override
	public OrgOrganization getOrgOrganizationById(Long id) {
		return organizationDao.get(id);
	}
}
