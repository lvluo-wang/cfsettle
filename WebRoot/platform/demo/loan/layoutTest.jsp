<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA_TEST">
	<tiles:putAttribute name="tool">
		<x:button click="doAdd" text="新增单据" icon="icon-add"/>
		<x:button click="doEdit" text="编辑单据" icon="icon-edit"/>
		<x:button click="doRemove" text="删除单据" icon="icon-remove"/>
		<x:button click="doView" text="查看单据" icon="icon-print"/>
		<x:button click="doApply" text="提 交" icon="icon-audit"/>
		<x:button click="doAdd" text="新增单据" icon="icon-add"/>
		<x:button click="doEdit" text="编辑单据" icon="icon-edit"/>
		<x:button click="doRemove" text="删除单据" icon="icon-remove"/>
		<x:button click="doView" text="查看单据" icon="icon-print"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryMainForm" class="query_form" >
			<table>
				<tr>
					<td class="title">贷款编号:</td>
					<td><input name="loanInfo.loanNo" /></td>
					<td class="title">贷款人:</td>
					<td><input name="loanInfo.userName" /></td>
					<td class="title">贷款状态:</td>
					<td>
						<x:combobox name="loanInfo.status"/>
					</td>
					<td class="title">贷款编号:</td>
					<td>
						<input class="Wdate" type="text" onClick="WdatePicker()" > 
					</td>
				</tr>
				<tr>
					<td class="title">贷款人:</td>
					<td>
						<input class="Wdate" type="text" onClick="WdatePicker()" > 
					</td>
					<td class="title">贷款编号:</td>
					<td>
						<input class="Wdate" type="text" name="busiDt" onClick="WdatePicker()" > 
					</td>
					<td class="title">贷款人:</td>
					<td>
						<input class="Wdate" type="text" onClick="WdatePicker()" > 
					</td>
					<td>
						<x:button icon="icon-search" click="doSearch" text="query" />
						<!-- 测试 -->
						<a href="javascript:selectedFields();">getSelectedMutField</a>
					</td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">

	<x:datagrid id="mydatagrid" url='/demo/loan_queryData.jhtml' autoload="false" form="queryMainForm" pagebar="true">
		<x:columns>
		    <!-- width默认70px,align默认center,单元格不能显示完整支持换行 -->
			<x:column title="" field="id" checkbox="true" width="20" />
			<x:column title="loanNo" field="loanNo"  width="30" align="left"  />
			<x:column title="userName" field="userName"    />
			<x:column title="money" field="money"   formatter="curformatter"/>
			<x:column title="status" field="status" />
			<x:column title="userName" field="userName"   />
			<x:column title="money" field="money"   />
			<x:column title="status" field="status"   />
			<x:column title="userName" field="userName"   />
			<x:column title="money" field="money"   />
			<x:column title="status" field="status"    />
			<x:column title="userName" field="userName"   />
			<x:column title="money" field="money"   />
			<x:column title="status" field="status"   />
		</x:columns>
	</x:datagrid>
		
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit" style="display:none;width:450px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		
		
		var dataGrid;
		
		//工具栏具体实现方法
		function doSearch(){
			mydatagrid.load();
		}
		function doAdd(){
			var url="<s:url value='/platform/demo/loan/add.jsp'/>?flag="+Math.random()*99999;
			requestAtWindow(url,"add_edit","新增贷款单");		
		}
		function doEdit(){
			var id=mydatagrid.getSelectedField("id");
			if(id){
				var url="<s:url value='/demo/loan_edit.jhtml' />?id="+id+"&flag="+Math.random()*99999;
				requestAtWindow(url,"add_edit","编辑贷款单");
			}else{
				info(global.notSelectInfo);
			}
			
		}
		function doRemove(){		
			var ids=mydatagrid.getSelectedIds();
			var url = "<s:url value='/demo/loan_delete.jhtml'/>";
			doPost(url,{"ids":ids},function(result){
				printError(result);
				mydatagrid.refresh();
			});
		}
		function doView(){
			var id=mydatagrid.getSelectedField("id");
			if(id){
				var url="<s:url value='/demo/loan_view.jhtml' />?loanInfo.id="+id+"&flag="+Math.random()*99999;
				requestAtWindow(url,"add_edit","查看贷款单");
			}else{
				info(global.notSelectInfo);
			}
		}
		
		function doApply(){
			var url = "<s:url value='/demo/loan_apply.jhtml'/>";
			var ids=mydatagrid.getSelectedIds();
			doPost(url,{"ids":ids},function(result){
				printError(result);
				mydatagrid.refresh();
			});
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
        //p1:value,p2:rowData,p3:rowIndex
		function curformatter(val,rowdata,rowIndex){
		    var tmp = val+"";
		    if(tmp.indexOf(".")<0)
		    	tmp = tmp + ".00";
			tmp = tmp*100;
			tmp = Math.round(tmp)/100;
		    return "￥"+tmp;
		}
		function statusformatter(val,rowdata,rowIndex){
			if(val == 0) return "状态状态状态状态状态状态状态状态状态状态状态状态0";
			if(val == 1) return "状态1";
		    return "未知";
		}

		function selectedFields(){
			var fields = ["loanNo","status"];
			var data = mydatagrid.getSelectedMutField(fields);
			alert(data["loanNo"]+","+data["status"]);
		}

		mydatagrid.onDblClickRow = function(rowIndex,rowData){
			alert("行号["+rowIndex+"],loanNo="+rowData["loanNo"]+",status="+rowData["status"]);
		};
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>