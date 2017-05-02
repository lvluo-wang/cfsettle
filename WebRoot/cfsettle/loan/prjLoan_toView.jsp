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
                     <td class="title">剩余放款金额(元):</td>
                    <td>${prj.demandAmount-prj.loanedAmount}</td>
                </tr>
                <tr>
                    <td class="title">项目期限:</td>
                    <td>
                    	${prj.timeLimit}
                        <x:codeItem codeNo="prj.timeLimitUnit" codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT %>"/>
                    </td>
                    <td class="title">项目利率:</td>
                    <td>
                       ${prj.yearRate}%
                    </td>
                </tr>
                <tr>
                    <td class="title">募集金额(元):</td>
                    <td>${prj.demandAmount}</td>
                    <td class="title">募集期利率:</td>
                    <td>
                        ${prj.periodRate}%
                    </td>
                </tr>
                <tr>
                    <td class="title">还款方式:</td>
                    <td><x:codeItem codeNo="prj.repayWay" codeKey="<%=UtilConstant.CFS_REPAYMENT_TYPE %>"/></td>
                    <td class="title">项目成立金额(元):</td>
                    <td>${prj.minLoanAmount}</td>
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
            </table>
        </form>
   		<div class="func_data_area">
   			<x:datagrid id="dataTableView" singleSelect="true" url="/prj/prjLoan_listLoan.jhtml?prjLoanLog.prjId=${prj.id}" autoload="true" form="mainQueryForm">
				<x:columns>
					<x:column title="放款次数" field="loanTimes" align="center" width="140" formatter="formatTimes"/>
					<x:column title="项目名" field="prjName" align="center" width="140"/>
					<x:column title="放款时间" field="loanTime" align="center" width="120" formatter="format2Time"/>
					<x:column title="放款金额(元)" field="loanAmount" align="left" width="80"/>
					<x:column title="放款银行" field="loanBankName" align="left" width="80" formatter="formateBank"/>
					<x:column title="放款卡号" field="loanAccountNo" align="left" width="180"/>
					<x:column title="资金流水" field="loanSerialNum" align="left" width="240"/>
					<x:column title="操作人" field="sysUserName" align="left" width="90"/>
				</x:columns>
			</x:datagrid>
		</div>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
        	
        var keys=["<%=UtilConstant.CFS_BANK_TYPE%>"];
    	var code=new XhhCodeUtil(keys);
    	code.loadData();

        function formateBank(value) {
    		return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
        }
            function doReturn() {
                window.history.go(-1);
            }
            
            function formatTimes(val){
            	return "第"+val+"期";
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