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
						<td class="title" ><s:text name="auditRouteName"/>:</td>
						<td>${auditRoute.auditRouteName }</td>
					</tr>
					<tr>
						<td class="title"><s:text name="auditMode"/>:</td>
						<td>
						<x:combobox disabled="true" name="auditRoute.auditMode" list="auditMode" textField="codeName" valueField="codeNo"/>
						</td>
					</tr>
					<tr>
						<td class="title"><s:text name="auditNodeExecMode"/>:</td>
						<td>
							<x:combobox disabled="true" name="auditRoute.auditNodeExecMode" list="nodeExecMode" textField="codeName" valueField="codeNo"/>
						</td>
					</tr>
					<tr>
						<td class="title"><s:text name="auditRouteRemark"/>:</td>
						<td>${auditRoute.auditRouteRemark }</td>
					</tr>
					</tbody>
				</table>
		</form>
	</div>
</tiles:putAttribute>
</tiles:insertDefinition>