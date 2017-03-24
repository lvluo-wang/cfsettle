<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_QUERY_DATA">
	<tiles:putAttribute name="query">
		<s:hidden name="taskId" id="taskId" />
			<!--
					<b id='assign_type_p'><label for="分配类型">分配类型:</label>
						<s:select list="assignTypeList" listKey="codeNo" 
							listValue="codeName" name="assignType" id="assignType" onchange="doQueryActor()">
						</s:select>&nbsp;&nbsp;
					</b>	
				-->
					<input id="assignType" type="hidden" name="assignType" value="2"/>	
					<label for="分配机构" class="formPannel_label">分配机构:</label>
					<input id="branch_brchName" class="inputSel" onClick="chooseTreeBranch()"/>
					<!-- 
					<s:textfield class="formPannel_input" id="branch_brchName" name="takeBranch.brchName" disabled="true" required="true" />					
					<x:button icon="icon-search" click="chooseTreeBranch()"  text="选择机构"/>
					 -->
					<s:hidden name="takeBranch.brchId" id="branch_brchId"/>		
					<s:hidden name="takeBranch.brchNo" id="branch_brchNo"/>
					
					&nbsp;&nbsp;<INPUT TYPE="checkbox" NAME="isAppSubBrch" id="isAppSubBrch">应用于下级机构
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						
					<span id='to_brch_p'><label >授予机构:</label>
					
					<input id="toBrch_brchName" class="inputSel" onClick="chooseTreeBranch(selectTakeBranch)"/>
					<!-- 
					<s:textfield class="formPannel_input" id="toBrch_brchName" name="takeBranch.brchName" disabled="true" required="true" />					
					<x:button icon="icon-search" click="chooseTreeBranch(selectTakeBranch)"  text="选择机构"/>
				   -->
					<input type="hidden" name="toBrchId" id="toBrchId" value="${takeBranch.brchId}"/>		
							
					</span>
						
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<x:button icon="icon-save" click="doSave"  text="save"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<div>
		<table id="dataTable"></table>
		<br/><br/><br/>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		var query;//查询组件
		var dataGrid;//数据分页组件
		
			function doQueryActor(){
				var assignType = $("#assignType").val();
				var taskId = $("#taskId").val();
				var toBrchIdStr = $("#toBrchId").val();
				var takeBrchId = $("#branch_brchId").val();
				if (assignType=='2'){
					$("#to_brch_p").show();
					doQueryRole(taskId,takeBrchId,toBrchIdStr);
				}else{
					$("#to_brch_p").hide();
					doQueryBrch(taskId, toBrchIdStr);
				}
			}
			
			function doQueryRole(taskId,takeBrchId, toBrchIdStr){
				var keys=['B001'];
				var code=new CodeUtil(keys);
				code.loadData();
				// 定义冻结窗口列
				var frozenColumns=[[
				                    {field:'ck',checkbox:true,width:20}
				        		]];
				//定义显示列
				var columns=[[  
					  			{field:'roleName',title:'<s:text name="role"/><s:text name="name"/>',width:200,sortable:true,
									sorter:function(a,b){return (a>b?1:-1);}
								},
								{field:'roleType',title:'<s:text name="role"/><s:text name="type"/>',width:fixWidth(0.1),formatter:function(value,r){
										return code.getValue('B001',value);
									}}
							]];		
				
				//初始化数据组件
				dataGrid=new DataPageComponent("dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					fit:false,
					onLoadSuccess:function(data){
						//data.initSelecteds为自定义的已分配的index的数组
						if (data.initSelecteds){
							for (i=0; i<data.initSelecteds.length; i++ ){
								$("#dataTable").datagrid("selectRow",  data.initSelecteds[i]);
							}
						}						
					},
					url:'<s:url value="/bpm/taskassignex_roleAssignList.jhtml"/>?taskId='+taskId+'&toBrchIdStr='+toBrchIdStr + "&takeBranch.brchId="+takeBrchId
				});
				dataGrid.load();
			}
			function doQueryBrch(taskId, toBrchIdStr){
				// 定义冻结窗口列
				var frozenColumns=[[
				                    {field:'ck',checkbox:true,width:20}
				        		]];		
				//定义显示列
				var columns=[[  
					  			{field:'brchNo',title:'<s:text name="branch"/><s:text name="no"/>',width:200,sortable:true,
									sorter:function(a,b){return (a>b?1:-1);}
								},
								{field:'brchName',title:'<s:text name="branch"/><s:text name="name"/>',width:200,sortable:true,
									sorter:function(a,b){return (a>b?1:-1);}
								}
							]];		
				//初始化数据组件
				dataGrid=new DataPageComponent("dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					fit:false,
					onLoadSuccess:function(data){
						//data.initSelecteds为自定义的已分配的index的数组
						if (data.initSelecteds){
							for (i=0; i<data.initSelecteds.length; i++ ){
								$("#dataTable").datagrid("selectRow",  data.initSelecteds[i]);
							}
						}						
					},
					url:'<s:url value="/bpm/taskassignex_brchAssignList.jhtml"/>?taskId='+taskId+'&toBrchIdStr='+toBrchIdStr
				});
				dataGrid.load();
			}
			
			function doSave(){	
				var beIds=getAllSelected("dataTable","roleId");
				var unIds=getAllUnSelected("dataTable","roleId");
				var assignType = $("#assignType").val();
				var isChecked = $("#isAppSubBrch").get(0).checked ? '1':'0';
				if (assignType=='3'){
					beIds=getAllSelected("dataTable","brchId");
					unIds=getAllUnSelected("dataTable","brchId");
				}
				if(beIds.length<1 && unIds.length<1){
					return;
					info(global.notSelectInfo);
				}else{
					var taskId = $("#taskId").val();
					var toBrchIdStr = $("#toBrchId").val();
					var takeBranchId=$("#branch_brchId").val();
					var url = "<s:url value='/bpm/taskassignex_save.jhtml'/>?beIds="+beIds+"&unIds="+unIds
						+"&isChecked="+isChecked+"&assignType="+assignType+"&taskId="+taskId+"&toBrchIdStr="+toBrchIdStr+"&takeBranch.brchId="+takeBranchId;
					requestAndRefresh(url,"dataTable");
				}		
			}
			
			function getAllUnSelected(datagrid,pkid){
				var beIds=getMutSelected(datagrid,pkid);
				var ids = [];
				var rows = $('#'+datagrid).datagrid('getRows');
				for(var i=0;i<rows.length;i++){			
					var v = rows[i][pkid];
					if (beIds.indexOf(v)<0){
						ids.push(v);
					}			
				}
				return ids.join(':');
			}
			
			function getAllSelected(datagrid,pkid){
				var ids = [];
				var rows = $('#'+datagrid).datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i][pkid]);
				}
				return ids.join(':');
			}
			//init
			doQueryActor();	
			
			function selectTakeBranch(node){
				var branch_brchName_val = "";
				var branch_brchId_val = "";
				if(node){
					branch_brchName_val=node.brchName;
					branch_brchId_val=node.brchId;
					$("#toBrch_brchName").val(branch_brchName_val);
					$("#toBrchId").val(branch_brchId_val);
					doQueryActor();
				}
				
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>