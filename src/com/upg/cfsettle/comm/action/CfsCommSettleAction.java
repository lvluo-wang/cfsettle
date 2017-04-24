package com.upg.cfsettle.comm.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.security.UserLogonInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.comm.core.CfsCommOrderRelateService;
import com.upg.cfsettle.comm.core.ICfsMyCommInfoService;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.organization.core.IOrgDeptService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.DateTimeUtil;

@SuppressWarnings("serial")
public class CfsCommSettleAction extends BaseAction {
	
	private CfsMyCommInfo searchBean;
	
	private CfsPrj cfsPrj;
	
	private Buser buser;
	
	private CfsOrgDept orgDept;
	
	@Autowired
	private ICfsMyCommInfoService myCommInfoService;
	@Autowired
	private CfsCommOrderRelateService cfsCommOrderRelateService;
	private Long sysUserId;
	private Date dateMonth;
	
	private CfsMyCommInfo commInfo;
	@Autowired
	private IPrjService prjService;
	
	private List<FiCodeItem> commStatus;
	
	private List<FiCodeItem> bankList;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrgDeptService deptService;

	private UserLogonInfo logonInfo;
	
	
	
	/**
	 * 跳转主页面
	 * @return
	 */
	public String main(){
		commStatus = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_COMM_PAY_STATUS);
		return SUCCESS;
	}
	
	/**
	 * 项目佣金
	 * @return
	 */
	public String prjComm(){
		logonInfo = SessionTool.getUserLogonInfo();
		return "prjComm";
	}
	
	/**
	 * 项目佣金List
	 * @return
	 */
	public String prjCommList(){
		List<CfsPrj> list = prjService.findPrjByCondition(cfsPrj, getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	
	/**
	 * 查询信息
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(myCommInfoService.findByCommInfoCondition(searchBean, getPg()), getPg());
	}


	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2017年3月31日 下午10:16:38
	 * @return
	 */
	public String toAdd(){
		commInfo = myCommInfoService.queryCfsMyCommInfoById(getPKId());
		buser = userService.getUserById(commInfo.getSysid());
		if(buser.getDeptId() != null){
			orgDept = deptService.getOrgDeptById(buser.getDeptId());
		}else{
			orgDept = new CfsOrgDept();
			orgDept.setDeptName("无");
		}
		bankList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_BANK_TYPE);
		return SUCCESS;
	}

	/**
	 * 佣金明细
	 * @return
     */
	public String toView(){
		commInfo = myCommInfoService.queryCfsMyCommInfoById(getPKId());
		buser = userService.getUserById(commInfo.getSysid());
		return SUCCESS;
	}

	/**
	 * 支付佣金信息
	 */
	public void doPay(){
		myCommInfoService.doPayCfsMyCommInfo(commInfo);
	}
	
	/**
	 * 新增cfsCust信息
	 */
	public void doAdd(){
		myCommInfoService.addCfsMyCommInfo(commInfo);
	}
	
	/**
	 * 查询结算今年明细
	 * @author renzl123
	 * @date 2017年4月12日 下午11:04:12
	 * @return
	 */
	public String listComm(){
		return setDatagridInputStreamData(myCommInfoService.findByCommDetail(searchBean,getPg()),getPg());
	}
	
	public void doExport() throws Exception{
    	HttpServletResponse response = getHttpResponse();
		HSSFWorkbook workbook = null;
		OutputStream os = null;
		/*searchBean.setStatus(LcsConstant.FUND_ORDER_BOOK_AUDIT);*/
		try {
			os = response.getOutputStream(); // 取得输出流
			response.reset();// 清空输出流
			String fileName = "佣金结算管理" + DateTimeUtil.getCurDate() + ".xls";
			response.setHeader("Content-disposition","attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			String title = "佣金结算管理";
			String[] headers = new String[] {"职位类型", "名称", "联系方式","佣金计提月份","佣金总额","付款时间", "状态"};
			workbook = myCommInfoService.generatSettleData(os, searchBean,headers,title,getPg());
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			os.flush();
		}
    }

	public CfsMyCommInfo getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CfsMyCommInfo searchBean) {
		this.searchBean = searchBean;
	}

	public CfsMyCommInfo getCommInfo() {
		return commInfo;
	}

	public void setCommInfo(CfsMyCommInfo commInfo) {
		this.commInfo = commInfo;
	}

	public CfsPrj getCfsPrj() {
		return cfsPrj;
	}

	public void setCfsPrj(CfsPrj cfsPrj) {
		this.cfsPrj = cfsPrj;
	}

	public List<FiCodeItem> getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(List<FiCodeItem> commStatus) {
		this.commStatus = commStatus;
	}

	public Buser getBuser() {
		return buser;
	}

	public void setBuser(Buser buser) {
		this.buser = buser;
	}
	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Date getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(Date dateMonth) {
		this.dateMonth = dateMonth;
	}

	public List<FiCodeItem> getBankList() {
		return bankList;
	}

	public void setBankList(List<FiCodeItem> bankList) {
		this.bankList = bankList;
	}

	public CfsOrgDept getOrgDept() {
		return orgDept;
	}

	public void setOrgDept(CfsOrgDept orgDept) {
		this.orgDept = orgDept;
	}

	public UserLogonInfo getLogonInfo() {
		return logonInfo;
	}

	public void setLogonInfo(UserLogonInfo logonInfo) {
		this.logonInfo = logonInfo;
	}
}