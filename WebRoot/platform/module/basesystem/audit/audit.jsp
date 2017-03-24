<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
	<tiles:putAttribute name="end">
	

<script type="text/javascript">
	var processDataGrid;//数据分页组件
	var auditTaskId='<s:property value="auditTask.id"/>';
	var auditProcessId='<s:property value="auditProcess.id"/>';
	var entityId='<s:property value="auditTask.auditEntityId"/>';
	var prodUrl='<s:property value="product.prodUrl"/>';
	var prodFlag='<s:property value="prodFlag"/>';
	var prodNo='<s:property value="product.prodNo"/>';
	var processAuditKeys=['B014','B015'];
	var processAuditcode=new CodeUtil(processAuditKeys);
	function initPage(){
		var _parent=$("#auditBusiPage").parent();
		autoHeightFit(_parent,".area_audit_flow,.area_audit_process,.area_audit_result",".area_auditBusiPage");
		processAuditcode.loadData();
//		processListDatagrid.onLoadSuccess=function(rows){
//  			if(rows){
 // 				for(var i=0;i<rows.length;i++){
 // 					var r=rows[i];
 // 					if(r.AP_STATUS){
 // 						if(r.AP_STATUS=='1'){
 // 							return;
 // 						}
 // 					}
 // 				}
  //				$('#btn_save_result').linkbutton('disable');
  //				$('#btn_commit_result').linkbutton('disable');
  //			}
//		};
		processListDatagrid.load();
		doPost(baseDir+prodUrl,{"auditEntityId":entityId},function(result){
			if(!printError(result)){
				var dom=$("#auditBusiPage");
				dom.html(result);
				$.parser.parse(dom);
				$.xcarsParser.parse(dom);
			}
		});
	}
	function ap_status_column(value){
		return processAuditcode.getValue("B014",value);
	}
	function ap_time_column(value){
		if(value){
			if(value.time){
				return DateFormat.format(new Date(value.time),'yyyy-MM-dd hh:mm:ss');
			}
		}
	}
	function ap_result_column(value){
		return processAuditcode.getValue("B015",value);
	}

	function saveAuditResult(){
		if($("#auditResultForm").form("validate")){
			var url = "<s:url value='/audit/audit_saveAuditResult.jhtml'/>";
			var parm=formToObject("auditResultForm");
			processListDatagrid.call(url,parm,function(result){
				var obj=str2obj(result);
				$("#audit_auditProcess_version").val(obj.version);
				info("保存成功！");
			});
		}
	}
	function commitAuditResult(){
		if($("#auditResultForm").form("validate")){
		var url='<s:url value="/audit/audit_saveAndCommitAuditResult.jhtml"/>';
		var parm=formArrayToObject(["auditResultForm","busiDataForm"]);
		
		doPost(url,parm,function(result){
			if(!printError(result)){
				audit_step_return();
			}
		});
		}
	}
//	function processAuditCallBack(result){
//		if(result){
//			if(result.indexOf('{')==0){
//				var obj=eval('('+result+')');
//				if(obj.error){
//					error(obj.error);
//					obj=null;
//					return;
//				}
//			}
//		}
//		processListDatagrid.load();
//	}
	function audit_step_return(){
		var url = "<s:url value='/audit/audit_auditMain.jhtml'/>";	
		if("1"==prodFlag){
			redirectUrl(url+"?prodNo=${prodNo}");
		}else{
			redirectUrl(url);
		}
		
	}
</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<div  class="area_audit_flow func_flow_area">
		<div id="step">
			<span id="step_list">
				<span id="step1" class="step_first"><s:text name="productAudit"/></span>
				<span id="step2" class="step_nomal step_current"><s:text name="audit"/></span>
			</span>
			<span id="step_tool">
				<x:button id="btn_save_result" icon="icon-save" click="saveAuditResult" text="saveAuditRemark"/>
				<x:button id="btn_commit_result" icon="icon-yes" click="commitAuditResult" text="commitAuditRemark"/>
				<x:button icon="icon-back" click="audit_step_return" text="return"/>
			</span>
		</div>
	</div>
	<div class="area_auditBusiPage" style="font: 12px arial,sans-serif;overflow:auto;"id="auditBusiPage" >
		正在加载审批内容页面...
	</div>
		<div class="area_audit_process func_data_area">
			<x:datagrid id="processListDatagrid" height="66" url="/audit/audit_queryProcess.jhtml" pagebar="false" params="{'auditTask.id':'${auditTask.id}'}" autoload="false">
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
		<div class="area_audit_result func_form_area"  >
			<form method="post"  class="busi_form" id="auditResultForm" >
						<s:hidden name="auditProcess.id" />
						<s:hidden name="auditProcess.auditTaskId"/>
						<s:hidden name="auditProcess.auditProcessCommitStation"/>
						<s:hidden name="auditProcess.auditProcessCommitPerson"/>
						<s:hidden name="auditProcess.auditProcessExecStation"/>
						<s:hidden name="auditProcess.auditProcessExecPerson"/>
						<s:hidden name="auditProcess.auditProcessStatus"/>
						<s:hidden id="audit_auditProcess_version" name="auditProcess.version" />
						<s:hidden name="auditProcess.sortNo"/>
						<table >
							<tr>
								<td class="head" colspan="2"><s:text name="auditExecRemark"/></td>
							</tr>
								<tr>
									<td class="title"><s:text name="auditExecResult"/>:</td>
									<td>
									<x:combobox isValidateBox="true" required="true" textField="codeName" valueField="codeNo" value="${auditProcess.auditProcessExecResult}" list="auditProcessExecResult" name="auditProcess.auditProcessExecResult"/>
									</td>
								</tr>
								<tr >
									<td class="title"><s:text name="auditExecRemark"/>:</td>
									<td>
										<textarea name="auditProcess.auditProcessExecRemark" class="easyui-validatebox"  required="true" style="width:60%;height:50px;" maxLength="100" validType="length[1,200]">${auditProcess.auditProcessExecRemark }</textarea><font color=red>*</font>
									</td>
								</tr>
							</table>
						</form>
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>