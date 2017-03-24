package com.upg.ucars.tools.msg.cfg.fixlen;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractItems;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fixLenItemsType")

public class FixLenItems extends AbstractItems {
	@XmlElement(name="item")
	private List<FixLenItem> items;

	public List<FixLenItem> getItems() {
		if(items == null)
			items = new ArrayList<FixLenItem>();
		return items;
	}
	
	public void addItem(FixLenItem item){
		this.getItems().add(item);
	}
}
