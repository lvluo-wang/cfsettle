package com.upg.ucars.tools.msg;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-10-14
 **/
public abstract class AbsTxt2XmlTransformer extends AbsTransformer {

	/**
	 * 给列表数据部分增加父节点
	 * */
	protected Element createListParentElement(Document doc, String xpath) {
		Element element = null,e = null;
		if (xpath.startsWith("/")) {
			if (xpath.equals("/"))
				xpath = "";
			else
				xpath = xpath.substring(1);
		}
		String[] xpathArr = xpath.split("/");
		int len = xpathArr.length;
		Node node = null;
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				node = doc.selectSingleNode("/" + xpathArr[i]);
				if (node == null && !"".equals(xpathArr[i]))
					element = doc.addElement(xpathArr[i]);
				else
					element = doc.getRootElement();
			} else {
				node = element.selectSingleNode(xpathArr[i]);
				if (node == null) {
					e = DocumentHelper.createElement(xpathArr[i]);
					element.add(e);
					element = e;
				}else
					element = (Element)node;
			}
		}
		return element;
	}
	
	/**
	 * 给指定的元素增加节点
	 * */
	protected void addNodeValue(Element el, String xpath, String value) {
		String[] xpathArr = xpath.split("/");
		int len = xpathArr.length;
		boolean isElement = true;
		if (xpathArr[len - 1].startsWith("@"))
			isElement = false;
		Element element = el, e = null;
		for (int i = 0; i < len; i++) {
			if (i != (len - 1)) {
				e = DocumentHelper.createElement(xpathArr[i]);
				element.add(e);
				element = e;
			} else {
				if (isElement) {
					e = DocumentHelper.createElement(xpathArr[i]);
					element.add(e);
					e.setText(value);
				} else {
					Attribute att = DocumentHelper.createAttribute(element, xpathArr[i].substring(1), value);
					element.add(att);
				}
			}
		}
	}
	
	/**
	 * 给数据头部分增加节点
	 * */
	protected void addNodeValue(Document doc,String xpath,String value){
		if(xpath.startsWith("/")){
			if(xpath.equals("/")) 
				xpath = "";
			else
				xpath = xpath.substring(1);
		}
		String[] xpathArr = xpath.split("/");
		int len = xpathArr.length;
		boolean isElement = true;
		if(xpathArr[len-1].startsWith("@"))
			isElement = false;
		Element element = null,e = null;
		Node node = null;
		for(int i=0;i<len;i++){
			if(i != (len-1)){
				if(i == 0){
					node = doc.selectSingleNode("/"+xpathArr[i]);
					if(node == null && !"".equals(xpathArr[i]))
						element = doc.addElement(xpathArr[i]);
					else
						element = doc.getRootElement();
				}else{
					node = element.selectSingleNode(xpathArr[i]);
					if(node == null){
						e = DocumentHelper.createElement(xpathArr[i]);
						element.add(e);
						element = e;
					}else
						element = (Element)node;
				}
			}else{
				if(isElement){
					e = DocumentHelper.createElement(xpathArr[i]);
					element.add(e);
					e.setText(value);
				}else{
					Attribute att = DocumentHelper.createAttribute(element, xpathArr[i].substring(1), value);
					element.add(att);
				}
			}
		}
	}
}
