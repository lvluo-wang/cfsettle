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
                        </tr>
                        <tr>
                            <td class="title">募集金额:</td>
                            <td>${prj.demandAmount}万</td>
                            <td class="title">项目期限:</td>
                            <td>
                            	${prj.timeLimit}
                                <x:codeItem codeNo="prj.timeLimitUnit" codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT %>"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">项目利率:</td>
                            <td>
                               ${prj.yearRate}%
                            </td>
                            <td class="title">募集期利率:</td>
                            <td>
                                ${prj.periodRate}%
                            </td>
                        </tr>
                        <tr>
                            <td class="title">还款方式:</td>
                            <td><x:codeItem codeNo="prj.repayWay" codeKey="<%=UtilConstant.CFS_REPAYMENT_TYPE %>"/></td>
                            <td class="title">项目成立金额:</td>
                            <td>${prj.minLoanAmount}万</td>
                        </tr>
                        <tr>
                            <td class="title">融资开标时间：</td>
                            <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prj.startBidTime"/></td>
                            <td class="title">融资截标时间：</td>
                            <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prj.endBidTime"/></td>
                        </tr>
                        </table>
                    </form>
                    <form class="busi_form" id="prjExt_form">
                        <table>
                            <colgroup>
                                <col width="15%"/>
                                <col width="30%"/>
                                <col width="15%"/>
                                <col width="30%"/>
                            </colgroup>
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
                            <td style="text-align: center;" colspan="4"><b>放款信息账户</b></td>
                        </tr>
                      	<tr>
                      		<td class="title">放款时间:</td>
                      		<td>
                      			<input class="Wdate easyui-validatebox" type="text" required="true" name="prjLoanLog.loanTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                      			<input name="prjLoanLog.prjId" value="${prj.id}" type="hidden"/>
                      			<input name="prjLoanLog.prjName" value="${prj.prjName}" type="hidden"/>
                      		</td>
                      		<td class="title">放款金额:</td>
                      		<td><input name="prjLoanLog.loanAmount" class="easyui-validatebox" required="true" validType="money"/>元</td>
                      		
                        </tr>
                        <tr>
                            <td class="title">放款账户名:</td>
                            <td><input name="prjLoanLog.loanAccountName"  class="easyui-validatebox" required="true"/></td>
                            <td class="title">放款银行:</td>
                            <td colspan="3">
                                <x:combobox name="prjLoanLog.loanBankName" list="bankList" textField="codeName" valueField="codeNo" required="true" pleaseSelect="false" cssStyle="142px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">放款卡号:</td>
                            <td><input name="prjLoanLog.loanAccountNo" class="easyui-validatebox" required="true"/></td>
                            <td class="title">放款流水号:</td>
                            <td><input name="prjLoanLog.loanSerialNum" class="easyui-validatebox" required="true"/></td>
                        </tr>
                        <tr>
                            <td style="text-align: center;" colspan="4">
                                <x:button iconCls="icon-audit" text="提交" click="doLoanAdd" effect="round"/>
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