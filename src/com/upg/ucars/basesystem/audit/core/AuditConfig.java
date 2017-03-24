/*
 * 源程序名称: AuditConfig.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.audit.core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;

import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.StringUtil;
import com.upg.ucars.util.XmlUtil;
/**
 * 
 * 功能说明：审批配置类，管理产品号与回调服务的映射关系,可以自动加载最新的配置文件
 * @author shentuwy  
 * @date 2011-8-14 下午12:48:51 
 *
 */
public class AuditConfig {
	private static final String CONFIG_PATH = "audit-config.xml";
	private static  boolean devModel=false;
	private static Long configLastModified=0L;
	private static Set<String> resourcesSet=new HashSet<String>();
	private static Map<String,Long> resourcesMap=new HashMap<String,Long>();
	private static Map<String,String> urlMap=new HashMap<String,String>();
	private static Map<String,String> config=new HashMap<String,String>();
	/**
	 * 根据产品编号获取配置的回调服务，在开发模式下如果配置文件有修改则自动加载最新的配置
	 * @param prodNo 产品编号
	 * @return 回调服务
	 */
	public static String getCallBackByProdNo(String prodNo){
		if(!StringUtil.isEmpty(prodNo)){
			if(devModel){
				refreshConfig();
			}
			return config.get(prodNo);
		}
		return null;
	}
	/**
	 * 获取审批页面地址，在开发模式下如果配置文件有修改则自动加载最新的配置
	 * @param prodNo 产品编号
	 * @return 审批页面地址
	 */
	public static String getAuditUrlByProdNo(String prodNo){
		if(!StringUtil.isEmpty(prodNo)){
			if(devModel){
				refreshConfig();
			}
			return urlMap.get(prodNo);
		}
		return null;
	}
	/**
	 * 刷新配置信息，如果配置文件有修改则自动加载最新的配置
	 */
	private static void refreshConfig(){
		File file=getFile(CONFIG_PATH);
		if(file.lastModified()!=configLastModified){
			initConfig();
		}else{
			for(String path:resourcesSet){
				File f=getFile(path);
				Long lastModified=resourcesMap.get(path);
				if(f.lastModified()!=lastModified){
					parseConfig(path);
				}
			}
		}
	}
	private static File getFile(String path){
		File file=new File(AuditConfig.class.getClassLoader().getResource(path).getFile());
		return file;
	}
	/**
	 * 初始化配置
	 */
	public static void initConfig(){
		Document doc=null;
		try{
			doc=XmlUtil.parseXmlDoc(CONFIG_PATH);
			Element rootE=doc.getRootElement();
			
			String dev=rootE.getAttribute("devMode")==null?"false":rootE.getAttribute("devMode").getValue();
			if("true".equals(dev)||"1".equals(dev)){
				devModel=true;
				File file=getFile(CONFIG_PATH);
				configLastModified=file.lastModified();
			}else{
				devModel=false;
			}
			List resList=rootE.getChildren("include");
			for(int i=0;i<resList.size();i++){
				Element resElement=(Element)resList.get(i);
				String path=resElement.getAttribute("path").getValue();
				resourcesSet.add(path);
			}
			for(String p:resourcesSet){
				parseConfig(p);
			}
		}catch(Exception e){
			e.printStackTrace();
			LogUtil.getExceptionLog().error(e);
		}
		
		
	}
	/**
	 * 解析配置信息
	 * @param path 配置文件的路径
	 */
	private static void parseConfig(String path){
		Document doc = null;
		try {
			doc = XmlUtil.parseXmlDoc(path);
			if(devModel){
				File f=getFile(path);
				resourcesMap.put(path, f.lastModified());
			}
			Element root = doc.getRootElement();
			List mapList=root.getChildren("audit");
			for(int i=0;i<mapList.size();i++){
				Element mapE=(Element)mapList.get(i);
				String key=mapE.getAttribute("prodNo").getValue();
				String value=mapE.getAttribute("callBack").getValue();
				String url=mapE.getAttribute("url").getValue();
				config.put(key, value);
				urlMap.put(key, url);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.getExceptionLog().error(e);
		}
		
	}
}
