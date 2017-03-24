<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
               <form id="addForm" class="busi_form" name="addForm" method="post">             
                      <table>
                      	 <colgroup>
		                    <col width="35%"/>
		                    <col width="65%"/>
		                 </colgroup>
						 <tbody>
                        <tr>
                            <td class="title"><s:text name="memberInfo.miNo"/>:</td>
                            <td><input class="easyui-validatebox" required="true" validType="miNo" name="member.miNo" maxLength="20"/><font color="red">*</font></td>                     
                        </tr>
                        <tr>
                            <td class="title"><s:text name="memberInfo.miName"/>:</td>
                            <td><input class="easyui-validatebox" required="true" name="member.miName" validType="miName" maxLength="50"/><font color="red">*</font></td>                     
                        </tr>
                        <tr>
							<td class="title"><s:text name="memberInfo.miType"/>:</td>
							<td>
								<x:combobox id="miType" name="member.miType" valueField="codeNo" textField="codeName" list="miType" pleaseSelect="false" onchange="changeMiType()"/>
							</td>
						</tr>
						<tr id="bankNoDiv">
                           <td class="title"><s:text name="unionBankNo"/>:</td>
                           <td><input id="bankNo"  class="easyui-validatebox" name="member.bankNo" validType="bankNo" maxLength="12"/><font color="red">*</font></td>
                        </tr>
                        <tr id="orgCodeDiv" >
                           <td class="title"><s:text name="memberInfo.orgCode"/>:</td>
                           <td><input id="orgCode"  class="easyui-validatebox" name="member.orgCode"  maxLength="11"/><font color="red">*</font></td>
                        </tr>
                        <tr>
                           <td class="title"><s:text name="memberInfo.miKey"/>:</td>
                           <td><textarea class="easyui-validatebox" name="member.miKey" validType="length[0,200]"/></td>
                        </tr>
                       	<tr>
                            <td class="title"><s:text name="memberInfo.ebKey"/>:</td>
                            <td><textarea class="easyui-validatebox" name="member.ebKey" validType="length[0,200]"/></td>
                        </tr>
                        </tbody>
                    </table>
                 </form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="btn_add_member" click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function changeMiType(){
		var miType = $("#miType").xcombobox("getValue");
		if(miType=='1'){
			$("#bankNoDiv").show();
			$("#orgCodeDiv").hide();
			$('#bankNo').validatebox({
				required:true
				});
			$('#orgCode').validatebox({
				required:false
				});
		}
		if(miType=='2'){
			$("#bankNoDiv").hide();
			$("#orgCodeDiv").show();
			$('#bankNo').validatebox({
				required:false
				});
			$('#orgCode').validatebox({
				required:true
				});
		}
		if(miType=='3'){
			$("#bankNoDiv").hide();
			$("#orgCodeDiv").show();
			$('#bankNo').validatebox({
				required:false
				});
			$('#orgCode').validatebox({
				required:true
				});
		}
	}
	changeMiType();
	function doSave(){
		var miType=$("#miType").xcombobox("getValue");
		if(miType !='1' )
			$("#bankNo").attr("value","");
		//alert($("#bankNo").val());
		var url = "<s:url value='/security/member_save.jhtml'/>";
		doPost(url,formToObject("addForm"),function(result){
			if(result){
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
				}
				return;
			}
			memberMainDG.refresh();
			$('#member_add_edit').window('close');
		});
	}
	function doCancel(){
		$("#member_add_edit").window("close");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>