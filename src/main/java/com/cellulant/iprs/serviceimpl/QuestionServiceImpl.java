package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.Question;
import com.cellulant.iprs.repository.ExperienceRepository;
import com.cellulant.iprs.repository.QuestionRepository;
import com.cellulant.iprs.service.IExperienceService;
import com.cellulant.iprs.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question create(Long procedureID, String name, String title, String description) {
        Question question = Question.builder()
                .procedureID(procedureID)
                .name(name)
                .title(title)
                .description(description)
                .build();
        return questionRepository.save(question);
    }

    @Override
    public Long count() {
        return questionRepository.count();
    }
}
