package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    Optional<Procedure> findByProcedureID(long userId);
    List<Procedure> findByProcedureName(String procedureName);
    Optional<Procedure> findByProcedureNameIgnoreCase(String username);
    List<Procedure> findByCategory(String category);
}
