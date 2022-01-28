package com.jetwinner.servlet.csrf;

/**
 * @author xulixin
 */
public interface CsrfProvider {

    /**
     * Generates a CSRF token for a page of your application.
     *
     * @param intention Some value that identifies the action intention
     *                  (i.e. "authenticate"). Doesn't have to be a secret value.
     */
    String generateCsrfToken(String intention);

    /**
     * Validates a CSRF token.
     *
     * @param intention The intention used when generating the CSRF token
     * @param token     The token supplied by the browser
     * @return bool    Whether the token supplied by the browser is correct
     */
    boolean isCsrfTokenValid(String intention, String token);
}
