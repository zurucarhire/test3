package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ExpiryCheck;

import java.util.List;

public interface IExpiryCheckService {
    List<ExpiryCheck> findAll();
    ExpiryCheck update(long id, int period);
    ExpiryCheck delete(long id);
}
