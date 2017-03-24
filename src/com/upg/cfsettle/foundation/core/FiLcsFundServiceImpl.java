package com.upg.cfsettle.foundation.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.basesystem.component.core.IAttachmentService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.mapping.basesystem.other.Attachment;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.BeanUtils;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;
import com.upg.cfsettle.mapping.filcs.FiLcsBookingOrder;
import com.upg.cfsettle.mapping.filcs.FiLcsFund;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;
import com.upg.cfsettle.util.LcsUtils;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:57:11
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsFundServiceImpl extends BaseService implements IFiLcsFundService {
	
	@Autowired
	private IFiLcsFundDao fiLcsFundDao;
	@Autowired
	private IAttachmentService attachmentService;
	@Autowired
	private IFiLcsFundAttachService lcsFundAttachService;
	@Autowired
	private IFiLcsBookingOrderService bookingOrderService;
	@Override
	public List<FiLcsFund> findByCondition(FiLcsFund searchBean, Page page) {
		String hql = "from FiLcsFund fiLcsFund";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long fundType = searchBean.getFundType();
			if (fundType != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundType", ConditionBean.EQUAL, fundType));
			}
			
			String fundName = searchBean.getFundName();
			if (!StringUtil.isEmpty(fundName)) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundName", ConditionBean.LIKE, fundName));
			}
			
			Date startTime = searchBean.getStartTime();
			if (startTime != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.ctime", ConditionBean.MORE_AND_EQUAL, startTime));
			}
			
			Date endTime = searchBean.getEndTime();
			if (endTime != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.ctime", ConditionBean.LESS_AND_EQUAL, DateTimeUtil.getSpecifiedDayAfter(endTime)));
			}
			
			Byte fundStatus = searchBean.getFundStatus();
			if (fundStatus != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundStatus", ConditionBean.EQUAL,fundStatus));
			}
			
			Byte status = searchBean.getStatus();
			if (status != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.status", ConditionBean.EQUAL,status));
			}else{
				condition.addCondition(new ConditionBean("fiLcsFund.status", ConditionBean.EQUAL,Byte.valueOf("2")).addPartner(
						new ConditionBean("fiLcsFund.status", ConditionBean.EQUAL,Byte.valueOf("3")).setLogic(ConditionBean.LOGIC_OR)
						));
			}
		}
		
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		OrderBean auditStatus = new OrderBean("status", true);
		OrderBean timeOrder = new OrderBean("ctime", false);
		orderList.add(auditStatus);
		orderList.add(timeOrder);
		return fiLcsFundDao.queryEntity(condition.getConditionList(),orderList, page);
	}

	@Override
	public void addFiLcsFund(FiLcsFund fiLcsFund, FiLcsFundAttach fiLcsFundAttach) {
		if(fiLcsFund.getStartTime().getTime()<DateTimeUtil.getNowMillis()){
			UcarsHelper.throwServiceException("预售开始时间不能小于当前时间");
		}
		if(fiLcsFund.getEndTime().getTime()<DateTimeUtil.getNowMillis()){
			UcarsHelper.throwServiceException("预售结束时间不能小于当前时间");
		}
		if(fiLcsFund.getStartTime().getTime()>=fiLcsFund.getEndTime().getTime()){
			UcarsHelper.throwServiceException("预售开始时间不能大于预售结束时间");
		}
		if(fiLcsFund.getBuildTime() == null){
			fiLcsFund.setBuildTime(DateTimeUtil.getNowDateTime());
		}
		Long fundId = fiLcsFundDao.addFiLcsFund(fiLcsFund);
		if(!StringUtil.isEmpty(fiLcsFund.getAttachId())){
			List<Attachment> attachments = attachmentService.getAttachListByIdList(LcsUtils.StringToLong(fiLcsFund.getAttachId().split(",")));
			List<FiLcsFundAttach> fundAttachs = new ArrayList<FiLcsFundAttach>();
			for(Attachment attachment:attachments){
				FiLcsFundAttach attach = new FiLcsFundAttach();
				attach.setFileName(attachment.getName());
				attach.setFileType("file");
				attach.setFundId(fundId);
				attach.setPath(attachment.getAttachPath());
				attach.setSortNo(0L);
				attach.setCtime(DateTimeUtil.getNowDateTime());
				attach.setMtime(DateTimeUtil.getNowDateTime());
				attach.setAttachId(attachment.getId());
				fundAttachs.add(attach);
			}
			lcsFundAttachService.addFundAttachList(fundAttachs);
		}
	}

	@Override
	public FiLcsFund getFiLcsFundById(Long id) {
		return  fiLcsFundDao.get(id);
	}

	@Override
	public void editFiLcsFund(FiLcsFund fiLcsFund) {
		if(fiLcsFund.getStartTime().getTime()<DateTimeUtil.getNowMillis()){
			UcarsHelper.throwServiceException("预售开始时间不能小于当前时间");
		}
		if(fiLcsFund.getEndTime().getTime()<DateTimeUtil.getNowMillis()){
			UcarsHelper.throwServiceException("预售结束时间不能小于当前时间");
		}
		if(fiLcsFund.getStartTime().getTime()>=fiLcsFund.getEndTime().getTime()){
			UcarsHelper.throwServiceException("预售开始时间不能大于预售结束时间");
		}
		fiLcsFund.setMtime(DateTimeUtil.getNowDateTime());
//		fiLcsFund.setEndTime(DateTimeUtil.getSpecifiedDayAfter(fiLcsFund.getEndTime()));
		fiLcsFundDao.update(fiLcsFund);
		Long fundId = fiLcsFund.getId();
		if(!StringUtil.isEmpty(fiLcsFund.getAttachId())){
			List<Attachment> attachments = attachmentService.getAttachListByIdList(LcsUtils.StringToLong(fiLcsFund.getAttachId().split(",")));
			List<FiLcsFundAttach> delFundAttachs = lcsFundAttachService.getFundAttachsByFundId(fiLcsFund.getId());
			lcsFundAttachService.deleteFundAttach(delFundAttachs);
			List<FiLcsFundAttach> fundAttachs = new ArrayList<FiLcsFundAttach>();
			for(Attachment attachment:attachments){
				if(isNotExistFundAttach(attachment.getId())){
					FiLcsFundAttach attach = new FiLcsFundAttach();
					attach.setFileName(attachment.getName());
					attach.setFileType("file");
					attach.setFundId(fundId);
					attach.setPath(attachment.getAttachPath());
					attach.setSortNo(0L);
					attach.setCtime(DateTimeUtil.getNowDateTime());
					attach.setMtime(DateTimeUtil.getNowDateTime());
					attach.setAttachId(attachment.getId());
					fundAttachs.add(attach);
				}
			}
			lcsFundAttachService.addFundAttachList(fundAttachs);
		}
	}

	private boolean isNotExistFundAttach(Long attachId) {
		FiLcsFundAttach fundAttach = new FiLcsFundAttach();
		fundAttach.setAttachId(attachId);
		List<FiLcsFundAttach> attachs = lcsFundAttachService.findByCondition(fundAttach, null);
		return CollectionUtils.isEmpty(attachs);
	}

	@Override
	public void doAuditFiLcsFund(Long id,Byte status,FiLcsFund fiLcsFund) {
		FiLcsFund fund = this.getFiLcsFundById(id);
		fund.setStatus(status);
		fund.setMtime(DateTimeUtil.getNowDateTime());
		fund.setLastReason(fiLcsFund.getLastReason());
		fiLcsFundDao.update(fund);
	}

	@Override
	public List<FiLcsFund> findAuditByCondition(FiLcsFund searchBean, Page page) {
		String hql = "from FiLcsFund fiLcsFund";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long fundType = searchBean.getFundType();
			if (fundType != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundType", ConditionBean.EQUAL, fundType));
			}
			
			String fundName = searchBean.getFundName();
			if (!StringUtil.isEmpty(fundName)) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundName", ConditionBean.LIKE, fundName));
			}
			
			Date startTime = searchBean.getStartTime();
			if (startTime != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.ctime", ConditionBean.MORE_AND_EQUAL, startTime));
			}
			
			Date endTime = searchBean.getEndTime();
			if (endTime != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.ctime", ConditionBean.LESS_AND_EQUAL, DateTimeUtil.getSpecifiedDayAfter(endTime)));
			}
			
			Byte fundStatus = searchBean.getFundStatus();
			if (fundStatus != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundStatus", ConditionBean.EQUAL,fundStatus));
			}
			
			Byte status = searchBean.getStatus();
			if (status != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.status", ConditionBean.EQUAL,status));
			}
		}
		
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		OrderBean auditStatus = new OrderBean("status", true);
		OrderBean timeOrder = new OrderBean("ctime", false);
		orderList.add(auditStatus);
		orderList.add(timeOrder);
		return fiLcsFundDao.queryEntity(condition.getConditionList(),orderList, page);
	}

	@Override
	public List<FiLcsFund> findOperByCondition(FiLcsFund searchBean, Page page) {
		String hql = "from FiLcsFund fiLcsFund";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long fundType = searchBean.getFundType();
			if (fundType != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundType", ConditionBean.EQUAL, fundType));
			}
			
			String fundName = searchBean.getFundName();
			if (!StringUtil.isEmpty(fundName)) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundName", ConditionBean.LIKE, fundName));
			}
			
			Date startTime = searchBean.getStartTime();
			if (startTime != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.ctime", ConditionBean.MORE_AND_EQUAL, startTime));
			}
			
			Date endTime = searchBean.getEndTime();
			if (endTime != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.ctime", ConditionBean.LESS_AND_EQUAL, DateTimeUtil.getSpecifiedDayAfter(endTime)));
			}
			
			Byte fundStatus = searchBean.getFundStatus();
			if (fundStatus != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.fundStatus", ConditionBean.EQUAL,fundStatus));
			}
			
			Byte status = searchBean.getStatus();
			if (status != null) {
				condition.addCondition(new ConditionBean("fiLcsFund.status", ConditionBean.EQUAL,status));
			}
		}
		
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		orderList.add(new OrderBean("fundStatus", false));
		orderList.add(new OrderBean("top", false));
		orderList.add(new OrderBean("activity", false));
		orderList.add(new OrderBean("mtime", false));
		List<FiLcsFund> list = fiLcsFundDao.queryEntity(condition.getConditionList(),orderList, page);
		return list;
	}

	@Override
	public void doUpperLcsFundById(Long id) {
		FiLcsFund fiLcsFund = this.getFiLcsFundById(id);
		fiLcsFund.setMtime(DateTimeUtil.getNowDateTime());
		fiLcsFund.setTop(Byte.valueOf("1"));
		fiLcsFundDao.update(fiLcsFund);
	}

	@Override
	public void doEditOperInfo(FiLcsFund fiLcsFund) {
		FiLcsFund fund = this.getFiLcsFundById(fiLcsFund.getId());
		try {
			BeanUtils.copyNoNullProperties(fund, fiLcsFund);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		fiLcsFundDao.update(fund);
	}

	@Override
	public void doActiveLcsFundById(Long id) {
		FiLcsFund fiLcsFund = this.getFiLcsFundById(id);
		fiLcsFund.setMtime(DateTimeUtil.getNowDateTime());
		fiLcsFund.setActivity(Byte.valueOf("1"));
		fiLcsFundDao.update(fiLcsFund);
	}

	@Override
	public void doEditStartTime(FiLcsFund fiLcsFund) {
		if(fiLcsFund.getStartTime().getTime()<DateTimeUtil.getNowMillis()){
			UcarsHelper.throwServiceException("预售开始时间不能小于当前时间");
		}
		FiLcsFund fund = this.getFiLcsFundById(fiLcsFund.getId());
		if(fiLcsFund.getStartTime().getTime()>fund.getEndTime().getTime()){
			UcarsHelper.throwServiceException("预售开始时间不能大于预售结束时间");
		}
		try {
			BeanUtils.copyNoNullProperties(fund, fiLcsFund);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		fiLcsFundDao.update(fund);
		
	}

	@Override
	public void doEditEndTime(FiLcsFund fiLcsFund) {
		if(DateTimeUtil.getSpecifiedDayAfter(fiLcsFund.getEndTime()).getTime()<DateTimeUtil.getNowMillis()){
			UcarsHelper.throwServiceException("预售结束时间不能小于当前时间");
		}
		FiLcsFund fund = this.getFiLcsFundById(fiLcsFund.getId());
		if(fund.getStartTime().getTime()>=fiLcsFund.getEndTime().getTime()){
			UcarsHelper.throwServiceException("预售结束时间不能小于预售开始时间");
		}
		try {
			BeanUtils.copyNoNullProperties(fund, fiLcsFund);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		fiLcsFundDao.update(fund);
	}

	@Override
	public void undoFund(Long id) {
		FiLcsFund fiLcsFund = this.getFiLcsFundById(id);
		List<FiLcsBookingOrder> orders = bookingOrderService.findByCondition(new FiLcsBookingOrder(id), null);
		if(!orders.isEmpty()){
			UcarsHelper.throwServiceException("项目已产生订单,不能撤销");
		}
		fiLcsFund.setActivity(Byte.valueOf("0"));
		fiLcsFund.setStatus(Byte.valueOf("3"));
		fiLcsFund.setMtime(DateTimeUtil.getNowDateTime());
		fiLcsFundDao.update(fiLcsFund);
	}

	@Override
	public void doEditFundStatus(FiLcsFund fiLcsFund) {
		FiLcsFund lcsFund = this.getFiLcsFundById(fiLcsFund.getId());
		lcsFund.setFundStatus(fiLcsFund.getFundStatus());
		fiLcsFundDao.update(lcsFund);
	}
}
