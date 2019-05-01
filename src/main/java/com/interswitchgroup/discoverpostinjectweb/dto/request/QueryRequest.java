package com.interswitchgroup.discoverpostinjectweb.dto.request;

import com.interswitchgroup.discoverpostinjectweb.dto.params.PageParams;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.util.Date;

public class QueryRequest extends PageParams {

    private String searchKey;

    public QueryRequest() {
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
