package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.ChangeLog;
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
    public ChangeLog create(Long createdBy, String narration) {
        ChangeLog changeLog = ChangeLog.builder()
                .createdBy(createdBy)
                .narration(narration)
                .build();

        return changeLogRepository.save(changeLog);
    }

    @Override
    public List<ChangeLog> findAll() {
        return changeLogRepository.findAll();
    }
}
