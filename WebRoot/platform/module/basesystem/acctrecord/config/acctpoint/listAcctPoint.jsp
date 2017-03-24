<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="tool">
	 	<x:button icon="icon-add" click="doAdd" text="add"/>
	       <x:button icon="icon-remove" click="doRemove" text="del"/>	
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTableForDetails" autoload="false" url="/acctConfig/acctPointAction_queryData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="AcctPoint.prodNo" field="prodNo"  width="130"/>
				<x:column title="AcctPoint.prodName" field="prodNo_prodName"  width="120"/>
				<x:column title="AcctPoint.eventNo" field="eventNo"  width="120"/>
				<x:column title="AcctPoint.eventName" field="eventNo"  width="120" formatter="eventName_column"/>
			</x:columns>
		</x:datagrid>			
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"  style="width:450px;display:none;">
	</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">		
			var keys=["B023"];
			var code=new CodeUtil(keys);
			
			function initPage(){				
			   code.loadData();
			   dataTableForDetails.load();//数据分页标签加载远程数据
			}	
			
			function eventName_column(value){
				return "<span>"+code.getValue('B023',value)+"</span>";
			}
			//工具栏具体实现方法			
			
			function doAdd(){
				var url="<s:url value='/acctConfig/acctPointAction_toCreateAcctPoint.jhtml'/>?flag="+Math.random()*99999;
				requestAtWindow(url,"add_edit","<s:text name="AcctPoint.addAcctPoint"/>");		
			}
			
			function doRemove(){
				if(isSingleSelected(dataTableForDetails)){
					var cfmsg = "<s:text name="AcctPoint.deleteConfirm"/>";
					$.messager.confirm(global.alert,cfmsg, function(ok){
						if(ok){
							var ids=getSelected(dataTableForDetails,"id");
							var url = "<s:url value='/acctConfig/acctPointAction_deleteAcctPoint.jhtml'/>?id="+ids;
							doPost(url,null,function(){						
								
								if(dataTableForDetails){
									dataTableForDetails.refresh();
								}
							});
						}
					});
				}
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
