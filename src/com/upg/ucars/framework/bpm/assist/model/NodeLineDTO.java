package com.upg.ucars.framework.bpm.assist.model;

/** 
 * 节点及连线
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class NodeLineDTO {
	/* C : org.jbpm.graph.node.ProcessState 
     D : org.jbpm.graph.node.Decision 
     E : org.jbpm.graph.node.EndState 
     F : org.jbpm.graph.node.Fork 
     J : org.jbpm.graph.node.Join 
     K : org.jbpm.graph.node.TaskNode 
     M : org.jbpm.graph.node.MailNode 
     N : org.jbpm.graph.def.Node 
     R : org.jbpm.graph.node.StartState 
     S : org.jbpm.graph.node.State 
     U : org.jbpm.graph.def.SuperState */
	
	/**类型--开始结点*/
	public final static String Type_Start = "R";
	/**类型--状态结点*/
	public final static String Type_State = "S";
	/**类型--判断结点*/
	public final static String Type_Decision = "D";
	/**类型--自动结点*/
	public final static String Type_Node = "N";
	/**类型--任务结点*/
	public final static String Type_TaskNode = "K";
	/**类型--分支结点*/
	public final static String Type_Fork = "F";
	/**类型--合并结点*/
	public final static String Type_Join = "J";
	/**类型--连线*/
	public final static String Type_Line = "L";
	/**类型--节点结点*/
	public final static String Type_End = "E";
	
	private Long id;
	private String name;//名称
	private String desc;//描述
	private String type;//类型  NodeLineDTO.Type_*
	
	public NodeLineDTO(){};	
		
	public NodeLineDTO(Long id, String name, String desc, String type) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}

 
