<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--system js
<script language="javascript" src="${pageContext.request.contextPath}/scf/static/js/xfosc_global_message_${session.WW_TRANS_I18N_LOCALE}.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/scf/static/js/xfosc_validate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/scf/static/js/xfosc_common.js"></script>
 -->
<script>

function codeFormatter() {
	var codeFormatterTds = $('td[codeFormatter]');
	var keys = [];
	 
	codeFormatterTds.each(function() {
		keys.push($(this).attr("codeFormatter"));
	});
	
	var code=new CodeUtil(keys);
	code.loadData();	
	
	codeFormatterTds.each(function() {
		 $(this).text(code.getValue($(this).attr("codeFormatter"), $(this).text()));
	});
}

jQuery.fn.inputChange = function(callback){
	if($.browser.msie){
		this.bind("propertychange", function(e){
			if(e.originalEvent.propertyName == "value"){
				$(this).keyup();
			}
		});
		this.bind("keyup", callback);
	}
	else{
		this.bind("input", callback);
	}
	return(this);
};

$.fn.outerHTML = function() {
    // IE, Chrome & Safari will comply with the non-standard outerHTML, all others (FF) will have a fall-back for cloning
    return (!this.length) ? this : (this[0].outerHTML ||
    (function(el) {
        var div = document.createElement('div');
        div.appendChild(el.cloneNode(true));
        var contents = div.innerHTML;
        div = null;
        return contents;
    })(this[0]));
};


function calSum() {
	$(".sum").each(function() {
		var suffix = $(this).attr("suffix") || "";
		var columnIndex = $(this).getNonColSpanIndex() + 1;
		var $tbody = $(this).closest('tbody');
		var tot = 0;
		$tbody.children("tr").children("td:nth-child(" + columnIndex + ")").each(function() {
			tot += parseFloat($(this).html()) || 0;
	  	});
		
		$(this).html((tot.toFixed(2) * 100 / 100) + suffix);
	});
}


	function getProcessViewUrl(type,obj){
		var url = null;
		if(type == 'com.upg.ubsp.mapping.RepaymentDelay'){
			url = '<s:url value="/cash/taskCash_viewDoneTask.jhtml"/>';
		}
		return url;
	}
	
	//显示当前任务名
	function showTaskTips(dataTable){
		dataTable.onLoadSuccess=function(){
			$("span.process_tip").each(function(){
				$(this).parent().tip({
					url:'<s:url value="/bpm/task_getTodoTaskInfos.jhtml"/>?ib.id='+$(this).attr("instanceBusinessId")
				});
			});
		}
	}
	function formatStatusTips(value,row,codeKey){
		var result = '<span class="process_tip" instanceBusinessId="' + row.instanceBusinessId + '">' + code.getValue(codeKey, value) + '</span>';
		return result;
	}
</script>

<!-- 这些样式影响比较大啊，如果需要的话再改吧！ -->
<style>
h3{
	clear: both;
	margin: 10px 0px 3px 0px;
	padding: 0px;
	font-size: 13px;
	color:#666666;
}	
h3.err_title{
	clear:none;
}
h4{
	clear: both;
	margin: 5px 0px 3px 0px;
	padding: 0px 0px 0px 10px;
	font-weight: normal;
}
.btn_box{
	margin:5px 5px 10px 0px;
	clear: both;
	text-align: right;
	width:100%;
}
a, a:VISITED {
	color: #333333;
}
a:hover{
	color: #ff6600;
}
</style>
<!--system css  -->
