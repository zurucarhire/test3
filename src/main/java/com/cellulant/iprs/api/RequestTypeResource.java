package com.cellulant.iprs.api;

import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.service.IRequestTypeService;
import com.cellulant.iprs.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/requesttype")
@Slf4j
public class RequestTypeResource {
    private final IRequestTypeService requestTypeService;

    @GetMapping("/findall")
    public ResponseEntity<List<RequestType>> findAll(){

        return ResponseEntity.ok().body(requestTypeService.findAll());
    }
}
