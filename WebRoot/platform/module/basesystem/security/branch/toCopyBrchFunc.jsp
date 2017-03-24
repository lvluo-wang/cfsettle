<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="tool">
		模板机构:<b>${brch.brchName}</b>
		<x:button iconCls="icon-copy" text="复制" click="doCopy" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<x:button iconCls="icon-copy" text="返回" click="doBack" />
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table class="detail_table">
			<colgroup>
						<col width="50%"/>
			            <col width="50%"/>
		        </colgroup>	
			<tbody>
				
		         <tr>
					<td class="detail_head">权限</td>
					<td class="detail_head">机构</td>
				</tr>
				<tr>
					<td class="detail_td">
						<div style="height:400px;overflow:auto;">
							<ul id="funcTree"></ul>
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
				var treeData=${beJSONInfo};
				$("#funcTree").tree({
					data:treeData
				});
				$("#branchTree").tree({
					checkbox:true,
					url:'<s:url value="/security/brch_getBranchTree.jhtml"/>'
				});
			});
			function doCopy(){
				var branchId = '${brch.brchId}';
				if (!branchId) {
					info("请选择模板机构");
					return;
				}
				var targetBrachIds = getTreeChecked("branchTree");
				if (!targetBrachIds) {
					info("请要复制的机构");
					return;
				}
				
				doPost('<s:url value="/security/brch_doCopyBrchFunc.jhtml"/>',{'brch.brchId':branchId,'ids':targetBrachIds},function(result){
					if(!printError(result)){
						info("复制成功");
					}
				});
				
			}
			function doBack(){
				var url = '<s:url value="/security/brch_list.jhtml"/>';
				redirectUrl(url);
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>