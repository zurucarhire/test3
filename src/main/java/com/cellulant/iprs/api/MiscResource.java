package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.ProductCategory;
import com.cellulant.iprs.service.IMiscService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/misc")
@Slf4j
public class MiscResource {
    private final IMiscService miscService;

    @GetMapping("/findallproductcategory")
    public ResponseEntity<List<ProductCategory>> findAll(){
        log.info("findAll");
        return ResponseEntity.ok().body(miscService.findAllProductCategories());
    }
}
