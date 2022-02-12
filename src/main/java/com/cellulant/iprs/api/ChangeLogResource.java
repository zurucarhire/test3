package com.cellulant.iprs.api;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.LoginLog;
import com.cellulant.iprs.service.IChangeLogService;
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
@RequestMapping("/api/iprs/changelog")
@Slf4j
public class ChangeLogResource {
    private final IChangeLogService changeLogService;

//    @RequestMapping(value = "/findall", method = RequestMethod.POST)
//    public DataTablesOutput<ChangeLog> getChangeLogs(@Valid @RequestBody DataTablesInput input) {
//        log.info("hello {}", input);
//        return changeLogService.findAll(input);
//    }
    @GetMapping("/findall")
    public ResponseEntity<List<ChangeLog>> findAll(){
        log.info("findAllRoles");
        return ResponseEntity.ok().body(changeLogService.findAll());
    }
}
