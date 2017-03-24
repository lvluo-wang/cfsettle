<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="head">
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/subsystem-validate.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="tool">
	        <x:button icon="icon-add" click="doAdd" text="add"/>
            <x:button icon="icon-edit" click="doEdit" text="edit"/>
            <x:button icon="icon-remove" click="doRemove" text="del"/>
			<span class="separator"></span>
			<x:button icon="icon-yes" click="doOpen" text="open"/>
            <x:button icon="icon-no" click="doClose" text="close"/>
			<span class="separator"></span>
			<x:button icon="icon-edit" click="doFunc" text="set,sysfunc"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" class="query_form">
			<table><tr>
				<td><s:text name="subsystem.subsysName"/>:</td>
				<td><input name="subsystem.subsysName" id="subsystem_subsysName" ></input></td>
				<td>
				<x:button icon="icon-search" click="doQuery" text="query"/>
				</td>
			</tr></table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<x:datagrid id="subsysMainDG" url="/security/subsystem_list.jhtml" form="queryForm" >
		<x:columns>
			<x:column title="" field="subsysId" checkbox="true" width="20" />
			<x:column title="subsystem.subsysName" field="subsysName" width="200" align="left" />
			<x:column title="subsystem.type" field="type" width="100" formatter="subsysFormatter"/>
			<x:column title="subsystem.subsysStatus" field="subsysStatus" width="100" formatter="subsysFormatter"/>
			<x:column title="remark" field="remark" width="300" />
		</x:columns>
	</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="subsystem_add_edit" style="width:460px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	var keys=['A013'];
	var code=new CodeUtil(keys);
	code.loadData();
	function subsysFormatter(value,field,row,rowIndex){
		if(field == "type"){
			return code.getValue('A013',value);
		}else if(field == "subsysStatus"){
			if (value=="0")
				return '<span style="color:red"><s:text name="subsystem.subsysStatus.close"/></span>';
			if (value=="1")
				return '<span style="color:green"><s:text name="subsystem.subsysStatus.open"/></span>';	
		}
		return "";	
	}
	
	function initPage(){
		//subsysMainDG.load();
		enterKeyPressInit([{'id':'subsystem_subsysName','keypress':'doQuery()'}]);
	}

	//工具栏方法实现区域
	function doQuery(){
		subsysMainDG.load();
	}
	function doAdd(){
		var url="<s:url value='/security/subsystem_toAdd.jhtml'/>";
		requestAtWindow(url,"subsystem_add_edit","<s:text name="add"/>");		
	}
	function doEdit(){
		if(isSingleSelected(subsysMainDG)){
	           var selectedId = subsysMainDG.getSelectedField("subsysId");
	           var url="<s:url value='/security/subsystem_toEdit.jhtml' />?subsystem.subsysId="+selectedId;
	           requestAtWindow(url, "subsystem_add_edit", "<s:text name="edit"/>");
		}
	}	
	function doRemove(){
	       if(subsysMainDG.getSelectedRowNum()>0){
	           $.messager.confirm(global.alert,"<s:text name="prompt_delete"/>",
	                  function(r) {
	                     if (r) {
	                         var ids = subsysMainDG.getSelectedIds();
	                         var url="<s:url value='/security/subsystem_batchDelete.jhtml'/>";
	                         doPost(url,{"ids":ids},function(result){
	                            printError(result);
	                            subsysMainDG.refresh();
	                         });
	                     }
	                  });
	       }else{
	           info(global.notSelectInfo);
	       }
	}
	function doOpen(){
	       var num = subsysMainDG.getSelectedRowNum();//查询选中的行数
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var isopen = subsysMainDG.getSelectedField("subsysStatus");
				if(isopen == "1"){
					return;
				}
	           var selectedId = subsysMainDG.getSelectedField("subsysId");//获取选中行的指定列数据
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){
					if(r){
						var url="<s:url value='/security/subsystem_open.jhtml'/>";				
						doPost(url,{"subsystem.subsysId":selectedId}, function(result) {
							printError(result);
							subsysMainDG.refresh();
						 });			
					}
				});
	       }else{
	           info(global.notSelectInfo);
	       }
	}
	
	function doClose(){
	       var num = subsysMainDG.getSelectedRowNum();//查询选中的行数
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var isopen = subsysMainDG.getSelectedField("subsysStatus");
				if(isopen=="0"){
					return;
				}
	           var selectedId = subsysMainDG.getSelectedField("subsysId");//获取选中行的指定列数据
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){
					if(r){
						var url="<s:url value='/security/subsystem_close.jhtml'/>";					
						doPost(url,{"subsystem.subsysId":selectedId}, function(result) {
							printError(result);
							subsysMainDG.refresh();
						 });			
					}
				});
	       }else{
	           info(global.notSelectInfo);
	       }
	}
	
	function doFunc(){
	       var num = subsysMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var selectedId = subsysMainDG.getSelectedField("subsysId");
	           var selectedName = subsysMainDG.getSelectedField("subsysName");
	           var url = "<s:url value='/security/subsystem_funcMain.jhtml'/>";
	           redirectUrl(url,{"subsystem.subsysId":selectedId,"subsystem.subsysName":selectedName});
	       }else{
	           info(global.notSelectInfo);
	       }
	}
	
	function doCancel(){
		$("#subsystem_add_edit").window("close");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
