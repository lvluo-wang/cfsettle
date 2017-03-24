<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="com.upg.ubsp.constant.ComDictKeyConst"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_QUERY_DATA">
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">角色名称:</td>
					<td>
						<input name="ibSearchBean.col1" value="${ibSearchBean.col1}"/> 
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/bpm/task_taskCountList.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id"/>
				<x:column title="角色名称"  field="ROLE_NAME" width="200" align="left"/>
				<x:column title="处理中"  field="PROCESSING" width="120" align="center" formatter="formatProcessCount"/>
				<x:column title="已领用"  field="GET" width="120" formatter="formatGETCount"/>
				<x:column title="1天内未处理"  field="ONE_DAY" width="80" formatter="formatOneDayCount"/>
				<x:column title="2天内未处理"  field="TWO_DAY" width="100" formatter="formatTwoDayCount"/>
				<x:column title="3天内未处理"  field="THREE_DAY" width="80" align="center" formatter="formatThreeDayCount"/>
				<x:column title="7天内未处理"  field="SEVEN_DAY" width="80" align="center" formatter="formatSevenDayCount"/>
				<x:column title="7天以上未处理"  field="AFTER_SEVEN_DAY" width="80" align="center" formatter="formatSevenDayMoreCount"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="task_history" style="width:760px;height:450px;padding:3px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	
	function doQuery(){
		dataTable.load();
	}
	
	function formatProcessCount(value,field,row){
		var process = row.PROCESSING;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 1 +')">' + process + '</a>';
		return result;
	}
	function formatGETCount(value,field,row){
		var getVal = row.GET;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 2 +')">' + getVal + '</a>';
		return result;
	}
	function formatOneDayCount(value,field,row){
		var oneDayVal = row.ONE_DAY;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 3 +')">' + oneDayVal + '</a>';
		return result;
	}
	function formatTwoDayCount(value,field,row){
		var twoDayVal = row.TWO_DAY;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 4 +')">' + twoDayVal + '</a>';
		return result;
	}
	function formatThreeDayCount(value,field,row){
		var threeDayVal = row.THREE_DAY;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 5 +')">' + threeDayVal + '</a>';
		return result;
	}
	function formatSevenDayCount(value,field,row){
		var sevenDayVal = row.SEVEN_DAY;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 6 +')">' + sevenDayVal + '</a>';
		return result;
	}
	function formatSevenDayMoreCount(value,field,row){
		var sevenDayMoreVal = row.AFTER_SEVEN_DAY;
		var result = '<a href="#" onclick="hrefToViewProcessHref(' + row.ROLE_ID + ','+ 7 +')">' + sevenDayMoreVal + '</a>';
		return result;
	}
	function hrefToViewProcessHref(roleId,countType){
		var url="<s:url value='/bpm/task_toViewProcessDetail.jhtml' />?roleId="+roleId+"&countType="+countType;
		redirectUrl(url);
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
