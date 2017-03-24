package com.upg.cfsettle.foundation.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.basesystem.component.core.IAttachmentService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.mapping.basesystem.other.Attachment;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.JsonUtils;
import com.upg.ucars.util.StringUtil;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManager;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManagerExt;
import com.upg.cfsettle.util.LcsUtils;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:57:11
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsFundManagerServiceImpl extends BaseService implements IFiLcsFundManagerService {
	
	@Autowired
	private IFiLcsFundManagerDao fundManagerDao;
	@Autowired
	private IFiLcsFundManagerExtService fundManagerExtService;
	@Autowired
	private IAttachmentService attachmentService;

	@Override
	public void addLcsFundManagerOfPer(FiLcsFundManager fundManager) {
		fundManager.setCtime(DateTimeUtil.getNowDateTime());
		fundManager.setMtime(DateTimeUtil.getNowDateTime());
		fundManager.setManageType(Byte.valueOf("1"));
		Long fundManagerId = fundManagerDao.save(fundManager);
		List<FiLcsFundManagerExt> fundManagerExts = (List<FiLcsFundManagerExt>) JsonUtils.stringToCollectionWithShortTime(fundManager.getFundManagerPerExt(),FiLcsFundManagerExt.class);
		for(FiLcsFundManagerExt ext:fundManagerExts){
			if(!DateTimeUtil.getFormatDate(ext.getEndTime(),"yyyy-MM-dd").equals("1970-01-01")){
				if(ext.getStartTime().getTime() >= ext.getEndTime().getTime()){
					UcarsHelper.throwServiceException("管理基金开始时间不能大于结束时间");
				}
			}else{
				ext.setEndTime(null);
			}
			ext.setFundManagerId(fundManagerId);
			ext.setCtime(DateTimeUtil.getNowDateTime());
			ext.setMtime(DateTimeUtil.getNowDateTime());
		}
		fundManagerExtService.addFundManagerExtList(fundManagerExts);
		/*Attachment attachment = attachmentService.getById(fundManager.getHeadAttach());
		FiLcsFundAttach fundAttach = new FiLcsFundAttach();
		fundAttach.setPath(attachment.getAttachPath());
		fundAttach.setFileName(attachment.getName());
		fundAttach.setAttachId(fundManager.getHeadAttach());
		fundAttach.setSortNo(0L);
		fundAttach.setFileType("pic");
		fundAttach.setCtime(DateTimeUtil.getNowDateTime());
		fundAttach.setMtime(DateTimeUtil.getNowDateTime());
		fundAttachService.addFundAttach(fundAttach);*/
	} 

	@Override
	public void addFiFundManager(FiLcsFundManager fundManager,FiLcsFundManagerExt fundManagerExt) {
//		Long fundManagerId = this.addLcsFundManager(fundManager);
//		fundManagerService.addFundManagerExtList(null);
	}

	@Override
	public void addLcsFundManagerOfTeam(FiLcsFundManager fundManager) {
		List<Long> fundIndex = LcsUtils.StringToLong(fundManager.getFundIndex().split(","));
		List<FiLcsFundManager> fundManagers = (List<FiLcsFundManager>) JsonUtils.stringToCollectionWithShortTime(fundManager.getFundManagerTeam(),FiLcsFundManager.class);
		List<FiLcsFundManagerExt> fundManagerExts = (List<FiLcsFundManagerExt>) JsonUtils.stringToCollectionWithShortTime(fundManager.getFundManagerTeamExt(),FiLcsFundManagerExt.class);
		List<Long> fundManageIds = new ArrayList<Long>();
		for(FiLcsFundManager manager:fundManagers){
			manager.setFundId(fundManager.getFundId());
			manager.setCtime(DateTimeUtil.getNowDateTime());
			manager.setMtime(DateTimeUtil.getNowDateTime());
			manager.setManageType(Byte.valueOf("2"));
			fundManageIds.add(fundManagerDao.save(manager));
		}
		int index=0;
		Long count = fundIndex.get(index);
		Long mangeId = fundManageIds.get(index);
		for(FiLcsFundManagerExt ext:fundManagerExts){
			if(!DateTimeUtil.getFormatDate(ext.getEndTime(),"yyyy-MM-dd").equals("1970-01-01")){
				if(ext.getStartTime().getTime() >= ext.getEndTime().getTime()){
					UcarsHelper.throwServiceException("管理基金开始时间不能大于结束时间");
				}
			}else{
				ext.setEndTime(null);
			}
			if(count>0){
				ext.setFundManagerId(mangeId);
				count--;
			}else{
				index++;
				count = fundIndex.get(index)-1;
				mangeId = fundManageIds.get(index);
				ext.setFundManagerId(mangeId);
			}
			
			ext.setCtime(DateTimeUtil.getNowDateTime());
			ext.setMtime(DateTimeUtil.getNowDateTime());
		}
		fundManagerExtService.addFundManagerExtList(fundManagerExts);
	}

	@Override
	public List<FiLcsFundManager> findByCondition(FiLcsFundManager fundManager,Page page) {
		String hql = "from FiLcsFundManager fiLcsFundManager";
		QueryCondition condition = new QueryCondition(hql);
		if (fundManager != null) {
			Long fundId = fundManager.getFundId();
			if (fundId != null) {
				condition.addCondition(new ConditionBean("fiLcsFundManager.fundId", ConditionBean.EQUAL, fundId));
			}
			
			String xname = fundManager.getXname();
			if (!StringUtil.isEmpty(xname)) {
				condition.addCondition(new ConditionBean("fiLcsFundManager.xname", ConditionBean.LIKE, xname));
			}
		}
		return fundManagerDao.queryEntity(condition.getConditionList(), page);
	}

	@Override
	public FiLcsFundManager getFundManagerById(Long id) {
		return fundManagerDao.get(id);
	}

	@Override
	public List<FiLcsFundManagerExt> getFundManagerExtById(Long id) {
		return fundManagerExtService.findByCondition(new FiLcsFundManagerExt(id),null);
	}

	@Override
	public FiLcsFundAttach getFundAttachByHeadAttachId(Long headAttach) {
		FiLcsFundAttach fundAttach = new FiLcsFundAttach();
		if(headAttach ==null) return fundAttach;
		Attachment attachment = attachmentService.getById(headAttach);
		fundAttach.setPath(attachment.getAttachPath());
		fundAttach.setFileName(attachment.getName());
		fundAttach.setAttachId(headAttach);
		return fundAttach;
	}

	@Override
	public void doEditFundManager(FiLcsFundManager fundManager) {
		fundManager.setCtime(DateTimeUtil.getNowDateTime());
		fundManager.setMtime(DateTimeUtil.getNowDateTime());
		fundManagerDao.update(fundManager);
		List<FiLcsFundManagerExt> oldExt = fundManagerExtService.findByCondition(new FiLcsFundManagerExt(fundManager.getId()), null);
		fundManagerExtService.deleteLcsFundManagerExtist(oldExt);
		List<FiLcsFundManagerExt> fundManagerExts = (List<FiLcsFundManagerExt>) JsonUtils.stringToCollectionWithShortTime(fundManager.getFundManagerPerExt(),FiLcsFundManagerExt.class);
		for(FiLcsFundManagerExt ext:fundManagerExts){
			if(!DateTimeUtil.getFormatDate(ext.getEndTime(),"yyyy-MM-dd").equals("1970-01-01")){
				if(ext.getStartTime().getTime() >= ext.getEndTime().getTime()){
					UcarsHelper.throwServiceException("管理基金开始时间不能大于结束时间");
				}
			}else{
				ext.setEndTime(null);
			}
			ext.setFundManagerId(fundManager.getId());
			ext.setCtime(DateTimeUtil.getNowDateTime());
			ext.setMtime(DateTimeUtil.getNowDateTime());
		}
		fundManagerExtService.addFundManagerExtList(fundManagerExts);
	}
}
