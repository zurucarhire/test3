package com.cellulant.iprs.api;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.service.IChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/changelog")
@Slf4j
public class ChangeLogResource {
    private final IChangeLogService changeLogService;

    @GetMapping("/findall")
    public ResponseEntity<List<ChangeLog>> findAll(){
        log.info("findAllChangeLogs");
        return ResponseEntity.ok().body(changeLogService.findAll());
    }
}
