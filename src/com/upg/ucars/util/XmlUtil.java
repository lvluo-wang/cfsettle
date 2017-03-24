package com.upg.ucars.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;


/**
 *  xml解析工具
 *
 * @author shentuwy
 */
public class XmlUtil {
	
	
	/**
	 * 解析xml文件 
	 *
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Document parseXmlDoc(String fileName) throws Exception{
		Document  doc=null;
		SAXBuilder sb=new SAXBuilder();
		//创建文档		
		try {
			doc=sb.build(XmlUtil.class.getClassLoader().getResourceAsStream(fileName));
		} catch (Exception e) {
//			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.RESOURCE_FILE_PARS_ERR,new String[]{e.getMessage()}, e);
			throw e;
		} 
		return doc;
	}
	/**
	 * XML字串解析
	 * @param xmlContent	XML字串
	 * @return
	 * @throws Exception
	 */
	public static Document parse(String xmlContent) throws Exception {
		if(xmlContent == null) return null;
		SAXBuilder sb = new SAXBuilder();
		//创建文档	
		try {
			return sb.build(new ByteArrayInputStream(xmlContent.getBytes()));
		} catch (Exception e) {
//			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.RESOURCE_FILE_PARS_ERR,new String[]{e.getMessage()}, e);
			throw e;
		} 
	}
	/**
	 * 解析XML输入流
	 * @param is
	 * @return Document
	 * @throws Exception
	 */
	public static Document parse(InputStream is) throws Exception {
		SAXBuilder sb = new SAXBuilder();
		try {
			return sb.build(is);
		} catch (Exception e) {
//			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.RESOURCE_FILE_PARS_ERR,new String[]{e.getMessage()}, e);
			throw e;
		} 
	}
	/**
	 * XML字串解析
	 * @param xmlContent  XML字串
	 * @return	org.dom4j.Document对象
	 */
	public static org.dom4j.Document parse2Dom4j(String xmlContent) throws Exception {
		try {
			return new SAXReader().read(new ByteArrayInputStream(xmlContent.getBytes()));
		} catch (Exception e) {
//			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.RESOURCE_FILE_PARS_ERR,new String[]{e.getMessage()}, e);
			throw e;
		} 
	}
	/**
	 * xml解析成集合数对
	 * @param doc org.dom4j.Document对象
	 * @return Map  集合数对
	 */
	public static Map xmlMap(org.dom4j.Document doc) {
		InitMapVisit visit = new InitMapVisit();
		doc.getRootElement().accept(visit);
		return visit.getMap();		
	}
	/**
	 * 集合数对转成对象
	 * @param startTag	集合数对中key的起始标签，如key="document.bill.billNo",则可定义startTag="document"
	 * @param datas	集合数对
	 * @param bean	对象bean
	 */
	public static void map2Bean(String startTag, Map datas, Object bean) {
		List props = BeanWrapperUtils.getBeanPropertyName(bean);
		Iterator it = props.iterator();
		String propName = "", mapKey = "";
		while(it.hasNext()) {
			propName = (String)it.next();
			if(propName != null) {
				Class clz = BeanWrapperUtils.getPropertyType(bean,propName);
				mapKey = startTag + "." + propName;
				Object value = datas.get(mapKey);
				if(clz.isPrimitive() || BeanWrapperUtils.isWrapClass(clz) || clz == String.class || clz == Date.class) {
					BeanWrapperUtils.setBeanProperty(bean, propName, value);
				} else {
					map2Bean(propName, datas, bean);
				}
			}
		}
	}
	public static void xml2Bean(String xml, Object bean) throws Exception {
		map2Bean("document", xmlMap(parse2Dom4j(xml)), bean);
	}
	public static org.dom4j.Document bean2xml(Object bean, String startTag) {
		
		org.dom4j.Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement(startTag);
		
		addElement(root, bean);
		return doc;
		
	}
	private static void addElement(Element e, Object bean) {
		List props = BeanWrapperUtils.getBeanPropertyName(bean);
		Iterator it = props.iterator();
		String propName = "";
		while(it.hasNext()) {
			propName = (String)it.next();
			Object value = BeanWrapperUtils.getBeanPropertyValue(bean, propName);
			Class clz = BeanWrapperUtils.getPropertyType(bean,propName);
			if(clz.isPrimitive() || BeanWrapperUtils.isWrapClass(clz) || clz == String.class) {
				e.addElement(propName);
				e.addText(String.valueOf(value));
			} else {
				//addElement(value);
			}
		}
	}

}

/**
 * 实现访问者模式,处理每个结点的访问操作
 */
class InitMapVisit extends VisitorSupport {

	public HashMap map = new HashMap();

	public void visit(Element element) {
		List elementsList = element.elements();
		if (elementsList.isEmpty()) {
			map.put(element.getPath().replace("/", ".").replace(".Document.", ""), element.getTextTrim());
		}
	}

	public HashMap getMap() {
		return map;
	}

	public void setMap(HashMap map) {
		this.map = map;
	}

}

