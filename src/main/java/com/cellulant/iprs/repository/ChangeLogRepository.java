package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogRepository extends DataTablesRepository<ChangeLog, Long> {

}
