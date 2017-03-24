<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">

<tiles:putAttribute name="tool">
	<x:button icon="icon-add" click="doAdd" text="add"/>
	<x:button icon="icon-copy" click="doCopy" text="复制"/>
	<x:button icon="icon-edit" click="doEdit" text="edit"/>
	<x:button icon="icon-up" click="doUpMove" text="上移"/>
	<x:button icon="icon-down" click="doDownMove" text="下移"/>
	<x:button icon="icon-remove" click="doRemove" text="del"/>
					
</tiles:putAttribute>
<tiles:putAttribute name="query">
		<form id="queryForm">
		<s:hidden id="colNames" name="colNames"></s:hidden>
		<table>
			<tr>
				<td>
				<s:select id="groupNo" name="groupNo" list="groupMap" listKey="key" listValue="value" onchange="doChangeGroup(this);" ></s:select>组<s:hidden name="tranId"></s:hidden>
				</td>
			</tr>
		</table>
		</form>
</tiles:putAttribute>
<tiles:putAttribute name="data">

	<table id="dataTable1" style="height:300px"></table>

</tiles:putAttribute>
<tiles:putAttribute name="window">		
	
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
var query;
var dataGrid;
var config
var rowIndex;
//function initPage(){alert("asa");
	$(function(){
	// 获取初始化组件所需的数据
	var frozenColumns=[[
	                    {field:'ck',checkbox:true,width:20}
	        		]];
	var columns=[[ ]]; 
	
	var colNameArr = $("#colNames").val().split(",");
	
	var widVal = 720/colNameArr.length;
	for(i=1;i<=colNameArr.length;i++){
		var col = {field:'col'+i, title:colNameArr[i-1], width:widVal, align:'center'};
		columns[0].push(col);
	}	
	config = {
		columns:columns,
		fit:false,
		pagination:false,//是否支持分页
		frozenColumns:frozenColumns,
		singleSelect:true,
		url:'<s:url value="/acctConfig/tranItemAction_tranItemList.jhtml"/>',
		queryParams:{"tranId":'<s:property value="tranId"/>', "groupNo":'<s:property value="groupNo"/>'},
		onSelect: function(rowIndex2, rowData){rowIndex = rowIndex2;}

	};
	dataGrid = new DataPageComponent("dataTable1",config);
	//dataGrid=new initDataComponent("dataTable1",'<s:url value="/acctConfig/tranItemAction_tranItemList.jhtml"/>?tranId='+'<s:property value="tranId"/>&groupNo='+'<s:property value="groupNo"/>',frozenColumns,columns);
	dataGrid.load();
	
	});
	//工具栏具体实现方法
	
	function doSearch(){
		var parm=formToObject("queryForm");
		dataGrid.queryByParam(parm);
	}
	
	function doChangeGroup(t){		
		var tranId=$("#tranId").val();
		var groupNo=$("#groupNo").val();
		var url='<s:url value="/acctConfig/tranItemAction_toTranItemList.jhtml"/>?tranId='+tranId+'&groupNo='+groupNo+'&flag='+Math.random()*99999;
		//var title = $("#tranDetail").titile;
		//requestAtDom("tranDetail",url,null);
		doPost(url,null,function(result){
			if(result){
				if(result.indexOf("{")!=0){
					$("#tranDetail").html(result);
				}else{
					var obj=eval('('+result+')');
					if(obj.error){
							error(obj.error);
					}
					setNull(obj);
				}
				setNull(result);
			}
		});
	}
	function doAdd(){
		var tranId=$("#tranId").val();
		var groupNo=$("#groupNo").val();
		var url="<s:url value='/acctConfig/tranItemAction_toCreateTranItem.jhtml'/>?flag="+Math.random()*99999;
		var param = {'tranId':tranId,'groupNo':groupNo};
		requestAtWindow(url,"add_edit","<s:text name="TranItem.addTranItem"/>",param);
	}
	
	function doCopy(){
		var tranId = $("#tranId").val();
		var groupNo = $("#groupNo").val();
		
		if(!isNaN(rowIndex)){
			var row = rowIndex;row++;//rowIndex从0开始
			var url = "<s:url value='/acctConfig/tranItemAction_toCopyTranItem.jhtml'/>";
			var param = {'id':row,'tranId':tranId,'groupNo':groupNo};
			requestAtWindow(url,"add_edit","<s:text name="TranItem.addTranItem"/>",param);
			
		}
	}
	
	function doEdit(){
		var tranId = $("#tranId").val();
		var groupNo = $("#groupNo").val();
		
		if(!isNaN(rowIndex)){
			var row = rowIndex;row++;//rowIndex从0开始
			var url = "<s:url value='/acctConfig/tranItemAction_toEditTranItem.jhtml'/>"
			var param = {'id':row,'tranId':tranId,'groupNo':groupNo};
			requestAtWindow(url,"add_edit","<s:text name="TranItem.editTranItem"/>",param);
			
		}
	}
	
	function doRemove(){
		var tranId = $("#tranId").val();
		var groupNo = $("#groupNo").val();
		
		if(!isNaN(rowIndex)){
			var cfmsg = "<s:text name="AcctPoint.deleteConfirm"/>";
			$.messager.confirm(global.alert,cfmsg, function(ok){
				if(ok){
					var row = rowIndex;row++;//rowIndex从0开始
					var url = "<s:url value='/acctConfig/tranItemAction_deleteTranItem.jhtml'/>?id="+row;
						url=url+"&tranId="+tranId+"&groupNo="+groupNo;
					doPost(url,null,function(rs){dataGrid.load();});
				}
			});
		}
	}
	function doUpMove(){
		var tranId = $("#tranId").val();
		var groupNo = $("#groupNo").val();
		
		if(!isNaN(rowIndex)){
			var cfmsg = "确实要上移一行吗？";
			$.messager.confirm(global.alert,cfmsg, function(ok){
				if(ok){
					var row = rowIndex;row++;//rowIndex从0开始
					var url = "<s:url value='/acctConfig/tranItemAction_upMoveRow.jhtml'/>?id="+row;
						url=url+"&tranId="+tranId+"&groupNo="+groupNo;
					doPost(url,null,function(rs){dataGrid.load();});
				}
			});
		}
	}
	function doDownMove(){
		var tranId = $("#tranId").val();
		var groupNo = $("#groupNo").val();
		
		if(!isNaN(rowIndex)){
			var cfmsg = "确实要下移一行吗？";
			$.messager.confirm(global.alert,cfmsg, function(ok){
				if(ok){
					var row = rowIndex;row++;//rowIndex从0开始
					var url = "<s:url value='/acctConfig/tranItemAction_downMoveRow.jhtml'/>?id="+row;
						url=url+"&tranId="+tranId+"&groupNo="+groupNo;
					doPost(url,null,function(rs){dataGrid.load();});
				}
			});
		}
	}	
	
	
</script>

</tiles:putAttribute>
</tiles:insertDefinition>
