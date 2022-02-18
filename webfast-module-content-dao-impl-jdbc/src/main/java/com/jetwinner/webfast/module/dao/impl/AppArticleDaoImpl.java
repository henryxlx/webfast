package com.jetwinner.webfast.module.dao.impl;

import com.jetwinner.toolbag.MapKitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.dao.AppArticleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppArticleDaoImpl extends FastJdbcDaoSupport implements AppArticleDao {

    @Override
    public int searchArticlesCount(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions).select("COUNT(id)");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<Map<String, Object>> searchArticles(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                                    Integer start, Integer limit) {

        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderByBuilder)
                .setFirstResult(start)
                .setMaxResults(limit);

        return getNamedParameterJdbcTemplate().queryForList(builder.getSQL(), conditions);
    }

    @Override
    public Map<String, Object> addArticle(Map<String, Object> article) {
        Number key = insertMapReturnKey("app_article", article);
        return getJdbcTemplate().queryForList("SELECT * FROM app_article WHERE id = ?", key.intValue())
                .stream().findFirst().orElse(new HashMap<>(0));
    }

    private DynamicQueryBuilder createSearchQueryBuilder(Map<String, Object> map) {
        Map<String, Object> conditions = MapKitOnJava8.filterToNewMap(map);

        if (conditions.containsKey("property")) {
            Object key = conditions.get("property");
            if (key != null) {
                conditions.put(String.valueOf(key), 1);
            }
        }

        if (conditions.containsKey("hasPicture")) {
            if (Boolean.parseBoolean(String.valueOf(conditions.get("hasPicture"))) == true) {
                conditions.put("pictureNull", "");
                conditions.remove("hasPicture");
            }
        }

        if (EasyStringUtil.isNotBlank(conditions.get("keywords"))) {
            conditions.put("keywords", "%" + conditions.get("keywords") + "%");
        }

        DynamicQueryBuilder builder = new DynamicQueryBuilder(conditions)
                .from("app_article", "article")
                .andWhere("status = :status")
                .andWhere("categoryId = :categoryId")
                .andWhere("featured = :featured")
                .andWhere("promoted = :promoted")
                .andWhere("sticky = :sticky")
                .andWhere("title LIKE :keywords")
                .andWhere("picture != :pictureNull")
                .andWhere("categoryId = :categoryId")
                .andWhere("categoryId IN (:categoryIds)");

        return builder;
    }
}
