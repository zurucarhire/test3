package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.ExperienceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExperienceCommentRepository extends JpaRepository<ExperienceComment, Long> {

}
