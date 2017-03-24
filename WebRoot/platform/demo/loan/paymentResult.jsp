<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button icon="icon-edit" click="dealTask" text="处 理"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" class="query_form">
			<table>
			<tr>
			<td class="title">贷款编号:</td>
			<td><input name="loanInfo.loanNo"/></td>
			<td>
				<x:button icon="icon-search" click="doSearch" text="query"/>
			</td>
			</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table id="dataTable"></table>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"   style="width:500px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		var dataGrid;
		function initPage(){	
			var frozenColumns=[[
			                    {field:'ck',checkbox:true,width:20},
			                    {title:'编号',field:'loanNo',width:120,sortable:true}
			        		]];
			var columns=[[  
			  			{field:'userName',title:'名称',width:200,sortable:true,
							sorter:function(a,b){
								return (a>b?1:-1);
							}
						},
						{field:'money',title:'金额',width:fixWidth(0.2)},
						{field:'status',title:'状态',width:100,align:'center',
							formatter:function(value,rec){
								if (value=="0")
									return '<span style="color:red">新增</span>';
								if (value=="1")
									return '<span style="color:green">业务受理</span>';
								if (value=="2")
									return '<span style="color:red">普通审核</span>';
								if (value=="3")
									return '<span style="color:red">高级审核</span>';
								if (value=="4")
									return '<span style="color:red">划款</span>';
								if (value=="5")
									return '<span style="color:blue">结束</span>';
							}
						}
					]];
			//初始化数据组件
			dataGrid=new DataPageComponent("dataTable",{
				url:'<s:url value="/demo/loan_queryPaymentResult.jhtml"/>',
				columns:columns,
				frozenColumns:frozenColumns
			});
			dataGrid.load();
		}
	//工具栏具体实现方法
	function doSearch(){
		var parm=formToObject("queryForm");
		dataGrid.queryByParam(parm);		
	}
	
	function dealTask(){		
		if(isSingleSelected("dataTable","tokenId")){
			var selectedId=getSelected("dataTable","tokenId");		
			var url = "<s:url value='/demo/loan_dealPaymentResult.jhtml'/>?tokenId="+selectedId;
			requestAndRefresh(url,"dataTable");
		}
	}
	
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>