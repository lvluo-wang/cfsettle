<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
<tiles:putAttribute name="body">
	<table id="listAcctTranForChoose_dataTable"></table>
</tiles:putAttribute>

<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){
		var keys=["B023"];
		var code=new CodeUtil(keys);
		code.loadData();
		
		// 获取初始化组件所需的数据
		var frozenColumns=[[
		                    {field:'ck',checkbox:true,width:20},
		                    {title:'<s:text name="memberInfo.miNo"/>',field:'miNo',width:120,sortable:true}
		        		]];
		var columns=[[  
		  			{field:'tranNo',title:'<s:text name="AcctTran.tranNo"/>',width:120,sortable:true},
		  			{field:'tranName',title:'<s:text name="AcctTran.tranName"/>',width:120,sortable:true},
					{field:'pointId_eventNo',title:'<s:text name="AcctPoint.pointName"/>',width:200,
						formatter:function(value,rec){
							return "<span>"+rec.pointId_prodNo_prodName + code.getValue('B023',value)+"</span>";
						}
					}
				]];
		//初始化数据组件
		//var dataGrid=new initDataComponent("listAcctTranForChoose_dataTable",'<s:url value="/acctConfig/acctTranAction_queryData.jhtml"/>',frozenColumns,columns);
		var dataGrid = new DataPageComponent("listAcctTranForChoose_dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					singleSelect:true,
					url:'<s:url value="/acctConfig/acctTranAction_queryData.jhtml"/>',
					onDblClickRow:function(rowIndex, rowData){
						$('#tranItem_tranId').val(rowData.id);
						$('#tranItem_tranName').val(rowData.tranName);
						$('#acctTranSelect').window('close');
					}
				});
		dataGrid.load();
	
	});
		//工具栏具体实现方法
		
		function doSearch(){
			var parm=formToObject("queryForm");
			dataGrid.queryByParam(parm);
		}
	</script>
</tiles:putAttribute>
</tiles:insertDefinition>
