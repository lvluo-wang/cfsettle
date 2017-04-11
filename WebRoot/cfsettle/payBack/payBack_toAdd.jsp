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
		                    <td class="title">已回款期数:</td>
		                    <td>Y/M</td>
		                </tr>
		                <tr>
		                    <td class="title">回款本息(元):</td>
		                    <td>${prj.payBackAmount}</td>
		                    <td class="title">回款截止时间:</td>
		                    <td>${prj.lastRepayTime}</td>
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
		                    <td style="text-align: left;" colspan="4"><b>借款人付款信息</b></td>
		                </tr>
		                <tr>
		                    <td class="title">付款账户名:</td>
		                    <td><input name="paybackLog.paybackAccountName"  class="easyui-validatebox" required="true"/></td>
		                    <td class="title">付款银行:</td>
		                    <td>
		                    	<x:combobox name="paybackLog.paybackBank" class="easyui-validatebox" list="bankList" textField="codeName" valueField="codeNo" required="true" pleaseSelect="false" cssStyle="width:142px;"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="title">付款卡号:</td>
		                    <td><input name="paybackLog.paybackAccountNo"  class="easyui-validatebox" required="true"/></td>
		                    <td colspan="2"></td>
		                </tr>
		                <tr>
		                    <td style="text-align: left;" colspan="4"><b>公司收款信息</b></td>
		                </tr>
		                <tr>
		                    <td class="title">收款时间:</td>
		                    <td>
		                    	<input class="Wdate easyui-validatebox" type="text" required="true" name="paybackLog.paybackTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                      			<input name="paybackLog.prjId" value="${prj.id}" type="hidden"/>
                      			<input name="paybackLog.prjName" value="${prj.prjName}" type="hidden"/>
                      			<input name="paybackLog.prjRepayPlanId" value="${repayPlan.id}" type="hidden"/>
                      			<input name="paybackLog.paybackTimes" value="${repayPlan.repayPeriods}" type="hidden"/>
		                    </td>
		                    <td class="title">收款金额：</td>
		                    <td><input name="paybackLog.paybackAmount" class="easyui-validatebox" required="true" validType="money"/>元</td>
		                </tr>
		                <tr>
		                    <td class="title">收款银行:</td>
		                    <td>
								<x:combobox name="paybackLog.collectionBank" class="easyui-validatebox" list="bankList" textField="codeName" valueField="codeNo" required="true" pleaseSelect="false" cssStyle="width:142px;"/>
							</td>
		                    <td class="title">收款卡号:</td>
		                    <td><input name="paybackLog.collectionAccountNo"  class="easyui-validatebox" required="true"/></td>
		                </tr>
		                <tr>
		                    <td class="title">资金流水编号:</td>
		                    <td><input name="paybackLog.paybackSerialNum" class="easyui-validatebox" required="true"/></td>
		                    <td colspan="2"></td>
		                </tr>
		                 <tr>
		                    <td class="title">备注</td>
		                    <td colspan="3">
		                        <textarea name="paybackLog.remark" class="easyui-validatebox" required="true" cols="30" style="width: 70%"></textarea>
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
                if ($("#prjExt_form").form("validate")) {
                    var url = '<s:url value="/prj/payBack_doPayBackAdd.jhtml"/>';
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