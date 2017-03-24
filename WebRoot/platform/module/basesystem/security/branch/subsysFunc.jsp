<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
$(function(){
	funcFlag=false;
	doPost('<s:url value="/security/brch_changeSubsysMode.jhtml"/>',{"brch.brchId":brchId},function(result){
		if(result){
				var obj=str2obj(result);
				if(obj.error){
					error(obj.error);
					return;
				}
				var subTree=obj.subsys;
				if(subTree){
					$("#subsysTree").tree({
						checkbox:true,
						onCheck:function(node,checked){
							var nodes=$("#subsysTree").tree('getChecked');
							var s = '';
							for(var i=0; i<nodes.length; i++){
								if (s != '') s += ',';
								s += nodes[i].id;
							}
							doPost('<s:url value="/security/brch_querySubsysFunc.jhtml"/>',
								{"subsysIds":s,"brch.brchId":brchId},
								function(ret){
									var o=str2obj(ret);
									$("#unFuncTree").tree({
										checkbox:true
									}).tree('loadData',o.unList)
							});
						}
					}).tree('loadData',subTree);
				}
				
			}
	});
});
</script>
<div style="border:0px;border-bottom:1px solid #bacfe2;height:120px;overflow:auto;">
<ul id="subsysTree" class="easyui-tree"></ul>
</div>
<div style="border:0px;height:270px;overflow:auto;">
 <ul id="unFuncTree" class="easyui-tree"></ul>
</div>
