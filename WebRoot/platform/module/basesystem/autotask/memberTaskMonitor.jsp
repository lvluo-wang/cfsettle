<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">	
	<tiles:putAttribute name="tool">
		<x:button icon="icon-open" click="doRun" text="运行"/>
		<span class="separator"></span>
		<x:button icon="icon-close" click="doStop" text="终止"/>
		<span class="separator"></span>
		<x:button icon="icon-back" click="toBack" text="back"/>
	</tiles:putAttribute>	
	<tiles:putAttribute name="data">
		<form id="qform">
			<s:hidden id="id" name="id"></s:hidden>	
		</form>
		<x:datagrid id="dataTable" autoload="false" form="qform" url="/autotask/active_memberList.jhtml">
			<x:columns>
				<x:column field="taskId" checkbox="true" width="20" />
				<x:column title="taskName" field="name"  width="120" />
				<x:column title="memberName" field="memberName"  width="120" />
				<x:column title="startTime" field="startTime"  width="120" formatter="format2Time"/>
				<x:column title="endTime" field="endTime"  width="120" formatter="format2Time"/>
				<x:column title="runDate" field="runDate"  width="120" formatter="format2Date"/>
				<x:column title="status" field="status"  width="120" formatter="status_column"/>
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"  style="width:750px;height:420px;padding:5px;background: #fafafa;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		var keys=['B031','B032','B033'];
		var code=new CodeUtil(keys);
		function initPage(){
			
			code.loadData();
			dataTable.load();
		}
		
		function status_column(value,field,row,index){
			var info = code.getValue('B033',value);
			if ('1'==value)
				return "<font color='black'>"+info+"</font>";
			if ('2'==value)
				return "<font color='blue'>"+info+"</font>";	          				
			if ('6'==value)
				return "<font color='green'>"+info+"</font>";
			
			return "<font color='red'>"+info+"</font>";
		}
		
		function toBack(){
			var url = "<s:url value='/autotask/active_main.jhtml'/>";	
			
			redirectUrl(url,null);
			
		}
		var taskId = '<s:property value="id"/>';
		function doRun(){
			if(isSingleSelected(dataTable)){			
				var memberNo=getSelected(dataTable,"memberNo");
				$.messager.confirm(global.alert, '确实要手动运行此任务吗', function(r){
					if(r){
						var url='<s:url value="/autotask/active_runSubTask.jhtml"/>';			
						doPost(url,
								{"id":taskId,"memberNo":memberNo},
								function(result){
									if(result){
										var obj=str2obj(result);
										if(obj.error){
											error(obj.error);
										}
									}
									dataGrid.reLoad();
						});
					}
				});
				
			}		
			
		}
		
		
		function doStop(){
			if(isSingleSelected(dataTable)){
				var memberNo=getSelected(dataTable,"memberNo");
				$.messager.confirm(global.alert, '手动终止任务有可能造成数据异常，确实要手动终止此任务吗', function(r){
					if(r){
						var url='<s:url value="/autotask/active_stopSubTask.jhtml"/>';			
						doPost(url,
								{"id":taskId,"memberNo":memberNo},
								function(result){
									if(result){
										var obj=str2obj(result);
										if(obj.error){
											error(obj.error);
										}
									}
									dataTable.refresh();
						});
					}
				});
				
				
			}		
			
		}
		
		setInterval("pageRefresh()",5000);
		
		function pageRefresh(){
			dataTable.refresh();
		}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
	