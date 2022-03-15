package com.jetwinner.webfast.mvc.extension.csrf;

/**
 * @author xulixin
 */
public interface CsrfProviderDefinable {

    String CSRF_TOKEN_NAME_IN_FORM = "_csrf_token";
    String CSRF_INTENTION_KEY_IN_SESSION = "csrf_intention_key_in_session";

    /**
     * Generates a CSRF token for a page of your application.
     *
     * @param intention Some value that identifies the action intention
     *                          (i.e. "authenticate"). Doesn't have to be a secret value.
     */
    String generateCsrfToken(String intention);

    /**
     * Validates a CSRF token.
     *
     * @param intention The intention used when generating the CSRF token
     * @param token     The token supplied by the browser
     *
     * @return bool    Whether the token supplied by the browser is correct
     */
    boolean isCsrfTokenValid(String intention, String token);
}
