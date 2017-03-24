<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
               <form id="editForm" class="busi_form" name="addForm"  method="post"> 
               		<s:hidden name="config.miNo" />
               		<s:hidden name="config.scId" />
               		<s:hidden name="config.version" />
                        <table>
                          <colgroup>
		                    <col width="35%"/>
		                    <col width="65%"/>
		                 </colgroup>
						 <tbody>
                        <tr>
                            <td class="title"><s:text name="config.pwdEffectDays"/>:</td>
                            <td>
								<!--
								<input class="easyui-validatebox" required="true" name="config.pwdEffectDays"  validType="positive_int_zero" value="${config.pwdEffectDays }"/>
								-->
								<input class="easyui-numberbox" name="config.pwdEffectDays" min="0" max="100" required="true" maxlength="8" value="${config.pwdEffectDays }" />
								<font color="red">*</font>
							</td>                    
                        </tr>
                        <tr>
							<td class="title"><s:text name="config.errAllowNum"/>:</td>
							<td>
								<!--
								<input class="easyui-validatebox" required="true" name="config.errAllowNum"  validType="positive_int_zero" value="${config.errAllowNum }"/>
								-->
								<input class="easyui-numberbox" name="config.errAllowNum" min="0" max="10" required="true" maxlength="8" value="${config.errAllowNum }" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
                           <td class="title"><s:text name="config.pwdInit"/>:</td>
                           <td><input type="password" class="easyui-validatebox" name="config.pwdInit" maxlength="30" required="true" validType="safepass" value="${config.pwdInit }"/><font color="red">*</font></td>
                        </tr>
						<tr>
                           <td class="title"><s:text name="config.sysParamCheck"/>:</td>
                           <td>
							<x:combobox name="config.sysParamCheck" value="${config.sysParamCheck}" valueField="codeNo" textField="codeName" list="yesNo" pleaseSelect="false"/>
							</td>
                        </tr>
                        <tr>
                           <td class="title"><s:text name="config.isMultiIp"/>:</td>
                           <td>
							<x:combobox name="config.isMultiIp" value="${config.isMultiIp}" valueField="codeNo" textField="codeName" list="yesNo" pleaseSelect="false"/>
							</td>
                        </tr>
                        <tr>
                           <td class="title"><s:text name="config.isOnlineLogon"/>:</td>
                           <td>
							<x:combobox name="config.isOnlineLogon" value="${config.isOnlineLogon}" valueField="codeNo" textField="codeName" list="yesNo" pleaseSelect="false"/>
							</td>
                        </tr>
                        </tbody>
                    </table>
                 </form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="btn_edit_config" click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function doSave(){
		if($("#editForm").form("validate")){
			$.messager.confirm(global.alert,global.update_confirm_info,function(r){
				if(r){
					var url = "<s:url value='/security/member_saveOrEditConfig.jhtml'/>";
					windowFormSubmitForDatagrid(url,"editForm","dataTable","member_config");
				}
			});
			
		}
	}
	function doCancel(){
		$("#member_config").window("close");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>