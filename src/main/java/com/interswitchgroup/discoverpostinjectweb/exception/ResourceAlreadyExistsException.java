package com.interswitchgroup.discoverpostinjectweb.exception;

//import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ResourceAlreadyExistsException extends BaseException {

    private String resource;
    private String resourceId;

    public ResourceAlreadyExistsException(String resource, String resourceId) {
        super();
        this.resource = resource;
        this.resourceId = resourceId;
    }

//    public ResourceAlreadyExistsException(String resource, List<String> params) {
//        super();
//        this.resource = resource;
//        this.resourceId = StringUtils.join(params, ",");
//    }

    public String getResource() {
        return resource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMessageCode() {
        return "resource.already.exists";
    }

    public Object[] getMessageArguments() {
        return new Object[]{resource, resourceId};
    }

    @Override
    public String getMessage() {
        return String.format("%s with matching %s already exists", resource, resourceId);
    }
}
