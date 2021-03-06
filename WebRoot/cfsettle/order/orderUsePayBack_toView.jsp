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
                    <td class="title">所属客户经理:</td>
                    <td>${buser.userName}</td>
                    <td class="title">客户经理电话:</td>
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
                    <td class="title">投资时间:</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prjOrder.investTime"/></td>
                </tr>
                <tr>
                    <td class="title">投资金额(元):</td>
                    <td>${prjOrder.money}</td>
                    <td class="title">项目还款时间:</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prj.lastRepayTime"/></td>
                </tr>
                <tr>
                    <td class="title">还款本金(元):</td>
                    <td>${orderRepayPlan.principal}</td>
                    <td class="title">还款利息(元):</td>
                    <td>${orderRepayPlan.yield}</td>
                </tr>
                <tr>
                    <td class="title">还款本息(元):</td>
                    <td>${orderRepayPlan.priInterest}</td>
                    <td class="title">还款期数:</td>
                    <td>${orderRepayPlan.repayPeriods}/${orderRepayPlan.totalPeriods}</td>
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
	                </tr>
	            </table>
	        </form>
   		<div class="func_data_area">
   			<x:datagrid id="dataTableView" singleSelect="true" url="/order/orderUse_listUse.jhtml" autoload="true" form="form_for_query">
				<x:columns>
					<x:column title="预计还款时间" field="perPayDate" align="center" width="140" formatter="format2Date"/>
					<x:column title="实际还款时间" field="paybackTime" align="center" width="140" formatter="format2Time"/>
					<x:column title="还款期数" field="paybackTimes" align="center" width="140" formatter="formatPayTimes"/>
					<x:column title="还款金额(元)" field="paybackAmount" align="center" width="140"/>
					<x:column title="付款银行" field="paybackBank" align="center" width="180" formatter="formateBank"/>
					<x:column title="付款支行" field="paybackSubBank" align="center" width="120"/>
					<x:column title="付款账号" field="paybackAccountNo" align="center" width="180"/>
					<x:column title="资金流水编号" field="paybackSerialNum" align="center" width="180"/>
					<x:column title="操作人" field="sysUserName" align="center" width="90"/>
					<x:column title="操作备注" field="remark" align="center" width="240"/>
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
	         
	      function formatPayTimes(val,field,row){
	      	if(val==0||val==""){
	      		return "募集期";
	      	}
	      	return val+"/"+row.totalPays;
	      }
     </script>
    </tiles:putAttribute>
</tiles:insertDefinition>