package com.interswitchgroup.discoverpostinjectweb.dao.util;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowCountMapper implements RowMapper<Long> {

//    @Override
//    public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//        try {
//            return Long.valueOf(resultSet.getLong(1));
//        }catch (IndexOutOfBoundsException ex) {
//            return Long.valueOf(0L);
//        }
//    }

    public RowCountMapper() {
    }

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong(1);
    }
}
