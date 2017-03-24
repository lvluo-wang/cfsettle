package com.upg.ucars.tools.msg.cfg.sp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spItemType")
public class SpItem extends AbstractItem implements Comparable<SpItem>{
	@XmlElement(name = "lineNo")
	private int lineNo;
	@XmlElement(name = "sn")
	private int sn;

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public int compareTo(SpItem o) {
		if(o == null) return -1;
		if(this.getLineNo() == o.getLineNo())
			return this.sn - o.getSn();
		else
			return (this.getLineNo() - o.getLineNo());
	}
}
