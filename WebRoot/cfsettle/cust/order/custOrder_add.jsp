<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<%
    request.setAttribute("now", new java.util.Date());
%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
    <tiles:putAttribute name="form">
                <form class="busi_form" id="prjOrder_form">
                    <input type="hidden" name="prjOrder.custId" value="${cust.id}"/>
                    <table>
                        <colgroup>
                            <col width="20%"/>
                            <col width="30%"/>
                            <col width="20%"/>
                            <col width="30%"/>
                        </colgroup>
                        <tr>
                            <td style="text-align: center;" colspan="4"><b>客户信息</b></td>
                        </tr>

                        <tr>
                            <td class="title">客户姓名:</td>
                            <td>
                                ${cust.realName}
                            </td>
                            <td class="title">客户身份证:</td>
                            <td>
                                ${cust.idCard}
                            </td>
                        </tr>
                        <tr>
                            <td class="title">客户手机:</td>
                            <td>
                                    ${cust.mobile}
                            </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td class="title">投资项目:</td>
                            <td colspan="3">
                                <x:combobox name="prjOrder.prjId" class="easyui-validatebox" required="true"
                                            list="prjList" textField="prjName" valueField="id"
                                            pleaseSelect="false" cssStyle="width:380px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">投资金额(元):</td>
                            <td><input name="prjOrder.money" class="easyui-validatebox" required="true" />元
                            </td>
                            <td class="title">投资时间:</td>
                            <td>
                                <input class="Wdate easyui-validatebox" id="start_time"
                                       type="text" required="true" name="prjOrder.investTime"
                                       value='<s:date name="prjOrder.investTime" format="yyyy-MM-dd HH:mm:00"/>'
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">付款银行:</td>
                            <td>
                                <x:combobox name="prjOrder.payBank" class="easyui-validatebox" required="true"
                                            list="bankList" textField="codeName" valueField="codeNo"
                                             pleaseSelect="false"/>
                            </td>
                            <td class="title">付款支行:</td>
                            <td>
                                <input name="prjOrder.paySubBank"
                                       class="easyui-validatebox" required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">付款卡号:</td>
                            <td>
                                <input name="prjOrder.payAccountNo"
                                       class="easyui-validatebox" required="true" validType=""/>
                            </td>
                            <td class="title">合同编号:</td>
                           	<td><input name="prjOrder.contractNo" class="easyui-validatebox"/></td>
                        </tr>
                        <s:if test="cust.cardFace==null">
	                        <tr>
								<td class="title">身份证正面: </td>
								<td>
									<input name="cust.cardFace" type="hidden" id="cfscust_face_card_id_order"/>
									<img id="id_card_face_pic_order" alt="身份证正面" src="http://" height="50px" width="200px" onclick="showPic(this)"/>
								</td>
								<td colspan="2">
									<s:include value="/platform/common/uploadFile.jsp">
										<s:param name="refresh">y</s:param>
										<s:param name="suffix">1</s:param>
										<s:param name="imgServer">true</s:param>
										<s:param name="nowater">1</s:param>
										<s:param name="callback">idCardFaceCallBack</s:param>
										<s:param name="opt">{'fileExt':'*.gif;*.jpg;*.png;*.jpeg','fileDesc':'图片文件'}</s:param>
									</s:include></td>
							</tr>
                        </s:if>
                        <s:if test="cust.cardBack==null">
							<tr>
								<td class="title">身份证反面: </td>
								<td>
									<input name="cust.cardBack" type="hidden" id="cfscust_back_card_id_order"/>
									<img id="id_card_back_pic_order" alt="身份证正面" src="http://" height="50px" width="200px" onclick="showPic(this)"/>
								</td>
								<td colspan="2">
									<s:include value="/platform/common/uploadFile.jsp">
										<s:param name="refresh">y</s:param>
										<s:param name="suffix">2</s:param>
										<s:param name="imgServer">true</s:param>
										<s:param name="nowater">1</s:param>
										<s:param name="callback">idCardBackCallBack</s:param>
										<s:param name="opt">{'fileExt':'*.gif;*.jpg;*.png;*.jpeg','fileDesc':'图片文件'}</s:param>
									</s:include></td>
							</tr>
                        </s:if>
                        <tr>
                            <td class="title">打款凭证: </td>
                            <td>
                            	<input type="hidden" id="payNotesAttid" name="prjOrder.payNotesAttid" class="easyui-validatebox"  required="true" />
                            	<img id="payNotesDiv" alt="身份证正面" src="http://" height="50px" width="200px" onclick="showPic(this)"/>
                            </td>
                            <td colspan="3">
                                <s:include value="/platform/common/uploadFile.jsp">
                                    <s:param name="refresh">y</s:param>
                                    <s:param name="canEdit">true</s:param>
                                    <s:param name="suffix">3</s:param>
                                    <s:param name="imgServer">false</s:param>
                                    <s:param name="nowater">1</s:param>
                                    <s:param name="callback">uploadFileCallBack</s:param>
                                    <s:param name="opt">{'fileExt':'*.gif;*.jpg;*.png;*.jpeg','fileDesc':'图片文件'}</s:param>
                                </s:include>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center;" colspan="4"><b>备注</b></td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <textarea name="prjOrder.remark" cols="30" style="width: 100%" id="verifyDesc"></textarea>
                            </td>
                        </tr>

                        <tr>
                            <td style="text-align: center;" colspan="4">
                                <x:button iconCls="icon-audit" text="提交" click="doSave" effect="round"/>
                            </td>
                        </tr>
                    </table>
                </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
            function uploadFileCallBack(value,index){
            	$('#payNotesDiv').attr('src',fileDownLoadUrl+"?id="+value.id);
        		$('#payNotesAttid').val(value.id);
            }
            
            function idCardFaceCallBack(value) { 
        		$('#id_card_face_pic_order').attr('src',fileDownLoadUrl+"?id="+value.id);
        		$('#cfscust_face_card_id_order').val(value.id);
        	}
        	
        	function idCardBackCallBack(value) { 
        		$('#id_card_back_pic_order').attr('src',fileDownLoadUrl+"?id="+value.id);
        		$('#cfscust_back_card_id_order').val(value.id);
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
                        $('#payNotesDiv').remove();
                        $('#payNotesAttid').val('');
                    }
                });
            }

            function doAddCancel() {
                closeWindow("project_add_win");
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

            function doSave() {
                if ($("#prjOrder_form").form("validate")) {
                    /* var payNotesAttid = $('#payNotesAttid').val();
                    if(payNotesAttid ==''){
                        info("请上传打款凭证");
                        return;
                    }
                    if($('#id_card_face_pic_order').lenght>0&&$('#id_card_face_pic_order').val==''){
                        info("请上传身份证正面");
                        return;
                    }
                    if($('#id_card_back_pic_order').lenght>0&&$('#id_card_back_pic_order').val==''){
                        info("请上传身份证反面");
                        return;
                    } */
                    var url = '<s:url value="/custOrder/custOrder_doAdd.jhtml"/>';
                    var param = formToObject("prjOrder_form");
                    AddRunningDiv("提交处理中，请稍候...");
                    doPost(url, param, function (result) {
                        if (!printError(result)) {
                            $(".datagrid-mask").remove();
                            $(".datagrid-mask-msg").remove();
                            closeWindow("project_add_win");
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