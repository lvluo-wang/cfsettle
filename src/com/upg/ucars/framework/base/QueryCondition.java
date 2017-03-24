package com.upg.ucars.framework.base;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.queryComponent.OperateType;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.QueryObject;
import com.upg.ucars.framework.base.queryComponent.SortObject;
import com.upg.ucars.framework.base.queryComponent.ValueType;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.DateTimeUtil;

/**
 * 查询组件
 *
 * @author shentuwy
 */
public class QueryCondition {
	
	private static final String DEFAULT_COUNT_KEY = "*";
	
	/** 初始化hql */
	private String hql;
	/** 统计的字段 */
	private String countKey = DEFAULT_COUNT_KEY;
	/** 条件集合 */
	private List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
	/** 排序字段集合 */
	private List<OrderBean> orderList = new ArrayList<OrderBean>(1);	
	/** 查询hql */
	private String queryHql;
	/** 参数 */
	private Map<String, Object> parameterMap;
	
		
	/**
	 * 构造器
	 * @param hql
	 * @param countKey 统计的键名,一般为id
	 */
	public QueryCondition(String hql, String countKey) {
		this.hql = hql;
		this.countKey = countKey;
	}

	public QueryCondition(String hql) {
		this.hql = hql;
	}

	public QueryCondition() {
	}

	/**
	 * 增加查询条件
	 * @param condition
	 */
	public void addCondition(ConditionBean condition){
		if( condition != null ){
			this.conditionList.add(condition);
		}
	}
	
	/**
	 * 增加查询条件 
	 *
	 * @param conditionList
	 */
	public void addConditionList(List<ConditionBean> conditionList){
		if( conditionList != null && conditionList.size() > 0 ){
			this.conditionList.addAll(conditionList);
		}
	}
	
	/**
	 * 增加排序字段
	 * @param order
	 */
	public void addOrder(OrderBean order){
		if( order != null ){
			orderList.add(order);
		}
	}
	
	public void addOrderList(List<OrderBean> orderList){
		if( orderList != null && orderList.size() > 0 ){
			this.orderList.addAll(orderList);
		}
	}
	
	public List<ConditionBean> getConditionList() {
		return conditionList;
	}

	public List<OrderBean> getOrderList() {
		return orderList;
	}

	/**
	 * 获取查询hql
	 * @return
	 */
	public String getQueryHql(){
		return queryHql;
	}
	/**
	 * 获取统计总数的查询hql
	 * @return
	 */
	public String getCountHql(){		
		if (countKey==null)
			countKey=DEFAULT_COUNT_KEY;
		String countStr = "select count("+countKey+") ";
		String queryHql = this.getQueryHql();
		String lowerHql = queryHql.toLowerCase();
		
		int fromIndex = lowerHql.indexOf("from ");
		int orderIndex = lowerHql.indexOf("order by");
		int groupIndex = lowerHql.indexOf("group by");
		if (orderIndex>0)
			queryHql = queryHql.substring(0, orderIndex);	
		if (groupIndex>0)
			queryHql = queryHql.substring(0, groupIndex);
		
		
		String countHql = countStr + queryHql.substring(fromIndex);
		if (countHql.toLowerCase().indexOf(" fetch ") > 1 && countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
			int firstWhereIndex = countHql.toLowerCase().indexOf(" where ");
			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
		}
		return countHql;
	}
	
	/**
	 * 获取参数
	 * @return Map< paramName, paramValue >
	 */
	public Map<String, Object> getParameterMap(){
		return parameterMap;
	}
	
	/**
	 * 增加参数 
	 *
	 * @param key 与hql中的参数对应(如hql中=:userType 则key='userType' )
	 * @param value
	 */
	public void addParameter(String key, Object value ){
		if (parameterMap == null)
			parameterMap = new HashMap<String, Object>();
		
		parameterMap.put(key, trimString(value));		
	}
	
	/**
	 * 内容初始化
	 */
	public void contextInitialized(){		
		String lowerHql = hql.toLowerCase();
		int whereIndex = lowerHql.indexOf("where ");
		int pos1 = lowerHql.indexOf("group by");
		int pos2 = lowerHql.indexOf("order by");
		int endPos=-1;
		{//存在GROUP 及 ORDER时，取最小的INDEX删除
			if (pos1>=0)
				endPos=pos1;
			if (endPos<0 || (pos2>=0 && pos2<endPos))
				endPos=pos2;
			
		}
		String frontHql = hql;
		String behindHql = null;
		if (endPos>=0){
			frontHql=hql.substring(0, endPos);
			behindHql = hql.substring(endPos);
		}
		StringBuilder sb = new StringBuilder(frontHql);
		if (whereIndex<1){
			sb.append(" where 1=1 ");
		}		
		if (!conditionList.isEmpty()){
			for (ConditionBean cb : conditionList){//增加条件及条件参数
				String condStr = this.paserCondition(cb);
				sb.append(condStr == null ? "" : condStr);
			}
		}		
		
		if (behindHql==null && !orderList.isEmpty()){
			sb.append(" order by ");
			for (OrderBean ob : orderList){
				sb.append(ob.getProperty()+(ob.isAsc()?" asc,":" desc,"));
			}
			sb.deleteCharAt(sb.length()-1);
		}
		if (behindHql != null)
			sb.append(" "+behindHql);
		
		queryHql = sb.toString();
	}
	
	/**
	 * 解析查询条件
	 *
	 * @param cb
	 * @return 条件hql
	 */
	private String paserCondition(ConditionBean cb){
		if (cb.hasPartner()){//组合条件
			StringBuffer sb = new StringBuffer();
			
			String condStr = this.paserSingleCondition(cb);
			if (condStr!=null)
				sb.append(condStr);
			
			for(ConditionBean partnerCb : cb.getPartners()){
				String str = this.paserCondition(partnerCb);
				if (str !=null )
					sb.append(str);
			}
			if (sb.length()>0){
				sb.insert(0, " "+cb.getLogic()+" (");//字符开始处前增加 and | or		
				sb.append(") ");
				return sb.toString();
			} else
				return null;			
			
		}else{//单个条件
			String condStr = this.paserSingleCondition(cb);
			if (condStr == null)
				return null;
			
			return " "+cb.getLogic()+" "+condStr;
		}
	
	}
	private int paramIndex = 1;//参数序列
	private String _param = "_param";//参数名前缀
	/**
	 * 解析无组合关系的条件
	 *
	 * @param cb
	 * @return
	 */
	private String paserSingleCondition(ConditionBean cb){
		String compStr=null;
		if (ConditionBean.IS_NULL.equals(cb.getOperate()) || ConditionBean.IS_NOT_NULL.equals(cb.getOperate())){
			compStr =  cb.getField()+" "+cb.getOperate();
		}else if (ConditionBean.EXISTS.equals(cb.getOperate()) || ConditionBean.NOT_EXISTS.equals(cb.getOperate())) {
			compStr = cb.getOperate() + " (" + cb.getField() + ")";
			Map<String,Object> subQueryParam = (Map<String,Object>) cb.getValue();
			if (subQueryParam != null && subQueryParam.size() > 0) {
				for (Iterator<Map.Entry<String, Object>> it = subQueryParam.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					addParameter(entry.getKey(), entry.getValue());
				}
			}
		}else if (cb.getValue() != null && !("".equals(cb.getValue()))){
			//组装hql的条件
			String paramName = _param+paramIndex;
			paramIndex++;
			
			if (ConditionBean.IN.equals(cb.getOperate()) || ConditionBean.NOT_IN.equals(cb.getOperate())){
				compStr = cb.getField()+" "+cb.getOperate()+"(:"+ paramName+")" ;
			}else{
				compStr = cb.getField()+" "+cb.getOperate()+":"+ paramName;
			}
			
			//设置参数
			if (ConditionBean.LIKE.equals(cb.getOperate())){
				if (cb.getValue().toString().contains("_") || cb.getValue().toString().contains("%"))
					addParameter(paramName,trimString(cb.getValue()));
				else
					addParameter(paramName,"%"+ trimString(cb.getValue())+"%");	
				
			}else
				addParameter(paramName, trimString(cb.getValue()));
		}
		
		return compStr;
	}

	public String getCountKey() {
		return countKey;
	}

	public void setCountKey(String countKey) {
		this.countKey = countKey;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getHql() {
		return hql;
	}	

	public void read(QueryComponent comp){
		if(comp==null)
			return;
		List<QueryObject> queryList=comp.getQueryList();
		Iterator<QueryObject> it=queryList.iterator();
		while(it.hasNext()){
			QueryObject query=it.next();
			String name=query.getQueryName();
			String operate=query.getQueryOperate();
			if(operate==null){
				operate=OperateType.EQ;
			}
			operate=getOp(operate);
			String value=query.getQueryValue();
			if(value==null||value.length()<1)
				continue;
			Object val = value;
			//根据类型转换成对应对象
			if(ValueType.DOUBLE.equals(query.getValueType())){
				val = new BigDecimal(value);
			}else if(ValueType.LONG.equals(query.getValueType())){
				val = Long.valueOf(value);
			}else if(ValueType.DATE.equals(query.getValueType())){
				try {
					val = DateTimeUtil.parse(value, "yyyy-MM-dd");
				} catch (ParseException e) {
					try {
						val = DateTimeUtil.parse(value, "yyyyMMdd");
					} catch (ParseException e1) {
						continue;
					}
				}
			}
			
			ConditionBean bean=new ConditionBean(name, operate, val);
			addCondition(bean);
		}
		SortObject sortO=comp.getSort();
		if(sortO!=null){
			OrderBean ord=null;
			if("asc".equals(sortO.getSortType())){
				ord=new OrderBean(sortO.getSortName(),true);
			}else{
				ord=new OrderBean(sortO.getSortName(),false);
			}
			addOrder(ord);
		}
	}
	public String getOp(String src){
		if("eq".equals(src)){
			return "=";
		}
		if("gt".equals(src)){
			return ">";
		}
		if("ge".equals(src)){
			return ">=";
		}
		if("lt".equals(src)){
			return "<";
		}
		if("le".equals(src)){
			return "<=";
		}
		return src;
	}
	
	/**
	 * 
	 * 过滤字符串的前后空格，非字符串返回本身
	 *
	 * @param obj
	 * @return
	 */
	private static Object trimString(Object obj){
		if( obj != null && obj instanceof String ){
			String str = (String)obj;
			return str.trim();
		}
		return obj;
	}
}
