package com.cellulant.iprs.api;

import com.cellulant.iprs.model.LoginLog;
import com.cellulant.iprs.repository.LoginLogRepository;
import com.cellulant.iprs.service.ILoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/loginlog")
@Slf4j
public class LoginLogResource {
    private final ILoginLogService loginLogService;
    private final LoginLogRepository loginLogRepository;

    @RequestMapping(value = "/findall", method = RequestMethod.POST)
    public DataTablesOutput<LoginLog> getUsers(@RequestBody DataTablesInput input) {
        log.info("hello55 {}", input);
        //return loginLogRepository.findAll(input);
        return loginLogService.findAll(input);
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public DataTablesOutput<LoginLog> list(@Valid DataTablesInput input) {
        return loginLogService.findAll(input);
    }
}
