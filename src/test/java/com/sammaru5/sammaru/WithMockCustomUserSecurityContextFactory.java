package com.sammaru5.sammaru;

import com.sammaru5.sammaru.config.security.UserDetail;
import com.sammaru5.sammaru.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetail principal = new UserDetail(
                User.builder()
                        .id(Long.parseLong(customUser.userId()))
                        .role(customUser.role())
                        .build());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customUser.role().name()));

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        context.setAuthentication(auth);
        return context;
    }
}