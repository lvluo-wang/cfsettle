/***
 * 平台提供的脚本文件
 */
/*-----------------base-------------------*/
var baseDir="";
var isWorkSpaceBusy=false;
var global={};
function setBaseDir(dir){
	baseDir=dir;
}
//选择控件上下文

Class = {
	create:function(){
		return function(){this.initialize.apply(this,arguments);};
	}
};
//将一个字符串中的一部分全部替换成另一个字符串
//调用方式：var s = "1:2:3"; s = s.replaceAll(":",",");
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};
/*-----------------tool-------------------*/
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
 * form转换为js对象
 * @param formId
 * @returns
 */
function formToObject(formId){
	var o={};
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
 * 获取字符串长度
 * @param {Object} str
 * @return {TypeName} 
 */
function getLength(str){
	var ret = 0;
	if( str ){
		ret = str.length;
	}
	return ret;
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
/**
 * 检查结果集中是否包含错误对象
 * 包含错误则返回true，并且弹出错误提示框
 * @param result 支持字符串和对象
 * @returns {Boolean}
 */
function printError(result){
	if(result&&result.error){
		error(result.error);
		return true;
	}
	if(typeof result =="string"){
		var obj=str2obj(result);
		if(obj.error){
			error(obj.error);
			return true;
		}
	}
	return false;
}
/**
 * 根据事件来关闭按钮所隶属的窗口容器
 * @param e
 */
function closeWindowByEvent(e){
	$(e.srcElement).parents(".window").find(".panel-tool-close").click();
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
		 dataType:"text",
		 context:context,
		 cache:false,
		 error:function(result){
			 error(global.requestFailedInfo);
		 },
		 success:callback
	 });
}
/**
 * post方式同步提交
 * @param url
 * @param param
 * @param callback
 */
function doSyncPost(url,param,callback,context){
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
		 context:context,
		 cache:false,
		 async:false,
		 error:function(result){
			 error(global.requestFailedInfo);
		 },
		 success:callback
	 });
}
/**
 * get方式异步提交
 * @param url
 * @param param
 * @param callback
 */
function doGet(url,param,callback,context){
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
		 dataType:"text",
		 context:context,
		 cache:false,
		 error:function(result){
			 error(global.requestFailedInfo);
		 },
		 success:callback
	 });
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
function enterKeyPressInit(ids,event){
	if(ids){
		if (ids instanceof Array) {
			$.each(ids,function(i,v){
				if(v){
					try{
						$('#'+v.id).keypress(function(e){
							if( e.keyCode == 13 ){
								var kp = v.keypress;
								if(!kp){
									kp = event;
								}
								if( kp ){
									eval(kp);
								}
								return false;
							}
						});
					}catch(e){
					}
				}
			});
		}
	}
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
	 function padding(s,len){     
		 var _len =len - (s+'').length;     
		 for(var i=0;i<_len;i++){s = '0'+ s;}     
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
					 var _int = parseInt(matchs2[i]);  
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
	   var newTypeValue = $('#'+this.rateType).val();
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
function formatMoney(value)
{
	return new Number(value).formatMoney(2);
};
/***
*利率格式化
*/
function formatRate(value)
{		
	return new Number(value).formatMoney(4);
};
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
/*-----------------component-------------------*/
/**
 * 数据分页组件,适用于easyui的数据分页方式
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
				$("#"+tableId).datagrid("options").queryParams=queryParams;
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
	        printError(result);
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
							printError(result);
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
						printError(result);
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
		script:baseDir+'/commons/uploadAction.jhtml;jsessionid='+sid,
		uploader:baseDir+'/platform/static/plugins/uploadfy/uploadify.swf?tmp='+ new Date().getTime(),
		fileDataName:fileInputId,
		queueID : 'fileQueue', //和存放队列的DIV的id一致 
		auto : false, //是否自动开始 
		method: 'Get',
		multi: false, //是否支持多文件上传 
		buttonText: 'Browse', //按钮上的文字 
		displayData: 'speed',//有speed和percentage两种，一个显示速度，一个显示完成百分比 
		simUploadLimit: 3, //一次同步上传的文件数目 
		sizeLimit: 19871202, //设置单个文件大小限制 
		queueSizeLimit: 1, //队列中同时存在的文件个数限制 
		cancelImg:baseDir+'/platform/static/images/cancel.png',
		fileDesc : '支持格式:doc/docx/rar/zip/pdf.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
		fileExt : '*.doc;*.docx;*.rar;*.zip;*.pdf',//允许的格式   
	   onError : function(event, queueID,fileObj) {
		    	info('文件: ' + fileObj.name + '上传失败');
			},
	   onCancel : function(event, queueID,fileObj) {
				info('已取消上传文件: ' + fileObj.name);
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
 * 在指定的窗口显示请求的结果
 * @param url
 * @param win
 * @param title
 */
function requestAtWindow(url,win,title){
	 doPost(url,null,function(result) {
		 if(!printError(result)){
			 var w = $('#'+win);
				w.html(PREFIX_CONTENT+result + SUBFIX_CONTENT);
				$.parser.parse(w);
				w.show();
				w .window({
						title:title,
						resizable:false,
						minimizable:false,
						maximizable:false,
						collapsible:false,
						closable:true,
						modal:true
					});
				$(".xcarsui-disable",w).xdisable('disable');  
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
			var action = pageBodyUrl+"?targetUrl="+url+"&ajax_request=async&isRedirectUrl=1";
			var paramStr = "";
			var k = null;
			for( k in param ){
				paramStr += '<input name="' + k +'" value="'+ param[k] + '"/>';
			}
			$("body").prepend('<div style="display:none;"><form id="changeUrlForm" action="'+action+'" method="post">'+paramStr+'</form></div>');
			$("#changeUrlForm")[0].submit();
		}else{
			var tmpUrl = url;
			if( tmpUrl.indexOf("?") > -1 ){
				tmpUrl += "&wrap=1";
			}else{
				tmpUrl += "?wrap=1";
			}
			location.href=tmpUrl;
		}
	}
}
/**
 * 链接访问控制，当一个页面中“a”标记的链接需要访问控制是，在页面的js区域调用此方法
 * "a"标记必须包含参数，acl="true" 和 url="";
 */
function ACL(){
	 doPost(securityACL,null,function(result){
		 if(result){
			var obj=eval('('+result+')');
       	if(obj.error){
       		error(obj.error);
			}
       	var allUrl=obj.allAuth;
       	var userUrl=obj.userAuth;
       	$("a[acl]").each(function(){
       		var dom=$(this);
       		var url=dom.attr("url");
       		var enFlag=dom.attr("acl");
       		if(enFlag){
       			if(inArray(url,allUrl)){
	        			if(inArray(url,userUrl)){
	        				return;
	        			}else{
	        				dom.hide();
	        			}
	        		}
       		}
       		
       	});
		 }
	 });
}