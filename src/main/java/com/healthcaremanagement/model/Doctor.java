package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name= "Doctor")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DoctorId")
    private int doctorId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;
    private String specialty;
    private String email;


   @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Appointment> appointments = new ArrayList<>();

   @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
   @JoinTable(
           name = "doctor_patient",
           joinColumns = @JoinColumn(name = "doctor_id"),
           inverseJoinColumns = @JoinColumn(name = "patient_id"),
           uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "patient_id"})
   )
   private List<Patient> patients;

   @OneToOne(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   private Office office;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctorId == doctor.doctorId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(doctorId);
    }
}
