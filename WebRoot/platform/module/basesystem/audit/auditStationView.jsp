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
								<td class="title" ><s:text name="auditStationName"/>:</td>
								<td>${auditStation.auditStationName }</td>
							</tr>
							<tr>
								<td class="title"><s:text name="auditStationPrivilege"/>:</td>
								<td>${auditStation.auditStationPrivilege }</td>
							</tr>
							<tr>
								<td class="title" ><s:text name="stationBindBrch"/>:</td>
								<td>
									${bindBrch.brchName }
								</td>
							</tr>
							<tr>
								<td class="title" ><s:text name="auditStationRemark"/>:</td>
								<td>
									${auditStation.auditStationRemark }
								</td>
							</tr>
							</tbody>
						</table>
				</form>
			</div>
</tiles:putAttribute>
</tiles:insertDefinition>