<%@page import="com.upg.ucars.constant.CodeKey"%>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_TOOL_FORM">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="commSettleView">
			<table>
				<colgroup>
					<col width="25%"/>
					<col width="30%"/>
					<col width="25%"/>
					<col width="30%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title">员工姓名: </td>
						<td>${buser.userName}</td>
						<td class="title">员工手机: </td>
						<td>${buser.userNo}</td>
					</tr>
					<tr>
						<td class="title">员工职位: </td>
						<td><x:codeItem codeKey="<%=UtilConstant.CFS_BUSER_POS_CODE%>" codeNo="buser.posCode"/></td>
						<td class="title">佣金计提金额(元): </td>
						<td>${commInfo.commMoney}</td>
					</tr>
					
				</tbody>
			</table>
		</form>
		<div class="func_data_area">
   			<x:datagrid id="dataTableView" singleSelect="true" url="/order/orderAudit_listComm.jhtml?prjOrder.commId=${commInfo.id}" autoload="true">
				<x:columns>
					<x:column title="合同编号" field="contractNo" align="center" width="140"/>
					<x:column title="投资时间" field="investTime" align="center" width="140"/>
					<x:column title="客户名" field="realName" align="center" width="120"/>
					<x:column title="购买项目" field="prjName" align="center" width="180"/>
					<x:column title="购买金额" field="money" align="center" width="180"/>
					<x:column title="佣金计提比例" field="commRate" align="center" width="180"/>
					<x:column title="佣金金额" field="commMoney" align="center" width="90"/>
				</x:columns>
			</x:datagrid>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" text="save" click="doEditSave" effect="round"/>
		<x:button iconCls="icon-cancel" text="cancel" click="doEditCancel" effect="round"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
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
			    $("#viewpicture").css("width",imgWidth); //以最终的宽度对图片缩放
		}
        $(".bannerPic").click(function(){
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            //imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
            var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
            viewPicture(src);
        });
    });
	
	function imgShow(outerdiv, innerdiv, bigimg, _this){
	    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
	    $(bigimg).attr("src", src);//设置#bigimg元素的src属性
	        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
	    $("<img/>").attr("src", src).load(function(){
	        var windowW = $(window).width();//获取当前窗口宽度
	        var windowH = $(window).height();//获取当前窗口高度
	        var realWidth = this.width;//获取图片真实宽度
	        var realHeight = this.height;//获取图片真实高度
	        var imgWidth, imgHeight;
	        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
	         
	        if(realHeight>windowH*scale) {//判断图片高度
	            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
	            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
	            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
	                imgWidth = windowW*scale;//再对宽度进行缩放
	            }
	        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
	            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
	                        imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
	        } else {//如果图片真实高度和宽度都符合要求，高宽不变
	            imgWidth = realWidth;
	            imgHeight = realHeight;
	        }
	                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放
	         
	        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
	        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
	        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
	        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
	    });
	     
	    $(outerdiv).click(function(){//再次点击淡出消失弹出层
	        $(this).fadeOut("fast");
	    });
	}
	
	function doCancel(){
		history.back();
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>