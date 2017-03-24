<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="head">
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/member-validate.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="tool">
			<x:button icon="icon-add" click="doAdd" text="add"/>
			<x:button icon="icon-edit" click="doEdit" text="edit"/>
			<x:button icon="icon-print" click="doView" text="view"/>
			<span class="separator"></span>
			<x:button icon="icon-yes" click="doOpen" text="open"/>
			<x:button icon="icon-no" click="doClose" text="close"/>
			<span class="separator"></span>
			<x:button icon="icon-ok" click="doConfig" text="memberInfo.config"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" class="query_form">
			<table>
				<tr>
					<td><s:text name="memberInfo.miNo"/>:</td>
					<td><input name="member.miNo"></input></td>
					<td><s:text name="memberInfo.miName"/>:</td>
					<td><input name="member.miName"></input></td>
					<td>
					<x:button icon="icon-search" click="doSearch" text="query"/>
					</td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="memberMainDG" url="/security/member_data.jhtml" form="queryForm" >
			<x:columns>
				<x:column title="" field="miNo" checkbox="true" width="20" />
				<x:column title="memberInfo.miNo" field="miNo" width="120" align="left" />
				<x:column title="memberInfo.miName" field="miName" width="200" align="left" />
				<x:column title="memberInfo.miType" field="miType" width="100" formatter="memberFormatter"/>
				<x:column title="memberInfo.status" field="isOpen" width="100" formatter="memberFormatter"/>
				<x:column title="memberInfo.miDt" field="miDt" width="200" formatter="memberFormatter"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="member_add_edit"  style="display:none;width:500px;"></div>
	<div id="member_config"  style="display:none;width:500px;"></div>
	<div id="member_product"  style="display:none;width:500px;"></div>
	<div id="error_view"  style="display:none;width:500px;"></div>
	<div id="member_sysparam"  style="display:none;width:280px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">

<script type="text/javascript">
var keys=["B004"];
var code=new CodeUtil(keys);
code.loadData();
function memberFormatter(value,field,row,rowIndex){
	if(field == "miType"){
		return '<span>'+code.getValue("B004",value)+'</span>';
	}else if(field == "isOpen"){
		if (value=="0")
			return '<span style="color:red"><s:text name="member.status.close"/></span>';
		if (value=="1")
			return '<span style="color:green"><s:text name="member.status.open"/></span>';
	}else if(field == "miDt"){
		if(value){
			if(value.time){
				return DateFormat.format(new Date(value.time));
			}
		}
	}
	return "";
}

function initPage(){
	//memberMainDG.load();
}
	//工具栏具体实现方法
	function doSearch(){
		memberMainDG.load();
	}
	
	function doAdd(){
		var url='<s:url value="/security/member_add.jhtml"/>?flag='+Math.random()*99999;
		requestAtWindow(url,"member_add_edit",'<s:text name="add"/>');
	}
	
	function doEdit(){
	       var num = memberMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var selectedId = memberMainDG.getSelectedIds();
	           var url="<s:url value='/security/member_edit.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
	           requestAtWindow(url,"member_add_edit",'<s:text name="edit"/>');
	       }
	}
	
	function doView(){
	       var num = memberMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var selectedId = memberMainDG.getSelectedIds();
			   var url="<s:url value='/security/member_view.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
			   requestAtWindow(url,"member_add_edit",'<s:text name="view"/>');
	       }
	}
	
	function doOpen(){
	       var num = memberMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var isopen = memberMainDG.getSelectedField("isOpen");
				if(isopen=="1"){
					return;
				}
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){
					if(r){
						var selectedId = memberMainDG.getSelectedIds();
						var url="<s:url value='/security/member_open.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
						requestAndRefresh(url,"dataTable");
						doPost(url,{}, function(result) {
							printError(result);
							memberMainDG.refresh();
						 });
					}
				});
	       }
	}
	
	function doClose(){
	       var num = memberMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var isopen = memberMainDG.getSelectedField("isOpen");
				if(isopen=="0"){
					return;
				}
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){
					if(r){
						var selectedId = memberMainDG.getSelectedIds();
						var url="<s:url value='/security/member_close.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
						doPost(url,{}, function(result) {
							printError(result);
							memberMainDG.refresh();
						 });
					}
				});
	       }
	}
	
	function doConfig(){
	       var num = memberMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var selectedId = memberMainDG.getSelectedIds();
				var url="<s:url value='/security/member_config.jhtml' />?member.miNo="+selectedId+"&flag="+Math.random()*99999;
				requestAtWindow(url,"member_config",'<s:text name="config"/>');
	       }
	}
	
	function doProduct(){
	       var num = memberMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
			   var selectedId = memberMainDG.getSelectedIds();
			   var url="<s:url value='/security/member_toMemberProduct.jhtml' />?member.miNo="+selectedId;
			   requestAtWindow(url,"member_product",'<s:text name="product"/><s:text name="distribution"/>');
	       }
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>