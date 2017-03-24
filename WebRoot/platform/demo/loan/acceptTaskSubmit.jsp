<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
  
<tiles:insertDefinition name="FUNC_FLOW_FORM">
	<tiles:putAttribute name="flow">
	<s:include value="/platform/demo/loan/step.jsp?index=4"></s:include>
	</tiles:putAttribute>
	<tiles:putAttribute name="form"> 
               <form id="formElem" class="busi_form" name="formElem" action="#" method="post">
                   	<s:hidden name="taskId"></s:hidden>
                   	<s:hidden name="id"></s:hidden>     
					<s:hidden name="br.success"></s:hidden>
                   	<s:hidden name="br.info"></s:hidden>
                   	<table>
                   		<tr>
                   			<td colspan="3">信息确认</td>
                   		</tr>
                   		<tr>
                   			<td  class="title" rowspan="4">个人信息</td>
                   			<td class="title">姓名:</td>
                   			<td><input name="personInfo.name" class="formPannel_input"></input>  </td>
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
                   		<tr>
                   			<td  class="title" rowspan="3">账户信息</td>
                   			<td class="title">账户名称:</td>
                   			<td><input name="personInfo.acctName" class="formPannel_input"></input>   </td>
                   		</tr>
                   		<tr>
                   			<td class="title">账户帐号:</td>
                   			<td><input name="personInfo.acctNo" class="formPannel_input"></input></td>
                   		</tr>
                   		<tr>
                   			<td class="title">开户行:</td>
                   			<td><input name="personInfo.bankNo" class="formPannel_input"></input></td>
                   		</tr>
                   		<tr>
                   			<td colspan="3"><i><font color="blue">以上信息填写完成，并确认无误后请提交。</font></i></td>
                   		</tr>
                   		<tr>
                   			<td colspan="3"><a href="#" class="easyui-linkbutton" style="background:#efefef;" plain="true" onclick="submitForm()">确认提交</a></td>
                   		</tr>
                   	</table>  
                    
                 </form>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">

function step_next(){
	
} 
function step_return(){
	var url = "<s:url value='/demo/loan_acceptTaskAcctInfo.jhtml'/>";	
	var paras = formToObject("formElem");
	//requestToWorkapace(url,paras,"业务受理");
	//initPageContentWithPara("pageContent",paras,url);
	redirectUrl(url,paras);
}

function submitForm(){
	var url = "<s:url value='/demo/loan_acceptInfo.jhtml'/>";	
	var paras = formToObject("formElem");
	//requestToWorkapace(url,paras,"业务受理");
	//initPageContentWithPara("pageContent",paras,url);
	redirectUrl(url,paras);
}

</script>
	</tiles:putAttribute>
	</tiles:insertDefinition>