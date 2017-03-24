<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<%
	boolean isShowMeta = UserManager.isImplementation();
%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form id="editForm"  class="busi_form">
			<table>
			<colgroup>
				<col width="40%"/>
				<col width="60%"/>
			</colgroup>
				<tbody>
				<tr>
					<td class="title"><s:text name="param.name"/></td>
					<td>
						<%
							if( !isShowMeta ){
						%>
							<s:if test="sp != null">
								<input type="hidden" name="sp.paramName" value="${sp.paramName}" />
								${sp.paramName}
							</s:if>
						<%
							}else{
						%>
							<input class="easyui-validatebox" required="true" value="<s:property value='sp.paramName'/>"  maxLength="25" name="sp.paramName" validType="chs_char_num_ul"/>
							<font color="red">*</font>
						<%
							}
						%>
					</td>
				</tr>
				<tr>
					<td class="title"><s:text name="param.code"/></td>
					<td>
						<%
							if( !isShowMeta ){
						%>
							<s:if test="sp != null">
								<input type="hidden" name="sp.paramKey" value="${sp.paramKey}" />
								${sp.paramKey}
							</s:if>
						<%
							}else{
						%>
							<input class="easyui-validatebox" required="true" value="<s:property value='sp.paramKey'/>"  maxLength="30" name="sp.paramKey" validType="char_num_ul"/>
							<font color="red">*</font>
						<%
							}
						%>
						
					</td>
				</tr>
				<%
					if(isShowMeta){
				%>
					<tr>
						<td class="title"><s:text name="codemeta"/></td>
						<td>
							<input id="codeMetaName" value='<s:property value="codeMeta.name" />'  class="inputSel" onClick="chooseCodeMeta(chooseCodeMetaCallBack_)"/>
						</td>
					</tr>
				<%
					}
				%>
				<tr>
					<td class="title"><s:text name="param_value"/></td>
					<td>
						<x:combobox id="sp_tempValue_combo" required="true" name="sp.tempValue" value="${sp.tempValue}" valueField="codeNo" textField="codeName" list="codeList"/>
						<span id="sp_temp_value_inp">
						<input class="easyui-validatebox" required="true" name="sp.tempValue" value="${sp.tempValue}" style="width:100px;" maxLength="30" />
						</span>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="title"><s:text name="remark"/>:</td>
					<td>
						<textarea style="width:200px;height:100px;" name="sp.remark" class="easyui-validatebox" validType="length[0,150]">${sp.remark}</textarea>
					</td>
				</tr>
				</tbody>
			</table>
			<s:hidden name="sp.paramValue" />
			<s:hidden name="sp.status"/>
			<s:hidden name="sp.buildUserId"/>
			<s:hidden name="sp.reviewUserId"/>
			<s:hidden name="sp.version"/>
			<s:hidden name="sp.paramId"/>
			<s:hidden name="sp.miNo"/>
			<input type="hidden" name="sp.codeKey" id="add_sp_codeKey" value='<s:property value="sp.codeKey"/>'/>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	<s:if test="sp.codeKey != null">
		$("#sp_temp_value_inp").empty();
	</s:if>
	<s:else>
		$("#sp_tempValue_combo").xcombobox("disable").hide().xcombobox("disValidate");
	</s:else>
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>