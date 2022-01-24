package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.spring.YmlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xulixin
 */
@Service
@PropertySource(value = {"classpath:test/buildin-user.yml"}, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "data")
public class TestAppUserServiceWithYmlFile {

    private List<BaseAppUser> users = new ArrayList<>();

    public List<BaseAppUser> getUsers() {
        return users;
    }

    public void setUsers(List<BaseAppUser> users) {
        this.users = users;
    }

    public BaseAppUser getByUsername(String username) {
        BaseAppUser appUser = new BaseAppUser();
        if (users != null) {
            for (BaseAppUser user : users) {
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
