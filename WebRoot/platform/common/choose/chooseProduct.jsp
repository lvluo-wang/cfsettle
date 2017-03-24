<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseProductSelect" icon="icon-yes"  text="choose"/>
		<x:button id="chooseProductCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<div style="height:270px;overflow:auto;">
			<ul id="chooseProductTree" class="easyui-tree" style="height:270px;"></ul>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
ChooseContext["choose_product"].init=function(){
	var pram={};
	var url="";
	if(ChooseContext["choose_product"].attrKey&&ChooseContext["choose_product"].attrValue){
		pram={attrKey:ChooseContext["choose_product"].attrKey,attrValue:ChooseContext["choose_product"].attrValue};
		url="<s:url value="/web_ui/choose_queryAttrProduct.jhtml"/>?attrKey="+pram.attrKey+"&attrValue="+pram.attrValue;
		if (ChooseContext["choose_product"].prodNos && ChooseContext["choose_product"].prodNos != 'undefined') {
			pram.prodNos=ChooseContext["choose_product"].prodNos;
			url+="&prodNos="+pram.prodNos;
		}
		if (ChooseContext["choose_product"].notProdNos && ChooseContext["choose_product"].notProdNos != 'undefined') {
			pram.notProdNos=ChooseContext["choose_product"].notProdNos;
			url+="&notProdNos="+pram.notProdNos;
		}
		if (ChooseContext["choose_product"].prodType && ChooseContext["choose_product"].prodType != 'undefined') {
			pram.prodType=ChooseContext["choose_product"].prodType;
			url+="&prodType="+pram.prodType;
		}
		
	}else{
		pram={prodType:ChooseContext["choose_product"].prodType};
		url='<s:url value="/web_ui/choose_queryProduct.jhtml"/>?prodType='+pram.prodType;
		if (ChooseContext["choose_product"].prodNos && ChooseContext["choose_product"].prodNos != 'undefined') {
			pram.prodNos=ChooseContext["choose_product"].prodNos;
			url+="&prodNos="+pram.prodNos;
		}
		if (ChooseContext["choose_product"].notProdNos && ChooseContext["choose_product"].notProdNos != 'undefined') {
			pram.notProdNos=ChooseContext["choose_product"].notProdNos;
			url+="&notProdNos="+pram.notProdNos;
		}
	}
	
	var checkbox = false;
	var  productDblClick = function(node){
		var r = true;
		if(ChooseContext["choose_product"].callback&&$.isFunction(ChooseContext["choose_product"].callback)){
				var _product = str2obj(node.attributes.product);
				var isLeaf = false;
				try{
					isLeaf = $("#chooseProductTree").tree("isLeaf",node.target);
				}catch(e){}
				_product.isLeaf=isLeaf;
				r = ChooseContext["choose_product"].callback(_product);
		}
		if (!(r === false)) {
			ChooseContext["choose_product"].close();
			var target = ChooseContext["choose_product"].target;
			if (target) {
				$(target).focus();
			}
		}
		return false;
	};
	
	if (ChooseContext["choose_product"].multi == 'true') {
		checkbox = true;
		productDblClick = function(node){
			//多选的时候双击失效 		
		};
	}
	
	$("#chooseProductTree").tree({
		checkbox: checkbox,
		url:url,
		onDblClick:productDblClick

	});
	
};
//选择按钮绑定的事件
$("#chooseProductSelect").unbind().click(function(){
	//单选状态
	if(ChooseContext["choose_product"].multi != 'true'){
		var node = $('#chooseProductTree').tree('getSelected');
		if(node&&node.attributes){
			var r = true;
			if(ChooseContext["choose_product"].callback&&$.isFunction(ChooseContext["choose_product"].callback)){
					var _product = str2obj(node.attributes.product);
					var isLeaf = false;
					try{
						isLeaf = $("#chooseProductTree").tree("isLeaf",node.target);
					}catch(e){}
					_product.isLeaf=isLeaf;
					r = ChooseContext["choose_product"].callback(_product);
			}
			if (!(r === false)) {
				ChooseContext["choose_product"].close();
				var target = ChooseContext["choose_product"].target;
				if (target) {
					$(target).focus();
				}
			}
			return false;
		}else{
			warning(global.notSelectInfo);
		}
		return false;
	}else{
	//多选状态
		var nodes = $('#chooseProductTree').tree('getChecked');		
			if(nodes){
				var str = "[";
				for(var i=0;i<nodes.length;i++){
					if(i == nodes.length-1){
						str += nodes[i].attributes.product;
					}else{
						str += (nodes[i].attributes.product + ',');
					}
				}
				str += ']';
			
				var r = true;
				if(ChooseContext["choose_product"].callback&&$.isFunction(ChooseContext["choose_product"].callback)){
						var _product = str2obj(str);
						for(var i in nodes){
							var isLeaf = false;
							try{
								isLeaf = $("#chooseProductTree").tree("isLeaf",nodes[i].target);
							}catch(e){}
							_product[i].isLeaf=isLeaf;
						}
						r = ChooseContext["choose_product"].callback(_product);
				}
				if (!(r === false)) {
					ChooseContext["choose_product"].close();
					var target = ChooseContext["choose_product"].target;
					if (target) {
						$(target).focus();
					}
				}
			
			return false;
		}else{
			warning(global.notSelectInfo);
		}
		
		return false;
	}	
});
$("#chooseProductCancel").unbind().click(function(){
	if(ChooseContext["choose_product"].callback&&$.isFunction(ChooseContext["choose_product"].callback)){
		ChooseContext["choose_product"].callback();
	}
	ChooseContext["choose_product"].close();
	var target = ChooseContext["choose_product"].target;
	if (target) {
		$(target).focus();
	}
	return false;
});
</script>
	
	</tiles:putAttribute>
</tiles:insertDefinition>