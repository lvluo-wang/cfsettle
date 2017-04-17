<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page import="com.upg.cfsettle.util.CfsConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <td class="title">项目联系电话:</td>
                            <td>
                                ${prj.prjMobile}
                            </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td class="title">募集金额:</td>
                            <td>${prj.demandAmount}
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
                            <td>${prj.minLoanAmount}
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
                        <%--<tr>--%>
                            <%--<td class="title" >选择-省份:</td>--%>
                            <%--<td >--%>
                                <%--<x:combobox id="edit_province_id" readOnly="true" name="prjExt.provinceId" onchange="changeProvince" value="${prjExt.provinceId}"  required="true" valueField="id" textField="nameCn"/>--%>
                            <%--</td>--%>
                            <%--<td class="title" >选择-城市:</td>--%>
                            <%--<td >--%>
                                <%--<x:combobox id="edit_city_id" readOnly="true" name="prjExt.cityId" onchange="changeCity" value="${prjExt.cityId}" required="true" valueField="id" textField="nameCn"/>--%>
                            <%--</td>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                        <%--<script type="text/javascript">--%>

                            <%--$("#edit_province_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getProvinces.jhtml"/>'});--%>
                            <%--//编辑的时候加下面两条语句--%>
                            <%--$("#edit_city_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid=${prjExt.provinceId}'});--%>

                            <%--function changeProvince(provinceId){--%>
                                <%--if(provinceId){--%>
                                    <%--$("#edit_city_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid='+provinceId});--%>
                                <%--}--%>

                            <%--}--%>
                        <%--</script>--%>
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

                        <tr>
                            <td style="text-align: center;" colspan="4"><b>项目附加信息</b></td>
                        </tr>
                        <tr>
                            <td class="title">电子合同:</td>
                            <td><div id="uploadName_1">
                                <s:if test="prjExt.contractAttid !=null">
                                <a href='#' class='_attach_info'
                                   onclick='_downloadFile("${prjExt.contractAttid}")'>${prjExt.contractName}</a>
                                </a>
                                </s:if>
                            </div></td>
                            <td colspan="3">
                                <input type="hidden" id="uploadFile_1" value="${prjExt.contractAttid}" name="prjExt.contractAttid" />
                            </td>
                        </tr>
                        <tr>
                            <td class="title">募资说明:</td>
                            <td><div id="uploadName_2">
                                <s:if test="prjExt.periodAttid != null">
                            <a href='#' class='_attach_info'
                               onclick='_downloadFile("${prjExt.periodAttid}")'>${prjExt.periodName}</a>
                                </s:if>
                            </div>
                            </td>
                            <td colspan="2">
                                <input type="hidden" id="uploadFile_2" value="${prjExt.periodAttid}" name="prjExt.periodAttid" />
                            </td>
                        </tr>
                        <tr>
                            <td class="title">担保手续费:</td>
                            <td><div id="uploadName_3">
                                 <s:if test="prjExt.guaranteAttid != null">
                                <a href='#' class='_attach_info'
                                   onclick='_downloadFile("${prjExt.guaranteAttid}")'>${prjExt.guaranteName}</a>
                                 </s:if>
                            </div></td>
                            <td colspan="2">
                                <input type="hidden" id="uploadFile_3" value="${prjExt.guaranteAttid}" name="prjExt.guaranteAttid" />
                            </td>
                        </tr>
                        <tr>
                            <td class="title">推广资料:</td>
                            <td><div id="uploadName_4">
                                  <s:if test="prjExt.spreadAttid != null">
                                 <a href='#' class='_attach_info'
                                    onclick='_downloadFile("${prjExt.spreadAttid}")'>${prjExt.spreadName}</a>
                                  </s:if>
                            </div></td>
                            <td colspan="2">
                                <input type="hidden" id="uploadFile_4" value="${prjExt.spreadAttid}" name="prjExt.spreadAttid" />
                            </td>
                        </tr>

                        <tr>
                            <td class="title">佣金总比例:</td>
                            <td colspan="3">
                                <input name="prj.totalRate" id="totalRate" value="${prj.totalRate}" class="easyui-validatebox" required="true"
                                       validType="compareSum(['areaRate','deptRate','teamRate','sysuserRate'])"
                                        />
                                <span>%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">区域经理佣金计提:</td>
                            <td>
                                <input name="prj.areaRate" id="areaRate" value="${prj.areaRate}" class="easyui-validatebox" validType="positive_percent" required="true"/>
                                <span>%</span>
                            </td>
                            <td class="title">营业部佣金计提:</td>
                            <td>
                                <input name="prj.deptRate" id="deptRate" value="${prj.deptRate}" class="easyui-validatebox" validType="positive_percent" required="true"/>
                                <span>%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">团队佣金计提:</td>
                            <td>
                                <input name="prj.teamRate" id="teamRate" value="${prj.teamRate}" class="easyui-validatebox" validType="positive_percent" required="true"/>
                                <span>%</span>
                            </td>
                            <td class="title">客户经理佣金计提:</td>
                            <td>
                                <input name="prj.sysuserRate" id="sysuserRate" value="${prj.sysuserRate}" class="easyui-validatebox" validType="positive_percent" required="true"/>
                                <span>%</span>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center;" colspan="4"><b>审核意见</b></td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <textarea name="prj.remark" cols="30"  style="width: 100%" id="verifyDesc"></textarea>
                            </td>
                        </tr>

                        <tr>
                            <td style="text-align: center;" colspan="4">
                                <x:button iconCls="icon-audit" text="审核通过" click="doApplyReview" effect="round"/>
                                <x:button iconCls="icon-cancel" text="审核驳回" click="doApplyReject" effect="round"/>
                            </td>
                        </tr>
                    </table>
                </form>

            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
            $(function () {
                $('#tt').css("height", $(document.body).height() - 50).tabs({});
            });

            function doReturn() {
                window.history.go(-1);
            }

            function AddRunningDiv(str) {
                $("<div class=\"datagrid-mask\"></div>").css({
                    display: "block",
                    width: "100%",
                    height: $(document).height()
                }).appendTo("body");
                $("<div class=\"datagrid-mask-msg\"></div>").html(str).appendTo("body").css({
                    display: "block",
                    left: ($(document.body).outerWidth(true) - 190) / 2,
                    top: ($(document).height() - 45) / 2
                });
            }

            function doApplyReview() {
                if ($("#prj_form").form("validate") && $("#prj_Ext").form("validate")) {
                    var verifyDesc = $("#verifyDesc").val();
                    if(verifyDesc == ''){
                        info("请输入审核意见");
                        return;
                    }
                    var url = '<s:url value="/prj/prjAudit_doReview.jhtml"/>';
                    var param1 = formToObject("prj_form");
                    var param2 = formToObject("prjExt_form");
                    var param = $.extend(param1, param2);
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

            function doApplyReject() {
                var verifyDesc = $("#verifyDesc").val();
                if(verifyDesc == ''){
                    info("请输入审核意见");
                    return;
                }
                var url = '<s:url value="/prj/prjAudit_doReviewReject.jhtml"/>';
                var param1 = formToObject("prj_form");
                var param2 = formToObject("prjExt_form");
                var param = $.extend(param1, param2);
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
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>