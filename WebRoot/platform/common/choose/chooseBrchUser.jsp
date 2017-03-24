<%@page import="com.upg.ucars.framework.base.SessionTool"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseUserSelect" icon="icon-yes"  text="choose"/>
		<x:button id="chooseUserCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	
		<table class="detail_table">
			    <colgroup>
			    
						<col width="200px"/>
			            <col width="100%"/>
		        </colgroup>	
			<tbody>
				
		         <tr>
					<td class="detail_head">机构树</td>
					<td class="detail_head"><div id="title">用户信息</div></td>
				</tr>
				<tr>
					<td class="detail_td">
						<div style="height:420px;width:210px;overflow:auto;">
							<ul id="brchTree" ></ul>
						</div>
					</td>
				<td class="detail_td">
					
							<div id="userDetail"  closable="false" selected="false">
								<br>
								<h3><s:text name="prompt"/></h3>
								<ul>
									<li>左边为机构树</li>
									<li>请在右边选择用户。</li>								
								</ul>
							</div>
						
				</td>
			</tr>
			</tbody>
		</table>
		
		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
var chooseContext_user=   ChooseContext["choose_brchuser"];
chooseContext_user.init=function(){
	$("#brchTree").tree({
		checkbox: false,
		url:'<s:url value="/security/brch_getBranchTree.jhtml"/>',
		//data:str2obj(result),
		cascadeCheck:false,
		onClick:function(node){
			var brchId=node.id;
			loadBrchUser(brchId);
		}
	});
	
	loadBrchUser("<%=SessionTool.getUserLogonInfo().getBranchId()%>");
};

function loadBrchUser(brchId) {
	var url='<s:url value="/security/user_selectUser.jhtml"/>?branch.brchId='+brchId+'&flag='+Math.random()*99999;
	doPost(url,null,function(result){
	   //alert(result);
		if(result){
			$("#userDetail").html(result);
			$.xcarsParser.parse($("#userDetail"));
		}
	});
}

 var selectuser_dataTable   = null;
$("#chooseUserSelect").unbind().click(function(){
	
	if(selectuser_dataTable && selectuser_dataTable.getSelectedFirstRow())
	{
	   if(chooseContext_user.callback && $.isFunction(chooseContext_user.callback))
		   chooseContext_user.callback(selectuser_dataTable.getSelectedFirstRow());
		chooseContext_user.win.window("close");
		var target = chooseContext_user.target;
		if (target) {
			$(target).focus();
		}
	}else{
		info(global.notSelectInfo);
	}
	return false;
});
$("#chooseUserCancel").unbind().click(function(){
	if(chooseContext_user.callback && $.isFunction(chooseContext_user.callback)){
		 chooseContext_user.callback();
	}
	chooseContext_user.win.window("close");
	var target = chooseContext_user.target;
	if (target) {
		$(target).focus();
	}
	return false;
});
</script>
	
	</tiles:putAttribute>
</tiles:insertDefinition>