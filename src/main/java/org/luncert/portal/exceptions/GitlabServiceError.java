package org.luncert.portal.exceptions;

public class GitlabServiceError extends Exception {

    private static final long serialVersionUID = 1L;

    public GitlabServiceError() {
    }

    public GitlabServiceError(String message) {
        super(message);
    }

    public GitlabServiceError(Throwable t) {
        super(t);
    }

}