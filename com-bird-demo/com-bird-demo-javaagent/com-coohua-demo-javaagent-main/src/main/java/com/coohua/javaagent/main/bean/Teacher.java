package com.bird.javaagent.main.bean;

public class Teacher {

	private String name = "hepengyuan";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " is good.";
	}
}
