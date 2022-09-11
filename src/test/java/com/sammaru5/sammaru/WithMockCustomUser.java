package com.sammaru5.sammaru;

import com.sammaru5.sammaru.domain.UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String userId() default "1";

    UserAuthority role() default UserAuthority.ROLE_MEMBER;
}