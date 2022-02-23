package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.LoginLog;
import com.cellulant.iprs.repository.LoginLogRepository;
import com.cellulant.iprs.service.ILoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/loginlog")
@Slf4j
public class LoginLogResource {
    private final ILoginLogService loginLogService;
    private final LoginLogRepository loginLogRepository;

    @GetMapping("/findall")
    public ResponseEntity<List<LoginLog>> findAll(){
        log.info("findAllRoles");
        return ResponseEntity.ok().body(loginLogService.findAll());
    }
}
