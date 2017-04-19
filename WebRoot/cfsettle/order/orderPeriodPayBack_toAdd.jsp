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
		                    <td class="title">投资项目:</td>
		                    <td>${prj.prjName}</td>
			                <td class="title">项目期限:</td>
		                    <td>
		                    	${prj.timeLimit}
		                        <x:codeItem codeNo="prj.timeLimitUnit" codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT %>"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="title">募集期利息:</td>
		                    <td>${prj.periodRate}%</td>
		                    <td class="title">投资时间:</td>
		                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prjOrder.investTime"/></td>
		                </tr>
		                <tr>
		                    <td class="title">投资金额(元):</td>
		                    <td>${prjOrder.money}</td>
		                    <td class="title">计息天数:</td>
		                    <td>${orderRepayPlan.countDay}</td>
		                </tr>
		                <tr>
		                    <td class="title">待付利息(元):</td>
		                    <td>${orderRepayPlan.priInterest}</td>
		                </tr>
                    </table>
                </form>
                <form class="busi_form" id="payack_form">
                    <table>
                        <colgroup>
		                    <col width="15%"/>
		                    <col width="30%"/>
		                    <col width="15%"/>
		                    <col width="30%"/>
		                </colgroup>
		                <tr>
		                    <td style="text-align: left;" colspan="4"><b>客户收款银行信息</b></td>
		                </tr>
		                <tr>
		                    <td class="title">收款账户名:</td>
		                    <td>${cfsCust.realName}</td>
		                    <td class="title">收款银行:</td>
		                    <td>
		                    	<x:codeItem codeNo="prjOrder.payBank" codeKey="<%=UtilConstant.CFS_BANK_TYPE%>"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="title">收款支行:</td>
		                    <td>${prjOrder.paySubBank}</td>
		                    <td class="title">收款卡号:</td>
		                    <td>${prjOrder.payAccountNo}</td>
		                </tr>
		                <tr>
		                    <td style="text-align: left;" colspan="4"><b>公司付款账户信息</b></td>
		                </tr>
		                <tr>
		                    <td class="title">付款时间:</td>
		                    <td>
		                    	<input class="Wdate easyui-validatebox" type="text" required="true" name="orderPayLog.paybackTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                      			<input name="orderPayLog.prjId" value="${prj.id}" type="hidden"/>
                      			<input name="orderPayLog.prjName" value="${prj.prjName}" type="hidden"/>
                      			<input name="orderPayLog.prjOrderId" value="${prjOrder.id}" type="hidden"/>
                      			<input name="orderPayLog.prjOrderRepayPlanId" value="${orderRepayPlan.id}" type="hidden"/>
                      			<input name="orderPayLog.paybackTimes" value="${repayPlan.repayPeriods}" type="hidden"/>
		                    </td>
		                    <td class="title">付款金额：</td>
		                    <td><input name="orderPayLog.paybackAmount" class="easyui-validatebox" required="true" validType="money"/>元</td>
		                </tr>
		                <tr>
		                    <td class="title">付款银行:</td>
		                    <td>
								<x:combobox name="orderPayLog.paybackBank" class="easyui-validatebox" list="bankTypes" textField="codeName" valueField="codeNo" required="true" pleaseSelect="false" cssStyle="width:142px;"/>
							</td>
		                    <td class="title">付款卡号:</td>
		                    <td><input name="orderPayLog.paybackAccountNo"  class="easyui-validatebox" required="true"/></td>
		                </tr>
		                <tr>
		                	<td class="title">付款支行:</td>
		                    <td><input name="orderPayLog.paybackSubBank" class="easyui-validatebox" required="true"/></td>
		                    <td class="title">资金流水编号:</td>
		                    <td><input name="orderPayLog.paybackSerialNum" class="easyui-validatebox" required="true"/></td>
		                </tr>
		                 <tr>
		                    <td class="title">备注</td>
		                    <td colspan="3">
		                        <textarea name="orderPayLog.remark" class="easyui-validatebox" required="true" cols="30" style="width: 70%"></textarea>
		                    </td>
		                </tr>
		                <tr>
		                	<td style="text-align: right;" colspan="2">
                                <x:button iconCls="icon-audit" text="提交" click="doPaybackAdd" effect="round"/>
                            </td>
                            <td style="text-align: left;" colspan="2">
                                <x:button iconCls="icon-cancel" text="取消" click="doReturn" effect="round"/>
                            </td>
		                </tr>
                    </table>
                </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">

        <script type="text/javascript">
            function doReturn() {
                window.history.go(-1);
            }
            
            function doPaybackAdd() {
                if ($("#payack_form").form("validate")) {
                    var url = '<s:url value="/order/orderPeriod_doAdd.jhtml"/>';
                    var param = formToObject("payack_form");
                    AddRunningDiv("提交处理中，请稍候...");
                    doPost(url, param, function (result) {
                        if (!printError(result)) {
                            setTimeout("history.back()", 2000);
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