<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form  id="busi_form" class="busi_form">
			<s:hidden name="acctTran.id"></s:hidden>
			<s:hidden name="acctTran.miNo"></s:hidden>
			<table>
					<tbody>
						<tr>
							<td class="title"><label for="<s:text name="AcctPoint.pointName"/>"><s:text name="AcctPoint.pointName"/>:</label></td>
							<td>
								<input id="acctItem_pointName" class="inputSel easyui-validatebox" value="${acctTran.pointName}" readonly="true" required="true" onclick="selectAcctPoint('acctItem_pointId','acctItem_pointName','acctPointSelect')"/>
								<input type=hidden id="acctItem_pointId" name="acctTran.pointId"  value="${acctTran.pointId}" /><font color=red> *</font>
							</td>
						</tr>
						<tr>
							<td class="title"><label for="<s:text name="AcctTran.tranNo"/>"><s:text name="AcctTran.tranNo"/>:</label></td>
							<td>
								<input id="acctTran_tranNo" name="acctTran.tranNo" maxlength="50" value="${acctTran.tranNo}" class="easyui-validatebox" required="true" /><font color=red> *</font>
							</td>
						</tr>
						<tr>
							
							<td class="title"><label for="<s:text name="AcctTran.tranName"/>"><s:text name="AcctTran.tranName"/>:</label></td>
							<td>
								<input id="acctTran_tranName" name="acctTran.tranName" maxlength="25" value="${acctTran.tranName}" class="easyui-validatebox" required="true" /><font color=red> *</font>
							</td>
						</tr>
						<tr>
							<td class="title"><label for="<s:text name="AcctTranExpr.express"/>"><s:text name="AcctTranExpr.express"/>:</label></td>
							<td>
								<textarea id="acctTran_express" name="acctTran.express" class="easyui-validatebox"  validType="textLength['0','300']" >${acctTran.express}</textarea>
							</td>
						</tr>
						<tr>								
							
						</tr>
					</tbody>
				</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel"  effect="round" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">	
			function doSave(){
				if($("#editForm").form("validate")){
					var url = "<s:url value='/acctConfig/acctTranAction_editAcctTran.jhtml'/>";
					//formSubmitForDatagrid(url,"editForm","dataTable");
					//$('#add_edit').window('close');
					var parm=formToObject("busi_form");
					doPost(url,parm,function(){						
						$('#add_edit').window('close');
						if(dataTable){
							dataTable.refresh();
						}
					});
				}
			}
			function selectAcctPoint(id_, name_, div_){
				var url="<s:url value='/acctConfig/acctPointAction_listAcctPointForChoose.jhtml'/>?flag="+Math.random()*99999;
				url += "&AcctPointIdInputor="+id_+"&AcctPointNameInputor="+name_+"&ShowAcctPointDiv="+div_;
				requestAtWindow(url,"acctPointSelect","<s:text name="AcctPoint.selectAcctPoint"/>");
			}
			
			function doCancel(){
				$('#add_edit').window('close');
			}
			var str = $("#acctItem_pointName").val();
			$("#acctItem_pointName").val(str+acctTranCode.getValue('B023','${acctItem.pointId_eventNo}'));
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>