package org.luncert.portal.exceptions;

public class NoCachedResourceError extends GithubServiceError {

    private static final long serialVersionUID = 1L;

    public NoCachedResourceError(String account) {
        super("no cached resource found for account <" + account + ">");
    }

}