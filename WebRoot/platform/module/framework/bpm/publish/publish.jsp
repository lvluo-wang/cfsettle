<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/xcars-tags" prefix="x"%>
<tiles:insertDefinition name="FUNC_BLANK">
	<tiles:putAttribute name="head">
		<link rel="stylesheet" href="<s:url value='/platform/static/css/form.css'/>"   /> 
	</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div>
		<s:form id="upForm" class="formPannel_form" method="post" enctype="multipart/form-data" >             
			<fieldset class="formPannel_fieldset">
				<legend class="formPannel_legend"><s:text name="publish"/></legend>
				<table border="0" cellpadding="0" style="width:90%;">
					<tr>
						<td style="width:40%;height:300px;" valign="top">
							<ul id="productTree"></ul>
						</td>
						<td style="width:60%;" valign="top">
							<s:text name="select_process_file"/>:
							<s:file id="upload" accept="text/html,application/x-zip-compressed"  name="upload"/>  
							<input id="prodNos" type="hidden" name="prodNos" />
						</td>
					</tr>
				</table>
				<div style="text-align:center;">
					<x:button icon="icon-save" click="doSubmit();" text="publish"/>
					<span style="width:20px;display:inline-block;"></span>
					<x:button icon="icon-cancel" click="doCancel();" text="back"/>
			   </div>
			</fieldset>
			
		 </s:form>
	</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
$(document).ready(function() { 
	$("#productTree").tree({
		checkbox:true,
		url:'<s:url value="/product/product_getAllProduct.jhtml"/>?flag='+Math.random()*99999
	});
	$('#upForm').form({   
	       url:"<s:url value='/bpm/publish_publishProcess.jhtml'/>",   
	       onSubmit: function(){  // return false to prevent submit; 
	    	   var fileName = $("#upload").val();
	    	   if (fileName==null || fileName==''){
	    		   info("请选择要发布的流程定义文件。");
	    		   return false;
	    	   }
			   return true;
	      },   
	      success:function(data){ 
	    	  if (data=='1'){
	    		  $.messager.confirm(
					global.alert,
					"发布成功。是否继续发布其他流程?",
					function(r) {
						var url2 = "<s:url value='/bpm/publish_main.jhtml'/>";
						if (r) {
							url2 = "<s:url value='/bpm/publish_toPublish.jhtml'/>";
						}
						redirectUrl(url2);
					});
	    	  }	    		 
	    	  else{
	    		  error("发布失败.<br>请检查文件然后重新发布.");
	    	  }     
	       }   
	    });  
});
	function doSubmit(){
		var selectedNodes = $("#productTree").tree("getChecked");
		if( selectedNodes.length > 0 ){
			var prodNos = "";
			for( var i = 0; i<selectedNodes.length;i++ ){
				if( selectedNodes[i].state == null || $.trim(selectedNodes[i].state) == "" ){
					if (prodNos.length > 0) {
						prodNos += ",";
					}
					prodNos += selectedNodes[i].attributes.prodNo;
				}
			}
			$("#prodNos").val(prodNos);
		}
		$("#upForm").submit();	
	}
	function doCancel(){
		var url = "<s:url value='/bpm/publish_main.jhtml'/>";		
		redirectUrl(url);
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>