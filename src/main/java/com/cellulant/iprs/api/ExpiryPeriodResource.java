package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.ExpiryPeriod;
import com.cellulant.iprs.service.IExpiryCheckPeriodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/expiryperiod")
@Slf4j
public class ExpiryPeriodResource {
    private final IExpiryCheckPeriodService expiryCheckPeriodService;

    @PostMapping("/create/{createdBy}")
    public ResponseEntity<ExpiryPeriod> create(@PathVariable(value = "createdBy") long createdBy,
                                         @RequestBody ExpiryPeriod expiryPeriod) {
        log.info("createExpiryPeriod {} {}", createdBy, expiryPeriod);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/expiryperiod/create").toUriString());
        return ResponseEntity.created(uri).body(expiryCheckPeriodService.create(createdBy, expiryPeriod));
    }

    @PutMapping(value = "/update/{expiryPeriodId}/{period}/{updatedBy}")
    public ResponseEntity<ExpiryPeriod> update(@PathVariable(value = "expiryPeriodId") long expiryPeriodId,
                                               @PathVariable(value = "period") int period,
                                               @PathVariable(value = "updatedBy") int updatedBy) {
        log.info("update {} {} {}",expiryPeriodId, period, updatedBy);
        return ResponseEntity.ok(expiryCheckPeriodService.update(expiryPeriodId, period, updatedBy));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ExpiryPeriod>> findAll(){
        log.info("findAllClients");
        return ResponseEntity.ok().body(expiryCheckPeriodService.findAll());
    }


}
