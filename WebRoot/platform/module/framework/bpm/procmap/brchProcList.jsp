<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">
   <tiles:putAttribute name="tool">
		<x:button iconCls="icon-save" onClick="doSave()" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doCancel()" text="cancel"/>
		<form method="post" id="submitForm" >
			<s:hidden name="brchId"></s:hidden>
		 	<s:hidden name="treeJSONInfo"></s:hidden>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<div  style="height:300px;overflow:auto;">
		<ul id="procTree" class="easyui-tree"></ul>	
	</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){	
		$("#procTree").tree({
			checkbox: true,
			data:eval('('+$("#treeJSONInfo").attr("value")+')')	
			
		});
		
	});

	function collapseAll(thisTree){
		var node = thisTree.tree('getSelected');
		if (node){
			thisTree.tree('collapseAll', node.target);
		} else {
			thisTree.tree('collapseAll');
		}
	}
	function expandAll(thisTree){
		var node = thisTree.tree('getSelected');
		if (node){
			thisTree.tree('expandAll', node.target);
		} else {
			thisTree.tree('expandAll');
		}
	}

		
		//获取树内所有节点ID,逗号分隔。
		function getTreeCheckedNodeId(treeId){		
			var nodes = $('#'+treeId).tree('getChecked');		
			var s='';
			if(nodes!=null){
				for(var i=0;i<nodes.length;i++){				
					if(s!=''){
						s+=',';
					}
					s+=nodes[i].id;
				}
			}
			return s;
		}

		function doSave(){
			var beIds = getTreeCheckedNodeId("procTree");
			
			$.messager.confirm(global.alert,"<s:text name="prompt_save_set_sysfunc"/>", function(r){
				if(r){
					var url = "<s:url value='/bpm/procmap_saveBrchProc.jhtml'/>?ids="+beIds;
					formSubmitForDatagrid(url,"submitForm","dataTable");
					$('#brch_proc').window('close');
				}
				
			});
		}
		
		function doCancel(){
			$('#brch_proc').window('close');
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>