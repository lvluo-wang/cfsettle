HELLO ${model};
安练武
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>X-FOSC</title>


</head>
<body>
<div class="easyui-layout" fit="true" border="false">
	<div region="north" style="padding-left:2px;padding-top:5px;background:#fafafa;height:100px;overflow:hidden;" class="formPannel" border="false" split="false">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_label" align="right">客户号:</td>
				<td class="td_input" align="left">${custInfo.custNo }</td>
				<td width="60px"></td>
				<td class="td_label" align="right">客户名称:</td>
				<td class="td_input" align="left">${custInfo.custName }</td>
			</tr>
			<tr>
				<td class="td_label" align="right">联系人:</td>
				<td class="td_input" align="left">${custInfo.contactPerson}</td>
				<td width="60px"></td>
				<td class="td_label" align="right">联系电话:</td>
				<td class="td_input" align="left">${custInfo.contactPhone }</td>
			</tr>
			
		</table>
	</div>
	<div region="center"  id="dataView" style="overflow:hidden;height:370px;" border="false">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_label" align="right">客户账号</td>
				<td class="td_label" align="right">开户机构</td>
				<td class="td_label" align="right">开户日期</td>
				
			</tr>
			
			<#list accounts as acc>
			<tr>
				
				<td class="td_input" align="left">${acc.acctNo}</td>
				<td class="td_input" align="left">${acc.acctBrchName}</td>
				<td class="td_input" align="left">${acc.openDate?string("yyyy-MM-dd")}</td>
				
			</tr>
			</#list>


