package com.upg.ucars.mapping.basesystem.acctrecord;

import org.apache.commons.beanutils.MethodUtils;

/**
 * AcctRecordInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctRecordInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long flowId;
	private String groupNo;
	private String rowNo;
	private String item1;
	private String item2;
	private String item3;
	private String item4;
	private String item5;
	private String item6;
	private String item7;
	private String item8;
	private String item9;
	private String item10;
	private String item11;
	private String item12;
	private String item13;
	private String item14;
	private String item15;

	// Constructors

	/** default constructor */
	public AcctRecordInfo() {
	}

	/** full constructor */
	public AcctRecordInfo(Long flowId, String groupNo, String rowNo,
			String item1, String item2, String item3, String item4,
			String item5, String item6, String item7, String item8,
			String item9, String item10, String item11, String item12,
			String item13, String item14, String item15) {
		this.flowId = flowId;
		this.groupNo = groupNo;
		this.rowNo = rowNo;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		this.item5 = item5;
		this.item6 = item6;
		this.item7 = item7;
		this.item8 = item8;
		this.item9 = item9;
		this.item10 = item10;
		this.item11 = item11;
		this.item12 = item12;
		this.item13 = item13;
		this.item14 = item14;
		this.item15 = item15;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFlowId() {
		return this.flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getRowNo() {
		return this.rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getItem1() {
		return this.item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return this.item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return this.item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return this.item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getItem5() {
		return this.item5;
	}

	public void setItem5(String item5) {
		this.item5 = item5;
	}

	public String getItem6() {
		return this.item6;
	}

	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public String getItem7() {
		return this.item7;
	}

	public void setItem7(String item7) {
		this.item7 = item7;
	}

	public String getItem8() {
		return this.item8;
	}

	public void setItem8(String item8) {
		this.item8 = item8;
	}

	public String getItem9() {
		return this.item9;
	}

	public void setItem9(String item9) {
		this.item9 = item9;
	}

	public String getItem10() {
		return this.item10;
	}

	public void setItem10(String item10) {
		this.item10 = item10;
	}

	public String getItem11() {
		return this.item11;
	}

	public void setItem11(String item11) {
		this.item11 = item11;
	}

	public String getItem12() {
		return this.item12;
	}

	public void setItem12(String item12) {
		this.item12 = item12;
	}

	public String getItem13() {
		return this.item13;
	}

	public void setItem13(String item13) {
		this.item13 = item13;
	}

	public String getItem14() {
		return this.item14;
	}

	public void setItem14(String item14) {
		this.item14 = item14;
	}

	public String getItem15() {
		return this.item15;
	}

	public void setItem15(String item15) {
		this.item15 = item15;
	}
	
	public void setItemValue(int serialNo, String value){
		try {
			MethodUtils.invokeMethod(this,		"setItem"+serialNo,		 value);//按交易项的序号设置在相应的字段上
		} catch (Exception e) {
			//TODO
		}
	}
	public String getItemValue(int serialNo){
		try {
			Object o = MethodUtils.invokeMethod(this,	"getItem"+serialNo, null);//按交易项的序号设置在相应的字段上
			if (o!=null)
				return o.toString();
			
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public AcctRecordInfo clone() {
		AcctRecordInfo o = null;
		o = new AcctRecordInfo(flowId, groupNo, rowNo,
				item1, item2, item3, item4,
				item5, item6, item7, item8,
				item9, item10, item11, item12,
				item13, item14, item15);
		return o;
	}
	
	

}