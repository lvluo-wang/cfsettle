package com.upg.finance.icbc.tc.transportor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.upg.finance.icbc.bean.BasePackage;
import com.upg.finance.icbc.bean.ans.AnswerPub;
import com.upg.finance.icbc.bean.ans.CancelUserAccountAns;
import com.upg.finance.icbc.bean.ans.CancelUserAccountAnsPackage;
import com.upg.finance.icbc.processor.IcbcRequestProcoessor;

@Component
public class CancelUserAccountTransportorTest extends AbstractTransportorTest {

	@Override
	protected BasePackage getAnsPackage() {

		CancelUserAccountAnsPackage ansPackage = new CancelUserAccountAnsPackage();

		AnswerPub pub = new AnswerPub();
		pub.setRetcode("B2001");
		ansPackage.setPub(pub);

		CancelUserAccountAns ans = new CancelUserAccountAns();
		ans.setUid("1001");
		ansPackage.setAns(ans);

		List<Object> signBeanList = new ArrayList<Object>();
		signBeanList.add(pub);
		signBeanList.add(ans);

		String signature = IcbcRequestProcoessor.getSignResult(signBeanList);
		ansPackage.setSignature(signature);
		
		return ansPackage;
	}

}
