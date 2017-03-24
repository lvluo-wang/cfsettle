package com.upg.ucars.tools.msg;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.upg.ucars.tools.msg.cfg.sp.SpConfig;
import com.upg.ucars.tools.msg.cfg.sp.SpField;
import com.upg.ucars.tools.msg.cfg.sp.SpItem;
import com.upg.ucars.tools.msg.cfg.sp.SpList;



/**
 * 分割符文本报文到xml报文转换器
 * @author shentuwy
 * @date 2011-10-14
 **/
public class Sp2XmlTransformer extends AbsTxt2XmlTransformer {
	private Sp2XmlTransformer(){}

	private static Sp2XmlTransformer instance;
	
	public static Sp2XmlTransformer getInstance(){
		if(instance != null)
			return instance;
		else{
			instance = new Sp2XmlTransformer();
			return instance;
		}
	}

	@Override
	public String doTransform(final String msg, final String configKey) {

		SpConfig spConfig = (SpConfig)MsgConfigsBuilder.getConfig(configKey);
		int totalLines = 0;
		try {
			totalLines = getTotalLines(msg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		TreeMap<Integer,List<SpItem>> spItemsMap = spConfig.getSortItemsMap();
		String xpath, line = "";
		String sp = spConfig.getSp();
		if("|".equals(sp))
			sp = "\\|";
		else if(".".equals(sp))
			sp = "\\.";
		String[] values;
		int sn = 0;
		Integer lineNo = 0;
		Document doc = DocumentFactory.getInstance().createDocument();
		Iterator<Integer> it = spItemsMap.keySet().iterator();
		List<SpItem> spItems = null;
		// 生成消息头
		for(;it.hasNext();){
			lineNo = it.next();
			try {
				line = readLine(msg,lineNo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			values = line.split(sp);
			spItems = spItemsMap.get(lineNo);
			for(SpItem item : spItems){
				xpath = item.getXpath();
				sn = item.getSn();
				if(sn <= values.length)
					addNodeValue(doc,xpath,values[sn-1]);
				else
					addNodeValue(doc,xpath,"");
			}
		}
		
		// 生成数据循环单元
		SpList list = spConfig.getList();
		String listXpath = list.getXpath();
		int startLineNo = list.getStartLineNo();
		int lastIndex = listXpath.lastIndexOf("/");
		String dataEelementName = listXpath.substring(lastIndex+1);
		String dataParentXpath = listXpath.substring(0,lastIndex);
		Element parentElement = createListParentElement(doc,dataParentXpath);
		Element e = null;
		List<SpField> spFields = spConfig.getSortFields();
		for(int i = startLineNo;i<=totalLines;i++){
			try {
				line = readLine(msg,i);//这里需要改造
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			values = line.split(sp);
			e = DocumentHelper.createElement(dataEelementName);
			parentElement.add(e);
			for(SpField field : spFields){
				sn = field.getSn();
				xpath = field.getXpath();
				if(sn <= values.length)
					addNodeValue(e,xpath,values[sn-1]);
				else
					addNodeValue(e,xpath,"");
			}
		}
		return doc.asXML();
	}

	/**
	 * 读取文本报文的某行数据
	 * */ 
	private String readLine(String msg, int lineNo) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(msg.getBytes())));
		String line = reader.readLine();
		int num = 0;
		while (line != null) {
			if (lineNo == ++num) {
				break;
			}
			line = reader.readLine();
		}
		reader.close();
		return line;
	}

	/**
	 * 获得报文的总行数
	 * */ 
	private int getTotalLines(String msg) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(msg.getBytes())));
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}
}
