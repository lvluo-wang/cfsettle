<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
			<s:hidden name="prod.id"/>
					<s:hidden name="prod.parentProdId"/>
					<s:hidden name="prod.version"/>
					<s:hidden name="prod.sortNo"/>
						<table >
						<tr>
							<td class="title"><s:text name="product"/><s:text name="no"/>:</td>
							<td><input class="easyui-validatebox" maxlength="30" required="true" name="prod.prodNo" value="${prod.prodNo}" validType="userNo" /><font color=red>*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="product"/><s:text name="name"/>:</td>
							<td><input  value="${prod.prodName}"  class="easyui-validatebox" maxlength="50" required="true" name="prod.prodName" validType="fieldName" /><font color=red>*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="product"/><s:text name="name"/><s:text name="i18n"/>:</td>
							<td><input  value="${prod.prodNameKey}"  class="easyui-validatebox" maxlength="30" required="true" name="prod.prodNameKey" validType="userNo"/><font color=red>*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="product"/><s:text name="type"/>:</td>
							<td>
								<x:combobox name="prod.prodType" list="prodType"/>
								<font color=red>*</font>
							</td>
						</tr>
						<tr>
							<td class="title">URL:</td>
							<td><input  value="${prod.prodUrl}"  name="prod.prodUrl" /></td>
						</tr>
						<tr>
							<td class="title"><s:text name="remark"/>:</td>
							<td><textarea  class="easyui-validatebox"  required="true"  name="prod.remark" >${prod.remark}</textarea><font color=red>*</font></td>
						</tr>
					</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave"  effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel"  effect="round" text="cancel"/>		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		function doSave(){
			if($("#busi_form").form("validate")){
				$.messager.confirm(global.alert,global.update_confirm_info,function(r){
					if(r){
						var url = "<s:url value='/product/product_update.jhtml'/>";
						var parm=formToObject("busi_form");
						doPost(url,parm,callBack);
						//parm=null;
					}
				});
				
			}
		}
		function callBack(result){
			if(result){
				if(result.indexOf('{')==0){
					var obj=eval('('+result+')');
					if(obj.error){
						error(obj.error);
						obj=null;
						return;
					}
				}
			}
			var node=$("#productTree").tree('getSelected');
			if(node){
				var parentNode=$("#productTree").tree('getParent',node.target);
				if(parentNode){
					$("#productTree").tree('reload',parentNode.target);
					parentNode=null;
				}else{
					$("#productTree").tree('reload');
				}
				node=null;
			}else{
				$("#productTree").tree('reload');
			}
			$("#product_add_edit").window('close');
		}
		function doCancel(){
			$("#product_add_edit").window("close");
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>