package com.upg.ucars.framework.bpm.assist.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.upg.ucars.framework.bpm.assist.model.ProcDefDTO;
import com.upg.ucars.util.XmlUtil;

/**
 * 配置管理器
 * @author shentuwy
 */
public class ProcDefManager {

	private static Map procDefMap = new HashMap();
	private static List<ProcDefDTO> procDefList = new ArrayList<ProcDefDTO>();

	static {
		init();

	}
	
	private static void init(){		
		Document doc;
		try {
			doc = XmlUtil.parseXmlDoc("BpmAssist.xml");
			Element rootElement = doc.getRootElement();
			List defineList = rootElement.getChildren("process");
			for (int i = 0; i < defineList.size(); i++) {
				Element ele = (Element) defineList.get(i);
				String procName = ele.getAttribute("proc-name").getValue();
				ProcDefDTO dto = new ProcDefDTO(procName, ele.getAttribute(
						"proc-desc").getValue(), ele.getAttribute("class")
						.getValue(), ele.getAttribute("id").getValue(), ele.getAttribute("no").getValue());
				procDefList.add(dto);
				procDefMap.put(procName, dto);
			}

		} catch (Exception e) {

		}
	}

	/**
	 * 取得指定流程定义
	 * 
	 * @param procName
	 * @return
	 */
	public static ProcDefDTO getProcDefByName(String procName) {
		return (ProcDefDTO) procDefMap.get(procName);
	}
	/**
	 * 取得所有流程定义
	 * @param procName
	 * @return
	 */
	public static ProcDefDTO[] getProcDefs() {
		return (ProcDefDTO[]) procDefList.toArray(new ProcDefDTO[0]);
	}
	
	public static List<ProcDefDTO> getProcDefList() {
		//init();
		return new ArrayList<ProcDefDTO>(procDefList);
	}

}
