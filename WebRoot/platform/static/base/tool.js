/**
 * 通用脚本
 * @author cuckoo
 */	
	
	/**
	* 打印页面
	* @param printId 需要打印的元素ID
	*/
	function printPage(printId){
		if (printId) {
			$("#"+printId).printArea({mode: "popup", popClose: true,popHt:1,popWd:1});
		}
		return false;
	}

	/**
	 * 按备份比值返回宽度
	 * @param percent 百分比
	 * @returns {Number} 宽度
	 */
	function fixWidth(percent){
		return document.body.clientWidth*percent;
	}
	/**
	  * 判断指定的数组中是否包含指定的元素
	  * @param o 指定的元素
	  * @param objs 数据集合
	  * @returns {Boolean}
	  */
	 function inArray(o,objs){
		 for(var i=0;i<objs.length;i++){
			 if(objs[i]==o){
				 return true;
			 }
		 }
		 return false;
	 }
	/**
	 * 将字符串转化为对象
	 * @param strData
	 * @returns
	 */
	function parseObj(strData){
		return (new Function("return "+strData))();
	}
	/**
	 * 将字符串转化为对象
	 * @param strData
	 * @returns
	 */
	function str2obj(strData){
		if(typeof strData=="object"){
			return strData;
		}
		if(typeof strData== "string"){
			var str=strData.trim();
			if(str.indexOf("{")==0||str.indexOf("[")==0){
				return (new Function("return "+str))();
			}else{
				return {html:str};
			}
		}
		
		return undefined;
	}
	/**
	 * 检查结果集中是否包含错误对象
	 * 包含错误则返回true，并且弹出错误提示框
	 * @param result 支持字符串和对象
	 * @returns {Boolean}
	 */
	function printError(result){
		if(result&&result.error){
			RemoveRunningDiv();
			error(result.error);
			return true;
		}

		if(typeof result =="string"&&result.indexOf("{")==0){
			var obj=str2obj(result);
			if(obj&&obj.error){
				RemoveRunningDiv();
				error(obj.error);
				return true;
			}
			
		}
		return false;
	}
	function closeWindowByEvent(e){
		$(e.srcElement).parents(".window").find(".panel-tool-close").click();
	}
	
	function closeUcarsWindow(obj){
		var target = obj;
		if (!target) {
			target = getEventTarget();
		}
		if (target) {
			$(target).parents(".window").find(".panel-tool-close").click();
		}
	}
	
	function getEventTarget(){
		var target = null;
		if(window.event){
			target=window.event.srcElement;
		} else {
			target=arguments.callee.caller.arguments[0].target;
		}
		return target;
	}
	
	/**
	 * form转换为js对象
	 * @param formId
	 * @returns
	 */
	function formToObject(formId){
		var o={};
		$("textarea.xcarsui-editor","#"+formId).editor('sync');
		var a=$("#"+formId).serializeArray();
		$.each(a,function(){
			if(o[this.name]){
				if(!o[this.name].push){
					o[this.name]=[o[this.name]];
				}
				o[this.name].push(this.value||'');
			}else{
				o[this.name]=this.value||'';
			}
		});
		$(".currency",$("#"+formId)).each(function(){
			var jo = $(this);
			var tmp = {};
			var v = jo.val();
			if(v){
				v = v.replace(/,/g,"");
			}
			tmp[jo.attr("name")]=v;
			$.extend(o,tmp);
		});
		$(".xcarsui-disabled",$("#"+formId)).each(function(){
			var jo = $(this);
			var tmp = {};
			tmp[jo.attr("name")]=jo.val();
			$.extend(o,tmp);
		});
		return o;
	}
	function formToObj(formId){
		var o={};
		$("textarea.xcarsui-editor","#"+formId).editor('sync');
		var a=$("#"+formId).serializeArray();
		$.each(a,function(){
			if(o[this.name]){
				if(!o[this.name].push){
					o[this.name]=[o[this.name]];
				}
				o[this.name].push(this.value||'');
			}else{
				o[this.name]=this.value||'';
			}
		});
		$(".xcarsui-disabled",$("#"+formId)).each(function(){
			var jo = $(this);
			var tmp = {};
			tmp[jo.attr("name")]=jo.val();
			$.extend(o,tmp);
		});
		return o;
	}	
	/**
	 * 一组form转换为js对象 
	 * @param formIds
	 */
	function formArrayToObject(formIds){
		var ret = {};
		$.each(formIds,function(index,id){
			if($("#"+id).length > 0){
				$.extend(ret,formToObject(id));
			}
		});
		return ret;
	}
	/**
	 * 高度自适应
	 * @param parent 父级容器
	 * @param brother 占高度的兄弟节点
	 * @param me 需要适应高度的节点
	 */
	function autoHeightFit(parent,brother,me){
			 var wp=$(parent);
				var wpHeight=wp.height();
				if((typeof brother=="string")&&brother.length>1){
					$(">"+brother,wp).each(function(){
						wpHeight-=$(this).outerHeight();
					});
				}
				if(wpHeight>0){
					var target=$(">"+me,wp);
					if($.boxModel==true){
						var len=target.outerHeight()-target.height();
						target.css("height",wpHeight-len);
						return (wpHeight-len);
					}else{
						target.css("height",wpHeight);
					}
				}
				return wpHeight;
	}		

	function createFrame(url)
	{
		var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%"></iframe>';
		return s;
	}

	/**
	 * 获取数据组件的多选选项
	 * @param datagrid 数据组件id
	 * @param pkid 主键
	 * @returns "value,value2,value3"
	 */
	function getMutSelected(datagrid,pkid){
		if(typeof datagrid=="object"){
			return datagrid.getSelectedFields(pkid);
		}
		if(typeof datagrid=="string"){
			return getMultiSelectedArray(datagrid,pkid).join(':');
		}
		return null;
	}
	
	/**
	 * 获取选中的字段数组
	 *  
	 */
	function getMultiSelectedArray(datagrid,field){
		if(typeof datagrid=="object"){
			var fields=datagrid.getSelectedFields(field);
			if(fields.length&&fields.length>0){
				return fields.split(",");
			}
			return [];
		}
		if(typeof datagrid=="string"){
			var ret = [];
			var rows = $('#'+datagrid).datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				if( rows[i] ){
					try{
						if(field.indexOf(".") > 0){
							var obj = field.split(".");
							if(obj.length == 2){
								ret.push(rows[i][obj[0]][obj[1]]);
							}else if(obj.length == 3){
								ret.push(rows[i][obj[0]][obj[1]][obj[2]]);
							}
						}else{
							ret.push(rows[i][field]);			
						}
					}catch(e){
						if (e instanceof TypeError) {
							//error(e.name + ":" + e.message);
							ret.push(rows[i][field]);
						} 
					}
				}
			}
			return ret;
		}
		
	}
	
	/**
	 * 获得表格树的多选选项
	 * @param datagrid
	 * @param pkid
	 * @return
	 */
	function getTreeGridMutSelectedComma(datagrid,pkid){
		var ids = [];
		
		var rows = $('#'+datagrid).treegrid('getSelections');
		
		return getFiledValueFromMutRows(rows,pkid);
		
	}
	/**
	 * 获得多选对象的指定属性，以','进行分割
	 * @param datagrid
	 * @param pkid
	 * @return
	 */
	function getFiledValueFromMutRows(rows,pkid){
		var ids = [];
		if(rows && rows.length)
		{
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i][pkid]);
			}
			return ids.join(',');
		}else
		  return '';
		
	}
	
	/**
	 * 判断是否有选中项
	 * @param datagrid
	 * @returns {Boolean}
	 */
	function isSelected(datagrid){
		if(typeof datagrid=="object"){
			var n= datagrid.getSelectedRowNum();
			if(n>0){
				return true;
			}else{
				info(global.notSelectInfo);
				return false;
			}
		}
		if(typeof datagrid=="string"){
			var rows = $('#'+datagrid).datagrid('getSelections');
			if(rows.length>0){
				return true;
			}else{
				info(global.notSelectInfo);
				return false;
			}
		}
	}
	/**
	 * 获取数据组件选中的第一笔
	 * @param datagrid 数据组件
	 * @param pkId 主键
	 * @returns
	 */
	function getSelected(datagrid,pkId){
		if(typeof datagrid=="object"){
			return datagrid.getSelectedField(pkId);
		}
		if(typeof datagrid=="string"){
			var selected=$("#"+datagrid).datagrid("getSelected");
			if(selected){
				return selected[pkId];
			}
			return null;
		}
	}
	/**
	 * 获取选中行的多个字段并封装为指定的数据模型 
	 * @param datagrid 数据控件
	 * @param kvMap {key1:fieldName1,key2:fieldName2}
	 * @returns {key1:fieldValue1,key2:fieldValue2}
	 */
	function getSelected2obj(datagrid,kvMap){
		var fields=[];
		var ret=undefined;
		for(var o in kvMap){
			fields.push(kvMap[o]);
		}
		var data=datagrid.getSelectedMutField(fields);
		if(typeof data=="object"){
			ret={};
			for(var o in kvMap){
				ret[o]=data[kvMap[o]];
			}
		}
		return ret;
	}
	/**
	 * 判断数据组件是否为单选
	 * @param datagrid
	 * @returns {Boolean}
	 */
	 function isSingleSelected(datagrid){
		 if(typeof datagrid=="object"){
				var n= datagrid.getSelectedRowNum();
				if(n==1){
					return true;
				}
				if(n<1){
					info(global.notSelectInfo);
					return false;
				}
				if(n>1){
					info(global.singleSelectInfo);
					return false;
				}
			}
		 if(typeof datagrid=="string"){
			 	var rows = $('#'+datagrid).datagrid('getSelections');
				
				var len = rows.length;
				if(len <1){
					info(global.notSelectInfo);
					len=null;
					rows=null;
					ids=null;
					return false;
				}if(len > 1){
					info(global.singleSelectInfo);
					len=null;
					rows=null;
					ids=null;
					return false;
				}
				len=null;
				rows=null;
				ids=null;
				return true;
		 }
			
		}
	 /**
	  * post方式异步提交
	  * @param url
	  * @param param
	  * @param callback
	  */
	 function doPost(url,param,callback,context){
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
			 traditional:true,
			 dataType:"text",
			 context:context,
			 cache:false,
			 error:function(result){
				 error(global.requestFailedInfo);
			 },
			 success:callback,
			 complete:function (xhr, textStatus){
				 setNull(xhr);
			 }
		 });
		 setNull(tmpUrl);
	 }
	 /**
	  * get方式异步提交
	  * @param url
	  * @param param
	  * @param callback
	  */
	 function doGet(url,param,callback){
		 var tmpUrl;
		 if(url.indexOf('?')==-1){
			 tmpUrl=url+"?randomNum="+Math.random()*99999+"&ajax_request=async";
		 }else{
			 tmpUrl=url+"&randomNum="+Math.random()*99999+"&ajax_request=async";
		 }
		 $.ajax({
			 type:"GET",
			 url:tmpUrl,
			 data:param,
			 traditional:true,
			 cache:false,
			 error:function(result){
				 error(global.requestFailedInfo);
			 },
			 success:callback,
			 complete:function (xhr, textStatus){
				 setNull(xhr);
			 }
		 });
		 setNull(tmpUrl);
	 }
	 /**
	  * 不带屏蔽的异步提交
	  * @param {Object} url
	  * @param {Object} param
	  * @param {Object} callback
	  */
	  function doPostNoMask(url,param,callback){
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
			 traditional:true,
			 cache:false,
			 error:function(result){
				 error(global.requestFailedInfo);
			 },
			 success:callback,
			 complete:function (xhr, textStatus){
				 setNull(xhr);
			 }
		 });
		 setNull(tmpUrl);
	 }
	 /**
	  * post方式同步提交
	  * @param url
	  * @param param
	  * @param callback
	  */
	 function doSyncPost(url,param,callback){
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
			 traditional:true,
			 cache:false,
			 async:false,
			 error:function(result){
				 error(global.requestFailedInfo);
			 },
			 success:callback,
			 complete:function (xhr, textStatus){
				 setNull(xhr);
			 }
		 });
		 setNull(tmpUrl);
	 }
	 /**
	  * 获取树形结构的选中的数据 需要checkbox开启
	  * @param treeId
	  * @returns {String}
	  */
	 function getTreeChecked(treeId){
			var nodes=$("#"+treeId).tree('getChecked');
			var s='';
			if(nodes!=null){
				for(var i=0;i<nodes.length;i++){
					if(s!=''){
						s+=',';
					}
					s+=nodes[i].id;
				}
			}
			return s;
		}

		/**
		 * 日期格式化
		 *  //将一个 Date 格式化为日期/时间字符串。
		 *  alert(  DateFormat.format(new Date(),'yyyy年MM月dd日')  );
		 *  //从给定字符串的开始分析文本，以生成一个日期
		 *  alert(  DateFormat.parse('2010-03-17','yyyy-MM-dd')  );
		 */
		DateFormat = (function(){     
			 var SIGN_REGEXP = /([yMdhsm])(\1*)/g;     
			 var DEFAULT_PATTERN = 'yyyy-MM-dd';     
			 function padding(s,_len){     
				 var len =_len - (s+'').length;     
				 for(var i=0;i<len;i++){s = '0'+ s;}     
				 return s;     
			 };  
			 return({     
				 format: function(date,pattern){     
					 pattern = pattern||DEFAULT_PATTERN;     
					 return pattern.replace(SIGN_REGEXP,function($0){     
						 switch($0.charAt(0)){     
						 case 'y' : return padding(date.getFullYear(),$0.length);     
						 case 'M' : return padding(date.getMonth()+1,$0.length);     
						 case 'd' : return padding(date.getDate(),$0.length);     
						 case 'w' : return date.getDay()+1;     
						 case 'h' : return padding(date.getHours(),$0.length);     
						 case 'm' : return padding(date.getMinutes(),$0.length);     
						 case 's' : return padding(date.getSeconds(),$0.length);     
						 }     
					 });     
				 },     
				 
				
				 parse: function(dateString,pattern){     
					 var matchs1=pattern.match(SIGN_REGEXP);     
					 var matchs2=dateString.match(/(\d)+/g);     
					 if(matchs1.length==matchs2.length){     
						 var _date = new Date(1970,0,1);     
						 for(var i=0;i<matchs1.length;i++){  
							 var _int = parseInt(matchs2[i],10);  
							 var sign = matchs1[i];  
							 switch(sign.charAt(0)){     
							 case 'y' : _date.setFullYear(_int);break;     
							 case 'M' : _date.setMonth(_int-1);break;     
							 case 'd' : _date.setDate(_int);break;     
							 case 'h' : _date.setHours(_int);break;     
							 case 'm' : _date.setMinutes(_int);break;     
							 case 's' : _date.setSeconds(_int);break;     
							 }  
						 }     
						 return _date;     
					 }     
					 return null;     
				 }     
			 });     
			})();
			
			
	


		function toLoginPage(){
			var path = baseDir;
			if (!path){
					path = '/';
			}
			window.location.href=path;
		}
		/**
		 * 提示普通信息
		 * @param message
		 */
		function info(message){
			$.messager.alert(global.info,message,'info');
		}
		/**
		 * 提示错误信息
		 * @param message
		 */
		function error(message){
			$.messager.alert(global.error,message,'error');
		}
		/**
		 * 提示警告信息
		 * @param message
		 */
		function warning(message){
			$.messager.alert(global.warning,message,'warning');
		}
		var setNullObj_loop=['undefined','function'];
		  
		function continueSkipSetNull(obj,field){  
		    var typeo=""+typeof(obj);  
		      
		    if (obj==null) return true;  
		    if (inArray(typeo,setNullObj_loop)){  
		          
		        return true;  
		    }else{  
		          
		    }  
		  
		    return false;
		}                        
		                         
		function setNull(obj,depth){  
		    if (!depth) depth=4;  
		    if (continueSkipSetNull(obj)) return ;  
		    try{
		    	for ( var o in obj){  
			        if (continueSkipSetNull(obj[o],o)){  
			            continue;  
			        }         
			      if (typeof(obj[o]) == "object"){  
			          depth=depth-1;  
			          if (depth>0){  
			        	  setNull(obj[o],depth);
			          }  
			          try{  
			              obj[o]=null;
			          }catch(ex){  
			                
			          }  
			        }else{  
			              try{  
			                 obj[o]=null;  
			              }catch(ex){  
			                    
			              }  
			        }  
			    } 
		    }catch(e){}
		     
		    obj=null;  
		      
		 } 
		/**
		 * 获取菜单显示横坐标
		 * @param menuDiv 菜单div
		 * @param e 触发事件（鼠标）
		 * @returns
		 */
		function getMenuLeft(menuDiv,e){
			var wrap=$("html");
			var rightWidth=wrap.width()-e.pageX;
			var leftPoint=e.pageX;
			var menuWidth=$("#"+menuDiv,wrap).outerWidth();
			if(rightWidth<menuWidth){
				leftPoint=e.pageX-menuWidth;
			}
			wrap=null;
			return leftPoint;
		}
		/**
		 * 
		 * @param menuDiv 菜单div
		 * @param e 触发事件（鼠标）
		 * @returns
		 */
		function getMenuTop(menuDiv,e){
			var wrap=$("html");
			var bottomHeight=wrap.height()-e.pageY;
			var topPoint=e.pageY;
			var menuHeight=$("#"+menuDiv,wrap).outerHeight();
			if(bottomHeight<menuHeight){
				topPoint=e.pageY-menuHeight;
			}
			wrap=null;
			return topPoint;
		}
		
		
		
		
		/**
		 *
		 * 增加keypress事件
		 * @param arg 对象Id数组
		 * @param def 默认事件
		 *
		 */
		function enterKeyPressInit(arg,def){
			if(arg){
				/*
				if (arg instanceof Array) {
					$.each(arg,function(i,v){
						if(v){
							try{
								$('#'+v.id).keypress(function(e){
									if( e.keyCode == 13 ){
										var kp = v.keypress;
										if(!kp){
											kp = def;
										}
										if( kp ){
											eval(kp);
										}
										//默认不提交
										return false;
									}
								});
							}catch(e){
							}
						}
					});
				}
				*/
			}
		}
		
		/**
		 * 判断两个日期的大小。a>b返回1，a==b返回0，a<b返回-1。
		 * @param a
		 * @param b
		 * @returns {Number}
		 */
		function compareDay(a,b){//a , b 格式為 yyyy-MM-dd 
		     var a1 = a.split("-"); 
		     var b1 = b.split("-"); 
		     var d1 = new Date(a1[0],a1[1],a1[2]); 
		     var d2 = new Date(b1[0],b1[1],b1[2]);
		     if(Date.parse(d1) - Date.parse(d2) == 0){ //a==b 
		        return 0; 
		     } 
		     if (Date.parse(d1) - Date.parse(d2) > 0) {//a>b 
		        return 1; 
		     } 
		     if (Date.parse(d1) - Date.parse(d2) < 0) {//a<b 
		        return -1; 
		     } 
		}
		
		/**
		 * 计算某日期之后N天的日期
		 * 
		 * @param theDateStr
		 * @param days
		 * @return String
		 */
		 function getDate(theDate, days){
		 	var a1 = theDate.split("-"); 
		 	var d1 = new Date(a1[0],Number(a1[1])-1,a1[2]); 
		 	
		 	var newDateTime = d1.getTime() + (days*24*60*60*1000);
		 	var newDate = DateFormat.format(new Date(newDateTime),"yyyy-MM-dd");
		 	
		 	return newDate;
		 }		 
		 
		/**
		 * 计算两日期之间的天数
		 * 
		 * @param date1
		 * @param date2
		 * @return 两日期之间的天数
		 */
		 function getDaysBetween(date1, date2){
		 	var a1 = date1.split("-"); 
		    var b1 = date2.split("-"); 
		    var d1 = new Date(a1[0],Number(a1[1])-1,a1[2]); 
		    var d2 = new Date(b1[0],Number(b1[1])-1,b1[2]);
		 	
			return (d2.getTime()-d1.getTime())/(1000*60*60*24);
		 }
		
		//根据控件id清空控件内容,调用时多个控件id用冒号隔开
		//<img src="../public/images/icon_clear.PNG" style="cursor:hand" onclick="clearComponentValue('acctItem_pointName:acctItem_pointId')">
		function clearComponentValue(namelist){
			if(namelist==null) return;
			var names = namelist.split(":");
			for(var i=0; i<names.length; i++){
				$("#"+names[i]).val("");
			}
			return true;
		}
		
	
		
		//将一个字符串中的一部分全部替换成另一个字符串
		//调用方式：var s = "1:2:3"; s = s.replaceAll(":",",");
		String.prototype.replaceAll = function(s1,s2) { 
		    return this.replace(new RegExp(s1,"gm"),s2); 
		};
		
		//保留2位小数位四舍五入
		function round2bit(srcNum){
			return Number(Number(srcNum).toFixed(2));
		}
		
		//保留4位小数位四舍五入
		function round4bit(srcNum){
			return Number(Number(srcNum).toFixed(4));
		}
		
		//保留6位小数位四舍五入
		function round6bit(srcNum){
			return Number(Number(srcNum).toFixed(6));
		}
		
	
	/**
	 * 利率转换工具类
	 **/
	RateTransfer=function(){
        this.rateType = {};   // 利率类型对应的控件Id
        this.oldTypeValue=0;  // 转换前的利率类型值
        this.tagNo={};        // 利率显示符号对应的标签Id
	};
	RateTransfer=function(rateType,oldRateType,tagNo){
         this.rateType = rateType;
				   
	     this.oldTypeValue = $('#'+rateType).val();
	     this.tagNo = tagNo;
	     this.changeRateTagno();
	}; 
    RateTransfer.prototype={ 
      
        /**
         *  rateType, 利率类型对应的控件Id
         *  oldRateType  转换前隐藏的利率类型控件ID
         *  tagNo     利率显示符号对应的标签Id
         **/
	    initRate:function(rateType,oldRateType,tagNo)
		{
		           
				   this.rateType = rateType;
				   
				   this.oldTypeValue = $('#'+rateType).val();
				   this.tagNo = tagNo;
				   this.changeRateTagno();
				   
				   
		},
         /**
         *  根据类型类型转换对应的利率符号
         **/
		changeRateTagno:function()
		{
		   var  newTypeValue = $('#'+this.rateType).val();
		   if(!newTypeValue || newTypeValue=='')
		     newTypeValue = $('#'+this.rateType).xcombobox("getValue");
		    //‱     %  ‰
		    
		   if(newTypeValue=="1") $('#'+this.tagNo).html('‱');
		   if(newTypeValue=="30") $('#'+this.tagNo).html('‰');
		   if(newTypeValue=="360") $('#'+this.tagNo).html('%');
		},
	    /**
         *  rateValueId   利率对应控件Id
         **/
	    changeRateType:function(rateValueId)
		{
		 
		   var rate = $('#'+rateValueId).val();
		    
		   var newTypeValue = $('#'+this.rateType).val();
		  
		   var destRate = this.transRate(rate,this.oldTypeValue,newTypeValue);
		   
		   this.oldTypeValue=newTypeValue;
		   $('#'+rateValueId).val(destRate); 
		  // this.changeRateTagno();  
		},
		 /**
         *  rate     需要转换的利率值
         *  srcType  源利率类型值
         *  destType 目标利率类型值
         **/
	   transRate:function(rate,srcType,destType)
	   {
		    
		    if(rate==""||rate==null) return ""; 
		      
		    var destRate=rate;
		    
		    if(srcType=="1" && destType=="30")			
				destRate = rate*30/10000*1000;	
			else if(srcType=="1" && destType=="360")
			    destRate = rate*360/10000*100;	
				
			else if(srcType=="30" && destType=="360")
			    destRate = rate*12/1000*100;	
			else if(srcType=="30" && destType=="1")
				destRate = rate/30/1000*10000;	
				
			else if(srcType=="360" && destType=="30")
			    destRate = rate/12/100*1000;	
				
			else if(srcType=="360" && destType=="1")
			    destRate = rate/360/100*10000;	
			 
			 return Math.round(destRate*Math.pow(10,4))/Math.pow(10,4);	
		}
	};	
	
    /** json对象转为字符串 */
    function obj2str(o){
    	   var r = [];
    	   if(typeof o == "string" || o == null) {
    	     return o;
    	   }
    	   if(typeof o == "object"){
    	     if(!o.sort){
    	       r[0]="{";
    	       for(var i in o){
    	         r[r.length]=i;
    	         r[r.length]=":";
    	         r[r.length]=obj2str(o[i]);
    	         r[r.length]=",";
    	       }
    	       r[r.length-1]="}";
    	     }else{
    	       r[0]="[";
    	       for(var i =0;i<o.length;i++){
    	         r[r.length]=obj2str(o[i]);
    	         r[r.length]=",";
    	       }
    	       r[r.length-1]="]";
    	     }
    	     return r.join("");
    	   }
    	   return o.toString();
    }
/**
 * config.url 请求的地址
 * config.selectId select的ID
 * config.param 请求的参数
 * config.key option的value
 * config.name select的显示key
 * config.selectedValue 被选中的值
 *
 */
function changeSelectContent(config){
	if( config && config.selectId && config.url ){
		doPost(config.url,config.param,function(result){
			var selectObj = $('#'+config.selectId);
			var key = config.key || 'codeNo';
			var name = config.name || 'codeName';
			var selectedValue = config.selectedValue || '';
			if(selectObj.length > 0){
				if( result ){
					var obj = $.parseJSON(result);
					if(obj.error){
						error(obj.error);
						obj=null;
						return;
					}
					var optionContent = '<option value="">'+global.please_select+'</option>';
					for ( m in obj ) {
						if( parseInt(m) == m ){
							optionContent += "<option value='"+ obj[m][key] + "'" ;
							if( selectedValue == obj[m].codeNo ){
								optionContent += " selected='selected' ";
							}
							optionContent += ">" + obj[m][name] + '</option>';
						}
					}
					
					selectObj.empty();
					if( optionContent ){
						selectObj.html(optionContent);
					}
				}else{
					selectObj.empty();
					selectObj.html('<option value="">'+global.please_select+'</option>');
				}
			}
		});
	}
}
//----------------列格式化------------------
function  format2Money(value){
	if(value==0){
		return "0.00";
	}
	if(value){
		try{
			var ret=new Number(value).formatMoney(2);
			return ret;
		}catch(e){
			return value;
		}
	}
	return "";
}

function format2BigMoney(value){
	if(value==0){
		return "0.00";
	}
	if(value){
		var numArray = value.split(".");
		var arr = numArray[0].toCharArray();
		var str = "", cnt = 0;
		for (var i = arr.length - 1; i >= 0; i--) {
			str = arr[i] + str;
			cnt++;
			if (cnt == 3 && i != 0) {
				str = "," + str;
				cnt = 0;
			}
		}
		if(numArray.length == 1){
			str = str + ".00";
		}else {
			if(numArray[1].length >= 2){
				str = str + "." + numArray[1].substring(0, 2);
			}else{
				str = str + "." + numArray[1] + "0";
			}
		}
		return str;
	}
}

function format2Rate(value){
	if(value==0){
		return "0.0000";
	}
	if(value){
		try{
			var ret=new Number(value).formatMoney(4);
			return ret;
		}catch(e){
			return value;
		}
	}
	return "";
}
function convertDateFromJson(value){
	var ret ;
	if (value) {
		if (value.time) {
			ret = new Date(value.time);
		} else {
			try{
				ret = DateFormat.parse(value,'yyyy-MM-dd hh:mm:ss');
			}catch(e){
			}
		}
	}
	return ret;
}
function format2Date(value){
	var ret = "";
	try{
		var date = convertDateFromJson(value);
		if (date) {
			ret=DateFormat.format(date,"yyyy-MM-dd");
		}
	}catch(e){
	}
	return ret;
}

function format2Month(value){
	var ret = "";
	try{
		var date = convertDateFromJson(value);
		if (date) {
			ret=DateFormat.format(date,"yyyy-MM");
		}
	}catch(e){
	}
	return ret;
}
function format2Minute(value){
	var ret = "";
	try{
		var date = convertDateFromJson(value);
		if (date) {
			ret=DateFormat.format(date,"yyyy-MM-dd hh:mm");
		}
	}catch(e){
	}
	return ret;
}
function format2Time(value){
	var ret = "";
	try{
		var date = convertDateFromJson(value);
		if (date) {
			ret=DateFormat.format(date,"yyyy-MM-dd hh:mm:ss");
		}
	}catch(e){
	}
	return ret;
}
function ucarsValidate(jq){
	if (jq) {
		if (typeof jq === "string") {//ID
			$(".easyui-validatebox,.xcarsui-validatebox","#"+jq).validatebox();
		}else{
			jq.validatebox();
		}
	}
}
function ucarsInValidate(jq){
	if (jq) {
		if (typeof jq === "string") {//ID
			$(".validatebox-invalid,.validatebox-text","#"+jq).removeClass("validatebox-text validatebox-invalid").unbind(".validatebox").each(function(){
				$(this).removeData("validatebox");
			});
		}else{
			jq.removeClass("validatebox-text validatebox-invalid").unbind(".validatebox").removeData("validatebox");
		}
	}
}
function encodeValue(value){
	return encodeURI(encodeURI(value));
}
function formatStringLength(value,len){
	var result = value;
	if (value && len && value.length > len) {
		result = "<span title=\""+ value +"\"'>" + value.substring(0,len) + "..." + "</span>";
	}
	return result;
}
/**
*
* 转化中文数字
*
*/
function transChinaNumber(n){
	var numberArray = ['〇','一','二','三','四','五','六','七','八','九'];
	var unitArray = ['','十','百','千','万'];
	//
	var result = n;
	if (typeof n != 'undefined') {
		var str = "" + n;
		if (/^\d+$/.test(str)) {
			result = '';
			for (var i = 0; i<str.length;i++) {
				result += numberArray[parseInt(str.charAt(i),10)];
				if (str.length-i<=5 && str.length-i > 0) {
					if (result.length > 0) {
						result += unitArray[str.length-i-1];
					}
				}
			}
		}
	}
	return result;
}
//转化利率格式（120-->12.00%）
function formatThousandToHundredPer(val){
	var result = "0.00";
	if(val){
		result = (val/10).toFixed(2);
	}else{
		return '';
	}
	return result + "%";
}
//金额（分）转化为金额（元），带逗号分隔
function formateCent2Yuan(val){
	if(val){
		//return (val/100).toFixed(2);
		return formatCurrency(val/100);
	}else{
		return "0";
	}
}
/**通用覆盖页面*/
//增加遮罩层
function AddRunningDiv(msg) {
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(document).height() }).appendTo("body");
    if(msg==undefined || msg ==''){
        $("<div class=\"datagrid-mask-msg\"></div>").html("处理中，请稍后...").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(document).height() - 45) / 2 });
    }else{
        $("<div class=\"datagrid-mask-msg\" id=\"processBar\"></div>").html(msg).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(document).height() - 45) / 2 });
    }
}
//移除
function RemoveRunningDiv(){
	 $(".datagrid-mask").remove(); 
     $(".datagrid-mask-msg").remove();
}
/**窗口覆盖页面--开发中*/
function AddWinRunningDiv(msg){
	$("<div class=\"win-mask\"></div>").css({ display:"block", width:"100%", height:"100%" }).appendTo(".window-body");
    if(msg==undefined || msg ==''){
        $("<div class=\"win-mask-msg\"></div>").html("处理中，请稍后...").appendTo(".window-body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(document).height() - 100) / 2 });
    }else{
        $("<div class=\"win-mask-msg\"></div>").html(msg).appendTo(".window-body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(document).height() - 45) / 2 });
    }
}
function RemoveWinRunningDiv(){
	 $(".win-mask").remove(); 
     $(".win-mask-msg").remove();
}
//校验手机号码是否合格
function checkMobileNum(value){
	if (value){
		return /^((\(\d{2,3}\))|(\d{3}\-))?1[3578]\d{9}$/.test(value);
	}else{
		return false;
	}
}
/**只能输入正整数*/
function keyPressInt(ob) {
	if (ob.value.length == 1) {
		ob.value = ob.value.replace(/[^1-9]/g, '')
	} else {
		ob.value = ob.value.replace(/\D/g, '')
	}
}
function onAfterPasteInt(ob) {
	if (ob.value.length == 1) {
		ob.value = ob.value.replace(/[^1-9]/g, '')
	} else {
		ob.value = ob.value.replace(/\D/g, '')
	}
}
