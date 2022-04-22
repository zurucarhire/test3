package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.entity.NewsLetter;
import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.repository.NewsLetterRepository;
import com.cellulant.iprs.service.INewsLetterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class NewsLetterServiceImpl implements INewsLetterService {

    private final NewsLetterRepository newsLetterRepository;

    @Override
    public List<NewsLetter> findAll() {
        return newsLetterRepository.findAll();
    }

    @Override
    public NewsLetter create(String email) {
        newsLetterRepository.findByEmailIgnoreCase(email).ifPresent(s -> {
            throw new ResourceExistsException("Resource Already Exists");
        });
        NewsLetter newsLetter = NewsLetter.builder()
                .email(email)
                .build();
        return newsLetterRepository.save(newsLetter);
    }
}
