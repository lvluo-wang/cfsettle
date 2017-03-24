<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
  
<tiles:insertDefinition name="FUNC_FLOW_FORM">
	<tiles:putAttribute name="flow">
	<s:include value="/platform/demo/loan/step.jsp?index=3"></s:include>
	</tiles:putAttribute>
	<tiles:putAttribute name="form">
               <form id="formElem" class="busi_form" name="formElem" action="<%=request.getContextPath()%>/demo/loan_acceptInfo.jhtml" method="post">
                   	<s:hidden name="taskId"></s:hidden>
                   	<s:hidden name="id"></s:hidden>  
                   	<s:hidden name="br.success"></s:hidden>
                   	<s:hidden name="br.info"></s:hidden>
                   	<s:hidden name="personInfo.name"></s:hidden> 
                   	<s:hidden name="personInfo.address"></s:hidden>
                   	<s:hidden name="personInfo.phone"></s:hidden>
                   	<table>
                   		<tr>
                   			<td colspan="2">账户信息</td>
                   		</tr>
                   		<tr>
                   			<td class="title">账户名称:</td>
                   			<td><input class="formPannel_input" name="personInfo.acctName"/></td>
                   		</tr>
                   		<tr>
                   			<td class="title">账户帐号:</td>
                   			<td><input class="formPannel_input" name="personInfo.acctNo"/></td>
                   		</tr>
                   		<tr>
                   			<td class="title">开户行:</td>
                   			<td><input class="formPannel_input" name="personInfo.bankNo"/></td>
                   		</tr>
                   	</table>                
                 </form>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">

function step_next(){	
	var url = "<s:url value='/demo/loan_acceptTaskSubmit.jhtml'/>";	
	var paras = formToObject("formElem");
	//requestToWorkapace(url,paras,"业务受理");
	//initPageContentWithPara("pageContent",paras,url);
	redirectUrl(url,paras);
	
	
} 
function step_return(){
	var url = "<s:url value='/demo/loan_acceptTaskPersonInfo.jhtml'/>";	
	var paras = formToObject("formElem");
	//requestToWorkapace(url,paras,"业务受理");
	//initPageContentWithPara("pageContent",paras,url);
	redirectUrl(url,paras);
}

</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
