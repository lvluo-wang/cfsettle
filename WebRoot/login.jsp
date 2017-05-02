<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.upg.ucars.framework.base.SessionTool"%>
<%
	java.util.Locale locale = session.getAttribute(com.upg.ucars.constant.SessionKeyConst.SESSION_LOCAL) == null ? java.util.Locale.CHINA : (java.util.Locale) session.getAttribute(com.upg.ucars.constant.SessionKeyConst.SESSION_LOCAL);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<title>长富资金结算系统</title>
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery-1.11.0.min.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery-migrate-1.2.1.min.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/easyui/jquery.easyui.min_inner.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/easyui/locale/easyui-lang-<%=locale%>.js"></script>
<script language="javascript" src="<s:url value="/platform/static/base/base.js"/>"></script>
<script type="text/javascript">
	setBaseDir('${pageContext.request.contextPath}');
</script>
<script language="javascript" src="<s:url value="/platform/static/plugins/jsextend.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/i18n/global_message_<%=locale%>.js"></script>
<script language="javascript" src="<s:url value="/platform/static/base/tool.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/base/component.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/base/validate.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/plugins/mask/mask.js"/>"></script>
<!--platform css  -->
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/default/easyui.css"/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/icon.css"/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/plugins/mask/css/mask.css'/>"/> 
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/table_form.css'/>"/> 
<link rel="stylesheet" href="<s:url value='/platform/static/css/login.css'/>"   /> 
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery.md5.js"/>"></script>
<script type="text/javascript" language="javascript">
	if(window.parent.mainFrame){
		window.parent.location.href='<s:url value="/login.jsp"/>';
	}
	
	
</script>
<%
	String userNo = com.upg.ucars.framework.base.BaseAction.getCookie(request,"userNo") == null ? "" : com.upg.ucars.framework.base.BaseAction.getCookie(request,"userNo");
%>
<style type="text/css">
	input.validatebox-invalid{
		border:0px !important;
	}
	#mi_list li {
		padding:2px;
		cursor:pointer;
	}
	.mi_list-hover{
		background:none repeat scroll 0 0 rgb(178,191,199);
	}
</style>
</head>
<body>
<div id="loading" class="common-mask" style="display:none;"></div>
<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
<div id="mi-down-panel" style="position:absolute;z-index:10000;background:#fff;border-style:solid;border-width:1px;border-color:rgb(178,191,199);border-top:none;border-radius:8px;padding:10px;display:none;">
	<ol id="mi_list">
	</ol>
</div>
<div id="ubspContent">
	<div class="contentBox">
      <div class="login"></div>
      <div class="name">长富资金结算系统</div>
	  <form id="frm">
      <ul class="listTxt">
      	<li><input id="userNo" type="text" class="inputSy easyui-validatebox" required="true" value="<%=userNo%>" ghosttext="账号"/></li>
      	<li><input id="pwd" type="password" class="inputSy easyui-validatebox" required="true" ghosttext="密码"/></li>
      	<li id="inputlt">
			<input id="mi" type="text" class="inputSy down" ghosttext="接入点"/>
			<input id="mi_no" name="miNo" type="hidden" />
		</li>
      	<li id="fogpwd"><!--<a href="javascript:;">忘记密码</a>--></li>
      	<li id="subli"><input id="login_btn" type="button" class="subGo" value="登录"/><input id="reset_btn" type="reset" class="resetGo" value="重置"/></li>
      </ul>
	  </form>
	</div>
</div>
<script>
	var currentUserNo = "";
	$(function(){
		$("#userNo").blur(function(){
			listMi();
		}).keypress(function(e){
			if (e.which == 13) // 判断所按是否回车键  
			{ 
				$("#pwd").focus();
				return false;
			}
		});
		$("#login_btn").click(function(){
			saveData();
		});
		
		$("body").click(function(){
			$("#mi-down-panel").hide();
		});
		
		var mi = $("#mi");
		mi.bind("click",function(event){
			$("#mi-down-panel").show();
			return false;
		});
		var offset = mi.offset(),width = mi.width(),top = offset.top+mi.outerHeight(),left = offset.left;
		$("#mi-down-panel").css({'left':left,'top':top,"width":width-12});
		initMiList();
		$("#userNo").focus();
	});
	function initMiList(){
		$("#mi_list>li").each(function(){
			$(this).hover(function(){
				$(this).addClass("mi_list-hover");
			},function(){
				$(this).removeClass("mi_list-hover");
			}).bind("click",function(){
				$("#mi").val($(this).text());
				$("#mi_no").val($(this).attr("val"));
				$("#mi-down-panel").hide();
				return false;
			});
		});
	}
	function saveData(){
		if($("#frm").form("validate")){
			loginValid();
		}
		
	}

	 function loginValid(){
		var url='<s:url value="/security/login_logon.jhtml"/>';
		var successUrl='<s:url value="/security/index.jhtml"/>';
		var param={"user.userNo":$("#userNo").val(),"user.password":$.md5($("#pwd").val()),"user.miNo":$("#mi_no").val(),'isEncrypt':'1'};
		doPost(url,param, function(data) {
			
			if(data){
				if(data == '<%=com.upg.ucars.basesystem.security.action.LoginAction.LOGIN_SUCCESS%>'){
					window.location.href=successUrl;
				}else{
					var obj=eval('('+data+')');
					if(obj.error){
						$.messager.alert(global.loginFailed,obj.error,'error',function(){
							$("#userNo").focus();
							$("#userNo").select();
						});
						
					}
				}
			}
		});
	}
	function listMi(){
		var userNo=$("#userNo").val();
		if($("#userNo").validatebox('isValid')){
			if(userNo==currentUserNo){
				return;
			}else{
				currentUserNo=userNo;
			}
			var url='<s:url value="/security/member_queryByUserNo.jhtml"/>';
			$("#mi_list").empty();
			doPostNoMask(url,{'userNo':userNo},function(result){
				if(result){
					var obj=str2obj(result);
					if(obj.error){
						error(obj.error);
					}else{
						if(obj.list){
							//miName,miNo
							var mi_list_content = "";
							$.each(obj.list,function(){
								mi_list_content += '<li val="' + this.miNo + '">'+ this.miName +'</li>';
							});
							$("#mi_list").html(mi_list_content);
							initMiList();
							if(obj.list.length==1){
								$("#mi_no").val(obj.list[0].miNo);
								$("#mi").val(obj.list[0].miName);
							} else if (obj.list.length==0){
								$("#mi_no").val('');
								$("#mi").val('');
							}
						}
						
					}
				}
			});
		}
	}
</script>
</body>
</html>
