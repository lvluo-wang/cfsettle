package com.upg.ucars.framework.bpm.assign.dao;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActr;
/**
 * HumanTaskActr实体DAO接口
 * 
 * @author shentuwy
 *
 */
public interface IHumanTaskActrDAO extends IBaseDAO<HumnTaskActr, Long>{	
	/**
	 * 获取任务参与者
	 * @param processName
	 * @param taskName
	 * @param brchId
	 * @return List< Buser.UserNo >
	 * @throws DAOException
	 */
	public List<String> getTaskActors(String processName, String taskName, Long brchId) throws DAOException;
	
	/**
	 * 获取任务参与者
	 * @param taskId
	 * @param brchId
	 * @return List< Buser.UserNo >
	 * @throws DAOException
	 */
	public List<HumnTaskActr> getHumnTaskActrs(Long taskId, Long brchId) throws DAOException;
	/**
	 * 获取任务授权信息
	 *
	 * @param processName
	 * @param taskName
	 * @param brchId
	 * @return
	 * @throws ServiceException
	 */
	public List<HumnTaskActr> getHumnTaskActrs(String processName,String taskName, Long brchId) throws ServiceException;
	
	/**
	 * 删除一个机构的一个流程任务配置
	 * 
	 * @param branchId
	 * @param processDefId
	 */
	public void deleteHumnTaskActr(Long branchId,Long processDefId);
	
	/**
	 * 获取任务的分配列表
	 * 
	 * @param branchId
	 * @param processDefId
	 * @return
	 */
	public List<HumnTaskActr> getHumnTaskActr(Long branchId,Long processDefId);

}
