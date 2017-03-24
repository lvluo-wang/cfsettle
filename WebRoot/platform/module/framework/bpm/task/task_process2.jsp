<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<%
	ValueStack stack = ActionContext.getContext().getValueStack();
	Long entityId = (Long) stack.findValue("entityId");
	String processName = (String) stack.findValue("processName");
%>
<tiles:insertDefinition name="FUNC_BLANK">
	
	
	<tiles:putAttribute name="body">
		<jsp:include page="/platform/module/framework/bpm/task/task_history.jsp">
			<jsp:param name="entityId" value="<%=entityId%>"/>
			<jsp:param name="processName" value="<%=processName%>"/>
		</jsp:include>
		<br/><br/><br/>
		<form class="busi_form" id="taskprocess_form">
			<table>
				<colgroup>
					<col width="20%"/>
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<td class="title" valign="top">意见:</td>
						<td ><textarea id="tpr_remark" name="tpr.remark" cols="60" style="height:150px;" >${tpr.remark}</textarea></td>
					</tr>
					<tr>
						<td class="title" valign="top">审批流向:</td>
						<td>
							<br/>
							<span><input type="radio" name="tpr.pass" value="true" <s:if test="tpr == null || tpr.pass "> checked="checked"</s:if>/>&nbsp;&nbsp;分配</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;处理人:
							<s:hidden name="tpr.nextUser" id="tpr_nextUser" />
							<input id="tpr_nextUser_name"  class="inputSel" onClick="chooseBrchUser(processUserCallBack)"/>
							<br/><br/>
							<span><input type="radio" name="tpr.pass" value="false" <s:if test="tpr.pass==false"> checked="checked"</s:if> />&nbsp;&nbsp;驳回</span>
							<br/><br/>
						</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="taskId" value="${taskId}"/>
		</form>
		<br/>
		<div style="width:90%;margin-left:auto;margin-right:auto;text-align:center;">
			<!-- <x:button iconCls="icon-save" text="保存" click="doSave" />&nbsp;&nbsp; -->
			<x:button iconCls="icon-audit" text="提交处理" click="doCommit" />&nbsp;&nbsp;
			<x:button iconCls="icon-cancel" text="关闭" click="doCancel" />
		</div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
		<script>
			function formatAgree(value){
				return value == '1' ? "通过" : "驳回";
			}
			function doSave(){
				if(!$("#tpr_nextUser").val()){
					error("处理人不能为空");
					return ;
				}
				var param=formToObject("taskprocess_form");
				param.dealTask=false;
				var url = '<s:url value="/bpm/task_dealTask.jhtml"/>';
				doPost(url,param,function(result){
					if(result){
						if(result =='1'){
							$.messager.alert(global.alert,"分配成功", null,function(r){
								redirectUrl('<s:url value="/bpm/task_main.jhtml"/>');
							});
						}else{
							error("保存失败");
						}
					}
				});
			}
			function doCommit(){
				
				if($("input:radio[name='tpr.pass']:checked").val() == 'false'){
					if(!$("#tpr_remark").val()){
						error("意见不能为空");
						return ;
					}
				}else{
					if(!$("#tpr_nextUser").val()){
						error("处理人不能为空");
						return ;
					}
				}
				var param=formToObject("taskprocess_form");
				param.dealTask=true;
				var url = '<s:url value="/bpm/task_dealTask.jhtml"/>';
				doPost(url,param,function(result){
					if(result){
						if(result =='1'){
							$.messager.alert(global.alert,"任务处理成功", null,function(r){
								redirectUrl('<s:url value="/bpm/task_main.jhtml"/>');
							});
						}else{
							error("任务处理失败");
						}
					}
				});
			}
			function doCancel(){
				redirectUrl('<s:url value="/bpm/task_main.jhtml"/>');
			}
			function processUserCallBack(row){
				var userId = '';
				var userName = '';
				if(row){
					userId=row.userId;
					userName=row.userName;
				}
				$("#tpr_nextUser").val(userId);
				$("#tpr_nextUser_name").val(userName);
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>