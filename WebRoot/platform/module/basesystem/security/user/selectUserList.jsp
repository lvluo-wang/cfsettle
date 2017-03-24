<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_QUERY_DATA">
    
	
	<tiles:putAttribute name="query">
		<form id="selectBrchUserForm" class="query_form">
			<table>
				<tr>
				    <s:hidden name="branch.brchId" id="branch_brchId" ></s:hidden>
                    <td class="title">用户名:</td>
					<td><input name="user.userName" id="user_userName" ></input></td>
					
					<td><x:button iconCls="icon-search" text="query" click="doSelectUserQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="selectuser_dataTable" url="/security/user_queryBrchUsers.jhtml" autoload="true" form="selectBrchUserForm">
			<x:columns>
				<x:column title="" checkbox="true" field="userId" />
				<x:column title="用户号"  field="userNo" align="left" width="100"/>
				<x:column title="用户名"  field="userName" align="left" width="100"/>
				
			</x:columns>
		</x:datagrid>
		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			enterKeyPressInit([{'id':'user_userName'}],"doSelectUserQuery()");
			function doSelectUserQuery(){
				if(!$("#brchTree").tree("getSelected")) {
					$("#branch_brchId").val($("#brchTree").tree("getRoot").id);
				}
				selectuser_dataTable.load();
			}
			
			selectuser_dataTable.onDblClickRow=function(rowIndex,rowData){
			       if(chooseContext_user.callback && $.isFunction(chooseContext_user.callback))
			      		chooseContext_user.callback(rowData);
					chooseContext_user.win.window("close");
					var target = chooseContext_user.target;
					if (target) {
						$(target).focus();
					}
					return false;
			   };
   
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>