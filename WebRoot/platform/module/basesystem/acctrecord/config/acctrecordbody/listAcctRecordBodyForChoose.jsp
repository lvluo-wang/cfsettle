<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_BLANK">
<tiles:putAttribute name="body">
	<table id="listAcctRecordBodyForChoose_dataTable"></table>
</tiles:putAttribute>

<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){
		var keys=["B025","B026"];
		var code=new CodeUtil(keys);
		code.loadData();
		
		// 获取初始化组件所需的数据
		var frozenColumns=[[
		                    {field:'ck',checkbox:true,width:20},
		                    {title:'<s:text name="memberInfo.miNo"/>',field:'miNo',width:120,sortable:true}
		        		]];
		var columns=[[  
		  			{field:'groupNo',title:'<s:text name="AcctRecordBody.groupNo"/>',width:120,sortable:true},
					{field:'serialNo',title:'<s:text name="AcctRecordBody.serialNo"/>',width:120},
					{field:'name',title:'<s:text name="name"/>',width:120},
					{field:'recordType',title:'<s:text name="AcctRecordBody.recordType"/>',width:120,align:'center',
						formatter:function(value,rec){
							return "<span>"+code.getValue('B026',value)+"</span>";
						}
					},
					{field:'valueType',title:'<s:text name="AcctRecordBody.valueType"/>',width:120,align:'center',
						formatter:function(value,rec){
							return "<span>"+code.getValue('B025',value)+"</span>";
						}
					}
				]];
		//初始化数据组件
		//var dataGrid=new initDataComponent("listAcctRecordBodyForChoose_dataTable",'<s:url value="/acctConfig/acctRecordBodyAction_queryData.jhtml"/>',frozenColumns,columns);
		var myurl = "<s:url value="/acctConfig/acctRecordBodyAction_queryData.jhtml"/>";
		if(addTranItem_showValue_valueType != null){
			myurl += "?acctItem.valueType="+addTranItem_showValue_valueType;
			myurl += "&acctRecordBody.valueType="+addTranItem_showValue_valueType;
		}
		var dataGrid = new DataPageComponent("listAcctRecordBodyForChoose_dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					singleSelect:true,
					url:myurl,
					onDblClickRow:function(rowIndex, rowData){
						$('#tranItem_groupNo').val(rowData.groupNo);//组号
						$('#tranItem_serialNo').val(rowData.serialNo);//序号
						$('#tranItem_acctRecordBodyName').val(rowData.name);//名称
						$('#tranItem_recordBodyId').val(rowData.id);//分录结构体ID
						addTranItem_showValue_valueType = rowData.valueType;
						addTranItem_showValue("acctRecordBody");
						$('#acctRecordBodySelect').window('close');
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
