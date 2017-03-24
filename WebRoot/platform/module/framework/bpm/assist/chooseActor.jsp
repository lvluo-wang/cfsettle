<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
<tiles:putAttribute name="tool">
	<x:button icon="icon-yes" click="selectBranch" text="choose"/>
	<x:button icon="icon-no" click="cancelSelectBranch" text="cancel"/>
</tiles:putAttribute>
<tiles:putAttribute name="query">
	<form id="chooseBranchQueryForm" style="margin:0px;padding:0px;">
			<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="branch"/><s:text name="name"/>:</label>
			<input style='margin-top:5px;border:#a4bed4 1px solid;'name="branch.brchName" id="branch.brchName"  ></input>
			<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="branch"/><s:text name="no"/>:</label>
			<input style='margin-top:5px;border:#a4bed4 1px solid;'name="branch.brchNo" id="branch.brchNo"  ></input>
			<a href="#" class="easyui-linkbutton" style="margin-bottom:2px;" iconCls="icon-search" plain="true" onClick="chooseBranchQuery()"><s:text name="query"/></a>
			
	</form>
</tiles:putAttribute>
<tiles:putAttribute name="data">
	<table id="branchData"></table>
</tiles:putAttribute>
<tiles:putAttribute name="end">
	<script type="text/javascript">
	var branchGrid;
	var divBrchId='<s:property value="brchIdDiv"/>';
	var divBrchName='<s:property value="brchNameDiv"/>';
	var divTreeCode = '<s:property value="treeCodeDiv" />';
	var divBrchNo='<s:property value="brchNoDiv"/>';
	var winId='<s:property value="winId"/>';
	$(function(){
		branchGrid=new DataPageComponent('branchData',{
			columns:[[  
			  			{field:'brchName',title:'<s:text name="branch"/><s:text name="name"/>',width:200,sortable:true,
							sorter:function(a,b){
								return (a>b?1:-1);
							}
						},
						{field:'miNo',title:'<s:text name="memberInfo.miNo"/>',width:80,sortable:true},				
						{field:'brchStatus',title:'<s:text name="status"/>',sortable:true,width:100,align:'center',
							formatter:function(value,rec){
								if (value=="1")
									return '<span style="color:green"><s:text name="status_true"/></span>';
								if (value=="2")
									return '<span style="color:red"><s:text name="status_false"/></span>';
							}
						}
					]],
			frozenColumns:[[
		                    {field:'ck',checkbox:true,width:20},
		                    {title:'<s:text name="branch"/><s:text name="no"/>',field:'brchNo',width:120,sortable:true}
		        		]],
		        		singleSelect:true,
			url:'<s:url value="/web_ui/choose_queryBranch.jhtml"/>',
			onDblClickRow:function(rowIndex, rowData){
				if(divBrchId.length>0&&divBrchId!='null'){
					$("#"+divBrchId).val(rowData.brchId);
				}
				if(divBrchName.length>0&&divBrchName!='null'){
					$("#"+divBrchName).val(rowData.brchName);
				}
				if(divBrchNo.length>0&&divBrchNo!='null'){
					$("#"+divBrchNo).val(rowData.brchNo);
				}
				if( divTreeCode ){
					$('#' + divTreeCode).val(rowData.treeCode);
				}
				$("#"+winId).window('close');				
			}
		});
		branchGrid.load();
	});
	
	
	function selectBranch(){
		if(isSingleSelected("branchData","id")){
		       if(divBrchId.length>0&&divBrchId!='null'){
					$("#"+divBrchId).val(getSelected("branchData","brchId"));
				}
				if(divBrchName.length>0&&divBrchName!='null'){
					$("#"+divBrchName).val(getSelected("branchData","brchName"));
				}
				if(divBrchNo.length>0&&divBrchNo!='null'){
					$("#"+divBrchNo).val(getSelected("branchData","brchNo"));
				}
				if( divTreeCode ){
					$('#' + divTreeCode).val(getSelected("branchData","treeCode"));
				}
				$("#"+winId).window('close');	
			
		}
	}
	
	function cancelSelectBranch(){
	    if(divBrchId.length>0&&divBrchId!='null'){
			$("#"+divBrchId).val(null);
		}
		if(divBrchName.length>0&&divBrchName!='null'){
			$("#"+divBrchName).val(null);
		}
		if(divBrchNo.length>0&&divBrchNo!='null'){
			$("#"+divBrchNo).val(null);
		}
		if( divTreeCode ){
			$('#' + divTreeCode).val(null);
		}
		$("#"+winId).window("close");
	}
	
	
	function chooseBranchQuery(){
		var parm=formToObject("chooseBranchQueryForm");
		branchGrid.queryByParam(parm);
	}
	
	</script>
</tiles:putAttribute>
</tiles:insertDefinition>