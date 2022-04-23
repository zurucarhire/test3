package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.Product;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.repository.ProductRepository;
import com.cellulant.iprs.service.IMerchantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MerchantServiceImpl implements IMerchantService {
    //private static String UPLOADED_FOLDER = "/Users/abala/Desktop/zuru/";
    private static String UPLOADED_FOLDER = "/opt/psm/images/";
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByUserID(Long userID) {
        return productRepository.findAllByUserID(userID);
    }

    @Override
    public Product createProduct(Long userId, String category, String name, double price, int count, double discount, int sale, String description, MultipartFile[] thumbnail) {
        productRepository.findByNameIgnoreCase(name).ifPresent(s -> {
            throw new ResourceExistsException("Product name exists");
        });

        try {
            StringBuilder stringBuilder = new StringBuilder();

            for (MultipartFile file: thumbnail){
                byte[] bytesProfilePic = file.getBytes();
                String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
                String fileName = System.currentTimeMillis()+"."+extension;
                log.info(extension);
                Path pathProfilePic = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(pathProfilePic, bytesProfilePic);
                stringBuilder.append(fileName).append(",");
            }

            stringBuilder.deleteCharAt(stringBuilder.length()-1);

            double overallPrice = price;
            if (discount != 0){
                overallPrice = price - discount;
            }

            if (sale != 0){
                overallPrice = overallPrice - (price * sale/100);
            }

            Product product = new Product();
            product.setUserID(userId);
            product.setCategory(category);
            product.setSubcategory(category);
            product.setName(name);
            product.setPrice(price);
            product.setOverallprice(overallPrice);
            product.setCount(count);
            product.setDiscount(discount);
            product.setSale(sale);
            product.setDescription(description);
            product.setThumbnail(stringBuilder.toString());
            product.setStatus(1);
            return productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnprocessedResourceException("Something went wrong please try again");
        }
    }

    @Override
    public Product updateProduct(Long productID, double price, int count, double discount, int sale, String description) {
        Product product = productRepository.findByProductID(productID).
                orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        double overallPrice = price;
        if (discount != 0){
            overallPrice = price - discount;
        }

        if (sale != 0){
            overallPrice = overallPrice - (price * sale/100);
        }

        product.setPrice(price);
        product.setOverallprice(overallPrice);
        product.setCount(count);
        product.setDiscount(discount);
        product.setSale(sale);
        product.setDescription(description);
        return productRepository.save(product);
    }

    @Override
    public Long deleteProduct(Long productID) {
        productRepository.findByProductID(productID).
                orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        return productRepository.deleteByProductID(productID);
    }

    @Override
    public Product findByProductId(Long productId) {
        return productRepository.findByProductID(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public List<Product> findAllByCategory(String name) {
        return productRepository.findAllBySubcategory(name);
    }
}
