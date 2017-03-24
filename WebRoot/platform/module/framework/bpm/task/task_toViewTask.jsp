<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/platform/module/framework/bpm/task/task_history.jsp">
	<jsp:param name="entityId" value="${ib.entityId}"/>
	<jsp:param name="processName" value="${ib.processName}"/>
	<jsp:param name="processInstanceId" value='${ib.instanceId}'/>
</jsp:include>
