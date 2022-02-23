package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.IDNumber = ?1")
    Customer findCustomerByIDNumber(String idNumber);
}
