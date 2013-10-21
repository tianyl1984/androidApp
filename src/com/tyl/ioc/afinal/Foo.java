package com.tyl.ioc.afinal;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.ManyToOne;

public class Foo {

	@Id(column = "id")
	private int id;

	private String name;

	private String email;

	@ManyToOne(column = "id_gee")
	private Gee gee;

	public Foo() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public String toString() {
		return "id:" + id + ",name:" + name + ",email:" + email + (gee == null ? ",gee=null" : ",gee:" + gee.toString());
	}

	public Gee getGee() {
		return gee;
	}

	public void setGee(Gee gee) {
		this.gee = gee;
	}

}
