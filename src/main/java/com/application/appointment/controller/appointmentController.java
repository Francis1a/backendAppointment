package com.application.appointment.controller;

import com.application.appointment.dto.*;
import com.application.appointment.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.application.appointment.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/ms-appointment")
@CrossOrigin(origins = "http://localhost:3000")
public class appointmentController {

    @Autowired
    private AppointmentService AppointmentService;


    @PostMapping("/createAdmin")
    public UserAdmin createAdmin(@RequestBody UserAdmin userAdmin) {
        return AppointmentService.createAdminAccount(userAdmin);
    }

    @PostMapping("/createCustomer")
    public UserCustomer createCustomer(@RequestBody UserCustomer user) {
        return AppointmentService.createUserAccount(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAdmin userAdmin) {
        return AppointmentService.loginAccount(userAdmin);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        System.out.println("Token: " + token);
        try {
            // Extract email or other user info from the token
            String userEmail = JwtUtil.extractClaims(token).getSubject();

            // Validate the token
            if (JwtUtil.validateToken(token, userEmail)) {
                return ResponseEntity.ok("Token is valid");
            } else {
                return ResponseEntity.status(401).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }

    @PostMapping("/createAppointment")
    public CustomerAppointment createAppointment(@RequestBody CustomerAppointment customerAppointment) {
        return AppointmentService.createAppointment(customerAppointment);
    }

    @GetMapping("/getAppointments")
    public List<AppointmentResponse> getAllAppointments() {

        return AppointmentService.getAllAppointments();
    }

    @PutMapping("/approveAppointment/{id}")
    public CustomerAppointment approveAppointment(@PathVariable int id) {
        return AppointmentService.approveAppointment(id);
    }
}
