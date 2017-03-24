package com.upg.ucars.framework.bpm.assist.model;

import java.util.ArrayList;
import java.util.List;

/** 
 * 当前结点信息
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class CurrentNodeInfo extends NodeLineDTO{
		
	private List<NodeLineDTO> lines = new ArrayList<NodeLineDTO>(1);
	
	private List<NodeLineDTO> toNodes = new ArrayList<NodeLineDTO>(1);
	
	public CurrentNodeInfo() {
	}
	
	public CurrentNodeInfo(Long id, String name, String desc, String type) {		
		super(id, name, desc, type);
	}
	
	public void addLineAndToNode(NodeLineDTO line, NodeLineDTO toNode){
		lines.add(line);
		toNodes.add(toNode);
	}
	public int getSize(){
		return lines.size();
	}

	public List<NodeLineDTO> getLines() {
		return lines;
	}

	public void setLines(List<NodeLineDTO> lines) {
		this.lines = lines;
	}

	public List<NodeLineDTO> getToNodes() {
		return toNodes;
	}

	public void setToNodes(List<NodeLineDTO> toNodes) {
		this.toNodes = toNodes;
	}
	
	

}

 