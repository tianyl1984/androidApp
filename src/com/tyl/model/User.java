package com.tyl.model;

import java.io.Serializable;

public class User extends Root implements Serializable, IRoot {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String email;

	private Teacher teacher;

	private int age;

	public User() {

	}

	public User(String id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return id + ":" + name + ":" + email + ":" + age;
	}
}
