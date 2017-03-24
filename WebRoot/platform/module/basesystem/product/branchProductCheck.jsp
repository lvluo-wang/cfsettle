<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">
   <tiles:putAttribute name="tool">
		<x:button iconCls="icon-ok" onClick="doAgree()" text="agree"/>
		<x:button iconCls="icon-revoke" onClick="doRefuse()" text="refuse"/>
		<x:button iconCls="icon-cancel" onClick="doCancel()" text="close"/>
				
		<form id="submitForm">
			<s:hidden name="id"></s:hidden>
		</form>		
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table style="width:800px;">
		<tbody>
			<colgroup>
					<col width="40%"/>
		            <col width="55%"/>
	            </colgroup>	
	            <tr>
					<td class="detail_head_title"><s:text name="交易"/><s:text name="tree"/></td>
					<td class="detail_head_title"><div id="title"><s:text name="说明"/></div></td>
				</tr>
			<tr>
				<td class="detail_td" style="vertical-align:top;">
					<div style="height:400px;width:210px;overflow:auto;">
						<ul id="productTree" class="easyui-tree"></ul>
					</div>
				</td>
				<td class="detail_td" style="vertical-align:top;">
					<div id="productDetail">
						<dl>
							<dt><b><font color='green'>绿色节点</font></b>：</dt>
							<dd>已授权生效的产品</dd>
							<dt><b><strike><font color='green'>划中线绿色节点</font></strike></b>：</dt>
							<dd>待删除的已授权产品</dd>
							<dt><b><font color='#FFCC00'>黄色节点</font></b>：</dt>
							<dd>未生效的授权产品</dd>
						</dl>
					</div>
					
				</td>
			</tr>
		</tbody>
	</table>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
		$(function(){
			//初始化产品树
			doPost('<s:url value="/product/product_viewCheckBranchProduct.jhtml"/>',
					{"id":'<s:property value="id"/>'},
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
										data:obj.products,
										
										onLoadSuccess:function(node, data){
											
											
										}
									});							
								}
								
							}
						}
			});
		});
			
			function doAgree(){
				var info = "未生效的授权产品将生效；待删除的授权产品将删除。确认吗";
				$.messager.confirm(global.alert,info, function(r){//将保存权限设置，确认吗？
					if(r){
						var url = "<s:url value='/product/product_checkBranchProduct.jhtml'/>?isAgree=1";
						//formSubmitForDatagrid(url,"submitForm","dataTable");
						
						var parm=formToObject("submitForm");
						doPost(url,parm,function(){						
							$('#brch_prod').window('close');
							dataTable.refresh();
						});
						
						
					}
					
				});
				
			}
			function doRefuse(){
				var info = "未生效的授权产品将无效；待删除的授权产品将不被删除。确认吗";
				$.messager.confirm(global.alert,info, function(r){//将保存权限设置，确认吗？
					if(r){
						var url = "<s:url value='/product/product_checkBranchProduct.jhtml'/>?isAgree=0";
						//formSubmitForDatagrid(url,"submitForm","dataTable");
						var parm=formToObject("submitForm");
						doPost(url,parm,function(){						
							$('#brch_prod').window('close');
							dataTable.refresh();
						});
					}
					
				});
			}
			
			function doCancel(){
				$('#brch_prod').window('close');
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
	