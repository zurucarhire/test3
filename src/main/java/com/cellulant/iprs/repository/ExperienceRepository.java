package com.cellulant.iprs.repository;

import com.cellulant.iprs.dto.ExperienceDTO;
import com.cellulant.iprs.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("SELECT new com.cellulant.iprs.dto.ExperienceDTO(e.experienceID, e.category, e.description, e.dateCreated, p.procedureName, p.description) FROM Experience e INNER JOIN Procedure p ON e.procedureID = p.procedureID")
    List<ExperienceDTO> findAllExperiences();
}
