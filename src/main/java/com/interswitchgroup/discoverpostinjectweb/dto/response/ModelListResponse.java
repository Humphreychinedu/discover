package com.interswitchgroup.discoverpostinjectweb.dto.response;

import java.util.List;

public class ModelListResponse<T> extends BaseResponse{

    private long count;
    private List<T> modelList;

    public ModelListResponse(String status, String message, long count, List<T> modelList) {
        super(status, message );
        this.count = count;
        this.modelList = modelList;
    }

    public ModelListResponse( long count, List<T> modelList) {
        this.count = count;
        this.modelList = modelList;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getModelList() {
        return modelList;
    }

    public void setModelList(List<T> modelList) {
        this.modelList = modelList;
    }
}
