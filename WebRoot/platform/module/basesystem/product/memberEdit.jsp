<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form id="busi_form" class="busi_form">
					<s:hidden name="memberProd.id"/>
					<s:hidden name="memberProd.miNo"/>
					<s:hidden name="memberProd.prodId"/>
					<s:hidden name="memberProd.version"/>
					<s:hidden name="memberProd.parentProdId"/>
					<s:hidden name="memberProd.sortNo"/>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td class="title"><s:text name="product"/><s:text name="no"/>:</td>
							<td><input class="easyui-validatebox" readonly="readonly"  disabled="disabled" required="true" name="prod.prodNo" value="${prod.prodNo}" /><font color=red>*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="product"/><s:text name="name"/>:</td>
							<td><input  value="${prod.prodName}" readonly="readonly" disabled="disabled" name="prod.prodName"/><font color=red>*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="product"/><s:text name="name"/>本地化:</td>
							<td><input  value="${memberProd.prodAlias}" class="easyui-validatebox" maxlength="25" name="memberProd.prodAlias"/></td>
						</tr>
						<tr>
							<td class="title">需要审批:</td>
							<td>
	           					<x:combobox list="yesNoList" name="memberProd.isAudit" required="true"/>
								<font color=red>*</font>
							</td>
						</tr>
						<tr>
							<td class="title">需要复核:</td>
							<td>
								<x:combobox list="yesNoList" name="memberProd.isCheck" required="true"/>
								<font color=red>*</font>
							</td>
						</tr>						
					</table>
					
				</form> 
	</tiles:putAttribute>
<tiles:putAttribute name="button">
	<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
	<x:button icon="icon-cancel" click="doCancel" effect="round"  text="cancel"/>
</tiles:putAttribute>
<tiles:putAttribute name="end">
	<script type="text/javascript">
		function doSave(){
			if($("#busi_form").form("validate")){
				$.messager.confirm(global.alert,global.update_confirm_info,function(r){
					if(r){
						var url = "<s:url value='/product/product_saveMemberProduct.jhtml'/>";
						var parm=formToObject("busi_form");
						doPost(url,parm,callBack);
						parm=null;
					}
				});
				
			}
		}
		
		function maxLength50(val){
			if(val.value.length>20){
				var chlCode = val.value;   
				var totalLength = chlCode.length;   
				//匹配汉字      
				var charMatch = chlCode.match(/[\u4e00-\u9fa5]/g);      
				//汉字个数      
				var charNum = charMatch ? charMatch.length : 0;    
				var num = totalLength -charNum + charNum * 2;    
				if(num > 50){	   
					val.value =  val.value.substring(0,val.value.length-1);
				}
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
