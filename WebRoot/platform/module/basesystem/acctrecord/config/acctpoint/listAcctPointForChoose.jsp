<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_BLANK">
<tiles:putAttribute name="body">	
		<x:datagrid id="dataTableForAcctPointChoose" singleSelect="true" url="/acctConfig/acctPointAction_queryData.jhtml" autoload="false">
			<x:columns>
				<x:column field="id" checkbox="true"/>
				<x:column field="prodNo" title="AcctPoint.prodNo" width="160"/>
				<x:column field="prodNo_prodName" title="AcctPoint.prodName" width="160"/>
				<x:column field="eventNo" title="AcctPoint.eventNo" width="160"/>
				<x:column field="eventName" title="AcctPoint.eventName" width="160" formatter="eventName_column"/>
			</x:columns>
		</x:datagrid>
</tiles:putAttribute>

<tiles:putAttribute name="end">
<script type="text/javascript">

var keys=["B023"];
var code=new CodeUtil(keys);

function initPage(){				
   code.loadData();
   dataTableForAcctPointChoose.load();//数据分页标签加载远程数据
   	//双击行事件的使用：
   dataTableForAcctPointChoose.onDblClickRow=function(rowIndex, rowData){
   		$('#${param.AcctPointIdInputor}').val(rowData.id);
   		$('#${param.AcctPointNameInputor}').val(rowData.prodNo_prodName+code.getValue('B023',rowData.eventNo));
   		$('#${param.ShowAcctPointDiv}').window('close');
   	}

}

function eventName_column(value,field,row,index){
	return "<span>"+code.getValue('B023',row.eventNo)+"</span>";
}
	
</script>
</tiles:putAttribute>
</tiles:insertDefinition>


