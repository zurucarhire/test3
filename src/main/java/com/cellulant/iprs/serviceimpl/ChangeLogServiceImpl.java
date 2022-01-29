package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.service.IChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChangeLogServiceImpl implements IChangeLogService {

    private final ChangeLogRepository changeLogRepository;

    @Override
    public List<ChangeLog> findAll() {
        return (List<ChangeLog>) changeLogRepository.findAll();
    }
}
