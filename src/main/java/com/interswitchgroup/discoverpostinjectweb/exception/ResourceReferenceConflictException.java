package com.interswitchgroup.discoverpostinjectweb.exception;

//import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceReferenceConflictException extends BaseException {

    private String resource;
    private String resourceId;

    public ResourceReferenceConflictException(String resource, String resourceId) {
        super();
        this.resource = resource;
        this.resourceId = String.format("id [%s]", resourceId);
    }

    public ResourceReferenceConflictException(String resource, Object resourceId, String resourceIdName) {
        super();
        this.resource = resource;
        this.resourceId = String.format("%s [%s]", resourceIdName, resourceId);
    }

//    public ResourceReferenceConflictException(String resource, Map<String, Object> parameters) {
//        super();
//        this.resource = resource;
//        List<String> params = parameters.entrySet().stream()
//                .map(m -> String.format("%s [%s]", m.getKey(), m.getValue()))
//                .collect(Collectors.toList());
//        this.resourceId = StringUtils.join(params, ", ");
//    }

    public String getResource() {
        return resource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMessageCode() {
        return "resource.reference.conflict";
    }

    public Object[] getMessageArguments() {
        return new Object[]{resource, resourceId};
    }

    @Override
    public String getMessage() {
        return String.format("%s with %s cannot be deleted because it is referenced by other resources", resource, resourceId);
    }
}
