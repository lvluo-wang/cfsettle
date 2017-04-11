<%@ page import="com.upg.cfsettle.util.CfsConstant" %>
<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_QUERY_DATA">
	<tiles:putAttribute name="query">
		<form id="mainQueryForm" class="query_form">
		<table>
			<tr>
				<td>${organizationBean.areaName}</td>
				&nbsp;&nbsp;
				<td>${organizationBean.deptName}</td>
				&nbsp;&nbsp;
				<td>${organizationBean.teamName}</td>
			</tr>
		<tr>
			<td class="title">员工职位: </td>
			<td><x:codeItem codeKey="<%=UtilConstant.CFS_BUSER_POS_CODE%>" codeNo="buser.posCode"/></td>
			<td class="title">员工姓名:</td>
			<td>${buser.userName}</td>
			&nbsp;&nbsp;
			<td class="title">员工手机号:</td>
			<td>${buser.userNo}</td>
			<td class="title">客户数:</td>
			<td id="totalNum"></td>
		</tr>
		</table>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="custAssignDataTable" pagebar="false" url="/assign/assignManage_custList.jhtml?buserId=${buserId}" autoload="true" >
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="客户姓名" field="realName" align="center" width="100"/>
				<x:column title="性别" field="sex" align="center" width="40" formatter="formatSex"/>
				<%--<x:column title="客户手机" field="mobile" align="left" width="90"/>--%>
				<x:column title="身份证号" field="idCard" align="center" width="100" />
				<x:column title="添加时间" field="ctime" align="center" width="100"/>
				<x:column title="验证" field="isValid" align="center" width="80" formatter="formatYesNo"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		var keysAssign=["<%=CfsConstant.CFS_COMM_YSE_NO%>","<%=CfsConstant.CFS_COMM_SEX%>"];
		var codeAssign=new XhhCodeUtil(keysAssign);
		codeAssign.loadData();

		custAssignDataTable.onLoadSuccess = function(data){
			var num = data== null ? 0 : data.length;
			$("#totalNum").html(num+"人");
		};

		function formatSex(value){
			return codeAssign.getValue("<%=CfsConstant.CFS_COMM_SEX%>",value);
		}

		function formatYesNo(value){
			return codeAssign.getValue("<%=CfsConstant.CFS_COMM_YSE_NO%>",value);
		}

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
