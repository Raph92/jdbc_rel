package com.example.jdbcdemo.domain;

public class Weapon {
	
	private int id_weapon;
	private String name;
	
	public Weapon() {
	}
	
	public Weapon(int id_weapon, String name) {
		super();
		this.id_weapon = id_weapon;
		this.name = name;
	}
	public int getId() {
		return id_weapon;
	}
	public void setId(int id) {
		this.id_weapon = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
