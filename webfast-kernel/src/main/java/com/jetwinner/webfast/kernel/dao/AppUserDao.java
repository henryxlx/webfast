package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.AppUser;

/**
 * @author xulixin
 */
public interface AppUserDao {

    AppUser getByUsername(String username);
}
