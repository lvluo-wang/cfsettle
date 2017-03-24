var baseDir="";
var isWorkSpaceBusy=false;
var global={};
function setBaseDir(dir){
	baseDir=dir;
}
/**
 * 选择控件上下文
 */
var ChooseContext={
		createInstance:function(config){
			var defaultConfig=$.extend({
				id:undefined,
				url:"",
				flag:false,
				width:600,
				title:"选择",
				win:{},
				close:function(){},
				init:{},
				refresh:false,
				target:undefined,
				callback:undefined,
				closable:true
			},config||{});
			if(defaultConfig.id){
				if(ChooseContext[defaultConfig.id] && !defaultConfig.refresh){
					//过滤掉默认配置和当前定制配置中都没有的属性，flag比较特殊属于Boolean类型故排除
					for(var i in ChooseContext[defaultConfig.id]){
						if(!defaultConfig[i]&&(i!="flag")){
							ChooseContext[defaultConfig.id][i]=undefined;
						}
					}
					//过滤之后，把定制的属性赋值给控件
					ChooseContext[defaultConfig.id]=$.extend(ChooseContext[defaultConfig.id],config||{});
				}else{
					//根据默认配置和定制配置赋值给控件
					ChooseContext[defaultConfig.id]=defaultConfig;
				}
				if(!ChooseContext[defaultConfig.id].flag){
					if (ChooseContext[defaultConfig.id].refresh) {
						$("#"+ChooseContext[defaultConfig.id].id).remove();
					}
					doSyncPost(ChooseContext[defaultConfig.id].url,null,function(result){
						if(result){
							var domStr="<div id='"+ChooseContext[defaultConfig.id].id+"' class='easyui-window' closed='true' modal='true' style='width:"+ChooseContext[defaultConfig.id].width+"px;'>"+result+"</div>";
							var dom=$(domStr);
							dom.appendTo("body");
							$.parser.parse(dom);
							ChooseContext[defaultConfig.id].win=dom;
							ChooseContext[defaultConfig.id].close=function(){
								ChooseContext[defaultConfig.id].win.window("close");
							};
							ChooseContext[defaultConfig.id].flag=true;
						}
					});
				}
				ChooseContext[defaultConfig.id].win.window({
					title:ChooseContext[defaultConfig.id].title,
					resizable:false,
					minimizable:false,
					maximizable:false,
					collapsible:false,
					closable:ChooseContext[defaultConfig.id].closable,
					modal:true
				}).window("open");
				$.xcarsParser.parse(ChooseContext[defaultConfig.id].win);
				ChooseContext[defaultConfig.id].init();
			}
		}
};
/**提供类的语法*/
Class = {
	create:function(){
		return function(){this.initialize.apply(this,arguments);};
	}
};
$.boxModel=( document.compatMode === "CSS1Compat" );