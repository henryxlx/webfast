package com.jetwinner.webfast.module.bigapp.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.module.bigapp.dao.AppTagDao;
import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppTagServiceImpl implements AppTagService {

    private final AppTagDao tagDao;
    private final AppLogService logService;

    public AppTagServiceImpl(AppTagDao tagDao, AppLogService logService) {
        this.tagDao = tagDao;
        this.logService = logService;
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
    public Map<String, Object> addTag(AppUser currentUser, Map<String, Object> fields) {
        Map<String, Object> tag = ArrayToolkit.part(fields, "name");

        filterTagFields(tag, null);
        tag.put("createdTime", System.currentTimeMillis());

        tag = tagDao.addTag(tag);
        logService.info(currentUser, "tag", "create",
                String.format("添加标签%s(#%s)", tag.get("name"), tag.get("id")));
        return tag;
    }

    @Override
    public List<Map<String, Object>> getTagByLikeName(String partOfName) {
        return tagDao.getTagByLikeName(partOfName);
    }

    @Override
    public List<Map<String, Object>> findTagsByIds(String[] ids) {
        return tagDao.findTagsByIds(ids);
    }

    @Override
    public int getAllTagCount() {
        return tagDao.findAllTagsCount();
    }

    @Override
    public List<Map<String, Object>> findAllTags(Integer start, Integer limit) {
        return tagDao.findAllTags(start, limit);
    }

    @Override
    public Map<String, Object> getTag(Integer id) {
        return tagDao.getTag(id);
    }

    @Override
    public Map<String, Object> updateTag(AppUser currentUser, Integer id, Map<String, Object> toUpdateDataMap) {
        Map<String, Object> tag = getTag(id);
        if (tag == null || tag.isEmpty()) {
            throw new RuntimeGoingException(String.format("标签(#%d)不存在，更新失败！", id));
        }

        Map<String, Object> fields = ArrayToolkit.part(toUpdateDataMap, "name");
        filterTagFields(fields, tag);

        int nums = tagDao.updateTag(id, fields);
        if (nums > 0) {
            logService.info(currentUser, "tag", "update",
                    String.format("编辑标签%s(#%d)", fields.get("name"), id));
            tag = getTag(id);
        }
        return tag;
    }

    private void filterTagFields(Map<String, Object> tag, Map<String, Object> relatedTag) {
        if (EasyStringUtil.isBlank(tag.get("name"))) {
            throw new RuntimeGoingException("标签名不能为空，添加失败！");
        }

        String exclude = relatedTag != null ? String.valueOf(relatedTag.get("name")) : null;
        if (!isTagNameAvailable(String.valueOf(tag.get("name")), exclude)) {
            throw new RuntimeGoingException("该标签名已存在，添加失败！");
        }
    }

    @Override
    public int deleteTag(AppUser currentUser, Integer id) {
        int nums = tagDao.deleteTag(id);
        if (nums > 0) {
            logService.info(currentUser, "tag", "delete", String.format("编辑标签#%d", id));
        }
        return nums;
    }

    @Override
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
