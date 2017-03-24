package com.upg.cfsettle.foundation.action;

import java.util.List;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.foundation.core.IFiLcsQuestionService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.filcs.FiLcsQuestion;
import com.upg.cfsettle.util.LcsConstant;

/**
 * 理财师问题action
 * @author renzhuolun
 * @date 2016年6月16日 上午11:07:34
 * @version <b>1.0.0</b>
 */
@SuppressWarnings("serial")
public class FiLcsQuestionAction extends BaseAction {
	
	private IFiLcsQuestionService fiLcsQuestionService;
	
	private FiLcsQuestion searchBean;
	
	private List<FiCodeItem> typeList;
	
	private FiLcsQuestion lcsQuestion;
	
	/**
	 * 跳转主页面
	 * @author renzhuolun
	 * @date 2016年6月16日 上午11:11:20
	 * @return
	 */
	public String main(){
		typeList = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_QUESTION_TYPE);
		return SUCCESS;
	}
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年6月16日 上午11:12:05
	 * @return
	 */
	public String list(){
		List<FiLcsQuestion> list = fiLcsQuestionService.findByCondition(searchBean,getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	
	
	public String toAdd(){
		typeList = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_QUESTION_TYPE);
		return SUCCESS;
	}
	
	public void doAdd(){
		fiLcsQuestionService.addLcsQuestion(lcsQuestion);
	}
	
	
	public String toEdit(){
		lcsQuestion = fiLcsQuestionService.getFiLcsQuestionById(getPKId());
		typeList = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_QUESTION_TYPE);
		return SUCCESS;
	}
	
	public void doEdit(){
		fiLcsQuestionService.editLcsQuestion(lcsQuestion);
	}
	
	/**
	 * 查看
	 * @author renzhuolun
	 * @date 2016年6月16日 下午3:05:08
	 */
	public String doView(){
		lcsQuestion = fiLcsQuestionService.getFiLcsQuestionById(getPKId());
		return SUCCESS;
	}
	
	public void setFiLcsQuestionService(IFiLcsQuestionService fiLcsQuestionService) {
		this.fiLcsQuestionService = fiLcsQuestionService;
	}

	public FiLcsQuestion getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(FiLcsQuestion searchBean) {
		this.searchBean = searchBean;
	}

	public List<FiCodeItem> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<FiCodeItem> typeList) {
		this.typeList = typeList;
	}

	public FiLcsQuestion getLcsQuestion() {
		return lcsQuestion;
	}

	public void setLcsQuestion(FiLcsQuestion lcsQuestion) {
		this.lcsQuestion = lcsQuestion;
	}
}