<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form  id="editForm" class="busi_form">
					<s:hidden name="loanInfo.id" />
					<s:hidden name="loanInfo.status" />	
					<table>
					<colgroup>
						<col width="45%"/>
						<col width="55%"/>
					</colgroup>
						<tbody>
							<tr>
								<td class="title">贷款编号:</td>
								<td><s:textfield name="loanInfo.loanNo" id="add_loanNo" class="easyui-validatebox" required="true" ></s:textfield></td>
							</tr>
							<tr>
								<td class="title">贷款人名称:</td>
								<td><s:textfield name="loanInfo.userName" id="add_userName" class="easyui-validatebox" required="true" ></s:textfield></td>
							</tr>
							<tr>
								<td class="title">贷款金额:</td>
								<td><s:textfield name="loanInfo.money" id="add_money"  class="easyui-validatebox"></s:textfield></td>
							</tr>
						</tbody>
					</table>				
					<div>
								
					</div>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function doSave(){
		if($("#editForm").form("validate")){
			var url = "<s:url value='/demo/loan_editSave.jhtml'/>";
			doPost(url,formToObject("editForm"),function(result){
				if(!prindError(result)){
					$('#add_edit').window('close');
				}
				mydatagrid.refresh();
			});
		}
	}
	
	jQuery('document').ready(function(){
		//初始化验证
		var m = fmoney(jQuery('#add_money').val());
		$('#add_money').attr("value", m);
		
	});
	
	function fmoney(s){	  
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
	   var l = s.split(".")[0].split("").reverse(),
	   r = s.split(".")[1];
	   t = "";
	   for(i = 0; i < l.length; i ++ ){
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "" : "");
	   }
	   return t.split("").reverse().join("") + "." + r;
	}
	
	function rmoney(s){
		var s = $('#add_money').attr("value", m);
		var m = parseFloat(s.replace(/[^\d\.-]/g, ""));
		$('#add_money').attr("value", m);
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>