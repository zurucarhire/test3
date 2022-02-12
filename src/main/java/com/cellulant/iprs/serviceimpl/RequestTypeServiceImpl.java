package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.repository.RequestTypeRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IRequestTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestTypeServiceImpl implements IRequestTypeService {

    private final RequestTypeRepository requestTypeRepository;
    private final ChangeLogRepository changeLogRepository;
    private final IChangeLogService changeLogService;

    @Override
    public RequestType create(long createdBy, RequestType requestType) {
        requestTypeRepository.findByRequestTypeNameIgnoreCase(requestType.getRequestTypeName()).ifPresent(s -> {
            throw new UnprocessedResourceException("Request Type exists " + s.getRequestTypeName());
        });

        RequestType requestType1 = requestTypeRepository.save(requestType);
        changeLogService.create(createdBy, "create request type " + requestType.getRequestTypeName());
        return requestType1;
    }

    @Override
    public RequestType update(long requestTypeId, long updatedBy, RequestType requestType) {
        RequestType requestType1 = requestTypeRepository.findByRequestTypeID(requestTypeId).
                orElseThrow(() -> new ResourceNotFoundException("Request Type not found"));

        if (!requestType.getRequestTypeName().equals(requestType1.getRequestTypeName())){
            requestTypeRepository.findByRequestTypeNameIgnoreCase(requestType.getRequestTypeName()).ifPresent(s -> {
                throw new UnprocessedResourceException("Request Type exists " + s.getRequestTypeName());
            });
        }

        requestType1.setRequestTypeName(requestType.getRequestTypeName());
        requestType1.setActive(requestType.getActive());
        requestType1.setUpdatedBy(updatedBy);
        //client1.setDateModified(new Date());

        RequestType updatedRequestType = requestTypeRepository.save(requestType1);
        changeLogService.create(updatedBy, "update request type " + requestType.getRequestTypeName());
        return updatedRequestType;
    }

    @Override
    public Long delete(long requestTypeId, long updatedBy) {
        RequestType requestType1 = requestTypeRepository.findByRequestTypeID(requestTypeId).
                orElseThrow(() -> new ResourceNotFoundException("Request Type not found"));
        requestTypeRepository.deleteByRequestTypeID(requestTypeId);
        changeLogService.create(updatedBy, "delete request type " + requestType1.getRequestTypeName());
        return requestTypeId;
    }

    @Override
    public List<RequestType> findAll() {
        return requestTypeRepository.findAll();
    }

    @Override
    public List<RequestType> findAllActiveRequestTypes() {
        return requestTypeRepository.findAllActiveRequestTypes();
    }
}
