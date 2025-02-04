package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentId")
    private int appointmentId;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient  patient;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private Doctor doctor;

    //YYYY-MM-DD
    private String appointmentDate;
    private String notes;

}
