<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<%
	StringBuilder sb = new StringBuilder("{");
	String instanceBusinessId = request.getParameter("instanceBusinessId");
	if (instanceBusinessId != null && instanceBusinessId.length() > 0 ) {
		sb.append("\"ib.id\":\"").append(instanceBusinessId).append("\"");
	}
	sb.append("}");
	String suffix = request.getParameter("suffix") == null ? "" : (String)request.getParameter("suffix");
%>
<div style="border:#99bbe8 1px solid;width:100%;">
<x:datagrid id='<%="task_history_dataTable"+suffix%>' url="/bpm/task_getInstanceTaskHistory.jhtml" params="<%=sb.toString()%>" autoload="true" pagebar="false" autoFit="true">
	<x:columns>
		<x:column title="审批环节"  field="taskName" align="left" width="120"/>
		<x:column title="审批结果"  field="isAgree" align="left" width="60" formatter="formatAgree"/>
		<x:column title="意见"  field="remark" align="left" width="300" formatter="appendBusinessMessage"/>
		<x:column title="处理人" field="dealUserName" align="left" width="80" />
		<x:column title="处理时间" field="dealTime" align="left" width="115" formatter="format2Minute"/>
	</x:columns>
</x:datagrid>
</div>
<script>
	function appendBusinessMessage(val,id,row){
		var tmp = val;
		if (row.businessFormatMessage) {
			tmp = row.businessFormatMessage + tmp;
		}
		return tmp;
	}
	function formatAgree(value){
		return value == '1' ? "通过" : (value == '-1'? "处理中" : (value == '0' ? "驳回" : value));
	}
</script>