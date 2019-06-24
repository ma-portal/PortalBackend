package org.luncert.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.luncert.portal.exceptions.GitlabServiceError;
import org.luncert.portal.service.GitlabService;
import org.luncert.portal.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/gitlab")
public class GitlabController {

    @Autowired
    private GitlabService gitlabService;

    @GetMapping("/redirect")
    public ResponseEntity<Object> redirect(
        @RequestParam("code") String code,
        @RequestParam("state") String state,
        HttpServletResponse response) throws IOException {
        try {
            gitlabService.updateAuthDetails(code);
            String requestUri = gitlabService.getCachedResource(UUID.fromString(state));
            response.sendRedirect(requestUri);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (GitlabServiceError e) {
            return new ResponseEntity<>(NormalUtil.throwableToString(e),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}