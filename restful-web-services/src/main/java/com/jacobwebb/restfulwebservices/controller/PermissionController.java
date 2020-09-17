package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;
import java.util.Collection;
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
import com.jacobwebb.restfulwebservices.dao.RoleRepository;
import com.jacobwebb.restfulwebservices.model.Privilege;
import com.jacobwebb.restfulwebservices.model.Role;

/*
 * Controller that should only be accessible by the systemAdmin.
 * CRUD operations for Permissions (ie Roles and Privileges)
 */

@RestController
public class PermissionController {
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired RoleRepository roleRepository;
	
	/*
	 * Create Privilege
	 */
	@PostMapping(path="/security/privileges/")
	public ResponseEntity<Void> createPrivilege(@RequestBody Privilege privilege) {
		
		Privilege createdPrivilege = privilegeRepository.save(privilege);
		

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/{id}").buildAndExpand(createdPrivilege.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * Return all Privileges while nulling all privilege values of Roles 
	 * to eliminate infinite recursion
	*/
	@GetMapping("/security/privileges")
	public Collection<Privilege> getAllPrivileges() {
		
		// For each Role in each Privilege, set the associtated privileges to null
		List<Privilege> privileges = privilegeRepository.findAll();
		for (Privilege privilege: privileges) {
			for (Role role: privilege.getRoles()) {
				role.setPrivileges(null);
			}
		}
		
		return privilegeRepository.findAll();
	}
	
	/*
	 * Read Privilege based on id
	 */
	@GetMapping("/security/privileges/{id}")
	public Privilege getPrivilege(@PathVariable long id) {
		return privilegeRepository.findById(id).get();
	}
	
	/*
	 * Get the collection of roles belonging to a privilege
	 */
	@GetMapping("/security/privileges/{id}/roles")
	public Collection<Role> getPrivilegeRoles(@PathVariable long id) {
		
		Privilege privilege = privilegeRepository.findById(id).get();
		Collection<Role> roles = privilege.getRoles();
		return roles;
	}
	
	/*
	 * Update Privilege
	 * Change the name. Updating associated Roles will be taken care 
	 * of by updating Roles. 
	 */
	@PutMapping("/security/privileges/{id}")
	public ResponseEntity<Privilege> updatePrivilege(
			@PathVariable long id, @RequestBody Privilege privilege) {
		
		Privilege privilegeUpdated = privilegeRepository.save(privilege);
		
		return new ResponseEntity<Privilege>(privilegeUpdated, HttpStatus.OK);
	}
	
	/*
	 * Delete Privilege
	 */
	@DeleteMapping("/security/privileges/{id}")
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
	@PostMapping("/security/roles")
	public ResponseEntity<Void> createRole(@RequestBody Role role) {
		
		Role createdRole = roleRepository.save(role);
		

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/{id}").buildAndExpand(createdRole.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * Read Roles
	 *   return all Roles
	 */
	@GetMapping("/security/roles")
	public Collection<Role> getAllRoles() {
		
		return roleRepository.findAll();
	}
	
	/*
	 * Read Role
	 *   Return the role found by id
	 */
	@GetMapping("/security/roles/{id}")
	public Role getRole(@PathVariable long id) {
		return roleRepository.findById(id).get();
	}
	
	/*
	 * Update Role
	 *   update name, collection of users, collections of roles, or all of the above
	 */
	@PutMapping("/security/roles/{id}")
	public ResponseEntity<Role> updateRole(
			@PathVariable long id, @RequestBody Role role) {
		
		if (roleRepository.findById(id) != null) {
			return new ResponseEntity<Role>(roleRepository.save(role), HttpStatus.OK);
		}
		return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Delete Role
	 *   Remove Role from the table
	 */
	@DeleteMapping("/security/roles/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable long id) {
		// Check that the Role exists before deleting it
				if (roleRepository.existsById(id)) {
					roleRepository.deleteById(id);
				}
				
				return ResponseEntity.noContent().build();
	}

}
