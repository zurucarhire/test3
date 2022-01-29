package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.LoginLog;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends DataTablesRepository<LoginLog, Long> {
}
