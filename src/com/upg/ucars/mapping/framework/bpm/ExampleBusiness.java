package com.upg.ucars.mapping.framework.bpm;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExampleBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2442220467699004620L;

	private Long				id;
	private String				customerName;
	private String				arole;
	private String				brole;
	private BigDecimal			financeAmount;
	private String				status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getArole() {
		return arole;
	}

	public void setArole(String arole) {
		this.arole = arole;
	}

	public String getBrole() {
		return brole;
	}

	public void setBrole(String brole) {
		this.brole = brole;
	}

	public BigDecimal getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(BigDecimal financeAmount) {
		this.financeAmount = financeAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
