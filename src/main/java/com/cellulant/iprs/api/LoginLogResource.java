package com.cellulant.iprs.api;

import com.cellulant.iprs.model.LoginLog;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.service.ILoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/loginlog")
@Slf4j
public class LoginLogResource {
    private final ILoginLogService loginLogService;

    @RequestMapping(value = "/findall", method = RequestMethod.POST)
    public DataTablesOutput<LoginLog> getUsers(@Valid @RequestBody DataTablesInput input) {
        log.info("hello {}", input);
        return loginLogService.findAll(input);
    }
}
