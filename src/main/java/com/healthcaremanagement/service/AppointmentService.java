package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Appointment;
import com.healthcaremanagement.repository.AppointmentRepository;

import java.util.List;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(Appointment appointment) {
        appointmentRepository.createAppointment(appointment);
    }

    public Appointment getAppointmentById(int id) {
        return appointmentRepository.getAppointmentById(id);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.updateAppointment(appointment);
    }

    public void deleteAppointment(int id) {
        System.out.println(id);
        appointmentRepository.deleteAppointment(id);
    }

    public Boolean hasOtherAppointmentsBetween(int doctorId, int patientId){
        return appointmentRepository.hasOtherAppointmentsBetween(doctorId, patientId);
    }
}
