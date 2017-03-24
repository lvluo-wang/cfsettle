<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_form_area">
			<table class="detail_table">
				<tr>
					<td class="detail_head">
					<s:text name="userFuncAudit"/>[<s:property value="user.userName"/>]
					</td>
				</tr>
				<tr>
					<td class="detail_td">
					<ul id="funcTree" class="easyui-tree"></ul>
					</td>
				</tr>
			</table>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		var treeData=${treeJSONInfo};
		$("#funcTree").tree({
			data:treeData
		});
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

