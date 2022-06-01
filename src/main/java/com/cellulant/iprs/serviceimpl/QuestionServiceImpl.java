package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.Question;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.repository.ExperienceRepository;
import com.cellulant.iprs.repository.QuestionRepository;
import com.cellulant.iprs.service.IExperienceService;
import com.cellulant.iprs.service.IQuestionService;
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
public class QuestionServiceImpl implements IQuestionService {
    @Value("${psm.image.dir}")
    private String UPLOADED_FOLDER;
    //private static String UPLOADED_FOLDER = "/opt/psm/images/";
    //private static String UPLOADED_FOLDER = "/Users/abala/Desktop/zuru/";
    private final QuestionRepository questionRepository;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question create(Long procedureID, String name, String title, String description) {
        Question question = Question.builder()
                .procedureID(procedureID)
                .category(name)
                .title(title)
                .description(description)
                .build();
        return questionRepository.save(question);
    }

    @Override
    public Question createQuestion(Long procedureID, String category, String title, String description, MultipartFile[] thumbnail) {
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

            Question question = new Question();
            question.setProcedureID(procedureID);
            question.setCategory(category);
            question.setTitle(title);
            question.setDescription(description);
            question.setThumbnail(thumbnail != null ? stringBuilder.toString() : "nil");
            return questionRepository.save(question);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnprocessedResourceException("Something went wrong please try again");
        }
    }

    @Override
    public Long count() {
        return questionRepository.count();
    }
}
