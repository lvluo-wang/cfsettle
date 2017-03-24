<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_BLANK">

<tiles:putAttribute name="body">
	<div class="func_form_area">
			<table class="detail_table">

				<tbody>
				<tr>
					<td class="detail_head"><s:text name="交易"/><s:text name="tree"/></td>
					<td class="detail_head"><div id="title"><s:text name="help"/></div></td>
				</tr>
				<tr>
					<td class="detail_td" style="width:250px;">
					<div style="height:400px;width:250px;overflow:auto;">
							<ul id="tranTree" class="easyui-tree"></ul>
						</div>
					</td>
					<td class="detail_td" style="vertical-align:top;">
						<div id="tranDetail" >
							<div>
								<br>
								<h3><s:text name="prompt"/></h3>
								<ul>
									<li>从左侧树中选中交易[双击]，列出交易分录配置信息。</li>
									<li><s:text name="prompt_sysfucn_li2"/></li>
									<li><s:text name="prompt_sysfucn_li3"/></li>
								</ul>
							</div>
						</div>
						
					</td>
				</tr>
				
		</tbody>
			</table>
			</div>
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<!-- 弹出式窗口定义开始 -->
	<div id="sel"  style="width:600px;display:none;"></div>
	<div id="error_view" style="width:500px;display:none;"></div>
	<div id="add_edit" style="width:700px;display:none;"></div>
</tiles:putAttribute>
<tiles:putAttribute name="menu">
	<!-- 弹出式窗口定义结束 -->
	<!-- 右键菜单定义开始 -->
	<div id="taskMenu" class="easyui-menu" style="width:120px;display:none;">
		<div onclick="reload()" iconCls="icon-reload"><s:text name="refresh"/></div>
		<div class="menu-sep"></div>
		<div onclick="expandAll()"><s:text name="expandAll"/></div>
		<div onclick="collapseAll()"><s:text name="collapseAll"/></div>
		<div class="menu-sep"></div>
		<div onclick="doView()" iconCls="icon-search"><s:text name="view"/></div>
		<div class="menu-sep"></div>
		<div onclick="doAdd()" iconCls="icon-add"><s:text name="sysfuncAddChild"/></div>
		<div onclick="doEdit()" iconCls="icon-edit"><s:text name="edit"/></div>
		<div class="menu-sep"></div>
		<div onclick="doRemove()" iconCls="icon-remove"><s:text name="del"/></div>
	</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
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
		var node = $('#tranTree').tree('getSelected');
		if(node){
			var tranId=node.id;
			viewTran(tranId);
		}else{
			info(global.notSelectInfo);
		}
	}
	function viewTran(tranId){
		var url='<s:url value="/acctConfig/tranItemAction_toTranItemList.jhtml"/>';
		doPost(url,{"tranId":tranId},function(result){
			if(result){
				if(result.indexOf("{")!=0){
					$("#tranDetail").html(result);
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
		var node = $('#tranTree').tree('getSelected');
		if (node){
			$('#tranTree').tree('collapseAll', node.target);
		} else {
			$('#tranTree').tree('collapseAll');
		}
	}
	function expandAll(){
		var node = $('#tranTree').tree('getSelected');
		if (node){
			$('#tranTree').tree('expandAll', node.target);
		} else {
			$('#tranTree').tree('expandAll');
		}
	}
	function reload(){
		$('#tranTree').tree('expandAll');
	}
	
	function chooseBranch() {
		var url="<s:url value="/security/user_toChooseBrch.jhtml"/>?flag="+Math.random()*99999;
		requestAtWindow(url,"sel",'<s:text name="choose"/><s:text name="branch"/>');
		
	}

	function doCancel(){
		$('#add_edit').window('close');
	}
	$("#taskMenu").menu();
	//初始化权限异步树形结构
	$("#tranTree").tree({
		dnd:false,
		url:'<s:url value="/acctConfig/tranItemAction_queryTranTree.jhtml"/>?flag='+Math.random()*99999,
		onClick:function(node){
			$(this).tree('toggle', node.target);
		},
		
		onDblClick:function(node){			
			if(!$("#tranTree").tree('isLeaf',node.target)){				
				return;
			}
			var tranId=node.id;
			$("#title").html(node.text);
			var url='<s:url value="/acctConfig/tranItemAction_toTranItemList.jhtml"/>?tranId='+tranId+'&flag='+Math.random()*99999;
			requestAtDom("tranDetail",url,null);
		},
		
		onLoadSuccess:function(node, da){
			if(node){
				updateMask(global.load+node.text+global.success);
				closeMask();
			}
			
		}
	});
	
	
</script>
</tiles:putAttribute>
</tiles:insertDefinition>