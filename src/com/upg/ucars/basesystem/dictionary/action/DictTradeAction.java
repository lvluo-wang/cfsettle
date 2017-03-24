/*
 * 源程序名称: DictTradeAction.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: UPG业务服务平台(UBSP)
 * 模块名称：数据字典-行业
 * 
 */

package com.upg.ucars.basesystem.dictionary.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.upg.ucars.basesystem.dictionary.core.code.IDictTradeService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.dictionary.DictTrade;


public class DictTradeAction extends BaseAction{
	/**显示样式*/
	//private String displayType = "1";
	
	private InputStream jsonStream;
	
	private DictTrade trade;
	/**行业类别服务*/
	private IDictTradeService dictTradeService;
	
	


	public DictTrade getTrade() {
		return trade;
	}


	public void setTrade(DictTrade trade) {
		this.trade = trade;
	}


	public void setDictTradeService(IDictTradeService dictTradeService) {
		this.dictTradeService = dictTradeService;
	}


	public InputStream getJsonStream() {
		return jsonStream;
	}


	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}


	/**跳转到选择行业页面
	 * @return
	 */
	public String toSelectTrade() {
		
		return "toSelectTrade";
	}


	/**显示行业类别列表信息
	 * @return
	 */
	public String viewListTrade() {
		//UserLogonInfo uli = SessionTool.getUserLogonInfo();
//		if (trade == null) {	
//			trade = new DictTrade();
//		} 
		
		String jsons = "";
	
		jsons = dictTradeService.createTree(trade, this.getPg(), "");
				

		try {
			jsonStream = new ByteArrayInputStream(jsons.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		
		return "table";
	}
	
}
