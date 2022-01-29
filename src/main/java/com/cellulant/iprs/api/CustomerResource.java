package com.cellulant.iprs.api;

import com.cellulant.iprs.model.Customer;
import com.cellulant.iprs.repository.CustomerRepository;
import com.cellulant.iprs.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/customer")
@Slf4j
public class CustomerResource {
    private final ICustomerService customerService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll(){
        log.info("findAllCustomerss");
        return ResponseEntity.ok().body(customerService.findAll());
    }

    @GetMapping("/findall2/{name}")
    public ResponseEntity<List<Customer>> findAll2(@PathVariable(value = "name") String name){
        log.info("findAllCustomerss2 {}", name);
        log.info("pass {}", passwordEncoder.encode("joe123"));
        return ResponseEntity.ok().body(customerRepository.findAllTopTenBySurName(name));
    }
}
