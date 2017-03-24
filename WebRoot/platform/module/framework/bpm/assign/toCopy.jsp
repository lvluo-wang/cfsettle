<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="tool">
		模板机构:<s:textfield class="formPannel_input" id="branch_brchName" name="takeBranch.brchName" disabled="true" />					
		<x:button icon="icon-search" click="chooseTreeBranch(null,{miNoControl:false})"  text="选择机构"/>
		<s:hidden id="branch_brchId" />		
		<s:hidden id="branch_brchNo"/>
		<x:button iconCls="icon-copy" text="复制" click="doCopy" />
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table class="detail_table">
			<colgroup>
						<col width="50%"/>
			            <col width="50%"/>
		        </colgroup>	
			<tbody>
				
		         <tr>
					<td class="detail_head">流程</td>
					<td class="detail_head">机构</td>
				</tr>
				<tr>
					<td class="detail_td">
						<div style="height:400px;overflow:auto;">
							<ul id="taskTree"></ul>
						</div>
					</td>
				<td class="detail_td">
					<div id="taskDetail" closable="false" selected="false">
						<div style="height:400px;overflow:auto;">
							<ul id="branchTree"></ul>
						</div>
					</div>
				</td>
			</tr>
			</tbody>
		</table>
		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script>
			$(function(){
				$("#taskTree").tree({
					checkbox:true,
					url:'<s:url value="/bpm/taskassign_getProcessTree.jhtml"/>'
				});
				$("#branchTree").tree({
					checkbox:true,
					url:'<s:url value="/security/brch_getBranchTree.jhtml"/>'
				});
			});
			function doCopy(){
				var branchId = $("#branch_brchId").val();
				if (!branchId) {
					info("请选择模板机构");
					return;
				}
				var processIds = getTreeChecked("taskTree");
				if(!processIds){
					info("请选择流程");
					return;
				}
				var targetBrachIds = getTreeChecked("branchTree");
				if (!targetBrachIds) {
					info("请要复制的机构");
					return;
				}
				
				doPost('<s:url value="/bpm/taskassign_doCopy.jhtml"/>',{'brchId':branchId,'processIds':processIds,'ids':targetBrachIds},function(result){
					if(!printError(result)){
						info("操作成功");
					}
				});
				
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>