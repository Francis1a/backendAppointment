package com.application.appointment.repositories;

import com.application.appointment.dto.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<UserAdmin, Long> {
}
