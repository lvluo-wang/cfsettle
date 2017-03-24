package com.upg.cfsettle.foundation.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.cfsettle.mapping.filcs.FiLcsAnswer;

/**
 * 理财师答案service
 * @author renzhuolun
 * @date 2016年6月16日 上午11:03:40
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsAnswerServiceImpl extends BaseService implements IFiLcsAnswerService{
	@Autowired
	private IFiLcsAnswerDao answerDao;

	@Override
	public List<FiLcsAnswer> findByCondition(FiLcsAnswer searchBean, Page page) {
		String hql = "from FiLcsAnswer fiLcsAnswer";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String answer = searchBean.getAnswer();
			if (answer != null) {
				condition.addCondition(new ConditionBean("fiLcsAnswer.answer", ConditionBean.LIKE, answer));
			}
			
			Long questionId = searchBean.getQuestionId();
			if (questionId != null) {
				condition.addCondition(new ConditionBean("fiLcsAnswer.questionId", ConditionBean.EQUAL, questionId));
			}
			
		}
		return answerDao.queryEntity(condition.getConditionList(), page);
	}

	@Override
	public void addLcsAnswer(FiLcsAnswer lcsAnswer) {
		lcsAnswer.setCtime(DateTimeUtil.getNowDateTime());
		lcsAnswer.setMtime(DateTimeUtil.getNowDateTime());
		answerDao.save(lcsAnswer);
	}

	@Override
	public FiLcsAnswer getFiLcsAnswerById(Long id) {
		return answerDao.get(id);
	}

	@Override
	public void editLcsAnswer(FiLcsAnswer lcsAnswer) {
		lcsAnswer.setMtime(DateTimeUtil.getNowDateTime());
		answerDao.update(lcsAnswer);
	}
}
