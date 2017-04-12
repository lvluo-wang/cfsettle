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
                <tr>
                    <td style="text-align: left;" colspan="4"><b>客户收款银行信息</b></td>
                </tr>
                 <tr>
                    <td class="title">收款账户名:</td>
                    <td>${cfsCust.realName}</td>
                    <td class="title">收款银行：</td>
                    <td><x:codeItem codeNo="prjOrder.payBank" codeKey="<%=UtilConstant.CFS_BANK_TYPE %>"/></td>
                </tr>
                <tr>
                    <td class="title">收款支行:</td>
                    <td>${prjOrder.paySubBank}</td>
                    <td class="title">付款卡号:</td>
                    <td>${prjOrder.payAccountNo}</td>
                </tr>
               </table>
         </form>
         <form id="form_for_query">
            <table>
                <tr>
                    <td><input name="orderPayLog.prjOrderId" value="${prjOrder.id}" type="hidden"/></td>
                    <td><input name="orderPayLog.prjOrderRepayPlanId" value="${orderRepayPlan.id}" type="hidden"/></td>
                </tr>
            </table>
         </form>
   		<div class="func_data_area">
   			<x:datagrid id="dataTableView" singleSelect="true" url="/order/orderPeriod_listPeriod.jhtml" autoload="true" form="form_for_query">
				<x:columns>
					<x:column title="付息时间" field="paybackTime" align="center" width="140" formatter="format2Time"/>
					<x:column title="付息金额(元)" field="paybackAmount" align="center" width="140"/>
					<x:column title="付款支行" field="paybackSubBank" align="center" width="120"/>
					<x:column title="付款银行" field="paybackBank" align="center" width="180" formatter="formateBank"/>
					<x:column title="付款账号" field="paybackAccountNo" align="center" width="180"/>
					<x:column title="资金流水编号" field="paybackSerialNum" align="center" width="180"/>
					<x:column title="操作人" field="sysUserName" align="center" width="90"/>
					<x:column title="审核备注" field="remark" align="center" width="240"/>
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