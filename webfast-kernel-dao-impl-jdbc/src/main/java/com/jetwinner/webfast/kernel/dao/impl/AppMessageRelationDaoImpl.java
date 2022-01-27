package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppMessageRelationDao;
import com.jetwinner.webfast.kernel.model.AppModelMessageRelation;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppMessageRelationDaoImpl extends FastJdbcDaoSupport implements AppMessageRelationDao {

    private static final String TABLE_NAME = "app_message_relation";

    @Override
    public int addRelation(Map<String, Object> fields) {
        return insertReturnKey(TABLE_NAME, fields).intValue();
    }

    @Override
    public int deleteConversationMessage(Integer conversationId, Integer messageId) {
        String sql = String.format("DELETE FROM %s WHERE conversationId = ? AND messageId = ?", TABLE_NAME);
        return getJdbcTemplate().update(sql, conversationId, messageId);
    }

    @Override
    public int deleteRelationByConversationId(Integer conversationId) {
        String sql = String.format("DELETE FROM %s WHERE conversationId = ?", TABLE_NAME);
        return getJdbcTemplate().update(sql, conversationId);
    }

    @Override
    public int getRelationCountByConversationId(Integer conversationId) {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE  conversationId = ?", TABLE_NAME);
        return getJdbcTemplate().queryForObject(sql, Integer.class, conversationId);
    }

    @Override
    public AppModelMessageRelation getRelationByConversationIdAndMessageId(Integer conversationId, Integer messageId) {
        String sql = String.format("SELECT * FROM %s WHERE conversationId = ? AND messageId = ?", TABLE_NAME);
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(), conversationId, messageId);
    }

    @Override
    public int updateRelationIsReadByConversationId(Integer conversationId, Map<String, Object> fields) {
        return updateMap(TABLE_NAME, fields, "conversationId", conversationId);
    }

    @Override
    public List<AppModelMessageRelation> findRelationsByConversationId(Integer conversationId, int start, int limit) {
        String sql = String.format("SELECT * FROM %s WHERE conversationId = ? ORDER BY messageId DESC LIMIT %d, %d",
                TABLE_NAME, start, limit);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(), conversationId);
    }
}
