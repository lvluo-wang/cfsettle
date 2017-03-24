<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_FLOW_FORM">
<tiles:putAttribute name="flow">
		<div id="step">
			<span id="step_list">
				<span id="step1" class="step_first"><s:text name="product"/><s:text name="audit"/><s:text name="set"/></span>
				<span id="step2" class="step_nomal step_current"><s:text name="set"/><s:text name="auditStation"/></span>
			</span>
			<span id="step_tool">
				<x:button iconCls="icon-back" onClick="step_return()" text="return"/>
			</span>
		</div>
</tiles:putAttribute>
<tiles:putAttribute name="form">
		<form class="busi_form">
			<table>
				<colgroup>
					<col width="252px"/>
					<col width="100%"/>
				</colgroup>
				<tbody>
				
				
				<tr >
					<td class="head"><s:text name="auditTree"/></td>
					<td class="head"><label id="detailTitle"><s:text name="help"/></label></td>
				</tr>
				<tr>
					<td style="vertical-align:top;">
					<div  style="vertical-align:top; width:250px;padding:1px;">
					<ul id="auditTree" class="easyui-tree"></ul>
					</div>
					</td>
					<td style="vertical-align:top;">
					<div id="auditDetail" ></div>
					<div>
								<br>
								<h3><s:text name="prompt"/></h3>
								<ul>
									<li><s:text name="prompt_sysfucn_li1"/></li>
									<li><s:text name="prompt_sysfucn_li2"/></li>
									
								</ul>
								<s:if test="tipMsg != null">
									<div style="color:#f00;">${tipMsg}</div>
								</s:if>
							</div>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<div id="auditStation_add_edit"   style="width:450px;display:none;">
	</div>
</tiles:putAttribute>
<tiles:putAttribute name="menu">
	<div id="routeMenu"class="easyui-menu" style="width:120px;display:none;">
		<div onclick="doView()" iconCls="icon-search"><s:text name="view"/></div>
		<div class="menu-sep"></div>
		<div onclick="reload()" iconCls="icon-reload"><s:text name="refresh"/></div>
		<div class="menu-sep"></div>
		<div onclick="expandAll()"><s:text name="expandAll"/></div>
		<div onclick="collapseAll()"><s:text name="collapseAll"/></div>
	</div>
	<div id="nodeMenu"class="easyui-menu" style="width:120px;display:none;">
		<div onclick="doAdd()" iconCls="icon-add"><s:text name="addAuditStation"/></div>
		<div onclick="doView()" iconCls="icon-search"><s:text name="view"/></div>
		<div class="menu-sep"></div>
		<div onclick="reload()" iconCls="icon-reload"><s:text name="refresh"/></div>
		<div class="menu-sep"></div>
		<div onclick="expandAll()"><s:text name="expandAll"/></div>
		<div onclick="collapseAll()"><s:text name="collapseAll"/></div>
	</div>
	<div id="stationMenu"class="easyui-menu" style="width:120px;display:none;">
		<div onclick="doEdit()" iconCls="icon-edit"><s:text name="edit"/></div>
		<div onclick="doView()" iconCls="icon-search"><s:text name="view"/></div>
		<div class="menu-sep"></div>
		<div onclick="toStationAssign()" iconCls="icon-set"><s:text name="auditStation"/><s:text name="assign"/></div>
		<div class="menu-sep"></div>
		<div onclick="reload()" iconCls="icon-reload"><s:text name="refresh"/></div>
		<div class="menu-sep"></div>
		<div onclick="doRemove()" iconCls="icon-remove"><s:text name="del"/></div>
	</div>
</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function treeLoadData(){
		var url='<s:url value="/audit/audit_queryAuditRouteTree.jhtml"/>';
		 doPost(url,
				 {"auditRoute.id":'<s:property value="auditRoute.id"/>'},
				 function(result){
					 if(result){
						 if(result.indexOf('{')==0||result.indexOf('[')==0){
							 var obj=parseObj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 $("#auditTree").tree({
										onContextMenu:function(e,node){
											e.preventDefault();
											$(this).tree('select',node.target);
											var menuName=null;
											if("route"==node.attributes.type){
												menuName="routeMenu";
											}
											if("node"==node.attributes.type){
												menuName="nodeMenu";
											}
											if("station"==node.attributes.type){
												menuName="stationMenu";
											}
											if(menuName){
												//$("#"+menuName).show();
												$("#"+menuName).menu('show',{
													left:getMenuLeft(menuName,e),
													top:getMenuTop(menuName,e)
												});
												
											}
											return false;
											
										},
										onDblClick:function(node){
											var id=node.id;
											var type=node.attributes.type;
											var url='<s:url value="/audit/audit_view.jhtml"/>?nodeId='+id+'&nodeType='+type;
											$("#detailTitle").html(node.text);
											requestAtDom("auditDetail",url,null);
											//requestAtTab(url,'auditDetails','auditDetail',node.text);
										}

									}).tree('loadData',obj);
							 }
						 }
					 }
		 });
	}
	//工具栏方法实现
	function doAdd(){
		var node = $('#auditTree').tree('getSelected');
		if(node){
			var id=node.id;
			var type=node.attributes.type;
			var winName="";
			if(type=='route'||type=='station'){
				return;
			}
			if(type=='node'){
				winName="auditStation_add_edit";
			}
			var url='<s:url value="/audit/audit_add.jhtml"/>?nodeId='+id+'&nodeType='+type;
			requestAtWindow(url,winName,'<s:text name="add"/>');
		}else{
			info(global.notSelectInfo);
		}
		
		

	}
	function doEdit(){
		var node = $('#auditTree').tree('getSelected');
		if(node){
			var id=node.id;
			var type=node.attributes.type;
			if(type=='route'||type=='node'){
				return;
			}
			if(type=='station'){
				winName="auditStation_add_edit";
			}
			var url='<s:url value="/audit/audit_edit.jhtml"/>?nodeId='+id+'&nodeType='+type;
			requestAtWindow(url,winName,'<s:text name="edit"/>');
		}else{
			info(global.notSelectInfo);
		}

	}
	function doView(){
		var node = $('#auditTree').tree('getSelected');
		if(node){
			var id=node.id;
			var type=node.attributes.type;
			var url='<s:url value="/audit/audit_view.jhtml"/>?nodeId='+id+'&nodeType='+type;
			$("#detailTitle").html(node.text);
			requestAtDom("auditDetail",url,null);
			//requestAtTab(url,"auditDetails","auditDetail",node.text);
		}else{
			info(global.notSelectInfo);
		}
	}
	function doRemove(){
		var node = $('#auditTree').tree('getSelected');
		if(node){
			$.messager.confirm(global.alert, node.text+global.del_confirm_info, function(r){
				if(r){
					var id=node.id;
					var type=node.attributes.type;
					var url='<s:url value="/audit/audit_delete.jhtml"/>';
					doPost(url,{
						nodeId:id,
						nodeType:type
					},function(result){
						if(result){
	        				var data=eval('('+result+')');
		        			if(data.error){
		        				error(data.error);
							}
	        			}
	        			reload();
					});
					
				}
					
			});
		}else{
			info(global.notSelectInfo);
		}
	}
	function collapseAll(){
		var node = $('#auditTree').tree('getSelected');
		if (node){
			$('#auditTree').tree('collapseAll', node.target);
		} else {
			$('#auditTree').tree('collapseAll');
		}
	}
	function expandAll(){
		var node = $('#auditTree').tree('getSelected');
		if (node){
			$('#auditTree').tree('expandAll', node.target);
		} else {
			$('#auditTree').tree('expandAll');
		}
	}
	function reload(){
		treeLoadData();
	}
	function step_return(){
		var url = "<s:url value='/audit/audit_setProduct.jhtml'/>";	
		redirectUrl(url);
	}
	function chooseBrchCallback(row){
		if(row&&row.brchName&&row.brchId){
			$("#brchName").val(row.brchName);
			$("#brchId").val(row.brchId);
		}
	}
	function toStationAssign(){
		var node = $('#auditTree').tree('getSelected');
		if(node){
			var id=node.id;
			var type=node.attributes.type;
			var url='<s:url value="/audit/audit_toAssignStation.jhtml"/>?nodeId='+id+'&nodeType='+type+'&auditRoute.id=<s:property value="auditRoute.id"/>';
			redirectUrl(url);
		}else{
			info(global.notSelectInfo);
		}
	}
	$(".easyui-menu").menu();
	var tipm="${tipMsg}";
	if(tipm!="null"&&tipm.length>4){
		info('${tipMsg}');
	}
	treeLoadData();
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>