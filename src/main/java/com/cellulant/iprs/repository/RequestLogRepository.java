package com.cellulant.iprs.repository;

import com.cellulant.iprs.dto.RequestLogDTO;
import com.cellulant.iprs.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    @Query("SELECT new com.cellulant.iprs.dto.RequestLogDTO(u.userName, r.requestType, r.requestNumber, r.requestSerialNumber, r.dateCreated) FROM RequestLog r INNER JOIN User u ON r.createdBy = u.userID where r.dateCreated > ?1 and r.dateCreated < ?2 and r.requestNumber = ?3")
    List<RequestLogDTO> findAllRequestLogs(Date startDate, Date endDate, Long requestNumber);
}
