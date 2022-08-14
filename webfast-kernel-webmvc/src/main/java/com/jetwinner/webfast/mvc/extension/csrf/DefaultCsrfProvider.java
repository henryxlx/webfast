package com.jetwinner.webfast.mvc.extension.csrf;

import com.jetwinner.util.FastEncryptionUtil;

/**
 * @author xulixin
 */
public class DefaultCsrfProvider implements CsrfProviderDefinable {

    /**
     * A secret value used for generating the CSRF token
     *
     * @var string
     */
    protected String secret;

    /**
     * Initializes the provider with a secret value
     * <p>
     * A recommended value for the secret is a generated value with at least
     * 32 characters and mixed letters, digits and special characters.
     *
     * @param secret A secret value included in the CSRF token
     */
    public DefaultCsrfProvider(String secret) {
        this.secret = secret;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public String generateCsrfToken(String intention) {
        return FastEncryptionUtil.sha1(this.secret + intention + this.getSessionId());
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public boolean isCsrfTokenValid(String intention, String token) {
        return token != null && token.equals(generateCsrfToken(intention));
    }

    /**
     * Returns the ID of the user session.
     * <p>
     * Automatically starts the session if necessary.
     *
     * @return string The session ID
     */
    protected Object getSessionId() {
        return "unsupported";
    }
}
