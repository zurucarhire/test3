package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMerchantService {
    List<Product> findAll();
    List<Product> findByUserID(Long userID);
    Product createProduct(Long userID, String name, double price, int count, double discount, int sale, String description, MultipartFile[] thumbnail);
    Product updateProduct(Long productID, double price, int count, double discount, int sale, String description);
    Long deleteProduct(Long userID);
}
