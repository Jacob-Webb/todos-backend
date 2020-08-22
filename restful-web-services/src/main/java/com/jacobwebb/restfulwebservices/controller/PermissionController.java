package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jacobwebb.restfulwebservices.dao.PrivilegeRepository;
import com.jacobwebb.restfulwebservices.model.Privilege;

@RestController
public class PermissionController {
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	/*
	 * Permissions include: read and write for Todos, Users, UserDate, UserRoles
	 * Need some way for Admin to send reset password 
	 * Roles include: 
	 * 			User: 		Todos-RW, UserData-RW, 
	 * 			Admin:		User + Users-RW
	 * 			SuperAdmin:	Admin + UserRoles-RW
	 */
	
	/*
	 * Create Permissions
	 *   if not already created
	 *     set the name of the permission
	 *     save the permission
	 *       example: READ_USER_DATA or WRITE_TODO
	 */
	//trial
	@PostMapping(path="webbj/privileges/")
	public ResponseEntity<Void> createPrivilege(@RequestBody Privilege privilege) {
		
		Privilege createdPrivilege = privilegeRepository.save(privilege);
		

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/{id}").buildAndExpand(createdPrivilege.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	/*
	 * Read Permissions
	 *   Return a list of all permissions
	 *     return names and associated roles
	*/
	
	/*
	 * Read Permission
	 *   return a name of the permission and all roles associated found by id
	 */
	
	/*
	 * Update Permission
	 *   Change name of Permission
	 *       or
	 *   Update roles collection of Permission
	 */
	
	/*
	 * Delete Permission
	 *   Remove Permission from table
	 */
	
	/*
	 * Create Role
	 *   if Role not already created
	 *     set which permissions a role will have
	 *     set the name of the role
	 *     save the role
	 */
	
	/*
	 * Read Roles
	 *   return all Roles
	 */
	
	/*
	 * Read Role
	 *   Return the role found by id
	 */
	
	/*
	 * Update Role
	 *   update name, collection of users, collections of roles, or all of the above
	 */
	
	/*
	 * Delete Role
	 *   Remove Role from the table
	 */

}
