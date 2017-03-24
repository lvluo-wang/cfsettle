<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<div id="step">
	<span id="step_list">
		<span id="step1">受理业务信息</span>
		<span id="step2">个人信息</span>
		<span id="step3">贷款信息</span>
		<span id="step4">提交</span>
	</span>
	<span id="step_tool">
		<x:button id="step_return" icon="icon-back" click="step_return" text="back"/>
		<x:button id="step_next" icon="icon-next" click="step_next" text="next"/>
	</span>
</div>
<script type="text/javascript">
	var current=1;
	var fieldsetCount=0;
	var index="<%=request.getParameter("index")%>";
	fieldsetCount = $('#step_list').find('span').length;
		if(index=='null'){
			index='1';
		}else{
			current=parseInt(index);
		}
		$('#step_list span').each(function(i){
			var $step=$(this);
			if(i==0){
				$step.addClass('step_first');
			}else{
				$step.addClass('step_nomal');
			}
			if(parseInt(index)==i+1){
				$step.addClass('step_current');	
			}
		});
		if(parseInt(index)==1){
			$("#step_return").hide();
		}
		if(parseInt(index)==fieldsetCount){
			$("#step_next").hide();
		}

</script>