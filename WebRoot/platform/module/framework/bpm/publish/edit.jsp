<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
			<s:hidden name="process.id" />    
            <s:hidden name="process.version" />    
			<s:hidden name="process.jbpmPdId" />    
                  <table>
				 <colgroup>
					<col width="40%"/>
		            <col width="60%"/>
	            </colgroup>	
	            <tbody>
                   <tr>
                       <td class="title"><s:text name="process.procName"/>:</td>
                       <td><input class="easyui-validatebox" required="true" maxlength="25" name="process.procName" value="${process.procName}"/></td>                        
                   </tr>
                   
                   <tr>
                       <td class="title"><s:text name="process.desiProdNo"/>:</td>
                       <td><input class="easyui-validatebox" required="true" maxlength="25"  name="process.desiProdNo" value="${process.desiProdNo}"/></td>                        
                   </tr>
                   
                	 <tr>
                       <td class="title"><s:text name="process.procCnName"/>:</td>
                       <td><input id="bankNo"  class="easyui-validatebox" maxlength="25"  name="process.procCnName" value="${process.procCnName}"/></td>                        
                   </tr>
                   <tr>
                       <td class="title"><s:text name="process.procNameKey"/>:</td>
                       <td><input id="bankNo"  class="easyui-validatebox" maxlength="25"  name="process.procNameKey" value="${process.procNameKey}"/></td>                        
                   </tr>
                 </tbody> 
            </table>
		</form>	 	
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" effect="round" text="cancel"/>		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">		
	<script type="text/javascript">
		function doSave(){
		  if($("#busi_form").form("validate")){
			var url = "<s:url value='/bpm/publish_editSave.jhtml'/>";
			//formSubmitForDatagrid(url,"editForm","dataTable");
			var parm=formToObject("busi_form");
			doPost(url,parm,function(){
				dataTable.refresh();
				$('#add_edit').window('close');
			});
			
			
		  }
		}
		function doCancel(){
			$("#add_edit").window("close");
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
