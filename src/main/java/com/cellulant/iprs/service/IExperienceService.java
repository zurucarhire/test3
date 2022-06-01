package com.cellulant.iprs.service;

import com.cellulant.iprs.dto.ExperienceDTO;
import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.ExperienceComment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IExperienceService {
    List<ExperienceDTO> findAll();
    Experience create(Long procedureID, String name, String description);
    ExperienceComment createComment(Long experienceID, String name, String description);
    Experience createExperience(Long procedureID, String category, String title, String completed, double cost, String description, MultipartFile[] thumbnail);
    Long count();
}
