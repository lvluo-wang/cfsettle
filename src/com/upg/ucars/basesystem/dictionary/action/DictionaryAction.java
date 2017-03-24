package com.upg.ucars.basesystem.dictionary.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.DictArea;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * DictionaryAction
 * 
 * @author shentuwy
 * 
 */
public class DictionaryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -9139979612774399910L;

	/** 多个key的字符串 */
	private String				keys;

	/** <code>CODE</code> */
	private Code				code;

	/** 代码服务 */
	private ICodeService		codeService;

	private Long areaPid;
	private String areaCode;
	/**
	 * 
	 * 查询数据字典数据
	 * 
	 * @return 字典的json数据
	 */
	public String findValues() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (keys != null && keys.length() > 0) {
			String key[] = keys.split(COMMA);
			for (String k : key) {
				String[] v = k.split(COLON);
				List<Code> codes = null;
				if (v.length == 2) {
					codes = DictionaryUtil.getCodesByField(v[0], v[1]);
				} else {
					// 说明传递过来的是codeKey
					codes = DictionaryUtil.getCodesByKey(k);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", k);
				map.put("map", codes);
				list.add(map);
			}
		}
		return setInputStreamData(list);
	}

	/**
	 * 
	 * <p>
	 * 根据<code>codeKey</code>获取<code>Code</code>列表
	 * </p>
	 * 
	 * @return
	 */
	public String getCodeListByKey() {
		List<Code> ret = new ArrayList<Code>(0);
		if (code != null && StringUtils.isNotBlank(code.getCodeKey())) {
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean(Code.CODE_KEY, code.getCodeKey()));
			conditionList.add(new ConditionBean(Code.LANG_TYPE, SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL)
					.toString()));
			ret = codeService.getEntityList(conditionList, null);
		}
		return setInputStreamData(ret);
	}

	/**
	 * 
	 * <p>
	 * 根据<code>codeKey</code>和<code>codeNo</code>模糊获取<code>Code</code>列表
	 * </p>
	 * 
	 * @return
	 */
	public String getCodeListByNoLike() {
		List<Code> ret = new ArrayList<Code>(0);
		if (code != null && StringUtils.isNotBlank(code.getCodeKey()) && StringUtils.isNotBlank(code.getCodeNo())) {
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean(Code.CODE_KEY, code.getCodeKey()));
			conditionList.add(new ConditionBean(Code.CODE_NO, ConditionBean.LIKE, code.getCodeNo()));
			conditionList.add(new ConditionBean(Code.LANG_TYPE, SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL)
					.toString()));
			ret = codeService.getEntityList(conditionList, null);
		}
		return setInputStreamData(ret);
	}

	
	
	/**
	 * 
	 * <p>
	 * 获取省份信息
	 * </p>
	 * 
	 * @return
	 */
	public String getProvinces() {
		List<DictArea> ret = DictionaryUtil.getProvinces();
		
		return setInputStreamData(ret);
	}
	
	/**
	 * 
	 * <p>
	 * 获取下级区域信息
	 * </p>
	 * 
	 * @return
	 */
	public String getAreaListByPid() {

		List<DictArea> ret = DictionaryUtil.getAreaListByPid(areaPid);
		
		return setInputStreamData(ret);
	}
	
	/**
	 * 
	 * <p>
	 * 根据Code获取下级信息
	 * </p>
	 * 
	 * @return
	 */
	public String getAreaListByCode(String code) {
		List<DictArea> ret = DictionaryUtil.getAreaListByCode(code);
		if(ret!=null&&ret.size()>=1) {
			return setInputStreamData(ret.get(0).getNameCn());
		}
		return null;
	}
	
	
	
	public Long getAreaPid() {
		return areaPid;
	}

	public void setAreaPid(Long areaPid) {
		this.areaPid = areaPid;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public void setCodeService(ICodeService codeService) {
		this.codeService = codeService;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
