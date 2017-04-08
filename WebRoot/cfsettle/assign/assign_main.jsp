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
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/assign/assignManage_list.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="userId" />
				<x:column title="员工职位" field="posCode" align="center" width="80" formatter="userFormatter"/>
				<x:column title="姓名" field="userName" align="center" width="100"/>
				<x:column title="归属团队" field="teamName" align="center" width="100" />
				<x:column title="所属营业部" field="deptName" align="center" width="100"/>
				<x:column title="归属区域" field="areaName" align="center" width="100" />
				<x:column title="状态" field="status" align="center" width="80" formatter="userFormatter"/>
				<x:column title="客户数量" field="custNum" align="center" width="80" formatter="userFormatter"/>
				<x:column title="客户分配" field="userId" align="center" width="80" formatter="userFormatter"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:750px;height:500px;display:none;"></div>
	<div id="selectWin" style="width:300px;height:auto;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		var keys=['B003'];
		var code=new CodeUtil(keys);
		code.loadData();
		var xhhCode = new XhhCodeUtil(["<%=UtilConstant.CFS_BUSER_POS_CODE%>"]);
		xhhCode.loadData();


	function doQuery(){
		dataTable.load();
	}
	function userFormatter(value,field,row,rowIndex){
		if(field == "posCode"){
			return xhhCode.getValue("<%=UtilConstant.CFS_BUSER_POS_CODE%>",value);
		}else if(field == "status"){
			if(value == 4){
				return code.getValue("B003",value);
			}
			return "在职";
		}else if(field == "custNum"){
			return value+"人";
		}else if(field == "userId"){
			if(row.status == 4 && row.custNum > 0){
				return "<a href='#' onclick='assignCust("+value+")'>待分配</a>";
			}
			if(row.status == 4 && row.custNum == 0){
				return '已分配';
			}
			if(row.status != 4){
				return "-"
			}
		}
		return "";
	}

		function assignCust(value) {
			var url="<s:url value='/assign/assignManage_toAssign.jhtml'/>?id="+value;
			requestAtWindow(url,"project_add_win","客户分配");
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
