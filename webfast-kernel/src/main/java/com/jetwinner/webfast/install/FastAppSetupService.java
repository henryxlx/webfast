package com.jetwinner.webfast.install;

import com.jetwinner.security.BaseAppUser;

import java.util.Map;

public interface FastAppSetupService {

    void initAdmin(Map<String, String> params);

    BaseAppUser getUserByUsername(String username);

    void initSiteSettings(Map<String, String> params);

    void initRegisterSetting(Map<String, String> params);

    void initMailerSetting(String sitename);

    void initStorageSetting();

    default void initTag(BaseAppUser user) {};

    default void initCategory() {

    }

    void initFile();

    default void initCategory(BaseAppUser user) {

    }

    default void initPages(BaseAppUser user) {

    }

    void initNavigations(BaseAppUser user);

    void initBlocks(BaseAppUser user);

    void initThemes();

    default void initLockFile() {

    }

    default void initArticleSetting() {

    }

    default String getOtherSqlFilePath() {
        return null;
    }
}
