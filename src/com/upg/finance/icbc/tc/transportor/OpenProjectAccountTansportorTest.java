package com.upg.finance.icbc.tc.transportor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.upg.finance.icbc.bean.BasePackage;
import com.upg.finance.icbc.bean.ans.AnswerPub;
import com.upg.finance.icbc.bean.ans.OpenProjectAccountAns;
import com.upg.finance.icbc.bean.ans.OpenProjectAccountAnsPackage;
import com.upg.finance.icbc.processor.IcbcRequestProcoessor;

@Component
public class OpenProjectAccountTansportorTest extends AbstractTransportorTest {

	@Override
	protected BasePackage getAnsPackage() {

		OpenProjectAccountAnsPackage ansPackage = new OpenProjectAccountAnsPackage();

		AnswerPub pub = new AnswerPub();
		pub.setRetcode("301");
		ansPackage.setPub(pub);

		OpenProjectAccountAns ans = new OpenProjectAccountAns();
		ans.setPrjNo("3001");
		ansPackage.setAns(ans);

		List<Object> signBeanList = new ArrayList<Object>();
		signBeanList.add(pub);
		signBeanList.add(ans);

		String signature = IcbcRequestProcoessor.getSignResult(signBeanList);
		ansPackage.setSignature(signature);
		
		return ansPackage;
	}

}
