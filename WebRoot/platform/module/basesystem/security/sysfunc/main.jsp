<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
	<tiles:putAttribute name="head">
<script language="javascript" src="${pageContext.request.contextPath}/platform/module/basesystem/security/resources/sysfunc/message_${session.WW_TRANS_I18N_LOCALE}.js"></script>
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/sysfunc-validate.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">

	//工具栏方法实现
	function doAdd(){
		var node = $('#funcTree').tree('getSelected');
		if(node){
			var funcId=node.id;
			var url='<s:url value="/security/sysfunc_add.jhtml"/>?funcId='+funcId+'&flag='+Math.random()*99999;
			requestAtWindow(url,"sysfunc_add_edit",'<s:text name="sysfuncAddChild"/>');
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
			var funcText=node.text;
			$("#detailTitle").html(funcText);
			viewFunc(funcId);
		}else{
			info(global.notSelectInfo);
		}
	}
	function viewFunc(funcId){
		var url='<s:url value="/security/sysfunc_view.jhtml"/>';
		//requestAtTab(url,"funcDetails","funcDetail",node.text);
		//initPageContentWithPara("sysfuncMain",{"funcId":funcId}, url);
		doPost(url,{"funcId":funcId},function(result){
			if(result){
				if(result.indexOf("{")!=0){
					$("#funcDetail").html(result);
					$.xcarsParser.parse($("#funcDetail"));
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
		var node = $('#funcTree').tree('getSelected');
		if (node){
			$('#funcTree').tree('collapseAll', node.target);
		} else {
			$('#funcTree').tree('collapseAll');
		}
	}
	function expandAll(){
		var node = $('#funcTree').tree('getSelected');
		if (node){
			$('#funcTree').tree('expandAll', node.target);
		} else {
			$('#funcTree').tree('expandAll');
		}
	}
	function reload(){
		var node = $('#funcTree').tree('getSelected');
		if(node){
			$('#funcTree').tree('reload',node.target);
		}else{
			$('#funcTree').tree('reload');
		}
		
	}
	function enmove(o){
		if(o.checked){
			$("#funcTree").tree({dnd:true});
		}else{
			$("#funcTree").tree({dnd:false});
		}
	}
	$("#funcMenu").menu();
	//初始化权限异步树形结构
	$("#funcTree").tree({
		dnd:false,
		url:'<s:url value="/security/sysfunc_queryFunc.jhtml"/>?flag='+Math.random()*99999,
		onContextMenu:function(e,node){
			e.preventDefault();
			$(this).tree('select',node.target);
			$("#funcMenu").menu('show',{
				left:getMenuLeft("funcMenu",e),
				top:getMenuTop("funcMenu",e)
			});
			return false;
		},
		onDblClick:function(node){
			var funcId=node.id;
			var funcText=node.text;
			$("#detailTitle").html(funcText);
			viewFunc(funcId);
		},
		onDrop: function(targetNode, source, point){
	        var obj=$("#funcTree").tree('getData', targetNode);
        	var url='<s:url value="/security/sysfunc_move.jhtml"/>?flag='+Math.random()*99999;
	       	var targetId;
        	if(obj.id){
        		targetId=obj.id;
	       	}else{
	       		targetId=$(targetNode).attr("node-id");
	       	}
	        requestByTreeMove(url,targetId,source.id,point,"funcTree");
       	
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
</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="func_tool_area">
        <x:button icon="icon-add" click="doAdd" text="add"/>
        <x:button icon="icon-edit" click="doEdit" text="edit"/>
        <x:button icon="icon-add" click="doRemove" text="del"/>
		<span class="separator"></span>
		<x:button icon="icon-search" click="expandAll" text="expandAll"/>
		<input type="checkbox" id="move" onClick="enmove(this)"><s:text name="sysfunc.func.allow_move_tip"/>
		</div>
		<div class="func_form_area">
		<table class="detail_table">
				<tbody>
				
				
				<tr>
					<td class="detail_head"><s:text name="sysfunc"/><s:text name="tree"/></td>
					<td class="detail_head"><label id="detailTitle"><s:text name="help"/></label></td>
				</tr>
			<tr>
				<td  class="detail_td" style="width:250px;">
					<div style="height:400px;width:250px;overflow:auto;">
						<ul id="funcTree" class="easyui-tree"></ul>
					</div>
				</td>
				<td  class="detail_td" id="funcDetail" style="padding-top:10px;vertical-align:top;">
					<h3><s:text name="prompt"/></h3>
						<ul>
							<li><s:text name="prompt_sysfucn_li1"/></li>
							<li><s:text name="prompt_sysfucn_li2"/></li>
							<li><s:text name="prompt_sysfucn_li3"/></li>
						</ul>
				</td>
			</tr>
			</tbody>
		</table>
		
	</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="sysfunc_add_edit"  style="display:none;width:500px;">
	</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="menu">
		<div id="funcMenu" class="easyui-menu" style="width:120px;display:none;">
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
</tiles:insertDefinition>