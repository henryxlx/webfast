/*
 * Copyright (c) 2009--2019 ChangChun Genghis Technology Co.Ltd. All rights reserved.
 * ChangChun Genghis copyrights this specification. No part of this specification may be reproduced
 * in any form or means, without the prior written consent of ChangChun Genghis.
 *
 */

package com.jetwinner.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author xulixin
 */
public final class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
        // reserved.
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil objectToString error: " + e.getMessage());
        }
        return "";
    }

    public static <T> T stringToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("JsonUtil stringToObject error: " + e.getMessage());
        }
        return null;
    }

    public static <T> T jsonDecode(Object json, Class<T> clazz) {
        try {
            return objectMapper.readValue(String.valueOf(json), clazz);
        } catch (IOException e) {
            log.error("JsonUtil jsonDecode error: " + e.getMessage());
        }
        return null;
    }

    public static String jsonDecode(Object jsonStr, boolean assoc) {
        return String.valueOf(jsonStr);
    }

    public static Map<String, Object> jsonDecodeMap(Object json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(json, new TypeReference<Map<String, Object>>() {
        });
    }
}
