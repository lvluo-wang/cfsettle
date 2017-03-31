<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="bannerEditForm">
			<input type="hidden" name="banner.id" value="${banner.id}"/>
			<input type="hidden" name="banner.isActive" value="${banner.isActive}"/>
			<%--<input type="hidden" name="banner.sort"value="${banner.sort}"/>--%>
			<input type="hidden" id="editpicWidthHight"/>
			<table>
				<tbody>
				    <tr>
						<td class="title">banner图片: </td>
						<td colspan="3"><img class="bannerPic" id="editpicture" alt="banner图片" src="http://${picBasePath}" height="100px" width="200px"/>
						<font color="red"><span  id="editmessage"></span></font></td>
					</tr>
					<tr>
						<td class="title">图片路径<font color="red">*</font>: </td>
						<td colspan="3">
							<input name="banner.img" id="banner_edit_img" class="easyui-validatebox" readonly="readonly" style="width:550px" required="true" value="${banner.img}"></input>
							<br/><br/>
							<s:include value="/platform/common/uploadFile.jsp">
								<s:param name="refresh">y</s:param>
								<s:param name="suffix">2</s:param>
								<s:param name="imgServer">true</s:param>
								<s:param name="nowater">1</s:param>
								<s:param name="callback">uploadFileCallBack</s:param>
								<s:param name="opt">{'fileExt':'*.gif;*.jpg;*.png;*.jpeg','fileDesc':'图片文件'}</s:param>
							</s:include>
						</td>
					</tr>
					<tr>
						<td class="title">位置<font color="red">*</font>: </td>
						<td colspan="3"><x:combobox name="banner.position"  id="editposition" list="bannerPositionList" required="true"  pleaseSelect="false"
						 textField="codeName" valueField="codeNo" value="${banner.position}" cssStyle="width:180px;"/>
						</td>
					</tr>
					<tr>
					<td class="title">接入点号<font color="red">*</font>:</td>
						<td colspan="3"><x:combobox name="banner.miNoCode" list="bannerMiNoList" required="true" textField="codeName" valueField="codeNo" value="${banner.miNo}" cssStyle="width:180px;"/>
						</td>	
					</tr>
					<tr>
						<td class="title">标题<font color="red">*</font>: </td>
						<td colspan="3"><input name="banner.title" class="easyui-validatebox" maxLength="60" required="true" style="width:197px" value="${banner.title}"></input>
						</td>
					</tr>
				
					<tr>
					<td class="title">链接<font color="red">*</font>:</td>
						<td colspan="3"><input name="banner.href" class="easyui-validatebox" maxLength="120" required="true" style="width:550px" value="${banner.href}"></input>
						</td>
					</tr>
				
					    <tr>
					    <td class="title">激活时间<font color="red">*</font>:</td>				 
					    <td colspan="3">
						<input name="banner.starttime" id="edit_start_time" class="Wdate easyui-validatebox" required="true" value="<s:date format="yyyy-MM-dd HH:mm:00" name="banner.starttime"/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})" />
						-
						<input name="banner.endtime" id="edit_end_time" class="Wdate easyui-validatebox" required="true"  value="<s:date format="yyyy-MM-dd HH:mm:00" name="banner.endtime"/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})" />
					    </td>	
					    </tr>
					<tr>
						<td class="title">排序号:</td>
						<td colspan="3"><input name="banner.sort"
											   class="easyui-validatebox" maxLength="120" required="true"
											   style="width: 550px" value="${banner.sort}"></input></td>
					</tr>
					<tr>
						<td class="title">描述: </td>
						<td colspan="3"><textarea name="banner.content" class="easyui-validatebox" style="width:550px;height:60px" maxLength="300" >${banner.content}</textarea></td>
					</tr>
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="edit" iconCls="icon-save" text="save" click="doBannerEditSave" effect="round"/>
		<x:button iconCls="icon-cancel" text="cancel" click="doBannerEditCancel" effect="round"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.XXH_BANNER_POSITION%>","<%=UtilConstant.YES_NO%>"];
	var code = new XhhCodeUtil(keys);
	code.loadData();

	 $(function(){
		var image = new Image();
		image.src = "http://${picBasePath}";
		image.onload = function(){
			var realWidth = this.width;//获取图片真实宽度
			  var realHeight = this.height;//获取图片真实高度
			  var scale = 0.25;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
			  var imgWidth, imgHeight;
			   var windowW = $(window).width();//获取当前窗口宽度
		        var windowH = $(window).height();//获取当前窗口高度
		        imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
	            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
			    $("#editpicture").css("width",imgWidth); //以最终的宽度对图片缩放
			  $("#editpicWidthHight").val('');
			  $("#editpicWidthHight").val(realWidth+"*"+realHeight);
		        var picWidhtHight=$("#editpicWidthHight").val();
		        var position=$("#editposition").xcombobox("getText");
				 if(position.indexOf("*")>-1){
					 if(position.indexOf(picWidhtHight)>-1){
						 $("#editmessage").html("");
					 }else{
						 $("#editmessage").html(" 该图片大小为宽:"+realWidth+"高:"+realHeight+"不符合尺寸！");
						 return false;
					 }
				}
		}
	}); 
	function doBannerEditSave(){
		if($("#bannerEditForm").form("validate")){
			var starttime = $("#edit_start_time").val(); //激活开始时间
			var endtime = $("#edit_end_time").val(); //激活截止时间
			if(starttime!=''&&endtime!=''){
				/** 激活开始时间要小于激活截止时间 */
				if (new Date(starttime.replace(/-/g, "//")) >= new Date(endtime.replace(/-/g, "//"))) {
					warning("激活开始时间必须小于激活截止时间！");
					return false; 
				}
			}else{
				warning("激活开始时间或激活截止时间不能为空！");
				return false;
			}
			var picWidhtHight=$("#editpicWidthHight").val();
			 var position=$("#editposition").xcombobox("getText");
			 if(position.indexOf("*")>-1){
			 if(position.indexOf(picWidhtHight)>-1){
			 }else{
				 info("图片大小不符合尺寸");
				 return false;
			 }
		}
			$("#edit").xbutton("disable");
			var url = '<s:url value="/banner/bannerManage_doEdit.jhtml"/>';
			doPost(url, formToObject("bannerEditForm"),
					function(result) {
						if (!printError(result)) {
							closeWindow("project_edit_win");
							dataTable.refresh();
						}
					});
		}
	}
	function doBannerEditCancel(){
		closeWindow("project_edit_win");
	}
	
	function uploadFileCallBack(value){

		if(value.isSupported == 0){
			info("上传操作不支持此文件类型");
			return false;
		}

		var image = new Image();
		image.src = "http://"+value.domain+""+value.attachPath+""+value.saveName;
		image.onload = function(){
			var realWidth = this.width;//获取图片真实宽度
			  var realHeight = this.height;//获取图片真实高度
			  var scale = 0.25;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
			  var imgWidth, imgHeight;
			   var windowW = $(window).width();//获取当前窗口宽度
		        var windowH = $(window).height();//获取当前窗口高度
		        imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
	            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
			    $("#editpicture").css("width",imgWidth); //以最终的宽度对图片缩放
				$("#editpicture").attr("src","http://"+value.domain+""+value.attachPath+""+value.saveName);
			  $("#editpicWidthHight").val('');
			  $("#editpicWidthHight").val(realWidth+"*"+realHeight);
		        var picWidhtHight=$("#editpicWidthHight").val();
		        var position=$("#editposition").xcombobox("getText");
				 if(position.indexOf("*")>-1){
					 if(position.indexOf(picWidhtHight)>-1){
						 $("#editmessage").html("");
					 }else{
						 $("#editmessage").html(" 该图片大小为宽:"+realWidth+"高:"+realHeight+"不符合尺寸！");
						 info("图片大小不符合尺寸");
						 return false;
					 }
				}
		}
		$('#banner_edit_img').val(value.xhhResult);
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
