package com.interswitchgroup.discoverpostinjectweb.dao.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.interswitchgroup.discoverpostinjectweb.dto.params.SearchParams;

import java.util.Map;

public class BeanPropertySqlParameterMapper {

    public BeanPropertySqlParameterMapper() {
    }

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // Ignore @JsonValue on enum classes and use .name() to serialize
        // To ensure that search param can match database value
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
    }

    public static Map<String, Object> map(SearchParams params) {
        return mapper.convertValue(params, Map.class);
    }
}