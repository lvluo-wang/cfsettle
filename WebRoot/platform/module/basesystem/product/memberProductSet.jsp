<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">
   <tiles:putAttribute name="tool">
		<x:button iconCls="icon-save" onClick="doSave()" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doCancel()" text="cancel"/>
		<form method="post" id="submitForm2" >
			<s:hidden name="member.miNo"></s:hidden>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<div style="height:250px;overflow:auto">
			<ul id="productTree" class="easyui-tree"></ul>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		$(function(){
			//初始化产品树
			doPost('<s:url value="/product/product_setMemberProduct.jhtml"/>',
					{"member.miNo":'<s:property value="member.miNo"/>'},
					function(result){
						if(result){
							if(result.indexOf('{')==0){
								var obj=eval('('+result+')');
								if(obj.error){
									error(obj.error);
									obj=null;
									return;
								}
								if(obj.products){
									$("#productTree").tree({
										checkbox:true,
										data:obj.products
									});
									
								}
							}
						}
			});
		});
			function doSave(){
				$.messager.confirm(global.alert,global.update_confirm_info, function(r){//将保存权限设置，确认吗？
					if(r){
						var ids=getTreeCheckedIdAndPId("productTree");
						var url = "<s:url value='/product/product_saveProductToMember.jhtml'/>?ids="+ids;
						formSubmitForDatagrid(url,"submitForm2","dataTable");
						$('#member_product').window('close');
					}
				});
			}
			function doCancel(){
				$('#member_product').window('close');
			}
		
			 /**
			  * 获取树形结构的选中的数据 需要checkbox开启
			  * @param treeId
			  * @returns 类似id:parentProId,id:parentProId格式字符串
			  */
			 function getTreeCheckedIdAndPId(treeId){
					var nodes=$("#"+treeId).tree('getChecked');
					var s='';
					var sp="";
					if(nodes!=null){
						for(var i=0;i<nodes.length;i++){
							s = s + sp + nodes[i].id+":"+nodes[i].attributes.parentProdId;
							sp = ",";
						}
					}
					return s;
				}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>