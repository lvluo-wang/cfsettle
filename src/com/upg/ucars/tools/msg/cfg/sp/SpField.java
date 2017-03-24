
package com.upg.ucars.tools.msg.cfg.sp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractField;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spFieldType", propOrder = {
    "sn"
})
public class SpField extends AbstractField implements Comparable<SpField>{

    private int sn;

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

	public int compareTo(SpField o) {
		if(o == null) return -1;
		return this.getSn() - o.getSn();
	}

}
