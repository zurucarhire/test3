package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.ProductCategory;
import com.cellulant.iprs.repository.ProductCategoryRepository;
import com.cellulant.iprs.service.IMiscService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MiscServiceImpl implements IMiscService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }
}
