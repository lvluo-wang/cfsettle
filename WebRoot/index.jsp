<%
	String[] MOBILE_KEYWORD = {"android", "iphone", "ipod", "ipad", "windows phone", "mqqbrowser"};
	boolean mobile = false;
	String userAgent = request.getHeader("user-agent");
	if (userAgent != null && userAgent.trim().length() > 0) {
		userAgent = userAgent.toLowerCase();
		for (String keyword : MOBILE_KEYWORD) {
			if (userAgent.contains(keyword)) {
				//mobile = true;
				//break;
			}
		}
	}
%>
<%
	if (mobile) {
%>
	<jsp:forward page="/mobile/login.jsp"/>
<%
	} else {
%>
	<jsp:forward page="/login.jsp"/>
<%
	}
%>

