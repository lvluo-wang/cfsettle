<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
			<s:hidden id="jsonArrayAcctItemList" name="jsonArrayAcctItemList"></s:hidden>
			<s:hidden id="jsonArrayTranItemList" name="jsonArrayTranItemList"></s:hidden>
			<form method="post" id="editForm" class="busi_form">
				<s:hidden name="groupNo"></s:hidden>
				<s:hidden name="tranId"></s:hidden>
				<s:hidden name="id"></s:hidden><!-- 第几行 -->
				
				<s:hidden name="recordBodyIds"></s:hidden>
				<s:hidden name="acctItemIds"></s:hidden>
				<s:hidden name="itemValues"></s:hidden>
				<table>		
					<colgroup>
							<col width="30%"/>
							<col width="30%"/>
							<col width="40%"/>						
						</colgroup>			
					<tbody>
						
						<tr>
							<td  class="head">项名称</td>
							<td  class="head">信息选择项</td>
							<td  class="head">值</td>
						</tr>
						<s:iterator value="groupBodyList" status="sta">
							<tr>
								<td class="title"><label for="<s:text name="AcctItem.itemName"/>"><s:property value="name"/>:</label></td>
								<td>
									<s:hidden id="bodyValueType%{id}" name="%{valueType}"/>						
									<s:select id="bodyId%{id}" name="bodyId%{id}" pk="bodyId" onchange="changeBodyItem(this)" list="{}" ></s:select>
																													
								</td>
								<td>
									<s:textfield id="bodyValue%{id}" name="bodyalue%{id}" pk="bodyValue" value="" class="easyui-validatebox" required="true"/><font color=red> *</font>
								</td>
							</tr>
						</s:iterator>							
					</tbody>
				</table>
				
			</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave"  effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel"  effect="round" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">		
		<div id="acctItemSelect"   style="width:800px;display:none;"></div>
		<div id="acctRecordBodySelect" style="width:800px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		var acctItemArr ;
		function initPage(){//初始化
			acctItemArr = eval('('+$("#jsonArrayAcctItemList").val()+')');
					
			$("select[pk='bodyId']").each(function(i){//遍历下拉项
				var valueType = $("#"+this.id.replace("bodyId","bodyValueType")).val();
				this.options.add(new Option('','-1',null,false));//-1表示为空
				for(i=0; i<acctItemArr.length;i++){
					if (valueType==acctItemArr[i].valueType){
						this.options.add(new Option(acctItemArr[i].itemName,acctItemArr[i].id,null,false)); 
					}
				}
				
			});	
			
			var tranItemArr = eval('('+$("#jsonArrayTranItemList").val()+')');
			for(i=0; i<tranItemArr.length;i++){
				$("#bodyId"+tranItemArr[i].recordBodyId).val(tranItemArr[i].acctItemId);//设置指定的value为选中
				//$("#bodyValue"+tranItemArr[i].recordBodyId).attr("value", tranItemArr[i].value);			
				initBodyItem("bodyValue"+tranItemArr[i].recordBodyId, tranItemArr[i].acctItemId);
				//$("#bodyValue"+tranItemArr[i].recordBodyId).attr("value", tranItemArr[i].value);
				$("#bodyValue"+tranItemArr[i].recordBodyId).val(tranItemArr[i].value);
				
			}
			
		}
		
		function initBodyItem(stid, value){
			
			var parent = $("#"+stid).parent();		
			parent.empty();	
			
			if (value=='-1'){
				var inputHTML = '<input type=text id='+stid+' pk=bodyValue class="easyui-validatebox" >';
				parent.append(inputHTML);
		
				return;
			}
			
			for(i=0; i<acctItemArr.length;i++){
				if (value==acctItemArr[i].id){				
					var inputHTML = '<input type=text id='+stid+' pk=bodyValue value='+acctItemArr[i].express+' class="easyui-validatebox" readonly=true>';
					parent.append(inputHTML);
					
					if(acctItemArr[i].express == null){//'null'会被解析成null					
						$("#"+stid).attr("readonly","");
						$("#"+stid).attr("value","");
					}else if(acctItemArr[i].express.indexOf('{')==0){
						parent.empty();	
						var inputHTML = '<select class=lang_select name=tranItem.value pk=bodyValue  id='+stid+'  class="easyui-validatebox" readonly=true>';
						parent.append(inputHTML);
						
						var express = acctItemArr[i].express.replace("{","");
						express = express.replace("}","");
						var exps = express.split(",");
						for(var i=0; i<exps.length; i++){
							var exs = exps[i].split(":");						
							$("#"+stid).get(0).options.add(new Option(exs[1], exs[0],null,false));						
						}
	
					}else{
						
					}
					
					 return;
				}
			}
		}
			
		function changeBodyItem(t){
			
			var stid =t.id.replace("bodyId","bodyValue");		
			var parent = $("#"+stid).parent();		
			parent.empty();	
			
			if (t.value=='-1'){
				var inputHTML = '<input type=text id='+stid+' pk=bodyValue class="easyui-validatebox" >';
				parent.append(inputHTML);
		
				return;
			}
			
			for(i=0; i<acctItemArr.length;i++){
				if (t.value==acctItemArr[i].id){				
					var inputHTML = '<input type=text id='+stid+' pk=bodyValue value='+acctItemArr[i].express+' class="easyui-validatebox" readonly=true>';
					parent.append(inputHTML);
					
					if(acctItemArr[i].express == null){//'null'会被解析成null					
						$("#"+stid).attr("readonly","");
						$("#"+stid).attr("value","");
					}else if(acctItemArr[i].express.indexOf('{')==0){
						parent.empty();	
						var inputHTML = '<select class=lang_select name=tranItem.value pk=bodyValue  id='+stid+'  class="easyui-validatebox" readonly=true>';
						parent.append(inputHTML);
						
						var express = acctItemArr[i].express.replace("{","");
						express = express.replace("}","");
						var exps = express.split(",");
						for(var i=0; i<exps.length; i++){
							var exs = exps[i].split(":");						
							$("#"+stid).get(0).options.add(new Option(exs[1], exs[0],null,false));						
						}
	
					}else{
						
					}
					
					 return;
				}
			}
		}
	
		function doSave(){
			
			if($("#editForm").form("validate")){
				var recordBodyIds = "";
				var acctItemIds = "";
				var itemValues = "";
				
				$("select[pk='bodyId']").each(function(i){//遍历下拉项
					recordBodyIds= recordBodyIds + this.id.substring(6)+',';
					acctItemIds = acctItemIds + this.value+',';
					
				});
				
				$("select[pk='bodyValue'],input[pk='bodyValue']").each(function(i){//遍历下拉项
					itemValues = itemValues + this.value+',';
				});
				
				$("#recordBodyIds").attr('value',recordBodyIds);
				$("#acctItemIds").attr('value',acctItemIds);
				$("#itemValues").attr('value',itemValues);
				
				var url = "<s:url value='/acctConfig/tranItemAction_modifyTranItem.jhtml'/>";
				
				//formSubmitForDatagrid(url,"editForm","dataTable");
				var parm=formToObject("editForm");
				doPost(url,parm,function(rs){dataGrid.load();});
				//dataGrid.refresh();
				
				$('#add_edit').window('close');
			}
		}
		
		function selectAcctItem(){
			var url="<s:url value='/acctConfig/acctItemAction_listAcctItemForChoose.jhtml'/>?flag="+Math.random()*99999;
			requestAtWindow(url,"acctItemSelect","<s:text name="AcctItem.selectAcctItem"/>");
		}
		
		function selectAcctRecordBody(){
			var url="<s:url value='/acctConfig/acctRecordBodyAction_listAcctRecordBodyForChoose.jhtml'/>?flag="+Math.random()*99999;
			requestAtWindow(url,"acctRecordBodySelect","<s:text name="AcctRecordBody.selectAcctRecordBody"/>");
		}
		
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
