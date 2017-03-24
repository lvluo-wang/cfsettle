<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_form_area">
			<form class="busi_form">
			<table class="detail_table">
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
				<tr>
					<td class="title"><s:text name="product"/><s:text name="no"/>:</td>
					<td>${prod.prodNo}</td>
				</tr>
				<tr>
					<td class="title"><s:text name="product"/><s:text name="name"/>:</td>
					<td>${prod.prodName}</td>
				</tr>
				<tr>
					<td class="title" ><s:text name="product"/><s:text name="name"/>本地化:</td>
					<td>${memberProd.prodAlias}</td>
				</tr>
				<tr>
					<td class="title">需要审批:</td>
					<td>
						<x:combobox name="memberProd.isAudit" disabled="ture"  list="yesNoList"/>
						
					</td>
				</tr>
				<tr>
					<td class="title">需要复核:</td>
					<td>
						<x:combobox name="memberProd.isCheck" disabled="ture"  list="yesNoList"/>
						
					</td>
				</tr>
				<tr>
					<td class="title"><s:text name="product"/><s:text name="type"/>:</td>
					<td>
						<x:combobox name="prod.prodType" disabled="ture"  list="prodType"/>
						
					</td>
				</tr>
				<tr>
					<td class="title">URL:</td>
					<td>${prod.prodUrl}</td>
				</tr>
				<tr>
					<td class="title"><s:text name="remark"/>:</td>
					<td>${prod.remark}</td>
				</tr>
				</tbody>
			</table>
			</form>
			<h3><s:text name="prompt"/></h3>
					<ul>
						<li><s:text name="prompt_sysfucn_li1"/></li>
						<li><s:text name="prompt_sysfucn_li2"/></li>
					</ul>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
