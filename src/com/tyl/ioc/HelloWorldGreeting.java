package com.tyl.ioc;

public class HelloWorldGreeting implements IGreetingService {

	@Override
	public String getGreeting() {
		return "Hello World";
	}

}
