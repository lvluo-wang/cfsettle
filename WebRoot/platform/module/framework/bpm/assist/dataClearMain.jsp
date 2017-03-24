<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>废旧流程数据归档</title>
    <script type="text/javascript" language="javascript">
		function toClear() {
			
			//var paras = $("#queryForm").serialize();
			
			var url = "<s:url value='/bpm/dataClear_toClearConfirm.jhtml'/>";
			
			url = url+"?clearDate="+document.getElementById("clearDate").value;
			redirectUrl(url,null);
		}		
	</script>
    
</head>

<body>
	
<div id="content">
	
<fieldset>
	<legend>废旧流程数据清理</legend>
	<s:form name="queryForm" id="queryForm" theme="simple" action="dataClear_toClearConfirm.jhtml">
		<div align="center" width="90%" >
		<table align="center" width="90%">			
			<tr>
				<td>请输入清理日期:
					<input name="clearDate" class="easyui-datebox" id="clearDate" validType="dateValidator" maxLength="10" value="<fmt:formatDate value="${clearDate}" pattern="yyyy-MM-dd"/>" />
					
					（注：清理此日期（含）之前结束的流程）
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-close" onClick="toClear()"><s:text name="清理"/></a>
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
