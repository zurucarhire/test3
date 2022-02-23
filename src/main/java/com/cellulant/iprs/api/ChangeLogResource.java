package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.ChangeLog;
import com.cellulant.iprs.service.IChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/changelog")
@Slf4j
public class ChangeLogResource {
    private final IChangeLogService changeLogService;

    @GetMapping("/findall")
    public ResponseEntity<List<ChangeLog>> findAll(){
        log.info("findAllRoles");
        return ResponseEntity.ok().body(changeLogService.findAll());
    }
}
