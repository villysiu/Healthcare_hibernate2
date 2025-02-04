package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.repository.DoctorRepository;

import java.util.List;

public class DoctorService {
    private final DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void createDoctor(Doctor doctor) {
        doctorRepository.createDoctor(doctor);
    }

    public Doctor getDoctorById(int id) {
        return doctorRepository.getDoctorById(id);
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.getAllDoctors();
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.updateDoctor(doctor);
    }

    public void deleteDoctor(int id) {
        doctorRepository.deleteDoctor(id);
    }

    public void addPatientToDoctor(int doctorId, Patient patient){
        doctorRepository.addPatientToDoctor(doctorId, patient);
    }
    public void removePatientFromDoctor(int doctorId, Patient patient){
        doctorRepository.removePatientFromDoctor(doctorId, patient);
    }
}
