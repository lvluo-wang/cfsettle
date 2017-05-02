<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
    <tiles:putAttribute name="tool">
        <x:button icon="icon-yes" text="设置" click="choosePartBuser" />
        <x:button icon="icon-no" text="取消" click="cancelPartBuser" />
    </tiles:putAttribute>
     <tiles:putAttribute name="query">
        <form id="chooseBuserPartForm">
            <input type="hidden" name="searchBean.posCode" value="${searchBean.posCode}">
            <input type="hidden" name="searchBean.teamId" value="${searchBean.teamId}" id="chooseId">
        </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="data">
        <x:datagrid id="chooseBuserPartTable" url="/orgTeam/orgTeamManage_partBuser.jhtml" autoload="true" form="chooseBuserPartForm" pagebar="false">
            <x:columns>
                <x:column field="userId" checkbox="true"/>
                <x:column field="userNo" title="手机号码" width="100"/>
                <x:column field="userName" title="用户名" width="200"/>
            </x:columns>
        </x:datagrid>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
        var data = new Array();
        function choosePartBuser(){
     	    var url = '<s:url value="/orgTeam/orgTeamManage_setPartBuser.jhtml"/>';
	   	    var buserId;
	   	    var status;
	   	    var num = chooseBuserPartTable.getSelectedRowNum();
	   	    if(num >=1){
	   	    	status='1';
	   	    	buserId = chooseBuserPartTable.getSelectedFields('userId');
	   	    }else{
	   	    	status='0';
	   	    	if(data.length ==0){
	   	    		warning('请选择一个用户');
	   	    		return;
	   	    	}
	   	    	buserId = null;
	   	    }
			var param={'searchBean.newBuserStr':buserId,'searchBean.teamId':$('#chooseId').val(),'searchBean.havBuser':status,'searchBean.oldBuserStr':data.toString()};
			doPost(url,param ,function(result) {
				if (!printError(result)) {
					closeWindow("project_set_part_buser");
					dataTable.refresh();
				}
			});
        }
           
           function cancelPartBuser(){
        	   closeWindow("project_set_part_buser");
           }
           
           chooseBuserPartTable.onLoadSuccess=function(datas){
           var chooseId = $('#chooseId').val();
   		   for (var i=0;i<datas.length;i++) {
				if (datas[i].teamId ==chooseId ) {
					data.push(datas[i].userId);
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