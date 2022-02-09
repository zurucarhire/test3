package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.ExpiryCheck;
import com.cellulant.iprs.repository.ExpiryCheckRepository;
import com.cellulant.iprs.service.IExpiryCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpiryCheckServiceImpl implements IExpiryCheckService {

    private final ExpiryCheckRepository expiryCheckRepository;

    @Override
    public List<ExpiryCheck> findAll() {
        return expiryCheckRepository.findAll();
    }

    @Override
    public ExpiryCheck update(long id, int period) {
        ExpiryCheck expiryCheck = expiryCheckRepository.findByExpiryID(id).
                orElseThrow(() -> new ResourceNotFoundException("expiry id not found " + id));
        expiryCheck.setExpiryPeriod(period);
        return expiryCheckRepository.save(expiryCheck);
    }

    @Override
    public ExpiryCheck delete(long id) {
        ExpiryCheck expiryCheck = expiryCheckRepository.findByExpiryID(id).
                orElseThrow(() -> new ResourceNotFoundException("expiry id not found " + id));
        expiryCheck.setActive(0);
        return expiryCheckRepository.save(expiryCheck);
    }
}
