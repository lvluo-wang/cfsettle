<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>废旧流程数据清理</title>
    <script type="text/javascript" language="javascript">
		function toBack() {
			var url = "<s:url value='/bpm/dataClear_main.jhtml'/>";			
			redirectUrl(url,null);
		}		
	</script>
</head>

<body> 

	

<div id="content">
	
<fieldset>
	<legend>废旧流程数据清理</legend>
	<s:form name="queryForm" theme="simple" action="dataClear_main.jhtml">
		<div align="center" width="100%" >
		<table align="center" width="100%">
			
			<tr>
				<td>预期清理总数:
					<FONT  color="blue"><s:property value="rsCount[0]"></s:property></FONT>
				</td>
			</tr>
			<tr>
				<td>实际清理总数:
					<FONT  color="blue"><s:property value="rsCount[1]"></s:property></FONT>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>													
				<td>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-back" onClick="toBack()"><s:text name="返回"/></a>
				</td>
			</tr>	
		</table>
		</div>
	</s:form>
</fieldset>
	<div class="content">
		
	</div>
</div>
</body>
</html>
