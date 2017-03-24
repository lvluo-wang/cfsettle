<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">
   <tiles:putAttribute name="tool">
		<x:button iconCls="icon-save" onClick="doSave()" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doCancel()" text="cancel"/>
		<form method="post" id="submitForm" >
			<s:hidden name="miNo"></s:hidden>
		 	<s:hidden name="treeJSONInfo"></s:hidden>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table class="detail_table">
			<colgroup>
				<col width="252px"/>
				<col width="100%"/>
			</colgroup>
			<tbody>
				<tr>
					<td class="detail_head_title"><s:text name="process"/><s:text name="list"/></td>
					<td class="detail_head_title"><s:text name="process"/><s:text name="set"/></td>
				</tr>
				<tr>
					<td class="detail_td" style="vertical-align:top;">
						<div  style="vertical-align:top; width:250px;padding:1px;height:280px;overflow:auto;">
							<ul id="procTree" class="easyui-tree"></ul>
						</div>
					</td>
					<td class="detail_td" style="vertical-align:top;">
						<div id="default_proc_div">							
						</div>
						
					</td>
				</tr>
					
			</tbody>
		</table>		
					
				<div id="unFuncMenu" class="easyui-menu" style="width:120px;display:none;">
					<div onclick="expandAll($('#unFuncTree'))"><s:text name="expandAll"/></div>
					<div onclick="collapseAll($('#unFuncTree'))"><s:text name="collapseAll"/></div>
					<div class="menu-sep"></div>
					<div onclick="moveToRight()" iconCls="icon-next"><s:text name="moveRigth"/></div>
					<div class="menu-sep"></div>		
				</div>
				<div id="beProcMenu" class="easyui-menu" style="width:120px;display:none;">
					<div onclick="expandAll($('#beFuncTree'))"><s:text name="expandAll"/></div>
					<div onclick="collapseAll($('#beFuncTree'))"><s:text name="collapseAll"/></div>
					<div class="menu-sep"></div>		
					<div onclick="moveToLeft()" iconCls="icon-back"><s:text name="moveLeft"/></div>
					<div class="menu-sep"></div>
				</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">	
	function collapseAll(thisTree){
		var node = thisTree.tree('getSelected');
		if (node){
			thisTree.tree('collapseAll', node.target);
		} else {
			thisTree.tree('collapseAll');
		}
	}
	function expandAll(thisTree){
		var node = thisTree.tree('getSelected');
		if (node){
			thisTree.tree('expandAll', node.target);
		} else {
			thisTree.tree('expandAll');
		}
	}
	
		
		//获取树内所有节点ID,逗号分隔。
		function getTreeCheckedNodeId(treeId){		
			var nodes = $('#'+treeId).tree('getChecked');		
			var s='';
			if(nodes!=null){
				for(var i=0;i<nodes.length;i++){				
					if(s!=''){
						s+=',';
					}
					s+=nodes[i].id;
				}
			}
			return s;
		}
	
		function doSave(){
			
			var beIds = getTreeCheckedNodeId("procTree");
			
			$.messager.confirm(global.alert,"<s:text name="将保存流程分配设置，确认吗？"/>", function(r){
				if(r){
					var pids = "";
					$("select[key='s']").each(function(i){//遍历下拉项
						pids += this.value+",";
					});	
					
					var url = "<s:url value='/bpm/procmap_saveMemberProc.jhtml'/>?ids="+beIds;
					url += "&pids="+pids;
					formSubmitForDatagrid(url,"submitForm","dataTable");
					$('#member_proc').window('close');
				}
				
			});
		}
		
		function doCancel(){
			$('#member_proc').window('close');
		}
		
		
		var procSelObj = {};//一个对象
		procSelObj.prodNos = '';//放入产品号，','分隔。
		setTimeout('initDefaultProc()', 200 );

		function initDefaultProc(){
			var nodes = $("#procTree").tree('getRoots');	
			if(nodes.length<1){
				info("流程列表为空：系统未发布任何流程或流程对应业务产品尚未定义。");
				
				return;
			}
			//var procSelObj = {};//一个对象
			for (var i=0; i<nodes.length; i++){
				var node = nodes[i];
				if(node.checked){
					if (procSelObj.prodNos.indexOf(node.attributes.prodNo+',')<0){
						procSelObj.prodNos += node.attributes.prodNo+',';
					}
					var prodNo = node.attributes.prodNo;
					if (procSelObj[prodNo]){				
						procSelObj[prodNo].push([node.id, node.text,  node.attributes.isDefault, node.attributes.prodName]);
					}else{
						procSelObj[prodNo] = new Array();
						procSelObj[prodNo].push([node.id, node.text,  node.attributes.isDefault, node.attributes.prodName]);
					}
					
				}
				
			}
			
			var prodNos = procSelObj.prodNos.split(",");
			for(i=0; i<prodNos.length-1; i++){//最后的','会多占一个，所以i<prodNos.length-1
				  var prodNo = prodNos[i];
				  if (procSelObj[prodNo].length>=2){
					  $("#default_proc_div").append("<p id=proc_p_"+prodNo+">"+procSelObj[prodNo][0][3]+":<select key='s' id=procsel_"+prodNo+" ></select></p>"); 
					  procsel_input = document.getElementById("procsel_"+prodNo);
					  for(var j=0; j<procSelObj[prodNo].length; j++){
						  var procData = procSelObj[prodNo][j];
						  procsel_input.options.add(new Option(procData[1], procData[0],null,procData[2]=='1'));				  
					  }
				  }
			}

			$("#procTree").tree('options').onCheck = function(node, checked){
				//勾项触发事件
				var prodNo = node.attributes.prodNo;
				if (checked){
					//勾中一项
					if (procSelObj.prodNos.indexOf(prodNo+',')<0){
						procSelObj.prodNos += prodNo+',';
					}
					if (procSelObj[prodNo]){				
						procSelObj[prodNo].push([node.id, node.text, node.attributes.isDefault, node.attributes.prodName]);
					}else{
						procSelObj[prodNo] = new Array();
						procSelObj[prodNo].push([node.id, node.text, node.attributes.isDefault, node.attributes.prodName]);
					}
					procsel_input = document.getElementById("procsel_"+prodNo);
					if (procsel_input){
						procsel_input.options.add(new Option(node.text, node.id,null,false));			
					}else{
						if (procSelObj[prodNo].length>=2){
							 $("#default_proc_div").append("<p id=proc_p_"+prodNo+">"+procSelObj[prodNo][0][3]+":<select key='s' id=procsel_"+prodNo+" ></select></p>"); 
							  procsel_input = document.getElementById("procsel_"+prodNo);
							  for(j=0; j<procSelObj[prodNo].length; j++){
								  var procData = procSelObj[prodNo][j];
								  procsel_input.options.add(new Option(procData[1], procData[0],null,false));				  
							  }
						}
						
					}
					
				}else{
					//除去勾中项
					for (var i=0; i<procSelObj[prodNo].length; i++){
						var procData = procSelObj[prodNo][i];
						if (procData[0] == node.id){
							procSelObj[prodNo].splice(i,1);//删除下标i项.
							break;
						}
					}
					if (procSelObj[prodNo].length==0){//如果为空，则除去
						procSelObj.prodNos = procSelObj.prodNos.replace(prodNo+',','');
					}
					if (procSelObj[prodNo].length==1){//
						procsel_p = document.getElementById("proc_p_"+prodNo);
						document.getElementById("default_proc_div").removeChild(procsel_p) ;
					}
					if (procSelObj[prodNo].length>=2){//
						procsel_input = document.getElementById("procsel_"+prodNo);
						for(i=0; i<procsel_input.options.length; i++){
							if (node.id==procsel_input.options[i].value){
								procsel_input.options.remove(procsel_input.options[i]);
								break;
							}
						}
					}
				
				}
			};
			
		}
		$(".easyui-menu").menu();
		$("#procTree").tree({
			checkbox: true,
			data:eval('('+$("#treeJSONInfo").attr("value")+')')
		});
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

