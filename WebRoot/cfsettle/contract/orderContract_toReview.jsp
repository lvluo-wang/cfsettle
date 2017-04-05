<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>
<%
    request.setAttribute("now", new java.util.Date());
%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
    <tiles:putAttribute name="form">
        <form class="busi_form" id="prjOrderAudit_form">
            <input type="hidden" value="${prjOrder.id}" name="prjOrder.id">
            <table>
                <colgroup>
                    <col width="20%"/>
                    <col width="30%"/>
                    <col width="20%"/>
                    <col width="30%"/>
                </colgroup>
                <tr>
                    <td class="title">合同编号</td>
                    <td>${prjOrder.contractNo}</td>
                    <td class="title">投资项目</td>
                    <td>${prj.prjName}</td>
                </tr>
                <tr>
                    <td class="title">客户姓名</td>
                    <td>${cust.realName}</td>
                    <td class="title">客户手机</td>
                    <td>${cust.mobile}</td>
                </tr>
                <tr>
                    <td class="title">投资金额</td>
                    <td>${prjOrder.money}元</td>
                    <td class="title">投资时间</td>
                    <td><s:date name="prjOrder.investTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td class="title">付款银行</td>
                    <td><x:codeItem codeKey="<%=UtilConstant.CFS_BANK_TYPE%>" codeNo="prjOrder.payBank"/></td>
                    <td class="title">付款卡号</td>
                    <td>${prjOrder.payAccountNo}</td>
                </tr>
                <tr>
                    <td calss="title">审核备注</td>
                    <input type="hidden" name="prjOrderAuditLog.status" id="auditStatus"/>
                    <td colspan="3">
                        <textarea name="prjOrderAuditLog.remark" class="easyui-validatebox" required="true" cols="30"
                                  style="width: 100%" id="verifyDesc"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="button">
        <x:button id="save" iconCls="icon-save" text="审核通过" click="reviewPass" effect="round"/>
        <x:button iconCls="icon-cancel" text="审核驳回" click="reviewReject" effect="round"/>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">

            function reviewPass() {
                //通过
                $('#auditStatus').val(1);
                if ($("#prjOrderAudit_form").form("validate")) {
                    var url = '<s:url value="/contract/contract_doReview.jhtml"/>';
                    var param = formToObject("prjOrderAudit_form");
                    doPost(url, param, function (result) {
                        if (!printError(result)) {
                            closeWindow("project_add_win");
                            info("提交成功");
                        }
                    });
                }
            }

            function reviewReject() {
                //驳回
                $('#auditStatus').val(2);
                if ($("#prjOrderAudit_form").form("validate")) {
                    var url = '<s:url value="/contract/contract_doReview.jhtml"/>';
                    var param = formToObject("prjOrderAudit_form");
                    doPost(url, param, function (result) {
                        if (!printError(result)) {
                            closeWindow("project_add_win");
                            info("提交成功");
                        }
                    });
                }
            }


        </script>
    </tiles:putAttribute>

</tiles:insertDefinition>