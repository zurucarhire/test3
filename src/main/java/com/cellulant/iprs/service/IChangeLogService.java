package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ChangeLog;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface IChangeLogService {
   // DataTablesOutput<ChangeLog> findAll(DataTablesInput var1);
   ChangeLog create(Long createdBy, String narration);
   List<ChangeLog> findAll();
}
