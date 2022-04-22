package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.Product;
import com.cellulant.iprs.service.IMerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/merchant")
@Slf4j
public class MerchantResource {
    private final IMerchantService merchantService;

    @GetMapping("/findall")
    public ResponseEntity<List<Product>> findAll(){
        log.info("findAllInventory");
        return ResponseEntity.ok().body(merchantService.findAll());
    }

    @GetMapping("/findbyuserid")
    public ResponseEntity<List<Product>> findByUserID(@RequestParam("userID") Long userID){
        log.info("findAllProduct {}", userID);
        log.info("findAllProduct2 {}", merchantService.findByUserID(userID));
        return ResponseEntity.ok().body(merchantService.findByUserID(userID));
    }

    @PostMapping("/createproduct")
    public ResponseEntity<Product> createProduct(
            @RequestParam("userID") Long userID,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("count") int count,
            @RequestParam("discount") double discount,
            @RequestParam("sale") int sale,
            @RequestParam("description") String description,
            @RequestParam("thumbnail") MultipartFile[] thumbnail) {
        log.info("createProduct {} {} {} {} {} {} {} {}", userID, name, price, count, discount, sale, description, thumbnail);
        //return null;
        return ResponseEntity.ok(merchantService.createProduct(userID, name, price, count, discount, sale, description, thumbnail));
    }

    @PutMapping("/updateproduct")
    public ResponseEntity<Product> updateProduct(
            @RequestParam("productID") Long productID,
            @RequestParam("price") double price,
            @RequestParam("count") int count,
            @RequestParam("discount") double discount,
            @RequestParam("sale") int sale,
            @RequestParam("description") String description) {
        log.info("updateProduct {} {} {} {} {} {}", productID, price, count, discount, sale, description);

        return ResponseEntity.ok(merchantService.updateProduct(productID, price, count, discount, sale, description));
    }

    @DeleteMapping(value = "/deleteproduct/{userID}")
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "userID") Long userID) {
        log.info("delete {} ", userID);
        merchantService.deleteProduct(userID);
        return ResponseEntity.ok(userID);
    }
}
