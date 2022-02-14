package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType, Long> {
    Optional<RequestType> findByRequestTypeID(Long requestTypeID);
    Optional<RequestType> findByRequestTypeNameIgnoreCase(String requestTypeName);
    @Query("SELECT r FROM RequestType r WHERE r.active = 1")
    List<RequestType> findAllActive();
    void deleteByRequestTypeID(Long requestTypeID);
}
