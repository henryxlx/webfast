package com.jetwinner.webfast.kernel.service;

/**
 * @author xulixin
 */
public interface AppNotificationService {

    void notify(Integer userId, String type, String content);
}
