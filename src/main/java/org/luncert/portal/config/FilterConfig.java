package org.luncert.portal.config;

import org.luncert.portal.filter.GitlabFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * GitlabFilter, 这个filter应该在security的filter之后被调用
     */

    @Bean
    public GitlabFilter gitlabFilter() {
        return new GitlabFilter();
    }
    
    @Bean
	public FilterRegistrationBean<GitlabFilter> testFilterRegistration(GitlabFilter gitlabFilter) {
		FilterRegistrationBean<GitlabFilter> registration = new FilterRegistrationBean<>(gitlabFilter);
		registration.addUrlPatterns("/user/project/*", "/stdio/project/*");
        registration.setName("GitlabFilter");
        registration.setOrder(Integer.MAX_VALUE);
		return registration;
	}

}