<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
               <form id="configForm" class="busi_form" method="post"> 
               		<s:hidden name="member.miNo" />
                    <fieldset>
                        <legend><s:text name="memberInfo.config"/></legend>
                        <table cellspacing="0" cellpadding="0">
                       	<tr>
                           	<td class="td_label" align="right" ><s:text name="config.pwdEffectDays"/>:</td>
                            <td class="td_input" align="left">
								<!--
								<input class="easyui-validatebox" required="true" validType="positive_int_zero" name="config.pwdEffectDays" value="${config.pwdEffectDays }" maxLength="10"/>
								-->
								<input class="easyui-numberbox" name="config.pwdEffectDays" min="0" max="100" required="true" value="${config.pwdEffectDays }" />
								<font color="red">*</font>
							</td>                      
                        </tr>
                        <tr>
							<td class="td_label" align="right" ><s:text name="config.errAllowNum"/>:</td>
							<td class="td_input" align="left">
								<!--
								<input class="easyui-validatebox" required="true" validType="positive_int_zero" name="config.errAllowNum" value="${config.errAllowNum }" maxLength="10" />
								-->
								<input class="easyui-numberbox" name="config.errAllowNum" min="0" max="10" required="true" value="${config.errAllowNum }" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
                           <td class="td_label" align="right" ><s:text name="config.pwdInit"/>:</td>
                           <td class="td_input" align="left"><input type="password" class="easyui-validatebox" name="config.pwdInit" required="true" validType="safepass" value="${config.pwdInit }"/><font color="red">*</font></td>
                        </tr>
                        <tr>
                           <td class="td_label" align="right" ><s:text name="config.isMultiIp"/>:</td>
                           <td class="td_input" align="left">
							<x:combobox name="config.isMultiIp" value="${config.isMultiIp}"  list="yesNo" pleaseSelect="false"/>
							</td>
                        </tr>
                        <tr>
                           <td class="td_label" align="right" ><s:text name="config.isOnlineLogon"/>:</td>
                           <td class="td_input" align="left">
							<x:combobox name="config.isOnlineLogon" value="${config.isOnlineLogon}" list="yesNo" pleaseSelect="false"/>
							</td>
                        </tr>
                    </table>
                    </fieldset>
                 </form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="btn_edit_member" click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function doSave(){
		var url = "<s:url value='/security/member_saveConfig.jhtml'/>";
		doPost(url,formToObject("configForm"),function(result){
			if(result){
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
				}
				return;
			}
			memberMainDG.refresh();
			$('#member_config').window('close');
		});
	}
	
	function doCancel(){
		$("#member_config").window("close");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>