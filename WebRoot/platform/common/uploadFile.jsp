<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	//attachIds: 附件id
	//canEdit:能否编辑
	//suffix: 后缀字符,主要解决同一页面多次引用的问题
	//customShow:自定义显示
	//callback:回调函数
	boolean canEdit = !"false".equals(request.getParameter("canEdit"));
	String suffix = request.getParameter("suffix") == null ? "" : request.getParameter("suffix");
	boolean customShow = !"false".equals(request.getParameter("customShow"));
	String callback = request.getParameter("callback");
	boolean refresh = "y".equals(request.getParameter("refresh"));
	boolean multiple = !"n".equals(request.getParameter("multiple"));
	boolean hasLoaded = "y".equals(request.getParameter("hasLoaded"));
	String opt = request.getParameter("opt") == null ? "{}" : request.getParameter("opt");
	boolean imgServer = "true".equals(request.getParameter("imgServer"));
	String nowater = request.getParameter("nowater") == null ? "0" : request.getParameter("nowater");
	String fileTypeExt = request.getParameter("fileTypeExt") == null ? "1" : request.getParameter("fileTypeExt");
%>

<%
	if( canEdit && !hasLoaded ){
%>
	
<%
	}
%>

<style>
.uploadStyle{
	font-size: 12px;	
}
.uploadStyle a {color:#000;text-decoration:none;}
.uploadStyle a:hover  {color:#000;text-decoration:none;}
</style>
<span class="uploadStyle">
<%
	if(canEdit){
%>
	<div id="fileQueue<%=suffix%>"></div>
	<input type="file" name="fileInput" id="fileInput<%=suffix%>" style="display:none"/>
	<a href="javascript:_startUpload<%=suffix%>();"><s:text name="file.startUpload"/></a>&nbsp;&nbsp;
	<a href="javascript:$('#fileInput<%=suffix%>').uploadifyClearQueue();"><s:text name="file.cancelUpload"/></a>
<%
	}
%>
<br/><!-- <font color=green id="uploadedListLabel"><s:text name="file.uploadedList"/>:</font> -->
<div id="uploadedFilesDiv<%=suffix%>" style="padding-left:10px;"></div>
</span>

<script type="text/javascript">
	var _fileUpload<%=suffix%>;
	var uploadedFilesDiv<%=suffix%>;
	
	$(function(){
		<%
			if(!customShow){
		%>
			uploadedFilesDiv<%=suffix%> = $("#uploadedFilesDiv<%=suffix%>");
			//展示已上传的文件
			_reloadUploadedList<%=suffix%>();
		<%
			}
		%>
		
		
		<%
			if(canEdit){
		%>
		<%
			if (!refresh){
		%>
		if (typeof _fileUpload<%=suffix%> == 'undefined') {
		<%}%>
			_fileUpload<%=suffix%>=new FileUploadComponent('fileInput<%=suffix%>',"<%=session.getId()%>",$.extend({
				queueID:'fileQueue<%=suffix%>',
				fileDataName:'fileInput',
				
				scriptData:{"imgServer":<%=imgServer%>,"nowater":<%=nowater%>,"fileTypeExt":<%=fileTypeExt%>},
				
				<%
					if(multiple){
				%>
					multi:true,
					queueSizeLimit:5,
				<%
					}
				%>
				onComplete : function(event,queueID, fileObj, response,data) {
					var json = eval("("+response+")");
					<%
						if(customShow){
							if(callback != null && callback.length() > 0){
					%>
								<%=callback%>(json);
					<%
							}
						}else{
					%>
						_uploadCallback<%=suffix%>(json);
					<%
						}
					%>
					
				}
			},<%=opt%>));
		<%
			if (!refresh){
		%>
		}
		<%}%>
		<%
			}
		%>
	});

	<%
		if(canEdit){
	%>
	//开始上传文件
	function _startUpload<%=suffix%>(){
		jQuery('#fileInput<%=suffix%>').uploadifyUpload();
	}
	
	<%
		}
	%>
	

	
	//============================start
	function _uploadCallback<%=suffix%>(attach){
		var attachmentItem = "<span id='_attachId_" + attach.id +"'><a href='#' class='_attach_info' attachId='"+ attach.id +"' onclick=\"_downloadFile('" + attach.id + "')\">" +attach.name + "</a>";
		<%
			if(canEdit){
		%>
		attachmentItem +="&nbsp;&nbsp;<a href='#' onclick=\"_deleteAttachment('" + attach.id + "')\"><s:text name="del"/></a>";
		<%
			}
		%>
		attachmentItem += "<br/></span>";
		uploadedFilesDiv<%=suffix%>.append(attachmentItem);
	}	
	<%
		if(canEdit){
	%>
	function _deleteAttachment(attachmentId){
		$.ajax({
			type:"POST",  
			url:"<s:url value='/component/attachment_delete.jhtml'/>",  
			dataType: 'json',  
			cache:false,  
			data:{id:attachmentId},
			error:function(){
			},
			success:function(){
				$("#_attachId_"+attachmentId).remove();
			}
		});
	}
	<%
		}
	%>
	
	function _reloadUploadedList<%=suffix%>(){
		uploadedFilesDiv<%=suffix%>.empty();
		var uploadedIds = "${param.attachIds}";
		if(uploadedIds != ""){
			$.ajax({
				type:"POST",  
				url:"<s:url value='/component/attachment_getAttachments.jhtml'/>",  
				dataType: 'json',  
				cache:false,  
				data:{ids:uploadedIds},
				error:function(){
				},
				success:function(result){
					if (result && result.indexOf("<!DOCTYPE") == -1) {
						var attachments = eval(result);
						for( var i = 0; i< attachments.length;i++ ){
							_uploadCallback<%=suffix%>(attachments[i]);
						}
					}
				}
			});
		}
	}
	function getAttachInfos<%=suffix%>(){
		var attachInfos = [];
		uploadedFilesDiv<%=suffix%>.find("a._attach_info").each(function(){
			var attach = {id:$(this).attr("attachId"),name:$(this).text()};
			attachInfos.push(attach);
		});
		return attachInfos;
	}
	function hasUnUploadAttachs<%=suffix%>(){
		var ret = false;
		var fq = $("#fileQueue<%=suffix%>");
		if(fq.length > 0){
			ret = fq.children("div").length > 0;
		}
		return ret;
	}
</script>