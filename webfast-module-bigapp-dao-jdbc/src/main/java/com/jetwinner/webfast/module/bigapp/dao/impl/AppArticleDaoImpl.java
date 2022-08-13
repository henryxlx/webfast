package com.jetwinner.webfast.module.bigapp.dao.impl;

import com.jetwinner.toolbag.MapKitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.bigapp.dao.AppArticleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
    public List<Map<String, Object>> searchArticles(Map<String, Object> conditions, OrderBy orderBy,
                                                    Integer start, Integer limit) {

        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderBy)
                .setFirstResult(start)
                .setMaxResults(limit);

        return getNamedParameterJdbcTemplate().queryForList(builder.getSQL(), conditions);
    }

    @Override
    public Map<String, Object> addArticle(Map<String, Object> article) {
        Number key = insertMapReturnKey("big_app_article", article);
        return getArticle(key.intValue());
    }

    @Override
    public Map<String, Object> getArticle(Object id) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_article WHERE id = ?", id)
                .stream().findFirst().orElse(Collections.emptyMap());
    }

    @Override
    public Map<String, Object> getArticlePrevious(Object categoryId, Long createdTime) {
        final String sql = "SELECT * FROM big_app_article WHERE `categoryId` = ? AND createdTime < ? ORDER BY `createdTime` DESC LIMIT 1";
        return getJdbcTemplate().queryForList(sql, categoryId, createdTime)
                .stream().findFirst().orElse(Collections.emptyMap());
    }

    @Override
    public Map<String, Object> getArticleNext(Object categoryId, Long createdTime) {
        final String sql = "SELECT * FROM big_app_article WHERE  `categoryId` = ? AND createdTime > ? ORDER BY `createdTime` ASC LIMIT 1";
        return getJdbcTemplate().queryForList(sql, categoryId, createdTime)
                .stream().findFirst().orElse(Collections.emptyMap());
    }

    @Override
    public int waveArticle(Object id, int diff) {
        final String sql = diff > 0 ?  "UPDATE big_app_article SET hits = hits + ? WHERE id = ? LIMIT 1" :
                "UPDATE big_app_article SET hits = hits - ? WHERE id = ? LIMIT 1";
        Integer hitValue = diff > 0 ? diff : -diff;
        return getJdbcTemplate().update(sql, hitValue, id);
    }

    @Override
    public int updateArticle(Integer id, Map<String, Object> fields) {
        fields.put("id", id);
        return updateMap("big_app_article", fields, "id");
    }

    @Override
    public int deleteArticle(Object id) {
        return getJdbcTemplate().update("DELETE FROM big_app_article WHERE id = ?", id);
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
                .from("big_app_article", "article")
                .andWhere("status = :status")
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
