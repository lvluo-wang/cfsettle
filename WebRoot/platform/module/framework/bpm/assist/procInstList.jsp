<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	
	<!-- 由此开始页面布局 -->
	<tiles:putAttribute name="tool">
		<x:button icon="icon-add" click="doTask" text="查看任务"/>
		<x:button icon="icon-edit" click="doVariable" text="查看上下文"/>
		<x:button icon="icon-submit" click="doActivity" text="流转过程"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm">
			<table>
				<tr>
					<td>
						流程:
					</td><td class="td-input">
							<s:select name="bpmSearchBean.procName" list="%{procDefList}" listKey="procName" listValue="procDesc+procName" />
					</td><td>	
						查询类型:
					</td><td>
							<s:select list="#{'1':'实体ID','2':'实体编号'}" onchange="changeType();" id="queryType" name="bpmSearchBean.type" />
						
					</td><td>
						输入值:
					</td><td><s:textfield name="bpmSearchBean.value" id="bpmSearchBean_value" onkeydown="replaceNumKeyUp(this);" onkeyup="replaceNumKeyUp(this);" ></s:textfield>
					</td><td valign="middle">	
						<x:button icon="icon-search" click="doQuery" text="query"/>
					</td>
				</tr>							
			</table>						
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" form="queryForm" url="/bpm/activity_queryData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="id" field="id"   width="50" />
				<x:column title="开始时间"  field="start"   width="180" formatter="format2Time"/>
				<x:column title="结束时间"  field="end"   width="180" formatter="format2Time"/>
				<x:column title="状态"   field="suspended"  width="180" formatter="suspended_column"/>
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>

			
	<tiles:putAttribute name="window">
		<!-- 弹出窗口定义开始 -->
		<div id="variable"   style="width:800px;display:none;"></div>
		<div id="flow"  style="width:720px;display:none;"></div>
		<!-- 弹出窗口定义结束 -->
	</tiles:putAttribute>

<tiles:putAttribute name="end">
	<script type="text/javascript">
	function initPage(){
		dataTable.load();		
	}
	
	function suspended_column(value,field,rowData,index){
		var ret = '';
		var endDate = rowData.end;
		if (endDate && !value)
			return  '<font color=black >结束</font>';
		else if (endDate && value)
			ret = '<font color=blue >暂停</font>';		
		else
			ret = '<font color=green >流转中</font>';		
		
		if (value) {
			return ret + ' <a href="#" onclick="doResume('+rowData.id+')"><font color="red">恢复</font></a>';
		} else{
			return ret + ' <a href="#" onclick="doSuspend('+rowData.id+')"><font color="red">停止</font></a>';
		}
	}
	
	function doSuspend(pid){
		var url='<s:url value="/bpm/activity_suspendFlow.jhtml"/>';			
		doPost(url,
				{"id":pid},
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
	
	function doResume(pid){
		var url='<s:url value="/bpm/activity_resumeFlow.jhtml"/>';			
		doPost(url,
				{"id":pid},
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
	
	//工具栏方法实现区域
	function doQuery(){
		dataTable.load();		
	}
	
	function doTask2(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"id");
			var url="<s:url value='/bpm/activity_toTaskInst.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;
			//var paras = $("#editForm").serialize();
			//initPageContentWithPara("pageContent",null,url);
			redirectUrl(url);
			//requestToWorkapace(url,paras,"业务处理");
			
		}		
	}
	
	function doTask(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"id");
			var url="<s:url value='/bpm/activity_toTaskInst.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;
			requestAtWindow(url,"variable","业务处理");
			
		}		
	}
	
	function doVariable(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"id");
			var url="<s:url value='/bpm/activity_toVarInst.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;		
			//initPageContentWithPara("pageContent",null,url);			
			requestAtWindow(url,"variable","流程上下文");			
		}	
	}
	
	function doAdd(){
		var url="<s:url value='/bpm/activity_toAddVar.jhtml'/>?flag="+Math.random()*99999;
		url += "&id="+$('#q_acctItem_pointName').val();
		url += "&acctItem.pointId="+$('#q_acctItem_pointId').val();
		requestAtWindow(url,"add_edit","<s:text name="AcctItem.addAcctItem"/>");
	}
	
	function doActivity(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"id");
			var url="<s:url value='/bpm/activity_queryFlow.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;		
			
			requestAtWindow(url,"flow","流程位置");	
		}
	}
	function changeType(){
		var obj = document.getElementById('bpmSearchBean_value');		
		replaceNumKeyUp(obj);
	}
	
	function replaceNumKeyUp(obj) { 
		var qt = document.getElementById('queryType').value;		
		if (qt=='1')
			obj.value = obj.value.replace(/[^0-9]/g,'');
		
	}  

	
	</script>
</tiles:putAttribute>
</tiles:insertDefinition>
