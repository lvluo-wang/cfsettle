<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">

<tiles:putAttribute name="tool">
	<x:button icon="icon-add" click="doAdd" text="add"/>
	<x:button icon="icon-edit" click="doEdit" text="edit"/>
	<x:button icon="icon-remove" click="doRemove" text="del"/>	
</tiles:putAttribute>
<tiles:putAttribute name="query">
		<form id="queryForm">
			<table><tr>
				<td><s:text name="AcctPoint.pointName"/>:</td>
				<td>
					<input id="q_acctItem_pointName" name="acctItem.pointName" class="inputSel easyui-validatebox" onclick="selectAcctPoint('q_acctItem_pointId','q_acctItem_pointName','acctPointSelect')"/>
					<input type=hidden id="q_acctItem_pointId" name="acctItem.pointId"/>
					<img src='<s:url value="/platform/static/images/icon_clear.PNG"/>' style="cursor:hand" onclick="clearComponentValue('q_acctItem_pointName:q_acctItem_pointId')"/>
				</td>
				<td><x:button icon="icon-search" click="doSearch" text="query"/></td>
			</tr></table>
		</form>
</tiles:putAttribute>
<tiles:putAttribute name="data">		
		<x:datagrid id="dataTable" autoload="false" form="queryForm" url="/acctConfig/acctCommonItem_queryData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="AcctItem.itemNo" field="itemNo"  width="120"/>
				<x:column title="AcctItem.itemName" field="itemName"  width="120"/>
				<x:column title="AcctItem.belongType" field="belongType"  width="120" formatter="belongType_column"/>
				<x:column title="AcctPoint.pointName" field="pointId_eventNo"  width="120" formatter="pointId_eventNo_column"/>
				<x:column title="AcctItem.valueType" field="valueType"  width="120" formatter="valueType_column"/>
				<x:column title="AcctItem.express" field="express"  width="120"/>
			</x:columns>
		</x:datagrid>	
</tiles:putAttribute>
<tiles:putAttribute name="window">		
	<div id="add_edit"   style="width:800px;display:none;"></div>
	<div id="acctPointSelect" style="width:800px;display:none;"></div>
	<div id="minoInfoSelect" style="width:800px;display:none;"></div>	
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
var keys=["B024","B023","B026"];
var code=new CodeUtil(keys);
	
	function initPage(){				
	   code.loadData();
	   dataTable.load();//数据分页标签加载远程数据
	}	
	
	function belongType_column(value){
		return "<span>"+code.getValue('B024',value)+"</span>";
	}
	function pointId_eventNo_column(value,field,row,index){
		return "<span>"+row.pointId_prodNo_prodName + code.getValue('B023',value)+"</span>";
	}
	function valueType_column(value){
		return "<span>"+code.getValue('B026',value)+"</span>";
	}
	//工具栏具体实现方法
	
	function doSearch(){
		dataTable.load();
	}
	
	function doAdd(){
		var url="<s:url value='/acctConfig/acctCommonItem_toCreateAcctItem.jhtml'/>?flag="+Math.random()*99999;
		url += "&acctItem.pointName="+$('#q_acctItem_pointName').val();
		url += "&acctItem.pointId="+$('#q_acctItem_pointId').val();
		requestAtWindow(url,"add_edit","<s:text name="AcctItem.addAcctItem"/>");
	}
	
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var url="<s:url value='/acctConfig/acctCommonItem_toEditAcctItem.jhtml'/>?flag="+Math.random()*99999;
			url += "&id="+getSelected(dataTable,"id");
			requestAtWindow(url,"add_edit",'<s:text name="AcctItem.editAcctItem"/>');
		}
	}
	
	function doRemove(){
		if(isSingleSelected(dataTable)){
			var cfmsg = "<s:text name="AcctPoint.deleteConfirm"/>";
			$.messager.confirm(global.alert,cfmsg, function(ok){
				if(ok){
					var ids=getSelected(dataTable,"id");
					var url = "<s:url value='/acctConfig/acctCommonItem_deleteAcctItem.jhtml'/>?id="+ids;
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
	
	function selectAcctPoint(id_, name_, div_){
		var url="<s:url value='/acctConfig/acctPointAction_listAcctPointForChoose.jhtml'/>?flag="+Math.random()*99999;
		url += "&AcctPointIdInputor="+id_+"&AcctPointNameInputor="+name_+"&ShowAcctPointDiv="+div_;
		requestAtWindow(url,"acctPointSelect","<s:text name="AcctPoint.selectAcctPoint"/>");
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>