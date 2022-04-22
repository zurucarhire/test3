package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.Procedure;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProcedureService {
   List<Procedure> findAll();
   List<Procedure> findByCategory(String category);
   List<Procedure> findByName(String procedureName);
   Procedure create(String name, String category, String subcategory, String type, String subtype, Double cost,
                    String description, MultipartFile thumbnail);
   Long delete(Long id);
   Procedure update(Procedure procedure);
   Long count();
}
