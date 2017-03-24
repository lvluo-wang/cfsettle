<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
		<tiles:putAttribute name="tool">
			<x:button iconCls="icon-add" onClick="setRoute" text="assignAuditRoute"/>
			<x:button iconCls="icon-cancel" onClick="cancelStation()" text="cancelAuditRoute"/>
			<x:button iconCls="icon-ok" onClick="setStation()" text="set,auditStation"/>
		</tiles:putAttribute>
		<tiles:putAttribute name="query">
			<form id="query_form" class="query_form">
				<table>
					<tr>
						<td class="title"><s:text name="prodNo"/>:</td>
						<td><input name="product.prodNo" id="product_prodNo" ></input></td>
						<td class="title"><s:text name="prodName"/>:</td>
						<td><input name="product.prodName" id="product_prodName" ></input></td>
						<td><x:button iconCls="icon-search" onClick="doMainQuery()" text="query"/></td>
					</tr>
				</table>
				
			</form>
		</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="prodAuditRouteGrid" autoload="false" form="query_form" url="/audit/audit_productAuditList.jhtml">
			<x:columns>
				<x:column field="prodId" checkbox="true"/>
				<x:column field="prodNo" title="prodNo" width="150" align="left"/>
				<x:column field="prodAlias" title="prodName" width="120" align="left"/>
				<x:column field="auditRouteName" title="auditRouteName" width="200" align="left"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="auditRoute_select"  style="width:500px;display:none;"></div>
	</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	function initPage(){
		prodAuditRouteGrid.load();
	}
	//为产品设置审批路线
	function setRoute(){
		if(isSelected(prodAuditRouteGrid)){
			var arids=getMultiSelectedArray(prodAuditRouteGrid,"auditRouteId");
			if(arids&&arids.length){
				for(var i=0;i<arids.length;i++){
					var arid=arids[i];
					if( arid&&(arid+"").length>0 ){
						info(global.set_auditRoute_for_bindedProd);
						return;
					}
				}
			}
			var ids=getMutSelected(prodAuditRouteGrid,"prodId");
			var url='<s:url value="/audit/audit_assignAuditRouteForProduct.jhtml"/>';
			chooseAuditRoute(function(row){
				if(row&&row.id){
					prodAuditRouteGrid.call(url,{prodIds:ids,"auditRoute.id":row.id});
				}
			});
		}
	}
	//设置审批岗位
	function setStation(){
		if(isSingleSelected(prodAuditRouteGrid)){
			var auditRouteId=getSelected(prodAuditRouteGrid,"auditRouteId");
			if(!auditRouteId||auditRouteId==null||auditRouteId==''){
				info('请先分配审批路线');
				return;
			}
			var url='<s:url value="/audit/audit_setProductAuditStation.jhtml"/>?auditRoute.id='+auditRouteId;
			redirectUrl(url);
		}
		
	}
	function doMainQuery(){
		prodAuditRouteGrid.load();
	}
	function cancelStation(){
		if(isSelected(prodAuditRouteGrid)){
			var ids=getMutSelected(prodAuditRouteGrid,"rapId");
			var url='<s:url value="/audit/audit_cancelAuditRouteForProduct.jhtml"/>';
			prodAuditRouteGrid.call(url,{"ids":ids});
		}
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>