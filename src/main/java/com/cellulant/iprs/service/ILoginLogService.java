package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.LoginLog;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface ILoginLogService {
    LoginLog create(LoginLog loginLog);
    List<LoginLog> findAll();
}
