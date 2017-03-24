<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_data_area">
			<x:datagrid id="processListDatagrid" height="150" url="/audit/audit_queryProcess.jhtml" pagebar="false" params="{'auditTask.id':'${auditTask.id}'}" autoload="false" >
				<x:columns>
					<x:column field="BRCH_NAME" title="auditBrch" width="100"/>
					<x:column field="USER_NAME" title="auditUser" width="100"/>
					<x:column field="AP_STATUS" title="auditExecStatus" width="80" formatter="ap_status_column"/>
					<x:column field="AP_EXEC_RESULT" title="auditExecResult" formatter="ap_result_column"/>
					<x:column field="AP_EXEC_REMARK" title="auditExecRemark" width="200"/>
					<x:column field="AP_DONE_TIME" title="auditProcessExecDoneTime" width="140" formatter="format2Time"/>
				</x:columns>
			</x:datagrid>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			var processAuditKeys=['B014','B015'];
			var processAuditcode=new CodeUtil(processAuditKeys);
			function ap_result_column(value){
				return processAuditcode.getValue("B015",value);
			}
			function ap_status_column(value){
				return processAuditcode.getValue("B014",value);
			}

			processAuditcode.loadData();
			processListDatagrid.load();

		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>


