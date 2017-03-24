package com.upg.ucars.framework.bpm.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.basesystem.product.core.product.IMemberProductAttributeService;
import com.upg.ucars.basesystem.product.core.product.IProductAttributeService;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.bpm.ext.IBpmPlantfromClientService;
import com.upg.ucars.framework.bpm.procmap.IProdProcMapService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.DealOpinionInfo;

/**
 * 
 * 使用工作流引擎客户服务
 * 
 * @author shentuwy
 * @date 2012-7-20
 * 
 */
public class BpmPlantfromClientServiceImpl extends BaseService implements
		IBpmPlantfromClientService {

	/** 产品流程映射服务 */
	private IProdProcMapService prodProcMapService;
	/** 任务意见处理服务 */
	private ITaskTraceInfoService taskTraceInfoService;
	/** 接入点产品属性服务 */
	@Autowired
	private IMemberProductAttributeService memberProductAttributeService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IBranchService branchService;

	public String getProcessName(Long brchId, String prodNo) {
		String result = prodProcMapService.getProcessName(brchId, prodNo, true);
		if (StringUtils.isBlank(result)) {
			String processProductNo = convertProcessProductNo(brchId, prodNo);
			if (StringUtils.isBlank(processProductNo)) {
				processProductNo = prodNo;
			}
			result = prodProcMapService.getProcessName(brchId, processProductNo);
		}
		return result;
	}

	public String getProcessNameIgnoreNoProcess(Long brchId, String prodNo) {
		String result = prodProcMapService.getProcessName(brchId, prodNo, true);
		if (StringUtils.isBlank(result)) {
			String processProductNo = convertProcessProductNo(brchId, prodNo);
			if (StringUtils.isBlank(processProductNo)) {
				processProductNo = prodNo;
			}
			result = prodProcMapService.getProcessName(brchId, processProductNo, true);
		}
		return result;
	}

	private String getMiNoByBrchId(Long brchId) {
		String result = null;
		if (brchId != null) {
			Branch branch = branchService.getBranchByBrchId(brchId);
			if (branch != null) {
				result = branch.getMiNo();
			}
		}
		return result;
	}
	
	private String convertProcessProductNo(Long brchId,String productNo){
		String miNo = getMiNoByBrchId(brchId);
		return convertProcessProductNo(miNo, productNo);
	}

	private String convertProcessProductNo(String miNo, String productNo) {
		String result = null;
		if (StringUtils.isNotBlank(productNo) && StringUtils.isNotBlank(miNo)) {
			ProductInfo product = productService.getProductByProdNo(productNo);
			if (product != null) {
				List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
				conditionList.add(new ConditionBean("productId", product.getId()));
				conditionList.add(new ConditionBean("miNo", miNo));
				conditionList.add(new ConditionBean("attributeKey", IProductAttributeService.PRODUCT_PROCESS_ATTR_CODE));
				List<MemberProductAttribute> attributes = memberProductAttributeService
						.getEntityList(conditionList, null);
				if (attributes != null && attributes.size() > 0) {
					result = attributes.get(0).getAttributeValue();
				}else{
					if (product.getParentProdId() != null) {
						ProductInfo parent = productService.getProduct(product.getParentProdId());
						result = convertProcessProductNo(miNo, parent.getProdNo());
					}
				}
			}
		}
		return result;
	}

	public void traceTaskDeal(Long taskId, Long entityId,
			DealOpinionInfo opinionInfo) {
		taskTraceInfoService.traceTaskDeal(taskId, entityId, opinionInfo);
	}

	public void setProdProcMapService(IProdProcMapService prodProcMapService) {
		this.prodProcMapService = prodProcMapService;
	}

	public void setTaskTraceInfoService(
			ITaskTraceInfoService taskTraceInfoService) {
		this.taskTraceInfoService = taskTraceInfoService;
	}

}
