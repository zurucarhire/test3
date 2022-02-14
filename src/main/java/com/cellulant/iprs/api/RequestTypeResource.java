package com.cellulant.iprs.api;

import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.service.IRequestTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/requesttype")
@Slf4j
public class RequestTypeResource {
    private final IRequestTypeService requestTypeService;

    @PostMapping("/create/{createdBy}")
    public ResponseEntity<RequestType> create(@PathVariable(value = "createdBy") long createdBy,
                                              @Valid @RequestBody RequestType requestType) {
        log.info("createRequestType {}", requestType);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/requesttype/create").toUriString());
        return ResponseEntity.created(uri).body(requestTypeService.create(createdBy, requestType));
    }

    @PutMapping(value = "/update/{requestTypeId}/{updatedBy}")
    public ResponseEntity<RequestType> update(@PathVariable(value = "requestTypeId") long requestTypeId,
                                              @PathVariable(value = "updatedBy") long updatedBy,
                                              @Valid @RequestBody RequestType requestType) {
        log.info("update {} {} {}", requestTypeId, updatedBy, requestType);
        return ResponseEntity.ok(requestTypeService.update(requestTypeId, updatedBy, requestType));
    }

    @DeleteMapping(value = "/delete/{requestTypeId}/{updatedBy}")
    public ResponseEntity<Long> delete(@PathVariable(value = "requestTypeId") long requestTypeId,
                                       @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("delete {} ", requestTypeId);
        requestTypeService.delete(requestTypeId, updatedBy);
        return ResponseEntity.ok(requestTypeId);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<RequestType>> findAll(){
        return ResponseEntity.ok().body(requestTypeService.findAll());
    }

    @GetMapping("/findallactive")
    public ResponseEntity<List<RequestType>> findAllActive(){
        return ResponseEntity.ok().body(requestTypeService.findAllActive());
    }
}
