package com.upg.ucars.framework.base.queryComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.XmlUtil;

/**
 * 查询组件的配置文件加载
 * @author cuckoo
 *
 */
public class QueryConfigLoader {
	private static String RES_CONFIG = "query-config.xml";
	private static Map<String,QueryComponent> map=new HashMap<String,QueryComponent>();
	private static List<String> pathList=new ArrayList<String>();
	static{
		initConfig();
		initQuery();
		
	}
	/**
	 * (初始化查询组件配置)  void
	 */
	private static void initQuery() {
			Iterator<String> pathIt=pathList.iterator();
			while(pathIt.hasNext()){
				String path=pathIt.next();
				Document doc = null;
				try {
					doc = XmlUtil.parseXmlDoc(path);
					Element root = doc.getRootElement();
					List componentList=root.getChildren("component");
					for(int i=0;componentList!=null&&i<componentList.size();i++){
						QueryComponent component=new QueryComponent();
						Element componentE=(Element)componentList.get(i);
						String id=componentE.getAttribute("id").getValue();
						component.setId(id);//设置查询组件id
						Element queryE=(Element)componentE.getChildren("query").get(0);
						List dataEList=queryE.getChildren("data");
						for(int j=0;j<dataEList.size();j++){
							QueryObject bean=new QueryObject();
							Element dataE=(Element)dataEList.get(j);
							Attribute operateAtt=dataE.getAttribute("operate");
							if(operateAtt!=null){
								String operate=operateAtt.getValue();
								String[] operates=operate.split(",");
								for(int k=0;k<operates.length;k++){
									bean.addOperate(operates[k]);//添加可选的查询操作符
								}
							}
							Attribute dataNameAtt=dataE.getAttribute("name");
							String dataName=dataNameAtt.getValue();
							Attribute dataTypeAtt=dataE.getAttribute("type");
							String dataType="String";
							String dataValue="";
							String dataValueList="";
							if(dataTypeAtt!=null){
								dataType=dataTypeAtt.getValue();
							}
							Attribute dataValueAtt=dataE.getAttribute("value");
							if(dataValueAtt!=null){
								dataValue=dataValueAtt.getValue();
							}
							if("List".equals(dataType)){
								Element listE=(Element)(dataE.getChildren("list").get(0));
								dataValueList=listE.getText();
								dataValueList=dataValueList.replaceAll("\t", "");
								dataValueList=dataValueList.replaceAll("\n", "");
							}
							bean.setQueryName(dataName);//设置查询名称
							bean.setQueryValue(dataValue);//设置默认的查询值
							bean.setValueType(dataType);//设置值的类型
							bean.setValueList(dataValueList);//list类型的值
							component.addQuery(bean);//添加查询条件
						}
						List sortEList=componentE.getChildren("sort");
						if(sortEList!=null&&sortEList.size()>0){
							Element sortE=(Element)sortEList.get(0);
							List sortParamEList=sortE.getChildren("param");
							for(int j=0;j<sortParamEList.size();j++){
								Element sortParamE=(Element)sortParamEList.get(j);
								String sortParamName=sortParamE.getAttribute("name").getValue();
								String sortParamType=sortParamE.getAttribute("type")!=null?sortParamE.getAttribute("type").getValue():"asc";
								SortObject sort=new SortObject();
								sort.setSortName(sortParamName);
								sort.setSortType(sortParamType);
								component.addSort(sort);//添加排序条件
							}
						}
						
						map.put(id, component);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LogUtil.getExceptionLog().error(e);
				}
				
			}
	}
	private static void initConfig(){
			Document doc = null;
			try {
				doc = XmlUtil.parseXmlDoc(RES_CONFIG);
				Element root=doc.getRootElement();
				List resList=root.getChildren("include");
				if(resList!=null&&resList.size()>0){
					for(int i=0;i<resList.size();i++){
						Element resElement=(Element)resList.get(i);
						String path=resElement.getAttribute("path").getValue();
						pathList.add( path);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.getExceptionLog().error(e);
			}
	}
	/**
	 * 根据组件id获取组件模型
	 * @param componentId
	 * @return
	 */
	public static QueryComponent getQueryComponent(String componentId){
		QueryComponent comp=null;
		try {
			QueryComponent tmp=map.get(componentId);
			comp=(QueryComponent)(tmp.clone());
		} catch (Exception e) {
			ExceptionManager.throwException(SysException.class, ErrorCodeConst.SYS_ERROR, e);
		}
		return comp;
	}
	/**
	 * (将map转换为组件可以识别的字符串[{"id":"1","text":"value"}])
	 * @param map
	 * @return  String
	 */
	public static String getValueListByMap(Map<String,String> map){
		if(map!=null){
			String str="[";
			Set<String> keys=map.keySet();
			Iterator<String> it=keys.iterator();
			boolean flag=false;
			while(it.hasNext()){
				String tmp="{";
				if(flag)
					tmp=",{";
				String key=it.next();
				String value=map.get(key);
				tmp+="\"id\":\""+key+"\",\"text\":\""+value+"\"}";
				flag=true;
				str+=tmp;
			}
			str+="]";
			return str;
		}
		return "";
	}

}
