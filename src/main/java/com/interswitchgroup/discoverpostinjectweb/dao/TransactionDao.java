package com.interswitchgroup.discoverpostinjectweb.dao;

import com.interswitchgroup.discoverpostinjectweb.dao.util.RowCountMapper;
import com.interswitchgroup.discoverpostinjectweb.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TransactionDao extends AbstractBaseDao<Transaction> {

    private JdbcTemplate jdbcTemplate;

    private static final String COUNT = "count";
    private static final String LIST = "list";

    @Autowired
    public void setDataSource(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.jdbcTemplate.setResultsMapCaseInsensitive(true);

        create = new SimpleJdbcCall(jdbcTemplate).withProcedureName("psp_transaction").withReturnValue();
        findAll = new SimpleJdbcCall(jdbcTemplate).withProcedureName("uspGetAllMerchantUser")
                .returningResultSet(MULTIPLE_RESULT, new BeanPropertyRowMapper<>(Transaction.class))
                .returningResultSet(RESULT_COUNT, new RowCountMapper());

        getAll = new SimpleJdbcCall(jdbcTemplate).withProcedureName("uspGetAllMerchantUser")
                .returningResultSet(MULTIPLE_RESULT, BeanPropertyRowMapper.newInstance(Transaction.class))
                .returningResultSet(RESULT_COUNT, new RowCountMapper());
    }



}
