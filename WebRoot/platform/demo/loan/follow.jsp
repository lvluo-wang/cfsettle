<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
 <link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/follow.css'/>"    /> 
<script type="text/javascript">
$(function(){
	var index="<%=request.getParameter("index")%>";

		if(index=='null'){
			$("#step1").attr('class',"current");
			return;
		}
		if(index>1){
			for(var i=1;i<index;i++){
				$("#step"+i).attr('class',"done current-prev");
			}
		}
		
		if(index=='4'){
			$("#step"+index).attr('class',"last-current");
		}else{
			$("#step"+index).attr('class',"current");
		}
		
});
	
	
</script>
<div class="flow-steps">
	<ol class="num4">
		<li id="step1">
			<span>1.申请</span>
		</li>
		<li id="step2">
			<span>2.业务受理</span>
		</li>
		<li id="step3">
			<span>3.审核</span>
		</li>
		<li id="step4" class="last">
			<span>4.转账完成</span>
		</li>
	</ol>
</div>