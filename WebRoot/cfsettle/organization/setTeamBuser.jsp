<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
    <tiles:putAttribute name="tool">
        <x:button icon="icon-yes" text="设置" click="chooseBuser" />
        <x:button icon="icon-no" text="取消" click="cancelBuser" />
    </tiles:putAttribute>
     <tiles:putAttribute name="query">
        <form id="chooseBuserForm">
            <input type="hidden" name="searchBean.posCode" value="${searchBean.posCode}">
            <input type="hidden" name="searchBean.teamId" value="${searchBean.teamId}" id="chooseId">
        </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="data">
        <x:datagrid id="chooseBuseraTable" singleSelect="true" url="/orgTeam/orgTeamManage_buserList.jhtml" autoload="true" form="chooseBuserForm" pagebar="false">
            <x:columns>
                <x:column field="userId" checkbox="true"/>
                <x:column field="userNo" title="手机号码" width="100"/>
                <x:column field="userName" title="用户名" width="200"/>
            </x:columns>
        </x:datagrid>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
        var data = null;
        function chooseBuser(){
     	    var url = '<s:url value="/orgTeam/orgTeamManage_setBuser.jhtml"/>';
		   	    var buserId;
		   	    var status;
		   	    var num = chooseBuseraTable.getSelectedRowNum();
		   	    if(num ==1){
		   	    	status='1';
		   	    	buserId = chooseBuseraTable.getSelectedField('userId');
		   	    }else{
		   	    	if(data ==null){
		   	    		warning('请选择一个用户');
		   	    		return;
		   	    	}
		   	    	status='0';
		   	    	buserId = null;
		   	    }
				var param={'newBuserId':buserId,'searchBean.teamId':$('#chooseId').val(),'searchBean.havBuser':status,'oldBuserId':data};
				doPost(url,param ,function(result) {
					if (!printError(result)) {
						closeWindow("project_set_team_buser");
						dataTable.refresh();
					}
				});
        }
           
           function cancelBuser(){
        	   closeWindow("project_set_team_buser");
           }
           
           chooseBuseraTable.onLoadSuccess=function(datas){
           var chooseId = $('#chooseId').val();
   		   for (var i=0;i<datas.length;i++) {
				if (datas[i].teamId ==chooseId ) {
					data = datas[i].userId;
					var ckObj = $("#cb_"+this.id+"_"+i);
					ckObj[0].checked=!ckObj[0].checked;
					$("#tr_"+this.id+"_"+i).addClass("datagrid-row-selected");
					this.selResults[i] = "true";
				}
			}
   		};
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>