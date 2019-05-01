package com.interswitchgroup.discoverpostinjectweb.dao;

import com.interswitchgroup.discoverpostinjectweb.dao.util.BeanPropertySqlParameterMapper;
import com.interswitchgroup.discoverpostinjectweb.dto.params.PageParams;
import com.interswitchgroup.discoverpostinjectweb.dto.params.SearchParams;
import com.interswitchgroup.discoverpostinjectweb.model.BaseEntity;
import com.interswitchgroup.discoverpostinjectweb.model.Page;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public abstract class AbstractBaseDao<T extends BaseEntity> implements BaseDao<T> {

    protected static final String SINGLE_RESULT = "single";
    protected static final String MULTIPLE_RESULT = "multiple";
    protected static final String RESULT_COUNT = "count";

    protected SimpleJdbcCall create, findAll, getAll;

    public abstract void setDataSource(DataSource dataSource);

    public T create(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        Map<String, Object> m = create.execute(in);
        long id = (long) m.get("id");
        model.setId(id);
        return model;
    }

    public List<T> findAll() {
        return this.findAll(0, 0).getContent();
    }

    public Page<T> findAll(int pageNum, int pageSize) throws DataAccessException {
        SqlParameterSource in = (new MapSqlParameterSource()).addValue("id", Integer.valueOf(0))
                .addValue("pageNum", Integer.valueOf(pageNum))
                .addValue("pageSize", pageSize == 0 ? null : Integer.valueOf(pageSize));
        Map<String, Object> m = findAll.execute(in);
        List<T> list = (List<T>) m.get(MULTIPLE_RESULT);

        long count = 0L;
        List<Long> counList = (List<Long>) m.get("count");
        if(counList != null && !counList.isEmpty()) {
            count = (long) counList.get(0);
        }
        return new Page<>(count, list);
    }

    public List<T> getAll() throws DataAccessException {
        return getAll(new PageParams(0, 0)).getContent();
    }

    public Page<T> getAll(SearchParams params) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource(BeanPropertySqlParameterMapper.map(params));
        Map<String, Object> m = getAll.execute(in);
        Long count = (Long) ((List) m.get(RESULT_COUNT)).get(0);
        List<T> content = (List) m.get(MULTIPLE_RESULT);
        return new Page(count, content);
    }
}
