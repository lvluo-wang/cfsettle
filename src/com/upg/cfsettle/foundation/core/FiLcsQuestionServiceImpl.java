package com.upg.cfsettle.foundation.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;
import com.upg.cfsettle.mapping.filcs.FiLcsQuestion;

/**
 * 理财师问题service
 * @author renzhuolun
 * @date 2016年6月16日 上午11:05:30
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsQuestionServiceImpl extends BaseService implements IFiLcsQuestionService{
	@Autowired
	private IFiLcsQuestionDao questionDao;

	@Override
	public List<FiLcsQuestion> findByCondition(FiLcsQuestion searchBean,Page page) {
		String hql = "from FiLcsQuestion fiLcsQuestion";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String question = searchBean.getQuestion();
			if (!StringUtil.isEmpty(question)) {
				condition.addCondition(new ConditionBean("fiLcsQuestion.question", ConditionBean.LIKE, question));
			}
			
			String typeCode = searchBean.getTypeCode();
			if (!StringUtil.isEmpty(typeCode)) {
				condition.addCondition(new ConditionBean("fiLcsQuestion.typeCode", ConditionBean.EQUAL, typeCode));
			}
			
		}
		
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		orderList.add(new OrderBean("typeCode", true));
		return questionDao.queryEntity(condition.getConditionList(),orderList, page);
	}

	@Override
	public void addLcsQuestion(FiLcsQuestion lcsQuestion) {
		lcsQuestion.setCtime(DateTimeUtil.getNowDateTime());
		lcsQuestion.setMtime(DateTimeUtil.getNowDateTime());
		questionDao.save(lcsQuestion);
	}

	@Override
	public void editLcsQuestion(FiLcsQuestion lcsQuestion) {
		lcsQuestion.setMtime(DateTimeUtil.getNowDateTime());
		questionDao.update(lcsQuestion);
	}

	@Override
	public FiLcsQuestion getFiLcsQuestionById(Long id) {
		return questionDao.get(id);
	}
}