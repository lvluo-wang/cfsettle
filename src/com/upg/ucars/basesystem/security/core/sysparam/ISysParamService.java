package com.upg.ucars.basesystem.security.core.sysparam;

import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.security.SysParam;

/**
 * 
 * ISysParamService
 * 
 * @author shentuwy
 * 
 */

public interface ISysParamService extends IBaseService {

	/**
	 * 新增参数
	 * 
	 * @param sp
	 *            参数信息
	 * @return 参数ID
	 */
	public Long addParam(SysParam sp);

	/**
	 * 编辑参数
	 * 
	 * @param sp
	 *            参数信息
	 */
	public void editParam(SysParam sp);

	/**
	 * 删除参数
	 * 
	 * @param sp
	 *            参数信息
	 */
	public void delParam(SysParam sp);

	/**
	 * 删除参数
	 * 
	 * @param id
	 *            参数ID
	 */
	public void delParamById(Long id);

	/**
	 * 批量新增
	 * 
	 * @param sps
	 *            参数信息集合
	 */
	public void addParams(List<SysParam> sps);

	/**
	 * 批量编辑
	 * 
	 * @param sps
	 *            参数信息集合
	 */
	public void editParams(List<SysParam> sps);

	/**
	 * 批量删除
	 * 
	 * @param sps
	 *            参数ID集合
	 */
	public void deleteParams(List<Long> idList);

	/**
	 * 获取系统参数
	 * 
	 * @param id
	 *            系统参数ID
	 * @return 系统参数记录
	 */
	public SysParam getParamById(Long id);

	/**
	 * 获取系统参数
	 * 
	 * @param miNo
	 *            接入者编号
	 * @param paramKey
	 *            参数编号
	 * @return SysParam
	 */
	public SysParam findSysParam(String miNo, String paramKey);

	/**
	 * 获取系统参数
	 * 
	 * @param miNo
	 *            接入者编号
	 * @return 接入者下所有参数列表
	 */
	public List<SysParam> getSysParams(String miNo);

	/**
	 * 组合条件查询
	 * 
	 * @param sp
	 *            组合条件信息
	 * @param page
	 *            分页信息
	 * @return 参数列表
	 */
	public List<SysParam> findParamByPage(SysParam sp, Page page);

	/**
	 * 提交审批
	 * 
	 * @param id
	 *            参数ID
	 */
	public void commitAudit(Long id);

	/**
	 * 批量提交审批
	 * 
	 * @param ids
	 *            参数ID列表
	 */
	public void commitAudit(List<Long> ids);

	/**
	 * 撤销审批
	 * 
	 * @param id
	 *            参数ID
	 */
	public void revokeAudit(Long id);

	/**
	 * 批量撤销审批
	 * 
	 * @param ids
	 *            参数ID列表
	 */
	public void revokeAudit(List<Long> ids);

	/**
	 * 查询审批任务
	 * 
	 * @param id
	 *            参数ID
	 * @return 审核任务
	 */
	public AuditTask findAuditTask(Long id);

	/**
	 * 
	 * 获取系统参数值 <br/>
	 * 注意：如果指定的接入点的参数不存在，则取默认的参数值
	 * 
	 * @param miNo
	 *            　接入者编号
	 * @param paramKey
	 *            　参数编号
	 * @return　参数值
	 */
	public String getSysParamValueByKey(String miNo, String paramKey);

	/**
	 * 机构权限分配是否需要审核
	 * 
	 * @param miNo
	 *            接入者编号
	 * @return　true：审核，false：不需要审核
	 */
	public boolean isCheckBrchFunc(String miNo);

	/**
	 * 用户角色分配是否需要审核
	 * 
	 * @param miNo
	 *            接入者编号
	 * @return true：审核，false：不需要审核
	 */

	public boolean isCheckUserRole(String miNo);

	/**
	 * 机构管理员角色分配是否需要审核
	 * 
	 * @param miNo
	 *            接入者编号
	 * @return true：审核，false：不需要审核
	 */
	public boolean isCheckBrchManagerRole(String miNo);

	/**
	 * 
	 * 获取系统参数列表
	 * 
	 * @param idList
	 *            参数ID列表
	 * @return 参数列表
	 */
	public List<SysParam> getSysParamByIds(List<Long> idList);

	/**
	 * 
	 * 为接入点分配参数<br/>
	 * 如果idList为空，则删除接入点的所有参数
	 * 
	 * @param idList
	 *            参数ID列表
	 * @param miNo
	 *            接入者编号
	 */
	public void assignSysParam(List<Long> idList, String miNo);

	/**
	 * 
	 * 转化成map格式
	 * 
	 * @param sp
	 *            参数信息
	 * @return map结构的参数信息
	 */
	public Map<String, Object> convertSysParamToMap(SysParam param);

}
