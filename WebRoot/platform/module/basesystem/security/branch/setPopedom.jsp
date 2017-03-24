<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
<tiles:putAttribute name="body">
	
	<div class="func_flow_area">
		<div id="step">
			<span id="step_list">
				<span  class="step_first"><s:text name="branchManagement"/></span>
				<span  class="step_normal step_current"><s:text name="branchFuncSet"/></span>
			</span>
			<span id="step_tool">
				<x:button iconCls="icon-back" onClick="step_return()" text="return"/>
			</span>
		</div>
	</div>
	
	
		<div class="func_form_area">
			<table class="detail_table">
				<tr>
					<td class="detail_head_title">
					<s:text name="titile_function_authorize_un"/>
					</td>
					<td class="detail_head_tool" >
						<x:button icon="icon-brch" click="changeSubsysMode" text="子系统模式"/>
						<x:button icon="icon-user" click="changeFuncMode" text="权限模式"/>
						<x:button icon="icon-add" click="moveToRight" text="添加权限"/>
					</td>
					<td class="detail_head_title" style="width:300px;"><s:text name="titile_function_authorize_on"/></td>
					<td class="detail_head_tool">
					<x:button icon="icon-cancel" click="moveToLeft" text="取消权限"/>
					</td>
				</tr>
				<tr>
					<td class="detail_td" colspan="2" >
					   <div id="unFuncTreeView" style="height:400px;overflow:auto;"><ul id="unFuncTree" class="easyui-tree"></ul></div>
					</td>
					<td class="detail_td"  colspan="2">
					<div style="height:400px;overflow:auto;"><ul id="beFuncTree" class="easyui-tree"></ul></div>
					</td>
				</tr>
			</table>
	</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
var brchId='${brch.brchId}';
var isSaas='${isSaas}';
var funcFlag=true;
$(function(){
	if(isSaas=='0'){
		$("#subMode").hide();
		$("#funcMode").hide();
	}
	doPost('<s:url value="/security/brch_brchFuncTree.jhtml"/>',{"brch.brchId":brchId},function(result){
		initTwoTree(result);
	});
});
	function initTwoTree(result){
		if(result){
			
				var obj=str2obj(result);
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
		var nodes=$('#unFuncTree').tree('getCheckedAndParent');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		if(nodes.length>0){
			if(funcFlag){
				doPost('<s:url value="/security/brch_addFunc.jhtml"/>',
					{"funcIds":s,"brch.brchId":brchId},
					function(result){
						initTwoTree(result);
					}
				);
			}else{
				var nodes=$("#subsysTree").tree('getCheckedAndParent');
				var s2 = '';
				for(var i=0; i<nodes.length; i++){
					if (s2 != '') s2 += ',';
					s2 += nodes[i].id;
				}
				doPost('<s:url value="/security/brch_addFuncBySys.jhtml"/>',
					{"funcIds":s,"subsysIds":s2,"brch.brchId":brchId},
					function(ret){
						initTwoTree(ret);
				});
			}
		}
	}
	
	function moveToLeft(){
		var rightTree = $('#beFuncTree');
		var nodes=rightTree.tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		if(nodes.length>0){
			if(funcFlag){
				doPost('<s:url value="/security/brch_delFunc.jhtml"/>',
					{"funcIds":s,"brch.brchId":brchId},
					function(result){
						initTwoTree(result);
					}
				);
			}else{
				var nodes=$("#subsysTree").tree('getChecked');
				var s2 = '';
				for(var i=0; i<nodes.length; i++){
					if (s2 != '') s2 += ',';
					s2 += nodes[i].id;
				}
				doPost('<s:url value="/security/brch_delFuncBySys.jhtml"/>',
					{"funcIds":s,"subsysIds":s2,"brch.brchId":brchId},
					function(ret){
						initTwoTree(ret);
				});
			}
			
		}

	}
	function changeSubsysMode(){
		
		requestAtDom('unFuncTreeView','<s:url value="/security/brch_toChangeSubsysMode.jhtml"/>');
	}
	function changeFuncMode(){
		
		doPost('<s:url value="/security/brch_brchFuncTree.jhtml"/>',{"brch.brchId":brchId},function(result){
			if(result){
			
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
					return;
				}
				var t=$('<ul id="unFuncTree" class="easyui-tree"></ul>');
				t.tree({
						checkbox:true
					}).tree('loadData',obj.unList);
				$("#unFuncTreeView").html(t);
				funcFlag=true;	
				
			}
		});
	}
	function step_return(){
		var url = "<s:url value='/security/brch_list.jhtml'/>";	
		redirectUrl(url);
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>