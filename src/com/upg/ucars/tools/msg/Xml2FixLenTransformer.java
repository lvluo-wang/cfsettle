package com.upg.ucars.tools.msg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ConfigException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenConfig;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenField;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenItem;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenList;
import com.upg.ucars.tools.msg.cfg.fixlen.Padding;

/**
 * xml报文转化为定长报文转换器
 * @author shentuwy
 * @date 2011-10-14
 **/
public class Xml2FixLenTransformer extends AbsXml2TxtTransformer {
	private Xml2FixLenTransformer(){}

	private static Xml2FixLenTransformer instance;
	
	public static Xml2FixLenTransformer getInstance(){
		if(instance != null)
			return instance;
		else{
			instance = new Xml2FixLenTransformer();
			return instance;
		}
	}

	@Override
	public String doTransform(final String msg, final String configKey) {

		FixLenConfig fixLenConfig = (FixLenConfig)MsgConfigsBuilder.getConfig(configKey);
		StringBuffer txtMsg = new StringBuffer("");
//		String filePath = "E:/myworkspaces85/xfosc2/src/msg-transform-fixLenConfig/demo/fixLenMsg.xml";
		try {
			SAXReader saxReader = new SAXReader();
//			Document doc = saxReader.read(new File(filePath));
			InputStream is = new ByteArrayInputStream(msg.getBytes("UTF-8"));
			Document doc = saxReader.read(is);
			List<FixLenItem> fixLenItems = fixLenConfig.getSortItems();
			String xpath, value, pos, paddingVal;
			int startPos, endPos, len, val_len;
			Padding padding;
			XPath path;
			// 生成消息头
			String errorMsg = null;
			for (FixLenItem fixLenItem : fixLenItems) {
				xpath = fixLenItem.getXpath();
				startPos = fixLenItem.getStartPos();
				endPos = fixLenItem.getEndPos();
				len = endPos - startPos + 1;
				path = DocumentHelper.createXPath(xpath);
				Node node = (Node)path.selectSingleNode(doc);
				value = node.getText();
				if (value == null)
					value = "";
				val_len = value.length();
				if (val_len < len) {
					padding = fixLenItem.getPadding();
					pos = padding.getPos();
					paddingVal = padding.getValue();
					value = pad(value, pos, paddingVal, len);
				} else if (val_len > len) {
					errorMsg = "The length of Node["+xpath+"]'value is "+val_len+",but config is "+len+". error!";
					ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_CONF_ERROR,errorMsg);
				}
				txtMsg.append(value);
			}
			// 生成数据循环单元
			FixLenList list = fixLenConfig.getList();
			String listXpath = list.getXpath();
			List<FixLenField> fixLenFields = fixLenConfig.getSortFields();
			List<Element> elements = doc.selectNodes(listXpath);
			for (Element elt : elements) {
				for (FixLenField field : fixLenFields) {
					xpath = field.getXpath();
					len = field.getLen();
					path = DocumentHelper.createXPath(xpath);
					Node node = (Node)path.selectSingleNode(elt);
					value = node.getText();
					if (value == null)
						value = "";
					val_len = value.length();
					if (val_len < len) {
						padding = field.getPadding();
						pos = padding.getPos();
						paddingVal = padding.getValue();
						value = pad(value, pos, paddingVal, len);
					} else if (val_len > len) {
						errorMsg = "The length of Node["+xpath+"]'value is "+val_len+",but config is "+len+". error!";
						ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_CONF_ERROR,errorMsg);
					}
					txtMsg.append(value);
				}
			}
		} catch (Exception e) {
			ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_ERROR,e.getMessage());
		}
//		System.out.println("-----------------transformXml2TxtMsg result-----------------------");
//		System.out.println(txtMsg.toString());
		return txtMsg.toString();
	}

	/**
	 * 填充字符
	 * */
	private String pad(String value,String pos,String paddingVal,int len){
		int val_len = value.length();
		int pad_num = len - val_len;
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<pad_num;i++){
			sb.append(paddingVal);
		}
		if(Padding.POS_RIGHT.equals(pos))
			return value+sb.toString();
		else if(Padding.POS_LEFT.equals(pos))
			return sb.toString()+value;
		return value;
	}
}
