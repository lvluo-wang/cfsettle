<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>


<tiles:insertDefinition name="FUNC_TOOL_FORM">
<tiles:putAttribute name="end">
<script type="text/javascript">
	function doEdit(){
		var node = $('#productTree').tree('getSelected');
		if(node){
			var prodId=node.id;
			var url='<s:url value="/product/product_memberEdit.jhtml"/>?prodId='+prodId+'&flag='+Math.random()*99999;
			requestAtWindow(url,"product_add_edit",'<s:text name="Edit_Product"/>');
		}else{
			info(global.notSelectInfo);
		}

	}
	function doView(){
		var node = $('#productTree').tree('getSelected');
		if(node){
			var prodId=node.id;
			$("#title").html(node.text);
			viewProd(prodId);
		}else{
			info(global.notSelectInfo);
		}
	}
	function viewProd(prodId){
		var url='<s:url value="/product/product_memberView.jhtml"/>';
		doPost(url,{"prodId":prodId},function(result){
			if(result){
				if(result.indexOf("{")!=0){
					$("#productDetail").html(result);
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
	function collapseAll(){
		var node = $('#productTree').tree('getSelected');
		if (node){
			$('#productTree').tree('collapseAll', node.target);
		} else {
			$('#productTree').tree('collapseAll');
		}
	}
	function expandAll(){
		var node = $('#productTree').tree('getSelected');
		if (node){
			$('#productTree').tree('expandAll', node.target);
		} else {
			$('#productTree').tree('expandAll');
		}
	}
	function reload(){
		$('#productTree').tree('expandAll');
	}
	$("#productTree").tree({
		dnd:false,
		url:'<s:url value="/product/product_queryMemberSubProd.jhtml"/>?flag='+Math.random()*99999,
		onContextMenu:function(e,node){
			e.preventDefault();
			$(this).tree('select',node.target);
			$("#productMenu").menu('show',{
				left:e.pageX,
				top:e.pageY
			});
			return false;
		},
		onDblClick:function(node){
			var prodId=node.id;
			$("#title").html(node.text);
			viewProd(prodId);
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
	$(".easyui-menu").menu();
</script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">
	<x:button icon="icon-edit" click="doEdit" text="edit"/>
	<span class="separator"></span>
	<x:button icon="icon-search" click="expandAll" text="expandAll"/>

</tiles:putAttribute>
<tiles:putAttribute name="form">
	<table class="detail_table">

		<tbody>
			
	            <tr>
					<td class="detail_head"><s:text name="交易"/><s:text name="tree"/></td>
					<td class="detail_head"><div id="title"><s:text name="help"/></div></td>
				</tr>
			<tr>
				<td class="detail_td" style="width:250px;">
					<div style="height:400px;width:250px;overflow:auto;">
						<ul id="productTree" class="easyui-tree"></ul>
					</div>
				</td>
				<td class="detail_td">
					<div id="productDetail">
						<h3><s:text name="prompt"/></h3>
						<ul>
							<li><s:text name="prompt_sysfucn_li1"/></li>
						<li><s:text name="prompt_sysfucn_li2"/></li>
						</ul>
					</div>
					
				</td>
			</tr>
		</tbody>
	</table>
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<!-- 弹出式窗口定义开始 -->
	<div id="product_add_edit"   style="width:500px;display:none;">
	</div>
	<!-- 弹出式窗口定义结束 -->
</tiles:putAttribute>
<tiles:putAttribute name="menu">
	<!-- 右键菜单定义开始 -->
	<div id="productMenu" class="easyui-menu" style="width:120px;display:none;">
		<div onclick="reload()" iconCls="icon-reload"><s:text name="refresh"/></div>
		<div class="menu-sep"></div>
		<div onclick="expandAll()"><s:text name="expandAll"/></div>
		<div onclick="collapseAll()"><s:text name="collapseAll"/></div>
		<div class="menu-sep"></div>
		<div onclick="doView()" iconCls="icon-search"><s:text name="view"/></div>
		<div class="menu-sep"></div>
		<div onclick="doEdit()" iconCls="icon-edit"><s:text name="edit"/></div>
	</div>
	<!-- 右键菜单定义结束 -->
</tiles:putAttribute>
</tiles:insertDefinition>