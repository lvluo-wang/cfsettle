<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button icon="icon-add" click="doAdd" text="AddLoanInfo"/>
		<x:button icon="icon-edit" click="doEdit" text="EditLoanInfo"/>
		<x:button icon="icon-remove" click="doRemove" text="DelLoanInfo"/>
		<x:button icon="icon-print" click="doView" text="ViewLoanInfo"/>
		<x:button icon="icon-audit" click="doApply" text="commit"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" class="query_form" >
			<table>
				<tr>
					<td class="title">贷款编号:</td>
					<td><input name="loanInfo.loanNo" /></td>
					<td class="title">贷款人:</td>
					<td><input name="loanInfo.userName" /></td>
					<td class="title">贷款状态:</td>
					<td>
					<x:combobox url="sdfsdf" data="ddd" name="loanInfo.status"/>
					</td>
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
		<div id="add_edit" style="width:300px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		var dataGrid;
		function initPage(){
			// 获取初始化组件所需的数据
			var columns=[[  
{field:'ck',checkbox:true,width:20},
{title:'编号',field:'loanNo',width:50,sortable:true},
			  			{field:'userName',title:'名称',width:50,sortable:true,
							sorter:function(a,b){
								return (a>b?1:-1);
							}
						},
						{field:'money',title:'金额',width:50},
						{field:'status',title:'状态',width:50,align:'center',
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
						},
						{field:'userName',title:'名称',width:50,sortable:true,
							sorter:function(a,b){
								return (a>b?1:-1);
							}
						},
						{field:'money',title:'金额',width:50},
						{field:'status',title:'状态',width:50,align:'center',
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
						},
						{field:'userName',title:'名称',width:50,sortable:true,
							sorter:function(a,b){
								return (a>b?1:-1);
							}
						},
						{field:'money',title:'金额',width:50},
						{field:'status',title:'状态',width:50,align:'center',
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
						},
						{field:'userName',title:'名称',width:50,sortable:true,
							sorter:function(a,b){
								return (a>b?1:-1);
							}
						},
						{field:'money',title:'金额',width:50},
						{field:'status',title:'状态',width:50,align:'center',
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
				url:'<s:url value="/demo/loan_queryData.jhtml"/>',
				columns:columns


			});
			dataGrid.load();
			
		};
		//工具栏具体实现方法
		function doSearch(){
			var parm=formToObject("queryForm");
			dataGrid.queryByParam(parm);		
		}
		function doAdd(){
			var url="<s:url value='/platform/demo/loan/add.jsp'/>?flag="+Math.random()*99999;
			requestAtWindow(url,"add_edit","新增贷款单");		
		}
		function doEdit(){
			if(isSingleSelected("dataTable","funcId")){
				var selectedId=getSelected("dataTable","id");
				var url="<s:url value='/demo/loan_edit.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;
				requestAtWindow(url,"add_edit","编辑贷款单");
			}

		}
		function doRemove(){		
			var ids=getMutSelected("dataTable","id");
			if(ids.length<1){
				return;
			}
			var url = "<s:url value='/demo/loan_delete.jhtml'/>";
			dataGrid.call(url,{"ids":ids});
		}
		function doView(){
			if(isSingleSelected("dataTable","id")){
				var selectedId=getSelected("dataTable","id");
				var url="<s:url value='/demo/loan_view.jhtml' />?loanInfo.id="+selectedId+"&flag="+Math.random()*99999;
				requestAtWindow(url,"add_edit","查看贷款单");
			}
		}
		
		function doApply(){
			var ids=getMutSelected("dataTable","id");
			if(ids.length<1){
				return;
			}
			var url = "<s:url value='/demo/loan_apply.jhtml'/>";
			dataGrid.call(url,{"ids":ids});
		}
		function doCancel(){
			$("#add_edit").window("close");
		}
		var ddd = [{value:'0',text:'新建'},
		           {value:'1',text:'业务受理'},
					{value:'2',text:'普通审批'},
					{value:'3',text:'高级审批'},
					{value:'4',text:'划款'},
					{value:'5',text:'结束'}
		           ];
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>