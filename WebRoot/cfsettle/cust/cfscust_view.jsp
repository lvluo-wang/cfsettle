<%@page import="com.upg.ucars.constant.CodeKey"%>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_FORM">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-back" text="back" click="doCancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="form">
		<form class="busi_form" id="bannerViewForm">
			<input type="hidden" name="banner.id" value="${banner.id}"/>
			<input type="hidden" name="banner.isActive" value="${banner.isActive}"/>
			<table>
				<!--  <colgroup>
					<col width="25%"/>
					<col width="30%"/>
					<col width="25%"/>
					<col width="30%"/>
				</colgroup>   -->
				<tbody>
					<tr>
						<td class="title">位置: </td>
						<td><x:codeItem codeKey="<%=UtilConstant.XXH_BANNER_POSITION%>" codeNo="banner.position"/></td>
					</tr>
					<tr>
						<td class="title">标题: </td>
						<td>${banner.title}</td>
					</tr>
					<tr>
						<td class="title">banner图片: </td>
						<td colspan="3"><img class="bannerPic" id="viewpicture" alt="banner图片" src="http://${picBasePath}" height="100px" width="200px"/></td>
					</tr>
					<tr>
						<td class="title">是否激活: </td>
						<td><x:codeItem codeKey="<%=UtilConstant.YES_NO%>" codeNo="banner.isActive"/></td>
					</tr>
					<tr>
						<td class="title">激活时间:</td>				 
					    <td><s:date format="yyyy-MM-dd HH:mm:00" name="banner.starttime"/>
						至<s:date format="yyyy-MM-dd HH:mm:00" name="banner.endtime"/>
					    </td>
					</tr>				
					<tr>				
						<td class="title">链接:</td>
						<td colspan="3">${banner.href}</td>									
					</tr>
					<tr>
						<td class="title">接入点号:</td>
					    <td>${banner.miName}</td>									
					</tr>
					<tr>
						<td class="title">描述: </td>
						<td colspan="3"><textarea name="banner.content" disabled="disabled" class="easyui-validatebox" style="width:550px;height:60px" maxLength="300" >${banner.content}</textarea></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
			<div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" />
			</div>
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