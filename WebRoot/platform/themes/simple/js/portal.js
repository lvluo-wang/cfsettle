var pp_today = new Date();
var pp_keys=["C0401","C0201","C1004"];
var pp_code=new CodeUtil(pp_keys);
pp_code.loadData();
function pp_selectMenu(menuName,subMenuName){
	var ret = false;
	var mg = $("#menuGroup");
	var panels = mg.accordion("panels");
	for(var i = 0; i< panels.length;i++){
		var p = panels[i];
		if(p.panel("options").title == menuName){
			mg.accordion("select",menuName);
			ret = true;
			break;
		}
	}
	return ret;
}
function pp_doProcess(row,title,urlParam){
	var selectedId=row.id;
	var param = {
		'ib.processName':row.processName,
		'ib.taskName':row.taskName,
		'ib.prodNo':row.prodNo,
		'taskId':row.taskId
	};
	var url=baseDir+'/bpm/task_toProcess.jhtml';
	doPost(url,param,function(result){
		if(result){
			if(result.charAt(0)=='/'){
				result = result.replace(baseDir,"");
				if(result.indexOf("?") == -1){
					result += "?";
				}else{
					result += "&";
				}
				result += "taskId="+row.taskId;
				result += "&entityId="+row.entityId;
				result += "&processName="+row.processName;
				result += "&processId="+row.instanceId;
				result += "&ib.id="+row.id;
				result += "&ib.entityType="+row.entityType;
				if(typeof urlParam != 'undefined'){
					result += "&" + urlParam;
				}
				updateWorkapace(result,title);
			}else{
				printError(result);
			}
		}
	});
}
var pp_refreshAllPortal_count = 0;
var pp_portal_loading = {'pp_project_todotask':false,'pp_postloan_todotask':false,'pp_assmeet':false,'expire_remind':false,'post_loan_remind':false};
function pp_changePortalLoad(key,val){
	pp_portal_loading[key]=val;
}
function pp_refreshAllPortal(){
	if(++pp_refreshAllPortal_count>1){
		if(!pp_portal_loading.pp_project_todotask){
			$("#pp_project_todotask").datagrid("reload");
			pp_changePortalLoad('pp_project_todotask',true);
		}
		if(!pp_portal_loading.pp_postloan_todotask){
			$("#pp_postloan_todotask").datagrid("reload");
			pp_changePortalLoad('pp_postloan_todotask',true);
		}
		if(!pp_portal_loading.pp_assmeet){
			$("#pp_assmeet").datagrid("reload");
			pp_changePortalLoad('pp_assmeet',true);
		}
		if(!pp_portal_loading.expire_remind){
			$("#expire_remind").datagrid("reload");
			pp_changePortalLoad('expire_remind',true);
		}
		if(!pp_portal_loading.post_loan_remind){
			$("#post_loan_remind").datagrid("reload");
			pp_changePortalLoad('post_loan_remind',true);
		}
	}
}



$(function(){
	$("#platform_portal").show().portal({
		border:false
	});
	/*
	$("#portal_cc").calendar({
		current:new Date(),
		border:false
	});
	*/
	$("#pp_project_todotask").datagrid({
		idField:'id',
		border:false,
		fit:true,
		url:baseDir+'/bpm/task_listMyTask.jhtml',
		queryParams:{'page':1,'rows':10},
		columns:[[
			{title:"名称",  field:"col1", align:"left",width:160 },
			{title:"编号",  field:"col9", align:"left",width:120 },
			{title:"流程类型", field:"prodName", align:"left",width:100 },
			{title:"状态",  field:"taskCnName", align:"left",width:140 }
		]],
		onDblClickRow:function(rowIndex,rowData){
			var finded = pp_selectMenu("流程管理","待处理任务");
			if (finded) {
				pp_doProcess(rowData,"待处理任务","backUrl="+baseDir+'/bpm/task_toMyTask.jhtml');
			}
		},
		onLoadSuccess: function(data){
			pp_changePortalLoad('pp_project_todotask',false);
			try{
				showTaskNum(data.total);
			}catch(e){}
		},
		onLoadError: function(){
			pp_changePortalLoad('pp_project_todotask',false);
		}
	});
	$("#pp_assmeet").datagrid({
		idField:'id',
		border:false,
		fit:true,
		url:baseDir+'/assessMeeting/assessMeeting_list.jhtml?searchBean.allType=true',
		columns:[[
			{title:"评审会名称",  field:"meetingName", align:"left",width:90 },
			{title:"会议时间", field:"meetingTime", align:"left",width:105,formatter:function(value){
				return format2Minute(value);
			}},
			{title:"会议地点",  field:"meetingPlace", align:"left",width:77}//,
			//{title:"状态",  field:"status", align:"left",width:50,formatter:function(value){
			//	return pp_code.getValue("C0201",value);
			//}}
		]],
		queryParams:{'searchBean.beginDate':pp_today.getFullYear()+"-"+(pp_today.getMonth()+1)+"-" + pp_today.getDate()},
		onDblClickRow:function(rowIndex,rowData){
			
		},
		onLoadSuccess: function(data){
			pp_changePortalLoad('pp_assmeet',false);
		},
		onLoadError: function(){
			pp_changePortalLoad('pp_assmeet',false);
		}
	});
	$("#post_loan_remind").datagrid({
		idField:'id',
		border:false,
		fit:true,
		url:baseDir+'/postloan/postloanremind_getPostLoanRemind.jhtml',
		queryParams:{'page':1,'rows':5},
		columns:[[
			{title:"项目编号",  field:"projectNo", align:"left",width:120 },
			{title:"项目名称", field:"projectName", align:"left",width:160},
			{title:"上次贷后日期",  field:"lastPostLoanDate", align:"left",width:80,formatter:format2Date},
			{title:"本次贷后日期",  field:"curPostLoanDate", align:"left",width:80,formatter:function(value) {
				if(value) {
					return format2Date(value);
				}
				return "首次贷后";
			}}
		]],
		onLoadSuccess: function(){
			pp_changePortalLoad('post_loan_remind',false);
		},
		onLoadError: function(){
			pp_changePortalLoad('post_loan_remind',false);
		}
		
	});
	$("#expire_remind").datagrid({
		idField:'id',
		border:false,
		fit:true,
		url:baseDir+'/postloan/postloanremind_getExpireRemind.jhtml',
		queryParams:{'page':1,'rows':5},
		columns:[[
			{title:"项目编号",  field:"PROJECT_NO", align:"left",width:120 },
			{title:"项目名称", field:"PROJECT_NAME", align:"left",width:160},
			{title:"到期日期",  field:"EXPIR_DATE", align:"left",width:80,formatter:function(value){
				return format2Date(value);}}
		]],
		onLoadSuccess: function(){
			pp_changePortalLoad('expire_remind',false);
		},
		onLoadError: function(){
			pp_changePortalLoad('expire_remind',false);
		}
	});
	//保全项目进度提醒
	$("#preserve_remind").datagrid({
		idField:'id',
		border:false,
		fit:true,
		url:baseDir+'/preserve/presLawsuit_getProgressRemind.jhtml',
		queryParams:{'page':1,'rows':5},
		columns:[[
			{title:"项目名称",  field:"projectName", align:"left",width:180 },
			{title:"流程名称",  field:"instanceName", align:"left",width:120},
			{title:"当前进度",  field:"status", align:"left",width:80,formatter:function(value){
				return pp_code.getValue("C1004", value);}}
		]],
		onLoadSuccess: function(){
			pp_changePortalLoad('e',false);
		},
		onLoadError: function(){
			pp_changePortalLoad('e',false);
		}
	});
});

$(function(){
	var user_menu_down_panel = '';
	
	$("body").append(user_menu_down_panel).click(function(){
		$("#user_menu_down_panel").hide();
	});
	
	$("#user_menu_list>li").each(function(){
		$(this).hover(function(){
			$(this).addClass("user_menu_down_panel-hover");
		},function(){
			$(this).removeClass("user_menu_down_panel-hover");
		});
	});
	
	var unl = $("#user_name_link");
	unl.bind("click",function(event){
		$("#user_menu_down_panel").show();
		return false;
	});
	var unlParent = unl.parent();
	var offset = unlParent.offset(),width = unlParent.width(),top = offset.top+unlParent.outerHeight(),left = offset.left;
	$("#user_menu_down_panel").css({'left':left,'top':top,"width":width-4});
});