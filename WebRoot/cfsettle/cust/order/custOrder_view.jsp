<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>
<%
    request.setAttribute("now", new java.util.Date());
%>
<tiles:insertDefinition name="FUNC_TOOL_FORM">
    <tiles:putAttribute name="tool">
        <x:button iconCls="icon-back" text="back" click="doReturn"/>
    </tiles:putAttribute>
    <tiles:putAttribute name="form">

        <div id="tt">
                <form class="busi_form" id="prj_form">
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
                            <td class="title">投资金额(元)</td>
                            <td>
                            <x:currency value="${prjOrder.money}" scale="2" />
                            </td>
                            <td class="title">投资状态</td>
                            <td><x:codeItem codeKey="<%=UtilConstant.CFS_PRJ_ORDER_STATUS%>"
                                            codeNo="prjOrder.status"/></td>
                        </tr>
                        <tr>
                            <td class="title">付款银行</td>
                            <td><x:codeItem codeKey="<%=UtilConstant.CFS_BANK_TYPE%>" codeNo="prjOrder.payBank"/></td>
                            <td class="title">投资时间</td>
                            <td><s:date name="prjOrder.investTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td class="title">还款方式</td>
                            <td><x:codeItem codeKey="<%=UtilConstant.CFS_REPAYMENT_TYPE%>" codeNo="prj.repayWay"/></td>
                            <td class="title">付款卡号</td>
                            <td>${prjOrder.payAccountNo}</td>
                        </tr>
                        <tr>
                            <td class="title">项目成立时间</td>
                            <td><s:date name="prj.buildTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                            <td class="title">还款期数</td>
                            <td>
                                <c:if test="totalPeriod !=0">
                            ${totalPeriod}
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">募集期利率</td>
                            <td>${prj.periodRate}%</td>
                            <td class="title">项目成立金额(元)</td>
                            <td>
                                <x:currency value="${prj.minLoanAmount}" scale="2" />
                            </td>
                        </tr>
                        <tr>
                            <td class="title">募集期利息(元)</td>
                            <td>${raisePrjOrderRepayPlan.yield}</td>
                            <td class="title">募集期计息天数</td>
                            <td>${raiseDay}</td>
                        </tr>
                        <tr>
                            <td class="title">服务员工</td>
                            <td>${prjOrder.serviceSysName}</td>
                            <td class="title">募集期利息状态</td>
                            <td>
                                <c:if test="raisePrjOrderRepayPlan != null">
                                <x:codeItem codeKey="<%=UtilConstant.CFS_COMM_PAY_STATUS%>" codeNo="raisePrjOrderRepayPlan.status"/>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">归属团队</td>
                            <td>${prjOrder.ownedTeamName}</td>
                            <td class="title">服务员工职位</td>
                            <td>
                                <x:codeItem codeKey="<%=UtilConstant.CFS_BUSER_POS_CODE%>" codeNo="prjOrder.serviceSysType"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">归属区域</td>
                            <td>${prjOrder.ownedAreaName}</td>
                            <td class="title">归属营业部</td>
                            <td>${prjOrder.ownedDeptName}</td>
                        </tr>
                        <tr>
                            <td class="title">合同审核时间</td>
                            <td><s:date name="prjOrder.contractAuditTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                            <td class="title">合同审核人</td>
                            <td>${prjOrder.contractAuidtSysName}</td>
                        </tr>
                        <tr>
                            <td class="title">收款审核时间</td>
                            <td><s:date name="prjOrder.collectAuditTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                            <td class="title">收款审核人</td>
                            <td>${prjOrder.collectAuditSysName}</td>
                        </tr>
                        <tr>
                            <td class="title">打款凭证</td>
                            <td colspan="3">
                                <img id="payNotes" src="" alt="打款凭证" height="100;" width="330px;" onclick="showPic(this)">
                            </td>
                        </tr>
                    </table>
                </form>
            <h3>还款计划</h3>
            <div class="func_data_area">
                <x:datagrid id="dataTableRepay"  url="/custOrder/custOrder_orderRepayList.jhtml?prjOrderId=${prjOrder.id}" height="260" pagebar="false" autoload="true" >
                    <x:columns>
                        <x:column title="预计还款时间" field="repayDate" align="center" width="200" />
                        <x:column title="还款本金(元)" field="principal" align="center" width="100" formatter="formatAmount"/>
                        <x:column title="还款利息(元)" field="yield" align="center" width="100" formatter="formatAmount"/>
                        <x:column title="还款本息(元)" field="priInterest" align="center" width="100" formatter="formatAmount"/>
                        <x:column title="还款期数" field="repayPeriod" align="center" width="100" formatter="formatPayTimes"/>
                        <x:column title="还款状态" field="status" align="center" width="80" formatter="formateRepayStatus"/>
                        <x:column title="实际还款时间" field="paybackTime" align="center" width="200" />
                        <x:column title="还款审核人" field="paybackAuditName" align="center" width="100" />
                    </x:columns>
                </x:datagrid>
            </div>

        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
            $(function () {
                $('#payNotes').attr("src", fileDownLoadUrl + "?id=${prjOrder.payNotesAttid}");
            })

            function doReturn() {
                window.history.go(-1);
            }

            function formatAmount(value){
                return formatCurrency(value);
            }

            function formateRepayStatus(value) {
                if (value == '1') {
                    return '待还款';
                } else if (value == '2') {
                    return '已还款';
                } else {
                    return ''
                }
            }
            
            function formatPayTimes(val,field,row){
    	      	if(val==0||val==""){
    	      		return "募集期";
    	      	}
    	      	return "第"+val+"期";
    	      }

        </script>
    </tiles:putAttribute>

</tiles:insertDefinition>