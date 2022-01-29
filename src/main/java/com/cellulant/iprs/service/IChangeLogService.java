package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ChangeLog;

import java.util.List;

public interface IChangeLogService {
    List<ChangeLog> findAll();
}
