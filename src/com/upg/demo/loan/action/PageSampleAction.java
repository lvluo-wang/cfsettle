package com.upg.demo.loan.action;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.upg.ucars.basesystem.dictionary.core.code.IRegionService;
import com.upg.ucars.basesystem.dictionary.model.RegionModel;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.util.SourceTemplate;

public class PageSampleAction extends BaseAction{
	
	//字典
	private List<Code> codeDegreeList;//学位
	private String codeDegree;
	
	private List<Code> codeEnableList;//有效
	private String codeEnable;
	
	
	//选择页面
	private String idDiv;
	private String noDiv;
	private String nameDiv;
	private String winId;
	private String treeJSONInfo;
	private InputStream jsonStream;	
	
	private List<RegionModel> countryList;
	
	
	public String pageSample(){
		codeDegree = "4";
		this.codeDegreeList  = DictionaryUtil.getCodesByKey(CodeKey.DEGREE);
		
		this.codeEnableList  = DictionaryUtil.getCodesByKey(CodeKey.ENABLE);
		Code emptyCode = new Code();emptyCode.setCodeNo("");emptyCode.setCodeName(this.getText("select_choose"));//"--"+
		this.codeEnableList.add(0,  emptyCode);
		codeEnable="";
		
		
		
		return "PageCell";
	}
	
	
	public String toChooseCityTree(){
		
		IRegionService regionService  = SourceTemplate.getBean(IRegionService.class,BeanNameConstants.REGION_SERVICE);
		//国字
		RegionModel china = new RegionModel();china.setCodeNo("CN");china.setRegionName("中国");
		JQueryTreeNode countryNode = new JQueryTreeNode();
		countryNode.setId(china.getCodeNo());countryNode.setText(china.getRegionName());
		
		
		List<RegionModel> provinceList = regionService.getProvinces(countryNode.getId());
		for (RegionModel province : provinceList) {//省
			JQueryTreeNode provinceNode = new JQueryTreeNode();
			provinceNode.setId(province.getCodeNo());
			provinceNode.setText(province.getRegionName());
			provinceNode.setState(JQueryTreeNode.STATE_CLOSED);//
			countryNode.addChild(provinceNode);
			
			List<RegionModel> cityList = regionService.getCitys(province.getCodeNo());
			for (RegionModel city : cityList) {//城市
				JQueryTreeNode cityNode = new JQueryTreeNode();
				cityNode.setId(city.getCodeNo());
				cityNode.setText(city.getRegionName());
				provinceNode.addChild(cityNode);
			}
			
		}
		
		JSONArray bejson=JSONArray.fromObject(countryNode);
		//JSONObject bejson = JSONObject.fromObject(countryNode);

		this.setTreeJSONInfo(bejson.toString());
		
		return "CityTree";
	}
	
	public String toChooseCountryList(){
		
		return "CountryList";
	}	
	
	public String queryChooseCountryList(){
		
		IRegionService regionService  = SourceTemplate.getBean(IRegionService.class,BeanNameConstants.REGION_SERVICE);
		
		this.countryList = regionService.getCountrys();
		
		this.countryList = super.getSubListByPage(this.countryList, this.getPg());//分页
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",this.getPg().getTotalRows());
		jsonMap.put("rows",countryList);
		JSONObject   jsonObject = JSONObject.fromObject(jsonMap);		
		jsonStream = outJsonUTFStream(jsonObject);

		return "table";
	}
	
	public String toChooseMultiCountryList(){
		
		return "MultiCountryList";
	}	
	
	public String queryChooseMultiCountryList(){
		
		IRegionService regionService  = SourceTemplate.getBean(IRegionService.class,BeanNameConstants.REGION_SERVICE);
		
		this.countryList = regionService.getCountrys();
		
		this.countryList = super.getSubListByPage(this.countryList, this.getPg());//分页
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",this.getPg().getTotalRows());
		jsonMap.put("rows",countryList);
		JSONObject   jsonObject = JSONObject.fromObject(jsonMap);		
		jsonStream = outJsonUTFStream(jsonObject);

		return "table";
	}
	
	public String toChooseBookTree(){
		
		
		JQueryTreeNode bookNode = new JQueryTreeNode();
		bookNode.setId("01");bookNode.setText("书籍");
		{
			JQueryTreeNode secondNode = new JQueryTreeNode();
			secondNode.setId("0101");
			secondNode.setText("人文");
			secondNode.setState(JQueryTreeNode.STATE_CLOSED);//
			bookNode.addChild(secondNode);
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010101");
				thirdNode.setText("《孔子》");
				secondNode.addChild(thirdNode);
			}
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010102");
				thirdNode.setText("《诗经》");
				secondNode.addChild(thirdNode);
			}
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010103");
				thirdNode.setText("《唐诗》");
				secondNode.addChild(thirdNode);
			}
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010104");
				thirdNode.setText("《宋词》");
				secondNode.addChild(thirdNode);
			}
		}
		
		{
			JQueryTreeNode secondNode = new JQueryTreeNode();
			secondNode.setId("0102");
			secondNode.setText("历史");
			secondNode.setState(JQueryTreeNode.STATE_CLOSED);//
			bookNode.addChild(secondNode);
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010201");
				thirdNode.setText("《春秋战国》");
				secondNode.addChild(thirdNode);
			}
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010202");
				thirdNode.setText("《三国》");
				secondNode.addChild(thirdNode);
			}
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010203");
				thirdNode.setText("《隋唐》");
				secondNode.addChild(thirdNode);
			}
		}
		
		{
			JQueryTreeNode secondNode = new JQueryTreeNode();
			secondNode.setId("0103");
			secondNode.setText(" 小说");
			secondNode.setState(JQueryTreeNode.STATE_CLOSED);//
			bookNode.addChild(secondNode);
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010301");
				thirdNode.setText("《红楼梦》");
				secondNode.addChild(thirdNode);
			}
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010302");
				thirdNode.setText("《三国演义》");
				secondNode.addChild(thirdNode);
			}
		}
		
		{
			JQueryTreeNode secondNode = new JQueryTreeNode();
			secondNode.setId("0104");
			secondNode.setText("经济");
			secondNode.setState(JQueryTreeNode.STATE_CLOSED);//
			bookNode.addChild(secondNode);
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010401");
				thirdNode.setText("《理财》");
				secondNode.addChild(thirdNode);
			}
		}
		
		{
			JQueryTreeNode secondNode = new JQueryTreeNode();
			secondNode.setId("0105");
			secondNode.setText("生活");
			secondNode.setState(JQueryTreeNode.STATE_CLOSED);//
			bookNode.addChild(secondNode);
			{
				JQueryTreeNode thirdNode = new JQueryTreeNode();
				thirdNode.setId("010501");
				thirdNode.setText("《装修必知》");
				secondNode.addChild(thirdNode);
			}
		}
		
		JSONArray bejson=JSONArray.fromObject(bookNode);
		//JSONObject bejson = JSONObject.fromObject(countryNode);

		this.setTreeJSONInfo(bejson.toString());
		
		return "BookTree";
	}	


	public List<Code> getCodeDegreeList() {
		return codeDegreeList;
	}
	public void setCodeDegreeList(List<Code> codeDegreeList) {
		this.codeDegreeList = codeDegreeList;
	}	
	public String getCodeDegree() {
		return codeDegree;
	}
	public void setCodeDegree(String codeDegree) {
		this.codeDegree = codeDegree;
	}
	public List<Code> getCodeEnableList() {
		return codeEnableList;
	}
	public void setCodeEnableList(List<Code> codeEnableList) {
		this.codeEnableList = codeEnableList;
	}
	public String getCodeEnable() {
		return codeEnable;
	}
	public void setCodeEnable(String codeEnable) {
		this.codeEnable = codeEnable;
	}
	public String getIdDiv() {
		return idDiv;
	}
	public void setIdDiv(String idDiv) {
		this.idDiv = idDiv;
	}
	public String getNoDiv() {
		return noDiv;
	}
	public void setNoDiv(String noDiv) {
		this.noDiv = noDiv;
	}
	public String getNameDiv() {
		return nameDiv;
	}
	public void setNameDiv(String nameDiv) {
		this.nameDiv = nameDiv;
	}

	public String getWinId() {
		return winId;
	}
	public void setWinId(String winId) {
		this.winId = winId;
	}


	public String getTreeJSONInfo() {
		return treeJSONInfo;
	}


	public void setTreeJSONInfo(String treeJSONInfo) {
		this.treeJSONInfo = treeJSONInfo;
	}


	public InputStream getJsonStream() {
		return jsonStream;
	}


	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}


	public List<RegionModel> getCountryList() {
		return countryList;
	}


	public void setCountryList(List<RegionModel> countryList) {
		this.countryList = countryList;
	}	

}
