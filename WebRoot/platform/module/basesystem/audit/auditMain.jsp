<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_DATA">
		<tiles:putAttribute name="tool">
			<x:button icon="icon-yes" click="accept" text="auditAccept"/>
			<x:button icon="icon-no" click="revoke" text="revokeAccept"/>
			<x:button icon="icon-ok" click="audit" text="audit"/>
		</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="auditListDatagrid" url="/audit/audit_auditList.jhtml?prodNo=${prodNo}"  autoload="false">
			<x:columns>
				<x:column field="auditProcessId" checkbox="true"/>
				<x:column field="brchName" title="brch" width="120"/>
				<x:column field="userName" title="auditProcessCommitPerson" width="100"/>
				<x:column field="prodNo" title="prodNo" width="80"/>
				<x:column field="prodAlias" title="prodAlias" width="100"/>
				<x:column field="auditProcessStatus" title="auditProcessStatus" width="80" formatter="statusColumn"/>
				<x:column field="auditRemark" title="remark" width="120"/>
				<x:column field="auditRouteName" title="auditRouteName" width="120"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	var prodNo='${prodNo}';
	var keys=['B014'];
	var code=new CodeUtil(keys);
	function statusColumn(value){
		return code.getValue('B014',value);
	}
	function initPage(){
		
		code.loadData();
		auditListDatagrid.load();
		
	};
	function accept(){
		if(auditListDatagrid.getSelectedRowNum()>0){
			var ids = auditListDatagrid.getSelectedIds();
			var url='<s:url value="/audit/audit_acceptProcess.jhtml"/>';
			doPost(url,{"ids":ids},function(result){
				printError(result);
				auditListDatagrid.refresh();
			});
		}else{
			info(global.notSelectInfo);
		}
	}
	function revoke(){
		if(auditListDatagrid.getSelectedRowNum()>0){
			var ids = auditListDatagrid.getSelectedIds();
			var url='<s:url value="/audit/audit_revokeProcess.jhtml"/>';
			doPost(url,{"ids":ids},function(result){
				printError(result);
				auditListDatagrid.refresh();
			});
		}else{
			info(global.notSelectInfo);
		}
	}
	function audit(){
		var num=auditListDatagrid.getSelectedRowNum();
		if(num>0){
			if(num>1){
				info(global.singleSelectInfo);
				return;
			}
			var auditTaskId=auditListDatagrid.getSelectedField("auditTaskId");
			var auditProcessId=auditListDatagrid.getSelectedField("auditProcessId");
			var auditProcessStatus=auditListDatagrid.getSelectedField("auditProcessStatus");
			if ( "2" == auditProcessStatus ){
				warning('<s:text name="finishedAudit_NotAudit" />');
				return;
			}
			var prodId=auditListDatagrid.getSelectedField("prodId");
			var url='<s:url value="/audit/audit_audit.jhtml"/>';
			if(prodNo!=null&&prodNo.length>0){
				url='<s:url value="/audit/audit_audit.jhtml"/>?prodFlag=1';
			}
			redirectUrl(url,
				{"auditTask.id":auditTaskId,"auditTask.prodId":prodId,"auditProcess.id":auditProcessId,"prodNo":prodNo});
		}else{
			info(global.notSelectInfo);
		}
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>