package com.upg.demo.loan.esb.client;

public class SimpleServiceImpl implements ISimpleService {

	public String sayHello(String name) {
		String words = " hello " + name;
		System.out.println(words);
		return words;
	}

}
