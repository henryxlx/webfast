package com.jetwinner.webfast.mvc.extension.csrf;

import javax.servlet.http.HttpSession;

/**
 * @author xulixin
 */
public class SessionCsrfProvider extends DefaultCsrfProvider {

    public static final SessionCsrfProvider getDefaultInstance(HttpSession session) {
        return new SessionCsrfProvider(session, "ovwn07bkur4o8k08swcwkow40kw80g");
    }

    /**
     * The user session from which the session ID is returned
     * @var Session
     */
    protected HttpSession session;

    /**
     * Initializes the provider with a Session object and a secret value.
     *
     * A recommended value for the secret is a generated value with at least
     * 32 characters and mixed letters, digits and special characters.
     *
     * @param session The user session
     * @param secret  A secret value included in the CSRF token
     */
    public SessionCsrfProvider(HttpSession session, String secret) {
        super(secret);
        this.session = session;
    }

    @Override
    protected String getSessionId() {
        return this.session.getId();
    }

    @Override
    public String generateCsrfToken(String intention) {
        this.session.setAttribute(CSRF_INTENTION_KEY_IN_SESSION, intention);
        return super.generateCsrfToken(intention);
    }
}
