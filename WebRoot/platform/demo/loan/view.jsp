<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form  id="editForm" class="busi_form">
					<s:hidden name="loanInfo.id" />
					<s:hidden name="loanInfo.status" />	
					<table>
					<colgroup>
						<col width="45%"/>
						<col width="55%"/>
					</colgroup>
						<tbody>
							<tr>
								<td class="title">贷款编号:</td>
								<td>${loanInfo.loanNo}</td>
							</tr>
							<tr>
								<td class="title">贷款人名称:</td>
								<td>${loanInfo.userName}</td>
							</tr>
							<tr>
								<td class="title">贷款金额:</td>
								<td>${loanInfo.money}</td>
							</tr>
						</tbody>
					</table>				
					<div>
								
					</div>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-cancel" click="doCancel" text="cancel"/>
	</tiles:putAttribute>
</tiles:insertDefinition>