package com.thegummybears.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.thegummybears.common.entity.Role;
import com.thegummybears.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager enitiyManager;
	
	//Run Junit with empty test to create the table in DB
	//It will create 
	//1 users table and 
	//2 users_roles table
	//-------------------
	//Update method
	/*
	 * @Test public void testCreateUser() { Role roleAdmin =
	 * enitiyManager.find(Role.class, 1); //Assign to be Admin User userThitari =
	 * new User("Thitari@gmail.com", "password123", "Thitari", "Somboon", 110.0);
	 * userThitari.addRole(roleAdmin);
	 * 
	 * User savedUser = repo.save(userThitari);
	 * assertThat(savedUser.getId()).isGreaterThan(0); //Object save as persistant
	 * object }
	 * 
	 */	
	@Test
	public void testCreateNewUserwithOneRole() {
		Role roleAdmin = enitiyManager.find(Role.class, 1); //Assign to be Admin 
		User userTim = new User("tim@email.com", "tim123456", "Tim", "Smith");	
		userTim.addRole(roleAdmin);
		
		User savedUser =  repo.save(userTim);
		assertThat(savedUser.getId()).isGreaterThan(0); //Object save as persistant object
	}
	
	@Test
	public void testCreateNewUserwithTwoRoles() {
		User userJen = new User("jen@email.com", "jen123456", "Jen", "Robinson");	
		Role roleAdmin = new Role(1);
		Role roleEmployee = new Role(2);
		userJen.addRole(roleAdmin);
		userJen.addRole(roleEmployee);
		
		User savedUser =  repo.save(userJen);
		assertThat(savedUser.getId()).isGreaterThan(0); //Object save as persistant object
	}
	
	/**
	 * Test retriving all users in the DB
	 */
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	/**
	 * retrive user based on ID
	 */
	@Test
	public void testGetUsersById() {
		User userTim = repo.findById(1).get();
		System.out.println(userTim);
		assertThat(userTim).isNotNull();
	}
	
	/**
	 * test update user details
	 */
	@Test
	public void testUpdateUserDetails() {
		User userTim = repo.findById(1).get();
		userTim.setEnabled(true);
		userTim.setEmail("tim.updateEmail@gmail.com");
		
		repo.save(userTim);
	}
	
	/**
	 * Update role of existing user
	 * remove
	 * add
	 */
	@Test
	public void testUpdateUserRoles() {
		User userJen = repo.findById(2).get();
		Role roleAdmin = new Role(1);
		//Role roleManager = new Role(3);
		userJen.getRoles().remove(roleAdmin); 
		//userOla.addRole(roleManager);
		
		repo.save(userJen);
	}
	
	/**
	 * Test delete user based on ID
	 */
	@Test
	public void testDeleteUser() {
		Integer userId = 3;
		repo.deleteById(userId);
	}
	
	
	/**
	 * [30] Check Uniquness Email
	 */
	@Test
	public void testGetUserByEmail() {
		String email = "another@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();		
	}
	
	/**
	 * [33 - Delete user]
	 */
	@Test
	public void testCountById() {
		//Integer id = 100;
		Integer id = 1;
		Long countById = repo.countById(id);	
		
		assertThat(countById).isNotNull().isGreaterThan(0);
		}
	
	/**
	 * [34 - Update user enabled status]
	 */
	@Test
	public void testDisableUser() {
		Integer id = 4;
		repo.updateEnabledStatus(id, false);
	}
	
	/**
	 * [34 - Update user enabled status]
	 */
	@Test
	public void testEnableUser() {
		Integer id = 6;
		repo.updateEnabledStatus(id, true);
	}
	
}
