package org.luncert.portal.filter;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.luncert.portal.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class GithubFilter implements Filter {

    @Value("${github.api.authorize}")
    private String GITHUB_API_AUHORIZE;

    @Autowired
    private GithubService githubService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if ((response instanceof HttpServletResponse)
            && (request instanceof HttpServletRequest)
            && !githubService.authorized()) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rep = (HttpServletResponse) response;
            String requestUri = req.getRequestURI();
            String state = githubService.cacheRequestResource(requestUri);
            rep.sendRedirect(MessageFormat.format(GITHUB_API_AUHORIZE, state));
        } else {
            chain.doFilter(request, response);
        }
    }

}