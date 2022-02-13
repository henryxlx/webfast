package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppNavigationDao;
import com.jetwinner.webfast.kernel.model.AppModelNavigation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Service
public class AppNavigationServiceImpl implements AppNavigationService {

    private final AppNavigationDao navigationDao;
    private final AppLogServiceImpl logService;

    public AppNavigationServiceImpl(AppNavigationDao navigationDao, AppLogServiceImpl logService) {
        this.navigationDao = navigationDao;
        this.logService = logService;
    }

    @Override
    public void createNavigation(AppUser currentUser, Map<String, Object> model) {
        long now = System.currentTimeMillis();
        model.put("createdTime", now);
        model.put("updateTime", now);
        model.put("sequence", navigationDao.getNavigationsCountByType(model.get("type").toString()) + 1);
        int nums = navigationDao.insert(model);
        if (nums > 0) {
            logService.info(currentUser, "info", "navigation_create", "创建导航" + model.get("name"));
        }
    }

    public int countNavigationsByType(String type) {
        return navigationDao.countNavigationsByType(type);
    }

    public List<AppModelNavigation> findAllByType(String type, int start, int count) {
        return navigationDao.findAllByType(type, start, count);
    }

    @Override
    public List<AppModelNavigation> getNavigationsListByType(String type) {
        int count = countNavigationsByType(type);
        List<AppModelNavigation> preparedList = findAllByType(type, 0, count);

        Map<Integer, List<AppModelNavigation>> navigations = new HashMap<>();
        for (AppModelNavigation nav : preparedList) {
            if (!navigations.containsKey(nav.getParentId())) {
                navigations.put(nav.getParentId(), new ArrayList<>());
            }
            navigations.get(nav.getParentId()).add(nav);
        }

        List<AppModelNavigation> tree = new ArrayList<>();
        makeNavigationTreeList(tree, navigations, 0);
        return tree;
    }

    @Override
    public List<AppModelNavigation> findNavigationsByType(String type, int start, int limit) {
        return navigationDao.findAllByType(type, start, limit);
    }

    @Override
    public int deleteNavigation(Integer id) {
        return navigationDao.deleteById(id) + navigationDao.deleteByParentId(id);
    }

    @Override
    public AppModelNavigation getNavigationById(Integer id) {
        return navigationDao.getById(id);
    }

    @Override
    public int updateNavigation(AppUser currentUser, Integer id, Map<String, Object> fields) {
        fields.put("id", id);
        fields.put("updateTime", System.currentTimeMillis());
        int nums = navigationDao.updateNavigation(fields);
        if (nums > 0) {
            logService.info(currentUser, "info", "navigation_update", "编辑导航#" + id, fields);
        }
        return nums;
    }

    @Override
    public List<AppModelNavigation> getNavigationsTreeByType(String type) {
        int count = countNavigationsByType(type);
        List<AppModelNavigation> navigationList = findNavigationsByType(type, 0, count);
        Map<Integer, AppModelNavigation> map = navigationList.stream()
                .collect(Collectors.toMap(AppModelNavigation::getId, Function.identity()));

        List<AppModelNavigation> rootList = new ArrayList<>();
        for (AppModelNavigation nav : navigationList) {
            if (nav.getParentId() > 0) {
                if (map.get(nav.getParentId()).getChildren() == null) {
                    map.get(nav.getParentId()).setChildren(new ArrayList<>());
                }

                if (nav.getIsOpen() > 0) {
                    map.get(nav.getParentId()).getChildren().add(map.get(nav.getId()));
                    map.remove(nav.getId());
                }
            } else {
                rootList.add(map.get(nav.getId()));
            }

        }
        return rootList;
    }

    private void makeNavigationTreeList(List<AppModelNavigation> tree,
                                        Map<Integer, List<AppModelNavigation>> navigations, int parentId) {

        List<AppModelNavigation> subNavs = navigations.get(parentId);
        if (subNavs != null) {
            for (AppModelNavigation nav : subNavs) {
                tree.add(nav);
                makeNavigationTreeList(tree, navigations, nav.getId());
            }
        }
    }
}
