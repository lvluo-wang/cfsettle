package com.upg.ucars.framework.exception.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.XmlUtil;

/**
 * 异常配置文件上下文加载器，主要用来解析异常配置文件，将异常定义文件加载至内存中
 * 
 * @date 2010-12-17 上午11:50:44
 *
 */
public class ErrorContextLoader  {
	private static final String RES_CONFIG = "exception-config.xml";
	private static HashMap<String,List<String>> localeMap=new HashMap<String,List<String>>();
	private static HashMap<String, HashMap<String,EcError>> ecMap=new HashMap<String,HashMap<String,EcError>>();
	private static String defaultLocale=null;
	private static int errorCache=0;
	private static final int DEFAULT_CACHE=3;
	public static boolean DEVELOPMENTMODEL=false;
	private static List<String> ruleSupportList=null;
	private static List<String> ruleUnsupportList=null;
	static{
		initRes();
		initErrors();
	}
	/**
	 * 
	 * @param code 错误码
	 * @return 错误元对象
	 */
	public static EcError getError(String code) {
		EcError error=null;
		if(ecMap!=null&&code!=null){
			HashMap<String ,EcError> errMap=ecMap.get(defaultLocale);
			error= errMap.get(code);
		}
		if(error==null){
			error=new EcError();
			error.setCode(ErrorCodeConst.SYS_ERROR);
			error.setLog("true");
			error.setDetail("");
			error.setHelp("...");
			error.setType(ErrorType.ERROR);
			error.setMessage("Undefined,ERROR_CODE:"+code);
		}
		return error;
	}
	/**
	 * 
	 * @param code 错误编码
	 * @param locale 本地化标识
	 * @return 错误元对象
	 */
	public static EcError getError(String code, Locale locale) {
		EcError error=null;
		if(locale==null)
			error= getError(code);
		if(ecMap!=null&&code!=null){
			HashMap<String ,EcError> errMap=ecMap.get(locale.toString());
			if(errMap==null){
				error= getError(code);
			}else{
				error= errMap.get(code);
			}
			
		}
		if(error==null){
			error=new EcError();
			error.setCode(ErrorCodeConst.SYS_ERROR);
			error.setLog("true");
			error.setDetail("");
			error.setHelp("...");
			error.setType(ErrorType.ERROR);
			error.setMessage("Undefined,ERROR_CODE:"+code);
		}
		return error;
	}
	/**
	 * 获取异常缓存条数
	 * @return  int 异常缓存条数
	 */
	public static int getErrorCache(){
		return errorCache;
	}
	/**
	 * 获取支持异常记录的命名列表
	 * @return  List<String> 命名列表（包名或类名的一部分）
	 */
	public static List<String> getSupportList(){
		return ruleSupportList;
	}
	/**
	 * 获取不支持异常记录的命名列表
	 * @return  List<String> 命名列表（包名或类名的一部分）
	 */
	public static List<String> getUnsupportList(){
		return ruleUnsupportList;
	}
	/**
	 * 加载异常资源文件配置) 
	 */
	private static void initRes(){
			Document doc = null;
			try {
				doc = XmlUtil.parseXmlDoc(RES_CONFIG);
				Element root=doc.getRootElement();
				Element exceptionCacheE=(Element)(root.getChildren("exception-cache").get(0));
				String exceptionCache=exceptionCacheE.getAttribute("max").getValue();
				try{
					errorCache=Integer.parseInt(exceptionCache);
				}catch(Exception e){
					errorCache=DEFAULT_CACHE;
				}
				Element developmentE=(Element)(root.getChildren("development").get(0));
				String model=developmentE.getAttribute("model").getValue();
				if("1".equals(model)){
					DEVELOPMENTMODEL=true;
				}
				Element exceptionLogRuleE=(Element)(root.getChildren("exception-log-rule").get(0));
				Element supportE=(Element)(exceptionLogRuleE.getChildren("support").get(0));
				Element unsupportE=(Element)(exceptionLogRuleE.getChildren("unsupport").get(0));
				List supportList=supportE.getChildren("value");
				ruleSupportList=new ArrayList<String>();
				for(int i=0;i<supportList.size();i++){
					Element valueE=(Element)supportList.get(i);
					String value=valueE.getText();
					ruleSupportList.add(value);
				}
				List unsupportList=unsupportE.getChildren("value");
				ruleUnsupportList=new ArrayList<String>();
				for(int i=0;i<unsupportList.size();i++){
					Element valueE=(Element)unsupportList.get(i);
					String value=valueE.getText();
					ruleUnsupportList.add(value);
				}
				List resList=root.getChildren("resource");
				for(int i=0;i<resList.size();i++){
					Element resElement=(Element)resList.get(i);
					String locale=resElement.getAttribute("locale").getValue();
					String path=resElement.getAttribute("path").getValue();
					if(localeMap.get(locale)==null){
						localeMap.put(locale, new ArrayList<String>());
					}
					localeMap.get(locale).add(path);
					Attribute defAtt=resElement.getAttribute("default");
					if(defAtt!=null&&defaultLocale==null){
						defaultLocale=locale;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.getExceptionLog().error(e);
			}
	}
	/**
	 * 加载异常定义文件配置
	 */
	private static void initErrors() {
			Set<String> localeSet=localeMap.keySet();
			Iterator<String> setIt=localeSet.iterator();
			while(setIt.hasNext()){
				String locale=setIt.next();
				List<String> pathList=localeMap.get(locale);
				for(String path:pathList){
					Document doc = null;
					try {
						doc = XmlUtil.parseXmlDoc(path);
						Element rootElement=doc.getRootElement();
						List errorList=rootElement.getChildren("error");
						HashMap<String,EcError> errMap=ecMap.get(locale);
						if(errMap==null){
							errMap=new HashMap<String,EcError>();
							ecMap.put(locale, errMap);
						}
						for(int i=0;i<errorList.size();i++){
							EcError error=new EcError();
							Element errorElement=(Element)errorList.get(i);
							Attribute typeAtt=errorElement.getAttribute("type");
							Attribute logAtt=errorElement.getAttribute("log");
							String code=errorElement.getAttribute("code").getValue();
							String type=typeAtt==null?"Error":typeAtt.getValue();
							String log=logAtt==null?"true":logAtt.getValue();
							String message=((Element)errorElement.getChildren("message").get(0)).getText();
							String detail=((Element)errorElement.getChildren("detail").get(0)).getText();
							String help=((Element)errorElement.getChildren("help").get(0)).getText();
							error.setCode(code);
							error.setDetail(detail);
							error.setHelp(help);
							error.setLog(log);
							error.setMessage(message);
							error.setType(type);
							errMap.put(code, error);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LogUtil.getExceptionLog().error(e);
					}
				}
			}
			
	}




}
