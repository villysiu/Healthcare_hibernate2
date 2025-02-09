package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PatientId")
    private int patientId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "DateOfBirth")
    private String dateOfBirth;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany(mappedBy = "patients", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Doctor> doctors;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId == patient.patientId ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }
}
