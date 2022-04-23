package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT i FROM Product i WHERE i.status = 1")
    List<Product> findAllByUserID(Long userID);
    List<Product> findAllBySubcategory(String name);
    Optional<Product> findByUserID(Long userID);
    Optional<Product> findByNameIgnoreCase(String nameD);
    Optional<Product> findByProductID(Long productID);
    Long deleteByProductID(Long productID);
}
