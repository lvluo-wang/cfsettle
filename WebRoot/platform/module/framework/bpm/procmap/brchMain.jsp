<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button icon="icon-ok" click="doBrchProcess" text="process,set"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" url="/bpm/procmap_brchQuery.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="brch.brchNo" field="brchNo"   width="150" />
				<x:column title="brch.name_"  field="brchName"   width="180" />
				<x:column title="memberInfo.miNo"  field="miNo"   width="180"  />
				<x:column title="brch.parentName"   field="parentBrchName"  width="180" />
				
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="brch_proc"  		style="width:700px;display:none"></div>
		<div id="error_view"  style="width:500px;display:none"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		
		//工具栏具体实现方法
		function initPage(){
			dataTable.load();
		}
		function doBrchProcess(){
			if(isSingleSelected(dataTable)){
				var selectedId=getSelected(dataTable,"brchId");
				var url="<s:url value='/bpm/procmap_toBrchProc.jhtml' />?brchId="+selectedId+"&flag="+Math.random()*99999;
				requestAtWindow(url,"brch_proc",'<s:text name="product"/><s:text name="process"/>');
			}
		}
		
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>