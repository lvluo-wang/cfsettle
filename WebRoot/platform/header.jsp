<%@ page contentType="text/html; charset=UTF-8" import="com.upg.ucars.framework.base.SessionTool" pageEncoding="UTF-8" %>
<%@ page import="com.upg.ucars.basesystem.busidate.util.BusiDateUtil" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="headerBox">
		<div id="header">
			<div class="loginFl">
			<font style="color: white;size: 26px;font-size: 26px;font-weight: bold; font-family: sans-serif;margin-left: -99px">长富资金结算系统</font>
			</div>
			<div id="sysTip"></div>
	        <div class="topFr">
	        	<ul class="nav">
	        		<li><a href="javascript:selectFirstJob();" title="首页"><i class="icon note"></i><i class=" icon num" style="top:-15px;display:none;" id="show_mytask_number">0</i></a></li>
	        		<li>
	        			<a id="user_name_link" href="javascript:void(0)"><%=SessionTool.getUserLogonInfo().getUserName() %>&nbsp;(<%=SessionTool.getUserLogonInfo().getUserNo() %>)<i class="icon user"></i></a>
        			</li>
	        		<li><a href="javascript:changePwd();" title="修改密码"><i class="icon friend"></i></a></li>
	        		<!-- <li><a href="javascript:doUserSet();" title="设置"><i class="icon set"></i></a></li> -->
	        		<li><a href="javascript:logout();" title="退出"><i class="icon refresh"></i></a></li>
	        	</ul>
	        </div>
		</div>
</div>
<div id="change_pwd"   style="display:none;width:500px;padding:5px;background: #fafafa;"></div>
<div id="user_setting_win"   style="display:none;width:500px;padding:5px;background: #fafafa;"></div>
<!-- taskDelegated -->
<script>
	function logout(){
		var url=logoutUrl+"?flag="+Math.random()*99999;
		window.location.href=url;
	}
	function doUserSet(){
		var url='<s:url value="/userSetting/userSetting_main.jhtml"/>';
		requestAtWindow(url,"user_setting_win",'<s:text name="set"/>');
	}
	function changePwd(){
		var url='<s:url value="/security/user_toChangePassword.jhtml"/>?flag='+Math.random()*99999;
		requestAtWindow(url,"change_pwd",'<s:text name="modifyPwd"/>',{'isChangePwd':0});
	}
	function showTaskNum(n){
		var num = 0;
		if(n && /\d+/.test(""+n)){
			num = n;
		}
		var show_mytask_number = $("#show_mytask_number")
		if (num > 0) {
			show_mytask_number.text(num).show();
		} else {
			show_mytask_number.text(num).hide();
		}
	}
</script>
<!--
<table cellpadding="0" cellspacing="0" style="width: 100%;background:url('<s:url value="/platform/static/images/bg_top.gif"/>') repeat-x;">
			<tr>
				<td style="width:173px;height:70px;padding-left:5px;">
					<div style="color: #fff; font-size: 22px; font-weight: bold;">
						<img  src='<s:url value="/platform/static/images/logo.jpg"/>'/>
					</div>
				</td>
				<td style="width:19px;">
					<a  href="javascript:void(0)" class="prev"><img src="<s:url value="/platform/static/images/icon_lef.jpg"/>" /></a>
				</td>
				<td id="menu_wrap">
					<div id="menu_slider" class="menu_slider">
						<div class="menu_items">
							<div class="menu_item">
								<a href="javascript:void(0)" onclick="selectFirstJob()">
									<img src="<s:url value="/platform/static/images/business.gif"/>"/>
									<div class="menu_item_desc"><s:text name="busi_alert"/></div>
								</a>
							</div>
							<div class="menu_item">
								<a href="javascript:void(0)">
									<img src="<s:url value="/platform/static/images/nowb.gif"/>"/>
									<div class="menu_item_desc"><s:text name="curr_busi"/></div>
								</a>
							</div>
							<div class="menu_item">
								<a href="javascript:void(0)">
									<img src="<s:url value="/platform/static/images/daily.gif"/>"/>
									<div class="menu_item_desc"><s:text name="op_log"/></div>
								</a>
							</div>
							<div class="menu_item">
								<a href="javascript:void(0)">
									<img src="<s:url value="/platform/static/images/followstate.gif"/>"/>
									<div class="menu_item_desc"><s:text name="state_follow"/></div>
								</a>
							</div>
							<div class="menu_item">
								<a href="javascript:void(0)">
									<img src="<s:url value="/platform/static/images/keepstate.gif"/>"/>
									<div class="menu_item_desc"><s:text name="state_keep"/></div>
								</a>
							</div>
							<div class="menu_item">
								<a href="javascript:void(0)">
									<img src="<s:url value="/platform/static/images/transition.gif"/>"/>
									<div class="menu_item_desc"><s:text name="task_change"/></div>
								</a>
							</div>
						</div>
					</div>
				<td style="width:19px;">
					<a  href="javascript:void(0)" class="next"><img src="<s:url value="/platform/static/images/icon_rig.jpg"/>" /></a></td>
				</td>
				<td style="">
					<div style="overflow:hidden;width:200px;height:60px;float:right;color:#badbfc;text-align:left;padding-top:5px;line-height:20px;">
						<div style="width:300px;">
			        	<s:text name="buser.userName"/>：<%=SessionTool.getUserLogonInfo().getUserName() %>&nbsp;(<%=SessionTool.getUserLogonInfo().getUserNo() %>)<br/>
			        	<s:text name="brch.name_"/>：<%=SessionTool.getUserLogonInfo().getBranchName() == null ? "" : SessionTool.getUserLogonInfo().getBranchName()%><br/>
			        	<s:text name="businessDate"/>：<%=BusiDateUtil.getCurBusiDateStr() %>
			        	</div>
		        	</div>
				</td>
				<td style="color:#badbfc;width:240px;" align="right">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td align="right" colspan="2" style="padding:0px;border:0px;height:25px;">
								<div class="s-login" >
				            		<a href="#" onClick="logout()"><s:text name='exit'/></a>
				            		<a href="#" onclick="doUserSet()" id="help">个人<s:text name='set'/></a>
				            		<a href="#" onClick="changePwd()"><s:text name='modifyPwd'/></a>
			           			 </div>
							</td>
						</tr>
						<tr>
							<td  align="left" style="padding:0px;border:0px;height:45px;vertical-align:bottom;">
								<a class="sys_message" href="#" onClick="sysMessage()">-<s:text name="sys_message"/>-</a>
								
								<a class="sys_message" href="#" onClick="simpleTheme()">-S-</a>
								<a class="sys_message" href="#" onClick="defaultTheme()">-D-</a>
								
							</td>
							<td align="right" style="padding:0px;border:0px;height:45px;vertical-align:bottom;"> 
								<s:include value="/platform/common/LangSelector.jsp"/>
							</td>
						</tr>
					</table>
			           	

				</td>
				
				
			</tr>
		</table>
<script type="text/javascript">
var successUrl='<s:url value="/security/index.jhtml"/>';
	function simpleTheme(){
		var theme="simple";
		var url='<s:url value="/security/user_changeTheme.jhtml"/>';
		doPost(url,{"xtheme":theme},function(result){
			if(result){
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
					return;
				}
				
			}
			window.location.href=successUrl+"?randomNum="+Math.random();
		});
	}
	function defaultTheme(){
		var theme="default";
		var url='<s:url value="/security/user_changeTheme.jhtml"/>';
		doPost(url,{"xtheme":theme},function(result){
			if(result){
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
					return;
				}
				
			}
			window.location.href=successUrl+"?randomNum="+Math.random();
		});
	}
function logout(){
	var url=logoutUrl+"?flag="+Math.random()*99999;
	window.location.href=url;
}
	function createHeader(){
		slidMenu("menu_slider");
	}
	function changePwd(){
		var url='<s:url value="/security/user_toChangePassword.jhtml"/>?flag='+Math.random()*99999;
		requestAtWindow(url,"change_pwd",'<s:text name="modifyPwd"/>');
	}
	function doUserSet(){
		var url='<s:url value="/userSetting/userSetting_main.jhtml"/>';
		requestAtWindow(url,"user_setting_win",'<s:text name="set"/>');
	}
	function sysMessage(){
		var url='<s:url value="/web_ui/message_message.jhtml"/>?flag='+Math.random()*99999;
		doPost(url,null,function(result){
			if(result){
				if(result.indexOf("{")!=0){
					
				}else{
					var data=eval('('+result+')');
					if(data.error){
	        			error(data.error);
					}else{
						$("#messageView").html(data.message);
						$("#errorView").html(data.errors);
					}
					setNull(data);
				}
			}
		});
		
		$("#sys_msg").show().window({
			resizable:false,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			closable:true,
			modal:true
		});
	}
</script>
<div id="change_pwd"   style="display:none;width:500px;height:280px;padding:5px;background: #fafafa;"></div>
<div id="user_setting_win"   style="display:none;width:500px;height:280px;padding:5px;background: #fafafa;"></div>
<div id="sys_msg"  title="<s:text name='message_view'/>" style="display:none;width:500px;height:280px;background: #fafafa;text-align:left;">
	<div id="msgs"  fit="true" border="false">
			<div  id="messageView"  title="<s:text name='normal_message'/>"></div>
			<div id="errorView" title="<s:text name='error_message'/>"></div>
		</div>
</div>

-->