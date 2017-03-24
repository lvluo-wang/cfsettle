package com.upg.ucars.tools.msg;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.upg.ucars.tools.msg.cfg.fixlen.FixLenConfig;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenField;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenItem;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenList;

/**
 * 定长文本报文转化为xml报文转换器
 * @author shentuwy
 * @date 2011-10-14
 **/
public class FixLen2XmlTransformer extends AbsTxt2XmlTransformer {
	
	private FixLen2XmlTransformer(){}

	private static FixLen2XmlTransformer instance;
	
	public static FixLen2XmlTransformer getInstance(){
		if(instance != null)
			return instance;
		else{
			instance = new FixLen2XmlTransformer();
			return instance;
		}
	}

	@Override
	public String doTransform(final String msg, final String configKey) {
		FixLenConfig fixLenConfig = (FixLenConfig)MsgConfigsBuilder.getConfig(configKey);
		List<FixLenItem> fixLenItems = fixLenConfig.getSortItems();
		String xpath, value;
		int startPos, endPos, cycLen, cycStartPos;
		Document doc = DocumentFactory.getInstance().createDocument();
		// 生成items部分
		for (FixLenItem fixLenItem : fixLenItems) {
			xpath = fixLenItem.getXpath();
			startPos = fixLenItem.getStartPos();
			endPos = fixLenItem.getEndPos();
			value = msg.substring(startPos-1, endPos);
			addNodeValue(doc,xpath,value);
		}
		//生成列表数据部分
		FixLenList list = fixLenConfig.getList();
		String listXpath = list.getXpath();
		int lastIndex = listXpath.lastIndexOf("/");
		String dataEelement = listXpath.substring(lastIndex+1);
		String dataParentXpath = listXpath.substring(0,lastIndex);
		cycStartPos = list.getCycStartPos();
		cycLen = list.getCycLen();
		List<FixLenField> fixLenFields = fixLenConfig.getSortFields();
		int msgLen = msg.length();
		int cycNum = (msgLen-cycStartPos+1)/cycLen;
		Element parentElement = createListParentElement(doc,dataParentXpath);
		Element e = null;
		int startIndex=0;
		startIndex = cycStartPos - 1;
		for(int i=0;i<cycNum;i++){
			e = DocumentHelper.createElement(dataEelement);
			parentElement.add(e);
			String fieldXpath = "";
			int len;
			for(FixLenField field : fixLenFields){
				fieldXpath = field.getXpath();
				len = field.getLen();
				value = msg.substring(startIndex, startIndex + len);
				addNodeValue(e,fieldXpath,value);
				startIndex = startIndex + len;
			}
		}
		return doc.asXML();
	}

}
