package com.tyl.model;

public class Student {

	private String id;

	private String name;

	private Address address;

	public Student() {

	}

	public Student(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.address = new Address(this.name + "Address");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return id + ":" + name;
	}
}
