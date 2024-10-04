package com.application.appointment.repositories;

import com.application.appointment.dto.CustomerAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<CustomerAppointment, Long> {
}
