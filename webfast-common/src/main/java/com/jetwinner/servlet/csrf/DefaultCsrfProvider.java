package com.jetwinner.servlet.csrf;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xulixin
 */
public class DefaultCsrfProvider implements CsrfProvider {

    /**
     * A secret value used for generating the CSRF token
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

    @Override
    public String generateCsrfToken(String intention) {
        return sha1(this.secret + intention + getSessionId());
    }

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
    protected String getSessionId() {
        return "sessionId";
    }

    /**
     * This replicates the PHP sha1 so that we can authenticate the same users.
     */
    public static String sha1(String s) {
        try {
            return byteArray2Hex(MessageDigest.getInstance("SHA1").digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            //
        }
        return s;
    }

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String byteArray2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            sb.append(HEX[(b & 0xF0) >> 4]);
            sb.append(HEX[b & 0x0F]);
        }
        return sb.toString();
    }
}
