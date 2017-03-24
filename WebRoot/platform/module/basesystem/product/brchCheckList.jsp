<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_DATA">
<tiles:putAttribute name="head">
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/branch-validate.js"/>"></script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">
	<x:button icon="icon-audit" click="doBrchFunc" text="check,product"/>
</tiles:putAttribute>
<tiles:putAttribute name="data">			
	<x:datagrid id="dataTable" autoload="false" url="/product/product_queryCheckBrch.jhtml">
		<x:columns>
			<x:column field="id" checkbox="true" width="20" />
			<x:column title="brch.brchNo" field="brchNo"  width="120"/>
			<x:column title="brch.name_" field="brchName"  width="360"/>
			<x:column title="brch.parentName" field="parentBrchName"  width="120"/>
		</x:columns>
	</x:datagrid>	
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<div id="brch_prod"  style="width:600px;display:none;"></div>
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	function initPage(){	
		
		dataTable.load();
		
	}
	
	function doBrchFunc(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"brchId");
			
			var url = "<s:url value='/product/product_toCheckBranchProduct.jhtml'/>?";
			url = url + "id="+selectedId;
			requestAtWindow(url,"brch_prod","产品权限复核");
		}
	}
	
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
