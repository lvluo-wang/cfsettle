<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
	
	<br>							
		产品树的节点是否允许移动<INPUT TYPE="checkbox" NAME="" id="isAllowMove" checked="checked" onclick="doCheckAllowMove();"/>
		<br>
		<h3><s:text name="prompt"/></h3>
		<ul>
			<li><s:text name="prompt_sysfucn_li1"/></li>
			<li><s:text name="prompt_sysfucn_li2"/></li>
			<li><s:text name="prompt_sysfucn_li3"/></li>
		</ul>


<script type="text/javascript">
	document.getElementById("isAllowMove").checked = isAllowMove;
</script>