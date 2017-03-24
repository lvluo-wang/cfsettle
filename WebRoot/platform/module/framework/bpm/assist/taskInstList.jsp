<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">	
	<!-- 由此开始页面布局 -->
	<tiles:putAttribute name="tool">
		<x:button icon="icon-close" click="doCancelHolder" text="取消领用人"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="data">
		<form name="qform" id="qform">
			<s:hidden name="id"></s:hidden>
		</form>
		<x:datagrid id="taskDataTable" autoload="false" form="qform" url="/bpm/activity_queryTaskInstData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="id" field="id"   width="40" />
				<x:column title="名称"  field="name"   width="100" />
				<x:column title="描述"  field="desc"   width="120" />
				<x:column title="领用人"   field="holdUserName"  width="100" />
				<x:column title="开始时间"   field="start"  width="120" formatter="format2Time"/>
				<x:column title="结束时间"   field="end"  width="120" formatter="format2Time"/>
				<x:column title="状态"   field="suspended"  width="100" formatter="suspended_column"/>
			</x:columns>
		</x:datagrid>				
	</tiles:putAttribute>
			
	<tiles:putAttribute name="window">
		<!-- 弹出窗口定义开始 -->
		
		<!-- 弹出窗口定义结束 -->
	</tiles:putAttribute>

<tiles:putAttribute name="end">
	<script type="text/javascript">
	function initPage(){
		taskDataTable.load();
		
	};
		function suspended_column(value,field,rowData,index){
			var ret = '';
			var endDate = rowData.end;
			if (endDate)
				return '<font color=black >结束</font>';
			else if (value)
				ret = '<font color=blue>暂停</font>';
			else
				ret = '<font color=green >待处理</font>';								
				
			if (value) {
				return ret + ' <a href="#" onclick="doTaskResume('+rowData.id+')"><font color="red">恢复</font></a>';
			} else{
				return ret + ' <a href="#" onclick="doTaskSuspend('+rowData.id+')"><font color="red">停止</font></a>';
			}
		}
		//工具栏方法实现区域
		function doTaskQuery(){
			taskDataTable.refresh();
		}
		
		function doTaskSuspend(tid){
			var url='<s:url value="/bpm/activity_suspendTask.jhtml"/>';			
			doPost(url,
					{"id":tid},
					function(result){
						if(result){
							var obj=str2obj(result);
							if(obj.error){
								error(obj.error);
							}
						}
						doTaskQuery();
			});
		}

		function doTaskResume(tid){
			var url='<s:url value="/bpm/activity_resumeTask.jhtml"/>';			
			doPost(url,
					{"id":tid},
					function(result){
						if(result){
							var obj=str2obj(result);
							if(obj.error){
								error(obj.error);
							}
						}
						doTaskQuery();
			});
		}
		
		function doCancelHolder(){
			if(isSingleSelected(taskDataTable)){
				var selectedId=getSelected(taskDataTable,"id");
				var url='<s:url value="/bpm/activity_cancelHolder.jhtml"/>';			
				doPost(url,
						{"id":selectedId},
						function(result){
							if(result){
								var obj=str2obj(result);
								if(obj.error){
									error(obj.error);
								}
							}
						doTaskQuery();
				});
				
			}	
		}
		
		function toChooseActor(){
			if(isSingleSelected(taskDataTable)){
				var selectedId=getSelected(taskDataTable,"id");
				var url="<s:url value='/bpm/activity_chooseActor.jhtml'/>?id="+selectedId+"&flag="+Math.random()*99999;
				requestAtWindow(url,"task","选择人员");	
			}
		}
		
		function doSetHolder(taskId, actorId){
			if(isSingleSelected("taskQueryForm","id")){
				var selectedId=getSelected("taskQueryForm","id");
				var url='<s:url value="/bpm/activity_setHolder.jhtml"/>';			
				doPost(url,
						{"id":selectedId},
						function(result){
							if(result){
								var obj=str2obj(result);
								if(obj.error){
									error(obj.error);
								}
							}
						doTaskQuery();
				});
				
			}
		}
		
		function doViewActor(){//查看执行者
			
		}	
	
	</script>
</tiles:putAttribute>
</tiles:insertDefinition>

