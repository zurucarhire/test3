package com.cellulant.iprs.api;

import com.cellulant.iprs.model.ExpiryCheck;
import com.cellulant.iprs.service.IExpiryCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/expiry")
@Slf4j
public class ExpiryCheckResource {
    private final IExpiryCheckService expiryCheckService;

    @GetMapping("/findall")
    public ResponseEntity<List<ExpiryCheck>> findAll(){
        log.info("findAllClients");
        return ResponseEntity.ok().body(expiryCheckService.findAll());
    }

    @PutMapping(value = "/update/{expiryId}/{period}")
    public ResponseEntity<ExpiryCheck> update(@PathVariable(value = "expiryId") long expiryId,
                                       @PathVariable(value = "period") int period) {
        log.info("update {} {}",expiryId, period);
        return new ResponseEntity<ExpiryCheck>(expiryCheckService.update(expiryId, period), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{expiryId}")
    public ResponseEntity<ExpiryCheck> update(@PathVariable(value = "expiryId") long expiryId) {
        log.info("delete {}",expiryId);
        return new ResponseEntity<ExpiryCheck>(expiryCheckService.delete(expiryId), HttpStatus.OK);
    }
}
