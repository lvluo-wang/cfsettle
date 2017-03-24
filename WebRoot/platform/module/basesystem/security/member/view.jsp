<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
	 <tiles:putAttribute name="body">
	 	<div class="win_form_area">
               <form id="addForm" class="busi_form" name="addForm"  method="post"> 
               		<s:hidden name="member.miNo" />     
               		<s:hidden name="member.isOpen" />
               		<s:hidden name="member.miDt"/>
                      <table>
                         <colgroup>
		                    <col width="35%"/>
		                    <col width="65%"/>
		                 </colgroup>
						 <tbody>
                      	<tr>
                            <td class="title"><s:text name="memberInfo.miNo"/>:</td>
                            <td>${member.miNo }</td>                       
                        </tr>
                        <tr>
                            <td class="title"><s:text name="memberInfo.miName"/>:</td>
                            <td>${member.miName }</td>                       
                        </tr>
                        <tr>
							<td class="title"><s:text name="memberInfo.miType"/>:</td>
							<td>
							<x:combobox id="miType" name="member.miType" disabled="true" value="${member.miType}" valueField="codeNo" textField="codeName" list="miType" pleaseSelect="false" onchange="changeMiType()"/>
							</td>
						</tr>
						<tr id="bankNoDiv">
                           <td class="title"><s:text name="unionBankNo"/>:</td>
                           <td>${member.bankNo }</td>
                        </tr>
                        <tr id="orgCodeDiv">
                           <td class="title"><s:text name="memberInfo.orgCode"/>:</td>
                           <td>${member.orgCode }</td>
                        </tr>
                        <tr>
                           <td class="title"><s:text name="memberInfo.miKey"/>:</td>
                           <td>${member.miKey }</td>
                        </tr>
                        <tr>
                            <td class="title"><s:text name="memberInfo.ebKey"/>:</td>
                            <td>${member.ebKey }</td>
                        </tr>
                        </tbody>
                    </table>
                 </form>
                 </div>
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
	function closeView(){
		$("#member_add_edit").window("close");
		
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>