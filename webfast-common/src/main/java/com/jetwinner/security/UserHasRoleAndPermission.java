package com.jetwinner.security;

import java.util.Set;

/**
 * @author xulixin
 */
public class UserHasRoleAndPermission {

    private BaseAppUser user;

    private Set<String> roles;

    private Set<String> permissions;

    public UserHasRoleAndPermission(BaseAppUser user, Set<String> roles, Set<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }

    public BaseAppUser getUser() {
        return user;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
