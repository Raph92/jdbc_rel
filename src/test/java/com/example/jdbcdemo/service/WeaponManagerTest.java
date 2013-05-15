package com.example.jdbcdemo.service;

import com.example.jdbcdemo.domain.Bullet;
import com.example.jdbcdemo.domain.Weapon;
import com.example.jdbcdemo.domain.WeaponBullets;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class WeaponManagerTest {
	
	WeaponManager weaponManager = new WeaponManager();

// INSERT DATA
	private final static int[] bullet_ids = {1, 2, 3, 4, 5, 6};
	private final static String[] bullet_names = {"12,7 × 99 mm NATO", ".30-06 Springfield", "7,62 x 51 mm NATO", "9 x 19 mm Parabellum", "5,56 x 45 mm", "7,62 × 39 mm wz. 43"};
	private final static int[] weapon_ids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	private final static String[] weapon_names = {"M4A1", "M16", "G36", "Steyr AUG-77", "M107", "MP5", "USP", "M1 Garand", "G3", "M40A1", "UMP", "FN F2000", "AK 47"};
	
	private final static int[] bullet_types = {4, 4, 4, 4, 0, 3, 3, 1, 2, 2, 3, 4, 5};
		
	@Test
	public void checkConnection(){
		assertNotNull(weaponManager.getConnection());
	}

// Clear all records for testing
	@Test
	public void refreshDataBase() {
		weaponManager.clearBulletToWeapon();
		weaponManager.clearWeapons();
		weaponManager.clearBullets();
	}
	
	@Test
	public void checkBulletAdd(){
		Bullet[] bullet = new Bullet[bullet_names.length];
		
		for(int i = 0; i < bullet_names.length; i++){
			bullet[i] = new Bullet(bullet_ids[i], bullet_names[i]);
			assertEquals(1,weaponManager.addBullet(bullet[i]));		
		};		
				
		List<Bullet> bullets = weaponManager.getAllBullets();		
		for(int i = 0; i < bullet_names.length; i++){
			Bullet bulletRetrieved = bullets.get(i);
			assertEquals(bullet_ids[i], bulletRetrieved.getId());
			assertEquals(bullet_names[i], bulletRetrieved.getName());
		}		
	}
	
	@Test
	public void checkWeaponAdd(){
		Weapon[] weapon = new Weapon[weapon_names.length];
		
		for(int i = 0; i < weapon_names.length; i++){
			weapon[i] = new Weapon(weapon_ids[i], weapon_names[i]);
			assertEquals(1,weaponManager.addWeapon(weapon[i]));		
		};		
				
		List<Weapon> weapons = weaponManager.getAllWeapons();		
		for(int i = 0; i < weapon_names.length; i++){
			Weapon weaponRetrieved = weapons.get(i);
			assertEquals(weapon_ids[i], weaponRetrieved.getId());
			assertEquals(weapon_names[i], weaponRetrieved.getName());
		}		
	}
	
	@Test
	public void checkAddBulletToWeapon(){
		Bullet[] bullet = new Bullet[bullet_ids.length];
		Weapon[] weapon = new Weapon[weapon_ids.length];
		WeaponBullets[] weaponBullets = new WeaponBullets[weapon_ids.length];
		
		for (int i = 0; i < bullet_ids.length; i++) {
			bullet[i] = new Bullet(bullet_ids[i], bullet_names[i]);
		}
		
		for (int i = 0; i < weapon_ids.length; i++) {
			weapon[i] = new Weapon(weapon_ids[i], weapon_names[i]);
			weaponBullets[i] = new WeaponBullets(weapon_ids[i], bullet_ids[bullet_types[i]]);
		}

		for(int i = 0; i < weaponBullets.length; i++) {
			assertEquals(1, weaponManager.addBulletToWeapon(weaponBullets[i]));
		}
		
		for(int i = 0; i < weaponBullets.length; i++) {
			List<WeaponBullets> weaponBulletsList = weaponManager.getAllBulletToWeapon();		
			WeaponBullets weaponBulletsRetrieved = weaponBulletsList.get(i);
			
			assertEquals(weaponBullets[i].getId_weapon(), weaponBulletsRetrieved.getId_weapon());
			assertEquals(weaponBullets[i].getId_bullet(), weaponBulletsRetrieved.getId_bullet());
		}
	}		
	
	@Test
	public void getWeaponsWithBullet(){
		Bullet bullet = new Bullet(bullet_ids[4], bullet_names[4]);
		int count = 0;

		List<WeaponBullets> weaponBullets = weaponManager.getAllBulletToWeapon();
		
		for (int i = 0; i < weaponBullets.size(); i++) {
			if (weaponBullets.get(i).getId_bullet() == bullet.getId()) {
				count++;
			}
		}

		List<Weapon> weaponsRetrieved = weaponManager.getWeaponsWithBullet(bullet);
		
		assertEquals(count, weaponsRetrieved.size());
	}
	
	@Test
	public void checkDelWeaponWithBullet(){
		Weapon[] weapon = new Weapon[weapon_ids.length];
		
		for (int i = 0; i < weapon_ids.length; i++) {
			weapon[i] = new Weapon(weapon_ids[i], weapon_names[i]);
		}

		for(int i = 0; i < weapon.length; i++) {
			assertEquals(1, weaponManager.delWeaponWithBullet(weapon[i]));
		}
	}		
	
}
