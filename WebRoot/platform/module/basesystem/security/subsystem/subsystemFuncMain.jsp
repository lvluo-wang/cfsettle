<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
<tiles:putAttribute name="body">
<div class="func_flow_area">
	<div id="step">
		<span id="step_list">
			<SPAN id="step1" class="step_first"><s:text name="subsystem.definition"/></SPAN>
            <SPAN id="step2" class="step_nomal step_current"><s:text name="set"/><s:text name="sysfunc"/></SPAN>
		</span>
		<span id="step_tool">
			<x:button iconCls="icon-back" onClick="step_return()" text="return"/>
		</span>
	</div>
</div>

<div class="func_form_area">
	<table class="detail_table">
		<tr>
			<td class="detail_head_title" style="width:200px;">
				<s:text name="titile_function_authorize_un"/>
			</td>
			<td class="detail_head_tool" style="width:100px;">
				<x:button icon="icon-add" click="moveToRight" text="assign"/>
			</td>
			<td class="detail_head_title" style="width:300px;"><s:text name="subsystem"/>:${subsystem.subsysName}--(<s:text name="titile_function_authorize_on"/>)</td>
			<td class="detail_head_tool">
				<x:button icon="icon-cancel" click="moveToLeft" text="cancel,assign"/>
			</td>
		</tr>
		<tr>
			<td class="detail_td" colspan="2"style="width:300px;" >
			   <div id="unFuncTreeView" style="height:400px; width:300px;overflow:auto;"><ul id="unFuncTree" class="easyui-tree"></ul></div>
			</td>
			<td class="detail_td"  colspan="2">
				<div id="beFuncTreeView" style="height:400px;overflow:auto;"><ul id="beFuncTree" class="easyui-tree"></ul></div>
			</td>
		</tr>
	</table>
</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
var subsysId='${subsystem.subsysId}';
$(function(){
	doPost('<s:url value="/security/subsystem_funcTree.jhtml"/>',{"subsystem.subsysId":subsysId},function(result){
		initTwoTree(result);
	});
});

function initTwoTree(result){
	if(result){
		var obj = str2obj(result);
		if(obj.error){
			error(obj.error);
		}else{	
			$("#unFuncTree").tree({
				checkbox:true
			}).tree('loadData',obj.unList);
			
			$("#beFuncTree").tree({
				checkbox:true
			}).tree('loadData',obj.beList);
		}
	}
}

function moveToRight(){
	var nodes = $('#unFuncTree').tree('getCheckedAndParent');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	
	if(nodes.length > 0){
		doPost('<s:url value="/security/subsystem_addFunc.jhtml"/>',
			{"funcIds":s,"subsystem.subsysId":subsysId},
			function(result){
				initTwoTree(result);
			}
		);
	}
	
}

function moveToLeft(){
	var rightTree = $('#beFuncTree');
	var nodes = rightTree.tree('getChecked');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	if(nodes.length>0){
		doPost('<s:url value="/security/subsystem_deleteFunc.jhtml"/>',
			{"funcIds":s,"subsystem.subsysId":subsysId},
			function(result){
				initTwoTree(result);
			}
		);
	}

}

function step_return(){
	var url = "<s:url value='/security/subsystem_mainPage.jhtml'/>";	
	redirectUrl(url);
}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>