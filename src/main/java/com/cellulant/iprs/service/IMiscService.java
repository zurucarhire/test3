package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.ProductCategory;

import java.util.List;

public interface IMiscService {
   List<ProductCategory> findAllProductCategories();
}
