<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script language="javascript" src='<s:url value="/platform/static/plugins/uploadfy/jqueryuploadify.js"/>'></script>
<script language="javascript" src='<s:url value="/platform/static/plugins/uploadfy/swfobject.js"/>'></script>
<link rel="stylesheet" type="text/css" href='<s:url value="/platform/static/plugins/uploadfy/css/uploadify.css"/>'/> 
<script type="text/javascript">
var excelCode='';//'${excelCode}';
var excelGrid;
var errMsg="";
var fileUpload;
var url='<s:url value="/web_ui/excelUpload.jhtml"/>;jsessionid=<%=session.getId()%>';
function initPage(){
	if(excelCode==''){
		$("#fileCodeTr").show();
	}
	var lastIndex;
	fileUpload=new FileUpload('excelFile',{
		script:url,
		fileExt:'*.xls;*.xlsx',
		fileDesc:'支持格式:xls/xlsx',
		onComplete:function(event, queueID, fileObj, response, data){
			complateResponse(response);
		},
		onError:function(event, queueID, fileObj){
			info(fileObj.name+"上传失败");
		}
	});
}
function doUpload(){
	var code=$("#excelCode").val();
	if(code==''){
		info('文件编码不能为空');
	}else{
		if(fileUpload.getFileNum()==0){
			info('请选择要上传的文件');
		}else{
			fileUpload.upload({
				'excelCode':code
			});
		}
	}
}
</script>
<style type="text/css">
        #upload_box .uploadifyQueueItem {
  background-color: #F5F5F5;
  border: 2px solid #E5E5E5;
  font: 11px Verdana, Geneva, sans-serif;
  margin-top: 0px;
  padding: 5px;
  width: 250px;
  float:right;
}
#upload_box .uploadifyError {
  background-color: #FDE5DD !important;
  border: 2px solid #FBCBBC !important;
}
#upload_box .uploadifyQueueItem .cancel {
  float: right;
}
#upload_box .uploadifyQueue .completed {
  background-color: #E5E5E5;
  display:none;
}
#upload_box .uploadifyQueue{
  float:right;
}
#upload_box .uploadifyProgress {
  background-color: #E5E5E5;
  margin-top: 2px;
  width: 100%;
}
#upload_box .uploadifyProgressBar {
  background-color: #0099FF;
  height: 3px;
  width: 1px;
}
</style>
<div style="height:210px;">
<div class="easyui-layout" fit="true" border="false">
	<div region="north" style="padding-left:2px;padding-top:2px;background:#efefef;height:45px;" border="false" split="false">
		
		<table>
			<tr id="fileCodeTr" style="display: none;">
				<td >文件编号:<input type="text" id="excelCode" name="excelCode" value="${excelCode}"/>
				     <a href="#" class="easyui-linkbutton" onClick="doUpload()">上传</a>
					<a href="#" class="easyui-linkbutton" onClick="templateExport()">导出模板</a>
					<span id="error" style="display: none"><font color="red"><a href="#" onClick="showError()">解析出错，点击查看详细信息..</a></font></span>
						
				</td>
				<td>
					<div id="upload_box" style="height:35px;">
						<input type="file" name="excelFile" id="excelFile"/>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div region="center"  style="overflow:hidden;height:400px;" border="true">
		<table id="excelData"></table>
	</div>
	<div id="errorV" class="easyui-window" closed="true"  style="width:400px;height:200px;padding:5px;background: #fafafa;" title="解析错误"></div>
</div>
</div>
<script type="text/javascript">
	function buildColumns(head){
		var columns=[];
		var c=[];
		var index=0;
		for(var column in head){
			var t=head[column];
			c[index]={title:t,field:column,width:100,editor:"text",formatter:function(value,row){
				if(typeof(value)!='string'&&value!=null ){
					if(value.time){
						//日期格式
						return DateFormat.format(new Date(value.time));
					}
				}
				return value;
			}};
			index++;
		}
		columns[0]=c;
		return columns;
	}
	function buildFrozenColumns(){
		var columns=[];
		var c=[];
		c[0]={title:'操作',field:'operation',width:100,align:'center',formatter:function(value,row,index){
			if (row.editing){
				var sStr = '<a href="#" onclick="saveRow('+index+')">Save</a> ';
				var cStr = '<a href="#" onclick="cancelRow('+index+')">Cancel</a>';
				return sStr+'&nbsp;&nbsp;'+cStr;
			} else {
				var e = '<a href="#" onclick="editRow('+index+')">Edit</a> ';
				var d = '<a href="#" onclick="deleteRow('+index+')">Delete</a>';
				return e+'&nbsp;&nbsp;'+d;
			}
		}}
		columns[0]=c;
		return columns;
	}
	function complateError(errorList){
		if(errorList.length>0){
			for(var i=0;i<errorList.length;i++){
				errMsg+="<li>"+errorList[i]+"</li>";
			}
			$("#error").show();
		}
	}
	function dataImport(){
		doPost('<s:url value="/web_ui/excel_dataImport.jhtml"/>',
			null,
			function(result){
				if(result){
					var obj=str2obj(result);
					if(obj.error){
						error(obj.error);
					}
				}
			});
	}
	function dataExport(){
		window.location.href='<s:url value="/web_ui/excel_dataExport.jhtml"/>';
	}
	function templateExport(){
		window.location.href='<s:url value="/web_ui/excel_templateExport.jhtml"/>?excelCode='+$("#excelCode").val();
	}
	function buildDatagrid(head){
			var columns=buildColumns(head);
			var frozenColumns=buildFrozenColumns();
			var lastIndex;
			new DataPageComponent("excelData",{
				toolbar:[{
							text:'导入到服务器',
							iconCls:'icon-save',
							handler:function(){
								dataImport();
							}
						},'-',{
							text:'导出为Excel',
							iconCls:'icon-print',
							handler:function(){
								dataExport();
							}
						}],
				columns:columns,
				frozenColumns:frozenColumns,
				url:'<s:url value="/web_ui/excel_queryPage.jhtml"/>',
				rownumbers:true,
				singleSelect:true,
				onBeforeEdit:function(index,row){
							row.editing = true;
							updataAction();
						},
				onAfterEdit:function(index,row,changes){
					var cg="";
					for(var chg in changes){
						cg+=chg+":"+changes[chg]+",";
					}
					doPost('<s:url value="/web_ui/excel_updateRow.jhtml"/>',
						{'index':index,'change':cg},
						function(result){
							if(result){
								var o=str2obj(result);
								if(o.error){
									error(o.error);
									return false;
								}
							}
							row.editing = false;
							updataAction();
					});
					
				},
				onCancelEdit:function(index,row){
					row.editing = false;
					updataAction();
				},
				onDblClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#excelData').datagrid('endEdit', lastIndex);
						$('#excelData').datagrid('beginEdit', rowIndex);
					}
					
					lastIndex = rowIndex;
				}
			}).load();
	}
	function complateResponse(response){
		errMsg="";
			$("#error").hide();
			if(response){
				var obj=str2obj(response);
				if(obj){
					if(obj.error){
						error(obj.error);
						return false;
					}
					if(obj.errorList){
						complateError(obj.errorList);
					}
					
					if(obj.head){
						buildDatagrid(obj.head);
					}
					
				}
			}
	}
	function showError(){
		$("#errorV").window({
			resizable:false,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			closable:true,
			content:errMsg
		}).window("open");
	}
	function editRow(index){
		$('#excelData').datagrid('beginEdit', index);
	}
	function saveRow(index){
		
		$("#excelData").datagrid('endEdit',index);
	}
	function cancelRow(index){
		$("#excelData").datagrid('cancelEdit',index);
	}
	function deleteRow(index){
		$.messager.confirm('Confirm','Are you sure?',function(r){
			if(r){
				doPost('<s:url value="/web_ui/excel_deleteRow.jhtml"/>',
					{'index':index},
					function(result){
						if(result){
							var o=str2obj(result);
							if(o.error){
								error(o.error);
								return;
							}
						}
						$("#excelData").datagrid('reload');
				});
			}
		})
	}
	function updataAction(){
		var rowCount=$("#excelData").datagrid('getRows').length;
		for(var i=0;i<rowCount;i++){
			$("#excelData").datagrid('updateRow',{
				index:i,
				row:{operation:''}
			});
		}
	}
</script>