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
                    <input type="hidden" value="${prj.status}" name="prj.status"/>
                    <input type="hidden" value="${prj.loanedAmount}" name="prj.loanedAmount"/>
                    <input type="hidden" value="${prj.loanTimes}" name="prj.loanTimes"/>
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
                                <input id="prjUseName" value="${prj.prjUseName}"
                                       class="easyui-validatebox"
                                       name="prj.prjUseName"
                                       required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">项目联系电话:</td>
                            <td>
                                <input name="prj.prjMobile" value="${prj.prjMobile}"
                                       class="easyui-validatebox" required="true" validType="mobile"/>
                            </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td class="title">募集金额:</td>
                            <td><input name="prj.demandAmount" value="${prj.demandAmount}" class="easyui-validatebox"
                                       validType="positive_int" required="true"/>
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
                                       class="easyui-validatebox" required="true" validType="positive_int"/>
                                <span>%</span>
                            </td>
                            <td class="title">募集期利率:</td>
                            <td>
                                <input id="periodRate" name="prj.periodRate" value="${prj.periodRate}"
                                       class="easyui-validatebox" required="true" validType="positive_int"/>
                                <span>%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">还款方式:</td>
                            <td><x:combobox name="prj.repayWay" value="${prj.repayWay}" list="repaymentTypeList"
                                            textField="codeName" valueField="codeNo" required="true"
                                            pleaseSelect="false"/></td>
                            <td class="title">项目成立金额:</td>
                            <td><input name="prj.minLoanAmount" value="${prj.minLoanAmount}" class="easyui-validatebox" validType="positive_int"
                                       required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">融资开标时间：</td>
                            <td>
                                <input class="Wdate easyui-validatebox" id="start_time"
                                       type="text" required="true" name="prj.startBidTime"
                                       value='<s:date name="prj.startBidTime" format="yyyy-MM-dd HH:mm:00"/>'
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',maxDate:'#F{$dp.$D(\'end_time\')}'})"/>
                            </td>
                            <td class="title">融资截标时间：</td>
                            <td>
                                <input class="Wdate easyui-validatebox" id="end_time"
                                       type="text" required="true" name="prj.endBidTime"
                                       value='<s:date name="prj.endBidTime" format="yyyy-MM-dd HH:mm:00"/>'
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'#F{$dp.$D(\'start_time\')}'})"/>
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
                                <input name="prjExt.tenantName" value="${prjExt.tenantName}" class="easyui-validatebox" required="true"/>
                            </td>
                            <td class="title">收款银行:</td>
                            <td colspan="3">
                                <x:combobox name="prjExt.tenantBank" value="${prjExt.tenantBank}"
                                            list="bankList" textField="codeName" valueField="codeNo"
                                            required="true" pleaseSelect="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title" >选择-省份:</td>
                            <td >
                                <x:combobox id="edit_province_id" name="prjExt.provinceId" onchange="changeProvince" value="${prjExt.provinceId}"  required="true" valueField="id" textField="nameCn"/>
                            </td>
                            <td class="title" >选择-城市:</td>
                            <td >
                                <x:combobox id="edit_city_id" name="prjExt.cityId" onchange="changeCity" value="${prjExt.cityId}" required="true" valueField="id" textField="nameCn"/>
                            </td>
                            </td>
                        </tr>
                        <script type="text/javascript">

                            $("#edit_province_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getProvinces.jhtml"/>'});
                            //编辑的时候加下面两条语句
                            $("#edit_city_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid=${prjExt.provinceId}'});

                            function changeProvince(provinceId){
                                if(provinceId){
                                    $("#edit_city_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid='+provinceId});
                                }

                            }
                        </script>
                        <tr>
                            <td class="title">收款支行:</td>
                            <td>
                                <input name="prjExt.subBank" value="${prjExt.subBank}" class="easyui-validatebox" required="true"/>
                            </td>
                            <td class="title">收款账号:</td>
                            <td>
                                <input name="prjExt.accountNo" value="${prjExt.accountNo}" class="easyui-validatebox" required="true"/>
                            </td>
                        </tr>

                        <tr>
                            <td style="text-align: center;" colspan="4"><b>项目附加信息</b></td>
                        </tr>
                        <tr>
                            <td class="title">电子合同:</td>
                            <td><div id="uploadName_1">
                                <c:if test="prjExt.contractAttid !=null">
                                <a href='#' class='_attach_info'
                                   onclick='_downloadFile("${prjExt.contractAttid}")'>${prjExt.contractName}</a>
                                <a href='#' onclick='_deleteFile("${prjExt.contractAttid}",1)'><s:text name="del"/>
                                </a>
                                </c:if>
                            </div></td>
                            <td colspan="3">
                                <input type="hidden" id="uploadFile_1" value="${prjExt.contractAttid}" name="prjExt.contractAttid" required="true"/>
                                <br/><br/>
                                <div id="upload_1"></div>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">募资说明:</td>
                            <td><div id="uploadName_2">
                                <c:if test="prjExt.periodAttid != null">
                            <a href='#' class='_attach_info'
                               onclick='_downloadFile("${prjExt.periodAttid}")'>${prjExt.periodName}</a>
                                 <a href='#' onclick='_deleteFile("${prjExt.periodAttid}",2)'><s:text name="del"/></a>
                                </c:if>
                            </div>
                            </td>
                            <td colspan="2">
                                <input type="hidden" id="uploadFile_2" value="${prjExt.periodAttid}" name="prjExt.periodAttid" required="true"/>
                                <br/><br/>
                                <div id="upload_2"></div>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">担保手续费:</td>
                            <td><div id="uploadName_3">
                                 <c:if test="prjExt.guaranteAttid != null">
                                <a href='#' class='_attach_info'
                                   onclick='_downloadFile("${prjExt.guaranteAttid}")'>${prjExt.guaranteName}</a>
                                 <a href='#' onclick='_deleteFile("${prjExt.guaranteAttid}",3)'><s:text name="del"/></a>
                                 </c:if>
                            </div></td>
                            <td colspan="2">
                                <input type="hidden" id="uploadFile_3" value="${prjExt.guaranteAttid}" name="prjExt.guaranteAttid" required="true"/>
                                <br/><br/>
                                <div id="upload_3"></div>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">推广资料:</td>
                            <td><div id="uploadName_4">
                                  <c:if test="prjExt.spreadAttid != null">
                                 <a href='#' class='_attach_info'
                                    onclick='_downloadFile("${prjExt.spreadAttid}")'>${prjExt.spreadName}</a>
                                 <a href='#' onclick='_deleteFile("${prjExt.spreadAttid}",4)'><s:text name="del"/></a>
                                  </c:if>
                            </div></td>
                            <td colspan="2">
                                <input type="hidden" id="uploadFile_4" value="${prjExt.spreadAttid}" name="prjExt.spreadAttid" required="true"/>
                                <br/><br/>
                                <div id="upload_4"></div>
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
        <script id="uploadTemplate" type="text/x-jsrender">
        <s:include value="/platform/common/uploadFile.jsp">
            <s:param name="refresh">y</s:param>
            <s:param name="canEdit">true</s:param>
            <s:param name="suffix">{{:index}}</s:param>
            <s:param name="imgServer">false</s:param>
            <s:param name="nowater">1</s:param>
            <s:param name="callback">uploadFileCallBack</s:param>
            <s:param name="opt">{'fileExt':'*.pdf','fileDesc':'pdf文件'}</s:param>
        </s:include>
        </script>

        <script type="text/javascript">


            $(function () {
                $('#tt').css("height", $(document.body).height() - 50).tabs({});
                //changePercent($("input[name='carLoan.rateType']")[0].value);
                addUploadTemplate();

            });

            function addUploadTemplate() {
                var index = 4;
                for (var i = 1; i <= index; i++) {
                    var signObj = {
                        index: i
                    };
                    $("#upload_" + i).append($("#uploadTemplate").render(signObj));
                }
            }

            function uploadFileCallBack(value, index) {
                /*if(value.isSupported == 0){
                 info("上传操作不支持此文件类型");
                 return false;
                 }*/
                $('#uploadFile_' + index).val(value.id);

                var attachmentItem = "<a href='#' class='_attach_info' attachId='"+ value.id +"' onclick=\"_downloadFile('" + value.id + "')\">" +value.name + "</a>";
                attachmentItem +="&nbsp;&nbsp;<a href='#' onclick=\"_deleteFile('" + value.id + ","+index+"')\"><s:text name="del"/></a>";
                attachmentItem += "<br/>";
                $('#uploadName_'+index).html(attachmentItem);
            }

            function _deleteFile(attachmentId,index){
                $.ajax({
                    type:"POST",
                    url:"<s:url value='/component/attachment_delete.jhtml'/>",
                    dataType: 'json',
                    cache:false,
                    data:{id:attachmentId},
                    error:function(result){
                        printError(result);
                    },
                    success:function(){
                        $('#uploadName_'+index).remove();
                        $('#uploadFile_' + index).val('');
                    }
                });
            }

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

            function doApply() {
                if ($("#prj_form").form("validate") && $("#prjExt_form").form("validate")) {

                    var url = '<s:url value="/prj/prjManage_doEdit.jhtml"/>';
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