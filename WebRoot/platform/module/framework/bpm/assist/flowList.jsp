<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" language="javascript">
	
	function signal(nodeType, name){			
			var lineName=name;
			if (lineName==null || lineName=="")lineName="默认";
			if (nodeType=="F"){
				alert("当前节点为分支节点，不允许触发流转。");
				return;
			}
			
			if (confirm("该操作将触发流程沿["+lineName+"]连线流转，确认吗？")){
				if (confirm("是否已经设置好上下文变量，请确认？")){
					var nl = name==null?"":name;
					var pid = '<s:property value="processInstanceId"/>';
					var info = "id="+pid+"&lineName="+nl;
					var url = "<s:url value='/bpm/activity_signal.jhtml'/>";
					$.ajax({
						   type: "POST",
						   url: url,
						   data: info,
						   success: function(msg){
							   $('#flow').window('close');
							   doActivity();
						   }
						}); 
					
				}				
				
		    }  
	}
</script>

<fieldset>
<legend><FONT color="blue">流程当前所在节点</FONT></legend>
	<div align="center">	
			<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#99FF99;" >	
					<s:if test="curNodeInfo.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Decision">	
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(判断节点)</FONT>	
					</s:if>
					<s:elseif test="curNodeInfo.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Node">	
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(自动节点)</FONT>
					</s:elseif>
					<s:elseif test="curNodeInfo.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_TaskNode">	
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(任务节点)</FONT>	
					</s:elseif>	
					<s:elseif test="curNodeInfo.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_State">
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(状态节点)</FONT>		
					</s:elseif>
					<s:elseif test="curNodeInfo.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Start">
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(开始节点)</FONT>	
					</s:elseif>
					<s:elseif test="curNodeInfo.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_End">	
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(结束节点)</FONT>	
					</s:elseif>	
					<s:else>
						<FONT color="red"><s:property  value="curNodeInfo.name"/><br><s:property  value="curNodeInfo.desc"/><br>(其他节点)</FONT>
					</s:else>
											
				</div>
		<table cellspacing="0" cellpadding="0">
			
			<s:if test="curNodeInfo.size>0">				
					
				<s:iterator value="curNodeInfo.lines" status="sta">					
					<s:set name="toNode" value="curNodeInfo.toNodes[#sta.index]"></s:set>	
									
					<tr>
					<td width="15%">&nbsp;</td>
					<td width="15%">&nbsp;</td>					
					<td width="20%">&nbsp;</td>
					<td width="15%" align="right">
							<div style="border:1px solid blue;width:3px;height:20px;background:#006600;" >													
							</div>	
							<s:if test="#sta.index==(curNodeInfo.size-1)">
							<div style="border:0px solid blue;width:3px;height:20px;background:#FFFFFF;" >													
							</div>	
							</s:if>
							<s:else>
								<div style="border:1px solid blue;width:3px;height:20px;background:#006600;" >													
								</div>	
							</s:else>								
						</td>
					<td  width="25%" align="left">
						<div align="center">
							<FONT color="blue">连线名</FONT>							
						</div>
						<div align="center" style="border:1px solid blue;width:150px;height:3px;background:#006600;" >
								<FONT></FONT>				
						</div>
						<s:if test="isCenter==1">
							<div  align="center">
								<a href="#" ondblclick="signal('<s:property value='curNodeInfo.type'/>','<s:property value='name'/>');" >
								<FONT color="blue"><s:if test="name!=null">[<s:property value="name"/>]</s:if><s:else>[默认]</s:else></FONT>
								</a>							
							</div>
						</s:if>
					</td>			
					<s:if test="#toNode.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Node">			
						<td align="center">	
							<div align="center" style="border:1px solid black;width:110px;height:30px;background:#C0C0C0;" >							
								<FONT color="red"><s:property  value="#toNode.name"/><br><s:property  value="#toNode.desc"/><br>(自动节点)</FONT>			
							</div>		
						</td>								
					</s:if>
					<s:elseif test="#toNode.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_TaskNode">
						<td align="center">	
							<div align="center" style="border:1px solid black;width:110px;height:30px;background:#C0C0C0;" >							
								<FONT color="black"><s:property  value="#toNode.name"/><br><s:property  value="#toNode.desc"/><br>(任务节点)</FONT>			
							</div>				
						</td>							
					</s:elseif>
					<s:elseif test="#toNode.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Decision">
						<td align="center">	
							<div align="center" style="border:1px solid black;width:110px;height:30px;background:#C0C0C0;" >							
								<FONT color="black"><s:property  value="#toNode.name"/><br><s:property  value="#toNode.desc"/><br>(判断节点)</FONT>			
							</div>				
						</td>							
					</s:elseif>
					<s:elseif test="#toNode.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_State">
						<td align="center">	
							<div align="center" style="border:1px solid black;width:110px;height:30px;background:#C0C0C0;" >							
								<FONT color="black"><s:property  value="#toNode.name"/><br><s:property  value="#toNode.desc"/><br>(状态节点)</FONT>			
							</div>				
						</td>							
					</s:elseif>					
					<s:elseif test="#toNode.type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_End">
						<td align="center">	
							<div align="center" style="border:1px solid black;width:110px;height:30px;background:#C0C0C0;" >							
								<FONT color="black"><s:property  value="#toNode.name"/><br><s:property  value="#toNode.desc"/><br>(结束节点)</FONT>			
							</div>				
						</td>							
					</s:elseif>
					<s:else>
						<td align="center">	
							<div align="center" style="border:1px solid black;width:110px;height:30px;background:#C0C0C0;" >							
								<FONT color="black"><s:property  value="#toNode.name"/><br><s:property  value="#toNode.desc"/><br>(其他节点)</FONT>			
							</div>				
						</td>							
					</s:else>	
					
					<td width="15%">&nbsp;</td>	
				</tr>		
			   </s:iterator>	
			
			</s:if>	
			
			</table>			
			
	</div>
</fieldset>

<div align="left">	
	
	<ul>● <font color="green"><b>绿色框</font></b>为流程当前所处节点，<font color="black"><b>黑色框</b></font>为流程可能到达的下一节点。</ul>
	<ul>● 双击连线名，可触发流程从当前节点流转到对应的下一节点。注意：流转前，请预先设置好上下文变量。</ul>
	
</div>
<!-- 
<div align="center">	
	<br/>
	<legend><FONT color="blue" size="4">流程流转过程 </FONT></legend>
	<br/>
</div>
<table cellspacing="0" cellpadding="0">
			
	<s:iterator value="nodeLineList" status="stats"> 
	   <s:if test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Line">	 
	   		<tr><td align="center">
				<div align="center" style="border:1px solid blue;width:3px;height:50px;background:#006600;" >							
				</div>			
			</td>
			<td align="left"><FONT color="blue"><s:property value="name"/></FONT></td>
			</tr> 	
		</s:if>
		<s:elseif test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Node">			
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br><s:property  value="desc"/><br>(自动节点)</FONT>			
				</div>		
			</td>
			<td></td>
			</tr>		
		</s:elseif>
		<s:elseif test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_TaskNode">
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br><s:property  value="desc"/><br>(任务节点)</FONT>			
				</div>				
			</td>
			<td></td>
			</tr>	
		</s:elseif>
		<s:elseif test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Decision">
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br><s:property  value="desc"/><br>(判断节点)</FONT>			
				</div>				
			</td>
			<td></td>
			</tr>	
		</s:elseif>
		<s:elseif test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_State">
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br><s:property  value="desc"/><br>(状态节点)</FONT>			
				</div>				
			</td>
			<td></td>
			</tr>	
		</s:elseif>
		<s:elseif test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_Start">
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br><s:property  value="desc"/><br>(开始节点)</FONT>			
				</div>				
			</td>
			<td></td>
			</tr>	
		</s:elseif>
		<s:elseif test="type==@com.upg.ucars.framework.bpm.assist.model.NodeLineDTO@Type_End">
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br><s:property  value="desc"/><br>(结束节点)</FONT>			
				</div>				
			</td>
			<td></td>
			</tr>	
		</s:elseif>
		<s:else>
			<tr><td align="center">	
				<div align="center" style="border:1px solid blue;width:110px;height:30px;background:#663300;" >							
					<FONT color="red"><s:property  value="name"/><br>(其他节点)</FONT>			
				</div>				
			</td>
			<td></td>
			</tr>	
		</s:else>		
				
    </s:iterator>
	
</table>
 -->
<s:form name="pageForm" action="#" theme="simple">	
	
</s:form>

