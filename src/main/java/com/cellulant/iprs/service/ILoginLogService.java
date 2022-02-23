package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.LoginLog;

import java.util.List;

public interface ILoginLogService {
    LoginLog create(LoginLog loginLog);
    List<LoginLog> findAll();
}
