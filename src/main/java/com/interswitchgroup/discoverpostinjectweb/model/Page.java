package com.interswitchgroup.discoverpostinjectweb.model;

import java.util.List;

public class Page<T> {
    private long count;
    private List<T> content;

    public Page(long count, List<T> content) {
        this.count = count;
        this.content = content;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
