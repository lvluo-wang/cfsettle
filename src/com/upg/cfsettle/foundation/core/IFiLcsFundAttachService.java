package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:55:30
 * @version <b>1.0.0</b>
 */
public interface IFiLcsFundAttachService extends IBaseService {
	
	/**
	 * 批量添加附件
	 * @author renzhuolun
	 * @date 2016年6月7日 下午3:25:50
	 * @param fundAttachs
	 */
	void addFundAttachList(List<FiLcsFundAttach> fundAttachs);
	
	/**
	 * 获取基金所有材料附件
	 * @author renzhuolun
	 * @date 2016年6月7日 下午5:43:28
	 * @param id
	 * @return
	 */
	List<FiLcsFundAttach> getFundAttachsByFundId(Long id);
	
	/**
	 * 条件查询附件信息
	 * @author renzhuolun
	 * @date 2016年6月7日 下午5:45:25
	 * @param fundAttach
	 * @param page
	 * @return
	 */
	List<FiLcsFundAttach> findByCondition(FiLcsFundAttach fundAttach,Page page);
	
	/**
	 * 新增附件信息
	 * @author renzhuolun
	 * @date 2016年6月12日 下午12:10:10
	 * @param fundAttach
	 */
	Long addFundAttach(FiLcsFundAttach fundAttach);
	
	/**
	 * 删除附件
	 * @author renzhuolun
	 * @date 2016年7月1日 下午12:29:04
	 * @param delFundAttachs
	 */
	void deleteFundAttach(List<FiLcsFundAttach> delFundAttachs);
	
}
