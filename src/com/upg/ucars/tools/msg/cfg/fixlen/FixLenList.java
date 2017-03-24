
package com.upg.ucars.tools.msg.cfg.fixlen;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fixLenListType")
@XmlRootElement(name="list")
public class FixLenList extends AbstractList
{

    @XmlElement(name="field",required = true)
    private List<FixLenField> fields;
    @XmlAttribute(required = true)
    private int cycStartPos;
    @XmlAttribute(required = true)
    private int cycLen;
    
    public void addField(FixLenField field){
    	this.getFields().add(field);
    }

    /**
     * Gets the value of the cycStartPos property.
     * 
     */
    public int getCycStartPos() {
        return cycStartPos;
    }

    /**
     * Sets the value of the cycStartPos property.
     * 
     */
    public void setCycStartPos(int value) {
        this.cycStartPos = value;
    }

    /**
     * Gets the value of the cycLen property.
     * 
     */
    public int getCycLen() {
        return cycLen;
    }

    /**
     * Sets the value of the cycLen property.
     * 
     */
    public void setCycLen(int value) {
        this.cycLen = value;
    }

	public List<FixLenField> getFields() {
		if(fields == null)
			fields = new ArrayList<FixLenField>();
		return fields;
	}

}
