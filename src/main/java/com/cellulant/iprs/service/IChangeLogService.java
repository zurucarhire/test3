package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.ChangeLog;

import java.util.List;

public interface IChangeLogService {
   // DataTablesOutput<ChangeLog> findAll(DataTablesInput var1);
   ChangeLog create(Long createdBy, String narration);
   List<ChangeLog> findAll();
}
