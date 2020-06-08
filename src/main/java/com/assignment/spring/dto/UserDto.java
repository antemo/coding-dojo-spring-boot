package com.assignment.spring.dto;

import com.assignment.spring.entity.Permission;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserDto {

	@NotNull
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(max = 100)
	private String fullName;

	private final Set<Permission> permissions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	public void setPermissions(final Collection<Permission> permissions) {
		Assert.notNull(permissions, "Permissions collection can't be null");
		Assert.noNullElements(permissions, "Permissions collection can't hold null values");

		this.permissions.clear();
		this.permissions.addAll(permissions);
	}
}
