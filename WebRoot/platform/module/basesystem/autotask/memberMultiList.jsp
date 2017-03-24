<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">
<tiles:putAttribute name="tool">
						
		<x:button iconCls="icon-save" onClick="doSelect()" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doChooseMemberClose()" text="close"/>
		<form method="post" id="submitForm" >
			<div  title="任务" border="true">
				<s:hidden name="treeJSONInfo1"></s:hidden>
			</div>									
		 	
		</form>	
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<ul id="memberTree" class="easyui-tree"></ul>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		$(function(){	
			$("#memberTree").tree({
				checkbox: true,
				data:eval('('+$("#treeJSONInfo1").attr("value")+')'),	
				onContextMenu:function(e,node){
					
					return false;
				},
				onDblClick:function(node){				
					
					return false;
				}
		
			});
			
		});
		
		function doSelect(){//选择
			var nodes = $('#memberTree').tree('getChecked');
			for(var i=0;i<nodes.length;i++){
				var node = nodes[i];
				addOption($("#<s:property value='idDiv'/>"),node.id, node.text);
			}
			
			doChooseMemberClose();
		}
		
		function doChooseMemberClose(){
			$("#<s:property value='winId'/>").window('close');
		}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>