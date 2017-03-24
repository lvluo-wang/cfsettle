package com.upg.finance.icbc.tc.transportor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ucars.tsc.transportor.AbstractTransportor;
import com.ucars.tsc.util.BeanXmlUtils;
import com.upg.finance.icbc.bean.BasePackage;
import com.upg.finance.log.core.IFinanceCallLogService;

public abstract class AbstractTransportorTest extends AbstractTransportor {
	
	@Autowired
	protected IFinanceCallLogService financeCallLogService;

	@Override
	protected Object doTransport(Map<String, Object> map) {

		BasePackage ansPackage = getAnsPackage();

		String xml = BeanXmlUtils.bean2Xml(ansPackage);
		System.out.println(this.getClass().getName() + "===========" + xml);

		return xml;
	}

	protected abstract BasePackage getAnsPackage();
}
