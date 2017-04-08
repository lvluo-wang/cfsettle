<%@ page import="com.upg.cfsettle.util.CfsConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button icon="icon-yes" click="doCustAssign" text="选择分配"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="custAssignDataTable" pagebar="false" url="/assign/assignManage_custList.jhtml?buserId=${buserId}" autoload="true" >
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="客户姓名" field="realName" align="center" width="100"/>
				<x:column title="性别" field="sex" align="center" width="40" formatter="formatSex"/>
				<x:column title="客户手机" field="mobile" align="left" width="90"/>
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

		function formatSex(value){
			return codeAssign.getValue("<%=CfsConstant.CFS_COMM_SEX%>",value);
		}

		function formatYesNo(value){
			return codeAssign.getValue("<%=CfsConstant.CFS_COMM_YSE_NO%>",value);
		}

		function doCustAssign() {
                var selectedId = custAssignDataTable.getSelectedFields("id");
                var url = "<s:url value='/assign/assignManage_selectSale.jhtml'/>?custIds="+selectedId;
                requestAtWindow(url,"selectWin","选择员工");

        }

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
