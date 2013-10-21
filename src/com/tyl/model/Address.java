package com.tyl.model;

public class Address {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address(String name) {
		super();
		this.name = name;
	}

	public Address() {
		super();
	}

	@Override
	public String toString() {
		return name;
	}
}
