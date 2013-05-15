package com.example.jdbcdemo.domain;

public class Bullet {
	
	private int id_bullet;
	private String name;
	
	public Bullet() {
	}
	
	public Bullet(int id_bullet, String name) {
		super();
		this.id_bullet = id_bullet;
		this.name = name;
	}

	public int getId() {
		return id_bullet;
	}
	public void setId(int id) {
		this.id_bullet = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
