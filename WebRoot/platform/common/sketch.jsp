<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

$(function(){
	var flowName="<%=request.getParameter("flowName")%>";
	var flowindex="<%=request.getParameter("index")%>";	
	flowindex = flowindex - 1;
	var flowSize = $("#"+flowName+" > li").size();
	
	$("#sketchButton").mouseover( function () { switchSketch(flowName, flowindex, flowSize); } );
	$("#sketchButton").mouseout( function () { $("#mysketch").remove();} );
});

function switchSketch(flowName, flowindex, flowSize){	
	$("<div id=\"mysketch\" class=\"wf_viewdiv\" style=\"display:none;\"></div>").appendTo($("body"));
	
	$("#"+flowName+" > li").each(function(i){		
		if (i!=flowindex){
			$("#mysketch").append($(this).html());
		}
		if (i==flowindex){//删除本页面以外的
			$("#mysketch").append('<font color=red >'+$(this).html()+'</font>');
		}
		if (flowSize-1 != i){
			$("#mysketch").append('>>');
		}
	});	
	
	$("#mysketch").css({
	 	display:"block",
	 	width:500,
	 	height:30,
	 	left:$("#job_work_area").offset().left+180,
		top:$("#job_work_area").offset().top-25
	 });
	
}

</script>

<div id="flowcontext" style="display:none;">
	<div id="flow_loan">
		<li>贷款单据</li><li>受理业务</li><li>业务审批</li><li>高级审批</li><li>划款结果</li>
	</div>
	<div id="flow_example1">
		<li>aaa</li><li>bbb</li><li>ccc</li><li>ddd</li><li>eee</li>
	</div>
</div>

