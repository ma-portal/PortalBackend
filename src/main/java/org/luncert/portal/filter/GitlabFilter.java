package org.luncert.portal.filter;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.luncert.portal.service.GitlabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 这个filter应该在security的filter之后被调用
 */
@Component
@Order(Integer.MAX_VALUE)
@WebFilter(filterName = "GitlabFilter",
    urlPatterns = {"/user/project/**", "/stdio/project/**"})
public class GitlabFilter implements Filter {

    @Value("${gitlab.auth-uri-code}")
    private String GITLAB_AUTH_CODE;

    @Autowired
    private GitlabService gitlabService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if ((response instanceof HttpServletResponse)
            && (request instanceof HttpServletRequest)
            && !gitlabService.authorized()) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rep = (HttpServletResponse) response;
            String requestUri = req.getRequestURI();
            //  req.getServerName() + ":" + req.getServerPort() + req.getServletPath();
            String state = gitlabService.cacheRequestResource(requestUri);
            rep.sendRedirect(MessageFormat.format(GITLAB_AUTH_CODE, state));
        } else {
            chain.doFilter(request, response);
        }
    }

}