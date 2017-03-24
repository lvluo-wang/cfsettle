
package com.upg.ucars.tools.msg.cfg.sp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spListType")
public class SpList extends AbstractList
{

    @XmlElement(name="field",required = true)
    private List<SpField> fields;
    @XmlAttribute(required = true)
    private int startLineNo;
    @XmlAttribute(required = true)
    private int cycFieldNum;
    
    public void addField(SpField field){
    	this.getFields().add(field);
    }




	public List<SpField> getFields() {
		if(fields == null)
			fields = new ArrayList<SpField>();
		return fields;
	}




	public int getStartLineNo() {
		return startLineNo;
	}




	public void setStartLineNo(int startLineNo) {
		this.startLineNo = startLineNo;
	}




	public int getCycFieldNum() {
		return cycFieldNum;
	}




	public void setCycFieldNum(int cycFieldNum) {
		this.cycFieldNum = cycFieldNum;
	}

}
