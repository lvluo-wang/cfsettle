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
		                    <td class="title">员工名:</td>
		                    <td>${buser.userName}</td>
		                    <td class="title">员工手机:</td>
		                    <td>${buser.userNo}</td>
		                </tr>
		                <tr>
		                    <td class="title">所属营业部:</td>
		                    <td>${orgDept.deptName}</td>
		                    <td class="title">计提月份:</td>
		                    <td><s:date format="yyyy-MM" name="commInfo.commSettleDate"/></td>
		                </tr>
		                <tr>
			                <td class="title">佣金计提金额:</td>
		                    <td>${commInfo.commMoney}</td>
		                    <td colspan="2"></td>
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
		                    <td><input name="commInfo.collectAccountName" class="easyui-validatebox" required="true"/></td>
		                    <td class="title">收款银行:</td>
		                    <td>
		                    	<x:combobox name="commInfo.collectBank" class="easyui-validatebox" list="bankList" textField="codeName" valueField="codeNo" required="true" pleaseSelect="false" cssStyle="width:142px;"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="title">收款支行:</td>
		                    <td><input name="commInfo.collectSubBank" class="easyui-validatebox" required="true"/></td>
		                    <td class="title">收款卡号:</td>
		                    <td><input name="commInfo.collectBankNo" class="easyui-validatebox" required="true"/></td>
		                </tr>
		                <tr>
		                    <td style="text-align: left;" colspan="4"><b>公司付款账户信息</b></td>
		                </tr>
		                <tr>
		                    <td class="title">付款时间:</td>
		                    <td>
		                    	<input class="Wdate easyui-validatebox" type="text" required="true" name="commInfo.payTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                      			<input name="commInfo.id" value="${commInfo.id}" type="hidden"/>
		                    </td>
		                    <td class="title">付款金额：</td>
		                    <td><input name="commInfo.payMoney" class="easyui-validatebox" required="true" validType="money" value="${commInfo.commMoney}"/>元</td>
		                </tr>
		                <tr>
		                    <td class="title">付款银行:</td>
		                    <td>
								<x:combobox name="commInfo.payBank" class="easyui-validatebox" list="bankList" textField="codeName" valueField="codeNo" required="true" pleaseSelect="false" cssStyle="width:142px;"/>
							</td>
		                    <td class="title">付款卡号:</td>
		                    <td><input name="commInfo.payBankNo"  class="easyui-validatebox" required="true"/></td>
		                </tr>
		                <tr>
		                	<td class="title">付款支行:</td>
		                    <td><input name="commInfo.paySubBank" class="easyui-validatebox" required="true"/></td>
		                    <td class="title">资金流水编号:</td>
		                    <td><input name="commInfo.paybackSerialNum" class="easyui-validatebox" required="true"/></td>
		                </tr>
		                 <tr>
		                    <td class="title">备注</td>
		                    <td colspan="3">
		                        <textarea name="commInfo.remark" class="easyui-validatebox" required="true" cols="30" style="width: 70%"></textarea>
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
                    var url = '<s:url value="/comm/commSettle_doPay.jhtml"/>';
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