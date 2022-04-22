package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.Procedure;
import com.cellulant.iprs.entity.Role;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.repository.ProcedureRepository;
import com.cellulant.iprs.service.IProcedureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProcedureServiceImpl implements IProcedureService {

    private static String UPLOADED_FOLDER = "/Users/abala/Desktop/zuru/";
    //private static String UPLOADED_FOLDER = "/opt/psm/images/";

    private final ProcedureRepository procedureRepository;

    @Override
    public List<Procedure> findAll() {
        return procedureRepository.findAll();
    }

    @Override
    public List<Procedure> findByCategory(String category) {
        return procedureRepository.findByCategory(category);
    }

    @Override
    public List<Procedure> findByName(String name) {
        return procedureRepository.findByProcedureName(name);
    }

    @Override
    public Procedure create(String name, String category, String subcategory, String type, String subtype,
                            Double cost, String description, MultipartFile thumbnail) {

        try {
            byte[] bytesProfilePic = thumbnail.getBytes();
            Path pathProfilePic = Paths.get(UPLOADED_FOLDER + thumbnail.getOriginalFilename());
            Files.write(pathProfilePic, bytesProfilePic);

            Procedure procedure = Procedure.builder()
                    .procedureName(name)
                    .description(description)
                    .category(category)
                    .subCategory(subcategory)
                    .type(type)
                    .subType(subtype)
                    .photo(thumbnail.getOriginalFilename())
                    .cost(cost)
                    .build();
            return procedureRepository.save(procedure);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("uploadImage Error {}", e.getLocalizedMessage());
            throw new UnprocessedResourceException("Problem creating procedure");
        }

    }

    @Override
    public Long delete(Long id) {
        procedureRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("ID not found"));
        procedureRepository.deleteById(id);
        return id;
    }

    @Override
    public Procedure update(Procedure procedure) {
        Procedure procedure1 = procedureRepository
                .findByProcedureID(procedure.getProcedureID())
                .orElseThrow(()->new ResourceNotFoundException("ID not found"));
        procedure1.setProcedureName(procedure.getProcedureName());
        procedure1.setCategory(procedure.getCategory());
        procedure1.setSubCategory(procedure.getSubCategory());
        procedure1.setType(procedure.getType());
        procedure1.setSubType(procedure.getSubType());
        procedure1.setDescription(procedure.getDescription());
        procedure1.setCost(procedure.getCost());

        return procedureRepository.save(procedure1);
    }

    @Override
    public Long count() {
        return procedureRepository.count();
    }
}
