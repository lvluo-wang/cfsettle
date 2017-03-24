/*
 * 源程序名称: DictTradeServiceImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: UPG业务服务平台(UBSP)
 * 模块名称：数据字典-行业
 * 
 */

package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.DictTrade;


@Service
public class DictTradeServiceImpl implements IDictTradeService{
	
    @Autowired
	private IDictTradeDAO dictTradeDAO;
	



	public String createTree(DictTrade ttc, Page page, String... args) throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Long rootParentId = null;
		//所有行业类别
//		if (ttc != null) {
//			//根据名字模糊查询只能查询根级别
//			if ((ttc.getTradeNameCn() == null || ttc.getTradeNameCn().trim().equals("")) && ttc.getPid() == null) {
//				ttc.setPid(rootParentId);
//			} 
//		}else {
//			ttc = new DictTrade();
//			ttc.setPid(rootParentId);
//		}

		List<DictTrade> twiTrades = new ArrayList<DictTrade>();
		if (ttc != null && ttc.getPid() != null) {
			twiTrades = dictTradeDAO.findTradeByParentid(ttc.getPid());
		} else {
			twiTrades = dictTradeDAO.queryTrade(ttc, null);
		}
		//有按行业名称查询就查找当前节点最根级点
		if (ttc != null && ttc.getTradeNameCn() != null && !ttc.getTradeNameCn().equals("")) {
			Set<DictTrade> tt = new HashSet<DictTrade>();
			for (DictTrade t : twiTrades) {
				
				if (t.getPid()==null) {
					tt.add(t);
				} else {
					tt.add(this.findRootTradeById(t.getPid()));
				}
			}
			twiTrades = new ArrayList<DictTrade>();
			for (DictTrade t : tt) {
				twiTrades.add(t);
			}
		}

		for (DictTrade trade : twiTrades) {
			
			HashMap<String, Object> tradeMap = new HashMap<String, Object>();
			tradeMap.put("id", trade.getId());
			tradeMap.put("tradeName", trade.getTradeNameCn());
			tradeMap.put("tradeNo", trade.getCode());
			
			tradeMap.put("tradeDesc", trade.getTradeDesc());
			tradeMap.put("total", page.getTotalRows());
			tradeMap.put("rows",twiTrades);
			// 检查节点下是否存在子节点
			List<DictTrade> tcs = dictTradeDAO.findTradeByParentid(trade.getId());
			if (tcs != null && tcs.size() > 0) {
				tradeMap.put("state", "closed");
			}
			
			list.add(tradeMap);
		}
		JSONArray treeJson = JSONArray.fromObject(list);
		
		return treeJson.toString();
	}
	
	
	
	
	public DictTrade findRootTradeById(Long id) throws ServiceException {
		
		DictTrade t = null;
		DictTrade tc = dictTradeDAO.get(id);
		if (tc != null) {
			if (tc.getPid()==null) {
				t = tc;
			} else{
				t = findRootTradeById(tc.getPid());
			}
		}
		
		return t;
	}
	
	public DictTrade findTradeByCode(String code) throws ServiceException {
		DictTrade tc = dictTradeDAO.findTradeByCode(code);
		return tc;
	}

	@Override
	public DictTrade getDictTradeById(Long id) {
		return dictTradeDAO.get(id);
	}
	
}
