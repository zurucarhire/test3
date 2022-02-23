package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.entity.ExpiryPeriod;
import com.cellulant.iprs.repository.ExpiryPeriodRepository;
import com.cellulant.iprs.service.IExpiryCheckPeriodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpiryCheckPeriodImplService implements IExpiryCheckPeriodService {

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
    public ExpiryPeriod update(long expiryPeriodId, int period, long updatedBy) {
        ExpiryPeriod expiryPeriod = expiryPeriodRepository.findByExpiryPeriodID(expiryPeriodId).
                orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        expiryPeriod.setExpiryPeriod(period);
        return expiryPeriodRepository.save(expiryPeriod);
    }
}
