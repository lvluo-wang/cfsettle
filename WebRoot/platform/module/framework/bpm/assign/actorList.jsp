<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_QUERY_DATA">
	<tiles:putAttribute name="query">
		<s:hidden name="brchId" id="brchId" />
		<s:hidden name="taskId" id="taskId" />
		<label for="授予机构" >授予机构:</label>
		
		       <input type="hidden" name="toBrchId" id="toBrchId" value="${brchId}"/>
		       <input id="toBrchName" class="inputSel"  onClick="chooseTreeBranch(selectToBranchCallback,{miNoControl:false})"/>
		       
		     <!-- 
				<select class="lang_select" name="toBrchId" id="toBrchId" onchange="doQueryActor()">
					 <s:iterator value="toBrchList" >
					 		<option value="${brchId}">${brchName}</option>
        			 </s:iterator>
				</select>
			 -->
				
				&nbsp;&nbsp;
				<label for="分配类型">分配类型:</label>
				<s:select list="assignTypeList" listKey="codeNo" 
					listValue="codeName" name="assignType" id="assignType" onchange="doQueryActor()">
				</s:select>							
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
				var brchId = $("#brchId").val();
				var taskId = $("#taskId").val();
				var toBrchIdStr = $("#toBrchId").val();
				if (assignType=='1'){
					doQueryUser(brchId, taskId, toBrchIdStr);
				}else{
					doQueryRole(brchId, taskId, toBrchIdStr);
				}
				
			}
			
			function doQueryRole(brchId, taskId, toBrchIdStr){
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
									}
								}
							]];		
				
				//初始化数据组件
				//dataGrid=new initDataComponent("dataTable",'<s:url value="/bpm/taskassign_roleAssignList.jhtml"/>?brchId='+brchId+'&taskId='+taskId+'&toBrchIdStr='+toBrchIdStr,frozenColumns,columns);
				dataGrid=new DataPageComponent("dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					fit:false,
					url:'<s:url value="/bpm/taskassign_roleAssignList.jhtml"/>?brchId='+brchId+'&taskId='+taskId+'&toBrchIdStr='+toBrchIdStr,
					onLoadSuccess:function(data){
						if (data.initSelecteds){
							for (i=0; i<data.initSelecteds.length; i++ ){
								$("#dataTable").datagrid("selectRow",  data.initSelecteds[i]);
							}
						}						
					}
				});
				dataGrid.load();
			}
			function doQueryUser(brchId, taskId, toBrchIdStr){
				// 定义冻结窗口列
				var frozenColumns=[[
				                    {field:'ck',checkbox:true,width:20}
				        		]];		
				//定义显示列
				var columns=[[  
					  			{field:'userNo',title:'<s:text name="user"/><s:text name="no"/>',width:200,sortable:true,
									sorter:function(a,b){return (a>b?1:-1);}
								},
								{field:'userName',title:'<s:text name="user"/><s:text name="name"/>',width:200,sortable:true,
									sorter:function(a,b){return (a>b?1:-1);}
								}
							]];		
				//初始化数据组件
				//dataGrid=new initDataComponent("dataTable",'<s:url value="/bpm/taskassign_userAssignList.jhtml"/>?brchId='+brchId+'&taskId='+taskId+'&toBrchIdStr='+toBrchIdStr, frozenColumns,columns);
				dataGrid=new DataPageComponent("dataTable",{
					columns:columns,
					frozenColumns:frozenColumns,
					fit:false,
					url:'<s:url value="/bpm/taskassign_userAssignList.jhtml"/>?brchId='+brchId+'&taskId='+taskId+'&toBrchIdStr='+toBrchIdStr,
					onLoadSuccess:function(data){
						if (data.initSelecteds){
							for (i=0; i<data.initSelecteds.length; i++ ){
								$("#dataTable").datagrid("selectRow",  data.initSelecteds[i]);
							}
						}						
					}
				});
				dataGrid.load();
			}
			//工具栏方法实现区域
			function doQuery(){
				var url='<s:url value="/security/role_listRoles.jhtml"/>';
				var parm=formToObject("queryForm");
				queryForDatagrid(url,parm,"dataTable");
			}
			function doAdd(){
				var url="<s:url value='/security/role_toAdd.jhtml'/>?flag="+Math.random()*99999;
				requestAtWindow(url,"role_add_edit","<s:text name="add"/><s:text name="role"/>");		
			}
			function doEdit(){
				if(isSingleSelected("dataTable","roleId")){
					var selectedId=getSelected("dataTable","roleId");
					var url="<s:url value='/security/role_toEdit.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;
					requestAtWindow(url,"role_add_edit","<s:text name="edit"/><s:text name="role"/>");
				}
			}	
			function doSave(){	
				var beIds=getAllSelected("dataTable","userId");
				var unIds=getAllUnSelected("dataTable","userId");
				var assignType = $("#assignType").val();
				if (assignType=='2'){
					beIds=getAllSelected("dataTable","roleId");
					unIds=getAllUnSelected("dataTable","roleId");
				}
				if(beIds.length<1 && unIds.length<1){
					return;
					info(global.notSelectInfo);
				}else{
					var brchId = $("#brchId").val();
					var taskId = $("#taskId").val();
					var toBrchIdStr = $("#toBrchId").val();
					var url = "<s:url value='/bpm/taskassign_save.jhtml'/>?beIds="+beIds+"&unIds="+unIds
							+"&assignType="+assignType+"&brchId="+brchId+"&taskId="+taskId+"&toBrchIdStr="+toBrchIdStr;
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
			
			
			function selectToBranchCallback(node){
				var branch_brchName_val = "";
				var branch_brchId_val = "";
				if(node){
					branch_brchName_val=node.brchName;
					branch_brchId_val=node.brchId;
					$("#toBrchName").val(branch_brchName_val);
					$("#toBrchId").val(branch_brchId_val);
					
					doQueryActor();
				}
				
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>