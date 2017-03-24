/**
 * 组织结构图
 * data 数组数据[{id:id,text:text,child:[]}]
 * opt 配置 viewId:显示区域，size:large,small,medium
 * 
 */
function OrgChartComponent(data,opt){
	var rows=new Array();
	var totalNodeCol=0;
	var rowLast=new Array();
	var spaceClass='orgchart-space-medium';
	var nodeClass='orgchart-node-medium';
	var config=$.extend({
		viewId:null,
		size:medium
	},opt||{});
	
	this.draw=function(viewId){
		init();
		if(viewId&&viewId.length>0){
			config.viewId=viewId;
		}
		if(typeof(data)!='array'&&config.viewId!=null){
			var ht=buildHtml(data,1);
			$("#"+viewId).html(ht);
		}
	};
	this.getHtml=function(){
		return buildHtml(data,1);
	};
	this.getHtmlByRootId=function(rootId){
		init();
		var node=getNode(data,rootId);
		var tmpArray=new Array();
		tmpArray[0]=node;
		return buildHtml(tmpArray,1);
	};
	var init=function(){
		rows=new Array();
		totalNodeCol=0;
		rowLast=new Array();
		if(config.size=='large'){
			spaceClass='orgchart-space-large';
			nodeClass='orgchart-node-large';
		}else if(config.size=='small'){
			spaceClass='orgchart-space-large';
			nodeClass='orgchart-node-large';
		}
	}
	var getNode=function(data,id){
		for(var i=0;i<data.length;i++){
			if(id==data[i].id){
				return data[i];
			}else{
				if(data[i].child&&data[i].child.length>0){
					var n= getNode(data[i].child,id);
					if(n!=null){
						return n;
					}
				}else{
					continue;
				}
			}
		}
		return null;
	}
	var countColNum =function(node){
		var rt=1
		if(node.child&&node.child.length>0){
			var rn=0;
			for(var i=0;i<node.child.length;i++){
				var tmp=node.child[i];
				rn=rn+countColNum(tmp);
			}
			rt=rn;
		}
		return rt;
	};
	var getHeadHtml =function(){
		var len=totalNodeCol*8-2;
		var str="<tr>";
		for(var i=0;i<len;i++){
			str=str+'<td class="'+spaceClass+'"></td>';
		}
		str=str+"</tr>";
		return str;
	};
	var buildHtml=function(data,level){
		var parent=null;
		var lastNode=null;
		for(var i=0;i<data.length;i++){
			var tmp=data[i];
			if(i!=0){
				lastNode=data[i-1];
			}
			
			buildNode(tmp,level,parent,lastNode,i);
			
		}
		var str=getFirstHtml()+getHeadHtml();
		for(var j=0;j<rows.length;j++){
			str=str+'<tr>'+rows[j]+'</tr>';
		}
		str=str+getLastHtml();
		return str;
	};
	var buildNode=function (node,level,parent,lastNode,i){
		var cTotal=countColNum(node);//所占节点数
		var nodeWidth=cTotal*8-2;//所占列数
		var offset=cTotal*4-4;//节点左右空列数
		var rowNum=level*3-3;//节点所在行数
		var topRowNum=rowNum-1;//节点上部所在行数
		var bottomRowNum=rowNum+1;//节点下部所在行数
		var startIndex=0;
		if(parent!=null||lastNode!=null){
			if(lastNode==null){
				startIndex=parent.start;
			}else{
				startIndex=lastNode.end+2;
			}
		}
		var endIndex=startIndex+nodeWidth;
		node.start=startIndex;
		node.end=endIndex;
		var nullHtml='';
		if(startIndex!=0){
			var lastEnd=rowLast[rowNum];
				if(typeof(lastEnd)=='undefined'){
						rowLast[rowNum]=0;
					}
			var nullLen=startIndex-rowLast[rowNum];
			nullHtml='<td colspan="'+nullLen+'" class="orgchart-linenode">&nbsp;</td>';
		}
		var nodeHtml=nullHtml+getNodeHtml(offset,node.text);//节点本身的html
		if(typeof(rows[rowNum])=='undefined'){
				rows[rowNum]='';
			}
		rows[rowNum]=rows[rowNum]+nodeHtml;
		rowLast[rowNum]=node.end;
		var topHtml='';//节点上部html
		if(level>1){
			var topNullHtml='';
			if(startIndex!=0){
				var lastEnd=rowLast[topRowNum];
				if(typeof(lastEnd)=='undefined'){
						rowLast[topRowNum]=0;
					}
				var nullLen=startIndex-rowLast[topRowNum];
				topNullHtml='<td colspan="'+nullLen+'" class="orgchart-linenode">&nbsp;</td>';
			}
			//第一级以下的才有上层
			if(parent.child.length>1){
				if(i>0){
					topNullHtml='<td colspan="'+nullLen+'" class="orgchart-linenode orgchart-linetop">&nbsp;</td>';
				}
			}
			topHtml=topNullHtml+getTopHtml(offset,parent.child.length,i);

			if(typeof(rows[topRowNum])=='undefined'){
				rows[topRowNum]='';
			}
			rows[topRowNum]=rows[topRowNum]+topHtml;
			rowLast[topRowNum]=node.end;
		}
		var bottomHtml='';//节点下部html
		if(node.child&&node.child.length>0){
			var bottomNullHtml='';
			if(startIndex!=0){
				var lastEnd=rowLast[bottomRowNum];
				if(typeof(lastEnd)=='undefined'){
						rowLast[bottomRowNum]=0;
					}
				var nullLen=startIndex-rowLast[bottomRowNum];
				bottomNullHtml='<td colspan="'+nullLen+'" class="orgchart-linenode">&nbsp;</td>';
			}
			//包含子节点的才有下层
			bottomHtml=bottomNullHtml+getBottomHtml(offset,node.child.length);
			if(typeof(rows[bottomRowNum])=='undefined'){
				rows[bottomRowNum]='';
			}
			rows[bottomRowNum]=rows[bottomRowNum]+bottomHtml;
			rowLast[bottomRowNum]=node.end;
		}
		
		if(node.child&&node.child.length>0){
			for(var j=0;j<node.child.length;j++){
				var tmp=node.child[j];
				var tmpLast=null;
				if(j!=0){
					tmpLast=node.child[j-1];
				}
				buildNode(tmp,level+1,node,tmpLast,j);
			}
		}
		if(level==1){
			//计算总的一行所在节点数
			totalNodeCol=totalNodeCol+cTotal;
		}
	};
	var getFirstHtml=function (){
		return '<table class="orgchart-table" cellpadding="0" cellspacing="0" align="center"><tbody>';
	};
	var getLastHtml=function (){
		return '</tbody></table>';
	};
	var getBottomHtml=function (offset,len){
			return '<td colspan="'+(offset+3)+'" class="orgchart-linenode">&nbsp;</td><td class="orgchart-linenode orgchart-lineleft">&nbsp;</td><td colspan="'+(offset+2)+'" class="orgchart-linenode">&nbsp;</td>';

	};
	var getTopHtml=function (offset,len,i){
		if(len==1){
			return '<td colspan="'+(offset+3)+'" class="orgchart-linenode">&nbsp;</td><td class="orgchart-linenode orgchart-lineleft ">&nbsp;</td><td colspan="'+(offset+2)+'" class="orgchart-linenode ">&nbsp;</td>';
		}else{
			if(i==0){
				return '<td colspan="'+(offset+3)+'" class="orgchart-linenode">&nbsp;</td><td class="orgchart-linenode orgchart-lineleft orgchart-linetop ">&nbsp;</td><td colspan="'+(offset+2)+'" class="orgchart-linenode orgchart-linetop">&nbsp;</td>';
			}else if(i==len-1){
				return '<td colspan="'+(offset+2)+'" class="orgchart-linenode orgchart-linetop">&nbsp;</td><td class="orgchart-linenode orgchart-lineright orgchart-linetop">&nbsp;</td><td colspan="'+(offset+3)+'" class="orgchart-linenode">&nbsp;</td>';
			}else{
				return '<td colspan="'+(offset+3)+'" class="orgchart-linenode orgchart-linetop">&nbsp;</td><td class="orgchart-linenode orgchart-lineleft orgchart-linetop ">&nbsp;</td><td colspan="'+(offset+2)+'" class="orgchart-linenode orgchart-linetop">&nbsp;</td>';
			}
		}
		
	};
	var getNodeHtml=function (offset,text){
		var offsetHtml='';
		if(offset>0){
			offsetHtml='<td colspan="'+offset+'" class="orgchart-linenode">&nbsp;</td>';
		}
		return offsetHtml+'<td colspan="6"class="orgchart-node '+nodeClass+'">'+text+'</td>'+offsetHtml;
	};
}