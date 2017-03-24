<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseBranchSelect" icon="icon-yes"  text="choose"/>
		<x:button id="chooseBranchCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<div style="height:270px;overflow:auto;">
			<ul id="chooseBranchTree" class="easyui-tree" style="height:270px;"></ul>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">

var chooseContext_treebranch=ChooseContext["choose_treebranch"];
chooseContext_treebranch.init=function(){
	var param = chooseContext_treebranch.urlParam;
	var url = '<s:url value="/security/brch_getBranchTree.jhtml"/>';
	if (param && $.isPlainObject(param)) {
		for (var key in param ) {
			if (url.indexOf("?") == -1 ) {
				url += "?";
			}else{
				url += "&";
			}
			url += key+"="+param[key];
		}
	}
	var checkbox = false;
	if(chooseContext_treebranch.checkbox){
		checkbox = true;
	}
	
	var treeParam = {
		checkbox: checkbox,
		url:url,
		//data:str2obj(result),
		//cascadeCheck:checkbox
	};
	
	if(!checkbox){
		treeParam.onDblClick=function(node){
			if(chooseContext_treebranch.callback&&$.isFunction(chooseContext_treebranch.callback)){

					chooseContext_treebranch.callback(node.attributes);
				
			}
			chooseContext_treebranch.close();
			var target = chooseContext_treebranch.target;
			if (target) {
				$(target).focus();
			}
			return false;
		}
	}
	
	$("#chooseBranchTree").tree(treeParam);
};

$("#chooseBranchSelect").unbind().click(function(){
	var checkbox = false;
	if(chooseContext_treebranch.checkbox){
		checkbox = true;
	}
	if(!checkbox){
		var node = $('#chooseBranchTree').tree('getSelected');
		if(node&&node.attributes){
			if(chooseContext_treebranch.callback&&$.isFunction(chooseContext_treebranch.callback)){
				chooseContext_treebranch.callback(node.attributes);
			}
			chooseContext_treebranch.close();
			var target = chooseContext_treebranch.target;
			if (target) {
				$(target).focus();
			}
		}else{
			warning(global.notSelectInfo);
		}
	} else {
		var nodes = $('#chooseBranchTree').tree('getChecked');
		var result = {};
		var nodeNames = [];
		var nodeIds = [];
		$.each(nodes,function(index){
			var n = nodes[index];
			nodeNames.push(n.attributes.brchName);
			nodeIds.push(n.attributes.brchId);
		});
		result.brchNames=nodeNames;
		result.brchIds=nodeIds;
		if(nodes && nodes.length > 0){
			if(chooseContext_treebranch.callback&&$.isFunction(chooseContext_treebranch.callback)){
				chooseContext_treebranch.callback(result);
			}
			chooseContext_treebranch.close();
			var target = chooseContext_treebranch.target;
			if (target) {
				$(target).focus();
			}
		}else{
			warning(global.notSelectInfo);
		}
	}
	return false;
});

$("#chooseBranchCancel").unbind().click(function(){
	if(chooseContext_treebranch.callback&&$.isFunction(chooseContext_treebranch.callback)){
		chooseContext_treebranch.callback();
	}
	chooseContext_treebranch.close();
	var target = chooseContext_treebranch.target;
	if (target) {
		$(target).focus();
	}
	return false;
});
</script>
	
	</tiles:putAttribute>
</tiles:insertDefinition>