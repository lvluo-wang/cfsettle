package com.upg.demo.mapping.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.upg.ucars.framework.bpm.base.BaseBpmEntity;
import com.upg.ucars.framework.exception.ValidateException;
import com.upg.ucars.util.BeanWrapperUtils;


public class LoanInfo extends BaseBpmEntity {
	/**状态-新增**/
	public final static String STATUS_NEW = "0";
	/**状态-业务受理**/
	public final static String STATUS_ACCEPT = "1";
	/**状态-一般审核**/
	public final static String STATUS_COMMON_AUDIT = "2";
	/**状态-高级审核**/
	public final static String STATUS_ADVANCED_AUDIT = "3";
	/**状态-划款**/
	public final static String STATUS_PAYMENT_RESULT = "4";
	/**状态-结束**/
	public final static String STATUS_END = "5";

	// Fields

	private Long id;
	private String loanNo;
	private Date applyDate;
	private String userName;
	private BigDecimal money;
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toStr() {
		StringBuffer buf = new StringBuffer();
		List loanInfoProp = BeanWrapperUtils.getBeanPropertyName(this);
		Iterator it = loanInfoProp.iterator();
		while(it.hasNext()) {
			String propName = (String)it.next();
			Object value = BeanWrapperUtils.getBeanPropertyValue(this, propName);
			buf.append(propName);
			buf.append("=");
			buf.append(value);
			buf.append("\r\n");
		}
		return buf.toString();
	}
	@Override
	public String toString(){
		return "id="+id+",loanNo="+loanNo+",applyDate="+applyDate+",userName="+userName+",money="+money+",status="+status;
	}
	
	public boolean canDelete() throws ValidateException {
		return false;
	}
	
	public boolean validate() throws ValidateException {
		return false;
	}
	
}
