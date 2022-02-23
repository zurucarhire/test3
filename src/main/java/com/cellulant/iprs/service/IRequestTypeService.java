package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.RequestType;

import java.util.List;

public interface IRequestTypeService {
    RequestType create(long createdBy, RequestType requestType);
    RequestType update(long requestTypeId, long updatedBy, RequestType requestType);
    Long delete(long requestTypeId, long updatedBy);
    List<RequestType> findAllActive();
    List<RequestType> findAll();
}
