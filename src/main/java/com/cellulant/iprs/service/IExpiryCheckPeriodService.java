package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ExpiryPeriod;

import java.util.List;

public interface IExpiryCheckPeriodService {
    List<ExpiryPeriod> findAll();
    ExpiryPeriod create(long createdBy, ExpiryPeriod expiryPeriod);
    ExpiryPeriod update(long expiryPeriodId, int period, long updatedBy);
}
