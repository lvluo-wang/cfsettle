package com.upg.ucars.tools.msg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ConfigException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.tools.msg.cfg.sp.SpConfig;
import com.upg.ucars.tools.msg.cfg.sp.SpField;
import com.upg.ucars.tools.msg.cfg.sp.SpItem;
import com.upg.ucars.tools.msg.cfg.sp.SpList;

/**
 * xml报文转化为分隔符报文转换器
 * @author shentuwy
 * @date 2011-10-14
 **/
public class Xml2SpTransformer extends AbsXml2TxtTransformer {
	private Xml2SpTransformer(){}

	private static Xml2SpTransformer instance;
	
	public static Xml2SpTransformer getInstance(){
		if(instance != null)
			return instance;
		else{
			instance = new Xml2SpTransformer();
			return instance;
		}
	}
	
	@Override
	public String doTransform(final String msg, final String configKey) {
		SpConfig spConfig = (SpConfig)MsgConfigsBuilder.getConfig(configKey);
		StringBuffer txtMsg = new StringBuffer("");
//		String filePath = "E:/myworkspaces85/xfosc2/src/msg-transform-config/demo/spMsg.xml";
		try {
			SAXReader saxReader = new SAXReader();
//			Document doc = saxReader.read(new File(filePath));
			InputStream is = new ByteArrayInputStream(msg.getBytes("UTF-8"));
			Document doc = saxReader.read(is);
			String sp = spConfig.getSp();
			TreeMap<Integer,List<SpItem>> spItemsMap = spConfig.getSortItemsMap();
			Iterator<Integer> it = spItemsMap.keySet().iterator();
			List<SpItem> spItems = null;
			String xpath, value;
			XPath path;
			// 生成消息头
			for(;it.hasNext();){
				spItems = spItemsMap.get(it.next());
				String sp_temp = "";
				for(SpItem item : spItems){
					xpath = item.getXpath();
					path = DocumentHelper.createXPath(xpath);
					Node node = (Node)path.selectSingleNode(doc);
					value = node.getText();
					txtMsg.append(sp_temp).append(value);
					sp_temp = sp;
				}
				txtMsg.append("\r\n");
			}
			
			// 生成数据循环单元
			SpList list = spConfig.getList();
			String listXpath = list.getXpath();
			List<SpField> spFields = spConfig.getSortFields();
			List<Element> elements = doc.selectNodes(listXpath);
			for (Element elt : elements) {
				String sp_temp_ = "";
				for (SpField field : spFields) {
					xpath = field.getXpath();
					path = DocumentHelper.createXPath(xpath);
					Node node = (Node)path.selectSingleNode(elt);
					value = node.getText();
					if (value == null)
						value = "";
					txtMsg.append(sp_temp_).append(value);
					sp_temp_ = sp;
				}
				txtMsg.append("\r\n");
			}
		} catch (Exception e) {
			ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_ERROR,e.getMessage());
		}
//		System.out.println("-----------------transformXml2TxtMsg result-----------------------");
//		System.out.println(txtMsg.toString());
		return txtMsg.toString();
	
	}

}
