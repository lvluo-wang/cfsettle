<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
			<table cellspacing="0" cellpadding="0" width="100%">
				<tbody>
					<tr>
						<td class="title"><s:text name="AcctPoint.pointName"/>:</td>
						<td>
							<input id="acctItem_pointName" class="inputSel easyui-validatebox" readonly="readonly" required="true" onclick="selectAcctPoint('acctItem_pointId','acctItem_pointName','acctPointSelect')"/>
							<input type=hidden id="acctItem_pointId" name="acctTran.pointId"><font color=red>*</font>
						</td>
					</tr>
					<tr>
						<td class="title"><s:text name="AcctTran.tranNo"/>:</td>
						<td>
							<input id="acctTran_tranNo" name="acctTran.tranNo" maxlength="50" class="easyui-validatebox" required="true" /><font color=red> *</font>
						</td>
					</tr>
					<tr>
						<td class="title"><s:text name="AcctTran.tranName"/>:</td>
						<td>
							<input id="acctTran_tranName" name="acctTran.tranName" maxlength="25" class="easyui-validatebox" required="true" /><font color=red> *</font>
						</td>	
					</tr>
					<tr>					
						<td class="title"><s:text name="AcctTranExpr.express"/>:</td>
						<td>
							<textarea rows="2"  cols="3" id="acctTran_express" name="acctTran.express"  class="easyui-validatebox"  validType="textLength['0','300']" style="width:160px"/>
						</td>
					</tr>
					
				</tbody>
			</table>					
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" effect="round" text="cancel"/>		
	</tiles:putAttribute>	
	<tiles:putAttribute name="window">
		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			function doSave(){
				if($("#editForm").form("validate")){
					var url = "<s:url value='/acctConfig/acctTranAction_createAcctTran.jhtml'/>";
					//formSubmitForDatagrid(url,"editForm","dataTable");
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
				dataTable.refresh();
			}
		</script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>