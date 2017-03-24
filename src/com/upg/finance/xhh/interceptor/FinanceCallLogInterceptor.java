package com.upg.finance.xhh.interceptor;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.upg.finance.icbc.annotation.BusinessIdentifier;
import com.upg.finance.log.core.IFinanceCallLogService;
import com.upg.finance.mapping.local.FinanceCallLog;
import com.upg.ucars.framework.base.BaseActionConstant;

@SuppressWarnings("serial")
public class FinanceCallLogInterceptor extends AbstractInterceptor {

	private static final Logger		LOG			= Logger.getLogger(FinanceCallLogInterceptor.class);

	private static DateFormat		DATE_FORMAT	= new SimpleDateFormat("yyyyMMdd");

	private IFinanceCallLogService	financeCallLogService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		// 打印日志
		String methodName = invocation.getProxy().getMethod();
		Map<String, Object> param = invocation.getInvocationContext().getParameters();
		Map<String, String> paramStrMap = convertStringvalue(param);
		LOG.info("method=" + String.valueOf(methodName) + ",param:" + paramStrMap);

		// 增加调用日志记录
		String businessIdentifier = methodCallIdentifier(invocation, paramStrMap);
		saveFinanceCallLog(businessIdentifier);

		String result = null;
		try {
			result = invocation.invoke();

			// 更新调用成功
			FinanceCallLog log = financeCallLogService.get();
			if (log != null) {
				log.setStatus(FinanceCallLog.STATUS_SUCCESS);
			}

		} catch (Exception ex) {
			// 更新调用出错
			FinanceCallLog log = financeCallLogService.get();
			if (log != null) {
				log.setRemark(ex.getMessage());
				log.setStatus(FinanceCallLog.STATUS_FAIL);
			}
			LOG.error(ex.getMessage(), ex);
			result = BaseActionConstant.DATA_STREAM;
		} finally {
			FinanceCallLog log = financeCallLogService.get();
			if (log != null) {
				financeCallLogService.edit(log);
			}
			financeCallLogService.remove();
		}
		return result;
	}

	private static final Map<String, String> convertStringvalue(Map<String, Object> param) {
		Map<String, String> result = new HashMap<String, String>();
		if (param != null) {
			for (Iterator<String> it = param.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				Object value = param.get(key);
				if (value != null && value.getClass().isArray()) {
					value = Arrays.toString((Object[]) value);
				}
				result.put(key, String.valueOf(value));
			}
		}
		return result;
	}

	private void saveFinanceCallLog(String businessIdentifier) {
		String remark = null;
		String status = FinanceCallLog.STATUS_INIT;
		String cmpTxsNo = null;

		List<String> statusList = new ArrayList<String>();
		statusList.add(FinanceCallLog.STATUS_INIT);
		statusList.add(FinanceCallLog.STATUS_SUCCESS);
		statusList.add(FinanceCallLog.STATUS_FAIL);

		// 简单的防止重复
		List<FinanceCallLog> list = financeCallLogService.findByStatuses(businessIdentifier, statusList);
		if (list != null && !list.isEmpty()) {
			// 简单的判断原因

			FinanceCallLog successLog = null;
			FinanceCallLog initLog = null;
			FinanceCallLog failLog = null;

			for (FinanceCallLog log : list) {
				if (log.getCmpTxsNo() != null && log.getCmpTxsNo().trim().length() > 0) {
					String logStatus = log.getStatus();
					if (FinanceCallLog.STATUS_SUCCESS.equals(logStatus)) {
						successLog = log;
						break;
					} else if (FinanceCallLog.STATUS_INIT.equals(logStatus)) {
						initLog = log;
						break;
					} else if (FinanceCallLog.STATUS_FAIL.equals(logStatus)) {
						failLog = log;
					}
				}
			}

			if (successLog != null) {
				status = FinanceCallLog.STATUS_SUCCESS;
				remark = "" + successLog.getId() + "：执行成功";
			} else if (initLog != null) {
				status = FinanceCallLog.STATUS_SUCCESS;
				remark = "" + initLog.getId() + "：正在执行";
			} else if (failLog != null) {
				// 用之前的公司流水号
				cmpTxsNo = failLog.getCmpTxsNo();
			}

		}

		FinanceCallLog log = new FinanceCallLog();
		log.setBusinessIdentifier(businessIdentifier);
		log.setCmpTxsNo(cmpTxsNo);
		log.setStatus(status);
		log.setRemark(remark);

		// 尽早保存到数据库
		financeCallLogService.save(log);

		// 新的公司流水号
		if (log.getCmpTxsNo() == null) {
			String ctnPrefix = DATE_FORMAT.format(new Date());
			Long logId = log.getId();
			String ctnSuffix = "" + (logId.longValue() % 10000000000l);
			int ctnSuffixLen = ctnSuffix.length();
			if (ctnSuffixLen < 12) {
				for (int i = 0; i < 12 - ctnSuffixLen; i++) {
					ctnSuffix = "0" + ctnSuffix;
				}
			}
			cmpTxsNo = ctnPrefix + ctnSuffix;
			log.setCmpTxsNo(cmpTxsNo);
		}
		//

		financeCallLogService.set(log);
	}

	public void setFinanceCallLogService(IFinanceCallLogService financeCallLogService) {
		this.financeCallLogService = financeCallLogService;
	}

	// TODO 通过注解来实现
	private static final String methodCallIdentifier(ActionInvocation invocation, Map<String, String> param)
			throws Exception {
		String result = null;

		Object action = invocation.getProxy().getAction();
		String actionClassName = action.getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String key = actionClassName + "#" + methodName;
		Map<String, Object> map = BUSINESS_IDENTIFIER_MAP.get(key);
		if (map == null) {
			Method method = action.getClass().getDeclaredMethod(methodName, new Class[] {});
			BusinessIdentifier identifier = method.getAnnotation(BusinessIdentifier.class);
			map = new HashMap<String, Object>();
			map.put("prefix", identifier.prefix());
			map.put("paramName", identifier.paramName());
			BUSINESS_IDENTIFIER_MAP.put(key, map);
		}
		if (map != null) {
			result = String.valueOf(map.get("prefix"));
			String[] paramName = (String[]) map.get("paramName");
			StringBuilder sb = new StringBuilder();
			for (String pn : paramName) {
				if (sb.length() > 0) {
					sb.append("，");
				}
				sb.append(pn).append("=").append(param.get(pn));
			}
			result += "(" + sb.toString() + ")";

		}
		return result;
	}

	private static final Map<String, Map<String, Object>>	BUSINESS_IDENTIFIER_MAP	= new HashMap<String, Map<String, Object>>();
}
