package com.example.jdbcdemo.domain;

public class WeaponBullets {
	
	private int id_weapon;
	private int id_bullet;

	public WeaponBullets() {
	}
	
	public WeaponBullets(int id_weapon, int id_bullet) {
		super();
		this.id_weapon = id_weapon;
		this.id_bullet = id_bullet;
	}
	
	public int getId_weapon() {
		return id_weapon;
	}
	
	public void setId_weapon(int id_weapon) {
		this.id_weapon = id_weapon;
	}
	
	public int getId_bullet() {
		return id_bullet;
	}

	public void setId_bullet(int id_bullet) {
		this.id_bullet = id_bullet;
	}

	
}
