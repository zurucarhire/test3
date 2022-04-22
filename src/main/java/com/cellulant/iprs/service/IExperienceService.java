package com.cellulant.iprs.service;

import com.cellulant.iprs.dto.ExperienceDTO;
import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.ExperienceComment;

import java.util.List;

public interface IExperienceService {
    List<ExperienceDTO> findAll();
    Experience create(Long procedureID, String name, String description);
    ExperienceComment createComment(Long experienceID, String name, String description);
    Long count();
}
