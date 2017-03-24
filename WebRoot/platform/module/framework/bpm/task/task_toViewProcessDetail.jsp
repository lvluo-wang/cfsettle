<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="com.upg.ubsp.constant.ComDictKeyConst"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-back" text="back" click="doBack"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table id="task_mycount">
				<tr>
					<td class="title">项目编号:</td>
					<td>
						<input name="searchBean.col9" value="${searchBean.col9}"/> 
					</td>
					<td class="title">客户名称:</td>
					<td>
						<input name="searchBean.col1" value="${searchBean.col1}"/> 
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
					
				</tr>
			</table>			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<input type="hidden" id = "roleId" value="${roleId}"/>
		<input type="hidden" id = "countType" value="${countType}"/>
		<x:datagrid id="dataTable" url="/bpm/task_getProcessDetailList.jhtml" params="{'roleId':'${roleId}','countType':'${countType}'}" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id"/>
				<x:column title="项目编号"  field="COL9" width="100" align="left" formatter="formatViewUrl"/>
				<x:column title="客户名称"  field="COL1" width="200" align="left" />
				<x:column title="流程类型"  field="PROCESS_CN_NAME" width="120" align="left"/>
				<x:column title="当前节点"  field="DESCRIPTION_" width="200" align="left"/>
				<x:column title="处理人"  field="ACTOR_NAME" width="50" align="left"/>
				<x:column title="提交时间"  field="CREATE_TIME" width="100" formatter="format2Minute"/>
				<x:column title="上一节点时间"  field="CREATE_" width="100"  formatter="format2Minute"/>
			</x:columns>
		</x:datagrid>
		<div id="archive_view_win" style="width:860px;height:400px;display:none;"></div>
		<div id="lend_view" style="width:800px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		function doQuery(){
			dataTable.load();
		}
		function doBack(){
			window.history.go(-1);
		}
		function formatViewUrl(value,field,row){
			var projectNo = row.COL9; 
			result = "";
			if(typeof(projectNo)!="undefined"&&projectNo!=null&&projectNo != ""){
				var prodNo = row.PROD_NO;
				var projectId = row.PROJECT_ID;
				var result = projectNo;
				if(row.isGuaranteeBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToGuranProject(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isCashBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToCashProject(' + projectId + ',\''+ prodNo +'\')">' + projectNo + '</a>';
				}else if(row.isEquityInvestBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToEquityProject(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isPrbBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToPrbProject(' + projectId + ',\''+ prodNo +'\')">' + projectNo + '</a>';	
				}else if(row.isInvestmentBankBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToInvestProject(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isMembersFeeBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToMembersFee(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isArchiveBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToArchive(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isExtractBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToExtract(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isLendBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToLend(' + projectId + ')">' + projectNo + '</a>';
				}else if(row.isPreserveBusiness){
					result = '<a class="viewReportInfo" href="#" onclick="hrefToPreserve(' + projectId + ')">' + projectNo + '</a>';
				}
			} 
			return result;
		}
		
		function hrefToGuranProject(projectId){
			var url="<s:url value='/preGuarantee/project_toView.jhtml' />?id="+projectId;
			redirectUrl(url);
		}
		function hrefToCashProject(projectId, prodNo){
			var url = "";
			if(prodNo == 'C016' || prodNo == 'C018'){
				url="<s:url value='/cash/cashPG_toView.jhtml' />?id="+projectId;
			}else{
				url="<s:url value='/cash/cash_toView.jhtml' />?id="+projectId;	
			}
			redirectUrl(url);
		}
		function hrefToEquityProject(projectId){
			var url="<s:url value='/equityInvestment/equityInvestment_toView.jhtml' />?id="+projectId;
			redirectUrl(url);
		}
		function hrefToPrbProject(projectId, prodNo){
			var url = "";
			url = "<s:url value='/prb/prb_toView.jhtml'/>?project.id="+projectId;	
			redirectUrl(url);
		}
		function hrefToInvestProject(projectId){
			var url="<s:url value='/investmentBank/investmentBank_toView.jhtml'/>?id="+projectId;
			redirectUrl(url);
		}
		function hrefToMembersFee(projectId){
			var url="<s:url value='/membersFee/membersFee_toView.jhtml'/>?id="+projectId;
			redirectUrl(url);
		}
		function hrefToArchive(projectId){
			var url="<s:url value='/archive/archive_toView.jhtml'/>?id="+projectId;
			requestAtWindow(url,"archive_view_win","<s:text name='view'/>",null,{relDocTop:10});
		}
		function hrefToExtract(projectId){
			var url="<s:url value='/archive/extract_toView.jhtml'/>?id="+projectId;
			requestAtWindow(url,"archive_view_win","<s:text name='view'/>",null,{relDocTop:10});
		}
		function hrefToLend(projectId){
			var url="<s:url value='/archive/lend_toView.jhtml'/>?id="+projectId;
			requestAtWindow(url,"lend_view","<s:text name='view'/>",null,{relDocTop:10});
		}
		function hrefToPreserve(projectId){
			var url="<s:url value='/preserve/preserve_toView.jhtml'/>?id="+projectId+"&target=preserve";
			redirectUrl(url);
		}
		$(function(){
			dataTable.onDblClickRow=function(index,row){
	   			var projectNo = row.COL9; 
				if(typeof(projectNo)!="undefined"&&projectNo!=null&&projectNo != ""){
					var prodNo = row.PROD_NO;
					var projectId = row.PROJECT_ID;
					if(row.isGuaranteeBusiness){
						hrefToGuranProject(projectId);
					}else if(row.isCashBusiness){
						hrefToCashProject(projectId, ''+ prodNo +'');
					}else if(row.isEquityInvestBusiness){
						hrefToEquityProject(projectId);
					}else if(row.isPrbBusiness){
						hrefToPrbProject(projectId);
					}else if(row.isInvestmentBankBusiness){
						hrefToInvestProject(projectId);
					}else if(row.isMembersFeeBusiness){
						hrefToMembersFee(projectId);
					}else if(row.isArchiveBusiness){
						hrefToArchive(projectId);
					}else if(row.isExtractBusiness){
						hrefToExtract(projectId);
					}else if(row.isPreserveBusiness){
						hrefToPreserve(projectId);
					}else if(row.isLendBusiness){
						hrefToLend(projectId);
					}
	   		}}
			$.xcarsParser.parse($("#task_mycount"));
		});
		$(".viewReportInfo").click(function() {
			var url = $(this).attr("href");
			url += "&backUrl=<s:url value="/bpm/task_toViewProcessDetail.jhtml"/>?roleId=${roleId}&countType=${countType}";
			redirectUrl(url);
			return false;
		});
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
