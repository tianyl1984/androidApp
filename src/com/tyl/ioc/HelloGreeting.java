package com.tyl.ioc;

public class HelloGreeting implements IGreetingService {

	@Override
	public String getGreeting() {
		return "hello";
	}

}
