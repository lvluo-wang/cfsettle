<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>


<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
			<s:hidden id="taskId" name="autoTask.id"></s:hidden>	
			<s:hidden id="autoTask_memberNos" name="autoTask.memberNos" value=""></s:hidden>	
			<s:hidden id="autoTask_dependTasks" name="autoTask.dependTasks" value=""></s:hidden>	
			 <table>
				 <colgroup>
					<col width="30%"/>
		            <col width="36%"/>
		            <col width="30%"/>
		            <col width="36%"/>	
	            </colgroup>	
	            <tbody>
                 <tr>
                 	 <td class="title"><s:text name="taskName" /></td>
                     <td><input name="autoTask.name" value="${autoTask.name}" class="easyui-validatebox" required="true"/><font color=red>*</font></td>
                     <td class="title"><s:text name="cronExpr" /></td>
                     <td><input id="task_cronExpr" name="autoTask.cronExpr" value="${autoTask.cronExpr}" class="easyui-validatebox"  required="true" /><font color=red>*</font></td>                 	
                 </tr>
                 <tr>
                 	 <td class="title"><s:text name="className" /></td>
                     <td  colspan="3"><input id="task_className"  name="autoTask.className" value="${autoTask.className}"  style="width:442px;" class="easyui-validatebox"  required="true" /><font color=red>*</font></td>
                 </tr>
                 <tr>
                 	 <td class="title"><s:text name="para" /></td>
                     <td  colspan="3"><input name="autoTask.para" value="${autoTask.para}" style="width:442px;" class="easyui-validatebox"/></td>
                 </tr>
                 
                 <tr>
                     <td class="title"><s:text name="taskType" /></td>
                     <td>
						<x:combobox name="autoTask.taskType" id="taskType" list="taskTypeCodeList" onchange="changeTaskType(this.value);"/>
						<font color=red>*</font>
                     </td>
                     
                     <td class="title"><s:text name="member" /></td>
                     <td  rowspan="3">
                     	<select multiple="multiple" disabled="disabled" style="width:130px; height:90px;" name="memberNos" id="memberNos">
									</select>
									<div id="memberNosDiv1">
										任务类型为“通用类型”时，接入点项无效。
									</div>
									<div id="memberNosDiv2" style="display:none">
										<x:button icon="icon-add" click="chooseMultiMember" text=""/>
										<x:button icon="icon-remove" click="removeSelectedOption( $('#memberNos'))" text=""/>
									</div>
                     </td>
                     
                  </tr>
                  
                  <tr>
                     <td class="title"><s:text name="dependTask" /></td>
                     <td>
                     	<select multiple="multiple" style="width:130px; height:54px;"  id="dependTasks" name="dependTasks" >
											
						</select>
						<div>
							<x:button icon="icon-add" click="chooseMultiTask" text=""/>
							<x:button icon="icon-remove" click="removeSelectedOption( $('#dependTasks'))" text=""/>
						</div>
                     </td>
                    
                  </tr>
                  
                   <tr>
                     <td class="title"><s:text name="dependOutTime" /></td>
                     <td><input name="autoTask.dependOutTime" value="${autoTask.dependOutTime}" class="easyui-validatebox" validType="number" /></td>
                     
                  </tr>
                  
                   <tr>
                     <td class="title"><s:text name="nextTask" /></td>
                     <td>
						<x:combobox name="autoTask.nextTask" list="autoTasks" textField="name" valueField="id"/>
					</td>
                    
                  </tr>
                  
                 
	           </tbody> 
            </table>	
		</form>	 	
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave"  effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel"  effect="round" text="cancel"/>		
	</tiles:putAttribute>	
	<tiles:putAttribute name="window">
		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">	
		//-----多选select操作功能selComp=$('#id')
		function selectedAll(selComp){//选中所有项
			var size = selComp.get(0).options.length;
			for (i=0; i<size; i++){
				selComp.get(0).options[i].selected=true ;
			}
			
		}
		function isExistItem(selComp, value){
			var size = selComp.get(0).options.length;
			for (i=0; i<size; i++){				
				if (selComp.get(0).options[i].value == value)
					return true;
			}
			return false;
		}
		function getAllOptionValue(selComp){//得到所有项的值，包含未选中的
			var size = selComp.get(0).options.length;
			var v="";
			for (i=0; i<size; i++){
				v=v+selComp.get(0).options[i].value+",";
			}
			return v;
			
		}
		
		function addOption(selComp,value,text){//增加项
			//$("<option value='112' selected='selected'>1111</option>").appendTo("#m1");
			if (!isExistItem(selComp, value))
				selComp.get(0).options.add(new Option(text,value,null,true)); 
		}
		
		function removeSelectedOption(selComp){//删除选中的项
			//alert($('#m1').get(0).selectedIndex); 
			while(selComp.get(0).selectedIndex>=0){
				selComp[0].remove(selComp.get(0).selectedIndex);
			}	
		}
		
		function emptySelect(selComp){//删除选中的项
			selComp.empty();
		}
		
		$(function(){//初始化信息
			changeTaskType($("#taskType"));
			
			<s:iterator value="dependTasks" >
		 		addOption($("#dependTasks"),'${id}','${name}');						 	
			</s:iterator>
			
			<s:iterator value="memberInfos" >
				addOption($("#memberNos"),'${miNo}','${miName}');						 	
		</s:iterator>
			
		});
			
			
			function changeTaskType(val){
				if (val==1){
					$("#memberNos").attr("disabled","disabled");
					$("#memberNosDiv1").show();
					$("#memberNosDiv2").hide();
				}else{
					$("#memberNos").attr("disabled",null);
					$("#memberNosDiv1").hide();
					$("#memberNosDiv2").show();
				}
			
			}
			
			function chooseMultiMember(){
				var url = "<s:url value='/autotask/define_toChooseMemberList.jhtml'/>";
				url=url+'?idDiv=memberNos&winId=memberListWindow';
				requestAtWindow(url,"memberListWindow","选择依赖任务");
			
			}
			
			function chooseMultiTask(){
				var url = "<s:url value='/autotask/define_toChooseTaskList.jhtml'/>";
				url=url+'?idDiv=dependTasks&winId=taskListWindow';
				requestAtWindow(url,"taskListWindow","选择依赖任务");
			
			}
			
			function removeSelectedOption(selComp){//删除选中的项
				//alert($('#m1').get(0).selectedIndex); 
				while(selComp.get(0).selectedIndex>=0){
					selComp[0].remove(selComp.get(0).selectedIndex);
				}	
			}
				
			function doSave(){
				selectedAll($("#memberNos"));
				selectedAll($("#dependTasks"));
				$("#autoTask_memberNos").attr('value',$("#memberNos").val());
				$("#autoTask_dependTasks").attr('value',$("#dependTasks").val());				
				
				if($("#busi_form").form("validate")){
					var url = "<s:url value='/autotask/define_update.jhtml'/>";
					var parm=formToObject("busi_form");
					doPost(url,parm,callBack);
				}
			}
			function callBack(result){
				if(result){
					if (result.indexOf("rs:1")>=0){		
						$("#task_cronExpr").tipTip({
							show:true,
							activation: "focus",
							content:'表达式不符',
							defaultPosition: "right",
							autoHideTime:7
						});
						$("#task_cronExpr").focus();
						$("#task_cronExpr").unbind( "focus" );
						
						return
					}
					if (result.indexOf("rs:2")>=0){
						$("#task_className").tipTip({
							show:true,
							activation: "focus",
							content:'类不存在，或路径不符',
							defaultPosition: "right",
							autoHideTime:7
						});
						$("#task_className").focus();
						$("#task_className").unbind( "focus" );
						return;		
					}
					
					if(result.indexOf('{')==0){
						var obj=eval('('+result+')');
						if(obj.error){
							error(obj.error);
							obj=null;
							return;
						}
					}
				}
				$("#add_edit").window("close");
				if(dataTable){
					dataTable.refresh();
				}
			}
				
			function doCancel(){
				$("#add_edit").window("close");
			}
	</script>
		
	</tiles:putAttribute>

</tiles:insertDefinition>

