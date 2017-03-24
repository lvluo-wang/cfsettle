<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.ucars.basesystem.dictionary.util.DictionaryUtil"%>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="add" click="doAdd" />
		<x:button iconCls="icon-edit" text="edit" click="doEdit" />
		<x:button iconCls="icon-view" text="view" click="doView" />
		<span class="separator"></span>
		<x:button iconCls="icon-remove" text="del" click="doRemove" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">标题: </td>
					<td><input name="searchBean.title" style="width:130px"></input></td>
					<td class="title">位置: </td>
					<td>
						<x:combobox name="searchBean.position" list="bannerPositionList" textField="codeName" valueField="codeNo" cssStyle="width:200px;"/>
					</td>
					<td class="title">是否激活:</td>
					<td>
						<x:combobox name="searchBean.isActive" value="1" pleaseSelect="false" list="bannerIsAvtiveList" textField="codeName" valueField="codeNo" cssStyle="width:80px;"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
		<div id="outerdivmain" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
			<div id="innerdivmain" style="position:absolute;"><img id="bigimgmain" style="border:5px solid #fff;" src="" />
			</div>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/banner/bannerManage_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="标题" field="title" align="center" width="140"/>
				<x:column title="位置" field="position" align="center" width="140" formatter="formatBannerPosition"/>
				<x:column title="链接" field="href" align="left" width="320"/>
				<x:column title="是否激活" field="isActive" align="center" width="50" formatter="formatIsActive"/>
				<x:column title="排序号" field="sort" align="center" width="50"/>
				<x:column title="操作" field="id" align="center" width="80" formatter="forview"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>



	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:750px;height:480px;display:none;"></div>
	<div id="project_edit_win" style="width:750px;height:480px;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.YES_NO%>","<%=UtilConstant.XXH_BANNER_POSITION%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	
	function formatIsActive(value){
		 return code.getValue("<%=UtilConstant.YES_NO%>",value);
	}
	
	function formatBannerPosition(value){
		 return code.getValue("<%=UtilConstant.XXH_BANNER_POSITION%>",value);
	}
	
	function doQuery(){
		dataTable.load();
	}
		
	function doAdd(){
		var url="<s:url value='/banner/bannerManage_toAdd.jhtml'/>";
		requestAtWindow(url,"project_add_win","<s:text name='add'/>");
	}
	
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/banner/bannerManage_toView.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
		
	}
	
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/banner/bannerManage_toEdit.jhtml' />?id="+selectedId;
			requestAtWindow(url,"project_edit_win","<s:text name='edit'/>");
		}
	}

	function doRemove(){
		if(isSelected(dataTable)){
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
					dataTable.call('<s:url value="/banner/bannerManage_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
	
	function forview(value){
		return '<x:button iconCls="icon-edit" text="预览" click="toViewPicture('+value+')" />';
		//return "<a href='javascript:void(0)' onclick='toViewPicture("+value+")' >预览</a>";
	}
	//  预览图片
	function toViewPicture(id){
		var url = '<s:url value="/banner/bannerManage_toViewPicture.jhtml"/>?id='+id;
		doPost(url,null,function(result){
			viewPicture("http://"+result);
			dataTable.unSelectAll(this);
			//mainImgShow("#outerdivmain", "#innerdivmain", "#bigimgmain", result);
		});
	}
	
	function mainImgShow(outerdiv, innerdiv, bigimg, picBasePath){
	    var src = "http://"+picBasePath;  //获取当src属性
	    $(bigimg).attr("src", src);  //设置#bigimg元素的src属性
	        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
	    $("<img/>").attr("src", src).load(function(){
	        var windowW = $(document.body).width();//获取当前窗口宽度
	        var windowH = $(document.body).height();//获取当前窗口高度
	        var realWidth = this.width;//获取图片真实宽度
	        var realHeight = this.height;//获取图片真实高度
	        var imgWidth, imgHeight;
	        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
	        if(realHeight>windowH*scale) {//判断图片高度
	            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
	            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
	            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
	                imgWidth = windowW*scale;//再对宽度进行缩放
	            }
	        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
	            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
	                        imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
	        } else {//如果图片真实高度和宽度都符合要求，高宽不变
	            imgWidth = realWidth;
	            imgHeight = realHeight;
	        }
	                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放
	         
	        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
	        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
	        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
	        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
	    });
	     
	    $(outerdiv).click(function(){//再次点击淡出消失弹出层
	        $(this).fadeOut("fast");
	    });
	}
	
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
