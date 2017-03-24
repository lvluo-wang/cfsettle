package com.upg.ucars.basesystem.component.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.other.Attachment;

public interface IAttachmentDao extends IBaseDAO<Attachment, Long> {
	List<Attachment> getAttachListByIdList(List<Long> idList);
}
