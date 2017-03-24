<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertDefinition name="FUNC_TOOL_DATA">
<tiles:putAttribute name="end">
<script type="text/javascript">
$(function(){
	setBaseDir('<%=baseDir%>');
	var keys=["B023"];
	var code=new CodeUtil(keys);
	code.loadData();
	acctTranCode = code;
	
	// 获取初始化组件所需的数据
	var frozenColumns=[[
	                    {field:'ck',checkbox:true,width:20},
	                    {title:'<s:text name="AcctTranExpr.name"/>',field:'name',width:120,sortable:true}
	        		]];
	var columns=[[  
	  			{field:'express',title:'<s:text name="AcctTranExpr.express"/>',width:300,sortable:true}
			]];
	//初始化数据组件
	var dataGrid=new initDataComponent("dataTableForAcctTranExpr",'<s:url value="/acctConfig/acctTranAction_queryAcctTranExprData.jhtml"/>?id=${id}',frozenColumns,columns);
});
	//工具栏具体实现方法
	
	
	function doAddAcctTranExpr(){
		var url="<s:url value='/acctConfig/acctTranAction_toCreateAcctTranExpr.jhtml'/>?flag="+Math.random()*99999;
		url += "&id=${id}";
		requestAtWindow(url,"add_edit_for_acct_tran_expr","<s:text name="AcctTranExpr.addAcctTranExpr"/>");
	}
	
	function doRemoveAcctTranExpr(){
		if(isSingleSelected("dataTableForAcctTranExpr","id")){
			var cfmsg = "<s:text name="AcctPoint.deleteConfirm"/>";
			$.messager.confirm(global.alert,cfmsg, function(ok){
				if(ok){
					var ids=getSelected("dataTableForAcctTranExpr","id");
					var url = "<s:url value='/acctConfig/acctTranAction_deleteAcctTranExpr.jhtml'/>?id="+ids;
					requestAndRefresh(url,"dataTableForAcctTranExpr");
				}
			});
		}
	}
</script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onClick="doAddAcctTranExpr()"><s:text name="add"/></a>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onClick="doRemoveAcctTranExpr()"><s:text name="del"/></a>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
						<table id="dataTableForAcctTranExpr"></table>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="add_edit_for_acct_tran_expr" style="width:350px;display:none;">
	</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
