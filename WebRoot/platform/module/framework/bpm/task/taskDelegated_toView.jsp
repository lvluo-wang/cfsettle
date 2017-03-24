<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="taskDelegated_editForm">
			<h3 align="center">查看委托办理</h3>
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title"><s:text name="taskDelegated.actor"/>: </td>
						<td>
							${humnTaskActorDelegate.delegatorName}
						</td>
					</tr>
				   	<tr>
				   		<td class="title"><s:text name="taskDelegated.startTime"/>: </td>
						<td>
							<s:date name="humnTaskActorDelegate.startTime" format="yyyy-MM-dd HH:mm:00"/>
						</td>	
					</tr>
					<tr>
						<td class="title"><s:text name="taskDelegated.endTime"/>: </td>	
						<td>
							<s:date name="humnTaskActorDelegate.endTime" format="yyyy-MM-dd HH:mm:00"/>
						</td>
					</tr>
					<tr>
						<td class="title"><s:text name="taskDelegated.inEffect"/>: </td>
						<td>
							<s:if test="humnTaskActorDelegate.inEffect == 1 ">
								有效
							</s:if>
							<s:else>
								无效
							</s:else>
						</td>	
						
					</tr>
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	function doAddCancel() {
		closeWindow("project_add_win");
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>