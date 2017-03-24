package com.upg.ucars.framework.base.queryComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询组件对象
 * @author cuckoo
 *
 */
public class QueryComponent implements Serializable,Cloneable {
	private String id;//组件id
	private List<QueryObject> queryList=new ArrayList<QueryObject>();//组件包含的查询对象
	private List<SortObject> sortList=new ArrayList<SortObject>();//排序列表
	private SortObject sort;
	
	public SortObject getSort() {
		return sort;
	}

	public void setSort(SortObject sort) {
		this.sort = sort;
	}

	public List<SortObject> getSortList() {
		return sortList;
	}

	public QueryComponent() {
	}
	public void addQuery(QueryObject query){
		queryList.add(query);
	}
	public void addSort(SortObject sort){
		sortList.add(sort);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<QueryObject> getQueryList() {
		return queryList;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		QueryComponent com=null;
		com=(QueryComponent)super.clone();
		com.queryList=new ArrayList<QueryObject>();
		if(queryList!=null){
			for(QueryObject query:queryList){
				QueryObject obj=(QueryObject)query.clone();
				com.queryList.add(obj);
			}
		}
		if(sortList!=null){
			com.sortList=new ArrayList<SortObject>();
			for(SortObject so:sortList){
				SortObject obj=(SortObject)so.clone();
				com.sortList.add(obj);
			}
		}
		if(sort!=null){
			com.sort=(SortObject)sort.clone();
		}
		return com;
	}
	
}
