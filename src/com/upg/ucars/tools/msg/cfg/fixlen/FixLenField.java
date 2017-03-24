
package com.upg.ucars.tools.msg.cfg.fixlen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractField;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fixLenFieldType", propOrder = {
    "sn",
    "len",
    "padding"
})
@XmlRootElement(name="field")
public class FixLenField extends AbstractField implements Comparable<FixLenField>{

	private int sn;
    protected int len;
    @XmlElement(required = true, defaultValue = " ")
    protected Padding padding = new Padding();

    /**
     * Gets the value of the sn property.
     * 
     */
    public int getSn() {
        return sn;
    }

    /**
     * Sets the value of the sn property.
     * 
     */
    public void setSn(int value) {
        this.sn = value;
    }

    /**
     * Gets the value of the len property.
     * 
     */
    public int getLen() {
        return len;
    }

    /**
     * Sets the value of the len property.
     * 
     */
    public void setLen(int value) {
        this.len = value;
    }

    /**
     * Gets the value of the padding property.
     * 
     * @return
     *     possible object is
     *     {@link Padding }
     *     
     */
    public Padding getPadding() {
        return padding;
    }

    /**
     * Sets the value of the padding property.
     * 
     * @param value
     *     allowed object is
     *     {@link Padding }
     *     
     */
    public void setPadding(Padding value) {
        this.padding = value;
    }

	public int compareTo(FixLenField o) {
		if(o == null) return -1;
		return this.getSn() - o.getSn();
	}

}
