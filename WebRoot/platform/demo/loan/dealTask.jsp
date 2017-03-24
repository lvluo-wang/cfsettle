<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form  id="editForm" class="busi_form">
					<table>
						<tbody>
							
							<tr>
								<td class="title">同意:</td>
								<td ><s:checkbox id="success"  name="br.success"/></td>
							</tr>
							<tr>
								<td class="title">备注:</td>
								<td ><input name="br.info" id="br.info" class="easyui-validatebox" required="true" validType="length[1,3]" ></input></td>
							</tr>							
						</tbody>
					</table>
						<input type="hidden" id="taskId" name="taskId"/>
						<input type="hidden" id="id" name="id"/>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doTask" effect="round" text="提 交"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	
		var taskId="<%=request.getParameter("taskId")%>";
		$("#editForm")[0].taskId.value=taskId;
		var id="<%=request.getParameter("id")%>";
		$("#editForm")[0].id.value=id;

</script>
	</tiles:putAttribute>
</tiles:insertDefinition>