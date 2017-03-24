
package com.upg.ucars.tools.msg.cfg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.fixlen.FixLenField;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractFieldType")
@XmlSeeAlso({
    FixLenField.class
})
public abstract class AbstractField
    extends Basic
{


}
