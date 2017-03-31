<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="cfscustAddForm">
			<table>
				<tr>	
					<td class="title">客户姓名<font color="red">*</font>:</td>
					<td ><input name="cfsCust.realName" class="easyui-validatebox" maxLength="50" required="true"></td>
				</tr>
				<tr>
					<td class="title">客户手机<font color="red">*</font>:</td>
					<td ><input name="cfsCust.mobile" class="easyui-validatebox" maxLength="11" required="true" validType="mobile"></td>
				</tr>
				<tr>
					<td class="title">身份证号<font color="red">*</font>:</td>
					<td ><input name="cfsCust.mobile" class="easyui-validatebox" maxLength="21" required="true" validType="idCard"></td>
				</tr>
				<tr>
					<td class="title">性别<font color="red">*</font>:</td>
					<td ><x:combobox name="cfsCust.sex" list="yesNo" required="true" textField="codeName" valueField="codeNo" cssStyle="width:142px;" /></td>
				</tr>
				<tr>
					<td class="title">身份证正面: </td>
					<td><img class="bannerPic" id="editpicture" alt="banner图片" src="http://" height="100px" width="200px"/>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="save" iconCls="icon-save" text="save" click="doAddSave" effect="round" />
		<x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	function doAddSave() {
		if ($("#cfscustAddForm").form("validate")) {
			doPost(url, formToObject("cfscustAddForm"),
				function(result) {
					if (!printError(result)) {
						closeWindow("project_add_win");
						dataTable.refresh();
					}
				});
		}
	}
	function doAddCancel() {
		closeWindow("project_add_win");
	}

	function uploadFileCallBack(value) { 
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
			    $("#picture").css("width",imgWidth); //以最终的宽度对图片缩放
				$("#picture").attr("src","http://"+value.domain+""+value.attachPath+""+value.saveName);
			  $("#picWidthHight").val('');
			  $("#picWidthHight").val(realWidth+"*"+realHeight);
		        var picWidhtHight=$("#picWidthHight").val();
		        var position=$("#position").xcombobox("getText");
				 if(position.indexOf("*")>-1){
					 if(position.indexOf(picWidhtHight)>-1){
						 $("#message").html("");
					 }else{
						 $("#message").html(" 该图片大小为宽:"+realWidth+"高:"+realHeight+"不符合尺寸！");
						 info("图片大小不符合尺寸");
						 return false;
					 }
				}
		}
		$('#cfsCust_add_img').val(value.xhhResult);
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
