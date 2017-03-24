<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="jscss"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/default/css/layout.css'/>"/> 
</head>
<body style="overflow:auto;">
<div id="loading" class="common-mask" style="display:none;"></div>
<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
<script type="text/javascript">
loadmask();
	var userMenuStr='${userMenu}';
</script>

	<div id="header" region="north" style="overflow:hidden;">
		<tiles:insertAttribute name="header"/>
	</div>
	<div id="menuarea" region="west">
		<tiles:insertAttribute name="menuarea"/>
	</div>
	<div id="workarea" region="center">
		<tiles:insertAttribute name="workarea"/>
	</div>
	<div id="footer" region="south">
		<tiles:insertAttribute name="footer"/>
	</div>

</body>
<script language="javascript" src="<s:url value="/platform/themes/default/js/layout.js"/>"></script>
</html>