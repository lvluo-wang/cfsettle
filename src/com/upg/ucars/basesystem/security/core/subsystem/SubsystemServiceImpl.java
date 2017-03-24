/*
 * 源程序名称: SubsystemService.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.security.core.subsystem;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.ReSubsysFunc;
import com.upg.ucars.mapping.basesystem.security.Subsystem;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;

/**
 * 子系统服务实现类
 * 
 * @author shentuwy
 *
 */
public class SubsystemServiceImpl extends BaseService implements ISubsystemService {
	/** 子系统 */
	private ISubsystemDAO subsystemDAO;
	/** 子系统权限 */
	private ISubsysFuncDAO subsysFuncDAO;
	
	public ISubsystemDAO getSubsystemDAO() {
		return subsystemDAO;
	}

	public void setSubsystemDAO(ISubsystemDAO subsystemDAO) {
		this.subsystemDAO = subsystemDAO;
	}
	

	public ISubsysFuncDAO getSubsysFuncDAO() {
		return subsysFuncDAO;
	}

	public void setSubsysFuncDAO(ISubsysFuncDAO subsysFuncDAO) {
		this.subsysFuncDAO = subsysFuncDAO;
	}

	/*-------------------*/
	public void addFunc(Long subsysId, List<Long> funcIdList)
			throws ServiceException, DAOException {
		List<ReSubsysFunc> list=new ArrayList<ReSubsysFunc>();
		for(Long funcId:funcIdList){
			ReSubsysFunc re=subsysFuncDAO.findReSubsysFunc(subsysId,funcId);
			if(re==null){
				re=new ReSubsysFunc(subsysId,funcId);
				list.add(re);
			}

		}
		subsysFuncDAO.saveOrUpdateAll(list);
	}

	public void closeSubsystem(Long subsysId) throws ServiceException,
			DAOException {
		Subsystem subsys=subsystemDAO.get(subsysId);
		subsys.setSubsysStatus(Subsystem.STATUS_CLOSE);
		subsystemDAO.update(subsys);
	}

	public void createSubsystem(Subsystem subsystem) throws ServiceException,
			DAOException {
		subsystemDAO.save(subsystem);

	}

	public List<Subsystem> findAllSubsystem() throws ServiceException,
			DAOException {
		return subsystemDAO.findAllSubsystem();
	}

	public Subsystem findById(Long subsysId) throws ServiceException,
			DAOException {
		if( subsysId == null ){
			return null;
		}
		return subsystemDAO.get(subsysId);
	}

	public List<Sysfunc> findExistingFunc(Long subsysId)
			throws ServiceException, DAOException {
		return subsysFuncDAO.getSysfuncBySubsysId(subsysId);
	}

	public List<Subsystem> findOpenedSubsystem() throws ServiceException,
			DAOException {
		return subsystemDAO.getSubsystemByStatus(Subsystem.STATUS_OPEN);
	}

	public void openSubsystem(Long subsysId) throws ServiceException,
			DAOException {
		Subsystem subsys=subsystemDAO.get(subsysId);
		subsys.setSubsysStatus(Subsystem.STATUS_OPEN);
		subsystemDAO.update(subsys);

	}

	public void removeFunc(Long subsysId, List<Long> funcIdList)
			throws ServiceException, DAOException {
		List<ReSubsysFunc> list=new ArrayList<ReSubsysFunc>();
		for(Long funcId:funcIdList){
			ReSubsysFunc subsys=subsysFuncDAO.findReSubsysFunc(subsysId,funcId);
			if(subsys!=null){
				list.add(subsys);
			}
			
			
		}
		subsysFuncDAO.delAll(list);
	}

	public void renameSubsystem(Long subsysId, String subsysName)
			throws ServiceException, DAOException {
		Subsystem subsys=subsystemDAO.get(subsysId);
		subsys.setSubsysName(subsysName);
		subsystemDAO.update(subsys);

	}

	public void mondifySubsystem(Subsystem subsystem) throws ServiceException,
			DAOException {
		subsystemDAO.update(subsystem);
		
	}

	public void removeById(Long subsysId) throws ServiceException, DAOException {
		Subsystem subsystem = subsystemDAO.get(subsysId);
		if(Subsystem.STATUS_OPEN.equals(subsystem.getSubsysStatus())){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.SUBSYS_DEL_STATUS_OPEN);
		}
		if(subsysFuncDAO.hasFunc(subsysId)){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.SUBSYS_DEL_HAS_FUNC);
		}
		subsystemDAO.delete(subsysId);
	}

	public List<Subsystem> findSubsystemByCondition(List<ConditionBean> beanList,Page page)
			throws ServiceException, DAOException {
		return subsystemDAO.queryEntity(beanList, page);
	}

	public List<Subsystem> findAllSubsystem(Page pg) throws ServiceException,
			DAOException {
		return subsystemDAO.queryEntity(new QueryComponent(), pg);
	}

	public List<Sysfunc> findExistingFunc(List<Long> sysIdList)
			throws ServiceException, DAOException {
		return subsysFuncDAO.getSysfuncBySubsysId(sysIdList);
	}

	public List<Sysfunc> findMenuFunc(Long subsysId) throws ServiceException,
			DAOException {
		List<Sysfunc> ret = null;
		if( subsysId != null ){
			ret = subsysFuncDAO.getMenuSysfuncBySubsysId(subsysId);
		}
		return ret == null ? new ArrayList<Sysfunc>(0) : ret;
	}
	
	public boolean isInner(Subsystem subsystem) {
		boolean ret = false;
		if( subsystem != null ){
			ret = StringUtils.equals(TYPE_INNER, subsystem.getType());
		}
		return ret;
	}
}
