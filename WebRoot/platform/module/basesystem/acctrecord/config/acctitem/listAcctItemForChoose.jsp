<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_BLANK">
<tiles:putAttribute name="body">
	<table id="listAcctItemForChoose_dataTable"></table>
</tiles:putAttribute>

<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){
	
		var keys=["B024","B023","B025"];
		var code=new CodeUtil(keys);
		code.loadData();
		
		// 获取初始化组件所需的数据
		var frozenColumns=[[
		                    {field:'ck',checkbox:true,width:20},
		                    {title:'<s:text name="AcctItem.itemNo"/>',field:'itemNo',width:120,sortable:true}
		        		]];
		var columns=[[  
		  			{field:'itemName',title:'<s:text name="AcctItem.itemName"/>',width:120,sortable:true},
					{field:'belongType',title:'<s:text name="AcctItem.belongType"/>',width:120,align:'center',
						formatter:function(value,rec){
							return "<span>"+code.getValue('B024',value)+"</span>";
						}
					},
					{field:'miNo',title:'<s:text name="memberInfo.miNo"/>',width:120,align:'center'},
					{field:'pointId_eventNo',title:'<s:text name="AcctPoint.pointName"/>',width:120,
						formatter:function(value,rec){
							return "<span>"+rec.pointId_prodNo_prodName + code.getValue('B023',value)+"</span>";
						}
					},
					{field:'valueType',title:'<s:text name="AcctItem.valueType"/>',width:120,align:'center',
						formatter:function(value,rec){
							return "<span>"+code.getValue('B025',value)+"</span>";
						}
					},
					{field:'express',title:'<s:text name="AcctItem.express"/>',width:250}
				]];
		//初始化数据组件
		//var dataGrid=new initDataComponent("listAcctItemForChoose_dataTable",'<s:url value="/acctConfig/acctItemAction_queryData.jhtml"/>',frozenColumns,columns);
		var myurl = "<s:url value="/acctConfig/acctItemAction_queryData.jhtml"/>";
		if(addTranItem_showValue_valueType != null){
			myurl += "?acctItem.valueType="+addTranItem_showValue_valueType;
			myurl += "&acctRecordBody.valueType="+addTranItem_showValue_valueType;
		}
		var dataGrid = new DataPageComponent("listAcctItemForChoose_dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					singleSelect:true,
					url:myurl,
					onDblClickRow:function(rowIndex, rowData){
						$('#tranItem_acctItemId').val(rowData.id);
						$('#tranItem_acctItemName').val(rowData.itemName);
						addTranItem_showValue_valueType = rowData.valueType;
						addTranItem_showValue_express = rowData.express;
						addTranItem_showValue("acctItem");
						$('#acctItemSelect').window('close');
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