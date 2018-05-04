package com.jp.hczz.350m.model;

import com.jp.hczz.350m.constant.StudentSex;

public class StudentModel {
	private String id;
	private String name;
	private StudentSex sex;
	private Float score;
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
	public StudentSex getSex() {
		return sex;
	}
	public void setSex(StudentSex sex) {
		this.sex = sex;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
}
