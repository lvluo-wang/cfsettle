<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button icon="icon-add" click="doAdd" text="add"/>
		<x:button icon="icon-edit" click="doEdit" text="edit"/>
	    <x:button icon="icon-remove" click="doRemove" text="del"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" url="/acctConfig/acctRecordBodyAction_queryData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="memberInfo.miNo" field="miNo"  width="120"/>
				<x:column title="AcctRecordBody.groupNo" field="groupNo"  width="120"/>
				<x:column title="AcctRecordBody.serialNo" field="serialNo"  width="120"/>
				<x:column title="name" field="name"  width="120"/>
				<x:column title="AcctRecordBody.valueType" field="valueType"  width="120" formatter="valueType_column"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit" style="width:600px;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			var code;
			function initPage(){
				var keys=["B026"];
				code=new CodeUtil(keys);
				code.loadData();
				dataTable.load();
			}
			
			function valueType_column(value){
				return "<span>"+code.getValue('B026',value)+"</span>";
			}
			
			//工具栏具体实现方法		
			function doAdd(){
				var url="<s:url value='/acctConfig/acctRecordBodyAction_toCreateAcctRecordBody.jhtml'/>?flag="+Math.random()*99999;
				requestAtWindow(url,"add_edit","<s:text name="AcctRecordBody.addAcctRecordBody"/>");		
			}
			
			function doEdit(){
				if(isSingleSelected(dataTable)){
					var url="<s:url value='/acctConfig/acctRecordBodyAction_toEditAcctRecordBody.jhtml'/>?flag="+Math.random()*99999;
					url += "&id="+getSelected(dataTable,"id");
					requestAtWindow(url,"add_edit","<s:text name="AcctRecordBody.editAcctRecordBody"/>");
				}
			}
				
			function doRemove(){
				if(isSingleSelected(dataTable)){
					var cfmsg = "<s:text name="AcctRecordBody.deleteConfirm"/>";
					$.messager.confirm(global.alert,cfmsg, function(ok){
						if(ok){
							var ids=getSelected(dataTable,"id");
							var url = "<s:url value='/acctConfig/acctRecordBodyAction_deleteAcctRecordBody.jhtml'/>?id="+ids;
							//requestAndRefresh(url,"dataTable");
							doPost(url,null,function(){								
								if(dataTable){
									dataTable.refresh();
								}
							});
						}
					});
				}
			}
		</script>
	</tiles:putAttribute>

</tiles:insertDefinition>