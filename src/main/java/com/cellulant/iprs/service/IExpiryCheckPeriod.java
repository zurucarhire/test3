package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ExpiryPeriod;

import java.util.List;

public interface IExpiryCheckPeriod {
    List<ExpiryPeriod> findAll();
    ExpiryPeriod create(long createdBy, ExpiryPeriod expiryPeriod);
    ExpiryPeriod update(long id, int period);
    ExpiryPeriod delete(long id);
}
