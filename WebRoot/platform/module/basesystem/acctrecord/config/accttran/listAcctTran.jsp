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
		<x:datagrid id="dataTable" autoload="false" url="/acctConfig/acctTranAction_queryData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="memberInfo.miNo" field="miNo"  width="120"/>
				<x:column title="AcctTran.tranNo" field="tranNo"  width="120"/>
				<x:column title="AcctTran.tranName" field="tranName"  width="120"/>
				<x:column title="AcctPoint.pointName" field="pointId_eventNo"  width="180"  formatter="pointName_column"/>
				<x:column title="表达式" field="express"  width="300"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"  style="width:500px;display:none;"></div>
		<div id="acctPointSelect"  style="width:720px;display:none"></div>		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var acctTranCode;	
	function initPage(){		
		var keys=["B023"];
		var code=new CodeUtil(keys);
		code.loadData();
		acctTranCode = code;
		dataTable.load();
	}
	
	function pointName_column(value,field,row,index){
		return "<span>"+row.pointId_prodNo_prodName + acctTranCode.getValue('B023',value)+"</span>";
	}
		
		//工具栏具体实现方法
		function doAdd(){
			var url="<s:url value='/acctConfig/acctTranAction_toCreateAcctTran.jhtml'/>?flag="+Math.random()*99999;
			requestAtWindow(url,"add_edit","<s:text name="AcctTran.addAcctTran"/>");
		}
		
		function doEdit(){
			if(isSingleSelected(dataTable)){
				var url="<s:url value='/acctConfig/acctTranAction_toEditAcctTran.jhtml'/>?flag="+Math.random()*99999;
				url += "&id="+getSelected(dataTable,"id");
				requestAtWindow(url,"add_edit","<s:text name="edit"/>");
			}
		}
		
		function doRemove(){
			if(isSingleSelected(dataTable)){
				var cfmsg = "<s:text name="AcctPoint.deleteConfirm"/>";
				$.messager.confirm(global.alert,cfmsg, function(ok){
					if(ok){
						var ids=getSelected(dataTable,"id");
						var url = "<s:url value='/acctConfig/acctTranAction_deleteAcctTran.jhtml'/>?id="+ids;
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
		
		function listExpr(){
			if(isSingleSelected(dataTable)){
				var ids=getSelected(dataTable,"id");
				var url = "<s:url value='/acctConfig/acctTranAction_listAcctTranExpr.jhtml'/>?id="+ids;
				requestAtWindow(url,"manageAcctTranExpr","<s:text name="AcctTranExpr.manageAcctTranExpr"/>");
			}
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
