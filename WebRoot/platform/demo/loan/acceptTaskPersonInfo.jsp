<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
  
<tiles:insertDefinition name="FUNC_FLOW_FORM">
	<tiles:putAttribute name="flow">
	<s:include value="/platform/demo/loan/step.jsp?index=2"></s:include>
	</tiles:putAttribute>
	<tiles:putAttribute name="form">
               <form id="formElem"  class="busi_form" name="formElem" action="<%=request.getContextPath()%>/demo/loan_acceptInfo.jhtml" method="post">
                   	<s:hidden name="taskId"></s:hidden>
					<s:hidden name="id"></s:hidden>
					<s:hidden name="br.success"></s:hidden>
                   	<s:hidden name="br.info"></s:hidden>
                  <table>
                  	<tr>
                  		<td colspan="2">个人信息</td>
                  	</tr>
                  	<tr>
                  		<td class="title">姓名:</td>
                  		<td><input name="personInfo.name" class="formPannel_input"></input></td>
                  	</tr>
                  	<tr>
                  		<td class="title">地址:</td>
                  		<td><input name="personInfo.address" class="formPannel_input"></input></td>
                  	</tr>
                  	<tr>
                  		<td class="title">电话:</td>
                  		<td><input name="personInfo.phone" class="formPannel_input"></input></td>
                  	</tr>
                  	<tr>
                  		<td class="title">E-mail:</td>
                  		<td><input name="personInfo.email" class="formPannel_input"></input></td>
                  	</tr>
                  </table>
                 </form>
                </tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">

function step_next(){
	var url = "<s:url value='/demo/loan_acceptTaskAcctInfo.jhtml'/>";	
	var paras = formToObject("formElem");
	redirectUrl(url,paras);
} 
function step_return(){
	var url = "<s:url value='/demo/loan_mainAcceptTask.jhtml'/>";	
	var paras = formToObject("formElem");
	redirectUrl(url,paras);
}

</script>
	</tiles:putAttribute>
</tiles:insertDefinition>