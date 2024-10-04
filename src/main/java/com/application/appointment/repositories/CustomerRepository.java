package com.application.appointment.repositories;

import com.application.appointment.dto.UserCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<UserCustomer, Long> {
}
