package com.tyl.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

	private String id;

	private String name;

	private List<Student> students = new ArrayList<Student>();

	public Teacher() {

	}

	public static final Teacher getATeacher() {
		Teacher t = new Teacher("t1", "t1Name");
		t.addStu(new Student("s1", "s1Name"));
		t.addStu(new Student("s2", "s2Name"));
		t.addStu(new Student("s3", "s3Name"));
		t.addStu(new Student("s4", "s4Name"));
		t.addStu(new Student("s5", "s5Name"));
		t.addStu(new Student("s6", "s6Name"));
		return t;
	}

	public Teacher(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void addStu(Student stu) {
		this.students.add(stu);
	}

	@Override
	public String toString() {
		return id + ":" + name;
	}
}
