<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/query.css'/>"    /> 
<script type="text/javascript">
	$(function(){
		var componentId='<s:property value="queryComponentId"/>';
		var componentUrl='<s:property value="queryComponentUrl"/>';
		if(componentId!=null&&componentId.length>5){
			query=new initQueryComponent2('queryView',componentId,"querycontent");
		}else if(componentUrl!=null&&componentUrl.length>5){
			query=new initQueryComponent2('queryView',componentId,"querycontent",componentUrl);
		}
		
	});
	function closeQuery(){
		query.closeWindow();
	}
</script>
<div id="queryView" class="easyui-window" closed="true" style="padding:0px;">
	 	<div class="easyui-layout" fit="true">
			<div region="center"  id="queryComponent" name="queryComponent"  border="false" style="background:#fff;border:1px solid #ccc;">
				<form id='queryComponentForm'>
				<input type="hidden" id="queryComponentId" name="queryComponentId" value='<s:property value="queryComponentId"/>'/>
				
					<div id="querycontent" class="querycontent">
					
					</div>
			
				</form>
			</div>
			<div region="south"  border="false" style="text-align:right;height:30px;line-height:30px;background:#efefef;">
				<a  href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="doSearch();"><s:text name="query"/></a>
				<a  href="#" class="easyui-linkbutton" iconCls="icon-cancel" onClick="closeQuery();"><s:text name="cancel"/></a>
			</div>
		</div>
</div> 