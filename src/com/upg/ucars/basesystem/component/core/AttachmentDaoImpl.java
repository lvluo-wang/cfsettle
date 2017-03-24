package com.upg.ucars.basesystem.component.core;

import java.util.List;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.other.Attachment;

@Dao
public class AttachmentDaoImpl extends BaseDAO<Attachment, Long> implements IAttachmentDao {

	@Override
	public Class<Attachment> getEntityClass() {
		return Attachment.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> getAttachListByIdList(List<Long> idList) {
		final String hql = "from Attachment where id in (:idList)";
		return getHibernateTemplate().findByNamedParam(hql, "idList", idList);
	}
	
}
