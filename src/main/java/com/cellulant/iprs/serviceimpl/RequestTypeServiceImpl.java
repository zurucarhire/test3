package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.repository.RequestTypeRepository;
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

    @Override
    public List<RequestType> findAll() {
        return requestTypeRepository.findAll();
    }
}
