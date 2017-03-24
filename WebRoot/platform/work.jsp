<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div id="jobs"  fit="true"   border="false">
	<div id="job_common_area" title="<s:text name='welcome'/>" closable="false" border="false"><%
if(com.upg.ucars.basesystem.security.core.user.UserManager.isBrchNormalUser()){%>
			<%@ include file="/platform/portal.ucars"%><%
}%>
	</div>
	<div id="job_work_area" title="<s:text name='workarea'/>" closable="false" 
		<s:if test="#request.mobile">
		</s:if>
		<s:elseif test="#request.ipad">
		</s:elseif>
		<s:else>
			style="overflow:hidden;"
		</s:else>
		border="false" selected="false" >
		<iframe id="work_frame" name="mainFrame" frameborder="0" scrolling="auto" style="width: 100%; height: 100%"></iframe>
	</div>
	<!-- 
	<div id="task_work_area" title="提醒任务处理窗口" closable="false" style="overflow:hidden;" border="false" selected="false" >
		<iframe id="task_frame" name="taskFrame" frameborder="0" scrolling="auto" style="width: 100%; height: 100%"></iframe>
	</div>
	 -->
</div>

<script type="text/javascript">
	var portalArray = new Array();
	var currentIndex=0;
	var innerBodyWidth = document.body.clientWidth-198;//198为左边菜单的宽度，
	var panelWidth = innerBodyWidth*0.4;//6%--40%--4%+3%--40%--7%，将任务提醒分成两列，panel各自占40%，panel与panel，panel与两边距离为6%、7%左右
	var leftDivMarginLeft = innerBodyWidth*0.06;
	var rightDivMarginLeft = innerBodyWidth*0.03;
	var taskNameTitleWidth = panelWidth*0.7;
	
	//首页portal任务提醒入口函数	
	function initPortal(){
		var url = "<s:url value='/taskRemind/taskRemind_queryPortal.jhtml'/>";
		//fetchPortalPost(url,null,fetchPortalCallBack);
	}
	
	//在原有doPost基础上将锁屏功能去掉
	function fetchPortalPost(url,param,callback){
		 var tmpUrl;
		 if(url.indexOf('?')==-1){
			 tmpUrl=url+"?randomNum="+Math.random()*99999+"&ajax_request=async";
		 }else{
			 tmpUrl=url+"&randomNum="+Math.random()*99999+"&ajax_request=async";
		 }
		 $.ajax({
			 type:"POST",
			 url:tmpUrl,
			 data:param,
			 dataType:"text",
			 cache:false,
			 error:function(result){
				 error(global.requestFailedInfo);
			 },
			 success:callback,
			 complete:function (xhr, textStatus){
				 setNull(xhr);
			 },
			 beforeSend:function(xhr){
			 }
		 });
		 setNull(tmpUrl);
	 }

	//获取portalId回调函数，并开始获取第一个portal数据
	function fetchPortalCallBack(result){
		if(result){
			if(result.indexOf("{")==0){
				var obj = eval("("+result+")");
				if(obj.error){
					error(obj.error);
					obj = null;
					return;
				}else if(obj.success){
					info(obj.success);
					obj = null;
				}
			}
			var index = 1;
			
			$(obj.portalSet).each(function(){
				var str = "";
				if(index%2==1){
					str+= '<div style="margin-top:11%;margin-left:'+leftDivMarginLeft+'px;">';
				}else{
					str+= '<div style="margin-top:11%;margin-left:'+rightDivMarginLeft+'px;">';
				}	
					str+= '<div id="'+this+'_panel" style="width:'+panelWidth+'px;height:186px;background:#fafafa;">';
					str+= '<table id="'+this+'_table" border="false" style="display:none">';
					str+= '</table>';
					str+= '</div></div>';
				
				if(index%2==1){
					$("#leftTaskRemindDiv").append(str);
				}else{
					$("#rightTaskRemindDiv").append(str);
				}
				
				index++;
				var portalId = this;
				$("#"+this+"_panel").panel({
					title:"加载中...",
					collapsible:true,
					minimizable:false,
					maximizable:false,
					closable:true,
					onExpand:function(){
						fetchPortalData(portalId);
					}
				});
				portalArray.push(portalId);
			});
	
			if(portalArray.length>0){
				fetchNextPortalData();
			}
		}
	}

	//获取下一个portal数据
	function fetchNextPortalData(){
		if(portalArray.length>currentIndex){
			var portalId = portalArray[currentIndex];
			fetchPortalData(portalId);
			currentIndex +=1;
		}
	}
	
	//根据portalID获取portal数据
	function fetchPortalData(portalId){
		var url = "<s:url value='/taskRemind/taskRemind_queryTask.jhtml'/>?portalId="+portalId;
		fetchPortalPost(url,null,function(result){
				fetchPortalDataCallBack(portalId,result);
			}	
		);
	}

	//获取portal数据回调函数
	function fetchPortalDataCallBack(portalId,result){
		fetchNextPortalData();
		
		if(result){
			var obj;
			if(result.indexOf("{")==0){
				obj = eval("("+result+")");
				if(obj.error){
					error(obj.error);
					obj = null;
					return;
				}else if(obj.success){
					info(obj.success);
					obj = null;
				}
			}
	
			$('#'+portalId+"_table").show();
			$('#'+portalId+"_table").datagrid({
				rownumbers:true,
				singleSelect:true,
				fit:true,	
				columns:[[
						{field:'taskName',title:'任务名称',width:taskNameTitleWidth,align:'left',formatter:function(value,rowData,rowIndex){
							return "<a href='#' onclick='updateWorkapace(\""+rowData.linkURL+"\",\""+rowData.linkPageTitle+"\")'>"+value+"</a>";
						  }
						},
						{field:'taskType',title:'任务类型',width:70,align:'center'}
				]]
			});
			
			$('#'+portalId+"_panel").panel("setTitle",obj.title);

			if(obj && obj.rows){
				$('#'+portalId+"_table").datagrid("loadData",obj.rows);
			}else{
				$('#'+portalId+"_panel").panel("collapse");
			}
		}
	}
</script>
	<!--
	var	str ='<li>';
			str+='<a class="tabs-inner" href="javascript:void(0)">';
			str+='<span class="tabs-title">提醒任务处理窗口</span>';
			str+='<span class="tabs-icon"></span>';
			str+='</a>';
			str+='</li>';
		$(".tabs").append(str);
		
		var str2 ='<div class="panel" style="display: none;width: 1069px;">';
			str2+='<div id="task_work_area" class="panel-body panel-body-noheader panel-body-noborder" selected="true" border="false" style="overflow:hidden;" closable="false" title="">';
			str2+='<iframe id="task_frame" scrolling="auto" frameborder="0" style="width: 100%; height: 100%" name="taskFrame">';
			str2+='<html>';
			str2+='<head></head>';
			str2+='<body></body>';
			str2+='</html>';
			str2+='</iframe>';
			str2+='</div>';
			str2+='</div>';
		$(".tabs-panels").append(str2);	
	
	-->