package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.NewsLetter;
import com.cellulant.iprs.entity.Product;
import com.cellulant.iprs.service.IMerchantService;
import com.cellulant.iprs.service.INewsLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/newsletter")
@Slf4j
public class NewsLetterResource {
    private final INewsLetterService newsLetterService;

    @GetMapping("/findall")
    public ResponseEntity<List<NewsLetter>> findAll(){
        log.info("findAllNewsletter");
        return ResponseEntity.ok().body(newsLetterService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<NewsLetter> create(@RequestParam("email") String email) {
        log.info("create {}", email);
        return ResponseEntity.ok(newsLetterService.create(email));
    }


}
