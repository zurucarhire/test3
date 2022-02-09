package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ChangeLog;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IChangeLogService {
    DataTablesOutput<ChangeLog> findAll(DataTablesInput var1);
}
