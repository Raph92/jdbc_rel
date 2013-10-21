package com.example.jdbcdemo.service;

import com.example.jdbcdemo.domain.Bullet;
import com.example.jdbcdemo.domain.Weapon;
import com.example.jdbcdemo.domain.WeaponBullets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class WeaponManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";

	private String createTableBullet = "CREATE TABLE Bullet(id_bullet INT  PRIMARY KEY, name VARCHAR(20))";
	private String createTableWeapon = "CREATE TABLE Weapon(id_weapon INT  PRIMARY KEY, name VARCHAR(30))";
	private String createTableWeaponBullets = "CREATE TABLE WeaponBullets(id_weapon int, id_bullet int, " +
//											  "UNIQUE(id_weapon), " +
									   		  "FOREIGN KEY(id_weapon) REFERENCES Weapon, " +
									   		  "FOREIGN KEY(id_bullet) REFERENCES Bullet)";
	
// Bullets statements
	private PreparedStatement addBulletsStmt;
	private PreparedStatement delAllBulletsStmt;
	private PreparedStatement getAllBulletsStmt;
	private PreparedStatement getWeaponsWithBullet;
	private PreparedStatement getWeaponsStmt;

// Weapon statements
	private PreparedStatement addWeaponsStmt;
	private PreparedStatement delAllWeaponsStmt;
	private PreparedStatement getAllWeaponsStmt;

// WeaponBullets statement
	private PreparedStatement addBulletToWeaponStmt;
	private PreparedStatement delWeaponWithBulletStmt;
	private PreparedStatement delAllBulletToWeaponStmt;
	private PreparedStatement getBulletToWeaponStmt;
	
	
	
	private Statement statement;

	/**
	 * 
	 */
	public WeaponManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			
// Create tables if not exist
			ResultSet rs = connection.getMetaData().getTables(null, null, null,	null);

			boolean tableExists = false;
			while (rs.next()) {
				if ("Bullet".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			if (!tableExists) {
				statement.executeUpdate(createTableBullet);
			}
			tableExists = false;
			while (rs.next()) {
				if ("Weapon".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			if (!tableExists) {
				statement.executeUpdate(createTableWeapon);
			}
			tableExists = false;
			while (rs.next()) {
				if ("WeaponBullets".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			if (!tableExists) {
				statement.executeUpdate(createTableWeaponBullets);
			}
			
// Bullets statement
			addBulletsStmt = connection
					.prepareStatement("INSERT INTO Bullet (id_bullet, name) VALUES (?,?)");
			delAllBulletsStmt = connection
					.prepareStatement("DELETE FROM Bullet");
			getAllBulletsStmt = connection
					.prepareStatement("SELECT id_bullet, name FROM Bullet");
			getWeaponsWithBullet = connection
					.prepareStatement("SELECT id_weapon, name FROM Weapon WHERE id_weapon IN (SELECT id_weapon FROM WeaponBullets WHERE id_bullet = ?)");
			getWeaponsStmt = connection
					.prepareStatement("SELECT id_weapon FROM WeaponBullets WHERE id_weapon = ?");
// Weapons statement
			addWeaponsStmt = connection
					.prepareStatement("INSERT INTO Weapon (id_weapon, name) VALUES (?,?)");
			delAllWeaponsStmt = connection
					.prepareStatement("DELETE FROM Weapon");
			getAllWeaponsStmt = connection
					.prepareStatement("SELECT id_weapon, name FROM Weapon");
// WeaponBullets statements
			addBulletToWeaponStmt = connection
					.prepareStatement("INSERT INTO WeaponBullets (id_weapon, id_bullet) VALUES (?,?)");	
			delWeaponWithBulletStmt = connection
					.prepareStatement("DELETE FROM WeaponBullets WHERE id_weapon = ?");	
			delAllBulletToWeaponStmt = connection
					.prepareStatement("DELETE FROM WeaponBullets");
			getBulletToWeaponStmt= connection
					.prepareStatement("SELECT id_weapon, id_bullet FROM WeaponBullets");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}
	
// Bullet database methods
	void clearBullets() {
		try {
			delAllBulletsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addBullet(Bullet bullet) {
		int count = 0;
		try {
			addBulletsStmt.setInt(1, bullet.getId());
			addBulletsStmt.setString(2, bullet.getName());
			count = addBulletsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<Bullet> getAllBullets() {
		List<Bullet> bullets = new ArrayList<Bullet>();

		try {
			ResultSet rs = getAllBulletsStmt.executeQuery();
			while (rs.next()) {
				Bullet b = new Bullet();
				b.setId(rs.getInt("id_bullet"));
				b.setName(rs.getString("name"));
				bullets.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bullets;
	}
	
//	Weapon database operation
	void clearWeapons() {
		try {
			delAllWeaponsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addWeapon(Weapon weapon) {
		int count = 0;
		try {
			addWeaponsStmt.setInt(1, weapon.getId());
			addWeaponsStmt.setString(2, weapon.getName());
			count = addWeaponsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<Weapon> getAllWeapons() {
		List<Weapon> weapons = new ArrayList<Weapon>();

		try {
			ResultSet rs = getAllWeaponsStmt.executeQuery();
			while (rs.next()) {
				Weapon w = new Weapon();
				w.setId(rs.getInt("id_weapon"));
				w.setName(rs.getString("name"));
				weapons.add(w);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return weapons;
	}
	
	void clearBulletToWeapon() {
		try {
			delAllBulletToWeaponStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addBulletToWeapon(WeaponBullets weaponBullet) {
		int count = 0;
		try {
			addBulletToWeaponStmt.setInt(1, weaponBullet.getId_weapon());
			addBulletToWeaponStmt.setInt(2, weaponBullet.getId_bullet());
			count = addBulletToWeaponStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Weapon delWeaponWithBullet(Weapon weapon) {
		try {
			delWeaponWithBulletStmt.setInt(1, weapon.getId());
			delWeaponWithBulletStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return weapon;
	}
	
	public List<WeaponBullets> getAllBulletToWeapon() {
		List<WeaponBullets> weaponBullets = new ArrayList<WeaponBullets>();

		try {
			ResultSet rs = getBulletToWeaponStmt.executeQuery();
			while (rs.next()) {
				WeaponBullets wb = new WeaponBullets();
				wb.setId_weapon(rs.getInt("id_weapon"));
				wb.setId_bullet(rs.getInt("id_bullet"));
				weaponBullets.add(wb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return weaponBullets;
	}
	
	
	public List<Weapon> getWeaponsWithBullet(Bullet bullet) {
		
		List<Weapon> weapons = new ArrayList<Weapon>();

		try {
			getWeaponsWithBullet.setInt(1, bullet.getId());
			ResultSet rs = getWeaponsWithBullet.executeQuery();
			while (rs.next()) {
				Weapon w = new Weapon();
				w.setId(rs.getInt("id_weapon"));
				w.setName(rs.getString("name"));
				weapons.add(w);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return weapons;
	}
	
public WeaponBullets getWeapons(Weapon weapon) {
		
		WeaponBullets weaponBullets = new WeaponBullets();

		try {
			getWeaponsStmt.setInt(1, weapon.getId());
			ResultSet rs =getWeaponsStmt.executeQuery();
			while (rs.next()) {
				weaponBullets.setId_weapon(rs.getInt("id_weapon"));
				weaponBullets.setId_bullet(rs.getInt("id_bullet"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return weaponBullets;
	}
	
}
