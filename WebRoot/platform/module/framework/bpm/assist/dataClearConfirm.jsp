<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>废旧流程数据清理</title>

   <style type="text/css"> 
    body{ 
        text-align:center; 
    } 
   .graph{ 
        width:450px; 
        border:1px solid #F8B3D0; 
        height:25px; 
   }  
   #bar{ 
        display:block; 
        background:#FFE7F4; 
        float:left; 
        height:100%; 
        text-align:center; 
   } 
   #barNum{ 
        positon:absolute; 
   } 
    </style> 
    
    <script type="text/javascript" language="javascript">
		function dateClear() {
			if (confirm("确实要进行清理吗？    ")){
				document.getElementById('mybox').style.display="block";
				
				var url = "<s:url value='/bpm/dataClear_dataClear.jhtml'/>";
				//var paras = $("#queryForm").serialize();
				url = url+"?clearDate="+document.getElementsByName("clearDate")[0].value;;
				redirectUrl(url,null);
				
				//queryForm.submit();				
			}
			
		}	
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
	<s:form name="queryForm" id="queryForm" theme="simple" action="/bpm/dataClear_dataClear.jhtml">
		<div align="center" width="100%" >
		<table align="center" width="100%">
			
			<tr>
				<td>清理截止日:
					<input name="clearDate" id="clearDate" class="easyui-datebox" validType="dateValidator" maxLength="10" value="<fmt:formatDate value="${clearDate}" pattern="yyyy-MM-dd"/>" />
				</td>
			</tr>
			<tr>
				<td>此次清理流程数目:
					<FONT  color="blue"><s:property value="clearPiCount"></s:property></FONT>
				</td>
			</tr>
			<tr>
				<td>流程总数:
					<s:property value="piCount"></s:property>
				</td>
			</tr>
			<tr>
				<td>已结束流程总数:
					<s:property value="endPiCount"></s:property>
				</td>
			</tr>
			<tr>
				<td>未结束流程数:
					<s:property value="unEndCount"></s:property>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>													
				<td>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-close" onClick="dateClear();"><s:text name="清理确认"/></a>
					&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-back" onClick="toBack()"><s:text name="返回"/></a>
				</td>
				
			</tr>	
		</table>
		</div>
	</s:form>
</fieldset>
	<div id="mybox" class="graph" style="display:none"> 
	    <strong id="bar" style="width:100%;">正在清除，请稍候。。</strong> 
	</div> 
</div>
</body>
</html>
