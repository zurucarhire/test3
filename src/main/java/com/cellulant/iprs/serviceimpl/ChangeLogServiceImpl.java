package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.service.IChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChangeLogServiceImpl implements IChangeLogService {

    private final ChangeLogRepository changeLogRepository;

    @Override
    public DataTablesOutput<ChangeLog> findAll(DataTablesInput var1) {
        return changeLogRepository.findAll(var1);
    }
}
