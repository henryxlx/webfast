package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.dao.AppTagDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppTagServiceImpl implements AppTagService {

    private final AppTagDao tagDao;

    public AppTagServiceImpl(AppTagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Map<String, Object>> findTagsByNames(String[] names) {
        return tagDao.findTagsByNames(names);
    }

    @Override
    public Map<String, Object> getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public void addTag(Map<String, Object> fields) {
        Map<String, Object> tag = ArrayToolkit.part(fields, "name");

        filterTagFields(tag, null);
        tag.put("createdTime", System.currentTimeMillis());

        tag = tagDao.addTag(tag);
        // logService.info("tag", "create", String.format("添加标签%s(#%d)", tag.get("name"), tag.get("id")));
    }

    @Override
    public List<Map<String, Object>> getTagByLikeName(String partOfName) {
        return tagDao.getTagByLikeName(partOfName);
    }

    private void filterTagFields(Map<String, Object> tag, String relatedTagName) {
        if (EasyStringUtil.isBlank(tag.get("name"))) {
            throw new RuntimeGoingException("标签名不能为空，添加失败！");
        }

        String exclude = relatedTagName;
        if (!isTagNameAvailable(String.valueOf(tag.get("name")), exclude)) {
            throw new RuntimeGoingException("该标签名已存在，添加失败！");
        }
    }

    public boolean isTagNameAvailable(String name, String exclude) {
        if (EasyStringUtil.isBlank(name)) {
            return false;
        }

        if (EasyStringUtil.equals(name, exclude)) {
            return true;
        }

        Map<String, Object> tag = getTagByName(name);
        return tag!= null && tag.size() > 0 ? false : true;
    }
}
