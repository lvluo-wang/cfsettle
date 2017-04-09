<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<tiles:insertDefinition name="FUNC_TOOL_FORM">
    <tiles:putAttribute name="tool">
        <x:button iconCls="icon-back" text="back" click="doReturn"/>
    </tiles:putAttribute>
    <tiles:putAttribute name="form">
        <form class="busi_form" id="prj_form">
            <table>
                <colgroup>
                    <col width="15%"/>
                    <col width="30%"/>
                    <col width="15%"/>
                    <col width="30%"/>
                </colgroup>
                <tr>
                    <td style="text-align: left;" colspan="4"><b>客户信息</b></td>
                </tr>
                <tr>
                    <td class="title">合同编号:</td>
                    <td>${prjOrder.contractNo}</td>
                    <td class="title">客户姓名:</td>
                    <td>${cfsCust.realName}</td>
                </tr>
                <tr>
                    <td class="title">服务员工姓名:</td>
                    <td>${buser.userName}</td>
                    <td class="title">服务员工手机:</td>
                    <td>${buser.userNo}</td>
                </tr>
                <tr>
                    <td class="title">所属营业部:</td>
                    <td>${prjOrder.ownedDeptName}</td>
                    <td class="title">投资项目:</td>
                    <td>${prj.prjName}</td>
                </tr>
                <tr>
	                <td class="title">项目期限:</td>
                    <td>
                    	${prj.timeLimit}
                        <x:codeItem codeNo="prj.timeLimitUnit" codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT %>"/>
                    </td>
                    <td class="title">募集期利息:</td>
                    <td>${prj.periodRate}%</td>
                </tr>
                <tr>
                    <td class="title">投资时间:</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prjOrder.investTime"/></td>
                    <td class="title">投资金额：</td>
                    <td>${prjOrder.money}</td>
                </tr>
                <tr>
                    <td class="title">计息天数:</td>
                    <td>${orderRepayPlan.countDay}</td>
                    <td class="title">待付利息(元):</td>
                    <td>${orderRepayPlan.priInterest}</td>
                </tr>
                <tr>
                    <td class="title">项目状态:</td>
                    <td><x:codeItem codeNo="prj.status" codeKey="<%=UtilConstant.CFS_PRJ_STATUS %>"/></td>
                    <td colspan="2"></td>
                </tr>
               </table>
            </form>
   		<div class="func_data_area">
   			<x:datagrid id="dataTableView" singleSelect="true" url="/prj/payBack_listPayBack.jhtml?payBackLog.prjId=${prj.id}" autoload="true">
				<x:columns>
					<x:column title="回款期数" field="REPAY_PERIODS" align="center" width="140" formatter="formatTimes"/>
					<x:column title="回款截止时间" field="REPAY_DATE" align="center" width="140" formatter="format2Date"/>
					<x:column title="实际回款时间" field="PAYBACK_TIME" align="center" width="120" formatter="format2Time"/>
					<x:column title="回款金额(元)" field="PAYBACK_AMOUNT" align="center" width="80"/>
					<x:column title="付款账户名" field="PAYBACK_BANK" align="center" width="80" formatter="formateBank"/>
					<x:column title="付款银行" field="PAYBACK_ACCOUNT_NAME" align="center" width="180"/>
					<x:column title="付款账号" field="PAYBACK_ACCOUNT_NO" align="center" width="180"/>
					<x:column title="资金流水号" field="PAYBACK_SERIAL_NUM" align="center" width="180"/>
					<x:column title="状态" field="STATUS" align="center" width="50" formatter="formattStatus"/>
					<x:column title="操作人" field="sysUserName" align="center" width="90"/>
					<x:column title="审核备注" field="REMARK" align="center" width="240"/>
				</x:columns>
			</x:datagrid>
		</div>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
     <script type="text/javascript">
     	$(function(){
     		dataTableView.setHeight(document.body.clientWidth-870);
     	});
	    var keys=["<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS%>"];
	 	var code=new XhhCodeUtil(keys);
	 	code.loadData();
	
	     function formateBank(value) {
	 		return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
	     }
	     function doReturn() {
	         window.history.go(-1);
	     }
	     
	     function formattStatus(val){
	    	 return code.getValue("<%=UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS%>",val);
	     }
	         
	      function formatTimes(val){
	      	if(val==0||val==""){
	      		return "募集期";
	      	}
	      	return "第"+val+"次";
	      }
	         
	      function doLoanAdd() {
	          if ($("#prjExt_form").form("validate")) {
	              var url = '<s:url value="/prj/prjLoan_doLoanAdd.jhtml"/>';
	              var param = formToObject("prjExt_form");
	              AddRunningDiv("提交处理中，请稍候...");
	              doPost(url, param, function (result) {
	                  if (!printError(result)) {
	                      setTimeout("history.back()", 3000);
	                      info("提交成功!");
	                  } else {
	                      $(".datagrid-mask").remove();
	                      $(".datagrid-mask-msg").remove();
	                  }
	              });
	          }
	      }
     </script>
    </tiles:putAttribute>
</tiles:insertDefinition>