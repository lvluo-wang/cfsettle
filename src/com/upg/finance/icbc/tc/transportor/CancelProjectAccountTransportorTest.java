package com.upg.finance.icbc.tc.transportor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.upg.finance.icbc.bean.BasePackage;
import com.upg.finance.icbc.bean.ans.AnswerPub;
import com.upg.finance.icbc.bean.ans.CancelProjectAccountAns;
import com.upg.finance.icbc.bean.ans.CancelProjectAccountAnsPackage;
import com.upg.finance.icbc.processor.IcbcRequestProcoessor;

@Component
public class CancelProjectAccountTransportorTest extends AbstractTransportorTest {

	
	@Override
	protected BasePackage getAnsPackage() {
		CancelProjectAccountAnsPackage ansPackage = new CancelProjectAccountAnsPackage();
		
		//pub
		AnswerPub pub = new AnswerPub();
		pub.setRetcode("B2000");
		ansPackage.setPub(pub);
		
		
		//ans
		CancelProjectAccountAns ans = new CancelProjectAccountAns();
		ans.setPrjNo("P-test-01");
		ansPackage.setAns(ans);
		
		List<Object> signBeanList = new ArrayList<Object>();
		signBeanList.add(pub);
		signBeanList.add(ans);

		String signature = IcbcRequestProcoessor.getSignResult(signBeanList);
		ansPackage.setSignature(signature);

		return ansPackage;
	}

}
