<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
var queryGrid;

$(function(){	
	queryGrid=new DataPageComponent('contryData',{
		columns:[[  
		  			{field:'regionName',title:'<s:text name="name"/>',width:200,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					}
					
				]],
		frozenColumns:[[
	                    {field:'ck',checkbox:true,width:20},
	                    {title:'<s:text name="no"/>',field:'codeNo',width:120,sortable:true}
	        		]],
	        		singleSelect:true,
		url:'<s:url value="/demo/page_queryChooseCountryList.jhtml"/>',
		onDblClickRow:function(rowIndex, rowData){			
			$("#"+$("#noDiv").val()).val(rowData.codeNo);
			$("#"+$("#nameDiv").val()).val(rowData.regionName);
			$("#"+$("#winId").val()).window('close');				
		}
	});
	queryGrid.load();
});
function chooseCountryQuery(){
	var parm=formToObject("chooseCountryQueryForm");
	queryGrid.queryByParam(parm);
}
function chooseCountryClose(){
	$("#"+$("#winId").val()).window('close');	
}
</script>
<div class="easyui-layout" fit="true" border="false">	
			<div region="center"  style="overflow:hidden;" border="false">
				<div class="easyui-layout" fit="true" border="false">
					<div region="north" style="padding-left:2px;padding-top:2px;background:#efefef;height:34px;"border="false" split="false">
							<form id="chooseCountryQueryForm" style="margin:0px;padding:0px;">
								<s:hidden name="idDiv"></s:hidden>
							 	<s:hidden name="noDiv"></s:hidden>
							 	<s:hidden name="nameDiv"></s:hidden>
							 	<s:hidden name="winId"></s:hidden>
									<!--<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="contry"/><s:text name="name"/>:</label>
									<input style='margin-top:5px;border:#a4bed4 1px solid;'name="name" id="name"  ></input>
									<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="contry"/><s:text name="no"/>:</label>
									<input style='margin-top:5px;border:#a4bed4 1px solid;'name="serialNo" id="serialNo"  ></input>
									<a href="#" class="easyui-linkbutton" style="margin-bottom:2px;" iconCls="icon-search" plain="true" onClick="chooseCountryQuery()"><s:text name="query"/></a>
									&nbsp;&nbsp;
									--><a href="#" class="easyui-linkbutton" style="margin-bottom:2px;" iconCls="icon-cancel" plain="true" onClick="chooseCountryClose()"><s:text name="close"/></a>
							</form>
					</div>
					<div region="center"  style="overflow:hidden;height:400px;" border="false">
						<table id="contryData"></table>
					</div>
				</div>
				
			</div>
		</div>