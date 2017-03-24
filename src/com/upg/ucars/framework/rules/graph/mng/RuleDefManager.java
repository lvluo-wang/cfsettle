package com.upg.ucars.framework.rules.graph.mng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.upg.ucars.framework.rules.graph.def.Parameter;
import com.upg.ucars.framework.rules.graph.def.Rule;
import com.upg.ucars.framework.rules.graph.def.RuleNode;
import com.upg.ucars.util.XmlUtil;

/**
 * 规则定义管理器 
 *
 * @author shentuwy
 */
public class RuleDefManager {
	
	private static HashMap<String, RuleNode> rulesMap = new HashMap<String, RuleNode>(20);
	
	private static String RES_CONFIG = "rules-config.xml";
	private static List<Document> pathList=new ArrayList<Document>();
	
	static {
		initConfig();
		
		for (Document doc : pathList) {
			paserConfig(doc);
		}
		
	}
	/**
	 * 读取配置文件内容 
	 *
	 * @param doc 解析了的配置文件
	 */
	private static void paserConfig(Document doc) {
		
			Element rootElement = doc.getRootElement();
			
			//规则
			Iterator rulesIt = rootElement.getChildren("rule-node").iterator();
			while (rulesIt.hasNext()){
				Element rulesEle = (Element)rulesIt.next();
				String name = rulesEle.getAttribute("name").getValue();
				RuleNode rules = new RuleNode();
				rules.setName(name);
				
				Iterator ruleIt = rulesEle.getChildren("rule").iterator();
				while (ruleIt.hasNext()){
					Rule rule = new Rule();
					
					Element ruleEle = (Element)ruleIt.next();
					Attribute nameEle = ruleEle.getAttribute("name");
					String ruleName="nonName";
					if (nameEle!=null)
						ruleName = nameEle.getValue();
					String level ="1";
					if (ruleEle.getAttribute("level")!=null)
						level = ruleEle.getAttribute("level").getValue();
					Attribute clzAttr = ruleEle.getAttribute("class");		
					if (clzAttr!=null){//
						String clz = clzAttr.getValue();
						rule.setRuleClass(clz);
					}else{//
						{
							Element conditionEle = ruleEle.getChild("condition");
							Attribute clzAttr1 = conditionEle.getAttribute("class");	
							if (clzAttr1!=null){//
								String clz = conditionEle.getAttribute("class").getValue();
								rule.setConditionClass(clz);
							}							
							rule.setConditionExpr(conditionEle.getText());
						}
						{
							Element executeEle = ruleEle.getChild("execute");
							Attribute clzAttr1 = executeEle.getAttribute("class");	
							if (clzAttr1!=null){//
								String clz = executeEle.getAttribute("class").getValue();
								rule.setExecuteClass(clz);
							}							
							rule.setExecuteExpr(executeEle.getText());
						}						
					}
					//include-member
					Attribute inMbEle = ruleEle.getAttribute("include-member");		
					if (inMbEle!=null){//
						String inMb = inMbEle.getValue();
						rule.setIncludeMember(inMb);
					}
					//exclude-member
					Attribute exMbEle = ruleEle.getAttribute("exclude-member");		
					if (exMbEle!=null){//
						String exMb = exMbEle.getValue();
						rule.setExcludeMember(exMb);
					}
					
					rule.setName(ruleName);
					
					rule.setLevel(Integer.parseInt(level));
					
					rules.addRule(rule);
				}
				//参数
				Iterator paraIt = rulesEle.getChildren("para").iterator();
				while (paraIt.hasNext()){
					Parameter para = new Parameter();
					Element paraEle = (Element)paraIt.next();
					String paraName = paraEle.getAttribute("name").getValue();
					Attribute clzAttr = paraEle.getAttribute("class");	
					if (clzAttr!=null){//
						String clz = paraEle.getAttribute("class").getValue();
						para.setParaClass(clz);
					}
					para.setName(paraName);
					para.setParaExpr(paraEle.getText());	
					rules.addPara(para);
					
				}
				//import
				Iterator importIt = rulesEle.getChildren("import").iterator();
				while (importIt.hasNext()){					
					Element importEle = (Element)importIt.next();					
					rules.setImportExpr(importEle.getText());
					break;//只取第一个结节
				}
				
				rulesMap.put(name, rules);				
				
			}
	}
	
	private static void initConfig(){
		Document doc = null;
		try {
			doc = XmlUtil.parseXmlDoc(RES_CONFIG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pathList.add(doc);//解析文件
		
		Element root=doc.getRootElement();
		List resList=root.getChildren("include");
		for(int i=0;i<resList.size();i++){
			Element resElement=(Element)resList.get(i);
			String path=resElement.getAttribute("path").getValue();
			Document doc1 = null;
			try {
				doc1 = XmlUtil.parseXmlDoc(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pathList.add(doc1);
		}
	}
	
	/**
	 * 获取规则点的定义 
	 *
	 * @param name
	 * @return
	 */
	public static RuleNode getRules(String name){
		return (RuleNode)rulesMap.get(name);
	}
	
}
