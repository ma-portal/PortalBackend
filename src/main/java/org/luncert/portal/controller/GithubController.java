package org.luncert.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.luncert.portal.exceptions.GithubServiceError;
import org.luncert.portal.service.GithubService;
import org.luncert.portal.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/redirect")
    public ResponseEntity<Object> redirect(
        @RequestParam("code") String code,
        @RequestParam("state") String state,
        HttpServletResponse response) throws Exception {
        try {
            githubService.updateAccessToken(code, state);
            String requestUri = githubService.getCachedResource(UUID.fromString(state));
            response.sendRedirect(requestUri);
            return null;
        } catch (GithubServiceError e) {
            return new ResponseEntity<>(NormalUtil.throwableToString(e),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}