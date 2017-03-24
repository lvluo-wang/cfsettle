package com.upg.ucars.tools.msg.cfg.fixlen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractConfig;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fixLenConfigType")
@XmlRootElement(name="config")
public class FixLenConfig extends AbstractConfig {
	@XmlElement(name="items")
	private List<FixLenItems> itemses;
	@XmlElement(name="list")
	private FixLenList list;
	
	public FixLenList getList() {
		return list;
	}
	public void setList(FixLenList list) {
		this.list = list;
	}
	public List<FixLenItems> getItemses() {
		if(itemses == null)
			itemses = new ArrayList<FixLenItems>();
		return itemses;
	}
	public void addItems(FixLenItems items){
		this.getItemses().add(items);
	}
	
	public List<FixLenItem> getSortItems(){
		List<FixLenItem> fixLenItemList = new ArrayList<FixLenItem>();
		List<FixLenItems> itemses = this.getItemses();
		for(FixLenItems fixLenItems : itemses){
			if(fixLenItems != null){
				List<FixLenItem> items = fixLenItems.getItems();
				String parentXpath = fixLenItems.getXpath();
				for(FixLenItem item : items){
					String itemXpath = item.getXpath();
					if(!itemXpath.startsWith("/")){
						item.setXpath(parentXpath+"/"+itemXpath);
					}
					fixLenItemList.add(item);
				}
			}
		}
		Collections.sort(fixLenItemList);
		return fixLenItemList;
	}
	
	public List<FixLenField> getSortFields(){
		List<FixLenField> fixLenFields = new ArrayList<FixLenField>();
		FixLenList list = this.getList();
		if(list != null){
			fixLenFields = list.getFields();
		}
		Collections.sort(fixLenFields);
		return fixLenFields;
	}
}
