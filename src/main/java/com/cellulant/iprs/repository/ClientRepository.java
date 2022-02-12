package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByClientNameIgnoreCase(String clientName);
    Optional<Client> findByClientID(long clientId);
    void deleteByClientID(long clientId);
}
