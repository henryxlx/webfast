package com.jetwinner.webfast.kernel.service;

import com.jetwinner.spring.YmlPropertySourceFactory;
import com.jetwinner.webfast.kernel.AppUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xulixin
 */
@Service
@PropertySource(value = {"classpath:test/buildin-user.yml"}, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "data")
public class TestAppUserServiceWithYmlFile {

    private List<AppUser> users = new ArrayList<>();

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

    public AppUser getByUsername(String username) {
        AppUser appUser = new AppUser();
        if (users != null) {
            for (AppUser user : users) {
                if (username.equals(user.getUsername())) {
                    appUser.setUsername(user.getUsername());
                    appUser.setPassword(user.getPassword());
                    appUser.setSalt(user.getSalt());
                    appUser.setLocked(0);
                    break;
                }
            }
        }

        return appUser;
    }

}
