<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_DATA">
   <tiles:putAttribute name="tool">
		<x:button onclick="expandAll($('#funcTree'))" text="expandAll" icon="icon-view"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<div style="height:250px;overflow:auto;">
		<ul id="funcTree" class="easyui-tree"></ul>
		</div>
	</tiles:putAttribute>
    <tiles:putAttribute name="end">
<script type="text/javascript">
	$( function() {
		$("#funcTree").tree( {
			checkbox : false,
			data : eval('(${treeJSONInfo})')
			
		});
	});

	function collapseAll(thisTree) {
		var node = thisTree.tree('getSelected');
		if (node) {
			thisTree.tree('collapseAll', node.target);
		} else {
			thisTree.tree('collapseAll');
		}
	}
	function expandAll(thisTree) {
		var node = thisTree.tree('getSelected');
		if (node) {
			thisTree.tree('expandAll', node.target);
		} else {
			thisTree.tree('expandAll');
		}
	}

	function doClose() {
		$('#user_role').window('close');
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>