<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form2" id="busi_form2">
			<s:hidden name="id"></s:hidden>		
			<table cellspacing="0" cellpadding="0" width="100%">
				<tbody>
					<tr>
   						<td class="title" width="15%">键:</td>
   						<td><s:textfield name="vi.name" required="true"  class="easyui-validatebox"/></td>
    				</tr> 
			      	<tr> 
			      		<td class="title">值类型:</td>
			      		<td><s:select onchange="changeType(this.value)" list="#{'1':'String','2':'Long','3':'Integer','4':'Boolean','5':'Double','6':'Date'}" name="vi.type"></s:select></td>
				    </tr> 
			      	<tr>
			      		<td class="title">值:</td>
			      		<td><s:textfield  id="1" name="vi.stringVal"/>
      						<s:textfield  id="2" name="vi.LongVal" />
      						<s:textfield  id="3" name="vi.intVal"/>
      						<s:checkbox  id="4" name="vi.booleanVal"/>
      						<s:textfield  id="5" name="vi.DoubleVal"/>
      						<div id="6">
      							<input class="inputsel easyui-datebox" maxLength="120"  name="vi.DateVal" />
      						</div>
			      		</td>
				    </tr> 
			      	
			      	<tr>
			      		<td>&nbsp;<br></td>
			      	</tr>
			      	<tr>
			      		<td rowspan="2" colspan="2">注：如果键名相同则覆盖原值</td>
			      	</tr>								
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doVarSave" text="save"/>
		<x:button icon="icon-cancel" click="doVarCancel" text="cancel"/>		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		$(function(){
			
			changeType('1');
			
		});
			function doVarSave(){
				if($("#busi_form2").form("validate")){
								
					var url = "<s:url value='/bpm/activity_createVarInst.jhtml' />";
					//formSubmitForDatagrid(url,"editVariableForm","variableDataTable");
					
					var parm=formToObject("busi_form2");
					doPost(url,parm,function(){
						$('#add_edit_variable').window('close');
						variableDataTable.refresh();
					});
				}
			}
			
			function doVarCancel(){
				$("#add_edit_variable").window("close");
			}
			
			function changeType(id){
				var i=1;
		 		while(i<=6){ 			
		 			var obj = document.getElementById(i); 
		 			if (i==id)
		 				obj.style.display='block';
		 			else{
		 				obj.style.display='none';
		 			}		            	
		 			i++;
		 		}    		
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
