package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.dto.ExperienceDTO;
import com.cellulant.iprs.entity.ChangeLog;
import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.ExperienceComment;
import com.cellulant.iprs.entity.Product;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.repository.ExperienceCommentRepository;
import com.cellulant.iprs.repository.ExperienceRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceServiceImpl implements IExperienceService {
    @Value("${psm.image.dir}")
    private String UPLOADED_FOLDER;
   // private static String UPLOADED_FOLDER = "/opt/psm/images/";
    //private static String UPLOADED_FOLDER = "/Users/abala/Desktop/zuru/";
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
                .category(name)
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
    public Experience createExperience(Long procedureID, String category, String title, String completed, double cost, String description, MultipartFile[] thumbnail) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            if (thumbnail != null){
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
            }

            Experience experience = new Experience();
            experience.setProcedureID(procedureID);
            experience.setCategory(category);
            experience.setTitle(title);
            experience.setCompleted(completed);
            experience.setCost(cost);
            experience.setDescription(description);
            experience.setThumbnail(thumbnail != null ? stringBuilder.toString() : "nil");
            return experienceRepository.save(experience);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnprocessedResourceException("Something went wrong please try again");
        }
    }

    @Override
    public Long count() {
        return experienceRepository.count();
    }
}
