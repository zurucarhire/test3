package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.NewsLetter;

import java.util.List;

public interface INewsLetterService {
    List<NewsLetter> findAll();
    NewsLetter create(String email);
}
