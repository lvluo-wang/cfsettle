<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
			<s:hidden name="parentProd.id"/>
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td class="title"><s:text name="parent"/><s:text name="product"/><s:text name="name"/>:</td>
								<td><input value="${parentProd.prodName}" name="parentProd.prodName" disabled="true"/><font color=red>*</font></td>
							</tr>
							<tr>
								<td class="title"><s:text name="product"/><s:text name="no"/>:</td>
								<td ><input id="product_prodNo" class="easyui-validatebox"  required="true" name="prod.prodNo"  validType="userNo"/><font color=red>*</font></td>
							</tr>
							<tr>
								<td class="title"><s:text name="product"/><s:text name="name"/>:</td>
								<td ><input id="product_prodName" class="easyui-validatebox"  required="true" name="prod.prodName" validType="fieldName"/><font color=red>*</font></td>
							</tr>
							<tr>
								<td class="title"><s:text name="product"/><s:text name="name"/><s:text name="i18n"/>:</td>
								<td ><input  id="product_prodNameKey" class="easyui-validatebox"  required="true" name="prod.prodNameKey"  validType="userNo"/><font color=red>*</font></td>
							</tr>
							<tr>
								<td class="title"><s:text name="product"/><s:text name="type"/>:</td>
								<td >
									<x:combobox list="prodType" required="true" name="prod.prodType"/>
									<font color=red>*</font>
								</td>
							</tr>
							<tr>
								<td class="title">URL:</td>
								<td ><input  id="product_prodUrl" class="easyui-validatebox"  name="prod.prodUrl"/></td>
							</tr>
							<tr>
								<td class="title"><s:text name="remark"/>:</td>
								<td ><textarea  id="product_remark" class="easyui-validatebox"  required="true"  name="prod.remark" /><font color=red>*</font></td>
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
					var url = "<s:url value='/product/product_save.jhtml'/>";
					var parm=formToObject("busi_form");
					doPost(url,parm,callBack);
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
					var childNode=$("#productTree").tree('getChildren',node.target);
					if(childNode&&childNode.length>0){
						$("#productTree").tree('reload',node.target);
						
					}else{
						var parentNode=$("#productTree").tree('getParent',node.target);
						if(parentNode){
							$("#productTree").tree('reload',parentNode.target);
						}else{
							$("#productTree").tree('reload');
						}
						parentNode=null;
					}
					$("#product_add_edit").window('close');
					childNode=null;
					node=null;
				}else{
					$("#productTree").tree('reload');
					$("#product_add_edit").window('close');
				}
			}
			function doCancel(){
				$("#product_add_edit").window("close");
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>



