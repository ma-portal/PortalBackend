package org.luncert.portal.exceptions;

public class GithubServiceError extends Exception {

    private static final long serialVersionUID = 1L;

    public GithubServiceError() {
    }

    public GithubServiceError(String message) {
        super(message);
    }

    public GithubServiceError(Throwable t) {
        super(t);
    }

}