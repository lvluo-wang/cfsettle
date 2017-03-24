<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertAttribute name="head"/>
<!-- 由此开始页面布局 -->

	<div class="win_form_area">
		<tiles:insertAttribute name="form"/>
	</div>
	<div   class="win_button_area">
		<tiles:insertAttribute name="button"/>
	</div>
	<!-- 弹出窗口定义开始 -->
<tiles:insertAttribute name="window"/>
<tiles:insertAttribute name="end"/>
