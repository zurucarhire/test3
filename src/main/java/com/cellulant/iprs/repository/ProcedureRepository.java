package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Procedure;
import com.cellulant.iprs.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    Optional<Procedure> findByProcedureID(long userId);
    @Query("SELECT p FROM Procedure p where p.procedureName=?1")
    Procedure getByProcedureName(String procedureName);
    List<Procedure> findByProcedureName(String procedureName);
    @Query("SELECT p FROM Procedure p where p.procedureID=?1")
    List<Procedure> getByProcedureID(Long id);
    Optional<Procedure> findByProcedureNameIgnoreCase(String username);
    List<Procedure> findByCategory(String category);
    //@Query(value="SELECT * FROM procedures WHERE city -> 'city' @> '[\"New York\"]';",nativeQuery = true)
//    @Query(value="SELECT * FROM procedures WHERE city @> '[?1]';",nativeQuery = true)
    @Query(value="SELECT * FROM procedures WHERE city @> CAST(:name as jsonb);",nativeQuery = true)
    List<Procedure> findAllName(@Param("name") String name);
}
