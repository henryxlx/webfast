package com.jetwinner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonUtilTest {

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void jsonDecodeMap() throws JsonProcessingException {
        String s = "{\"choices\":[\"aaaaaaaaaaaaaaaaaaaaa\",\"aaaaaaaaaaaaaaaaaaaaa\",\"aaaaaaaaaaaaaaaaaaa\",\"aaaaaaaaaaaaaaaaaaaaa\"]}";
        Map<String, Object> map = JsonUtil.jsonDecodeMap(s);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("choices"));
        Object objForList = map.get("choices");
        assertTrue(objForList instanceof List);
        List list = (List) objForList;
        assertEquals(4, list.size());
        String expectedJson = "{\"name\":\"aLang\",\"age\":27,\"skillList\":[\"java\",\"c++\"]}";
        Map<String, Object> employeeMap = objectMapper.readValue(expectedJson, new TypeReference<Map>() {
        });
        assertEquals(employeeMap.get("name"), "aLang");
    }
}