package com.upg.ucars.tools.msg.cfg.sp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.upg.ucars.tools.msg.cfg.AbstractConfig;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spConfigType")
@XmlRootElement(name="config")
public class SpConfig extends AbstractConfig {
	@XmlElement(name="items")
	private List<SpItems> itemses;
	@XmlElement(name="list")
	private SpList list;
	@XmlElement(name="sp")
	private String sp;
	
	public SpList getList() {
		return list;
	}
	public void setList(SpList list) {
		this.list = list;
	}
	public List<SpItems> getItemses() {
		if(itemses == null)
			itemses = new ArrayList<SpItems>();
		return itemses;
	}
	public void addItems(SpItems items){
		this.getItemses().add(items);
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	
	public TreeMap<Integer,List<SpItem>> getSortItemsMap(){
		TreeMap<Integer,List<SpItem>> map = new TreeMap<Integer,List<SpItem>>();
		List<SpItem> spItemList = new ArrayList<SpItem>();
		List<SpItems> itemses = this.getItemses();
		for(SpItems spItems : itemses){
			if(spItems != null){
				List<SpItem> items = spItems.getItems();
				String parentXpath = spItems.getXpath();
				for(SpItem item : items){
					String itemXpath = item.getXpath();
					if(!itemXpath.startsWith("/")){
						item.setXpath(parentXpath+"/"+itemXpath);
					}
					spItemList.add(item);
				}
			}
		}
		Collections.sort(spItemList);
		List<SpItem> spItems = null;
		for(SpItem item : spItemList){
			Integer lineNo = item.getLineNo();
			if(map.containsKey(lineNo)){
				spItems = map.get(lineNo);
				spItems.add(item);
				map.put(lineNo, spItems);
//				List<SpItem> spItems = new ArrayList<SpItem>();
//				spItems.add(o)
			}else{
				spItems = new ArrayList<SpItem>();
				spItems.add(item);
				map.put(lineNo, spItems);
			}
		}
//		Collections.s
		return map;
	}
	
	public List<SpField> getSortFields(){
		List<SpField> spFields = new ArrayList<SpField>();
		SpList list = this.getList();
		if(list != null){
			spFields = list.getFields();
		}
		Collections.sort(spFields);
		return spFields;
	}
}
