package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.CustomerArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerArchiveRepository extends JpaRepository<CustomerArchive, Long> {

}
