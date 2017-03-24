<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<div class="win_form_area">
				<form id="viewForm" class="busi_form">
					<s:hidden name="sysfunc.funcId"/>
					<s:hidden name="sysfunc.parentFuncId"/>
					<s:hidden name="sysfunc.version"/>
					<s:hidden name="sysfunc.sortNo"/>
					<table>
						<colgroup>
							<col width="150px">
						
						</colgroup>
					<tbody>
						<tr>
							<td  class="title"><s:text name="sysfunc.funcName"/>:</td>
							<td>${sysfunc.funcName}</td>
						</tr>
						
						<tr>
							<td class="title" ><s:text name="sysfunc.funcType"/>:</td>
							<td>
							<x:combobox name="sysfunc.funcType" value="${sysfunc.funcType}" valueField="codeNo" disabled="true" textField="codeName" list="funcType" pleaseSelect="false"/>
							</td>
						</tr>
						<tr>
							<td  class="title"><s:text name="sysfunc.useType"/>:</td>
							<td>
							<x:combobox name="sysfunc.useType" value="${sysfunc.useType}" valueField="codeNo" textField="codeName" disabled="true" list="useType" pleaseSelect="false"/>
							</td>
						</tr>
						<tr>
							<td  class="title" >URL:</td>
							<td>${sysfunc.url}</td>
						</tr>
						<tr>
							<td  class="title"><s:text name="sysfunc.remark"/>:</td>
							<td>${sysfunc.remark}</td>
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