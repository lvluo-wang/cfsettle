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
			<span class="separator"></span>
			<x:button icon="icon-set" click="doSetInit" text="置为初始"/>			
			<span class="separator"></span>	
			<x:button icon="icon-set" click="doSetFinish" text="置为完成"/>		
	</tiles:putAttribute>	
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" url="/autotask/active_list.jhtml">
			<x:columns>
				<x:column field="taskId" checkbox="true" width="20" />
				<x:column title="taskName" field="name"  width="120" />
				<x:column title="startTime" field="startTime"  width="120" formatter="format2Time"/>
				<x:column title="endTime" field="endTime"  width="120" formatter="format2Time"/>
				<x:column title="runDate" field="runDate"  width="120" formatter="format2Date"/>
				<x:column title="taskType" field="taskType"  width="120" formatter="taskType_column"/>
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
			dataTable.load();//数据分页标签加载远程数据
		};
		function time_column(value,field,row,index){
			if(value){
				if(value.time){
					return DateFormat.format(new Date(value.time),'yyyy-MM-dd hh:mm:ss');
				}
			}
		}
		function date_column(value,field,row,index){
			if(value){
				if(value.time){
					return DateFormat.format(new Date(value.time),'yyyy-MM-dd');
				}
			}
		}
		function taskType_column(value,field,row,index){
			var a = "";
				if (value=="2"){
					var m = "toSubTask('"+row.taskId+"')";
					a = "| <a href='#' onclick="+m+">进入</a>";
				}
				return code.getValue('B031',value)+a;
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
		
		function toSubTask(taskId){
			var url = "<s:url value='/autotask/active_memberMain.jhtml'/>";	
			//var paras = $("#formElem").serialize();		
			var paras = {"id":taskId};
			redirectUrl(url,paras);
		}
		
		function doRun(){
			if(isSingleSelected(dataTable)){
				var taskId=getSelected(dataTable,"taskId");
				$.messager.confirm(global.alert, '确实要手动运行此任务吗', function(r){
					if(r){
						var url='<s:url value="/autotask/active_runTask.jhtml"/>';			
						doPost(url,
								{"id":taskId},
								function(result){
									printError(result);
									dataTable.refresh();
						});
					}
				});
				
				
			}		
			
		}
		
		function doStop(){
			if(isSingleSelected(dataTable)){
				var taskId=getSelected(dataTable,"taskId");
				$.messager.confirm(global.alert, '手动终止任务有可能造成数据异常，确实要手动终止此任务吗', function(r){
					if(r){
						var url='<s:url value="/autotask/active_stopTask.jhtml"/>';			
						doPost(url,
								{"id":taskId},
								function(result){
									printError(result);
									dataTable.refresh();
						});
					}
				});
				
				
			}		
			
		}
		
		function doSetInit(){
			if(isSingleSelected(dataTable)){
				var taskId=getSelected(dataTable,"taskId");
				var status=getSelected(dataTable,"status");
				if (status == "2" || status == "3"){
					info("不允许对运行中任务手动设置状态.");
					return;
				}
				
				$.messager.confirm(global.alert, '手动设置状态有可能造成状态不正常，确实要设置此任务为初始状态吗', function(r){
					if(r){
						var url='<s:url value="/autotask/active_setInitStatus.jhtml"/>';			
						doPost(url,
								{"id":taskId},
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
		
		function doSetFinish(){
			if(isSingleSelected(dataTable)){
				var taskId=getSelected(dataTable,"taskId");
				var status=getSelected(dataTable,"status");
				if (status == "2" || status == "3"){
					info("不允许对运行中任务手动设置状态.");
					return;
				}
				
				$.messager.confirm(global.alert, '手动设置状态有可能造成状态不正常，确实要设置此任务为完成状态吗', function(r){
					if(r){
						var url='<s:url value="/autotask/active_setFinishStatus.jhtml"/>';			
						doPost(url,
								{"id":taskId},
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



