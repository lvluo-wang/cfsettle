<%@ page import="com.upg.cfsettle.util.CfsConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_BLANK">
	<%--<tiles:putAttribute name="tool">
		<x:button icon="icon-yes" click="doCustAssign" text="选择分配"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/cust/cfscust_list.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="uid" />
				<x:column title="客户姓名" field="realName" align="center" width="100"/>
				<x:column title="性别" field="sex" align="center" width="40" formatter="formatSex"/>
				<x:column title="客户手机" field="mobile" align="left" width="90"/>
				<x:column title="身份证号" field="idCard" align="center" width="100" />
				<x:column title="添加时间" field="ctime" align="center" width="100"/>
				<x:column title="验证" field="isValid" align="center" width="80" formatter="formatYesNo"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>--%>

	<tiles:putAttribute name="body">
			<tale id="dataAssignTable"></tale>
			</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		var keysAssign=["<%=CfsConstant.CFS_COMM_YSE_NO%>","<%=CfsConstant.CFS_COMM_SEX%>"];
		var codeAssign=new XhhCodeUtil(keysAssign);
		codeAssign.loadData();

		function formatSex(value){
			return codeAssign.getValue("<%=CfsConstant.CFS_COMM_SEX%>",value);
		}

		function formatYesNo(value){
			return codeAssign.getValue("<%=CfsConstant.CFS_COMM_YSE_NO%>",value);
		}

		$(function () {
		    buildDatagrid();
		})

		function buildColumns(){
			var columns=[];
			var c=[];
			var index=0;
			for(var i=0;i<1;i++){
				c[index]={title:"姓名",field:"realName",width:100,editor:"text",formatter:function(value,row){
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

		function buildDatagrid(){
			var columns=buildColumns();
			var lastIndex;
			new DataPageComponent("dataAssignTable",{
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
				//frozenColumns:frozenColumns,
				url:'<s:url value="/assign/assignManage_custList.jhtml"/>?buserId=${buserId}',
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



	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
