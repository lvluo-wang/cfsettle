<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_QUERY_DATA">

	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td><input name="custSearchBean.realName" style="width:130px"></input></td>
					<td class="title">客户手机: </td>
					<td><input name="custSearchBean.mobile" style="width:130px"></input></td>
					<td class="title">是否验证:</td>
					<td>
						<x:combobox name="custSearchBean.isValid" list="yseNo" textField="codeName" valueField="codeNo"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/cust/cfscust_infoList.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="uid" />
				<x:column title="客户姓名" field="realName" align="center" width="140"/>
				<x:column title="性别" field="sex" align="center" width="40" formatter="formatSex"/>
				<x:column title="客户手机" field="mobile" align="left" width="90"/>
				<x:column title="身份证号" field="idCard" align="center" width="150" />
				<x:column title="添加时间" field="ctime" align="center" width="150"/>
				<x:column title="归属员工类型" field="posCode" align="center" width="40" formatter="formatPosCode"/>
				<x:column title="归属员工姓名" field="userName" align="left" width="90"/>
				<x:column title="归属团队" field="teamName" align="center" width="150" />
				<x:column title="归属营业部" field="deptName" align="center" width="150"/>
				<x:column title="归属区域" field="areaName" align="center" width="150"/>
				<x:column title="验证" field="isValid" align="center" width="80" formatter="formatYesNo"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=CfsConstant.CFS_COMM_YSE_NO%>","<%=CfsConstant.CFS_COMM_SEX%>","<%=UtilConstant.CFS_BUSER_POS_CODE%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	
	function formatSex(value){
		 return code.getValue("<%=CfsConstant.CFS_COMM_SEX%>",value);
	}
	
	function formatYesNo(value){
		 return code.getValue("<%=CfsConstant.CFS_COMM_YSE_NO%>",value);
	}

    function formatPosCode(value){
        return code.getValue("<%=UtilConstant.CFS_BUSER_POS_CODE%>",value);
    }
	
	function doQuery(){
		dataTable.load();
	}

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
