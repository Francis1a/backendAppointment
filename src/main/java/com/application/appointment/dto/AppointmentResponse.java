package com.application.appointment.dto;

public class AppointmentResponse {
    private boolean approve;
    private String appointmentDate;
    private String appointmentTime;

    public AppointmentResponse(boolean approve, String appointmentDate, String appointmentTime) {
        this.approve = approve;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
