package com.jetwinner.webfast.kernel;

/**
 * @author xulixin
 */
public class BaseAppUser {

    public static final String MODEL_VAR_NAME = "appUser";

    private String username;

    private String password;

    private String salt;

    private Integer locked;

    private String email;

    private String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

}
