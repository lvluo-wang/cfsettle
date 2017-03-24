<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<%@ page import="java.util.Random"%>
<%@ page import="com.upg.ubsp.constant.ComDictKeyConst"%>
<%
	StringBuilder sb = new StringBuilder("{");
	sb.append("\"entityId\":\"").append(request.getParameter("entityId") == null ? "0" : request.getParameter("entityId")).append("\"");
	sb.append(",\"processName\":\"").append(request.getParameter("processName") == null ? "0" : request.getParameter("processName")).append("\"");
	String processInstanceId = request.getParameter("processInstanceId");
	if (processInstanceId != null && processInstanceId.length() > 0) {
		sb.append(",\"ib.instanceId\":\"").append(processInstanceId).append("\"");
	}
	String instanceBusinessId = request.getParameter("instanceBusinessId");
	if (instanceBusinessId != null && instanceBusinessId.length() > 0 ) {
		sb.append(",\"ib.id\":\"").append(instanceBusinessId).append("\"");
	}
	sb.append("}");
	String suffix = request.getParameter("suffix") == null ? ""+new Random().nextInt(100) : (String)request.getParameter("suffix");
%>
<div style="border:#99bbe8 1px solid;width:%;">
<x:datagrid id='<%="task_history_dataTable"+suffix%>' url="/bpm/task_getTaskHistory.jhtml" params="<%=sb.toString()%>" autoload="true" pagebar="false" autoFit="true">
	<x:columns>
		<x:column title="审批环节"  field="taskName" align="left" width="120" formatter='<%="formatTaskName" + suffix%>'/>
		<x:column title="审批结果"  field="isAgree" align="left" width="60" formatter="formatAgree"/>
		<x:column title="意见"  field="remark" align="left" width='<%="_project".equals(suffix) ? 500 : 300 %>' formatter='<%="appendBusinessMessage"+suffix%>'/>
		<x:column title="处理人" field="dealUserName" align="left" width="80" formatter='<%="formatterDealUserName"+suffix%>' />
		<x:column title="处理时间" field="dealTime" align="left" width="100" formatter="format2Minute"/>
	</x:columns>
</x:datagrid>
</div>
<script>
	var <%="task_history_code_keys"+suffix%>=["<%=ComDictKeyConst.APPROVAL_OPINION%>"];
	var <%="task_history_code"+suffix%>=new CodeUtil(<%="task_history_code_keys"+suffix%>);
	<%="task_history_code"+suffix%>.loadData();
	function formatTaskName<%=suffix%>(val,field,row){
		var result = val;
		if (row.isAgree == '-2') {
			result = '<div style="color:#BBB;">' + val + '</div>';
		}
		return result;
	}
	function appendBusinessMessage<%=suffix%>(val,id,row,i){
		var tmp = val;
		var bfm = row.businessFormatMessage;
		if (bfm) {
			tmp = bfm + tmp;
		}
		if (tmp) {
			var endIndex = 300;
			if (tmp.length > endIndex) {
				var tmpTrim = tmp.replace(/<br\/>/g,'').replace(/&nbsp;/g,'').replace(/<[^<]*>/g,'');
				if (tmpTrim.length > endIndex) {
					var linkMap = {};
					var subfix = '......<a href="javascript:void(0);" onclick="extendRemark<%=suffix%>(event,this,' + i +')">[ 展开 ]</a>';
					tmp = tmp.replace(/<br\/>/g,'%b%').replace(/<\/br>/g,'%%').replace(/&nbsp;/g,'&&');
					/* .replace(/<[^<]*>/g,'');  */
					var i = 0;
					tmp=tmp.replace(/<a\s*[^>]*>/g,function(link){
						var result = "@" + i + "@"
						linkMap[result] = link;
						i++;
						return result;
					}).replace(/<\/a>/g,"@@").replace(/<[^<]*>/g,'');
					
					while(tmp.charAt(endIndex) == '%' || tmp.charAt(endIndex) == '&' || tmp.charAt(endIndex) == '@'){
						endIndex++;
					} 
					tmp = tmp.substring(0,endIndex);
					tmp = tmp.replace(/%b%/g,'<br/>').replace(/&&/g,'&nbsp;').replace(/@@/g,"</a>");
					for (l in linkMap) {
						tmp = tmp.replace(l,linkMap[l]);
					}
					tmp = tmp + subfix;
				}
			}
		}
		return tmp;
	}
	function formatAgree(value,field,row){
		var ret = row.approvalOpinion;
		if (!ret) {
			ret = (value == '1' ? "通过" : (value == '-1'? "处理中" : (value == '0' ? "驳回" : (value == '-2' ? "未处理" : value))));
		}else{
			ret = <%="task_history_code"+suffix%>.getValue("<%=ComDictKeyConst.APPROVAL_OPINION%>", ret);
			if (value == '0') {
				ret += "(驳回)";
			}
		}
		if (row.isAgree == '-2') {
			ret = '<div style="color:#BBB;">' + ret + '</div>';
		}
		return ret
	}
	function extendRemark<%=suffix%>(event,obj,i){
		var row = <%="task_history_dataTable"+suffix%>.jsonData[i];
		var tmp = row.remark;
		if (row.businessFormatMessage) {
			tmp = row.businessFormatMessage + tmp;
		}
		tmp += '<a href="javascript:void(0);" onclick="shrinkRemark<%=suffix%>(event,this,' + i +')">[ 收起 ]</a>';
		$(obj).parent().html(tmp);
		if (event) {
			if(event.stopPropagation){
				event.stopPropagation();
			}else{
				event.cancelBubble = true;
			}
		}
	}
	function shrinkRemark<%=suffix%>(event,obj,i){
		var row = <%="task_history_dataTable"+suffix%>.jsonData[i];
		var tmp = appendBusinessMessage<%=suffix%>(row.remark,'remark',row,i);
		$(obj).parent().html(tmp);
		if (event) {
			if(event.stopPropagation){
				event.stopPropagation();
			}else{
				event.cancelBubble = true;
			}
		}
	}
	function formatterDealUserName<%=suffix%>(val,field,row){
		var result = val;
		if (row.isAgree == '-1'){
			if(result && !row.dealUserId){
				var arr = result.split('<br/>');
				var height = null;
				if (arr.length > 6) {
					height = '70';
				}
				result = '<div style="width:100%;';
				if (height) {
					result += 'height:' + height + 'px;overflow-y:auto;'
				}
				result += 'color:#BBB;">' + val + '</div>';
			}
		}else{
			if(row.delegateUser){
				result += "<br/>(委托办理)";
			}
		}
		return result;
	}
</script>