<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String baseDir = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/demo/slidform/css/slidform.css'/>"    />

<script language="javascript" src="<%=baseDir %>/platform/demo/slidform/slidform.js"></script>

<div>
        <div id="navigation" class="flow-steps" style="display:block;">
                    <ul>
                        <li class="current selected">
                            <a href="#">账户信息</a>
                        </li>
                        <li>
                            <a href="#">个人信息</a>
                        </li>                        
                        <li>
                            <a href="#">贷款信息</a>
                        </li>
						<li class="last">
                            <a href="#">提交</a>
                        </li>
                    </ul>
         </div>
      <div id="content">
            <div id="wrapper">
            <a class="prev disabled"></a>
			<a class="next disabled"></a>
                <div id="steps">
                    <form id="formElem" name="formElem" action="<%=request.getContextPath()%>/demo/loan_acceptInfo.do" method="post">
                    	<input type="hidden" name="taskId" />
                    	<input type="hidden" name="id" />
                        <fieldset class="step">
                            <legend>账户</legend>
                            <p>
                                <label for="accountName">账户名称:</label>
                                <input id="accountName" name="accountName" />
                            </p>
                            <p>
                                <label for="accountNo">账户帐号:</label>
                                <input id="accountNo" name="accountNo" placeholder="info@tympanus.net" type="email" AUTOCOMPLETE=OFF />
                            </p>
                            <p>
                                <label for="bankNo">开户行:</label>
                                <input id="bankNo" name="bankNo" type="bankNo" AUTOCOMPLETE=OFF />
                            </p>
                        </fieldset>
                        <fieldset class="step">
                            <legend>个人信息</legend>
                            <p>
                                <label for="name">姓名:</label>
                                <input id="name" name="name" type="text" AUTOCOMPLETE=OFF />                                
                            </p>
                            <p>
                                <label for="address">地址:</label>
                                <input id="address" name="address" type="text" AUTOCOMPLETE=OFF />
                            </p>
                            <p>
                                <label for="phone">电话:</label>
                                <input id="phone" name="phone" placeholder="e.g. +351215555555" type="tel" AUTOCOMPLETE=OFF />
                            </p>
                            <p>
                                <label for="mail">E-mail:</label>
                                <input id="mail" name="website" placeholder="e.g. http://www.codrops.com" type="tel" AUTOCOMPLETE=OFF />
                            </p>
                        </fieldset>                        
                        <fieldset class="step">
                            <legend>贷款信息</legend>
                            <p>
                                <label for="rateType">利率类型</label>
                                <select id="rateType" name="rateType">
                                    <option value="360" selected>年利率</option>
                                    <option value="30">月利率</option>
                                    <option value="1">日利率</option>
                                </select>
                            </p>
                            <p>
                                <label for="rate">利率</label>
                                <input id="rateType" name="rateType" type="text" AUTOCOMPLETE=OFF />
                            </p>
                        </fieldset>
						<fieldset class="step">
                            <legend>提交</legend>
							<p>
								以上信息填写完成，并确认无误后请提交。
							</p>
                            <p class="submit">
                                <button id="registerButton" type="submit">确认提交</button>
                            </p>
                        </fieldset>
                    </form>
                </div>
                <div>
                	<a href="<%=request.getContextPath()%>/demo/loan_mainAcceptTask.do">返回</a>
                </div>
            </div>
        </div>

</div>
<script type="text/javascript">	
	$(function(){
		var taskId="<%=request.getParameter("taskId")%>";
		formElem.taskId.value=taskId;
		var id="<%=request.getParameter("id")%>";
		formElem.id.value=id;
			
	});
</script>