
package com.upg.ucars.tools.msg.cfg.fixlen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fixLenItemType", propOrder = {
    "startPos",
    "endPos",
    "padding"
})
@XmlRootElement(name="item")
public class FixLenItem extends AbstractItem implements Comparable<FixLenItem>{

    private int startPos;
    private int endPos;
    @XmlElement(required = true, defaultValue = " ")
    private Padding padding = new Padding();

    /**
     * Gets the value of the startPos property.
     * 
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * Sets the value of the startPos property.
     * 
     */
    public void setStartPos(int value) {
        this.startPos = value;
    }

    /**
     * Gets the value of the endPos property.
     * 
     */
    public int getEndPos() {
        return endPos;
    }

    /**
     * Sets the value of the endPos property.
     * 
     */
    public void setEndPos(int value) {
        this.endPos = value;
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

	public int compareTo(FixLenItem o) {
		if(o == null) return -1;
		return this.getStartPos() - o.getStartPos();
	}

}
