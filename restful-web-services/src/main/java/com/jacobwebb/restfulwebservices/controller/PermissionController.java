package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 * Create Privilege
	 */
	@PostMapping(path="/webbj/privileges/")
	public ResponseEntity<Void> createPrivilege(@RequestBody Privilege privilege) {
		
		Privilege createdPrivilege = privilegeRepository.save(privilege);
		

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/{id}").buildAndExpand(createdPrivilege.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	/*
	 * Read Privileges
	*/
	@GetMapping("/webbj/privileges/")
	public List<Privilege> getAllPrivileges() {
		return privilegeRepository.findAll();
	}
	
	/*
	 * Read Privilege
	 */
	@GetMapping("webbj/privileges/{id}")
	public Privilege getPrivilege(@PathVariable long id) {
		return privilegeRepository.findById(id).get();
	}
	
	/*
	 * Update Privilege
	 */
	@PutMapping("/webbj/privileges/{id}")
	public ResponseEntity<Privilege> updatePrivilege(
			@PathVariable long id, @RequestBody Privilege privilege) {
		
		Privilege privilegeUpdated = privilegeRepository.save(privilege);
		
		return new ResponseEntity<Privilege>(privilegeUpdated, HttpStatus.OK);
	}
	
	/*
	 * Delete Privilege
	 */
	@DeleteMapping("/webbj/privileges/{id}")
	public ResponseEntity<Void> deletePrivilege(@PathVariable long id) {
		
		// Check that the privilege exists before deleting it
		if (privilegeRepository.existsById(id)) {
			privilegeRepository.deleteById(id);
		}
		
		return ResponseEntity.noContent().build();
	}
	
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
