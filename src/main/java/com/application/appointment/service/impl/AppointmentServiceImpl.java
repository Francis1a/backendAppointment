package com.application.appointment.service.impl;

import com.application.appointment.dto.*;
import com.application.appointment.repositories.AppointmentRepository;
import com.application.appointment.repositories.CustomerRepository;
import com.application.appointment.service.AppointmentService;
import com.application.appointment.repositories.AdminRepository;
import com.application.appointment.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository, AppointmentRepository appointmentRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
    }


    public List<UserAdmin> getAllAdmins() {
        return adminRepository.findAll();
    }


    //Create Admin account
    @Override
    public UserAdmin createAdminAccount(UserAdmin admin) {
        // Check if the email already exists
        if (adminRepository.findAll().stream().anyMatch(a -> a.getEmail().equals(admin.getEmail()))) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        // Check if the email format is valid
        if (!admin.getEmail().contains("@") || !admin.getEmail().contains(".")) {
            throw new IllegalArgumentException("Invalid email format. Please enter a valid email.");
        }

        // Check if the password is already taken (this might not be a good practice; usually passwords are hashed)
        if (adminRepository.findAll().stream().anyMatch(a -> a.getPassword().equals(admin.getPassword()))) {
            throw new IllegalArgumentException("This password is already taken. Please choose another.");
        }

        // Save the new admin account
        return adminRepository.save(admin);
    }


    //Create Customer account
    @Override
    public UserCustomer createUserAccount(UserCustomer user){
        // Check if the email already exists
        if (customerRepository.findAll().stream().anyMatch(c -> c.getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        // Check if the email format is valid
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new IllegalArgumentException("Invalid email format. Please enter a valid email.");
        }

        // Check if the password is already taken (this might not be a good practice; usually passwords are hashed)
        if (customerRepository.findAll().stream().anyMatch(c -> c.getPassword().equals(user.getPassword()))) {
            throw new IllegalArgumentException("This password is already taken. Please choose another.");
        }

        // Set the date and time fields
        user.setDate(java.time.LocalDate.now().toString());
        user.setTime(java.time.LocalTime.now().toString());

        // Save the new customer account
        return customerRepository.save(user);
    }


    //Login Admin or Customer account
    @Override
    public ResponseEntity<?> loginAccount(UserAdmin admin) {
        System.out.println(admin.getEmail());
        System.out.println(admin.getPassword());
        boolean isAdmin = adminRepository.findAll().stream()
                .anyMatch(a -> a.getEmail().equals(admin.getEmail()) && a.getPassword().equals(admin.getPassword()));
        boolean isCustomer = customerRepository.findAll().stream()
                .anyMatch(c -> c.getEmail().equals(admin.getEmail()) && c.getPassword().equals(admin.getPassword()));

        if (isAdmin) {
            String token = JwtUtil.generateToken(admin.getEmail(), "Admin");
            System.out.println(token);
            return ResponseEntity.ok(new LoginResponse(admin.getEmail(), "Admin", "Login successful", token));
        } else if (isCustomer) {
            String token = JwtUtil.generateToken(admin.getEmail(), "Customer");
            return ResponseEntity.ok(new LoginResponse(admin.getEmail(), "Customer", "Login successful", token));
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }



    //Create Appointment for Customer
    @Override
    public CustomerAppointment createAppointment(CustomerAppointment appointment) {
        // Check if the customer already has an appointment
        if (appointmentRepository.findAll().stream().anyMatch(a -> a.getCustomerName().equals(appointment.getCustomerName()))) {
            throw new IllegalArgumentException("This customer already has an appointment.");
        }

        // Convert appointment date from String to LocalDate
        java.time.LocalDate appointmentDate = java.time.LocalDate.parse(appointment.getAppointmentDate());

        // Check if the appointment date is in the future
        if (appointmentDate.isBefore(java.time.LocalDate.now()) || appointmentDate.isEqual(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("The appointment date must be in the future.");
        }

        // Check if the appointment date and time are already taken
        if (appointmentRepository.findAll().stream().anyMatch(a ->
                a.getAppointmentDate().equals(appointment.getAppointmentDate()) &&
                        a.getAppointmentTime().equals(appointment.getAppointmentTime()))) {
            throw new IllegalArgumentException("This appointment date and time are already taken.");
        }

        // Save the new appointment
        return appointmentRepository.save(appointment);
    }


    // Get all approved appointments
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(a -> new AppointmentResponse(a.getApprove(), a.getAppointmentDate(), a.getAppointmentTime()))
                .collect(Collectors.toList());
    }


    //Approved Appointments
    public CustomerAppointment approveAppointment(int getId) {
        // Retrieve the appointment by ID
        CustomerAppointment appointment = appointmentRepository.findById((long) getId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // Approve the appointment
        appointment.setApprove(true);

        // Save the updated appointment
        return appointmentRepository.save(appointment);
    }




}
