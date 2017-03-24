<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_FORM">
	<tiles:putAttribute name="tool">
		<x:button acl="/product/product_add.jhtml" iconCls="icon-add" onClick="doAdd()" text="add"/>
		<x:button acl="/product/product_edit.jhtml" iconCls="icon-edit" onClick="doEdit()" text="edit"/>
		<x:button acl="/product/product_delete.jhtml" iconCls="icon-remove" onClick="doRemove()" text="del"/>
		<x:button iconCls="icon-search" onClick="expandAll()" text="expandAll"/>
		<s:if test="isImplementation">
		<x:button iconCls="icon-edit" onClick="doFunc()" text="set,sysfunc"/>
		<x:button iconCls="icon-edit" onClick="doAttrs()" text="set,attribute"/>
		</s:if>
		<INPUT TYPE="checkbox" NAME="" id="isAllowMove" onclick="doCheckAllowMove();"/>产品树的节点是否允许移动
	</tiles:putAttribute>
	<tiles:putAttribute name="form">
	<table class="detail_table">
		<tr>
				<td class="detail_head"><s:text name="product"/><s:text name="tree"/></td>
				<td class="detail_head"><label id="detailTitle"><s:text name="help"/></label></td>
		</tr>
			<tr>
				<td class="detail_td" style="width:250px;">
					<div style="height:400px;width:250px;overflow:auto;">
						<ul id="productTree" class="easyui-tree"></ul>
					</div>
				</td>
				<td id="productDetail"  class="detail_td" style="padding-top:10px;">

					<br>
					<h3><s:text name="prompt"/></h3>
					<ul>
						<li><s:text name="prompt_sysfucn_li1"/></li>
						<li><s:text name="prompt_sysfucn_li2"/></li>
						<li><s:text name="prompt_sysfucn_li3"/></li>
					</ul>
				</td>
			</tr>
		</table>
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<!-- 弹出式窗口定义开始 -->
	<div id="product_add_edit"  style="width:500px;display:none;"></div>
	<div id="product_func"  style="width:280px;display:none;"></div>
	<div id="product_attributes"  style="width:280px;display:none;"></div>
	<!-- 弹出式窗口定义结束 -->
</tiles:putAttribute>
<tiles:putAttribute name="menu">
	<!-- 右键菜单定义开始 -->
	<div id="productMenu" class="easyui-menu" style="width:120px;">
		<div onclick="reload()" iconCls="icon-reload"><s:text name="refresh"/></div>
		<div class="menu-sep"></div>
		<div onclick="expandAll()"><s:text name="expandAll"/></div>
		<div onclick="collapseAll()"><s:text name="collapseAll"/></div>
		<div class="menu-sep"></div>
		<div onclick="doView()" iconCls="icon-search"><s:text name="view"/></div>
		<div class="menu-sep"></div>
		<div onclick="doAdd()" iconCls="icon-add"><s:text name="add"/><s:text name="child"/><s:text name="product"/></div>
		<div onclick="doEdit()" iconCls="icon-edit"><s:text name="edit"/></div>
		<div class="menu-sep"></div>
		<div onclick="doRemove()" iconCls="icon-remove"><s:text name="del"/></div>
	</div>
	<!-- 右键菜单定义结束 -->
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">

var isAllowMove=false;//默认不允许托拽
function doCheckAllowMove(){
	isAllowMove = document.getElementById("isAllowMove").checked;
	if(isAllowMove)
		$("#productTree").tree('enableDnd');
	else
		$("#productTree").tree('disableDnd');
}

	//工具栏方法实现
	function doAdd(){
		var node = $('#productTree').tree('getSelected');
		var prodId="";
		if(node){
			prodId=node.id;
		}
		var url='<s:url value="/product/product_add.jhtml"/>?prodId='+prodId+'&flag='+Math.random()*99999;
		requestAtWindow(url,"product_add_edit",'<s:text name="add"/><s:text name="product"/>');

	}
	function doEdit(){
		var node = $('#productTree').tree('getSelected');
		if(node){
			var prodId=node.id;
			var url='<s:url value="/product/product_edit.jhtml"/>?prodId='+prodId+'&flag='+Math.random()*99999;
			requestAtWindow(url,"product_add_edit",'<s:text name="Edit_Product"/>');
		}else{
			info(global.notSelectInfo);
		}

	}
	function doView(){
		var node = $('#productTree').tree('getSelected');
		if(node){
			var prodId=node.id;
			$("#detailTitle").text(node.text);
			viewProd(prodId);
		}else{
			info(global.notSelectInfo);
		}
	}
	function viewProd(prodId){
		var url='<s:url value="/product/product_view.jhtml"/>';
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
	function doHelp(){
		
		var url='<s:url value="/product/product_help.jhtml"/>?flag='+Math.random()*99999;
		requestAtDom(url,"productDetails","productDetail",'<s:text name="help"/>');
		//requestAtTab(url,"productDetails","productDetail",'<s:text name="help"/>');
	}
	
	function doRemove(){
		var node = $('#productTree').tree('getSelected');
		if(node){
			$.messager.confirm(global.alert, node.text+global.del_confirm_info, function(r){
				if(r){
					var prodId=node.id;
					var url='<s:url value="/product/product_delete.jhtml"/>?prodId='+prodId+'&flag='+Math.random()*99999;
					requestAndRefreshTree(url,"productTree");
					$("#productTree").tree('reload');
				}
					
			});
		}else{
			info(global.notSelectInfo);
		}
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
	function doFunc(){
		var node = $('#productTree').tree('getSelected');
		if(node){
			if( node.attributes.prodType == '2' ){
				var prodId=node.id;
				var url='<s:url value="/product/product_productFunc.jhtml"/>?prodId='+prodId;
				requestAtWindow(url,"product_func",'<s:text name="set"/><s:text name="sysfunc"/>');
			}else{
				info(global.only_ebank_assign);
			}
		}else{
			info(global.notSelectInfo);
		}
	}
	function doAttrs(){
		var node = $('#productTree').tree('getSelected');
		if(node){			
			var prodId=node.id;
			var url='<s:url value="/product/product_productAttributes.jhtml"/>?prodId='+prodId;
			requestAtWindow(url,"product_attributes",'<s:text name="set"/><s:text name="attribute"/>');
		}else{
			info(global.notSelectInfo);
		}
	}
	$("#productTree").tree({
		dnd:true,
		url:'<s:url value="/product/product_querySubProd.jhtml"/>?flag='+Math.random()*99999,
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
			$("#detailTitle").text(node.text);
			viewProd(prodId);
		},
		onDrop: function(targetNode, source, point){
	        var obj=$("#productTree").tree('getData', targetNode);
        	var url='<s:url value="/product/product_move.jhtml"/>?flag='+Math.random()*99999;
	       	var targetId;
        	if(obj.id){
        		targetId=obj.id;
	       	}else{
	       		targetId=$(targetNode).attr("node-id");
	       	}
	        requestByTreeMove(url,targetId,source.id,point,"productTree");
       	
	    },
		onBeforeLoad:function(node, par){
			if(node){
				mask();
				updateMask(global.load+node.text);
			}
			
			return true;
		},
		onLoadSuccess:function(node, da){
			
			if (!isAllowMove)
				$("#productTree").tree('disableDnd');
			if(node){
				updateMask(global.load+node.text+global.success);
				closeMask();
				
			}
			
		}
	});
	ACL();
	doCheckAllowMove();
	$(".easyui-menu").menu();
</script>
</tiles:putAttribute>
</tiles:insertDefinition>