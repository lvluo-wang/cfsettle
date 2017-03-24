/**
 * 主页面布局页面的初始化脚本
 */
	var groupMenuAction=baseDir+"/security/sysfunc_queryMenuGroup.jhtml";
	var subMenuAction=baseDir+"/security/sysfunc_queryMenu.jhtml";
	var messageAction=baseDir+"/web_ui/message_message.jhtml";
	var messageClearAction=baseDir+"/web_ui/message_clear.jhtml";
	var sysStatusRefreshAction=baseDir+"/web_ui/sys_status_refresh.jhtml";
	var logoutUrl=baseDir+"/security/login_logout.jhtml";
	var pageBodyUrl=baseDir+"/security/open.jhtml";

	function slidMenu(slidId,opt){
		var option=$.extend({
				offset:1,
				pageSize:4
			},opt||{});
		var itemCount=0;
		var index=1;
		var menuWidth=0;
		var widths=new Array();
		$("#"+slidId+" .menu_item").each(function(i){
			itemCount++;
			var $menu=$(this);
			widths[i]=menuWidth;
			menuWidth+=$menu.width();
		});
		
		var displayNav=function(){
				if(index==1){
					$(".prev").hide();
				}else{
					$(".prev").show();
				}
				if(index-1==itemCount-option.pageSize){
					$(".next").hide();
				}else{
					$(".next").show();
				}
		};
		displayNav();
		$("#"+slidId+" .menu_items").width(menuWidth);
		$("#"+slidId).parent("td").css("width",$("#"+slidId).width());
		$(".prev").bind('click',function(e){
				if(index==1){
						return false;
					}
				index-=option.offset;
				$("#"+slidId+" .menu_items").stop().animate({
					marginLeft:'-'+widths[index-1]+'px'
				},200);
				displayNav();
			});
		$(".next").bind('click',function(e){
				if(index-1==itemCount-option.pageSize){
						return false;
					}
				index+=option.offset;
				$("#"+slidId+" .menu_items").stop().animate({
					marginLeft:'-'+widths[index-1]+'px'
				},200);
				displayNav();
		});
	};
	function menuObject(){
		 var map=new Array();
		 var len=0;
		 this.putMenuConfig=function(id,text){
			 map[text]={"id":id,"text":text,"flag":"0"};
			 len++;
		 };
		 this.getMenuFlag=function(title){
			 return map[title].flag;
		 };
		 this.getMenu=function(title){
			 return map[title];
		 };
		 this.getMenuText=function(title){
			 return map[title].text;
		 };
		 this.setMenuFlag=function(title,f){
			 map[title].flag=f;
		 };
		 this.getLength=function(){
			 return len;
		 };
	 }
	
	function initMenus(userMenuStr){
		if(userMenuStr){
			if(userMenuStr.indexOf("{")!=0&&userMenuStr.indexOf("[")!=0){
				
			}else{
				var menuMap=new menuObject();
				var data=parseObj(userMenuStr);
				var mts="";
				for(var i=0;i<data.length;i++){
					var menuId=data[i].id;
					var menuText=data[i].text;
					menuMap.putMenuConfig(menuId,menuText);
					var mt='<div title="'+data[i].text+'"><ul id="menu'+menuId+'" class="easyui-tree"></ul></div>';					
					mts+=mt;
				}
				$(mts).appendTo($("#menuGroup"));
				$("#menuGroup").accordion({
					onSelect:function(title){
						var menuO=menuMap.getMenu(title);
						if("0"==menuO.flag){
							$("#menu"+menuO.id).tree({
								url:subMenuAction+"?ajax_request=async&funcId="+menuO.id+"&flag="+Math.random()*99999,
								onClick:function(node){
									$(this).tree('toggle', node.target);
									if(node.attributes.url&&node.attributes.url.length>2){
										var jobUrl=node.attributes.url;
											if(isWorkSpaceBusy==false){
												updateWorkapace(jobUrl,node.text);
											}
										jobUrl=null;
									}
								},
								onLoadSuccess:function(node, da){
									if(da.error){
										error(da.error);
										return;
									}

								}
							});
							
							menuMap.setMenuFlag(title,"1");
						}
						return true;
						
					}
				});
				mts=undefined;
				/**
				 * 释放内存
				 */
				setNull(data);
			}
			
		}
	}
	 
	
	/**
	 * 初始化消息组件
	 * @param message
	 */
	function initMessageComponent(message,error){
		/*双击关闭TAB选项卡*/
		$("#msgs .tabs-inner").dblclick(function(){
			doPost(messageAction,null,function(result){
				if(result){
					if(result.indexOf("{")!=0){
						
					}else{
						var data=eval('('+result+')');
						if(data.error){
		        			error(data.error);
						}else{
							$("#"+message).html(data.message);
							$("#"+error).html(data.errors);
						}
						setNull(data);
					}
				}
			});
		});
		$("#msgs").bind('contextmenu',function(e){
			$('#mm2').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
			
			var subtitle =$(this).children("span").text();
			$('#mm2').data("currtab",subtitle);
			
			return false;
		});
		$('#mm2-tabclear').click(function(){
			$("#"+message).html("");
			$("#"+error).html("");
		});
		$("#mm2-tabrefresh").click(function(){
			doPost(messageAction,null,function(result){
				if(result){
					if(result.indexOf("{")!=0){
						
					}else{
						var data=eval('('+result+')');
						if(data.error){
		        			error(data.error);
						}else{
							$("#"+message).html(data.message).show();
							$("#"+error).html(data.errors).show();
						}
						setNull(data);
					}
				}
			});
		});
		$("#mm2-tabclearAll").click(function(){
			doPost(messageClearAction,null,function(result){
				$("#"+message).html("");
				$("#"+error).html("");
			});
		});
//		var panel=$("#jobView").layout('panel','south');
//		panel.panel({
//			onOpen:function(){
//				doPost(messageAction,null,function(result){
//					if(result){
//						if(result.indexOf("{")!=0){
//							
//						}else{
//							var data=eval('('+result+')');
//							if(data.error){
//			        			error(data.error);
//							}else{
//								$("#"+message).html(data.message).show();
//								$("#"+error).html(data.errors).show();
//							}
//							setNull(data);
//						}
//					}
//				});
//			}
//		});
		//setTimeout(function(){
		//	$('#jobView').layout('collapse','south');
		//},2000);
	}
	/**
	 * 初始化系统状态自动更新
	 */
	function initSysStatus(){
		try{
		var itId="";
		itId=setInterval(function(){
			var url=sysStatusRefreshAction+"?randomNum="+Math.random()*99999+"&ajax_request=async";
			 $.ajax({
				 type:"POST",
				 url:url,
				 data:null,
				 cache:false,
				 error:function(result){
					 clearInterval(itId);
					 //$.messager.alert(global.requestFailed,global.requestFailedInfo,'error');
					 error(global.requestFailedInfo);
				 },
				 success:function(result){
					 if(result){
							var obj=eval('('+result+')');
							if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
			        			clearInterval(itId);
			        			closeMask();
							}
							if(obj.sys_status){
								var status=obj.sys_status;
								if(status.info){
									var level=status.level;
									var info=status.info;
									if(level==2){
										mask();
										updateMask(info);
									}else{
										updateMask(info);
										closeMask();
									}
									updateSysStatus(info);
								}
							}
						}
				 }
			 });
		},3000);
		}catch(e){
			//$.messager.alert(global.error,e,'error');
			error(e);
		}finally{
			closeMask();
		}
	}	/**
	 * 发起请求后，响应回来的页面在工作区中打开
	 * @param url
	 * @param param
	 * @param title
	 */
	function updateWorkapace(url,title){
		//$("#work_frame").attr('src',pageBodyUrl+"?randomNum="+Math.random()*99999+"&targetUrl="+baseDir+url);
		//if( url.indexOf('est') < 0 ){
			if( url.indexOf("?") < 0 ){
				url += "?";
			}else{
				url += "&";
			}
			url += "wrap=1";
		//}
		$("#work_frame").attr('src',baseDir+url);
		$("#jobs").tabs('update',{
			tab:$("#job_work_area"),
			options:{
				title:title
			}
			
		});
		$("#jobs").tabs('select',title);
		
	}
	 function updateSysStatus(msg){
		 $("#sysStatus").html(msg);
	 }
	 /**
	  * 退出
	  */
	function logout(){
		var url=logoutUrl+"?flag="+Math.random()*99999;
		window.location.href=url;
	}
	slidMenu("menu_slider");
	
$(function(){
		try{
			closeloadMask();
		var wrap=$(window);
		var winWidth=wrap.width();
		var winHeight=wrap.height();
		if(winWidth<1000){
			winWidth=1000;
		}
		if(winHeight<600){
			winHeight=600;
		}
		var wh = {"width":winWidth,"height":winHeight};
		$("body").css(wh).layout();
		
		}catch(e){
			closeloadMask();
		}
	});
	setTimeout(function(){
		initMenus(userMenuStr);
		$("#msgs").tabs();
		$("#jobs").tabs({
			"onSelect":function(title){
				if(title=="欢迎使用系统"){
					//在页面第一次载入，与每次单击选择“欢迎使用系统”页签时，会调用以下方法
					$("#leftTaskRemindDiv").empty();
					$("#rightTaskRemindDiv").empty();
					if(typeof initPortal != 'undefined'&&$.isFunction(initPortal)){
						initPortal();
					}
					
				}
			}
		});
		//if(typeof initPortal != 'undefined'&&$.isFunction("initPortal")){
		//initPortal();
		//}
		//initMessageComponent("messageView","errorView");
		//$("#jobView").layout("collapse","south");
	},0);