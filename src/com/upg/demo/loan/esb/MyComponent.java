package com.upg.demo.loan.esb;

public class MyComponent {
	public String process(String msg) {
		System.out.println("MyComponent process " + msg);
		return msg;
	}
}
