package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {
    Optional<NewsLetter> findByEmailIgnoreCase(String email);
}
