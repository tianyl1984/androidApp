package com.tyl.orm;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.ManyToOne;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class FooOrman extends Model<FooOrman> {

	@PrimaryKey(autoIncrement = true)
	private int id;

	private String name;

	private String email;

	@ManyToOne
	private ZooOrman zooOrman;

	public FooOrman() {

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

	public ZooOrman getZooOrman() {
		return zooOrman;
	}

	public void setZooOrman(ZooOrman zooOrman) {
		this.zooOrman = zooOrman;
	}

}
