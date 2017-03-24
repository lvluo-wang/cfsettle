<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">

<tiles:putAttribute name="end">
<script type="text/javascript">
var chooseContext_tradecategory=   ChooseContext["choose_tradecategory"];
var selectTradecontextPath = '<s:url value="/dictionary/trade_viewListTrade.jhtml"/>';

chooseContext_tradecategory.init=function(){
     
		     var _param = chooseContext_tradecategory.param;
		    
		     
			 $("#chooseTradecategoryQueryForm").form("clear");
			
		 	$('#chooseTradecategoryDataGrid').treegrid({
					fit:false,//自适应屏幕高度和宽度
					striped:true,//隔行变色
					rownumbers: true,
					singleSelect:_param.isSignle,
					url:selectTradecontextPath,
					idField:'id',
					treeField:'tradeName',
					
					frozenColumns:[[
		          	 {title:'<s:text name="trade.tradeName"/>',field:'tradeName',width:250,
			                formatter:function(value){
			                	return '<span style="color:red">'+value+'</span>';
			                }
		                }
					]],
					columns:[[
						{field:'tradeNo',title:'<s:text name="trade.tradeNo"/>',width:120,rowspan:2},
						
						{field:'tradeDesc',title:'<s:text name="trade.tradeDesc"/>',width:200,rowspan:2}
					]],
					
					
					onDblClickRow:function(row){
					    if(chooseContext_tradecategory.callback && $.isFunction(chooseContext_tradecategory.callback))
					    	chooseContext_tradecategory.callback($('#chooseTradecategoryDataGrid').treegrid('getSelections'));
					    chooseContext_tradecategory.close();
						
					},
					
					
					onBeforeLoad:function(row,param){
					
						if (row){
						
							$(this).treegrid('options').url = selectTradecontextPath + "?trade.pid=" + row.id;
						} else {
							$(this).treegrid('options').url = selectTradecontextPath;
						}
					}
		});

};

$("#choose_tradecategory_btn_select").unbind().click(function(){
	   if(chooseContext_tradecategory.callback && $.isFunction(chooseContext_tradecategory.callback))
		   chooseContext_tradecategory.callback($('#chooseTradecategoryDataGrid').treegrid('getSelections'));
		chooseContext_tradecategory.close();
	});
$("#choose_tradecategory_btn_cancel").unbind().click(function(){
    if(chooseContext_tradecategory.callback && $.isFunction(chooseContext_tradecategory.callback))
   		 chooseContext_tradecategory.callback();
	chooseContext_tradecategory.close();
});

$("#choose_tradecategory_btn_query").unbind().click(function(){
	var param = formToObject("chooseTradecategoryQueryForm");
	doPost(selectTradecontextPath,param,function(result){
		if(result){
			var obj = parseObj(result);
			if(obj.error){
				error(obj.error);
				return;
			}
			$('#chooseTradecategoryDataGrid').treegrid("loadData",obj);
		} 
		
	});			
});

</script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">	
		
	<x:button id="choose_tradecategory_btn_select" icon="icon-yes" click="" text="choose"/>
	<x:button id="choose_tradecategory_btn_cancel" icon="icon-no" click=""  text="cancel"/>
		
</tiles:putAttribute>
<tiles:putAttribute name="query">
	<form id="chooseTradecategoryQueryForm" class="query_form">
	     
		<table>
			<tr>
				<td class="title"><s:text name="trade.tradeName"/>:</td>
				<td><input name="trade.tradeNameCn"></input></td>
				<td><x:button id="choose_tradecategory_btn_query" icon="icon-search" click="" text="query"/></td>
			</tr>
		</table>
	</form>	
</tiles:putAttribute>
<tiles:putAttribute name="data">
  
	<table id="chooseTradecategoryDataGrid" style="height:300px;"></table>

</tiles:putAttribute>
</tiles:insertDefinition>
