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
									<!--
										<input id="auditRoute_auditRouteName"  class="inputSel" onClick="chooseAuditRoute()"/>
										<s:hidden id="auditRoute_id" name="auditRouteId" />
										-->
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
							<tr>
								<td class="title">选择接入点:</td>
								<td>
									
										<input id="memberInfo_miName"  class="inputSel" onClick="chooseMember()"/>
										<!--
										<img src='<s:url value="/platform/static/images/icon_clear.PNG"/>' style="cursor:hand" onclick="clearComponentValue('memberName:memberNo')">
										-->
										<s:hidden name="bindMemberNo" id="memberInfo_miNo" />
								</td>
								
								<td class="title">选择用户:</td>
								<td>
									     <s:hidden name="userId" id="user_userId" />
										<input id="user_userName"  name="userName" class="inputSel" onClick="chooseBrchUser()"/>
										
								</td>
							</tr>
							<tr>
								<td class="title">选择产品:</td>
								  
								<td>
									
										<input id="productInfo_prodName" class="inputSel" onClick="chooseProduct()"/>
										<s:hidden name="productInfo_id" id="productInfo_id" />
								</td>
								<td class="title">选择业务产品:</td>
								<td>
									<input id="prodName2" class="inputSel" onClick="chooseBusiProduct(chooseBusiProductCallback)"/>
									<s:hidden  id="prodId2" />
								</td>
								<script>
									function chooseBusiProductCallback(row){
										if(row&&row.id&&row.prodName){
											$("#prodName2").val(row.prodName);
											$("#prodId2").val(row.id);
										}
									}
								</script>
							</tr>
							<tr>
								
								<td class="title">属性产品:</td>
								
								<td>
									<input id="CHOOSE_PRODUCT_NAME" class="inputSel" onClick="chooseAttrProduct(chooseAttrProductCallback,'attr1',1)"/>
									<s:hidden name="chooseProductId" id="CHOOSE_PRODUCT_ID" />
								</td>
								<script>
									function chooseAttrProductCallback(row){
										if(row&&row.id&&row.prodName){
											$("#CHOOSE_PRODUCT_NAME").val(row.prodName);
											$("#CHOOSE_PRODUCT_ID").val(row.id);
										}
									}
								</script>
								<td class="title">选择 主业务产品:</td>
								<td>
									<input id="prodName3" class="inputSel" onClick="chooseMainBusiProduct(chooseMainBusiProductCallback)"/>
									<s:hidden  id="prodId3" />
								</td>
								<script>
									function chooseMainBusiProductCallback(row){
										if(row&&row.id&&row.prodName){
											$("#prodName3").val(row.prodName);
											$("#prodId3").val(row.id);
										}
									}
								</script>
							</tr>
							<tr>
								<td class="title">字典选择_学位:</td>
								<td>
								<x:combobox  list="codeDegreeList" value="${codeDegree}" textField="codeName" valueField="codeNo"/>

								</td>
							
								<td class="title">字典选择_有效性:</td>
								<td>
								<x:combobox  list="codeEnableList" value="${codeEnable}" textField="codeName" valueField="codeNo"/>
								</td>
							</tr>
							
							
	
							<tr>
							
								<td class="title" >列表选择-省份:</td>
								<td >
									<x:combobox id="province_id" name="province" onchange="changeProvince"  required="true" valueField="id" textField="nameCn"/>
									
								</td>
								
								<td class="title" >列表选择-城市:</td>
								<td >
									<x:combobox id="city_id" name="city" onchange="changeCity"  required="true" valueField="id" textField="nameCn"/>
									
								</td>
								</td>
								
							</tr>
							
							<script type="text/javascript">	
								
								$("#province_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getProvinces.jhtml"/>'});
								//编辑的时候加下面两条语句
								$("#city_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid=${provinceId}'});
								$("#area_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid=${cityId}'});
								
								function changeProvince(provinceId){
									if(provinceId){
										$("#city_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid='+provinceId});
									   var cityId = $("#area_id").xcombobox("getValue");
									 
									   if(!cityId)  cityId =-1;
									   $("#area_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid='+cityId});
									}
									
								}
								function changeCity(cityId){
									if(cityId){
										$("#area_id").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid='+cityId});
									}
								}
							</script>	
	
							<tr>
								<td class="title" >列表选择-区:</td>
								<td >
									<x:combobox id="area_id" name="area" required="true" valueField="id" textField="nameCn"/>
								</td>
								
								<td class="title" >树状选择-行业:</td>
								<td >
									<input id="trade_name" name = "tradeName" class="inputSel" onClick="chooseTrade()"/>
									<s:hidden name="trade_id" id="trade_id" />
										
									
								</td>
							
							</tr>
							
							
							<tr>
								<td class="title">选择客户:</td>
								  
								<td>
									
										<input id="custInfo_custName" name="custInfo.custName" class="inputSel" readonly="readonly" onClick="chooseCustinfo()"/>
										<input type="hidden" id="custInfo_id" name="custInfo.id"/>
								</td>
								<td class="title">选择合作方:</td>
								<td>
									<input id="partner_name" name="partner.name" class="inputSel" onClick="choosePartner()"/>
									<input type="hidden" id="partner_id" name="partner.id"/>
								</td>
								
							</tr>
							
							
							<tr>

								<td class="title" >长度:</td>
								<td >
									<input type="text" id="testDate" class="easyui-validatebox" maxlength="20" required="true" validType="textLength['0','3']" name="testLength"/ >
																								
								</td>
								<td class="title" >金额格式化:</td>
								<td >
									<input type="text" id="testDate" class="easyui-validatebox" maxlength="20" required="true" validType="moneyValidator" onchange="this.value=formatMoney(this.value)" name="testFormatMoney"/ >
																								
								</td>
								
							</tr>
							
							
							<tr>

								<td class="title" >自动编码1:</td>
								<td colspan="3">
									<input type="text" name ="testCode" id="testCode_id" class="easyui-validatebox" required="true" readonly="true"  style="width:130px"></input>
								    <s:hidden id="codeKey" value="%{@com.upg.ubsp.constant.AutoGenCodeConst@TEST1}"/>
									<a href="#" class="easyui-linkbutton" style="height:22px" plain="true" iconCls="icon-code" onClick="genCode('codeKey','testCode_id',this);">生成</a>	<font color=red> *</font>														
								</td>
								
							</tr>
							
						
							
							<tr>
								<td class="title" >利率类型</td>
								<td >
										<input  type="text" id="rateValue" class="easyui-validatebox" maxlength="20" required="true" name="testLength"/ >
										<span id="tagNo"></span>																
								</td>
								<td class="title" >
								    <input type="hidden" id="oldRateType"/>
								    
								    
									<select id="rateType" class="short_select" name="codeEnable" onChange="changeRateType('rateValue');">
									 		<option value="1" selected="selected">日</option>
									 		<option value="30">月</option>
									 		<option value="360">年</option>

									</select>
									
                                </td>
								<td >																						
								</td>								
							</tr>		
										
											
							
						</tbody>
					</table>
			</form>
			
			
			<div id="tt" class="easyui-tabs" style="width:500px;height:250px;">  
				<div title="Tab1" >  
					tab1  
				</div>  
				<div title="Tab2" >  
					tab2  
				</div>  
				<div title="Tab3" >  
					tab3  
				</div>  
			</div>  
			
			
		</div>
		<div class="win_button_area">

					<x:button icon="icon-add" text="新增"/>
					<x:button icon="icon-edit" text="编辑"/>
					<x:button icon="icon-import" text="导入"/>
					<x:button icon="icon-copy" text="复制录入"/>
					<x:button icon="icon-save" text="保存"/>
					<x:button icon="icon-remove" text="删除"/>
					<x:button icon="icon-cut" text="剪贴"/>
					<x:button icon="icon-yes" text="有效"/>
					<x:button icon="icon-no" text="无效"/>
					<x:button icon="icon-cancel" text="取消"/>
					<x:button icon="icon-reload" text="刷新"/>
					<x:button icon="icon-view" text="浏览"/>
					<x:button icon="icon-search" text="查找"/>
	                <x:button icon="icon-manage" text="明细维护"/>
					<x:button icon="icon-submit" text="提交"/>
					<x:button icon="icon-accept" text="受理"/>
					<x:button icon="icon-audit" text="审批"/>
					<x:button icon="icon-revoke" text="撤销"/>
					<x:button icon="icon-send" text="发送"/>
					<x:button icon="icon-back" text="返回"/>
		</div>
			
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">	
	
	$(function(){
		$('#tt').tabs({  
			border:true
		}); 
	});
	
	//-----多选select操作功能selComp=$('#id')
	function selectedAll(selComp){//选中所有项
		var size = selComp.get(0).options.length;
		for (var i=0; i<size; i++){
			selComp.get(0).options[i].selected=true ;
		}
		
	}
	function isExistItem(selComp, value){
		var size = selComp.get(0).options.length;
		for (var i=0; i<size; i++){				
			if (selComp.get(0).options[i].value == value)
				return true;
		}
		return false;
	}
	function getAllOptionValue(selComp){//得到所有项的值，包含未选中的
		var size = selComp.get(0).options.length;
		var v="";
		for (var i=0; i<size; i++){
			v=v+selComp.get(0).options[i].value+",";
		}
		return v;
		
	}
	
	function addOption(selComp,value,text){//增加项
		//$("<option value='112' selected='selected'>1111</option>").appendTo("#m1");
		if (!isExistItem(selComp, value))
			selComp.get(0).options.add(new Option(text,value,null,true)); 
	}
	
	function removeSelectedOption(selComp){//删除选中的项
		//alert($('#m1').get(0).selectedIndex); 
		while(selComp.get(0).selectedIndex>=0){
			selComp[0].remove(selComp.get(0).selectedIndex);
		}	
	}
	
	function emptySelect(selComp){//删除选中的项
		selComp.empty();
	}

	

	$(function(){
		var arr = [{"v":"A","t":"a"},{"v":"B","t":"b"},{"v":"C","t":"c"}];
		$('#cc').combobox({
			multiple:true,
			hasDownArrow:true,
			separator:',',
			data:arr,
			valueField:"v",
			textField:"t"

		});		
		$('#cc').combobox('setValues',['A','B']);
		autoHeightFit($(".win_form_area").parent(),".win_button_area",".win_form_area");
	});
	
	function chooseMultiCountry(){
		var url = "<s:url value='/demo/page_toChooseMultiCountryList.jhtml'/>";
		url=url+'?idDiv=m1&winId=countryMultiListWindow';
		requestAtWindow(url,"countryMultiListWindow","国家多选");
	
	}
	
	function chooseMultiBook(){
		var url = "<s:url value='/demo/page_toChooseBookTree.jhtml'/>";
		url=url+'?idDiv=book&winId=bookTreeWindow';
		 requestAtWindow(url,"bookTreeWindow","选择城市");
	
	}
	
	
	
	// 新建利率转换对象
	var rate = new RateTransfer('rateType','oldRateType','tagNo');
	//rate.initRate('rateType','oldRateType','tagNo');
	
	// 利率类型变换触发的函数
	function changeRateType(rateValueId)
	{
	   rate.changeRateType(rateValueId);
	   rate.changeRateTagno();
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

	