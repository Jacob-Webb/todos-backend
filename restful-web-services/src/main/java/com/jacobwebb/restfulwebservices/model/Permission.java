package com.jacobwebb.restfulwebservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="permission")
public class Permission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="permission_id")
	private Long id;
	
	@Column(name="title")
	private String roleTitle;
	
	@Column(name="edit_todos")
	private boolean editTodos;
	
	@Column(name="view_todos")
	private boolean viewTodos;
	
	public Permission() {
		
	}
	
	public Permission(String new_role, boolean canEdit, boolean canView) {
		roleTitle = new_role;
		editTodos = canEdit;
		viewTodos = canView;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleTitle() {
		return roleTitle;
	}

	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}

	public boolean isEditTodos() {
		return editTodos;
	}

	public void setEditTodos(boolean editTodos) {
		this.editTodos = editTodos;
	}

	public boolean isViewTodos() {
		return viewTodos;
	}

	public void setViewTodos(boolean viewTodos) {
		this.viewTodos = viewTodos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (id != other.id)
			return false;
		return true;
	}

}