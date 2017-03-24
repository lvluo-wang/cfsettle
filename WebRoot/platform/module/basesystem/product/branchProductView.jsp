<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_form_area">
			<table class="detail_table">
		<tbody>

			<tr>
				<td class="detail_td" style="width:250px;" >
					<div style="width:250px;height:250px;overflow:auto;">
						<ul id="productTree2" class="easyui-tree"></ul>
					</div>
				</td>
				<td class="detail_td">
						<b><font color='green'>绿色节点</font>：<br/>&nbsp;&nbsp;&nbsp;&nbsp;已授权生效的产品</b><br/><br/>
						<b><strike><font color='green'>划中线绿色节点</font></strike>：<br/>&nbsp;&nbsp;&nbsp;&nbsp;待删除的已授权产品</b><br/><br/>
						<b><font color='#FFCC00'>黄色节点</font>：<br/>&nbsp;&nbsp;&nbsp;&nbsp;未生效的授权产品</b><br/>
				</td>
			</tr>

		</tbody>
	</table>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			//初始化产品树
			doPost('<s:url value="/product/product_viewBranchProduct.jhtml"/>',
					{"id":'<s:property value="id"/>'},
					function(result){
						if(!printError(result)){
							var obj=str2obj(result);
							if(obj.products){
								$("#productTree2").tree({
									data:obj.products
								});							
							}
						}
			});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
