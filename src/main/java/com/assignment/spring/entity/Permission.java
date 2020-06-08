package com.assignment.spring.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public enum Permission implements GrantedAuthority {
	VIEW_WEATHER_LOGS, VIEW_USERS, EDIT_USERS;

	public String getAuthority() {
		return name();
	}

	/**
	 * {@link Converter} from collection of Permissions to String.<br>
	 * This is not mapped to IDs or some other more relational/normalized format, since R2DBC doesn't support object relations for now
	 */
	@WritingConverter
	public static class PermissionsWriteConverter implements Converter<Collection<Permission>, String> {

		public String convert(final Collection<Permission> source) {
			// source is never null as per documentation

			return StringUtils.join(source, ",");
		}
	}

	@ReadingConverter
	public static class PermissionsReadConverter implements Converter<String, Set<Permission>> {

		public Set<Permission> convert(final String source) {
			// source is never null as per documentation

			// filtering out empty Strings is added to handle users who don't have any permissions, since R2DBC doesn't allow converters to set that as null
			// trim values to avoid any possible errors due to accidental whitespace
			return Arrays.stream(source.split(",")).filter(value -> !value.isEmpty()).map(String::trim).map(Permission::valueOf).collect(Collectors.toSet());
		}
	}
}
