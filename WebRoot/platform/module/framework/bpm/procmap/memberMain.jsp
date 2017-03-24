<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="head">
		<script language="javascript" src="${pageContext.request.contextPath}/platform/module/basesystem/security/resources/message_${session.WW_TRANS_I18N_LOCALE}.js"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="tool">
		<x:button icon="icon-set" click="doProcess" text="process,distribution"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" form="queryForm" url="/bpm/procmap_data.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="memberInfo.miNo" field="miNo"   width="50" />
				<x:column title="memberInfo.miName"  field="miName"   width="180" />
				<x:column title="memberInfo.miType"  field="miType"   width="180" formatter="type_column"/>
				<x:column title="memberInfo.status"   field="isOpen"  width="180" formatter="status_column"/>
				<x:column title="memberInfo.miDt"   field="miDt"  width="180" formatter="format2Time"/>
				
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="member_proc"  		style="width:700px;display:none"></div>
		<div id="member_prod_proc"  style="width:500px;display:none"></div>
		<div id="member_product"  	style="width:500px;display:none"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		function initPage(){			
			dataTable.load();
		};
		
		function type_column(value,field,rowData,index){
			if (value=="1")
				return '<span>'+global.member_type_Bank+'</span>';
			if (value=="2")
				return '<span>'+global.member_type_Finance_Company+'</span>';	
			
				if (value=="3")
				return '<span>'+global.member_type_Enterprise+'</span>';
		}
		function status_column(value,field,rowData,index){
			if (value=="0")
				return '<span style="color:red">'+global.member_status_Close+'</span>';
			if (value=="1")
				return '<span style="color:green">'+global.member_status_Open+'</span>';
		}
		
		
		
		function doOpen(){
			if(isSingleSelected(dataTable)){
				var isopen=getSelected(dataTable,'isOpen');
				if(isopen=="1"){
					info(global.member_open_false_tip);
					return;
				}
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){
					if(r){
						var selectedId=getSelected(dataTable,"miNo");
						var url="<s:url value='/security/member_open.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
						requestAndRefresh(url,"dataTable");
					}
				});
				
			}
		}
		function doClose(){
			if(isSingleSelected(dataTable)){
				var isopen=getSelected(dataTable,'isOpen');
				if(isopen=="0"){
					info(global.member_close_false_tip);
					return;
				}
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){
					if(r){
						var selectedId=getSelected(dataTable,"miNo");
						var url="<s:url value='/security/member_close.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
						requestAndRefresh(url,"dataTable");
					}
				});
			}
		}
		
		function doProcess(){
			if(isSingleSelected(dataTable)){
				var selectedId=getSelected(dataTable,"miNo");
				var url="<s:url value='/bpm/procmap_toMemberProc.jhtml'/>?miNo="+selectedId;
				requestAtWindow(url,"member_proc",'<s:text name="process"/><s:text name="distribution"/>');
			}
		}
		function doProdProcess(){
			if(isSingleSelected(dataTable)){
				var selectedId=getSelected(dataTable,"miNo");
				var url="<s:url value='/bpm/procmap_toMemberProdProc.jhtml' />?miNo="+selectedId+"&flag="+Math.random()*99999;
				requestAtWindow(url,"member_prod_proc",'<s:text name="product"/><s:text name="process"/>');
			}
		}
		
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>





