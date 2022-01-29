package com.cellulant.iprs.service;

import com.cellulant.iprs.model.RequestType;

import java.util.List;

public interface IRequestTypeService {
    List<RequestType> findAll();
}
