package com.jetwinner.webfast.module.bigapp.service.impl;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.service.content.type.BaseContentType;
import com.jetwinner.webfast.kernel.service.content.type.ContentTypeFactory;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.dao.AppContentDao;
import com.jetwinner.webfast.module.bigapp.service.AppContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppContentServiceImpl implements AppContentService {

    private final AppContentDao contentDao;
    private final AppLogService logService;

    public AppContentServiceImpl(AppContentDao contentDao, AppLogService logService) {
        this.contentDao = contentDao;
        this.logService = logService;
    }

    @Override
    public void createContent(AppUser currentUser, Map<String, Object> content) {
        if (EasyStringUtil.isBlank(content.get("type"))) {
            throw new RuntimeGoingException("参数缺失，创建内容失败！");
        }

        BaseContentType type = ContentTypeFactory.create(content.get("type").toString());
        content.put("type", type.getAlias());

        if (EasyStringUtil.isBlank(content.get("title"))) {
            throw new RuntimeGoingException("内容标题不能为空，创建内容失败！");
        }

        content.put("userId", currentUser.getId());
        content.put("createdTime", System.currentTimeMillis());

        if (content.get("publishedTime") == null) {
            content.put("publishedTime", content.get("createdTime"));
        }
        contentDao.insert(content);
        if (content.containsKey("id")) {
            logService.info(currentUser, "content", "create",
                    String.format("创建内容《(%s)》(%s)", content.get("title"), content.get("id")));
        }
    }

    @Override
    public int searchContentCount(Map<String, Object> conditions) {
        return contentDao.searchContentCount(conditions);
    }

    @Override
    public List<Map<String, Object>> searchContents(Map<String, Object> conditions, OrderBy orderBy,
                                                    Integer start, Integer limit) {

        return contentDao.searchContent(conditions, orderBy, start, limit);
    }

    @Override
    public Map<String, Object> getContent(Integer id) {
        return contentDao.getContent(id);
    }

    @Override
    public void trashContent(AppUser currentUser, Integer id) {
        contentDao.updateContent(id, new ParamMap().add("status", "trash").toMap());
        logService.info(currentUser, "content", "trash", String.format("内容#{%d}移动到回收站", id));
    }

    @Override
    public void publishContent(AppUser currentUser, Integer id) {
        contentDao.updateContent(id, new ParamMap().add("status", "published").toMap());
        logService.info(currentUser, "content", "publish", String.format("内容#{%d}发布", id));
    }

    @Override
    public Map<String, Object> getContentByAlias(String alias) {
        return contentDao.getContentByAlias(alias);
    }
}
