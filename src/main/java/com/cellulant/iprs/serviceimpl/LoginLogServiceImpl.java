package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.LoginLog;
import com.cellulant.iprs.repository.LoginLogRepository;
import com.cellulant.iprs.service.ILoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginLogServiceImpl implements ILoginLogService {

    private final LoginLogRepository loginLogRepository;

    @Override
    public LoginLog create(LoginLog loginLog) {
        return loginLogRepository.save(loginLog);
    }

    @Override
    public List<LoginLog> findAll() {
        return loginLogRepository.findAll();
    }
}
