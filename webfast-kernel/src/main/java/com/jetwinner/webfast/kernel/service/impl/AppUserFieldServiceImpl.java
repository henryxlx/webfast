package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.dao.AppUserFieldDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppUserFieldService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xulixin
 */
@Service
public class AppUserFieldServiceImpl implements AppUserFieldService {

    private final AppUserFieldDao userFieldDao;
    private final AppUserService userService;

    public AppUserFieldServiceImpl(AppUserFieldDao userFieldDao, AppUserService userService) {
        this.userFieldDao = userFieldDao;
        this.userService = userService;
    }

    @Override
    public List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled() {
        return userFieldDao.getAllFieldsOrderBySeqAndEnabled();
    }

    @Override
    public int searchFieldCount(Map<String, Object> condition) {
        return userFieldDao.searchFieldCount(condition);
    }

    @Override
    public List<Map<String, Object>> getAllFieldsOrderBySeq() {
        return userFieldDao.getAllFieldsOrderBySeq();
    }

    @Override
    public int addUserField(Map<String, Object> field) {
        if (EasyStringUtil.isBlank(field.get("field_title"))) {
            throw new RuntimeGoingException("字段名称不能为空！");
        }

        if (EasyStringUtil.isBlank(field.get("field_seq"))) {
            throw new RuntimeGoingException("字段排序不能为空！");
        }

        if (!EasyStringUtil.isNumeric(String.valueOf(field.get("field_seq")))) {
            throw new RuntimeGoingException("字段排序只能为数字！");
        }

        Optional<String> fieldName = checkType(String.valueOf(field.get("field_type")));
        if (!fieldName.isPresent()) {
            throw new RuntimeGoingException("字段类型是错误的！");
        }

        field.put("fieldName", fieldName.get());
        field.put("title", field.get("field_title"));
        field.put("seq", field.get("field_seq"));
        field.put("enabled", 0);
        if (field.containsKey("field_enabled")) {
            field.put("enabled", 1);
        }
        field.put("createdTime", System.currentTimeMillis());

        return userFieldDao.addField(field);
    }

    @Override
    public Map<String, Object> getField(Object id) {
        return userFieldDao.getField(id);
    }

    @Override
    public void dropField(Object id) {
        Map<String, Object> field = userFieldDao.getField(id);
        userService.dropFieldData(field.get("fieldName"));
        userFieldDao.deleteField(id);
    }

    private Optional<String> checkType(String type) {
        Optional<String> fieldName = Optional.empty();
        if ("text".equals(type)) {
            for (int i = 1; i < 11; i++) {
                Map<String, Object> field = userFieldDao.getFieldByFieldName("textField" + i);
                if (MapUtil.isEmpty(field)) {
                    fieldName = Optional.of("textField" + i);
                    break;
                }
            }
        }
        if ("int".equals(type)) {
            for (int i = 1; i < 6; i++) {
                Map<String, Object> field = userFieldDao.getFieldByFieldName("intField" + i);
                if (MapUtil.isEmpty(field)) {
                    fieldName = Optional.of("intField" + i);
                    break;
                }
            }
        }
        if ("date".equals(type)) {
            for (int i = 1; i < 6; i++) {
                Map<String, Object> field = userFieldDao.getFieldByFieldName("dateField" + i);
                if (MapUtil.isEmpty(field)) {
                    fieldName = Optional.of("dateField" + i);
                    break;
                }
            }
        }
        if ("float".equals(type)) {
            for (int i = 1; i < 6; i++) {
                Map<String, Object> field = userFieldDao.getFieldByFieldName("floatField" + i);
                if (MapUtil.isEmpty(field)) {
                    fieldName = Optional.of("floatField" + i);
                    break;
                }
            }
        }
        if ("varchar".equals(type)) {
            for (int i = 1; i < 11; i++) {
                Map<String, Object> field = userFieldDao.getFieldByFieldName("varcharField" + i);
                if (MapUtil.isEmpty(field)) {
                    fieldName = Optional.of("varcharField" + i);
                    break;
                }
            }
        }
        return fieldName;
    }
}
