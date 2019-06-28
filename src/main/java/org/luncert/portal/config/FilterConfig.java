package org.luncert.portal.config;

import org.luncert.portal.filter.GithubFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * GithubFilter, 这个filter应该在security的filter之后被调用
     */

    @Bean
    public GithubFilter githubFilter() {
        return new GithubFilter();
    }
    
    @Bean
	public FilterRegistrationBean<GithubFilter> testFilterRegistration(GithubFilter githubFilter) {
		FilterRegistrationBean<GithubFilter> registration = new FilterRegistrationBean<>(githubFilter);
		registration.addUrlPatterns("/user/project/*", "/stdio/project/*");
        registration.setName("GithubFilter");
        registration.setOrder(Integer.MAX_VALUE);
		return registration;
	}

}