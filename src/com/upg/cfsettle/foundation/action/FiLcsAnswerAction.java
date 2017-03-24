package com.upg.cfsettle.foundation.action;

import java.util.List;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.foundation.core.IFiLcsAnswerService;
import com.upg.cfsettle.mapping.filcs.FiLcsAnswer;

/**
 * 理财师答案action
 * @author renzhuolun
 * @date 2016年6月16日 上午11:07:59
 * @version <b>1.0.0</b>
 */
@SuppressWarnings("serial")
public class FiLcsAnswerAction extends BaseAction {
	
	private IFiLcsAnswerService fiLcsAnswerService;
	
	private FiLcsAnswer searchBean;
	
	private Long questionId;
	
	private FiLcsAnswer lcsAnswer;
	
	/**
	 * 跳转主页面
	 * @author renzhuolun
	 * @date 2016年6月16日 上午11:11:20
	 * @return
	 */
	public String main(){
		questionId = getPKId();
		return SUCCESS;
	}
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年6月16日 上午11:12:05
	 * @return
	 */
	public String list(){
		List<FiLcsAnswer> list = fiLcsAnswerService.findByCondition(searchBean,getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	
	public String toAdd(){
		return SUCCESS;
	}
	
	public void doAdd(){
		fiLcsAnswerService.addLcsAnswer(lcsAnswer);
	}
	
	
	public String toEdit(){
		lcsAnswer = fiLcsAnswerService.getFiLcsAnswerById(getPKId());
		return SUCCESS;
	}
	
	public void doEdit(){
		fiLcsAnswerService.editLcsAnswer(lcsAnswer);
	}
	
	public void setFiLcsAnswerService(IFiLcsAnswerService fiLcsAnswerService) {
		this.fiLcsAnswerService = fiLcsAnswerService;
	}

	public FiLcsAnswer getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(FiLcsAnswer searchBean) {
		this.searchBean = searchBean;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public FiLcsAnswer getLcsAnswer() {
		return lcsAnswer;
	}

	public void setLcsAnswer(FiLcsAnswer lcsAnswer) {
		this.lcsAnswer = lcsAnswer;
	}
}