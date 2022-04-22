package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.Client;
import com.cellulant.iprs.service.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/client")
@Slf4j
public class ClientResource {
    private final IClientService clientService;

    @PostMapping("/create/{createdBy}")
    public ResponseEntity<Client> create(@PathVariable(value = "createdBy") long createdBy,
                                         @RequestBody Client client) {
        log.info("createClient {}", client);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/client/create").toUriString());
        return ResponseEntity.created(uri).body(clientService.create(createdBy, client));
    }

    @PutMapping(value = "/update/{clientId}/{updatedBy}")
    public ResponseEntity<Client> update(@PathVariable(value = "clientId") long clientId,
                                         @PathVariable(value = "updatedBy") long updatedBy,
                                         @RequestBody Client client) {
        log.info("update {} {} {}", clientId, updatedBy, client);
        return ResponseEntity.ok(clientService.update(clientId, updatedBy, client));
    }

    @DeleteMapping(value = "/delete/{clientId}/{updatedBy}")
    public ResponseEntity<Long> delete(@PathVariable(value = "clientId") long clientId,
                                       @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("delete {} ", clientId);
        clientService.delete(clientId, updatedBy);
        return ResponseEntity.ok(clientId);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Client>> findAll(){
        log.info("findAllClients");
        return ResponseEntity.ok().body(clientService.findAll());
    }

    @GetMapping("/findallactive")
    public ResponseEntity<List<Client>> findAllActive(){
        log.info("findAllActiveClients");
        return ResponseEntity.ok().body(clientService.findAllActive());
    }
}
