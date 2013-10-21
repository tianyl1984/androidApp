package com.tyl.ioc;

import com.google.inject.AbstractModule;

public class GreetingModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind(IGreetingService.class).to(HelloGreeting.class);
		bind(IGreetingService.class).to(HelloWorldGreeting.class);
		// bind(SharedPreferences.class).annotatedWith(Names.named("aaa")).toProvider(new
		// SharedPreferencesProvider("aaa"));
	}

}
