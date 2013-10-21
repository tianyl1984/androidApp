package com.tyl.ioc.afinal;

import net.tsz.afinal.annotation.sqlite.Id;

public class Gee {

	@Id(column = "id")
	private int id;

	private String name;

	public Gee() {

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

	@Override
	public String toString() {
		return "id:" + id + ",name:" + name;
	}
}
