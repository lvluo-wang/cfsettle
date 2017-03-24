package com.upg.finance.icbc.tc.transportor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.upg.finance.icbc.bean.BasePackage;
import com.upg.finance.icbc.bean.ans.AnswerPub;
import com.upg.finance.icbc.bean.ans.OpenUserAccountAns;
import com.upg.finance.icbc.bean.ans.OpenUserAccountAnsPackage;
import com.upg.finance.icbc.processor.IcbcRequestProcoessor;

@Component
public class OpenUserAccountTransportorTest extends AbstractTransportorTest {

	@Override
	protected BasePackage getAnsPackage() {
		OpenUserAccountAnsPackage answerPackage = new OpenUserAccountAnsPackage();
		// pub
		AnswerPub pub = new AnswerPub();
		pub.setRetcode("B2001");
		answerPackage.setPub(pub);

		// ans
		OpenUserAccountAns ans = new OpenUserAccountAns();
		ans.setUid("1000");
		ans.setAccountNo("1023012");
		answerPackage.setAns(ans);

		// signature
		List<Object> signBeanList = new ArrayList<Object>();
		signBeanList.add(pub);
		signBeanList.add(ans);

		String signature = IcbcRequestProcoessor.getSignResult(signBeanList);
		answerPackage.setSignature(signature);

		return answerPackage;
	}

}
