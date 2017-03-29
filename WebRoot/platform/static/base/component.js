/**
 * 仅使用于main.jsp
 */
	var PREFIX_CONTENT = '<script>function initPage(){}</script>';
	var SUBFIX_CONTENT = '<script>$(function(){if( typeof proxy$Array != "undefined" && $.isArray(proxy$Array) ){var fn = null;while( proxy$Array.length > 0 ){fn = proxy$Array.shift();if( typeof fn != "undefined" && $.isFunction(fn) ){fn();}}}});function renderPage(){if(typeof initPage != "undefined"){initPage();}}setTimeout("renderPage()",0);</script>';
	var queryComponentUrl=baseDir+"/web_ui/query_query.jhtml";
	var securityACL=baseDir+"/security/sysfunc_getACL.jhtml";
	var fileDownLoadUrl=baseDir+"/component/attachment_downLoad.jhtml";
	var imgServerFileDownLoadUrl=baseDir+"/component/attachment_downloadImgServerFile.jhtml";
	var pageBodyUrl=baseDir+"/platform/common/pageBody.jsp";
	var codeUrl=baseDir+"/dictionary/dictionary_findValues.jhtml";
	var codeSyncUrl=baseDir+"/dictionary/dictionary_findSyncValues.jhtml";
	var TASK_HISTORY_ACTION = baseDir+"/bpm/task_toViewTask.jhtml";
	var prodUrl=baseDir+"/product/product_findProdNames.jhtml";
	var prodSyncUrl=baseDir+"/product/product_findProdName.jhtml";
	var chooseBranchUrl=baseDir+"/web_ui/choose_branch.jhtml";
	var chooseSuperBranchUrl = baseDir+"/security/brch_selectBrch.jhtml";
	var chooseMemberUrl=baseDir+"/web_ui/choose_member.jhtml";
	var chooseProductUrl=baseDir+"/web_ui/choose_product.jhtml";
	var chooseAttrProductUrl = baseDir+"/web_ui/choose_attrProduct.jhtml";
	var chooseCodeMetaUrl=baseDir+"/web_ui/choose_codeMeta.jhtml";
	var chooseCodeUrl=baseDir+"/web_ui/choose_code.jhtml";
	var autoCodeUrl=baseDir+"/autocode/autocode_genCode.jhtml";
	var chooseMiNoUrl=baseDir+"/web_ui/choose_miNo.jhtml";
	var chooseAuditRouteUrl=baseDir+"/web_ui/choose_auditRoute.jhtml";
	var chooseCustinfoUrl=baseDir+"/custinfo/companyCustinfo_toChoose.jhtml";
	var chooseAssessMeetingUrl=baseDir+"/assessMeeting/assessMeeting_choose.jhtml";
	var xhhCodeUrl=baseDir+"/code/codeItem_findValues.jhtml";
	
	/**
	 * 初始化查询组件
	 * @param componentId 组件id
	 * @param divId 显示的视图id
	 */
	function initQueryComponent2(windowId,componentId,divId,componentUrl){
		this.openWindow=function(){
			$("#"+windowId).window('open').window('refresh');
		};
		this.closeWindow=function(){
			$("#"+windowId).window('close');
		};
		this.getQueryComponentQuery=function(){
			var data="{";
			$("div[id='"+divId+"']").find("input,select").each(function(){
				var name=$(this).attr('name');
				var value=$(this).attr('value');
				var tmp="'"+name+"':'"+value+"',";
				data=data+tmp;
			});
			data=data+"'flag':'"+Math.random()*99999+"'}";
			var obj=str2obj(data);
			return obj;
		};
		this.clearComponentQuery=function(){
			$("#queryComponentForm").form("clear");
		};
		var url=queryComponentUrl+'?queryComponentId='+componentId+'&flag='+Math.random()*99999;
		//增加自定义组件查询
		if(componentUrl){
			url=baseDir+componentUrl+"?flag="+Math.random()*99999;
		}
		doPost(url,null,function(result){
			if(result){
				if(result.indexOf('{')!=0){
					if(result.indexOf('<body>')!=-1){
						document.open();
						document.write(result);
						document.close();
					}
				}else{
					var obj=eval('('+result+')');
					if(obj.error){
		    			//$.messager.alert(global.error,obj.error,'error');
		    			error(obj.error);
		    			return;
					}
					var id=obj.id;
					var queryList=obj.queryList;
					var str="";
					for(var i=0;i<queryList.length;i++){
						var query=queryList[i];
						var label=query.label;
						var data_name=query.queryName;
						var data_value=query.queryValue;
						var value_list=query.valueList;
						var data_type=query.valueType;
						if(data_type==null||data_type==''){
							data_type='String';
						}
						var dataView="";
						if(data_type=='List'){
							var dv=eval('('+value_list+')');
							dataView+="<select class='query_lang_select' name='"+data_name+"'>";
							//自动加入全部的选项
							dataView+="<option value=''>"+global.total+"</option>";
							for(var k=0;k<dv.length;k++){
								var opId=dv[k].id;
								var opT=dv[k].text;
								dataView+="<option value='"+opId+"'>"+opT+"</option>";
							}
							dataView+="</select>";
						}else{
							dataView='<input type="text" name="'+data_name+'" value="'+data_value+'"/>';
						}
						var operate_list=query.operate;
						var op="";
						if(operate_list!=null&&operate_list.length>0){
							for(var k=0;k<operate_list.length;k++){
								op=op+'<option value="'+operate_list[k]+'">'+getOperate(operate_list[k])+'</option>';
							}
						}else{
							op=getOptions(data_type);
						}
						str+='<div class="query_div">';
						str+="<label>"+label+'</label>';
						str=str+'<select class="query_short_select" name="'+data_name+'_op">';
						str=str+op;
						str=str+'</select>&nbsp;';
						str=str+dataView+'</div>';
					}
					var sortList=obj.sortList;
					if(sortList!=null&&sortList.length>0){
						str+='<div class="query_div">';
						str+='<label><font color="red">↑</font><font color="green">↓</font></label>';
						str+='<select class="query_lang_select" name="sortList" onChange="doSearch()">';
						for(var i=0;i<sortList.length;i++){
							var sort=sortList[i];
							var viewName=sort.viewName;
							var sortName=sort.sortName;
							var sortType=sort.sortType;
							if(sortType=='asc'){
								str+="<option value='"+sortName+","+sortType+"' style='color:red'>"+viewName+" ↑</option>";
							}else{
								str+="<option value='"+sortName+","+sortType+"' style='color:green'>"+viewName+" ↓</option>";
							}
						}
						str+="</select></div>";
					}
				
					$("#"+divId).html($(str));
					//$(str).appendTo($("#"+divId));
					$("#"+windowId).window({
						title:global.queryWindow,
						top:80,
						left:"20%",
						width:760,
						height:180,
						border:false,
						minimizable:false,
						maximizable:false,
						draggable:false
					}).window('close');
				}
				
			}});
		var getOptions=function(dataType){
			if(dataType=='Double')
				return '<option value="eq">=</option><option value="gt">&gt;</option><option value="ge">&gt;=</option><option value="lt">&lt;</option><option value="le">&lt;=</option>';
			if(dataType=='String')
				return '<option value="eq">=</option><option value="like">like</option>';
			if(dataType=='Date')
				return '<option value="eq">=</option><option value="gt">&gt;</option><option value="ge">&gt;=</option><option value="lt">&lt;</option><option value="le">&lt;=</option>';
			else
				return '<option value="eq">=</option>';
		};
		var getOperate=function(op){
			if(op=='eq')
				return '=';
			if(op=='gt')
				return '&gt;';
			if(op=='ge')
				return '&gt;=';
			if(op=='lt')
				return '&lt;';
			if(op=='le')
				return '&lt;=';
			if(op=='like')
				return 'like';
		};
		var getClass=function(dataType){
			if(dataType=='Double')
				return '';
			if(dataType=='String')
				return '';
			if(dataType=='Date')
				return '';
			else
				return '';
		};
	}
	/**
	 * 初始化数据分页组件（旧的方法，不建议使用）
	 * @param tableId 页面table id
	 * @param url 对应的action url
	 * @param frozenColumns 需要冻结的列数组
	 * @param columns 显示的列项
	 */
	
	function initDataComponent(tableId,url,frozenColumns,columns){
		var parm={};
		$('#'+tableId).datagrid({
			iconCls:'icon-ok',
			width:'auto',
			height:320,
			nowrap: false,
			striped: true,
			fit:true,
			collapsible:true,
			url:url,
			remoteSort: false,
			frozenColumns:frozenColumns,
			columns:columns,
			pagination:true,
			rownumbers:false,
			toolbar:false,
			onLoadError:function(){
					error(global.dataLoadError);
				},
			onLoadSuccess:function(data){
				var panel=$("#"+tableId).datagrid('getPanel');
				var head=panel.find("div.datagrid-header");
				head.find("input[type=checkbox]").each(function(){
					$(this).attr("checked",false);
				});
				if(data.error){
					error(data.error);
				}
			}
		});
		this.batCheck=function(col,value){
			var rows = $("#"+tableId).datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				if( typeof value=="string"){
					if(value!=rows[i][col]){
						return false;
					}
				}else{
					if(value.length){
						var flag=false;
						for(var j=0;j<value.length;j++){
							if(value[j]==row[i][col]){
								flag=true;
								break;
							}
						}
						if(flag==false){
							return false;
						}
					}
				}
			}
			return true;
		};
		this.queryByParam=function(param){
			var rows=$("#"+tableId).datagrid("options").pageSize;
			parm=param;
			query(1,rows);
		};
		var query=function(page,rows){
			mask();
			$("#"+tableId).datagrid("getPager").pagination({pageNumber:page,pageSize:rows});
			$("#"+tableId).datagrid("options").pageNumber=page;
			$("#"+tableId).datagrid("options").pageSize=rows;
			var sortName=$("#"+tableId).datagrid("options").sortName;
			var sortOrder=$("#"+tableId).datagrid("options").sortOrder;
			var pt=$.extend(parm||{},{"page":page,"rows":rows});
			if(sortName!=null){
				$.extend(pt,{"sortName":sortName,"sortOrder":sortOrder});
			}
			 doPost(
			        url,
			        pt,
			        function(result) {
			        	if(result){
							if(result.indexOf('{')!=0){
								if(result.indexOf('<body>')!=-1){
									document.open();
									document.write(result);
									document.close();
								}
							}else{
				        		var obj=eval('('+result+')');
				        		if(obj.error){
				        			//$.messager.alert(global.error,obj.error,'error');
				        			error(obj.error);
				        		}else{
									$("#"+tableId).datagrid('loadData',obj);
									$("#"+tableId).datagrid('options').queryParams=parm;
								}
				        		closeMask();
							}
						}
			        	closeMask();
			        }
			    );
		};
		var pg=$('#'+tableId).datagrid("getPager");
		if(pg){
			$(pg).pagination({
				onSelectPage:function(pageNumber,pageSize){
					query(pageNumber,pageSize);
				}
			});
		}
	}
	/**
	 * 文件上传
	 * @param {Object} fileInputId
	 * @param {Object} opt
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function FileUpload(fileInputId,opt){
		var defaultConfig=$.extend({
			uploader:baseDir+'/platform/static/plugins/uploadfy/uploadify.swf?tmp='+ new Date().getTime(),
			fileDataName:fileInputId,
			cancelImg:baseDir+'/platform/static/images/cancel.png',
			removeCompleted:false,
			auto:false
		},opt||{});
		$("#"+fileInputId).uploadify(defaultConfig);
		this.upload=function(param){
			if(param){
				defaultConfig=$.extend(defaultConfig,{scriptData:param});
				$("#"+fileInputId).uploadifySettings('scriptData',param);
			}
			$("#"+fileInputId).uploadifyUpload();
			
		};
		this.getFileNum=function(){
			var num = $("#"+fileInputId).uploadifySettings('queueSize');
			return num;
		};
	}
	function FileUploadComponent(fileInputId,sid,opt){
		var defaultConfig=$.extend({
			script:baseDir+'/component/attachment_upload.jhtml;jsessionid='+sid,
			uploader:baseDir+'/platform/static/plugins/uploadfy/uploadify.swf?tmp='+ new Date().getTime(),
			fileDataName:fileInputId,
			queueID : 'fileQueue', //和存放队列的DIV的id一致 
			auto : false, //是否自动开始 
			method: 'Get',
			multi: false, //是否支持多文件上传 
			buttonText: 'Browse', //按钮上的文字 
			displayData: 'speed',//有speed和percentage两种，一个显示速度，一个显示完成百分比 
			simUploadLimit: 1, //一次同步上传的文件数目 
			sizeLimit: 31457280, //设置单个文件大小限制 
			queueSizeLimit: 1, //队列中同时存在的文件个数限制 
			cancelImg:baseDir+'/platform/static/images/cancel.png',
			fileDesc : '所有格式', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
			fileExt : '*.*',//允许的格式   
		   onError : function(event, queueID,fileObj) {
			    	info('文件: ' + fileObj.name + '上传失败');
				},
		   onCancel : function(event, queueID,fileObj) {
					//info('已取消上传文件: ' + fileObj.name);
				}
		},opt||{});
		$("#"+fileInputId).uploadify(defaultConfig);
		this.upload=function(param){
			if(param){
				defaultConfig=$.extend(defaultConfig,{scriptData:param});
				$("#"+fileInputId).uploadifySettings('scriptData',param);
			}
			$("#"+fileInputId).uploadifyUpload();
		};

	}
	/**
	 * 数据分页组件(建议使用)
	 * @param tableId 页面table id
	 */
	function DataPageComponent(tableId,opt){
		var defaultConfig=$.extend({
				fit:true,//自适应屏幕高度和宽度
				striped:true,//隔行变色
				pagination:true,//是否支持分页
				queryParams:{},
				border:false,
				url:null,
				onLoadError:function(){
					error(global.dataLoadError);
				},
				onLoadSuccess:function(data){
					var panel=$("#"+tableId).datagrid('getPanel');
					var head=panel.find("div.datagrid-header");
					head.find("input[type=checkbox]").each(function(){
						$(this).attr("checked",false);
					});
					$("#"+tableId).datagrid("options").queryParams={};
					if(data.error){
						error(data.error);
					}
				},
				onSortColumn:function(sort, order){
					$("#"+tableId).datagrid("options").queryParams=defaultConfig.queryParams;
				}
		},opt||{});
		/**
		 * 设置数据组件的配置参数
		 * 
		 */
		this.setOption=function(opt){
			defaultConfig=$.extend(defaultConfig,opt||{});
		};
		/**
		 * 加载远程数据
		 */
		this.load=function(){
			$("#"+tableId).datagrid(defaultConfig);
			if(defaultConfig.pagination){
				var pg=$('#'+tableId).datagrid("getPager");
				if(pg){
					$(pg).pagination({
						onSelectPage:function(pageNumber,pageSize){
							query(pageNumber,pageSize);
						},
						showPageList:false
					});
				}
			}
			
		};
		/**
		 * 重新加载
		 */
		this.reLoad=function(){
			$("#"+tableId).datagrid("reload");
		};
		var queryByParameter=function(param){
			var rows=$("#"+tableId).datagrid("options").pageSize;
			defaultConfig.queryParams=param;
			query(1,rows);
		};
		/**
		 * param：{key:value}
		 * 基于条件的查询
		 */
		this.queryByParam=function(param){
			queryByParameter(param);
		};
		
		var refreshComponent=function(){
			var rows=$("#"+tableId).datagrid("options").pageSize;
			var num=$("#"+tableId).datagrid("options").pageNumber;
			query(num,rows);
		};
		/**
		 * 刷新当前页
		 */
		this.refresh=function(){
			refreshComponent();
		};
		/**
		 * 批量校验已选中的列数据是否与预期一致
		 *  col 指定列
		 *  value 字符 或字符数组
		 * 返回校验结果
		 */
		this.batCheck=function(col,value){
			var rows = $("#"+tableId).datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				if( typeof value=="string"){
					if(value!=rows[i][col]){
						return false;
					}
				}else{
					if(value.length){
						var flag=false;
						for(var j=0;j<value.length;j++){
							if(value[j]==row[i][col]){
								flag=true;
								break;
							}
						}
						if(flag==false){
							return false;
						}
					}
				}
			}
			return true;
		};
		/**
		 * 获取选中的数据
		 *  col 列
		 * 返回数组
		 */
		this.getSelected=function(col){
			var ids = [];
			var rows = $('#'+tableId).datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i][col]);
			}
			return ids;
		};
		/**
		 * 获取选中的数据字符串
		 * 返回‘,’分隔的字符串 
		 */
		this.getSelectedValue=function(col){
			var ids = [];
			var rows = $('#'+tableId).datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i][col]);
			}
			return ids.join(',');
		};
		/**
		 * 判断是否为单选
		 * 
		 */
		this.isSingleSelect=function(){
			var rows = $('#'+tableId).datagrid('getSelections');
			if(1==rows.length){
				return true;
			}
			return false;
		};
		/**
		 * 获取第一个选中的列值
		 * 
		 */
		this.getFirstSelectValue=function(col){
			var row=$("#"+tableId).datagrid("getSelected");
			return row[col];
		};
		/**
		 * 向服务器发送请求，请求处理完毕刷新数据组件
		 *  
		 */
		this.call=function(req,param){
			doPost(req,param,function(result){
				if(result){
					if(result.indexOf('{')!=0){

					}else{
		        		var obj=eval('('+result+')');
		        		if(obj.error){
		        			error(obj.error);
		        			return;
		        		}
					}
				}
				refreshComponent();
			});
		};
		
		this.remove=function(config){
			var removeConfig = $.extend({key:"id"},config||{});
			if( removeConfig.key != null && removeConfig.url != null ){
				if (isSelected(tableId,removeConfig.key)){
					var ids=getMutSelected(tableId,removeConfig.key);
					$.messager.confirm(global.alert,global.prompt_delete, function(r){
						if(r){				
							doPost(removeConfig.url,{"ids":ids}, function(result) {
								if(result){
									var obj=str2obj(result);
									if(obj.error){
										error(obj.error);
										return;
									}
								}
								refreshComponent();
							 });					
						}
					});
				}
			}
		};
		
		/**
		 * form 提交
		 */
		this.formSubmit=function(conf){
			var defaultConf=$.extend({
				submitUrl:null,
				submitForm:null,
				queryForm:null,
				refreshTable:true,
				winId:null,
				closeWin:true
			},conf||{});
			if(null!=defaultConf.submitUrl){
				var formParam=null;
				if(null!=defaultConf.submitForm){
					if($("#"+defaultConf.submitForm).form('validate')){
						formParam=formToObject(defaultConf.submitForm);
						doPost(defaultConf.submitUrl,formParam,function(result){
							if(result){
								var ooo=str2obj(result);
								if(ooo.error){
									error(ooo.error);
									return;
								}
							}
							if(true==defaultConf.refreshTable){
								if(null!=defaultConf.queryForm){
									var queryFormParam=formToObject(defaultConf.queryForm);
									queryByParameter(queryFormParam);
								}else{
									refreshComponent();
								}
								
							}
							if(null!=defaultConf.winId){
								if(true==defaultConf.closeWin){
									$("#"+defaultConf.winId).window('close');
								}
							}
						});
					}
					
				}
				
			}
		};
		var query=function(page,rows){
			if(!defaultConfig.url&&defaultConfig.url.length<1){
				return;
			}
			var tmpUrl;
			 if(defaultConfig.url.indexOf('?')==-1){
				 tmpUrl=defaultConfig.url+"?page="+page+"&rows="+rows;
			 }else{
				 tmpUrl=defaultConfig.url+"&page="+page+"&rows="+rows;
			 }
			 var sortName=$("#"+tableId).datagrid("options").sortName;
			var sortOrder=$("#"+tableId).datagrid("options").sortOrder;
			if(sortName!=null){
				tmpUrl=tmpUrl+"&sortName="+sortName+"&sortOrder="+sortOrder;
			}
			$("#"+tableId).datagrid("getPager").pagination({pageNumber:page,pageSize:rows});
			$("#"+tableId).datagrid("options").pageNumber=page;
			$("#"+tableId).datagrid("options").pageSize=rows;
			 doPost(
			        tmpUrl,
			        defaultConfig.queryParams,
			        function(result) {
			        	if(result){
							if(result.indexOf('{')!=0){

							}else{
				        		var obj=eval('('+result+')');
				        		if(obj.error){
				        			error(obj.error);
				        		}else{
									$("#"+tableId).datagrid('loadData',obj);
								}
							}
						}
			        }
			    );
		};
		
	}
	

	 /**
	  * 数据分页组件的提交
	  * @param url 请求URL
	  * @param formId  绑定的form
	  * @param datagridId  数据组件视图id
	  */
	 function formSubmitForDatagrid(url,formId,datagridId){
		 mask();
		 var parm=$("#"+formId).serialize();
		 doPost( url,
				 parm,
		        function(result) {
			
		        	if(result){
		        		if(result.indexOf('{')!=0){
		        			if(result.indexOf('<body>')!=-1){
		        				document.open();
								document.write(result);
								document.close();
		        			}
		        		}else{
			        		var obj=eval('('+result+')');
			        		if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
							}else{
								if(obj.success){
									//$.messager.alert(global.info,obj.success,'info');
									info(obj.success);
								}
								$("#"+datagridId).datagrid('reload');
								
							}
		        		}
		        	}else{
		     
		        		$("#"+datagridId).datagrid('reload');
		        	}
		        	closeMask();
		        }
		    );
	 }

	 /**
	  * 基于数据组件的查询
	  * @param url
	  * @param parm 此条件会绑定在数据组件上
	  * @param datagridId
	  */
	 function queryForDatagrid(url,parm,datagridId){
		mask();
		var rows=$("#"+datagridId).datagrid("options").pageSize;
		var tmpUrl="";
		 if(url.indexOf('?')==-1){
				 tmpUrl=url+"?page="+1+"&rows="+rows;
		 }else{
			 tmpUrl=url+"&page="+1+"&rows="+rows;
		 }
		
		 doPost(tmpUrl,
		 	parm, 
			function(result) {
			 if(result){
	 				if(result.indexOf('{')!=0){
	 					if(result.indexOf('<body>')!=-1){
	 						document.open();
							document.write(result);
							document.close();
	 					}
	 				}else{
		        		var obj=eval('('+result+')');
		        		if(obj.error){
		        			//$.messager.alert(global.error,obj.error,'error');
		        			error(obj.error);
		        		}else{
							$("#"+datagridId).datagrid('loadData',obj);
							$("#"+datagridId).datagrid('options').queryParams=parm;
						}
		        		closeMask();
	 				}
		        }
			 }
		    );
	 }
	 /**
	  * 发起请求并刷新数据组件 用于删除、添加、编辑等
	  * @param url
	  * @param datagridId
	  */
	 function requestAndRefresh(url,datagridId){
		 mask();
		 doPost(url,null, function(result) {
		        	if(result){
		        		if(result.indexOf('{')!=0){
		        			if(result.indexOf('<body>')!=-1){
		        				document.open();
								document.write(result);
								document.close();
		        			}
		        		}else{
			        		var obj=eval('('+result+')');
			        		if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
							}else{
								$("#"+datagridId).datagrid('reload');
								
							}
		        		}
		        	}else{
		        		$("#"+datagridId).datagrid('reload');
		        	}
		        	closeMask();
		        }
		    );
	 }
	 
	  /**
    * 发起请求并刷新数据组件 用于删除、添加、编辑等
    * @param url
    * @param datagridId
    */
   function requestByParamAndRefresh(url,param,datagridId){
     mask();
     doPost(url,param, function(result) {
              if(result){
                if(result.indexOf('{')!=0){
                  if(result.indexOf('<body>')!=-1){
                    document.open();
                document.write(result);
                document.close();
                  }
                }else{
                  var obj=eval('('+result+')');
                  if(obj.error){
                    //$.messager.alert(global.error,obj.error,'error');
                    error(obj.error);
              }else{
                $("#"+datagridId).datagrid('reload');
                
              }
                }
              }else{
                $("#"+datagridId).datagrid('reload');
              }
              closeMask();
            }
        );
   }
   /**
    * 局部区域异步页面加载
    * @param domId
    * @param url
    * @param param
    */
   function requestAtDom(domId,url,param){
		doPost(url,param,function(result){
			if(!printError(result)){
				$("#"+domId).html(result);
				$.parser.parse($("#"+domId));
				$.xcarsParser.parse($("#"+domId));	
			}
		});
	}
	 /**
	  * 在指定的窗口显示请求的结果
	  * @param url
	  * @param win
	  * @param title
	  */
	 function requestAtWindow(url,win,title,param,windowParam){
		// mask();
		 doPost(url,param,function(result) {
			 if(result){
				 if(result.indexOf("{")!=0){
					 if(result.indexOf('<body>')!=-1){
						 document.open();
							document.write(result);
							document.close();
					 }else{
						var w = $('#'+win);
						
						w.html(PREFIX_CONTENT+ "<script>$(function(){$.parser.parse('#" + win + "');});</script>" + result + SUBFIX_CONTENT);
						
						//$.parser.parse(w);
//						if(!$.data(w[0], 'window')){
//							w.window({closed:true,modal:true});
//						}
						windowParam = windowParam || {};
						if (windowParam.relDocTop) {
							windowParam.top = $(document).scrollTop() + windowParam.relDocTop;
						}
						w.show();
						w .window($.extend({
								title:title,
								resizable:false,
								minimizable:false,
								maximizable:false,
								collapsible:false,
								closable:true,
								//closed:true,
								//content:PREFIX_CONTENT+result + SUBFIX_CONTENT,
								modal:true
							},windowParam||{}));
						 w.window('restore');
						 if(!w.hasClass("easyui-window"))
						 {
						    w.addClass("easyui-window");
						 }
						$.xcarsParser.parse(w);
					 }
				 }else{
					 var obj=eval('('+result+')');
		        		if(obj.error){
		        			//$.messager.alert(global.error,obj.error,'error');
		        			error(obj.error);
						}
				 }
	        		
	        	}
			// closeMask();
		 });
		 
	 }
	 function openDialog(url,id,title){
		 
	 }
	/**
	 * 表单提交后刷新指定的树形结构
	 * @param url
	 * @param form
	 * @param tree
	 */
	function formSubmitForTree(url,form,tree){
		 mask();
		 var parm=$("#"+form).serialize();
		 doPost( url,
				 parm,
		        function(result) {
			
		        	if(result){
		        		if(result.indexOf('{')!=0){
		        			if(result.indexOf('<body>')!=-1){
		        				document.open();
								document.write(result);
								document.close();
		        			}
		        		}else{
			        		var obj=eval('('+result+')');
			        		if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
							}else{
								$("#"+tree).tree('reload');
								
							}
		        		}
		        	}else{
		     
		        		$("#"+tree).tree('reload');
		        	}
		        	closeMask();
		        }
		    );
	 }
	/**
	 * 处理请求并刷新指定的树形结构
	 * @param url
	 * @param tree
	 */
	 function requestAndRefreshTree(url,tree){
		 mask();
		 doPost( url,
		        null,
		        function(result) {
		        	if(result){
		        		if(result.indexOf('{')!=0){
		        			if(result.indexOf('<body>')!=-1){
		        				document.open();
								document.write(result);
								document.close();
		        			}
		        		}else{
			        		var obj=eval('('+result+')');
			        		if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
							}else{
								var node=$("#"+tree).tree('getSelected');
								if(node){
									var parentNode=$("#"+tree).tree('getParent',node.target);
									$("#"+tree).tree('reload',parentNode.target);
								}else{
									$("#"+tree).tree('reload');
								}
								
							}
		        		}
		        	}else{
		        		var node=$("#"+tree).tree('getSelected');
						if(node){
							var parentNode=$("#"+tree).tree('getParent',node.target);
							$("#"+tree).tree('reload',parentNode.target);
						}else{
							$("#"+tree).tree('reload');
						}
		        	}
		        	closeMask();
		        }
		    );
	 }
	 /**
	  * 在指定的标签页中显示请求的结果
	  * @param url
	  * @param tabs 标签页组件 
	  * @param tab 子页
	  * @param title 标题
	  */
	 function requestAtTab(url,tabs,tab,title){
		 mask();
		 doPost(url,null, function(result) {
			 if(result){
				 if(result.indexOf("{")!=0){
					 $("#"+tabs).tabs('update',{
							tab:$("#"+tab),
							options:{
								title:title,
								content:PREFIX_CONTENT+result + SUBFIX_CONTENT
							}
							
						});
				 }else{
					 var obj=eval('('+result+')');
		        		if(obj.error){
		        			//$.messager.alert(global.error,obj.error,'error');
		        			error(obj.error);
						}
				 }
	        		
	        	}
			 closeMask();
		 });
		 
	 }
	 /**
	  * 树形机构中的节点移动，并参数请求
	  * @param url
	  * @param targetId 目标节点
	  * @param sourceId 源节点
	  * @param point 方位top,botton,append
	  * @param treeId
	  */
	 function requestByTreeMove(url,targetId,sourceId,point,treeId){
		 doPost(
				 url,
				 {
					 sourceId:sourceId,
					 targetId:targetId,
					 point:point
				 },
				 function(result){
					 try{
					 if(result){
						 var obj=eval('('+result+')');
			        		if(obj.error){
			        			$("#"+treeId).tree('reload');
			        			$.messager.alert(global.error,obj.error,'error',function(){
			        				$("#"+treeId).tree('expandAll');
			        			});
			        			
							}
					 }
					}catch(e){
					}finally{

					}
				 });
	 }
	 /**
	  * 窗口中的form提交后刷新数据组件并关闭窗口
	  * @param url 请求URL
	  * @param formId  绑定的form
	  * @param datagridId  数据组件视图id
	  */
	 function windowFormSubmitForDatagrid(url,formId,datagridId,win){
		 mask();
		 var parm=$("#"+formId).serialize();
		 doPost( url,
				 parm,
		        function(result) {
			
		        	if(result){
		        		if(result.indexOf('{')!=0){
		        			if(result.indexOf('<body>')!=-1){
		        				document.open();
								document.write(result);
								document.close();
		        			}
		        		}else{
			        		var obj=eval('('+result+')');
			        		if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
							}else{
								$("#"+win).window("close");
								$("#"+datagridId).datagrid('reload');
								
							}
		        		}
		        	}else{
		     
		        		$("#"+datagridId).datagrid('reload');
		        		$("#"+win).window("close");
		        	}
		        	closeMask();
		        }
		    );
	 }
	 /**
	  * 窗口中的form提交并关闭窗口
	  * @param url 请求URL
	  * @param formId  绑定的form
	  * @param datagridId  数据组件视图id
	  */
	 function windowFormSubmitAndClose(url,formId,win){
		 mask();
		 var parm=$("#"+formId).serialize();
		 doPost( url,
				 parm,
		        function(result) {
			 		
		        	if(result){
		        		if(result.indexOf('{')!=0){
		        			if(result.indexOf('<body>')!=-1){
		        				document.open();
								document.write(result);
								document.close();
		        			}
		        		}else{
			        		var obj=eval('('+result+')');
			        		if(obj.error){
			        			//$.messager.alert(global.error,obj.error,'error');
			        			error(obj.error);
							}else{
								$("#"+win).window("close");
							}
		        		}
		        	}else{
		     
		        		$("#"+win).window("close");
		        	}
		        	closeMask();
		        }
		    );
	 }
	 /**
	  * 访问控制，所有dom元素包含acl属性，如acl="/user/userAdd.jhtml"，
	  * acl的属性中的值如果是需要权限控制的资源，并且用户拥有此权限，则显示；
	  * acl的属性中的值如果是需要权限控制的资源，但是用户没有有此权限，则隐藏；
	  * acl的属性中的值如果不是需要权限控制的资源，则显示；
	  */
	 function ACL(){
		 doPost(securityACL,null,function(result){
			 if(result){
				 if(!printError(result)){
					 var obj=str2obj(result);
					 var allUrl=obj.allAuth;
			        	var userUrl=obj.userAuth;
			        	$("[acl]").each(function(){
			        		var dom=$(this);
			        		var url=dom.attr("acl");
		        			if(inArray(url,allUrl)){
			        			if(inArray(url,userUrl)){
			        				return;
			        			}else{
			        				dom.hide();
			        			}
			        		}
			        		
			        	});
				 }
			 }
		 });
	 }

	
	 
	 /**
	 *
	 * 刷新页面<br/>
	 * 整个页面进行了重新请求和刷新，之前的资源被丢弃。
	 * @param url 请求的地址
	 * @param param 请求的参数
	 * 
	 */
	 function redirectUrl(url,param){
		if( url ){
			if( $.isPlainObject(param) ){
				var paramStr = "";
				var k = null;
				for( k in param ){
					paramStr += '<input name="' + k +'" value="'+ param[k] + '"/>';
				}
				$("body").prepend('<div style="display:none;"><form id="changeUrlForm" action="'+url+'" method="post">'+paramStr+'</form></div>');
				$("#changeUrlForm")[0].submit();
			}else{
				location.href=url;
			}
		}
	 }
	 
	 /**
	  * 初始化内容页
	  * @param pageId
	  * @param targetUrl
	  */
	 function initPageContent(pageId,targetUrl){
//		 var tmpUrl;
//		 if(targetUrl.indexOf('?')==-1){
//			 tmpUrl=targetUrl+"?randomNum="+Math.random()*99999+"&ajax_request=async";
//		 }else{
//			 tmpUrl=targetUrl+"&randomNum="+Math.random()*99999+"&ajax_request=async";
//		 }
//		$("#"+pageId).layout('panel','center').panel({
//			href:targetUrl,
//			cache:false,
//			fit:true
//		});
		 initPageContentWithPara(pageId,{},targetUrl);
	 }
	 /**
	  * 初始化内容页
	  * @param pageId
	  * @param targetUrl
	  */
	 function initPageContentWithPara(pageId,para, targetUrl){

		 doPost(targetUrl,para,function(result){
				try{
					if(result){
						if(result.indexOf("{")!=0){
							$("#"+pageId).layout('panel','center').panel({
								content:PREFIX_CONTENT+result+SUBFIX_CONTENT,
								cache:false
							});
							$.xcarsParser.parse($("#"+pageId));
						}else{
							var obj=eval('('+result+')');
							if(obj.error){
								
								if( para != null && para.isRedirectUrl == '1' ){
									$.messager.alert(global.error,obj.error,'error',function(){
										history.back();
									});
								}else{
									error(obj.error);
								}
							}
							setNull(obj);
						}
						isWorkSpaceBusy=false;
						setNull(result);
					}
				}catch(e){
					error(e);
				}finally{

				}
			});
	 }
	 
	 function closeWindow(winId){
		if( winId ){
			$('#' + winId).window("close");
		}
	 }
	 
	

	 /**
	  * @param keys
	  * @returns
	  */
	 function CodeUtil(keys){
		 var parm={
				keys:keys?keys:[],
				maps:[]
		 };
		 /**
		  * 添加要查询的字典key
		  * key "字典key"或"表名:字段名"
		  */
		 this.putKey=function(key){
			 parm.keys.push(key);
		 };
		 /**
		  * 加载字典数据，根据提供的字典key集合，从服务器去除所有key对应的字典集合,为getValue方法提供缓存查询，与getValue方法结合使用
		  */
		 this.loadData=function(){
			 if(parm.keys&&parm.keys.length>0){
				//远程加载数据 
				 doSyncPost(codeUrl,{"keys":parm.keys.join(',')},function(result){
					 if(result){
						 if(result.indexOf('{')==0||result.indexOf('[')==0){
							 var obj=str2obj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 parm.maps=new Array();
								 for(var i in obj){
									 parm.maps.push(obj[i]);
								 }
							 }
						 }
					 }
				 });
			 }
			
		 };
		 /**
		  * 获取字典值，调用时从内存中取，依赖于loadData方法的调用，用于字典本身不庞大，但页面中频繁使用
		  * key "字典key"或"表名:字段名"
		  * no 序号
		  */
		 this.getValue=function(key,no){
			 if(typeof key != 'undefined'&& typeof no != 'undefined'){
				 for(var i in parm.maps){
					 if(key==parm.maps[i].key){
						var map=parm.maps[i].map;
						for(var j in map){
							if(no==map[j].codeNo){
								return map[j].codeName;
							}
						}
					 }
				 }
			 }
			 return no;
		 };
		 this.fillSelect=function(id,key){
			if(key){
				for(var i in parm.maps){
					 if(key==parm.maps[i].key){
						var map=parm.maps[i].map;
						var options="";
						for(var j in map){
							if(map[j].codeNo)
							options+="<option value="+map[j].codeNo+">"+map[j].codeName+"</option>";
						}
						$(options).appendTo($("#"+id));
						return;
					 }
				 }
			} 
		 };
		 /**
		  * 获取字典对象，有序号和值组成,依赖于loadData方法
		  * @param {Object} key
		  * @return {TypeName} 
		  */
		 this.getCode=function(key){
			 if(key){
				 for(var i in parm.maps){
					 if(key==parm.maps[i].key){
						var map=parm.maps[i].map;
						return map;
					 }
				 }
			 }else{
				 return {};
			 }
		 };
		 /**
		  * 同步的获取字典值，每调用一次想服务器请求一次，用于字典本身比较庞大，但页面中调用很少的地方
		  * key "字典key"或"表名:字段名"
		  * no 序号
		  */
		 this.getSyncValue=function(key,no){
			 var retName=no;
			 if(key&&no){
				 doSyncPost(codeSyncUrl,{"code.codeKey":key,"code.codeNo":no},function(result){
					 if(result){
						 if(result.indexOf('{')==0){
							 var obj=str2obj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 if(obj.codeName){
									 retName=obj.codeName;
								 }
							 }
						 }
					 }
				 });
			 }
			 return retName;
		 };
	 }
	 
	 /**
	  * @param keys
	  * @returns
	  */
	 function XhhCodeUtil(keys){
		 var parm={
				keys:keys?keys:[],
				maps:[]
		 };
		 /**
		  * 添加要查询的字典key
		  * key "字典key"或"表名:字段名"
		  */
		 this.putKey=function(key){
			 parm.keys.push(key);
		 };
		 /**
		  * 加载字典数据，根据提供的字典key集合，从服务器去除所有key对应的字典集合,为getValue方法提供缓存查询，与getValue方法结合使用
		  */
		 this.loadData=function(){
			 if(parm.keys&&parm.keys.length>0){
				//远程加载数据 
				 doSyncPost(xhhCodeUrl,{"keys":parm.keys.join(',')},function(result){
					 if(result){
						 if(result.indexOf('{')==0||result.indexOf('[')==0){
							 var obj=str2obj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 parm.maps=new Array();
								 for(var i in obj){
									 parm.maps.push(obj[i]);
								 }
							 }
						 }
					 }
				 });
			 }
			
		 };
		 /**
		  * 获取字典值，调用时从内存中取，依赖于loadData方法的调用，用于字典本身不庞大，但页面中频繁使用
		  * key "字典key"或"表名:字段名"
		  * no 序号
		  */
		 this.getValue=function(key,no){
			 if(typeof key != 'undefined'&& typeof no != 'undefined'){
				 for(var i in parm.maps){
					 if(key==parm.maps[i].key){
						var map=parm.maps[i].map;
						for(var j in map){
							if(no==map[j].codeNo){
								return map[j].codeName;
							}
						}
					 }
				 }
			 }
			 return no;
		 };
		 this.fillSelect=function(id,key){
			if(key){
				for(var i in parm.maps){
					 if(key==parm.maps[i].key){
						var map=parm.maps[i].map;
						var options="";
						for(var j in map){
							if(map[j].codeNo)
							options+="<option value="+map[j].codeNo+">"+map[j].codeName+"</option>";
						}
						$(options).appendTo($("#"+id));
						return;
					 }
				 }
			} 
		 };
		 /**
		  * 获取字典对象，有序号和值组成,依赖于loadData方法
		  * @param {Object} key
		  * @return {TypeName} 
		  */
		 this.getCode=function(key){
			 if(key){
				 for(var i in parm.maps){
					 if(key==parm.maps[i].key){
						var map=parm.maps[i].map;
						return map;
					 }
				 }
			 }else{
				 return {};
			 }
		 };
		 /**
		  * 同步的获取字典值，每调用一次想服务器请求一次，用于字典本身比较庞大，但页面中调用很少的地方
		  * key "字典key"或"表名:字段名"
		  * no 序号
		  */
		 this.getSyncValue=function(key,no){
			 var retName=no;
			 if(key&&no){
				 doSyncPost(codeSyncUrl,{"code.codeKey":key,"code.codeNo":no},function(result){
					 if(result){
						 if(result.indexOf('{')==0){
							 var obj=str2obj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 if(obj.codeName){
									 retName=obj.codeName;
								 }
							 }
						 }
					 }
				 });
			 }
			 return retName;
		 };
	 }

	 function ProductUtil(miNo){
		 
		 var parm={
					miNo:miNo,
					maps:[]
		 };
		 
		 this.loadData=function(){
			 if(parm.miNo&&parm.miNo.length>0){
				//远程加载数据 
				 doPost(prodUrl,{"miNo":parm.miNo},function(result){
					 if(result){
						 if(result.indexOf('{')==0||result.indexOf('[')==0){
							 var obj=str2obj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 parm.maps=new Array();
								 for(var i in obj){
									 parm.maps.push(obj[i]);
								 }
							 }
						 }
					 }
				 });
			 }			
		 };
		 /**
		  * 获取产品名称
		  * prodNo 序号
		  */
		 this.getProdName=function(prodNo){
			 if(prodNo){
				 for(var i in parm.maps){
					 if(prodNo==parm.maps[i].prodNo){
						 return parm.maps[i].prodName;
					 }
				 }
			 }
			 return prodNo;
		 };
		 
		 /**
		  * 同步的获取字典值，每调用一次想服务器请求一次，用于字典本身比较庞大，但页面中调用很少的地方
		  *
		  */
		 this.getSyncProdName=function(prodNo){
			 var retName="";
			 if(prodNo){
				 doSyncPost(prodSyncUrl,{"miNo":parm.miNo,"prodNo":prodNo},function(result){
					 if(result){
						 if(result.indexOf('{')==0){
							 var obj=str2obj(result);
							 if(obj.error){
								 error(obj.error);
							 }else{
								 if(obj.prodName){
									 retName=obj.prodName;
								 }
							 }
						 }
					 }
				 });
			 }
			 return retName;
		 };
		 
	 }
	 /**
	  * 选择字典元
	  * @param callback 回调函数
	  */
	 function chooseCodeMeta(callback){
		 ChooseContext.createInstance({
			 id:"choose_code_meta",
			 url:chooseCodeMetaUrl,
			 title:global.select_codeMeta,
			 callback:callback?callback:function(row){
				var name = '';
				var key = '';
				if(row&&row.key&&row.name){
					name = row.name;
					key = row.key;
				}
				$("#codeMeta_name").val(name);
				$("#codeMeta_key").val(key);	
			 }
		 });
	 }
	 /**
	  * 选择客户
	  * @param callback 回调函数
	  */
	 function chooseCustinfo(callback,param){
		param = param || {};
		var url = chooseCustinfoUrl;
		if (param.hideCompany) {
			url += '?hideCompany=' + (param.hideCompany);
		}
		if (param.hidePerson) {
			if (url.indexOf("?") == -1) {
				url += "?";
			}else{
				url += "&";
			}
			url += 'hidePerson=' + (param.hidePerson);
		}
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"choose_custinfo_list",
			 url:url,
			 title:global.select_custinfo,
			 refresh:true,
			 target:target,
			 callback:callback?callback:function(row){
				 var custId = '';
				 var custName = '';
				 if(row&&row.id&&row.custName){
					 custId = row.id;
					 custName=row.custName;
				}
				 $("#custInfo_id").val(custId);
				 $("#custInfo_custName").val(custName);
			 }
		 });
	 }
	 /**
	  * 选择客户
	  * @param callback 回调函数
	  */
	 function chooseAllCustinfo(callback){
		 var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"choose_custinfo_list",
			 url:chooseCustinfoUrl,
			 target:target,
			 title:global.select_custinfo,
			 callback:callback?callback:function(row){
				 var custId = '';
				 var custName = '';
				 if(row&&row.id&&row.custName){
					 custId = row.id;
					 custName=row.custName;
				}
				 $("#custInfo_id").val(custId);
				 $("#custInfo_custName").val(custName);
			 },
			 allCustomer:true
		 });
	 }
	 /**
	  * 选择客户账户
	  * @param callback 回调函数
	  */
	 function chooseCustinfoAccount(callback){
		 var custId=$("#custId").val();
		 ChooseContext.createInstance({
			 id:"choose_custinfo_account_list",
			 url:baseDir+"/custinfo/companyCustinfo_chooseAccount.jhtml?id="+custId,
			 title:global.select_account,
			 callback:callback?callback:function(row){
				if(row){
					 $("#account_id").val(row.id);
					 $("#account_Name").val(row.accountName);
					 $("#account_No").val(row.accountNo);
					 $("#account_bank").val(row.bank);
				}else{
					$("#account_id").val("");
					$("#account_Name").val("");
					$("#account_No").val("");
					$("#account_bank").val("");
				}
			 }
		 });
	 }
	 /**
	  * 选择合作方
	  * @param callback 回调函数
	  */
	 function choosePartner(callback,params){
		var url = baseDir+"/partner/partner_choosePartner.jhtml";
		if ($.isPlainObject(params)) {
			var k = null;
			for( k in params ){
				if (url.indexOf("?") == -1 ) {
					url += "?";
				}else{
					url += "&";
				}
				url += k + "=" + params[k];
			}
		}
		 ChooseContext.createInstance($.extend({
			 id:"choose_partner",
			 url:url,
			 title:global.select_partner,
			 callback:callback?callback:function(row){
				 var partnerId = '';
				 var partnerName = '';
				 if(row&&row.id&&row.name){
					 partnerId = row.id;
					 partnerName = row.name;
				 }
				 $("#partner_id").val(partnerId);
				 $("#partner_name").val(partnerName);
			 }
		 },params||{}));
	 }
	 
	 /**
	  * 选择现金流资金方
	  * @param callback 回调函数
	  */
	 function chooseCashPartner(callback){
		 ChooseContext.createInstance({
			 id:"choose_partner",
			 url:baseDir+"/partner/partner_chooseCashPartner.jhtml",
			 title:global.select_partner,
			 callback:callback?callback:function(row){
				 var partnerId = '';
				 var partnerName = '';
				 if(row&&row.id&&row.name){
					 partnerId = row.id;
					 partnerName = row.name;
				 }
				 $("#partner_id").val(partnerId);
				 $("#partner_name").val(partnerName);
			 }
		 });
	 }
	 
	 /**
	  * 选择合作方的联系人
	  * @param callback 回调函数
	  */
	 function choosePartnerLinkman(callback,_partnerIdName){
		var partnerId ;
		if (_partnerIdName) {
			partnerId=$('#'+_partnerIdName).val();
		}else{
			partnerId=$("#partner_id").val();
		 }
		 if(!partnerId){
			 alert("请先选择贷款机构");
		 }else{
			 ChooseContext.createInstance({
				 id:"choosePartnerLinkman",
				 url:baseDir+"/partner/partnerLinkman_chooseLinkman.jhtml?partner.id="+partnerId,
				 title:global.select_partner_linkman,
				 callback:callback?callback:function(row){
					var partnerLinkmanId = '';
					var partnerLinkmanName = '';
					 if(row&&row.id&&row.lmName){
						partnerLinkmanId=row.id;
						partnerLinkmanName=row.lmName;
					 }
					$("#partnerLinkman_id").val(partnerLinkmanId);
					$("#partnerLinkman_name").val(partnerLinkmanName);
				 }
			 }); 
		 }
	 }

	 /**
	  * 选择独立审批
	  * @param callback 回调函数
	  */
	 function chooseApprAuth(callback){
		 ChooseContext.createInstance({
			 id:"apprAuthChoose",
			 url:baseDir+"/risk/apprAuth_choose.jhtml",
			 title:global.select_apprAuth,
			 callback:callback?callback:function(row){
				 var apprAuthId = '';
				 var apprAuthName = '';
				 if(row&&row.userId&&row.userName){
					 apprAuthId = row.userId;
					 apprAuthName = row.userName;
				 }
				 $("#apprAuthId").val(apprAuthId);
				 $("#apprAuthName").val(apprAuthName);
			 }
		 });
	 }

	 
	 /**
	  * 选择评审会
	  * @param callback 回调函数
	  */
	 function chooseAssessMeeting(callback,param){
		var url = chooseAssessMeetingUrl;
		if (param) {
			url += "?" + param
		}
		 ChooseContext.createInstance({
			 id:"chooseAssessMeeting",
			 url:url,
			 title:global.select_assessMeeting,
			 callback:callback?callback:function(row){
				 var meetingId = '';
				 var meetingName = '';
				 if(row&&row.id&&row.name){
					 meetingId = row.id;
					 meetingName = row.name;
				 }
				 $("#assessMeeting_id").val(meetingId);
				 $("#assessMeeting_name").val(meetingName);
			 }
		 });
	 }

/**
 * 选择区域
 * @param callback 回调函数
 */
function chooseArea(callback,param){
	var target = getEventTarget();
	ChooseContext.createInstance({
		id:"choose_area_list",
		url:baseDir+"/orgArea/orgAreaManage_toChoose.jhtml",
		title:"选择区域",
		refresh:true,
		target:target,
		callback:callback?callback:function(row){
			var areaId = '';
			var areaName = '';
			if(row&&row.id&&row.areaName){
                areaId = row.id;
                areaName=row.areaName;
			}
			$("#area_id").val(areaId);
			$("#area_name").val(areaName);
		}
	});
}

/**
 * 选择营业部
 * @param callback 回调函数
 */
function chooseDept(callback,param){
    var areaId;
    if(param){
        areaId = $('#'+param).val();
    }
    if(!areaId){
        warning("请选择区域");
        return;
    }
    var target = getEventTarget();
    ChooseContext.createInstance({
        id:"choose_dept_list",
        url:baseDir+"/orgDept/orgDeptManage_toChoose.jhtml?areaId="+areaId,
        title:"选择营业部",
        refresh:true,
        target:target,
        callback:callback?callback:function(row){
            var deptId = '';
            var deptName = '';
            if(row&&row.id&&row.deptName){
                deptId = row.id;
                deptName=row.deptName;
            }
            $("#dept_id").val(deptId);
            $("#dept_name").val(deptName);
        }
    });
}
	 
	 /**
	  * 用于弹出选择行业类别信息，支持多选
	  * @_callback 回调函数，回调函数中处理时，获取选择的对象的属性时   $('#tradeId1').val(getFiledValueFromMutRows(rows,'id'));
	  * @_isSignle 是否单选 boolean类型，取值为 false,true
	  */
	 chooseTrade = function(callback,_isSignle){
		 
		  ChooseContext.createInstance(
	          {
	             title:global.select_trade,
	             width:650,
			     id:"choose_tradecategory",
			     url:baseDir+"/dictionary/trade_toSelectTrade.jhtml",
			     
			     //callback:_callback?_callback:ChooseContext.tradeCategoryCallback,
			     param:{isSignle:_isSignle},
			     callback:callback?callback:function(rows){
			    	 if(rows)
				      {  
					      if($("#trade_id")) $("#trade_id").val(getFiledValueFromMutRows(rows,'id'));
					      if($("#trade_code")) $("#trade_code").val(getFiledValueFromMutRows(rows,'tradeNo'));
					      if($("#trade_name")) $('#trade_name').val(getFiledValueFromMutRows(rows,'tradeName'));

					  }else
					  {
					      if($("#trade_id")) $("#trade_id").val("");
					      if($("#trade_code")) $("#trade_code").val("");
					       if($("#trade_name")) $("#trade_name").val("");
					  }
				 }
			    
	          }
	       )
	 }
	 
	
	 /**
	  * 选择国家
	  * @param callback
	 
	 function chooseCountry(callback){
		 var countryCode="A006";
		 chooseCode(callback?callback:function(row){
			 if(row&&row.codeName&&row.codeNo){
					$("#country_no").val(row.codeNo);
					$("#country_name").val(row.codeName);
				}
		 },countryCode,global.select_country);
	 }
	  */
	 /**
	  * 选择省份
	  * @param callback
	 
	 function chooseProvince(callback){
		 var provinceCode="A007";
		 chooseCode(callback?callback:function(row){
			 if(row&&row.codeName&&row.codeNo){
					$("#province_no").val(row.codeNo);
					$("#province_name").val(row.codeName);
				}
		 },provinceCode,global.select_province);
	 }
	  */
	 /**
	  * 城市
	  * @param callback
	  
	 function chooseCity(callback){
		 var cityCode="A008";
		 chooseCode(callback?callback:function(row){
			 if(row&&row.codeName&&row.codeNo){
					$("#city_no").val(row.codeNo);
					$("#city_name").val(row.codeName);
				}
		 },cityCode,global.select_city);
	 }
	 */
	 /**
	  * 选择字典
	  * @param callback 回调
	  * @param codeKey 字典key
	  */
	 function chooseCode(callback,codeKey,title){
		 ChooseContext.createInstance({
			 id:"choose_code",
			 url:chooseCodeUrl,
			 width:500,
			 title:title?title:global.select_code,
			 callback:callback?callback:function(row){
				 if(row&&row.codeName&&row.codeNo){
						$("#code_codeNo").val(row.codeNo);
						$("#code_codeName").val(row.codeName);
					}
			 },
			 codeKey:codeKey
		 });
	 }
	 /**
	  * 选择接入
	  * @param callback
	  */
	 function chooseMember(callback){
		 ChooseContext.createInstance({
			 id:"choose_member",
			 url:chooseMemberUrl,
			 title:global.select_member,
			 callback:callback?callback:function(row){
				 if(row&&row.miNo&&row.miName){
						$("#memberInfo_miName").val(row.miName);
						$("#memberInfo_miNo").val(row.miNo);
					}
			 }
		 });

	 }
	 /**
	  * 选择审批路线
	  * @param callback
	  */
	 function chooseAuditRoute(callback){
		 ChooseContext.createInstance({
			 id:"choose_audit_route",
			 url:chooseAuditRouteUrl,
			 title:global.select_audit_route,
			 callback:callback?callback:function(row){
				 if(row&&row.id&&row.auditRouteName){
						$("#auditRoute_auditRouteName").val(row.auditRouteName);
						$("#auditRoute_id").val(row.id);
					}
			 }
		 });
	 }
	 /**
	  * 选择机构
	  * @param callback
	  */
	 function chooseBranch(callback){
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"choose_branch",
			 url:chooseBranchUrl,
			 target:target,
			 title:global.select_branch,
			 callback:callback?callback:function(row){
				var brchName = '';
				var unionBankNo = '';
				var brchNo = '';
				var brchId = '';
				var treeCode = '';
				if(row&&row.brchName&&row.brchNo&&row.brchId){
					brchName = row.brchName;
					unionBankNo = row.unionBankNo;
					brchNo=row.brchNo;
					brchId=row.brchId;
					treeCode=row.treeCode;
				}
				$("#branch_brchName").val(brchName);
				$("#branch_unionBrankNo").val(unionBankNo);
				$("#branch_brchNo").val(brchNo);
				$("#branch_brchId").val(brchId);
				$("#branch_treeCode").val(treeCode);
			 }
		 });
	 }
	 
	 
	
	 /**
	  * 选择树状机构
	  * @param callback
	  */
	  function chooseTreeBranch(callback,param,componentParam){
	var target = getEventTarget();
	 ChooseContext.createInstance($.extend({
		 id:"choose_treebranch",
		 url:baseDir+"/web_ui/choose_treeBranch.jhtml",
		 title:global.select_branch,
		 target:target,
		 urlParam:param,
		 callback:callback?callback:function(row){
			var branch_brchName = '',branch_brchNo='',branch_brchId='',branch_treeCode='';
			 if(row&&row.brchName&&row.brchNo&&row.brchId){
				branch_brchName = row.brchName;
				branch_brchNo=row.brchNo;
				branch_brchId=row.brchId;
				branch_treeCode=row.treeCode;
			 }
			$("#branch_brchName").val(branch_brchName);
			$("#branch_brchNo").val(branch_brchNo);
			$("#branch_brchId").val(branch_brchId);
			$("#branch_treeCode").val(branch_treeCode);
		 }
	 },componentParam||{}));
	 }
	 
	 /**
	  * 选择上级机构
	  * @param callback
	  */
	 function chooseSuperBranch(curBranchId,callback){
		 ChooseContext.createInstance({
			 id:"choose_superBranch",
			 url:chooseSuperBranchUrl,
			 title:global.select_branch,
			 callback:callback?callback:function(row){
				 if(row&&row.brchName&&row.brchNo&&row.brchId){
						$("#branch_brchName").val(row.brchName);
						$("#branch_treeCode").val(row.treeCode);
					}
			 },
			 curBranchId:curBranchId
		 });
	 }
	 
	 
	 /**
	  * 选择机构下的人员
	  * @param callback
	  */
	 function chooseBrchUser(callback){
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"choose_brchuser",
			 width: 700,
			target:target,
			 url:baseDir+"/web_ui/choose_brchUser.jhtml",
			 title:global.select_user,
			 callback:callback?callback:function(row){
			     var userId = '';
			     var userNo = '';
			     var userName = '';
				 if(row&&row.userId&&row.userName){
					 userId = row.userId;
					 userNo = row.userNo;
					 userName = row.userName;
				 }
				 $("#partner_contractSignatory").val(userId);
				 $("#partner_contractSignatoryNo").val(userNo);
				 $("#partner_contractSignatoryName").val(userName);
			 }
		 });
	 }
	 
	 /**
	  * 根据属性选择产品
	  * @param callback
	  * @param attrKey 属性key
	  * @param attrValue 属性值
	  */
	 function chooseAttrProduct(callback,attrKey,attrValue){
		 baseChooseProduct(callback, "",attrKey,attrValue);
	 }
	 /**
	  * 选择产品
	  * @param callback
	  */
	 function chooseProduct(callback){
		 baseChooseProduct(callback, "");

	 }
	 /**
	  * 选择业务产品
	  * @param callback
	  */
	 function chooseBusiProduct(callback){
		 baseChooseProduct(callback, '1,2');

	 }
	 /**
	  * 选择主业务产品
	  * @param callback
	  */
	 function chooseMainBusiProduct(callback){
		 baseChooseProduct(callback, '2');

	 }
	 
	 function chooseMainBusiProductMulti(callback){
		 baseChooseProduct(callback, '2',null,null,null,null,'true');

	 }

	 //prodType=1 业务产品，2其他产品，null或'' 所有产品
	 function baseChooseProduct(callback, prodType,attrKey,attrValue,prodNos,notProdNos,multi){
		var target = getEventTarget();
		 if(attrKey&&attrValue){
			 ChooseContext.createInstance({
				 id:"choose_product",
				 url:chooseAttrProductUrl,
				 title:global.select_product,
				 target:target,
				 callback:callback?callback:function(row){
					 if(row&&row.id&&row.prodName){
							$("#productInfo_prodName").val(row.prodName);
							$("#productInfo_id").val(row.id);
							$("#productInfo_prodNo").val(row.prodNo);
						}
				 },
				 prodType:prodType,
				 attrKey:attrKey,
				 attrValue:attrValue,
				 prodNos:prodNos,
				 notProdNos:notProdNos,
				 multi:multi
			 });
		 }else{
			 ChooseContext.createInstance({
				 id:"choose_product",
				 url:chooseProductUrl,
				 title:global.select_product,
				 target:target,
				 callback:callback?callback:function(row){
					 if(row&&row.id&&row.prodName){
							$("#productInfo_prodName").val(row.prodName);
							$("#productInfo_id").val(row.id);
							$("#productInfo_prodNo").val(row.prodNo);
						}
				 },
				 prodType:prodType,
				 prodNos:prodNos,
				 notProdNos:notProdNos,
				 multi:multi
			 });
		 }

	 }	 
	 
	 /**
	   *自动生成业务编码
	   *key: 自动编码对应的key值，或页面的隐藏的input对象
	   *destObj: 编码存放的目标input对象。
	   *button:自动编码按钮，使用时传this即可，编码生成后，按钮自动隐藏
	  **/
	 function genCode(key,destObj,button){
	 
	    
		 var keyObj = document.getElementById(key);
		 if(keyObj){
		    key = keyObj.value;
		 }
		
		  doPost(autoCodeUrl,{"codeKey":key},function(result){
		            if(result){
						 $("#"+destObj).val(str2obj(result).code);
						 if(button){
							 button.style.display = "none";
						 }
					 }
				 });

	 }
	
	//===============================
	
	proxy$Array = [];
	//deprecated
	function proxy$(fn){
		if( typeof fn != 'undefined' && $.isFunction(fn) ){
			var isAdd = true;
			var fnStr = fn.toString();
			for( var i = 0; i<proxy$Array.length; i++  ){
				if(proxy$Array[i].toString() == fnStr){
					isAdd = false;
					break;
				}
			}
			if( isAdd ){
				proxy$Array.push(fn);
			}
		}
	}
	//可以提交的disabled属性控件
	(function($){
		function disable(target){
			var t = $(target);
			t.attr("disabled","disabled");
			if(t.hasClass("xcarsui-disabled")){
				t.removeClass("xcarsui-disabled");
			}
		}
		function xdisable(target){
			var t = $(target);
			t.attr("disabled","disabled");
			if(!t.hasClass("xcarsui-disabled")){
				t.addClass("xcarsui-disabled");
			}
		}
		function enable(target){
			var t = $(target);
			t.removeAttr("disabled");
			t.removeClass("xcarsui-disabled");
		}
		$.fn.xdisable=function(options,param){
			if( typeof options == 'string' ){
				if( options == 'disable' ){
					return this.each(function(){disable(this);});
				}else if ( options == 'unDisable' ){
					return this.each(function(){enable(this);});
				}else if ( options == 'enable' ){
					return this.each(function(){enable(this);});
				}else if ( options == 'xdisable' ){
					return this.each(function(){xdisable(this);});
				}
				return this;
			}
		};
	})(jQuery);
	//下拉插件
	(function($){
		var panelX = 0;
		var attr_name = /^(\d|\w|_)+$/;
		var _doc = $(document);
		var hasInitValidateBoxNullValue = false;
		if( !hasInitValidateBoxNullValue && $.fn.validatebox.defaults && ! $.fn.validatebox.defaults.nullValue){
			$.extend($.fn.validatebox.defaults,{"nullValue":getPleaseSelectText()});
			hasInitValidateBoxNullValue=true;
		}
		//组装下拉框
		function compositePanel(target){
			var state = getXcomboboxState(target);
			var opts = state.options,panel = state.panel,data = state.data;
			var isMultiple = isMultipleCombobx(target);
			if (isMultiple) {
				if( data && data.length > 0){
					var itemArray = [],valueField = opts.valueField,textField = opts.textField;
					for( var i = 0; i< data.length; i++ ){
						var item = '<div style="cursor:pointer" class="combobox-item';
						item +='" value="'+ (data[i][valueField] ? data[i][valueField] : '') +'">'+ (data[i][textField] ? data[i][textField] : '' ) +'</div>';
						itemArray.push(item);
					}
					panel.find("div.xcombo-panel").html(itemArray.join('')).find("div.combobox-item").hover(function(){
						$(this).addClass("combobox-item-hover");
					},function(){
						$(this).removeClass("combobox-item-hover");
					}).bind("mousedown",function(){
						//methods.setValue(target,$(this).attr("value"),this);
						var textValue = $(this).text();
						var toggle = true;
						if (opts.pleaseSelect == 'true' && (getPleaseSelectText() == textValue)) {
							toggle = false;
						}
						if (toggle) {
							$(this).toggleClass("combobox-item-selected");
						}
						setMultipleValue(target,this);
						return false;
					});
				}
			}else{
				var value = methods.getValue(target);
				if( data && data.length > 0){
					var itemArray = [],valueField = opts.valueField,textField = opts.textField;
					for( var i = 0; i< data.length; i++ ){
						var item = '<div style="cursor:pointer" class="combobox-item';
						if( value == data[i][valueField] ){
							item += ' combobox-item-selected';
						}
						item +='" value="'+ (data[i][valueField] ? data[i][valueField] : '') +'">'+ (data[i][textField] ? data[i][textField] : '' ) +'</div>';
						itemArray.push(item);
					}
					panel.find("div.xcombo-panel").html(itemArray.join('')).find("div.combobox-item").hover(function(){
						$(this).addClass("combobox-item-hover");
					},function(){
						$(this).removeClass("combobox-item-hover");
					}).bind("mousedown",function(){
						methods.setValue(target,$(this).attr("value"),this);
						panel.hide();
						return false;
					});
				}
				methods.setValue(target,value);
			}
		}
		// 清理不使用的下拉框
		function clearNoUsePanel(){
			$("body>div.xcombobox-panel").filter(function(i){
				return $("span[x=" + $(this).attr('x') +"]").length == 0;
			}).remove();
		}
		//获取下拉框的文本
		function getComboText(target){
			var ret = null;
			var state = getXcomboboxState(target);
			if( state ){
				ret = state.ct;
			}else{
				ret = $(".combo-text",$(target));
			}
			return ret ;
		}
		//获取下拉框的值
		function getComboValue(target){
			var ret = null;
			var state = getXcomboboxState(target);
			if( state ){
				ret = state.cv;
			}else{
				ret = $(".combo-value",$(target));
			}
			return ret;
		}
		//默认下拉框第一项的文本
		function getPleaseSelectText(){
			var ret = '';
			try{
				ret = global.please_select;
			}catch(e){}
			return ret;
		}
		//获取下拉框的数据
		function getXcomboboxState(target){
			return $.data(target,"xcombobox");
		}
		//初始化下拉框
		function init(target){
			clearNoUsePanel();
			var t = $(target);
			var state = getXcomboboxState(target);
			var opts = state.options;
			addPleaseSelect(target);
			if(opts.disabled == 'true' || opts.disabled == 'disabled' ){
				methods.disable(target);
			}else{
				methods.enable(target);
			}
			if( opts.isValidateBox == 'true' ){
				$(".xcarsui-validatebox",t).validatebox();
			}
			if( !opts.initComboxText ){
				methods.setValue(target,methods.getValue(target),null,true);
			}
		}
		//计算下拉框的位置
		function calPanelPosition(target){
			var t = $(target);
			var state = getXcomboboxState(target);
			var panel = state.panel;
			var $b = $(document.body);
			var bh = $b.height();
			var scrollTop = $b.scrollTop();
			var panelHeight = null;
			var offset = t.offset(),width = t.width(),top = offset.top+t.outerHeight(),left = offset.left;
			if(  bh + scrollTop < top + panel.height() ){
				if( scrollTop+(bh/2) < top ){
					if( scrollTop > top - t.outerHeight() - panel.height() ){
						panelHeight = top - t.outerHeight() - scrollTop - 4;
						top = top - t.outerHeight() - panelHeight;
					}else{
						top = top - t.outerHeight() - panel.height();
					}
				}else{
					panelHeight = scrollTop + bh - top - 4;
				}
			}
			if(panelHeight){
				panel.find("div.xcombo-panel").css({'height':panelHeight});
			}
			panel.css({'left':left,'top':top,"width":width+2});
		}
		//组装下拉框
		function comboPanel(target){
			var state = getXcomboboxState(target);
			if( ! state.panel){
				var opts = state.options
				var p = $('<div class="panel xcombobox-panel" x='+ (++panelX)  +' style="position:absolute;z-index:10000;display:none;"><div style="background: none repeat scroll 0 0 #FFFFFF;" class="xcombo-panel panel-body panel-body-noheader" title="" style="height:'+ (opts.panelHeight ? opts.panelHeight : 'auto' ) +';"></div></div>');
				var t = $(target);
				var offset = t.offset(),width = t.width();
				var wh = {"width":width,"height":opts.panelHeight?opts.panelHeight:'auto'};
				p.find("div.xcombo-panel").css(wh).bind("mousedown.xcombobox",false);
				$("body").append(p);
				state.panel=p;
				t.attr("x",p.attr("x"));
				calPanelPosition(target);
				loadData(target);
				compositePanel(target);
				_doc.unbind("mousedown.xcombobox").bind("mousedown.xcombobox",function(){
					$("body>div.xcombobox-panel").hide();
				});
			}
		}
		//加载下拉框数据
		function loadData(target){
			var state = getXcomboboxState(target);
			var opts = state.options;
			if( ! opts.isLoaded && opts.url ){
				$.ajax({
					url:opts.url,
					async:false,
					success:function(result){
						if(result){
							try{
								var d = $.parseJSON(result);
								if( $.isArray(d) ){
									if( state.data ){
										$.merge(state.data,d);
									}else{
										state.data = d;
									}
								}
							}catch(e){}finally{ opts.isLoaded = true; }
						}
					}
				});
			}
		}
		//增加下拉框默认项
		function addPleaseSelect(target){
			var state = getXcomboboxState(target);
			var opts = state.options;
			if( opts.pleaseSelect == 'true' ){
				var data = state.data || [];
				var ps = '{"' + opts.textField + '":"' + getPleaseSelectText() + '","'+opts.valueField + '":"' + opts.psValue +'"}';
				data.unshift($.parseJSON(ps));
				state.data = data;
			}
		}
		//存放早于初始化之前的数据
		var pre = {
			hasData:function(target){
				return !!$.data(target,"xcombobox-pre");
			},
			data:function(target,key,value){
				if(key){
					if($.isPlainObject(key)){
						var state = $.data(target,"xcombobox-pre") || {};
						$.extend(state,key);
						$.data(target,"xcombobox-pre",state);
					}else{
						if( typeof value != 'undefined' ){
							var state = $.data(target,"xcombobox-pre")||{};
							$.data(target,"xcombobox-pre",state);
							state[key]=value;
						}else{
							var state = $.data(target,"xcombobox-pre");
							if( state ){
								return state[key];
							}
							return null;
						}
					}
				}else{
					return $.data(target,"xcombobox-pre");
				}
			},
			clearAll:function(target){
				var state = $.data(target,"xcombobox-pre");
				if(state){
					$(target).removeData("xcombobox-pre");
				}
			}
		};
		
		function isMultipleCombobx(target){
			var state = getXcomboboxState(target);
			return "true"==state.options.multiple;
		}
		
		function setMultipleValue(target){
			var state = getXcomboboxState(target);
			var name = state.options.name;
			if (name){
				var ct = getComboText(target);
				var ctValue = '';
				var items = $("div.combobox-item-selected",state.panel);
				var valueHtml = '';
				items.each(function(i){
					valueHtml += '<input type="hidden" class="combo-value" name="' + name + '[' + i + ']" value="' + $(this).attr("value") + '">';
					if (ctValue) {
						ctValue += '，';
					}
					ctValue += $(this).text();
				});
				$(".combo-value",target).remove();
				if (valueHtml) {
					ct.parent().append(valueHtml);
				} else{
					if (state.options.pleaseSelect == 'true' ) {
						ctValue = getPleaseSelectText();
					}
				}
				ct.val(ctValue);
			}
		}
		
		//外部可以调用控件的方法
		var methods = {
			//设置控件的值
			setValue:function(target,val,selected,notFocus){
				var t = $(target);
				var state = getXcomboboxState(target);
				var cv = getComboValue(target);
				if(state){
					var opts = state.options,valueField = opts.valueField,textField = opts.textField,data = state.data;
					var textValue = '';
					if(!selected){
						if( ! opts.isLoaded ){
							loadData(target);
							data = state.data;
						}
						if( data && data.length > 0 ){
							for( var i = 0 ; i < data.length; i++ ){
								if( data[i][valueField] == val ){
									textValue = data[i][textField];
									break;
								}
							}
							if( !textValue ){
								textValue = data[0][textField];
								val = data[0][valueField];
							}
						}
						if( state.panel ){
							state.panel.find(".combobox-item").removeClass("combobox-item-selected").addClass(function(){
								if( val && $(this).attr("value") == val ){
									return "combobox-item-selected";
								}
							});
						}
					}else{
						state.panel.find(".combobox-item-selected").removeClass("combobox-item-selected");
						var st = $(selected).addClass("combobox-item-selected");
						textValue = st.text();
					}
					var origianl = cv.val();
					cv.val(val);
					getComboText(target).val(textValue);
					if (!notFocus) {
						getComboText(target).focus();
					}
					if( opts.isValidateBox == 'true' && origianl != val ){
						$(".xcarsui-validatebox",t).validatebox("validate").focus();
					}
					if( opts.onChange && origianl != val ){
						opts.onChange.call(target,val,t);
					}
				}else{
					cv.val(val);
				}
			},
			//获取控件的值
			getValue:function(target){
				return getComboValue(target).val();
			},
			//重新加载数据
			reload:function(target,param){
				var t = $(target);
				var state = getXcomboboxState(target);
				param = param || {};
				if(state){
					$.extend(state.options,param);
					var opts = state.options,panel = state.panel,data = state.data;
					if(panel){
						delete state.panel;
						panel.remove();
					}
					if( data ){
						state.data = [];
					}
					opts.isLoaded=false;
					addPleaseSelect(target);
					this.setValue(target,opts.psValue);
				}else{
					pre.data(target,param);
				}
			},
			//控件可用
			enable:function(target){
				var t = $(target);
				var state = getXcomboboxState(target);
				var ct = getComboText(target);
				var cv = getComboValue(target);
				if( state ){
					$("span.combo-arrow",t).unbind(".xcombobox").bind("mouseenter.xcombobox mouseleave.xcombobox",function(){
						$(this).toggleClass("combo-arrow-hover");
					}).bind("mousedown.xcombobox",function(event){
						//_doc.trigger("mousedown.xcombobox");
						$("body>div.xcombobox-panel").hide();
						comboPanel(target);
						var panel = getXcomboboxState(target).panel;
						if( panel ){
							calPanelPosition(target);
							panel.toggle();
						}
						return false;
					});
					ct.removeAttr("disabled");
					cv.removeAttr("disabled");
				}else{
					pre.data(target,"disabled","false");
				}
				if( cv.hasClass("xcarsui-disabled") ){
					cv.removeClass("xcarsui-disabled");
				}
			},
			//禁用控件
			disable:function(target){
				var t = $(target);
				var state = getXcomboboxState(target);
				var ct = getComboText(target);
				var cv = getComboValue(target);
				if( state ){
					$(".combo-arrow",t).unbind(".xcombobox");
					ct.attr("disabled","disabled");
					cv.attr("disabled","disabled");
				}else{
					pre.data(target,"disabled","true");
				}
				if(cv.hasClass("xcarsui-disabled")){
					cv.removeClass("xcarsui-disabled");
				}
			},
			//禁用控件，但可以提交数据
			xdisable:function(target){
				this.disable(target);
				var cv = getComboValue(target);
				if( ! cv.hasClass("xcarsui-disabled") ){
					cv.addClass("xcarsui-disabled");
				}
			},
			//获取控件选中的文本
			getText:function(target){
				return getComboText(target).val();
			},
			//获取控件选中的行数据
			getRow:function(target){
				var ret = null;
				var state = getXcomboboxState(target);
				var data = state.data;
				if( data && data.length > 0 ){
					var value = this.getValue(target);
					var valueField = state.options.valueField;
					for( var i = 0 ; i < data.length; i++ ){
						if( value == data[i][valueField] ){
							ret = data[i];
							break;
						}
					}
				}
				return ret;
			},
			//控件需要验证
			validate:function(target){
				var t = $(target);
				var ct = getComboText(target);
				if( ! ct.hasClass("xcarsui-validatebox") ){
					//ct.addClass("xcarsui-validatebox").attr("required","true").validatebox().validatebox("validate").focus();
					ct.addClass("xcarsui-validatebox validatebox-text").attr("required","true").validatebox();
					var state = getXcomboboxState(target);
					if( state ){
						state.options.isValidateBox="true";
					}else{
						pre.data(target,"isValidateBox","true");
					}
				}
			},
			//控件不需要验证
			disValidate:function(target){
				var t = $(target);
				var ct = getComboText(target);
				if(ct.hasClass("xcarsui-validatebox")){
					ct.removeClass("xcarsui-validatebox validatebox-text validatebox-invalid");
					var state = getXcomboboxState(target);
					if( state ){
						ct.unbind(".validatebox");
						state.options.isValidateBox="false";
					}else{
						pre.data(target,"isValidateBox","false");
					}
				}
			}
		};
		$.fn.xcombobox = function(options,param){
			if( typeof options == 'string' ){
				var ret ;
				if( this[0] && methods[options] ){
					ret = methods[options](this[0],param);
				}
				return typeof ret != 'undefined' ? ret : this;
			}
			options = options || {};
			return this.each(function(){
				var state = $.data(this,"xcombobox");
				var t = $(this);
				if( ! state ){
					var config = eval('({'+$("div.config-info",t).text()+'})');
					var opts = $.extend({},$.fn.xcombobox.defaults,config,options);
					if(pre.hasData(this)){
						var pre_config = pre.data(this);
						$.extend(opts,pre_config);
						pre.clearAll(this);
					}
					var coc = config.onchange;
					if( coc ){
						var onChange = null;
						if(attr_name.test(coc) ){
							if($.isFunction(window[coc])){
								onChange =  window[coc] ;
							}
						}else{
							onChange = new Function('value','cbb',coc);
						}
						opts.onChange = onChange;
					}
					var data = null;
					var dt = config.data;
					if(dt){
						var tmp = null;
						var isArrayStr = dt.charAt(0)=='[';
						if(isArrayStr){
							try{data=eval(dt);}catch(e){}
						}else{
							if( attr_name.test(dt) ){
								data = $.merge([],window[dt]||[]);
							}
						}
						opts.isLoaded = true;
					}
					var disabled = config.disabled;
					var state = {
						'options':opts,
						'data':data,
						'ct':$(".combo-text",t),
						'cv':$(".combo-value",t)
					};
					$.data(this,"xcombobox",state);
					state.ct.bind("keydown.xcombobox",function(event){
						if( event.keyCode==8 ){return false;}
					});
				}else{
					if( $.isEmptyObject(options) ){
						return;
					}
					$.extend(state.options,options);
				}
				init(this);
			});
		};
		
		$.fn.xcombobox.defaults = {
			url:null,
			valueField:'value',
			textField:'text',
			isLoaded:false,
			psValue:'',
			pleaseSelect:'true'
		}; 
	})(jQuery);
	(function($){
		$.fn.xquery=function(options,param){
			return this.each(function(){
				var t = $(this);
				$("input",this).keypress(function(e){
					if (e.which == '13') {
						var a = $("span.icon-search",t).last().parent().parent();
						if(a.length){
							a.trigger('click');
						}
						return false;
					}
				});
			});
		};
		$.fn.xquery.defaults={
		};
	})(jQuery);
	(function($){
		$.xcarsParser = {
			parse:function(context){
				$("span.xcarsui-combox",context).xcombobox();
				$("form .xcarsui-disable",context).xdisable('xdisable');
				if($.fn.currency){
					//$(".currency").currency();
				}
				$("form.query_form",context).xquery();
				$("textarea.xcarsui-editor",context).editor();
			}
		};
	})(jQuery);
	(function($){
		$.fn.editor=function(options,param){
			if(typeof options == 'string'){
				var ret ;
				if( methods[options] ){
					ret = methods[options](this[0],param);
				}
				return typeof ret != 'undefined' ? ret : this;
			}
			options = $.extend({},options,{afterBlur: function(){this.sync();}})
			return this.each(function(){
				var id = $(this).attr("id");
				if (id) {
					var varK = KindEditor.create('#' + id,options);
					idKindEditorMap[id]=varK;
				}
			});
		};
		var methods = {
			sync:function(target){
				var id = $(target).attr("id");
				if(id){
					var kindEditor = idKindEditorMap[id];
					if(kindEditor){
						kindEditor.sync();
					}
				}
			}
		};
		var idKindEditorMap = {
		};
	})(jQuery)
	function _datagridFitHeight(){
		$(".func_data_area").each(function(){
			var target = this;
			var t = $(this);
			var parent = t.parent();
			if(parent[0] != $("body")[0]){
				var fitHeight=parent.height();
				parent.children().each(function(){
					if(this != target){
						fitHeight-=$(this).outerHeight();
					}
				});
				if(fitHeight>0){
					if($.boxModel==true){
						var len=t.outerHeight()-t.height();
						t.css("height",fitHeight-len);
					}else{
						t.css("height",fitHeight);
					}
				}
			}
			if( typeof afterFit != 'undefined' && $.isFunction(afterFit)){
				afterFit();
			}
		});
	}
	(function($){
		$.fn.tip=function(options,param){
			options = options || {};
			return this.each(function(){
				if(options.url){
					options.content={
						text:function(event,api){
							$.ajax({
								url:options.url
							}).done(function(result){
								var html = result;
								if(result&&result.error){
									html=result.error;
								} else if(typeof result =="string"&&result.indexOf("{")==0){
									var obj=str2obj(result);
									if(obj&&obj.error){
										html=obj.error;
									}
								}
								if(!html){
									html = '没有信息';
								}
								api.set('content.text',html);
							}).fail(function(xhr,status,error){
								api.set('content.text', status + ': ' + error);
							});
							return "Loading...";
						}
					};
				} else if (options.text){
					options.content={
						text:options.text
					};
				}
				var opts = $.extend({},$.fn.tip.defaults,options);
				$(this).qtip(opts);
			});
		}
		$.fn.tip.defaults={
			style:{
				classes:'qtip-light'
			},
			position:{
				target:'mouse',
				adjust:{
					x:2,
					y:15
				}
			}
		};
	})(jQuery)
	//lightbox
	function viewPicture(url){
		if(url){
			if($.isNumeric(url)){
				url = fileDownLoadUrl+"?id="+url;
			}
			var template = '<a id="ucars_picture_view" data-lightbox="ucars" style="display:none;"></a>';
			var p$ = $;
			if(parent){
				p$ = parent.$;
			}
			if(p$("#ucars_picture_view").length==0){
				p$("body").append(template);
			}
			p$("#ucars_picture_view").attr("href",url).trigger("click");
			
		}
	}
	$(function(){
		$(".ucars-tabs").tabs({border:false,fit:true});
		$.xcarsParser.parse();
		$("form .easyui-validatebox").validatebox();
	});
	function _downloadFile(downloadFileId){
		var url = fileDownLoadUrl + "?id="+downloadFileId;
		window.document.location.href=url;
	}
	function _downloadImgServerFile(path,saveName,fileName){
		var param = {'attachment.name':fileName,'attachment.attachPath':path,'attachment.saveName':saveName};
		redirectUrl(imgServerFileDownLoadUrl,param);
	}
	function doTaskHistory(instanceBusinessId){
		requestAtWindow(TASK_HISTORY_ACTION + "?ib.id="+instanceBusinessId, "sys_task_history", "审批记录");
	}
	(function($){
		function setDisabled(target, disabled){
			if(disabled){
				var state = $.data(target, 'xbutton');
				if (!state){
					var options = {};
					if(target.onclick){
						options.onclick=target.onclick;
						target.onclick=null;
					}
					$.data(target,"xbutton",options);
				}
				if(!$(target).hasClass("l-btn-disabled")){
					$(target).addClass("l-btn-disabled");
				}
			} else {
				var state = $.data(target, 'xbutton');
				if(state&&state.onclick){
					target.onclick=state.onclick;
				}
				$(target).removeClass("l-btn-disabled");
			}
			
		}
		$.fn.xbutton=function(options,param){
			if(typeof options == 'string'){
				return methods[options](this,param)
			}
		};
		var methods={
			enable:function(jq){
				return jq.each(function(){
					setDisabled(this,false);
				});
			},
			disable:function(jq){
				return jq.each(function(){
					setDisabled(this,true);
				});
			}
		};
	})(jQuery);
	function formTabsValidate(jq){
		if (jq && jq.length == 1) {
			var tabC = jq.parents("div.tabs-container");
			if (tabC.length == 1) { //目前支持一级
				var invalidPanel = jq.parents(".tabs-container > .tabs-panels > .panel");
				if (invalidPanel.length == 1) {
					if(invalidPanel.css("display") == 'none') {
						var selectedInd = -1;
						var index = -1;
						invalidPanel.parent().children(".panel").each(function(){	
							index++;
							if ($(this).is(invalidPanel)) {
								selectedInd = index;
							}
						});
						
						if (selectedInd > -1) {
							index = -1;
							tabC.find("ul.tabs").children("li").each(function(){
								index++;
								if (index == selectedInd) {
									var text = $(this).find(".tabs-title").text();
									if (text) {
										tabC.tabs("select",text);
									}
								}
							});
						}
					}
				}
			}
		}
	}
	/**
	  * 选择当前用户的所有项目
	  * @param callback
	  */
	 function chooseProjects(callback){
		 ChooseContext.createInstance({
			 id:"chooseProjects",
			 width: 700,
			 url:baseDir+"/preGuarantee/project_toChoose.jhtml",
			 title:global.select_chooseProjects,
			 callback:callback?callback:function(row){
				 if(row&&row.PROJECT_ID&&row.PROJECT_NAME){
					 $("#project_id").val(row.ID);
					 $("#project_name").val(row.PROJECT_NAME);
				 }
				 else{
					 $("#project_id").val("");
					 $("#project_name").val("");
				 }
			 }
		 });
	 }
	 /**
	  * 选择普通或大项目项目
	  * @param callback
	  */
	 function chooseProjectsByCondiction(callback,type){
		 ChooseContext.createInstance({
			 id:"chooseProjects",
			 width: 700,
			 url:baseDir+"/preGuarantee/project_toChoose.jhtml?searchProjectCondiction="+type,
			 title:global.select_chooseProjects,
			 callback:callback?callback:function(row){
				 if(row&&row.PROJECT_ID&&row.PROJECT_NAME){
					 $("#project_id").val(row.ID);
					 $("#project_name").val(row.PROJECT_NAME);
				 }
				 else{
					 $("#project_id").val("");
					 $("#project_name").val("");
				 }
			 }
		 });
	 }
	 /**
	  * 选择当前用户的在保项目
	  * @param callback
	  */
	 function chooseProjectsInGuarantee(callback){
		 ChooseContext.createInstance({
			 id:"chooseProjectsInGuarantee",
			 width: 700,
		
			 url:baseDir+"/preGuarantee/project_chooseProjectsInGuarantee.jhtml",
			 title:global.select_projectsInGuarantee,
			 callback:callback?callback:function(row){
				 if(row&&row.PROJECT_ID&&row.PROJECT_NAME){
					 $("#project_id").val(row.ID);
					 $("#project_name").val(row.PROJECT_NAME);
				 }
				 else{
					 $("#project_id").val("");
					 $("#project_name").val("");
				 }
			 }
		 });
	 }
	 
	 /**
	  * 选择保全项目
	  * @param callback
	  */
	 function choosePreserveProjects(callback){
		 ChooseContext.createInstance({
			 id:"choosePreserveProjects",
			 width: 700,
			 
			 url:baseDir+"/preGuarantee/project_choosePreserveProjects.jhtml",
			 title:global.select_preserveProjects,
			 callback:callback?callback:function(row){
				 if(row&&row.PROJECT_ID&&row.PROJECT_NAME){
					 $("#project_id").val(row.ID);
					 $("#project_name").val(row.PROJECT_NAME);
				 }
				 else{
					 $("#project_id").val("");
					 $("#project_name").val("");
				 }
			 }
		 });
	 }
	 
	 /**
	  * 选择客户经理
	  * @param {} callback
	  */
	 function chooseBrchManager(callback){
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"managerChooser",
			 width: 700,
			 target:target,
			 url:baseDir+"/customer/clientManager_choose.jhtml",
			 title:global.select_user,
			 callback:callback?callback:function(row){
			     var userId = '';
			     var userNo = '';
			     var userName = '';
				 if(row&&row.userId&&row.userName){
					 userId = row.managerId;
					 userNo = row.realName;
					 userName = row.userName;
				 }
				 $("#partner_contractSignatory").val(userId);
				 $("#partner_contractSignatoryNo").val(userNo);
				 $("#partner_contractSignatoryName").val(userName);
			 }
		 });
	 }
	 
 	 /**
	  * 选择角色
	  * @param {} callback
	  */
	 function chooseBrchRole(callback){
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"roleChooser",
			 width: 700,
			 target:target,
			 url:baseDir+"/role/role_toAddRole.jhtml?id="+$('#customer_uid').val(),
			 title:global.select_role,
			 callback:callback?callback:function(row){
			     var userId = '';
			     var userNo = '';
			     var userName = '';
				 if(row&&row.userId&&row.userName){
					 userId = row.managerId;
					 userNo = row.realName;
					 userName = row.userName;
				 }
				 $("#partner_contractSignatory").val(userId);
				 $("#partner_contractSignatoryNo").val(userNo);
				 $("#partner_contractSignatoryName").val(userName);
			 }
		 });
	 }
	 
	 /**
	  * 选择权限
	  * @param {} callback
	  */
	 function chooseBrchPermission(callback){
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"permissionChooser",
			 width: 700,
			 target:target,
			 url:baseDir+"/permission/permission_toAddPermission.jhtml?id="+$('#fiRole_id_zz').val(),
			 title:global.select_permission,
			 callback:callback?callback:function(row){
			     var userId = '';
			     var userNo = '';
			     var userName = '';
				 if(row&&row.userId&&row.userName){
					 userId = row.managerId;
					 userNo = row.realName;
					 userName = row.userName;
				 }
				 $("#partner_contractSignatory").val(userId);
				 $("#partner_contractSignatoryNo").val(userNo);
				 $("#partner_contractSignatoryName").val(userName);
			 }
		 });
	 }
	 
	 function chooseBrchGuarantor(callback){
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"guarantorChoose",
			 width: 600,
			 target:target,
			 url:baseDir+"/guarantor/invest_choose.jhtml",
			 title:global.select_user,
			 callback:callback?callback:function(row){
			     var userId = '';
			     var userNo = '';
			     var userName = '';
				 if(row&&row.userId&&row.userName){
					 userId = row.managerId;
					 userNo = row.realName;
					 userName = row.userName;
				 }
				 $("#partner_contractSignatory").val(userId);
				 $("#partner_contractSignatoryNo").val(userNo);
				 $("#partner_contractSignatoryName").val(userName);
			 }
		 });
	 }
	 
	 function chooseInvestGuarantor(callback){
			var target = getEventTarget();
			 ChooseContext.createInstance({
				 id:"investGuarantorChoose",
				 width: 600,
				 target:target,
				 url:baseDir+"/risk/investGuarantor_choose.jhtml",
				 title:global.select_user,
				 callback:callback?callback:function(row){
				     var userId = '';
				     var userNo = '';
				     var userName = '';
					 if(row&&row.userId&&row.userName){
						 userId = row.managerId;
						 userNo = row.realName;
						 userName = row.userName;
					 }
					 $("#partner_contractSignatory").val(userId);
					 $("#partner_contractSignatoryNo").val(userNo);
					 $("#partner_contractSignatoryName").val(userName);
				 }
			 });
		 }
	 
	 function chooseRelatedProject(callback,param){
		    var chooseRelatedProjectBaseUrl = baseDir+"/assetsManage/relatedProject_choose.jhtml";
		    param = param || {};
			var chooseRelatedProjectUrl = chooseRelatedProjectBaseUrl;
			if (param.applyId) {
				chooseRelatedProjectUrl += '?applyId=' + (param.applyId);
			}
			if (param.borrowerName) {
				if (chooseRelatedProjectUrl.indexOf("?") == -1) {
					chooseRelatedProjectUrl += "?";
				}else{
					chooseRelatedProjectUrl += "&";
				}
				chooseRelatedProjectUrl += 'borrowerName=' + (param.borrowerName);
			}
			if (param.borrowerType) {
				if (chooseRelatedProjectUrl.indexOf("?") == -1) {
					chooseRelatedProjectUrl += "?";
				}else{
					chooseRelatedProjectUrl += "&";
				}
				chooseRelatedProjectUrl += 'borrowerType=' + (param.borrowerType);
			}
		 
			var target = getEventTarget();
			 ChooseContext.createInstance({
				 id:"investGuarantorChoose",
				 width: 600,
				 target:target,
				 url:chooseRelatedProjectUrl,
				 title:global.select_user,
				 callback:callback?callback:function(row){
				     var userId = '';
				     var userNo = '';
				     var userName = '';
					 if(row&&row.userId&&row.userName){
						 userId = row.managerId;
						 userNo = row.realName;
						 userName = row.userName;
					 }
					 $("#partner_contractSignatory").val(userId);
					 $("#partner_contractSignatoryNo").val(userNo);
					 $("#partner_contractSignatoryName").val(userName);
				 }
			 });
		 }
	 
	 function chooseUser(callback,param){	
		 var url=baseDir+"/carLoan/user_choose.jhtml"+"?id="+(param.publishInstId);
			var target = getEventTarget();
			 ChooseContext.createInstance({
				 id:"userChoose",
				 width: 800,
				 target:target,
				 url:url,
				 title:global.select_user,
				 callback:callback?callback:function(row){
				     var userId = '';
				     var userNo = '';
				     var userName = '';
					 if(row&&row.userId&&row.userName){
						 userId = row.managerId;
						 userNo = row.realName;
						 userName = row.userName;
					 }
					 $("#partner_contractSignatory").val(userId);
					 $("#partner_contractSignatoryNo").val(userNo);
					 $("#partner_contractSignatoryName").val(userName);
				 }
			 });
		 }
	//================================
	 
	 function chooseFiUser(callback,param){	
		var url=baseDir+"/customer/customer_chooseUser.jhtml";
		var target = getEventTarget();
		 ChooseContext.createInstance({
			 id:"fiUserChoose",
			 width: 630,
			 target:target,
			 url:url,
			 title:global.select_user,
			 closable:false,
			 callback:callback?callback:function(row){
				 
				 }
			 });
		 }
	 
	 /**银行联行号选择器***/
	 function dataBankChooser(callback,param){	
			var url=baseDir+"/data/dataBank_choose.jhtml";
			var target = getEventTarget();
			 ChooseContext.createInstance({
				 id:"dataBankChooser",
				 width: 700,
				 target:target,
				 url:url,
				 title:'选择收款银行',
				 closable:true,
				 callback:callback?callback:function(row){
					 
					 }
				 });
			 }