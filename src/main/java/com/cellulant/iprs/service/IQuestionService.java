package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.Question;

import java.util.List;

public interface IQuestionService {
    List<Question> findAll();
    Question create(Long procedureID, String name, String title, String description);
    Long count();
}
