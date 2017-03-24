<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
				<input type="hidden" id="ids" name="ids"/>
				<input type="hidden" id="status" />
			 <table>
				 <colgroup>
					<col width="30%"/>
		            <col width="70%"/>
	            </colgroup>	
	             <tbody>
	                 <tr>
							<td class="title"><label for="<s:text name="AcctPoint.prodName"/>"><s:text name="AcctPoint.prodName"/>:</label></td>
							<td>
								<input id="prodName2" class="inputSel" onClick="chooseBusiProduct(chooseBusiProductCallback)"/>
								<s:hidden  id="prodId2" name="acctPoint.prodNo" />
								<script>
									function chooseBusiProductCallback(row){
										if(row&&row.id&&row.prodName){
											$("#prodName2").val(row.prodName);
											$("#prodId2").val(row.prodNo);
										}
									}
								</script>
							</td>
						</tr>
						<tr>
							<td class="title"><label for="<s:text name="AcctPoint.eventName"/>"><s:text name="AcctPoint.eventName"/>:</label></td>
							<td>
							<x:combobox id="acctPoint_eventNo" name="acctPoint.eventNo"textField="codeName" valueField="codeNo" list="acctEventList"/>
								
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
				if($("#busi_form").form("validate")){
					var url = "<s:url value='/acctConfig/acctPointAction_createAcctPoint.jhtml'/>";
					//formSubmitForDatagrid(url,"editForm","dataTable");
					var parm=formToObject("busi_form");
					doPost(url,parm,function(){						
						$('#add_edit').window('close');
						if(dataTableForDetails){
							dataTableForDetails.refresh();
						}
					});
					
				}
			}
			
			function doCancel(){
				$('#add_edit').window('close');
				dataTableForDetails.refresh();
			}
		</script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>