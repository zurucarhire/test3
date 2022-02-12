package com.cellulant.iprs.api;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.model.ExpiryPeriod;
import com.cellulant.iprs.service.IExpiryCheckPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final IExpiryCheckPeriod expiryCheckPeriod;

    @PostMapping("/create/{createdBy}")
    public ResponseEntity<ExpiryPeriod> create(@PathVariable(value = "createdBy") long createdBy,
                                         @RequestBody ExpiryPeriod expiryPeriod) {
        log.info("createExpiryPeriod {} {}", createdBy, expiryPeriod);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/client/expiry").toUriString());
        return ResponseEntity.created(uri).body(expiryCheckPeriod.create(createdBy, expiryPeriod));
    }

    @PutMapping(value = "/update/{expiryId}/{period}")
    public ResponseEntity<ExpiryPeriod> update(@PathVariable(value = "expiryId") long expiryId,
                                               @PathVariable(value = "period") int period) {
        log.info("update {} {}",expiryId, period);
        return new ResponseEntity<ExpiryPeriod>(expiryCheckPeriod.update(expiryId, period), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ExpiryPeriod>> findAll(){
        log.info("findAllClients");
        return ResponseEntity.ok().body(expiryCheckPeriod.findAll());
    }


}
