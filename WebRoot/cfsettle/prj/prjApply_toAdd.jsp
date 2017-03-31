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
            <div title="发布信息" class="car_loan">
                <form class="busi_form" id="prj_form">
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
                                <input id="prjName" class="easyui-validatebox" name="prj.prjName"
                                       value="${prj.prjName}"
                                       required="true"/>
                            </td>
                            <td class="title">项目方名:</td>
                            <td>
                                <input id="prjUseName"
                                       class="easyui-validatebox"
                                       name="prj.prjUseName"
                                       required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">项目联系电话:</td>
                            <td>
                                <input name="prjMobile" value="${prj.prjMobile}"
                                       class="easyui-validatebox" required="true" validType="tel" />
                            </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td class="title">募集金额:</td>
                            <td><input name="prj.demandAmount" class="easyui-validatebox" validType="positive_int" required="true" />万
                            </td>
                            <td class="title">项目期限:</td>
                            <td>
                                <input name="prj.timeLimit" value="${prj.timeLimit}"
                                       class="easyui-validatebox" required="true" validType="positive_int"/>
                                <x:combobox name="prj.timeLimitUnit" value="${prj.timeLimitUnit}"
                                            list="timeLimitUnitList" textField="codeName" valueField="codeNo"
                                            cssStyle="width:30px;" required="true" pleaseSelect="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">项目利率:</td>
                            <td>
                                <input id="prjYearRate" name="prj.yearRate" value="${prj.yearRate}"
                                       class="easyui-validatebox" required="true" validType="percentage"/>
                                <span>%</span>
                            </td>
                            <td class="title">募集期利率:</td>
                            <td>
                                <input id="periodRate" name="prj.periodRate" value="${prj.periodRate}"
                                       class="easyui-validatebox" required="true" validType="percentage"/>
                                <span>%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">还款方式:</td>
                            <td><x:combobox name="prj.repayWay" value="${prj.repayWay}" list="repaymentTypeList"
                                            textField="codeName" valueField="codeNo" required="true"
                                            pleaseSelect="false"/></td>
                            <td class="title">项目成立金额:</td>
                            <td><input name="prj.minLoanAmount" class="easyui-validatebox" required="true" />万
                            </td>
                        </tr>
                        <tr>
                            <td class="title">融资开标时间：</td>
                            <td>
                                <input class="Wdate easyui-validatebox" id="start_time"
                                       type="text" required="true" name="prj.startBidTime"
                                       value='<s:date name="prj.startBidTime" format="yyyy-MM-dd HH:mm:00"/>'
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})"/>
                            </td>
                            <td class="title">融资截标时间：</td>
                            <td>
                                <input class="Wdate easyui-validatebox" id="end_time"
                                       type="text" required="true" name="prj.endBidTime"
                                       value='<s:date name="prj.endBidTime" format="yyyy-MM-dd HH:mm:00"/>'
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})"/>
                            </td>
                        </tr>
                        </table>
                    </form>
                    <form class="busi_form" id="prjExt_form">
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
                                <input name="prjExt.tenantName" class="easyui-validatebox" required="true" />
                            </td>
                            <td class="title">收款银行:</td>
                            <td colspan="3">
                                <x:combobox name="prjExt.tenantBank" value="${prj.tenantBank}"
                                            list="bankList" textField="codeName" valueField="codeNo"
                                             required="true" pleaseSelect="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">收款支行:</td>
                            <td >
                                <input name="prjExt.sub_bank" class="easyui-validatebox" required="true" />
                            </td>
                            <td class="title">收款账号:</td>
                            <td >
                                <input name="prjExt.accountNo" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>

                        <tr>
                            <td style="text-align: center;" colspan="4"><b>项目附加信息</b></td>
                        </tr>
                        <tr>
                            <td class="title">电子合同: </td>
                            <td colspan="3">
                                <span id="contractName"/>
                                <input type="hidden" name="prjExt.contractAttid" class="" required="true" />
                                <br/><br/>
                                <s:include value="/platform/common/uploadFile.jsp">
                                    <s:param name="refresh">y</s:param>
                                    <s:param name="suffix">1</s:param>
                                    <s:param name="imgServer">false</s:param>
                                    <s:param name="nowater">1</s:param>
                                    <s:param name="callback">uploadFileCallBack</s:param>
                                    <s:param name="opt">{'fileExt':'*.pdf','fileDesc':'pdf文件'}</s:param>
                                </s:include>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">募资说明: </td>
                            <td colspan="3">
                                <span id="period"/>
                                <input type="hidden" name="prjExt.periodAttid" class="" required="true" />
                                <br/><br/>
                                <s:include value="/platform/common/uploadFile.jsp">
                                    <s:param name="refresh">y</s:param>
                                    <s:param name="suffix">1</s:param>
                                    <s:param name="imgServer">false</s:param>
                                    <s:param name="nowater">1</s:param>
                                    <s:param name="callback">uploadFileCallBack</s:param>
                                    <s:param name="opt">{'fileExt':'*.pdf','fileDesc':'pdf文件'}</s:param>
                                </s:include>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">担保手续费: </td>
                            <td colspan="3">
                                <span id="guarante"/>
                                <input type="hidden" name="prjExt.guaranteAttid" class="" required="true" />
                                <br/><br/>
                                <s:include value="/platform/common/uploadFile.jsp">
                                    <s:param name="refresh">y</s:param>
                                    <s:param name="suffix">1</s:param>
                                    <s:param name="imgServer">false</s:param>
                                    <s:param name="nowater">1</s:param>
                                    <s:param name="callback">uploadFileCallBack</s:param>
                                    <s:param name="opt">{'fileExt':'*.pdf','fileDesc':'pdf文件'}</s:param>
                                </s:include>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">推广资料: </td>
                            <td colspan="3">
                                <span id="spread"/>
                                <input type="hidden" name="prjExt.spreadAttid" class="" required="true" />
                                <br/><br/>
                                <s:include value="/platform/common/uploadFile.jsp">
                                    <s:param name="refresh">y</s:param>
                                    <s:param name="suffix">1</s:param>
                                    <s:param name="imgServer">false</s:param>
                                    <s:param name="nowater">1</s:param>
                                    <s:param name="callback">uploadFileCallBack</s:param>
                                    <s:param name="opt">{'fileExt':'*.pdf','fileDesc':'pdf文件'}</s:param>
                                </s:include>
                            </td>
                        </tr>

                        <tr>
                            <td style="text-align: center;" colspan="4">
                                <x:button iconCls="icon-audit" text="提交" click="doApply" effect="round"/>
                            </td>
                        </tr>
                    </table>
                </form>

            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script id="signTemplate" type="text/x-jsrender">
			<tr id="tr{{:index}}">
				<td class="title">签章序列</td>
                <td >
                {{if index == 1}}
					<input name="" value="首签" style="width:100px;" readonly="readonly"/>
                {{/if}}
				{{if index == 2}}
					<input name="" value="二签" style="width:100px;" readonly="readonly"/>
                {{/if}}
				{{if index == 3}}
					<input name="" value="三签" style="width:100px;" readonly="readonly"/>
                {{/if}}
				{{if index == 4}}
					<input name="" value="四签" style="width:100px;" readonly="readonly"/>
                {{/if}}
				{{if index == 5}}
					<input name="" value="五签" style="width:100px;" readonly="readonly"/>
                {{/if}}
                </td>
				<td class="title">签章坐标</td>
				<td>
					<input name="" value="" style="width:100px;" class="easyui-validatebox" required="true"/>
				</td>
			</tr>

        </script>

        <script type="text/javascript">


            $(function () {
                $('#tt').css("height", $(document.body).height() - 50).tabs({});

                changePercent($("input[name='carLoan.rateType']")[0].value);


            });

            function guarantorCallBack(row) {
                if (row) {
                    $("#publish_instId_add").val(row.id);
                    $("#publish_instId_name_add").val(row.title);
                } else {
                    $("#publish_instId_add").val("");
                    $("#publish_instId_name_add").val("");
                }
            }

            var signIndex = 1;
            function addSign() {
                if (signIndex > 5) {
                    error("最大为五个签章");
                    return;
                }

                var signObj = {
                    index: signIndex
                };

                $("#signBody").append($("#signTemplate").render(signObj));

                signIndex++;
            }
            function delSign() {
                if (signIndex < 2) {
                    error("没有删除项目！");
                    return;
                }

                signIndex--;

                $("#tr" + signIndex).remove();
            }

            function doReturn() {
                window.history.go(-1);
            }

            function changePercent(rateType) {
                if ("day" == rateType) {
                    $("#ratePercent").html("‰");
                } else {
                    $("#ratePercent").html("%");
                }
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

            function doApply() {
                if ($("#bid_form").form("validate") && $("#busi_form").form("validate")) {
                    //校验预期利率的范围(和fi_risk_credit的package_price比较)
                    var minBidAmount = $("#carLoan_minBidAmount").attr("value");
                    var maxBidAmount = $("#carLoan_maxBidAmount").attr("value");
                    if (eval(minBidAmount) > eval(maxBidAmount)) {
                        info("投资起始金额不能大于最大投资金额!");
                        return false;
                    }


                    var url = '<s:url value="/prj/prjManage_doApply.jhtml"/>';
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
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>