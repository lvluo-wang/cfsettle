<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-set" text="assign" click="doAssign"/>
		<x:button iconCls="icon-edit" text="edit" click="doEdit"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="mainQueryForm" class="query_form">
			<table><tr>
				<td class="title"><s:text name="member"/>:</td>
				<td><input id="CHOOSE_MINAME"  class="inputSel" onClick="chooseMember(chooseMiNoCallBack)"/>
					<s:hidden name="mp.miNo" id="CHOOSE_MINO" />
				</td>
				<td>
					<x:button iconCls="icon-search" text="query" click="doQuery"/>
				</td>
			</tr></table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
			<tr>
				<td style="vertical-align:top;height:100%;width:210px;">
					<div style="overflow:auto;height:400px;">
						<ul id="productTree"></ul>
					</div>
				</td>
				<td id="productDetail" style="vertical-align:top;height:100%;border-left:1px solid #99BBE8;">
					<div style="display:none;">
					<form id="datagridQueryForm">
						<input id="dg_prodId_id" name="prodId"/>
						<input id="dg_miNo_id" name="miNo"/>
					</form>
					</div>
					<x:datagrid id="attrsTable" url="/product/product_getAttributesByProdId.jhtml" autoload="false" pagebar="false" form="datagridQueryForm">
						<x:columns>
							<x:column title="" checkbox="true" field="id" />
							<x:column title="name" field="attributeName" align="left" width="200" />
							<x:column title="value" field="attributeValueDisp" align="left" width="200" />
						</x:columns>
					</x:datagrid>
				</td>
			</tr>
		</table>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="attr_edit" style="display:none;width:350px;"></div>
		<div id="product_attributes"  style="width:280px;height:400px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script>
			var selectedMiNo = '';
			function doQuery(){
				selectedMiNo = $("#CHOOSE_MINO").val()
				$("#productTree").tree({
					url:'<s:url value="/product/product_getMemberProducts.jhtml"/>?miNo=' + selectedMiNo ,
					onClick:function(node){
						var prodId=node.id;
						$("#dg_prodId_id").val(prodId);
						$("#dg_miNo_id").val(selectedMiNo);
						attrsTable.load();
					}
				});
				$("#dg_prodId_id").val('');
				attrsTable.load();
			}
			function doEdit(){
				if(isSingleSelected(attrsTable)){
					var selectedId=attrsTable.getSelectedField("id");
					var url="<s:url value='/product/product_editMPAPage.jhtml' />?id="+selectedId;
					requestAtWindow(url,"attr_edit","<s:text name="edit"/>");
				}
			}
			function doAssign(){
				var node = $('#productTree').tree('getSelected');
				if( node ){
					var prodId=node.id;
					var url='<s:url value="/product/product_productAttributes.jhtml"/>?prodId='+prodId+"&miNo="+selectedMiNo;
					requestAtWindow(url,"product_attributes",'<s:text name="set"/><s:text name="attribute"/>');
				}else{
					info(global.notSelectInfo);
				}
			}
			function chooseMiNoCallBack(row){
				var n = '',v='';
				if(row && row.miNo && row.miName){
					n = row.miName;
					v = row.miNo;
				}
				$("#CHOOSE_MINO").val(v);
				$("#CHOOSE_MINAME").val(n);
			}
		</script>
	</tiles:putAttribute>
	
</tiles:insertDefinition>