<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_QUERY_DATA">
	<tiles:putAttribute name="query">
		<label for="分配机构" class="formPannel_label">分配机构:</label>
		<s:textfield class="formPannel_input" id="branch_brchName" name="takeBranch.brchName" disabled="true" />					

		<x:button icon="icon-search" click="chooseTreeBranch()"  text="选择机构"/>
		<s:hidden name="takeBranch.brchId" id="branch_brchId" />		
		<s:hidden name="brchNo" id="branch_brchNo"/>
		<!--<s:hidden name="unionBankNoId" id="branch_unionBrankNo" /> -->
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table class="detail_table">
			<colgroup>
						<col width="200px"/>
			            <col width="100%"/>
		        </colgroup>	
			<tbody>
				
		         <tr>
					<td class="detail_head"><s:text name="task"/><s:text name="tree"/></td>
					<td class="detail_head"><div id="title"><s:text name="task"/><s:text name="assign"/></div></td>
				</tr>
				<tr>
					<td class="detail_td">
						<div style="height:400px;width:210px;overflow:auto;">
							<ul id="taskTree" class="easyui-tree"></ul>
						</div>
					</td>
				<td class="detail_td">
					
							<div id="taskDetail" title='<s:text name="task"/><s:text name="assign"/>' closable="false" selected="false">
								<br>
								<h3><s:text name="prompt"/></h3>
								<ul>
									<li>左边树中 一级结点为:流程   二级结点为：任务</li>
									<li>选中任务双击，弹出任务的分配对象情况。</li>
									<li>可以通过上部的'选择机构按钮'选择子机构为下属机构可以任务分配。</li>									
								</ul>
							</div>
						
				</td>
			</tr>
			</tbody>
		</table>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		
			function initTree(){
				//初始化权限异步树形结构
				$("#taskTree").tree({
					dnd:false,
					url:'<s:url value="/bpm/taskassign_queryTaskTree.jhtml"/>?brchId='+$("#branch_brchId").val()+'&flag='+Math.random()*99999,
					onClick:function(node){
						$(this).tree('toggle', node.target);
					},		
					onDblClick:function(node){
						var b = $("#taskTree").tree("isLeaf",node.target);
						if (!b)
							return;
						var taskId=node.id;
						var brchId = $("#branch_brchId").val();
						var assignType = $("#assignType").val();
						if (!assignType)
							assignType="";
						var url='<s:url value="/bpm/taskassign_actorList.jhtml"/>?taskId='+taskId+'&brchId='+brchId+"&assignType="+assignType+'&flag='+Math.random()*99999;
						//requestAtTab(url,"taskDetails","taskDetail",'<s:text name="task"/>-'+node.text);
						doPost(url,null,function(result){
						   
							if(result){
								if(result.indexOf("{")!=0){
									$("#taskDetail").html(result);
								}else{
									var obj=eval('('+result+')');
									if(obj.error){
											error(obj.error);
									}
									setNull(obj);
								}
								setNull(result);
							}
						});
					},
					onBeforeLoad:function(node, par){
						if(node){
							mask();
							updateMask(global.load+node.text);
						}
						
						return true;
					},
					onLoadSuccess:function(node, da){
						if(node){
							updateMask(global.load+node.text+global.success);
							closeMask();
						}
						
					}
				});
			}
			//工具栏方法实现
			function doAdd(){
				var node = $('#funcTree').tree('getSelected');
				if(node){
					var funcId=node.id;
					var url='<s:url value="/security/sysfunc_add.jhtml"/>?funcId='+funcId+'&flag='+Math.random()*99999;
					requestAtWindow(url,"sysfunc_add_edit",'<s:text name="AddSysfunc"/>');
				}else{
					info(global.notSelectInfo);
				}
			}
			function doEdit(){
				var node = $('#funcTree').tree('getSelected');
				if(node){
					var funcId=node.id;
					var url='<s:url value="/security/sysfunc_edit.jhtml"/>?funcId='+funcId+'&flag='+Math.random()*99999;
					requestAtWindow(url,"sysfunc_add_edit",'<s:text name="Edit_Sysfunc"/>');
				}else{
					info(global.notSelectInfo);
				}
		
			}
			function doView(){
				var node = $('#funcTree').tree('getSelected');
				if(node){
					var funcId=node.id;
					var url='<s:url value="/security/sysfunc_view.jhtml"/>?funcId='+funcId+'&flag='+Math.random()*99999;
					requestAtTab(url,"funcDetails","funcDetail",node.text);
				}else{
					info(global.notSelectInfo);
				}
			}
			function doRemove(){
				var node = $('#funcTree').tree('getSelected');
				if(node){
					$.messager.confirm(global.alert, node.text+global.del_confirm_info, function(r){
						if(r){
							var funcId=node.id;
							var url='<s:url value="/security/sysfunc_delete.jhtml"/>?funcId='+funcId+'&flag='+Math.random()*99999;
							requestAndRefreshTree(url,"funcTree");
						}
							
					});
				}else{
					info(global.notSelectInfo);
				}
			}
			function collapseAll(){
				var node = $('#taskTree').tree('getSelected');
				if (node){
					$('#taskTree').tree('collapseAll', node.target);
				} else {
					$('#taskTree').tree('collapseAll');
				}
			}
			function expandAll(){
				var node = $('#taskTree').tree('getSelected');
				if (node){
					$('#taskTree').tree('expandAll', node.target);
				} else {
					$('#taskTree').tree('expandAll');
				}
			}
			function reload(){
				$('#taskTree').tree('expandAll');
			}
			//选择机构时点取消按钮是否将机构设置为空白
			function isSetBlankBranch(){
				return false;
			}
			//重新选择机构的回调
			function callbackChooseBranch(){
				//$("#taskTree").tree('reload',null);
				initTree();
				
				$("#taskDetails").tabs('update',{
					tab:$("#taskDetail"),
					options:{
						title:'<s:text name="task"/><s:text name="assign"/>',
						content:'<br><h3><s:text name="prompt"/></h3><ul>'
								+'<li>左边树中 一级结点为:流程   二级结点为：任务</li>'
								+'<li>选中任务双击，弹出任务的分配对象情况。</li>'
								+'<li>可以通过上部的\'选择机构按钮\'选择子机构为下属机构可以任务分配。</li>'									
								+'</ul>'
					}
					
				});
			}
			//init
			initTree();	
			//链接访问控制
			ACL();
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>