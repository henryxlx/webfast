package com.jetwinner.servlet.csrf;

import javax.servlet.http.HttpSession;

/**
 * @author xulixin
 */
public class SessionCsrfProvider extends DefaultCsrfProvider {

    private HttpSession session;

    public SessionCsrfProvider(HttpSession session, String secret) {
        super(secret);
        this.session = session;
    }

    @Override
    protected String getSessionId() {
        return session.getId();
    }
}
