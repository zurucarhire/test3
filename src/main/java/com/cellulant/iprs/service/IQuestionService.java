package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IQuestionService {
    List<Question> findAll();
    Question create(Long procedureID, String name, String title, String description);
    Question createQuestion(Long procedureID, String category, String title, String description, MultipartFile[] thumbnail);
    Long count();
}
