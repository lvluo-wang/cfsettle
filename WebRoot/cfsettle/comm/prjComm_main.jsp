<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td><input name="searchBean.realName" style="width:130px"></input></td>
					<td class="title">客户手机: </td>
					<td><input name="searchBean.mobile" style="width:130px"></input></td>
					<td class="title">是否验证:</td>
					<td>
						<x:combobox name="searchBean.isValid" list="yseNo" textField="codeName" valueField="codeNo"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/comm/prjComm_prjCommList.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="项目名称" field="prjName" align="center" width="140"/>
				<x:column title="募集金额(万)" field="demandAmount" align="center" width="100"/>
				<x:column title="年化利率(%)" field="yearRate" align="left" width="70"/>
				<x:column title="项目期限" field="timeLimit" align="center" width="80"  formatter="formatTimeUnit"/>
				<x:column title="还款方式" field="repayWay" align="center" width="80" formatter="formatReapayWay"/>
				<x:column title="佣金总比例(%)" field="totalRate" align="center" width="80"/>
				<x:column title="区域经理佣金比(%)" field="areaRate" align="center" width="100" />
				<x:column title="营业部佣金比(%)" field="deptRate" align="center" width="100"/>
				<x:column title="团队长佣金比(%)" field="teamRate" align="center" width="100"/>
				<x:column title="客户经理佣金比(%)" field="sysuserRate" align="center" width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:750px;height:auto;display:none;"></div>
	<div id="project_edit_win" style="width:750px;height:auto;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_TIMELIMIT_UNIT%>","<%=UtilConstant.CFS_REPAYMENT_TYPE%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	
	function formatTimeUnit(value,field,row){
		 return value+code.getValue("<%=UtilConstant.CFS_TIMELIMIT_UNIT%>",row.timeLimitUnit);
	}
	
	function formatReapayWay(value){
		 return code.getValue("<%=UtilConstant.CFS_REPAYMENT_TYPE%>",value);
	}
	function doQuery(){
		dataTable.load();
	}
		
	function doAdd(){
		var url="<s:url value='/cust/cfscust_toAdd.jhtml'/>";
		requestAtWindow(url,"project_add_win","<s:text name='add'/>");
	}
	
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/cust/cfscust_toView.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
		
	}
	
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			if(dataTable.getSelectedFirstRow().isValid =='0'){
				var url="<s:url value='/cust/cfscust_toEdit.jhtml'/>";
				requestAtWindow(url,"project_edit_win","<s:text name='edit'/>",{id:selectedId});
			}else{
				info('已验证用户不能修改');
			}
		}
	}

	function doRemove(){
		if(isSelected(dataTable)){
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
					dataTable.call('<s:url value="/cust/cfscust_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
