package com.tyl.orm;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class ZooOrman extends Model<ZooOrman> {

	@PrimaryKey(autoIncrement = true)
	private int id;

	private String name;

	public ZooOrman() {

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

}
