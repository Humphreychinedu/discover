package com.interswitchgroup.discoverpostinjectweb.dao;

import com.interswitchgroup.discoverpostinjectweb.dto.params.SearchParams;
import com.interswitchgroup.discoverpostinjectweb.model.Page;

import java.util.List;

public interface BaseDao<T> {
    public T create(T model);
    public Page<T> findAll(int pageNum, int pageSize, String searchKey);
    public List<T> findAll();
    public Page<T> getAll(SearchParams params);
}
