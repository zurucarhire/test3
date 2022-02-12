package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestTypeRepository extends JpaRepository<RequestType, Long> {
    @Query("SELECT r FROM RequestType r WHERE r.active = 1")
    List<RequestType> findAllActiveRequestTypes();
    Optional<RequestType> findByRequestTypeID(Long requestTypeID);
    Optional<RequestType> findByRequestTypeNameIgnoreCase(String requestTypeName);
    void deleteByRequestTypeID(Long requestTypeID);
}
