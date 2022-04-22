package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.dto.ExperienceDTO;
import com.cellulant.iprs.entity.ChangeLog;
import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.ExperienceComment;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.repository.ExperienceCommentRepository;
import com.cellulant.iprs.repository.ExperienceRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceServiceImpl implements IExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceCommentRepository experienceCommentRepository;

    @Override
    public List<ExperienceDTO> findAll() {
        return experienceRepository.findAllExperiences();
    }

    @Override
    public Experience create(Long procedureID, String name, String description) {
        Experience experience = Experience.builder()
                .procedureID(procedureID)
                .name(name)
                .description(description)
                .build();
        return experienceRepository.save(experience);
    }

    @Override
    public ExperienceComment createComment(Long experienceID, String name, String description) {
        ExperienceComment experience = ExperienceComment.builder()
                .experienceID(experienceID)
                .name(name)
                .description(description)
                .build();
        return experienceCommentRepository.save(experience);
    }

    @Override
    public Long count() {
        return experienceRepository.count();
    }
}
