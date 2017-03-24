<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_form_area">
		<form id="busiDataForm" class="busi_form">
					<table>
						<tr>
							<td class="head" colspan="2">参数复核<FONT style="color:#0b77b7;">[<s:property value='sp.paramName'/>]</FONT></td>
						</tr>
						
						<s:if test="sp.codeKey == null">
						<tr>
							<td class="title" ><s:text name="旧值"/>:</td>
							<td><s:property value='sp.paramValue'/></td>
						</tr>
						<tr>
							<td class="title" ><s:text name="新值"/>:</td>
							<td >
								<input id="showValue" value="<s:property value='sp.tempValue'/>"   name="sp.paramValue" />
							</td>
						</tr>
						</s:if>
						<s:if test="sp.codeKey != null">
						<tr>
							<td class="title" ><s:text name="旧值"/>:</td>
							<td >
								<x:combobox list="codeList" disabled="disabled" valueField="codeNo" textField="codeName" value="${sp.paramValue}"/>
							</td>
						</tr>
						<tr>
							<td class="title" ><s:text name="新值"/>:</td>
							<td >
							<x:combobox name="sp.paramValue" list="codeList" valueField="codeNo" textField="codeName" value="${sp.tempValue}"/>
							</td>
						</tr>	
						</s:if>
						<s:hidden name="sp.paramName" />
						<s:hidden name="sp.paramKey" />
						<s:hidden name="sp.tempValue" />
						<s:hidden name="sp.status" />
						<s:hidden name="sp.buildUserId"/>
						<s:hidden name="sp.reviewUserId"/>
						<s:hidden name="sp.version"/>
						<s:hidden name="sp.paramId"/>
						<s:hidden name="sp.miNo"/>
						<s:hidden name="sp.codeKey"/>
					</table>
				</form> 
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>