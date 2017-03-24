<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
    function langSelecter_onChanged() {
        document.langForm.submit();
    }
</script>
<s:set name="SESSION_LOCALE" value="#session['WW_TRANS_I18N_LOCALE']"/>
<s:bean id="locales" name="com.upg.ucars.framework.base.Locales"/>
<form style="border:0px;margin:0px;padding:0px;" action="<s:url includeParams="get" encode="true"/>" name="langForm" >
    <s:select label="Language" 
        list="#locales.locales" listKey="value"    listValue="key"
        value="#SESSION_LOCALE == null ? locale : #SESSION_LOCALE"
        name="request_locale" id="langSelecter" 
        onchange="langSelecter_onChanged()" theme="simple" style="float:right;"/>
</form>