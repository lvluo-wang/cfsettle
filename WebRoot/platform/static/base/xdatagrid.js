var td_rownumber = '<td><div style="TEXT-ALIGN:{XDATAGRID_TD_ALIGN};WIDTH:{XDATAGRID_TD_WIDTH}px;">{TD_ROWNUMBER}</div></td>';
var td_checkbox = '<TD><DIV style="TEXT-ALIGN:{XDATAGRID_TD_ALIGN}" class="datagrid-cell-check"><INPUT type="checkbox" onclick="{ONCLICK_FUNC}" id="{CHECKBOX_ID}"></DIV></TD>';
var td = '<TD ><DIV style="TEXT-ALIGN:{XDATAGRID_TD_ALIGN};WIDTH:{XDATAGRID_TD_WIDTH}px;word-wrap:break-word;white-space:normal;" class="datagrid-cell">{XDATAGRID_TD_VAL}</DIV></TD>';
//var tr = '<TR id="{TR_ID}" style="HEIGHT:22px" class="{TR_CLASS}" onMouseOver="$(this).addClass(\'datagrid-row-over\')" onMouseOut="$(this).removeClass(\'datagrid-row-over\')">';
var tr = '<TR id="{TR_ID}" style="HEIGHT:22px" class="{TR_CLASS}" onclick="{ONCLICK_FUNC}" ondblclick="{ONDBLCLICK_FUNC}" onMouseOver="$(this).addClass(\'datagrid-row-over\')" onMouseOut="$(this).removeClass(\'datagrid-row-over\')">';
var tr_end = '</TR>';
var row_class_0 = "";
var row_class_1 = "datagrid-row-alt";
var xdatagrid_default_setting = {
	empty_data_template:'<TR><TD><DIV style="TEXT-ALIGN:center;line-height:32px;color:#ff0000;">{empty_data_message}</DIV></TD></TR>',
	empty_data_data:'没有数据'
};
function createFunc(obj,funcName){
	var args = [];
	if(!obj) obj = window;
	for(var i=2; i<arguments.length; i++)
		args.push(arguments[i]);
	return function(){
		return obj[funcName].apply(obj,args);
	};
}

/**定义分页类*/
var Page = Class.create();
Page.prototype={
	currentPage:1,
	pageSize:10,
	startRow:0,
	totalPages:0,
	totalRows:0,
	queryAll:false,
	sortName:"",
	sortOrder:"asc",
	/** 初始化 */
	initialize:function(page){
		if(page){
			//this.currentPage = page["currentPage"];
			this.currentPage = 0;
			this.totalRows = 0;
			this.pageSize = page["pageSize"];
			this.startRow = page["startRow"];
			this.queryAll = page["queryAll"];
			this.sortName = page["sortName"];
			this.sortOrder = page["sortOrder"];
			//this.totalPages = Math.ceil(this.totalRows/this.pageSize);//if 4.9 return 5
			this.totalPages = 0;
		}
	},
	/** 根据总记录条数，每页记录条数以及请求的页数计算当前是第几页,总共页数 */
	calPage_:function(reqPageNo,reqPageSize,totalRows){
		this.pageSize = reqPageSize;
		this.totalRows = totalRows;
		this.totalPages = Math.ceil(this.totalRows/this.pageSize);//if 4.9 return 5
		if((reqPageNo*this.pageSize) > this.totalRows){
			this.currentPage = this.totalPages;
		}else
			this.currentPage = reqPageNo;
	},
	/** 根据服务端返回的page对象，计算当前是第几页,总共页数 */
	calPage:function(ret_page){
		this.currentPage = ret_page["currentPage"];
		this.pageSize = ret_page["pageSize"];
		this.totalRows = ret_page["totalRows"];
		this.totalPages = Math.ceil(this.totalRows/this.pageSize);//if 4.9 return 5
		//if((reqPageNo*this.pageSize) > this.totalRows){
		//	this.currentPage = this.totalPages;
		//}else
		//	this.currentPage = reqPageNo;
	},
	pre:function(){
		if(this.currentPage <= 1 || this.totalRows == 0) return 1;
		return (this.currentPage - 1);
	},
	next:function(){
		return this.currentPage + 1;
		//var nextPage = this.currentPage + 1;
		//if(nextPage > this.totalPages)
		//	return this.totalPages;
		//else
		//    return nextPage;
	},
	last:function(){
		if(this.totalPages == 0) 
			return 1;
		else
			return this.totalPages;
	},
	getPageInfo:function(){
		var info;
		var startNo = 0,endNo = 0;
		if(this.totalRows == 0){
			//startNo = 0,endNo = 0;
		}else{
			startNo = this.pageSize*(this.currentPage-1) + 1;
			if((this.pageSize*this.currentPage) >= this.totalRows)
				endNo = this.totalRows;
			else
				endNo = this.pageSize*this.currentPage;
		}
		//var info = "显示"+startNo+"到"+endNo+"条,共"+this.totalRows+"条记录";
		info = global.xdatagrid.displayMsg.replace("{from}",startNo).replace("{to}",endNo).replace("{total}",this.totalRows);
		return info;
	}
};

/**定义xdatagrid类*/
var Xdatagrid = Class.create();
Xdatagrid.prototype={
	url:"",
	params:{},
	/**true:第一次自动加载，false:第一次不加载数据:*/
	autoload:"true",
	/**Xdatagrid实例名称*/
	id:"",
    pg:new Page(),
    reqPageNo:1,
	reqPageSize:10,
    columns:[],
	jsonData:[],
	/**头部全选复选框*/
	allCheckboxId:"",
	/**复选框选择结果，值为ture或false*/
	selResults:[],
	/**查询表单id*/
	form:null,
	/**是否加载过数据*/
	loaded:false,
	/**是否需要显示分页条*/
	pagebar:false,
	singleSelect:false,
	fit:true,
	emptyDataMessage:'',
	/**初始化*/
	initialize:function(id,url,params,autoload,form,columns,p_page,pagebar,singleSelect,emptyDataMessage){
		this.id = id;
		this.url = url;
		this.params = params;
		this.autoload = autoload;
		this.form = form;
		this.columns = columns;
		this.allCheckboxId = "allcb_"+this.id;
		this.pg = new Page(p_page);
		this.pagebar = pagebar;
		this.singleSelect=singleSelect;
		this.reqPageSize = this.pg.pageSize;
		this.emptyDataMessage=emptyDataMessage;
		//alert(this.autoload);
		var that=this;
		var func=undefined;
		if(this.autoload == "true"){
			func=function(){
				that.load();
			};
		}
		else{
			func=function(){
				that.setPageInfo();
			};
		}
		setTimeout(func);
		var $data=$("#data_"+this.id);
		var $head=$("#head_"+this.id);
		var $wrap=$("#wrap_"+this.id);
		$data.scroll(function(){
			var left=$data.scrollLeft();
			$head.css("margin-left",-left);
		});
		if(typeof afterFit == 'undefined'||!$.isFunction(afterFit)){
				afterFit=function(){
					var parentWrap=$wrap.parent();
					if(parentWrap){
						var pH=parentWrap.height();
						if(pH>20){
							$wrap.css("height",pH);
							var fitHeight=autoHeightFit($wrap,".datagrid-header,.pagebar",".datagrid-body");
							var bw=$.browser;
							if(bw.msie){
								if(bw.version<8){
									//ie7
									$(">.datagrid-body",$wrap).css("height",fitHeight-1);
								}
							}
						}
					}
				};
		}
		
	},
	show:function(){
		$("#wrap_"+this.id).show();
	},
	hide:function(){
		$("#wrap_"+this.id).hide();
	},
	fit:function(){
		var $wrap=$("#wrap_"+this.id);
		var parentWrap=$wrap.parent();
		if(parentWrap){
			var pH=parentWrap.height();
			if(pH>20){
				$wrap.css("height",pH);
				var fitHeight=autoHeightFit($wrap,".datagrid-header,.pagebar",".datagrid-body");
				var bw=$.browser;
				if(bw.msie){
					if(bw.version<8){
						//ie7
						$(">.datagrid-body",$wrap).css("height",fitHeight-1);
					}
				}
			}
		}
	},
	/**内部调用*/
	request:function(){
		this.onLoadBefore();
		//alert("reqPageNo==="+this.reqPageNo);
		this.params.page = this.reqPageNo;
		this.params.rows = this.reqPageSize;
		
		var requestParams = {};
		if (this.params) {
			requestParams = $.extend(requestParams,this.params||{});
		}
		if(this.form != null){
			requestParams=$.extend(requestParams,formToObject(this.form)||{});
		}
		var paras = this.updateParams();
		if(paras){
			requestParams = $.extend(requestParams,paras||{});
		}
		
		doPost(this.url,requestParams,function(result){
			if(result){
				var data = str2obj(result);
				if(data.error){
					error(data.error);
					this.removeRunningDiv();
					return;
				}else{
			    	//this.pg.calPage(this.reqPageNo,this.reqPageSize,data["total"]);
			    	var datastr = "";
			    	this.jsonData = data["rows"];
			    	var row_num = this.jsonData.length;
					if(this.pagebar == false){
						this.pg.totalRows = row_num;
					}else
						this.pg.calPage(data["page"]);
			    	var col_num = this.columns.length;
			    	var row_class = row_class_1;
					
					var tbody = $("#body_"+this.id);
					tbody.parent("table").css("width","auto");
					if (!row_num) {
						var empty_data_data = xdatagrid_default_setting.empty_data_data;
						if (this.emptyDataMessage) {
							empty_data_data = this.emptyDataMessage;
						}
						datastr = xdatagrid_default_setting.empty_data_template.replace("{empty_data_message}",empty_data_data);
						tbody.parent("table").css("width","100%");
					}else{
						for(var i = 0; i<row_num; i++){
							if(row_class_1 == row_class)
								row_class = row_class_0;
							else if(row_class_0 == row_class)
								row_class = row_class_1;
							var tmptr = tr.replace("{TR_ID}", "tr_"+this.id+"_"+i);
							tmptr = tmptr.replace("{TR_CLASS}", row_class);
							//tmptr = tmptr.replace("{ONCLICK_FUNC}", "$('#cb_"+this.id+"_"+i+"').click();");
							tmptr = tmptr.replace("{ONCLICK_FUNC}", this.id+".selectRow("+i+");");
							//tmptr = tmptr.replace("{ONDBLCLICK_FUNC}", this.id+".dblClickRow("+i+");");
							tmptr = tmptr.replace("{ONDBLCLICK_FUNC}", this.id+".dblClickRow("+i+");");
							datastr = datastr + tmptr;
							
							for(var j = 0;j<col_num;j++){
								var tmp = "";
								if(this.columns[j]["checkbox"] == true){
									tmp = td_checkbox.replace("{XDATAGRID_TD_ALIGN}",this.columns[j]["align"]);
									tmp = tmp.replace("{ONCLICK_FUNC}",this.id+".select(this,"+i+",event);");
									datastr = datastr +  tmp.replace("{CHECKBOX_ID}","cb_"+this.id+"_"+i);
								} else if(this.columns[j]["rownumber"] == true) {
										var td_rn = i + 1;
										if (this.pagebar) {
											td_rn = (this.reqPageNo-1)*this.reqPageSize + td_rn;
										}
										tmp = td_rownumber.replace("{TD_ROWNUMBER}",td_rn);
										tmp = tmp.replace("{XDATAGRID_TD_ALIGN}",this.columns[j]["align"]);
										tmp = tmp.replace("{XDATAGRID_TD_WIDTH}",this.columns[j]["width"]);
										datastr += tmp;
								
								}else{
									tmp = td.replace("{XDATAGRID_TD_ALIGN}",this.columns[j]["align"]);
									tmp = tmp.replace("{XDATAGRID_TD_WIDTH}",this.columns[j]["width"]);
									//var val = this.jsonData[i][this.columns[j]["field"]];
									var val = this.getValue(this.jsonData[i],this.columns[j]["field"]);
									if(typeof val == 'undefined'){
										val = '';
									}
									var funName = this.columns[j]["formatter"];
									if(funName){
										var formatFun = createFunc(null,funName,val,this.columns[j]["field"],this.jsonData[i],i);
										val = formatFun();
									}
									datastr = datastr + tmp.replace("{XDATAGRID_TD_VAL}",val);
								}
							}
							datastr = datastr + tr_end;
						}
					}
			    	$("#body_"+this.id).html(datastr);
			    	this.loaded = true;
			    	this.setPageInfo();
					this.initSelect();
				
					if ($wrap=$("#wrap_"+this.id).attr("autoFit") == "true") {						
						var bw=$.browser;
						if(bw.msie){
							var $data=$("#data_"+this.id);
							var osh = $data[0].offsetHeight;
							var ch = $data[0].clientHeight;
							var sh = $data[0].scrollHeight;
							if (osh > ch) {
								if(bw.version<8 && sh > ch){
									$data.height(osh + 17);
								}else{
									$data.height($data.height());
								}
							}
						}
					}
					
					try{
						this.onLoadSuccess(data['rows'],data);
					}catch(e){error(e);}
					this.removeRunningDiv();
				}
			}
	   },this);
	},
	/**加载数据,以json对象形式返回,点击表单查询时候使用*/
	load:function(data){
		this.onLoadBefore();
		this.reqPageNo = 1;
		//this.params.page = this.reqPageNo;
		//this.params.rows = this.reqPageSize;
		if(typeof data=="object"){
	    	//this.pg.calPage(this.reqPageNo,this.reqPageSize,data["total"]);
	    	var datastr = "";
	    	this.jsonData = data["rows"];
	    	var row_num = this.jsonData.length;
			if(this.pagebar == false){
				this.pg.totalRows = row_num;
			}else
				this.pg.calPage(data["page"]);
	    	var col_num = this.columns.length;
	    	var row_class = row_class_1;
	    	for(var i = 0; i<row_num; i++){
				if(row_class_1 == row_class)
					row_class = row_class_0;
				else if(row_class_0 == row_class)
					row_class = row_class_1;
	    		var tmptr = tr.replace("{TR_ID}", "tr_"+this.id+"_"+i);
	    		tmptr = tmptr.replace("{TR_CLASS}", row_class);
	    		//tmptr = tmptr.replace("{ONCLICK_FUNC}", "$('#cb_"+this.id+"_"+i+"').click();");
	    		tmptr = tmptr.replace("{ONCLICK_FUNC}", this.id+".selectRow("+i+");");
	    		//tmptr = tmptr.replace("{ONDBLCLICK_FUNC}", this.id+".dblClickRow("+i+");");
	    		tmptr = tmptr.replace("{ONDBLCLICK_FUNC}", this.id+".dblClickRow("+i+");");
	    		datastr = datastr + tmptr;
	        	for(var j = 0;j<col_num;j++){
	        		var tmp = "";
	        		if(this.columns[j]["checkbox"] == true){
	        			tmp = td_checkbox.replace("{XDATAGRID_TD_ALIGN}",this.columns[j]["align"]);
	        			tmp = tmp.replace("{ONCLICK_FUNC}",this.id+".select(this,"+i+",event);");
	        			datastr = datastr +  tmp.replace("{CHECKBOX_ID}","cb_"+this.id+"_"+i);
	        		}else{
	        			tmp = td.replace("{XDATAGRID_TD_ALIGN}",this.columns[j]["align"]);
	        			tmp = tmp.replace("{XDATAGRID_TD_WIDTH}",this.columns[j]["width"]);
	        			//var val = this.jsonData[i][this.columns[j]["field"]];
	        			var val = this.getValue(this.jsonData[i],this.columns[j]["field"]);
						if(typeof val == 'undefined'){
							val = '';
						}
	        			var funName = this.columns[j]["formatter"];
	        			if(funName){
	        				var formatFun = createFunc(null,funName,val,this.columns[j]["field"],this.jsonData[i],i);
	        				val = formatFun();
	        			}
	        			datastr = datastr + tmp.replace("{XDATAGRID_TD_VAL}",val);
	        		}
	        	}
	        	datastr = datastr + tr_end;
	    	}
	    	$("#body_"+this.id).html(datastr);
	    	this.loaded = true;
	    	//this.setPageInfo();
			this.initSelect();
			try{
				this.onLoadSuccess(data['rows'],data);
			}catch(e){error(e);}
			this.removeRunningDiv();
		}else{
			this.request();
		}
		
	},
	/**发起远程数据处理,处理成功后进行回调处理并刷新**/
	call:function(url,param,callback){
		doPost(url,param,function(result){
			this.refresh();
			if( !printError(result)&&typeof callback != 'undefined' && $.isFunction(callback)){
				callback(result);
			}
		},this);
	},
	/**请求分页数据*/
	reqPage:function(pageflag){
    	if(this.autoload == "false" && this.loaded == false) return;
		if(pageflag == "first"){
			if(1==this.pg.currentPage){
				return;
			}
			this.reqPageNo = 1;
			
		}else if(pageflag == "pre"){
			if(1==this.pg.currentPage){
				return;
			}
			this.reqPageNo = this.pg.pre();
		}if(pageflag == "next"){
			if(this.pg.totalPages==this.pg.currentPage){
				return;
			}
			this.reqPageNo = this.pg.next();
		}if(pageflag == "last"){
			if(this.pg.totalPages==this.pg.currentPage){
				return;
			}
			this.reqPageNo = this.pg.last();
		}else if(pageflag == "refresh"){
		}
		this.request();
	},
	refresh:function(){
		this.reqPage("refresh");
	},
	/**转到第几页*/
	toPage:function(obj){
    	if(this.autoload == "false" && this.loaded == false) return;
		if (event.keyCode == 13){
        	this.reqPageNo = $(obj).val();
        	//load();
        	this.request();
    	}
	},
	pageList:function(obj){
		this.reqPageSize=$(obj).val();
		this.reqPageNo = 1;
		this.request();
	},
	setPageInfo:function(){
		$("#beforePageText_"+this.id).html(global.xdatagrid.beforePageText);
		//设置当前第几页
		$("#toPage_"+this.id).val(this.pg.currentPage);
		//设置总共几页
		$("#totalPages_"+this.id).html(global.xdatagrid.afterPageText.replace("{pages}",this.pg.totalPages));
		if(this.autoload == "false" && this.loaded == false){
			//alert(3445);
			//设置显示1到4,共4记录
			$("#pageInfo_"+this.id).html(global.xdatagrid.displayMsg.replace("{from}",0).replace("{to}",0).replace("{total}",0));
			$("#page_"+this.id).find(".first_page_btn,.pre_page_btn,.next_page_btn,.last_page_btn,.refresh_btn").addClass("l-btn-disabled");
			return;
		}
		//设置显示1到4,共4记录
		$("#pageInfo_"+this.id).html(this.pg.getPageInfo());
		if(1==this.pg.currentPage){
			$("#page_"+this.id).find(".first_page_btn,.pre_page_btn").addClass("l-btn-disabled");
		}else{
			$("#page_"+this.id).find(".first_page_btn,.pre_page_btn").removeClass("l-btn-disabled");
		}
		if(this.pg.totalPages==this.pg.currentPage){
			$("#page_"+this.id).find(".next_page_btn,.last_page_btn").addClass("l-btn-disabled");
		}else{
			$("#page_"+this.id).find(".next_page_btn,.last_page_btn").removeClass("l-btn-disabled");
		}
		$("#page_"+this.id).find(".refresh_btn").removeClass("l-btn-disabled");
	},
	//全选 
	selectAll:function(obj){
		var sel = obj.checked;
		var len = this.selResults.length;
		for(var j=0;j<len;j++){
			this.selResults[j] = sel+"";
			//$("#"+this.checkboxIds[j]).attr("checked",sel);
			//修改复选框选中关系
			//$("#cb_"+this.id+"_"+j).attr("checked",sel);
			//修改选中行样式
			if(sel){
				$("#cb_"+this.id+"_"+j)[0].checked=true;
				$("#tr_"+this.id+"_"+j).addClass("datagrid-row-selected");
			}else{
				$("#cb_"+this.id+"_"+j)[0].checked=false;
				$("#tr_"+this.id+"_"+j).removeClass("datagrid-row-selected");
			}
		}
	},
	/**选中数据，或取消选择*/
	select:function(obj,row,event){
		if(this.singleSelect=="true"){
			var len = this.selResults.length;
			for(var j=0;j<len;j++){
				if(j==row){
					continue;
				}
				if(this.selResults[j] == "true"){
					var _currentCB=$("#cb_"+this.id+"_"+j);
					if(_currentCB&&_currentCB[0]){
						_currentCB[0].checked=false;
					}
					
					this.selResults[j] = "false";
					$("#tr_"+this.id+"_"+j).removeClass("datagrid-row-selected");
				}
			}
		}
		//alert(obj.checked);

		this.selResults[row] = obj.checked+"";
		if(obj.checked)
			$("#tr_"+this.id+"_"+row).addClass("datagrid-row-selected");
		else{
			$("#tr_"+this.id+"_"+row).removeClass("datagrid-row-selected");
			var _allCheck=$("#"+this.allCheckboxId);
			if(_allCheck&&_allCheck[0]){
				
				_allCheck[0].checked=false;
			}
		}
			
		if (event.stopPropagation) {
			event.stopPropagation();
		}
		event.cancelBubble = true;
	},
	/** 点击行就选中 目前还未实现*/
	selectRow:function(row){
		if(this.singleSelect=="true"){
			var len = this.selResults.length;
			for(var j=0;j<len;j++){
				if(j==row){
					continue;
				}
				if(this.selResults[j] == "true"){
					//$("#cb_"+this.id+"_"+j).attr("checked",false);
					var _cbCurrent=$("#cb_"+this.id+"_"+j);
					if(_cbCurrent&&_cbCurrent[0]){
						_cbCurrent[0].checked=false;
					}
					
					this.selResults[j] = "false";
					$("#tr_"+this.id+"_"+j).removeClass("datagrid-row-selected");
				}
			}
		}
		var ckObj = $("#cb_"+this.id+"_"+row);
		//ckObj.attr('checked',!ckObj.attr('checked'));
		
		//alert(ckObj.attr('checked'));
		//if(ckObj.attr('checked') == "checked")
		if(ckObj&&ckObj[0]){
			ckObj[0].checked=!ckObj[0].checked;
			if(ckObj[0].checked)
				this.selResults[row] = "true";
			else
				this.selResults[row] = "false";
		}else{
			if(this.selResults[row] == "true"){
				this.selResults[row] = "false";
			}else{
				if(this.selResults[row] ||this.selResults[row] == "false"||this.selResults[row] == ""){
					this.selResults[row] = "true";
				}
			}
		}
		if(this.selResults[row] == "true")
			$("#tr_"+this.id+"_"+row).addClass("datagrid-row-selected");
		else{
			$("#tr_"+this.id+"_"+row).removeClass("datagrid-row-selected");
			var _allCheck=$("#"+this.allCheckboxId);
			if(_allCheck&&_allCheck[0]){
				
				_allCheck[0].checked=false;
			}
		}
			
	},
	dblClickRow:function(rowIndex){
		this.onDblClickRow(rowIndex,this.jsonData[rowIndex]);
	},
	//初始化选择数据
	initSelect:function(){
		$("#"+this.allCheckboxId).attr("checked",false);
		var _allCheck=$("#"+this.allCheckboxId);
		if(_allCheck&&_allCheck[0]){
			
			_allCheck[0].checked=false;
		}
		//$("#"+this.allCheckboxId).removeAttr("checked");
		var row_num = this.jsonData.length;
		this.selResults = new Array(row_num);
		//this.checkboxIds = new Array(row_num);
		for(var i=0; i < row_num; i++){
			this.selResults[i] = "false";
			//this.checkboxIds[i] = "cb_"+this.id+"_"+i;
		}
	},
	/**根据行数据和fieldName取得value*/
	getValue:function(rowData,fieldName){
		var data = rowData;
		var fns = fieldName.split(".");
		var len = fns.length;
		for(var i=0; i<len; i++){
			if(data[fns[i]] == undefined)
				return undefined;
			else
				data = data[fns[i]];
			//try{
			//	if(data[fns[i]] == undefined)
			//		return "";
			//	else{
			//		data = data[fns[i]];
			//	}
			//}catch(e){
			//	alert("i="+i+",["+fns[i]+"],"+data);
			//}finally{}
		}
		return data;
	},
	/**获得已经选择id串,返回形如1,2,3,4,5串,id对应配置项的第一列*/
	getSelectedIds:function(){
		var sp ="",id,ids="";
		var len  = this.selResults.length;
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				//id = this.jsonData[i][this.columns[0]["field"]];
				id = this.getValue(this.jsonData[i], this.columns[0]["field"]);
				if(id){
					ids = ids + sp + id;
					sp = ",";
				}
			}
		}
		return ids;
	},
	
	/**获得选择的第一行数据*/
	getSelectedFirstRow:function(){
		var len  = this.selResults.length;
		var data=undefined;
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				data = this.jsonData[i];
				break;
			}
		}
		return data;
	},
	/**获得选中的行**/
	getSelectedRows:function(){
		var len  = this.selResults.length;
		var data=[];
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				data.push(this.jsonData[i]);
			}
		}
		return data;
	},
	/**获得选择的第一行的某项数据*/
	getSelectedField:function(fieldName){
		var len  = this.selResults.length;
		var data=undefined;
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				//data = this.jsonData[i][fieldName];
				data = this.getValue(this.jsonData[i], fieldName);
				break;
			}
		}
		return data;
	},
	/**
	 * 获得选中行的指定字段的值
	 * var data = id.getSelectedFields("name");
	 * 则data = "upg1,upg2,upg3"
	 * @param fieldName 与列中的field配置一致。
	 * @return object 如下形式返回upg1,upg2,upg3
	 * */
	getSelectedFields:function(fieldName){
		if(!fieldName || fieldName.length <= 0) return {};
		var len  = this.selResults.length;
		var sp ="",data ="";
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				var tmp_data = this.getValue(this.jsonData[i], fieldName);
				if(tmp_data||tmp_data==0||tmp_data==='false'){
					data=data+sp+tmp_data;
					sp=",";
				}
			}
		}
		return data;
	},
	/**
	 * 获得选择的第一行的多项数据
	 * var data = id.getSelectedMutField(["name","add"]);
	 * 则data[name] = "upg",data[add] = "hangzhou";
	 * @param fieldNames 包含各项fieldName的数组。
	 * @return object 如下形式返回{name:"upg",add:"hangzhou"}
	 * */
	getSelectedMutField:function(fieldNames){
		if(!fieldNames || fieldNames.length <= 0) return {};
		var len  = this.selResults.length;
		var data = {};
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				//data = this.jsonData[i][fieldName];
				for(var j=0; j<fieldNames.length; j++){
					var tmp_data = this.getValue(this.jsonData[i], fieldNames[j]);
					data[fieldNames[j]] = tmp_data;
				}
				break;
			}
		}
		return data;
	},
	/**
	 * 获得选中行的多项数据
	 * var data = id.getSelectedMutFields(["name","add"]);
	 * 则data[0] = {name:"upg",add:"hangzhou"};
	 * @param fieldNames 包含各项fieldName的数组。
	 * @return object 如下形式返回[{name:"upg",add:"hangzhou"}]
	 * */
	getSelectedMutFields:function(fieldNames){
		if(!fieldNames || fieldNames.length <= 0) return {};
		var len  = this.selResults.length;
		var data = new Array();
		
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true"){
				//data = this.jsonData[i][fieldName];
				var inner_data={};
				for(var j=0; j<fieldNames.length; j++){
					var tmp_data = this.getValue(this.jsonData[i], fieldNames[j]);
					inner_data[fieldNames[j]] = tmp_data;
				}
				data.push(inner_data);
			}
		}
		return data;
	},
	/**返回选中的行数*/
	getSelectedRowNum:function(){
		var row_num = 0;
		var len  = this.selResults.length;
		for(var i=0;i<len;i++){
			if(this.selResults[i] == "true")
				row_num = row_num + 1;
		}
		return row_num;
	},
	unSelectAll:function(obj){
		var sel = obj.checked;
		var len = this.selResults.length;
		for(var j=0;j<len;j++){
			this.selResults[j] = sel+"";
			//$("#"+this.checkboxIds[j]).attr("checked",sel);
			//修改复选框选中关系
			//$("#cb_"+this.id+"_"+j).attr("checked",sel);
			//修改选中行样式
			$("#cb_"+this.id+"_"+j)[0].checked=false;
			$("#tr_"+this.id+"_"+j).removeClass("datagrid-row-selected");
		}
	},
	/**提供扩展，用修改查询参数*/
	updateParams:function(){
		return {};
	},
	/**响应双击事件,用户可以自己实现事件处理程序*/
	onDblClickRow:function(rowIndex,rowData){
		
	},
	/**数据请求成功返回事件**/
	onLoadSuccess:function(rowData,data){
		this.removeRunningDiv();
	},
	onLoadBefore:function() {
		this.addRunningDiv("数据正在加载中...");
	},
	addRunningDiv:function(str){
		$("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(document).height() }).appendTo("body");
        $("<div class=\"datagrid-mask-msg\"></div>").html(str).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(document).height() - 45) / 2 });
	},
	removeRunningDiv:function(){
		$(".datagrid-mask").remove(); 
	    $(".datagrid-mask-msg").remove();
	},
	/** 设置数据高度 */
	setHeight:function(h){
		if(/^\d*$/.test(h)){
			if(h>20){
				var $wrap=$("#wrap_"+this.id);
				$(".datagrid-body",$wrap).height(h-$(".datagrid-header",$wrap).height()-$(".pagebar",$wrap).height());
			}
		}
	}
};

