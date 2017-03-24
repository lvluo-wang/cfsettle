package com.upg.cfsettle.org.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.org.OrgOrganization;
import com.upg.cfsettle.mapping.org.OrgUser;
import com.upg.cfsettle.org.core.IOrgOrganizationService;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.util.PropertyTransVo;
/**
 * OrgOrganization action
 * @author renzhuolun
 * @date 2016年8月22日 下午4:06:36
 * @version <b>1.0.0</b>
 */
@SuppressWarnings("serial")
public class OrgOrganizationAction extends BaseAction {
	@Autowired
	private IOrgOrganizationService organizationService;
	
	private OrgOrganization searchBean;
	
	/**
	 * 跳转主页
	 * @author renzhuolun
	 * @date 2016年8月22日 下午4:44:33
	 * @return
	 */
	public String main(){
		return SUCCESS;
	}
	
	/**
	 * 查询
	 * @author renzhuolun
	 * @date 2016年8月22日 下午4:47:08
	 * @return
	 */
	public String list(){
		List<OrgOrganization> list = organizationService.findByCondition(searchBean,getPg());
		List<PropertyTransVo> transVoList = new ArrayList<PropertyTransVo>();
		transVoList.add(new PropertyTransVo("id", OrgUser.class, "orgId", "name", "userName"));
		transVoList.add(new PropertyTransVo("id", OrgUser.class, "orgId", "mobile", "mobile"));
		transVoList.add(new PropertyTransVo("id", OrgUser.class, "orgId", "deptName", "deptName"));
		transVoList.add(new PropertyTransVo("id", OrgUser.class, "orgId", "position", "position"));
		return setDatagridInputStreamData(DynamicPropertyTransfer.transform(list, transVoList), getPg());
	}
	
	/**
	 * 审核通过
	 * @author renzhuolun
	 * @date 2016年8月23日 下午4:53:43
	 */
	public void doPass(){
		organizationService.doPass(getIdList());
	}
	
	/**
	 * 审核不通过
	 * @author renzhuolun
	 * @date 2016年8月23日 下午4:53:58
	 */
	public void doUnPass(){
		organizationService.doUnPass(getIdList());
	}
	
	public OrgOrganization getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(OrgOrganization searchBean) {
		this.searchBean = searchBean;
	}
}
