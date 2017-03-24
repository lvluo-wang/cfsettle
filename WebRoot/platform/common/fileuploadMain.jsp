<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<tiles:insertDefinition name="FUNC_BLANK">
	<tiles:putAttribute name="body">
		<!--
			uploadFile.jsp的参数:
			attachIds:已经上传的附件ids
			canEdit:能否编辑
			suffix: 区别多个上传控件
			
			js方法:
			getAttachInfos() : 获取已经上传得附件数组
			hasUnUploadAttachs(): 是否存在没有需要上传但没有上传得附件
			
		-->
		<s:include value="/platform/common/uploadFile.jsp">
			<s:param name="suffix">0</s:param>
			<s:param name="attachIds">1424,1425</s:param>
		</s:include>
		<s:include value="/platform/common/uploadFile.jsp">
			<s:param name="suffix">1</s:param>
			<s:param name="attachIds">1428</s:param>
			<s:param name="canEdit">false</s:param>
		</s:include>
		<input type="button" value="保存" onclick="save()"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script>
			function save(){
				if(hasUnUploadAttachs0() || hasUnUploadAttachs1()){
					alert("请上传附件");
					return;
				}
				alert('');
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>