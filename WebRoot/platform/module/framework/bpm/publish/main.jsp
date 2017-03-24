<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">	

	<tiles:putAttribute name="tool">
			<x:button icon="icon-add" click="toPublish" text="publish"/>
			<x:button icon="icon-edit" click="doEdit" text="edit"/>
			<x:button icon="icon-remove" click="doRemove" text="del"/>
			<x:button icon="icon-next" click="doEnterTask" text="task"/>			
	</tiles:putAttribute>
	<tiles:putAttribute name="data">			
		<x:datagrid id="dataTable" autoload="false" url="/bpm/publish_queryData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="process.procName"  field="procName"   width="200"/>
				<x:column title="process.desiProdNo"  field="desiProdNo"   width="120"/>
				<x:column title="process.procCnName"   field="procCnName"  width="120"/>
				<x:column title="process.procNameKey" field="procNameKey"  width="200" />
				<x:column title="process.createTime" field="createTime"    width="100" formatter="format2Date"/>
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"  style="width:500px;padding:5px;background: #fafafa;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			function initPage(){
				dataTable.load();
			}
			function toPublish(){
				var url = "<s:url value='/bpm/publish_toPublish.jhtml'/>";
				redirectUrl(url);  
			}
			function doEdit(){
				if(isSingleSelected(dataTable)){
					var selectedId=getSelected(dataTable,"id");
					var url="<s:url value='/bpm/publish_edit.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;
					requestAtWindow(url,"add_edit",'<s:text name="edit"/>');
				}
		
			}
			function doRemove(){	
				if (isSingleSelected(dataTable)){			
					var selectedId=getSelected(dataTable,"id");
					$.messager.confirm(global.alert,"<s:text name="prompt_delete_process"/>", function(r){
						if(r){
							url = "<s:url value='/bpm/publish_deleteProcess.jhtml'/>?id="+selectedId;
							doPost(url,null,function(result){						
								if(result){
									var obj=eval('('+result+')');
									if(obj.error){
										error(obj.error);
										return;
									}
								}else{
									dataTable.refresh();
								}
							});
							
						}
						
					});
				}
			}
			function doEnterTask(){	
				if (isSingleSelected(dataTable)){			
					var selectedId=getSelected(dataTable,"id");
					var url = "<s:url value='/bpm/publish_taskList.jhtml'/>?procId="+selectedId;
					var paras = $("#submitForm").serialize();		
					
					redirectUrl(url);
				}
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
