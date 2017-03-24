<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
	$(function(){	
		$("#bookTree").tree({
			checkbox: true,
			data:eval('('+$("#treeJSONInfo").attr("value")+')'),	
			onContextMenu:function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
				$("#beMenu").menu('show',{
					left:e.pageX,
					top:e.pageY
				});
				return false;
			},
			onDblClick:function(node){				
				
				return false;
			}

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
	
	function doSelect(){//选择
		var nodes = $('#bookTree').tree('getChecked');
		for(var i=0;i<nodes.length;i++){
			var node = nodes[i];
			if (node.id.length>5)
				addOption($("#"+$("#idDiv").val()),node.id, node.text);
		}
		
		doClose();
	}

	function doClose(){
		$('#'+$('#winId').val()).window('close');
	}
	
	
</script>
<div class="easyui-layout" fit="true" border="false">
	<div region="center" border="false" style="overflow:hidden;" id="dataMain">
		<div class="easyui-layout" fit="true" border="false">			
				<form method="post" id="submitForm" >					
				 	<s:hidden name="treeJSONInfo"></s:hidden>
				 	<s:hidden name="idDiv"></s:hidden>
				 	<s:hidden name="noDiv"></s:hidden>
				 	<s:hidden name="nameDiv"></s:hidden>
				 	<s:hidden name="winId"></s:hidden>
				</form>				
			<div id="dataView"  region="center" border="false" style="overflow:hidden;">
				<div class="easyui-layout" fit="true" border="false">
					<div region="north" style="padding-left:2px;padding-top:2px;background:#efefef;height:30px;"border="false" split="false">
						<a href="#" class="easyui-linkbutton" style="margin-bottom:2px;" iconCls="icon-save" plain="true" onClick="doSelect()"><s:text name="save"/></a>
						<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" onClick="doClose()"><s:text name="close"/></a>					
					</div>
					<div id="bookTreeView" region="center" title="书籍" split="true" style="width:250px;padding:1px;">
						<ul id="bookTree" class="easyui-tree"></ul>
					</div>
				</div>
			</div>
		</div>		
	</div>
	
	<div id="beMenu" class="easyui-menu" style="width:120px;display:none;">
		<div onclick="expandAll($('#bookTree'))"><s:text name="expandAll"/></div>
		<div onclick="collapseAll($('#bookTree'))"><s:text name="collapseAll"/></div>
		<div class="menu-sep"></div>
	</div>
</div>