<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
<tiles:putAttribute name="body">
	<div class="win_form_area">
		<form method="post" class="busi_form" >
				<table>
					<colgroup>
						<col width="45%"/>
						<col width="55%"/>
					</colgroup>
					<tbody>
					<tr>
						<td class="title" ><s:text name="auditNodeName"/>:</td>
						<td>${auditNode.auditNodeName } </td>
					</tr>
					<tr>
						<td class="title"><s:text name="openAuditStationPrivilege"/>:</td>
						<td>
							<x:combobox disabled="true" name="auditNode.isPrivilegeCtrl" list="isPrivilegeCtrl" textField="codeName" valueField="codeNo"/>
						</td>
					</tr>
					</tbody>
				</table>
		</form> 
	</div>
</tiles:putAttribute>
</tiles:insertDefinition>