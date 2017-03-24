/*
 * 源程序名称: TestA.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator;

import java.math.BigDecimal;
import java.util.Date;

import com.upg.ucars.tools.validator.annotation.DateAddOrSub;
import com.upg.ucars.tools.validator.annotation.EnumRange;
import com.upg.ucars.tools.validator.annotation.NotNull;
import com.upg.ucars.tools.validator.constant.Operator;

public class TestA {
	@NotNull(condition="obj.inta == 1")
	public String name;
	//@Size(min="1",max="5")
	public String addr;
	
	public Date minDt;
	
	public Date maxDt;
	
	//@DateRange(min="minDt",max="maxDt",containMin=false,containMax=false)
	@DateAddOrSub(startDate="minDt",days="4",operator=Operator.lt)
	public Date curDt;
	
	//@DateRange(min="minDt",max="maxDt",containMin=false,containMax=false)
	public java.sql.Date dt2;
	
	//@Digits(precision=5,scale=2)
	public BigDecimal minBd;
	
	//@Digits(precision=5,scale=2)
	public Double doubleValue;
	
	public BigDecimal maxBd;
	
	//@NumberRange(min="minBd",max="maxBd",containMin=true,containMax=false)
	public int inta;
	
	public Long longa;
	
	public BigDecimal bd;
	
	@EnumRange(enumValue={"1","2","3"})
	public String code;
	
	//@EnumRange(enumValue={"1","2","3"})
	public int codeInt;
	
	@EnumRange(enumValue={"1","2","3"})
	public Long codeLong;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getMinDt() {
		return minDt;
	}

	public void setMinDt(Date minDt) {
		this.minDt = minDt;
	}

	public Date getMaxDt() {
		return maxDt;
	}

	public void setMaxDt(Date maxDt) {
		this.maxDt = maxDt;
	}

	public Date getCurDt() {
		return curDt;
	}

	public void setCurDt(Date curDt) {
		this.curDt = curDt;
	}

	public java.sql.Date getDt2() {
		return dt2;
	}

	public void setDt2(java.sql.Date dt2) {
		this.dt2 = dt2;
	}

	public BigDecimal getMinBd() {
		return minBd;
	}

	public void setMinBd(BigDecimal minBd) {
		this.minBd = minBd;
	}

	public BigDecimal getMaxBd() {
		return maxBd;
	}

	public void setMaxBd(BigDecimal maxBd) {
		this.maxBd = maxBd;
	}

	public int getInta() {
		return inta;
	}

	public void setInta(int inta) {
		this.inta = inta;
	}

	

	public Long getLonga() {
		return longa;
	}

	public void setLonga(Long longa) {
		this.longa = longa;
	}

	public BigDecimal getBd() {
		return bd;
	}

	public void setBd(BigDecimal bd) {
		this.bd = bd;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCodeInt() {
		return codeInt;
	}

	public void setCodeInt(int codeInt) {
		this.codeInt = codeInt;
	}

	public Long getCodeLong() {
		return codeLong;
	}

	public void setCodeLong(Long codeLong) {
		this.codeLong = codeLong;
	}
	
	

}
