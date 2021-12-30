package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.BaseAppUser;
import com.jetwinner.webfast.kernel.dao.AppContentDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.content.type.BaseContentType;
import com.jetwinner.webfast.kernel.service.content.type.ContentTypeFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppContentServiceImpl implements AppContentService {

    private final AppContentDao contentDao;

    public AppContentServiceImpl(AppContentDao contentDao) {
        this.contentDao = contentDao;
    }

    @Override
    public void createContent(Map<String, Object> content, BaseAppUser currentUser) {
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
    }

    @Override
    public List<Map<String, Object>> findAllContent() {
        return contentDao.findAll();
    }
}