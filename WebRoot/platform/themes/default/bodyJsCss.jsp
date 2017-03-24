<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--platform js -->
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery-1.6.min.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/easyui/jquery.easyui.min_inner.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/easyui/locale/easyui-lang-${session.WW_TRANS_I18N_LOCALE }.js"></script>
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

<script src="<s:url value='/platform/static/base/xdatagrid.js'/>" type="text/javascript"></script>
<!-- My97DatePicker -->
<script language="javascript" type="text/javascript" src='<s:url value="/platform/static/plugins/My97DatePicker/WdatePicker.js"/>' ></script>

<!--platform css  -->
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/default/easyui.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/icon.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/plugins/mask/css/mask.css'/>"/> 
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/main.css'/>" />
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/table_form.css'/>" />
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/step.css'/>" />
<script>
	function redirectUrl(url,param){
		if( url ){
			if( url.indexOf("?") > -1 ){
				url += "&wrap=1"
			}else{
				url += "?wrap=1";
			}
			if( $.isPlainObject(param) ){
				var paramStr = "";
				var k = null;
				for( k in param ){
					paramStr += '<input name="' + k +'" value="'+ param[k] + '"/>';
				}
				$("body").prepend('<div style="display:none;"><form id="changeUrlForm" action="'+url+'" method="post">'+paramStr+'</form></div>');
				$("#changeUrlForm")[0].submit();
			}else{
				location.href=url;
			}
		}
	 }
</script>