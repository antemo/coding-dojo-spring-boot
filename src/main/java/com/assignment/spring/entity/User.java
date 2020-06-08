package com.assignment.spring.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.*;

/**
 * This class stores user data. It implements {@link UserDetails} so that it can be set as {@link Principal} in Spring Security
 */
@Table("users")
public class User implements UserDetails {

	private static final long serialVersionUID = 205047387423212456L;

	@Id
	private Long id;

	/** All lowercase for getter to match {@link UserDetails} method */
	@Column("user_name")
	private String username;

	@Column("full_name")
	private String fullName;

	@Column("password")
	private String password;

	@Column("permissions")
	private final Set<Permission> permissions = new HashSet<>();

	public Long getId() {
		return id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	public void addPermission(final Permission permission) {
		Assert.notNull(permission, "Permission to add can't be null");

		permissions.add(permission);
	}

	public void removePermission(final Permission permission) {
		Assert.notNull(permission, "Permission to remove can't be null");

		permissions.remove(permission);
	}

	public void setPermissions(final Collection<Permission> permissions) {
		Assert.notNull(permissions, "Permissions collection can't be null");
		Assert.noNullElements(permissions, "Permissions collection can't hold null values");

		this.permissions.clear();
		this.permissions.addAll(permissions);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(username);
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		final User other = (User) object;

		return Objects.equals(username, other.getUsername());
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableSet(permissions);
	}

	public boolean isAccountNonExpired() {
		// out of scope of this project
		return true;
	}

	public boolean isAccountNonLocked() {
		// out of scope of this project
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// out of scope of this project
		return true;
	}

	public boolean isEnabled() {
		// out of scope of this project
		return true;
	}
}
