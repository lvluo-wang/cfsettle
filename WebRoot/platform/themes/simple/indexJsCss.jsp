<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- js -->
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery-1.11.0.min.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery-migrate-1.2.1.min.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/easyui/jquery.easyui.min.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/easyui/locale/easyui-lang-${session.WW_TRANS_I18N_LOCALE}.js"></script>
<script language="javascript" src="<s:url value="/platform/static/base/base.js"/>"></script>
<script type="text/javascript">
	setBaseDir('${pageContext.request.contextPath}');
</script>
<script language="javascript" src="<s:url value="/platform/static/plugins/jsextend.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/i18n/global_message_${session.WW_TRANS_I18N_LOCALE}.js"></script>
<script language="javascript" src="<s:url value="/platform/static/base/tool.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/base/component.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/base/validate.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/plugins/mask/mask.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/plugins/lightbox/lightbox.min.js"/>"></script>
<!-- css  -->
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/default/easyui.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/icon.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/plugins/mask/css/mask.css'/>"/> 
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/table_form.css'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/plugins/lightbox/css/lightbox.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/themes/simple/css/funcmain.css"/>">
