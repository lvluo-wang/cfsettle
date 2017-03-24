<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-save" text="save" click="doPASave"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<ul id="attributesTree"></ul>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	function initPage(){
		$('#attributesTree').tree({
			checkbox:true,
			url:'<s:url value="/product/product_getAllAttributes.jhtml"/>?prodId=<s:property value="prodId"/>&miNo=<s:property value="miNo"/>'
		});
	}
	function doPASave(){
		var nodes = $('#attributesTree').tree('getChecked');
		var ids = '';
		for(var i=0; i<nodes.length; i++){
			if (ids != '') ids += ',';
			ids += nodes[i].id;
		}
		doPost('<s:url value="/product/product_assignAttrsForProduct.jhtml"/>',{"ids":ids,"prodId":'<s:property value="prodId"/>',"miNo":'<s:property value="miNo"/>'},function(){
			$("#product_attributes").window("close");
			attrsTable.refresh();
		});
	}
</script>	
	</tiles:putAttribute>
</tiles:insertDefinition>