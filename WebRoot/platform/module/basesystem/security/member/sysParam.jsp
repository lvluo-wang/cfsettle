<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
	<tiles:putAttribute name="body">
		<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr style="font-weight:bold;color:#15428b;background:url(../platform/static/easyui/themes/default/images/panel_title.png) repeat-x;font-size:12px;">
				<td style="height:26px;width:30%;padding-left:6px;vertical-align:middle;border-right:1px solid #99BBE8;border-bottom:1px solid #99BBE8;" >接入信息</td>
				<td style="width:70%;padding-left:6px;vertical-align:middle;border-bottom:1px solid #99BBE8;">参数信息</td>
			</tr>
			<tr>
				<td style="border-right:1px solid #99BBE8;" valign="top">
					<ul id="memberTree"></ul>
				</td>
				<td valign="top">
				<div style="padding:0px;padding-top:1px;padding-left:2px;margin:0px;border:0px;border-bottom:1px solid #ccc;background:#efefef;width:100%;height:28px;">
					<x:button click="doAssign" text="save" icon="icon-save"/>
				</div>
				<x:datagrid id="sysParamDG" url="/security/member_sysParamList.jhtml" pagebar="false" autoload="false">
					<x:columns>
						<x:column title="" field="paramId" checkbox="true" width="20" />
						<x:column title="param.code" field="paramKey" width="150" align="left" />
						<x:column title="param.name" field="paramName" width="150" align="left" />
						<x:column title="param_value" field="tempValueDisp" width="100" />
						<x:column title="user_value" field="paramValueDisp" width="100" />
						<x:column title="remark" field="remark" width="200" />
					</x:columns>
				</x:datagrid>
				</td>
			</tr>
		</table>

	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script language="javascript">
function initPage(){
	$('#memberTree').tree({
		url:'<s:url value="/security/member_sysParamMember.jhtml"/>',
		onClick:function(node){
			sysParamDG.onLoadSuccess = select;
			sysParamDG.load();
		}
	});
}
function select(){
	var nid = $('#memberTree').tree('getSelected').id;
	if( nid ){
		doPost('<s:url value="/security/member_getSelectedSysParamByMember.jhtml"/>',{miNo:nid},function(result){
			if( result ){
				var obj=eval('('+result+')');
	        	if(obj.error){
	        		error(obj.error);
				}else{
					var rows = sysParamDG.jsonData;
					for(var i = 0; i < rows.length; i++){
						var r = rows[i];
						for( var j = 0; j < obj.length; j++ ){
							if( obj[j].paramKey == r.paramKey ){
								sysParamDG.selectRow(i);
								break;
							}
						}
					}
				}
			}
		});
	}
}

function doAssign(){
	var mt = $('#memberTree').tree('getSelected');
	if( mt ){
		var ids = sysParamDG.getSelectedIds("paramId");
		doPost('<s:url value="/security/member_assignSps.jhtml"/>',{"ids":ids,"miNo":mt.id},function(result){
			if(result){
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
					return;
				}
			}else{
				info(global.operate_success);
			}
		});
	}else{
		error(global.select_member);
	}
}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

