package com.application.appointment.service;

import com.application.appointment.dto.AppointmentResponse;
import com.application.appointment.dto.CustomerAppointment;
import com.application.appointment.dto.UserAdmin;
import com.application.appointment.dto.UserCustomer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentService {

    //Create Admin account
    UserAdmin createAdminAccount(UserAdmin admin);

    //Create User account
    UserCustomer createUserAccount(UserCustomer user);

    //Login admin account
    ResponseEntity<?> loginAccount(UserAdmin admin);

    //Appointment for customer
    CustomerAppointment createAppointment(CustomerAppointment customerAppointment);

    //get all Appointments
    List<AppointmentResponse> getAllAppointments();

    //Approve appointment
    CustomerAppointment approveAppointment(int id);
}
