<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
	<tiles:putAttribute name="body">
		<div class="win_form_area" style="overflow:auto">
			<form class="busi_form">
			<table>
						<colgroup>
							<col width="23%"/>
							<col width="27%"/>
							<col width="23%"/>
							<col width="27%"/>
						</colgroup>
						<tbody>
							<tr>
								<td class="title">选择字典元:</td>
								<td>
										<input id="codeMeta_name"  class="inputSel" onClick="chooseCodeMeta()"/>
										<s:hidden id="codeMeta_key" name="codeMetaKey"  />
								</td>
								<td class="title">审批路线选择:</td>
								
								<td>
										<input id="auditRoute_auditRouteName"  class="inputSel" onClick="chooseAuditRoute()"/>
										<s:hidden id="auditRoute_id" name="auditRouteId" />
								</td>
							</tr>
							<tr>
								<td class="title">选择机构列表:</td>
								
								<td>
									
										<input id="branch_brchName"  class="inputSel" onClick="chooseBranch()"/>
										<s:hidden name="brchId" id="branch_brchId" />
										<s:hidden name="brchNo" id="branch_brchNo"/>
										<s:hidden name="unionBankNoId" id="branch_unionBrankNo" />
									
								</td>
								<td class="title">选择机构树:</td>
									<script>
										function chooseTreeBranchCallback(row){
										    
											if(row){
												$("#treeBranch_brchName").val(row.brchName);
												$("#treeBranch_brchId").val(row.brchId);
											}
										}
									</script>
								<td>
								
								        <input id="treeBranch_brchName" class="inputSel" onClick="chooseTreeBranch(chooseTreeBranchCallback)"/>
										<s:hidden name="treeBranch_brchId" id="treeBranch_brchId" />
										
									<!-- 
									<input id="brachTree" name="branchId" class="easyui-combotree" url='<s:url value="/security/brch_getBranchTree.jhtml"/>' style="width:130px;" />
									 -->	
								</td>
							</tr>
								
										
											
							
						</tbody>
					</table>
			</form>
			
			
			<div id="tt" class="easyui-tabs" >  
				<div title="客户基本情况  ">  
					
					<div id="tab1" class="easyui-tabs"  plain="true" style="height:300px">
						<div title="企业基本信息" style="padding:10px;">Content 1</div>
						<div title="股权结构" style="padding:10px;">Content 2</div>
						<div title="管理者简历" style="padding:10px;">Content 3</div>
						<div title="实际控制人和主要经营者的基本评价" style="padding:10px;">Content 3</div>
						
					</div>
					<div class="win_button_area">
				        <x:button id="btn_add_sysfunc" click="doSave" text="save" effect="round" icon="icon-save"/>
			            <x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
		            </div>
				</div>  
				<div title="企业经营分析" >  
					  <div id="tab2" class="easyui-tabs"  plain="true" style="height:300px">
						<div title="基本信息" style="padding:10px;">Content 1</div>
						<div title="市场销售结构和渠道" style="padding:10px;">Content 2</div>
						<div title="主要上游客户及结算方式" style="padding:10px;">Content 3</div>
						<div title="主要下游客户及结算方式" style="padding:10px;">Content 3</div>
						
					</div>
				</div>  
				<div title="财务情况分析" >  
					 <div id="tab3" class="easyui-tabs"  plain="true" style="height:300px">
						<div title="企业基本信息" style="padding:10px;">Content 1</div>
						<div title="股权结构" style="padding:10px;">Content 2</div>
						<div title="管理者简历" style="padding:10px;">Content 3</div>
						<div title="实际控制人和主要经营者的基本评价" style="padding:10px;">Content 3</div>
						
					</div>
				</div>  
				<div title="还款来源分析" >  
					 <div id="tab4" class="easyui-tabs"  plain="true" style="height:300px">
						<div title="企业基本信息" style="padding:10px;">Content 1</div>
						<div title="股权结构" style="padding:10px;">Content 2</div>
						<div title="管理者简历" style="padding:10px;">Content 3</div>
						<div title="实际控制人和主要经营者的基本评价" style="padding:10px;">Content 3</div>
						
					</div>
				</div>  
				<div title="重大事项调查" >  
					 <div id="tab5" class="easyui-tabs"  plain="true" style="height:300px">
						<div title="企业基本信息" style="padding:10px;">Content 1</div>
						<div title="股权结构" style="padding:10px;">Content 2</div>
						<div title="管理者简历" style="padding:10px;">Content 3</div>
						<div title="实际控制人和主要经营者的基本评价" style="padding:10px;">Content 3</div>
						
					</div>
				</div>  
				<div title="主要风险与控制措施" >  
					 <div id="tab6" class="easyui-tabs"  plain="true" style="height:300px">
						<div title="企业基本信息" style="padding:10px;">Content 1</div>
						<div title="股权结构" style="padding:10px;">Content 2</div>
						<div title="管理者简历" style="padding:10px;">Content 3</div>
						<div title="实际控制人和主要经营者的基本评价" style="padding:10px;">Content 3</div>
						
					</div>
				</div>  
			</div>  
			
			
		</div>
		
			
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">	
	
	$(function(){
		$('#tt').tabs({  
			border:true
		}); 
		
		$('#tab1').tabs({  
			border:false
		}); 
		$('#tab2').tabs({  
			border:false
		}); 
		$('#tab3').tabs({  
			border:false
		}); 
		$('#tab4').tabs({  
			border:false
		}); 
		$('#tab5').tabs({  
			border:false
		}); 
		$('#tab6').tabs({  
			border:false
		}); 
	});
	
	
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

	