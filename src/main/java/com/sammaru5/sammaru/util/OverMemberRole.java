package com.sammaru5.sammaru.util;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OverMemberRole {
}
