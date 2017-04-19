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
                    <td style="text-align: center;" colspan="4"><b>项目基本信息</b></td>
                </tr>
                <tr>
                    <td class="title">项目名:</td>
                    <td>${prj.prjName}</td>
                    <td class="title">项目方名:</td>
                    <td>${prj.prjUseName}</td>
                </tr>
                <tr>
                    <td class="title">项目联系电话:</td>
                    <td>${prj.prjMobile}</td>
                    <td class="title">项目期限:</td>
                    <td>
                    	${prj.timeLimit}
                        <x:codeItem codeNo="prj.timeLimitUnit" codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT %>"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">项目年化利率:</td>
                    <td>${prj.yearRate}%</td>
                    <td class="title">项目成立时间：</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prj.endBidTime"/></td>
                </tr>
                <tr>
                    <td class="title">实际募集金额(元):</td>
                    <td>${prj.demandAmount-prj.remainingAmount}</td>
                    <td class="title">已回款金额(元):</td>
                    <td>${prj.payBackAmount}</td>
                </tr>
                <tr>
                    <td class="title">剩余回款金额(元):</td>
                    <td>${prj.demandAmount-prj.remainingAmount-prj.payBackAmount}</td>
                    <td class="title">已回款期数:</td>
                    <td>${repayPlan.repayPeriods}/${totalPeriod}</td>
                </tr>
                <tr>
                    <td class="title">收款账户名:</td>
                    <td>${prjExt.tenantName}</td>
                    <td class="title">收款银行:</td>
                    <td><x:codeItem codeNo="prjExt.tenantBank" codeKey="<%=UtilConstant.CFS_BANK_TYPE %>"/></td>
                </tr>
                <tr>
                    <td class="title">收款支行:</td>
                    <td >${prjExt.subBank}</td>
                    <td class="title">收款账号:</td>
                    <td >${prjExt.accountNo}</td>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="4"><b>回款信息记录</b></td>
                </tr>
               </table>
            </form>
   		<div class="func_data_area">
   			<x:datagrid id="dataTableView" singleSelect="true" url="/prj/payBack_listPayBack.jhtml?paybackLog.prjId=${prj.id}" autoload="true">
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
	      	return "第"+val+"期";
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