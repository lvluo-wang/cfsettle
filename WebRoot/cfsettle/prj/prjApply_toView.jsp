<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<%
    request.setAttribute("now", new java.util.Date());
%>
<tiles:insertDefinition name="FUNC_TOOL_FORM">
    <tiles:putAttribute name="tool">
        <x:button iconCls="icon-back" text="back" click="doReturn"/>
    </tiles:putAttribute>
    <tiles:putAttribute name="form">
        <style>
            div.car_loan {
                padding: 5px 10px 10px 10px;
            }

        </style>
        <div id="tt">
                <%--<%@ include file="/xhh/car/carLoanApply.ucars" %>--%>
            <div title="项目信息" class="car_loan">
                <form class="busi_form" id="prj_form">
                    <input type="hidden" value="${prj.id}" name="prj.id"/>
                    <table>
                        <colgroup>
                            <col width="20%"/>
                            <col width="30%"/>
                            <col width="20%"/>
                            <col width="30%"/>
                        </colgroup>
                        <tr>
                            <td style="text-align: center;" colspan="4"><b>项目基本信息</b></td>
                        </tr>

                        <tr>
                            <td class="title">项目名:</td>
                            <td>
                                ${prj.prjName}
                            </td>
                            <td class="title">项目方名:</td>
                            <td>
                                ${prj.prjUseName}
                            </td>
                        </tr>
                        <tr>
                            <td class="title">项目状态:</td>
                            <td>
                                <x:codeItem codeKey="<%=UtilConstant.CFS_PRJ_STATUS%>" codeNo="prj.status"/>
                            </td>
                            <td class="title">项目联系电话:</td>
                            <td>
                                ${prj.prjMobile}
                            </td>
                        </tr>
                        <tr>
                            <td class="title">募集金额:</td>
                            <td>${prj.demandAmount}万
                            </td>
                            <td class="title">项目期限:</td>
                            <td>
                               ${prj.timeLimit}
                                <x:codeItem codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT%>" codeNo="prj.timeLimitUnit"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">项目利率:</td>
                            <td>
                                ${prj.yearRate}
                                <span>%</span>
                            </td>
                            <td class="title">募集期利率:</td>
                            <td>
                                ${prj.periodRate}
                                <span>%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">还款方式:</td>
                            <td><x:codeItem codeKey="<%=UtilConstant.CFS_REPAYMENT_TYPE%>" codeNo="prj.repayWay" /></td>
                            <td class="title">项目成立金额:</td>
                            <td>${prj.minLoanAmount}万
                            </td>
                        </tr>
                        <tr>
                            <td class="title">融资开标时间：</td>
                            <td>
                                <s:date format="yyyy-MM-dd HH:mm:ss" name="prj.startBidTime"/>
                            </td>
                            <td class="title">融资截标时间：</td>
                            <td>
                                <s:date format="yyyy-MM-dd HH:mm:ss" name="prj.endBidTime"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">回款截止时间：</td>
                            <td colspan="3">
                                <s:date format="yyyy-MM-dd HH:mm:ss" name="prj.repayEndTime"/>
                            </td>
                        </tr>
                    </table>
                </form>
                <form class="busi_form" id="prjExt_form">
                    <input type="hidden" value="${prjExt.prjId}" name="prjExt.prjId"/>
                    <table>
                        <colgroup>
                            <col width="20%"/>
                            <col width="30%"/>
                            <col width="20%"/>
                            <col width="30%"/>
                        </colgroup>
                        <tr>
                            <td class="title">收款账户名:</td>
                            <td>
                                ${prjExt.tenantName}
                            </td>
                            <td class="title">收款银行:</td>
                            <td colspan="3">
                                <x:codeItem codeKey="<%=UtilConstant.CFS_BANK_TYPE%>" codeNo="prjExt.tenantBank"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">收款支行:</td>
                            <td>
                                ${prjExt.subBank}
                            </td>
                            <td class="title">收款账号:</td>
                            <td>
                                ${prjExt.accountNo}
                            </td>
                        </tr>
                    </table>
                </form>

                <%--//todo--%>
                <h3>放款记录</h3>
                <div>
                    <x:datagrid id="dataTableIssue" url="" height="120" pagebar="false" autoload="" >
                        <x:columns>
                            <x:column title="放款次数" field="operator" align="center" width="150" />
                            <x:column title="放款时间" field="ctime" align="center" width="150"/>
                            <x:column title="放款金额" field="remark" align="center" width="200" />
                            <x:column title="已放款总额" field="remark" align="center" width="200" />
                            <x:column title="剩余待放款" field="remark" align="center" width="200" />
                            <x:column title="放款审核人" field="remark" align="center" width="200" />
                        </x:columns>
                    </x:datagrid>
                </div>
                    <%--//todo--%>
                <h3>回款记录</h3>
                <div>
                    <x:datagrid id="dataTableRepay" url="" height="120" pagebar="false" autoload="" >
                        <x:columns>
                            <x:column title="回款次数" field="operator" align="center" width="150" />
                            <x:column title="回款时间" field="ctime" align="center" width="150"/>
                            <x:column title="回款金额" field="remark" align="center" width="200" />
                            <x:column title="已回款总额" field="remark" align="center" width="200" />
                            <x:column title="剩余待回款" field="remark" align="center" width="200" />
                            <x:column title="回款截止时间" field="remark" align="center" width="200" />
                            <x:column title="回款审核人" field="remark" align="center" width="200" />
                        </x:columns>
                    </x:datagrid>
                </div>

            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">


            $(function () {
                $('#tt').css("height", $(document.body).height() - 50).tabs({});
                //changePercent($("input[name='carLoan.rateType']")[0].value);
                //addUploadTemplate();

            });


            function doReturn() {
                window.history.go(-1);
            }

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>