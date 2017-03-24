package com.upg.ucars.tools.msg.cfg.sp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractItems;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spItemsType")
public class SpItems extends AbstractItems {
	@XmlElement(name="item")
	private List<SpItem> items;

	public List<SpItem> getItems() {
		if (items == null)
			items = new ArrayList<SpItem>();
		return items;
	}

	public void addItem(SpItem item) {
		this.getItems().add(item);
	}
}
