<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_form_area">
			<form class="busi_form">
			<table>
				<colgroup>
					<col width="150"/>
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
					<td class="title"><s:text name="product"/><s:text name="name"/><s:text name="i18n"/>:</td>
					<td>${prod.prodNameKey}</td>
				</tr>

				<tr>
					<td class="title"><s:text name="product"/><s:text name="type"/>:</td>
					<td>
						<x:combobox name="prod.prodType" disabled="true"  list="prodType"/>
						
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
						<li><s:text name="prompt_sysfucn_li3"/></li>
					</ul>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>