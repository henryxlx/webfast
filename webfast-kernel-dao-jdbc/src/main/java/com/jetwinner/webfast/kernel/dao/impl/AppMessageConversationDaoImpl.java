package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppMessageConversationDao;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppMessageConversationDaoImpl extends FastJdbcDaoSupport implements AppMessageConversationDao {

    private static final String TABLE_NAME = "app_message_conversation";

    @Override
    public AppModelMessageConversation getConversation(Integer id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", TABLE_NAME);
        return queryForObject(sql, new BeanPropertyRowMapper<>(AppModelMessageConversation.class), id);
    }

    @Override
    public Integer getConversationCountByToId(Integer userId) {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE toId = ?", TABLE_NAME);
        return getJdbcTemplate().queryForObject(sql, Integer.class, userId);
    }

    @Override
    public List<AppModelMessageConversation> findConversationsByToId(Integer toUserId, int start, int limit) {
        String sql = String.format("SELECT * FROM %s WHERE toId = ? ORDER BY latestMessageTime DESC LIMIT %d, %d",
                TABLE_NAME, start, limit);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(AppModelMessageConversation.class), toUserId);
    }

    @Override
    public AppModelMessageConversation getConversationByFromIdAndToId(Integer fromId, Integer toId) {
        String sql = String.format("SELECT * FROM %s WHERE fromId = ? AND toId = ?", TABLE_NAME);
        return queryForObject(sql, new BeanPropertyRowMapper<>(AppModelMessageConversation.class)
                , fromId, toId);
    }

    @Override
    public int updateConversation(Integer id, Map<String, Object> fields) {
        return updateMap(TABLE_NAME, fields, "id", id);
    }

    @Override
    public AppModelMessageConversation addConversation(Map<String, Object> fields) {
        int id = insertMapReturnKey(TABLE_NAME, fields).intValue();
        return getConversation(id);
    }

    @Override
    public int deleteConversation(Integer id) {
        return getJdbcTemplate().update("DELETE FROM app_message_conversation WHERE id = ?", id);
    }
}
