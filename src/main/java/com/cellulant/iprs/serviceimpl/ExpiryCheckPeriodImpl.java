package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.ExpiryPeriod;
import com.cellulant.iprs.repository.ExpiryPeriodRepository;
import com.cellulant.iprs.service.IExpiryCheckPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpiryCheckPeriodImpl implements IExpiryCheckPeriod {

    private final ExpiryPeriodRepository expiryPeriodRepository;

    @Override
    public List<ExpiryPeriod> findAll() {
        return expiryPeriodRepository.findAll();
    }

    @Override
    public ExpiryPeriod create(long createdBy, ExpiryPeriod expiryPeriod) {
        expiryPeriod.setActive(1);
        return expiryPeriodRepository.save(expiryPeriod);
    }

    @Override
    public ExpiryPeriod update(long id, int period) {
        ExpiryPeriod expiryPeriod = expiryPeriodRepository.findByExpiryID(id).
                orElseThrow(() -> new ResourceNotFoundException("expiry id not found " + id));
        expiryPeriod.setExpiryPeriod(period);
        return expiryPeriodRepository.save(expiryPeriod);
    }

    @Override
    public ExpiryPeriod delete(long id) {
        ExpiryPeriod expiryPeriod = expiryPeriodRepository.findByExpiryID(id).
                orElseThrow(() -> new ResourceNotFoundException("expiry id not found " + id));
        expiryPeriod.setActive(0);
        return expiryPeriodRepository.save(expiryPeriod);
    }
}
