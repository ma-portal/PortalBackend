package org.luncert.portal.config;

import org.luncert.portal.component.SecurityAuthFailureHandler;
import org.luncert.portal.component.SecurityAuthSuccessHandler;
import org.luncert.portal.component.SecuritySignoutSuccessHandler;
import org.luncert.portal.component.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private SecurityUserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
    
    @Autowired
    private SecurityAuthSuccessHandler authSuccessHandler;
    
    @Autowired
    private SecurityAuthFailureHandler authFailureHandler;

    @Autowired
    private SecuritySignoutSuccessHandler signoutSuccessHandler;

    @Override
	protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/user/avatar/*", "/static-resource/*").permitAll()
            .anyRequest().authenticated()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .and().csrf().disable().authorizeRequests()
            .and()
                .formLogin()
                .loginProcessingUrl("/user/signin")
                .usernameParameter("account")
                .passwordParameter("password")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
            .and()
                .logout()
                .logoutUrl("/user/signout")
                // .logoutSuccessUrl(logoutSuccessUrl)
                .logoutSuccessHandler(signoutSuccessHandler)
                ;
        
        // https://blog.csdn.net/u013435893/article/details/79704970
        // http.sessionManagement();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}