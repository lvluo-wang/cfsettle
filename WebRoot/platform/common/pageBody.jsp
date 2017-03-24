<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<s:include value="/platform/themes/default/bodyJsCss.jsp"  />
<s:include value="/customizeJsCss.jsp"/>
	<script type="text/javascript">
	$(function(){
		setBaseDir('${pageContext.request.contextPath}');
		var targetUrl='${param.targetUrl}';
		if(targetUrl.length>1){
			var param = null;
			<%
				java.util.Enumeration parameterNames = request.getParameterNames();
				if( parameterNames != null ){
					int i = 0;
					String paramJson = null;
					while(parameterNames.hasMoreElements()){
						String paramKey = (String) parameterNames.nextElement();
						if(paramKey != null ){
							String value = request.getParameter(paramKey);
							if(i++ > 0){
								paramJson += ",";
							}else{
								paramJson = "{";
							}
							paramJson += "'"+paramKey+"':'"+value + "'";
						}
					}
					if(paramJson != null){
						paramJson += "}";
						out.println("param="+paramJson + ";");
					}
				}
			%>
			if( param == null ){
				initPageContent("pageContent",targetUrl);
			}else{
				initPageContentWithPara("pageContent",param,targetUrl);
			}
		}
	});
</script>
</head>
<body id="pageContent"  class="easyui-layout" fit="true">
<div id="loading" class="common-mask" style="display:none;"></div>
<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
	<div region="center" border="false" style="overflow:hidden;padding:0px;margin:0px;">
		<!-- <div id="pageContent" class="easyui-panel" fit="true" border="false">
			
		</div> -->
	</div>

</body>
</html>