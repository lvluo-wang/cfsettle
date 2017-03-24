


<body>
<div class="easyui-layout" fit="true" border="false">
	<div region="north" style="padding-left:2px;padding-top:5px;background:#fafafa;height:100px;overflow:hidden;" class="formPannel" border="false" split="false">
		<#if custInfo??>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_label" align="right"><@s.text name="custInfo.custNo" />:</td>
					<td class="td_input" align="left">${custInfo.custNo }</td>
					<td width="60px"></td>
					<td class="td_label" align="right"><@s.text name="custInfo.custName" />:</td>
					<td class="td_input" align="left">${custInfo.custName }</td>
				</tr>
				<tr>
					<td class="td_label" align="right"><@s.text name="custInfo.contactPerson" />:</td>
					<td class="td_input" align="left">${custInfo.contactPerson}</td>
					<td width="60px"></td>
					<td class="td_label" align="right"><@s.text name="custInfo.contactPhone" />:</td>
					<td class="td_input" align="left">${custInfo.contactPhone }</td>
				</tr>
				
				<tr>
					<td class="td_label" align="right"><@s.text name="custInfo.contactPerson" />:</td>
					<td class="td_input" align="left">${map["cust"].contactPerson}</td>
					<td width="60px"></td>
					<td class="td_label" align="right"><@s.text name="custInfo.contactPhone" />:</td>
					<td class="td_input" align="left">${map.get("cust").contactPhone }</td>
				</tr>
				
				
			</table>
		</#if>
		
	</div>
	
	<div region="center"  id="dataView" style="overflow:hidden;height:370px;" border="false">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_label" >客户账号</td>
				<td class="td_label">开户机构</td>
				<td class="td_label">开户日期</td>
				
			</tr>
			<#if accounts??>
				<#list accounts as acc>
				<tr>
					
					<td class="td_input">${acc.acctNo}</td>
					<td class="td_input">${acc.acctBrchName}</td>
					<td class="td_input">${acc.openDate?string("yyyy-MM-dd")}</td>
					
				</tr>
				</#list>
			</#if>
		</table>
	</div>

</body>
</html>
