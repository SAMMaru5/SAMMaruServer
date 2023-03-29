package com.sammaru5.sammaru.config;

import com.sammaru5.sammaru.config.jwt.JwtAccessDeniedHandler;
import com.sammaru5.sammaru.config.jwt.JwtAuthenticationEntryPoint;
import com.sammaru5.sammaru.config.security.CustomUserDetailsService;
import com.sammaru5.sammaru.config.jwt.JwtAuthenticationFilter;
import com.sammaru5.sammaru.config.jwt.TokenProvider;
import com.sammaru5.sammaru.config.security.UnauthorizedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final TokenProvider jwtTokenProvider;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER) //AuthenticationManager를 외부에서 사용하기 위해 bean으로 등록
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**","/swagger/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/files/**")
                .permitAll()
                .antMatchers("/auth/signup").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/reissue").permitAll()
                .antMatchers("/auth/tempPassword").permitAll()
                .antMatchers("/auth/send").permitAll()
                .antMatchers("/auth/verify").permitAll()
                .antMatchers("/no-permit/**").permitAll()
                .anyRequest().authenticated();

        //Custom JWT Security Filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
