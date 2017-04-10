<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_DATA">
   <tiles:putAttribute name="tool">
		<x:button click="doSave" text="save"  icon="icon-save"/>
		<x:button click="doCancel" text="cancel"  icon="icon-cancel"/>
		<form method="post" id="submitForm" >
			<s:hidden name="custIds"></s:hidden>
			<s:hidden name="treeJSONInfo"></s:hidden>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<div style="height:200px;overflow:auto;">
		<ul id="roleTree" class="easyui-tree"></ul>
		</div>
    </tiles:putAttribute>
 <tiles:putAttribute name="end">
	<script type="text/javascript">
		$(function(){	
			$("#roleTree").tree({
				checkbox: true,
				data:eval('('+$("#treeJSONInfo").attr("value")+')')
			});
		});
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
			var beIds = getTreeCheckedNodeId("roleTree");
            var nodes = $('#roleTree').tree('getChecked');
            if(nodes!=null) {
                if (nodes.length > 1) {
                    info("请单选")
                    return;
                }
            }
			var userId = $("#custIds").attr("value");
			$.messager.confirm(global.alert,"确认分配吗？", function(r){
				if(r){
					var url = "<s:url value='/assign/assignManage_doAssign.jhtml'/>?selectBuser="+beIds;
					doPost(url,{"custIds":userId},function(result){
						if(result){
							var o=str2obj(result);
							if(o.error){
								error(o.error);
								return;
							}
						}
                        custAssignDataTable.refresh();
                        dataTable.refresh();
                        $('#selectWin').window('close');
					});
				}
			});
		}
		
		function doCancel(){
			$('#selectWin').window('close');
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>