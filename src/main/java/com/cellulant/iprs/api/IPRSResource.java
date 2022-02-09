package com.cellulant.iprs.api;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.service.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/iprs")
@Slf4j
public class IPRSResource {
    private final IClientService iClientService;

    @GetMapping("/findall")
    public ResponseEntity<List<Client>> findAll(){
        log.info("findAllClients");
        return ResponseEntity.ok().body(iClientService.findAll());
    }
}
