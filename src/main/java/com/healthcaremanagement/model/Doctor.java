package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

   @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
   @JoinTable(
           name = "doctor_patient",
           joinColumns = @JoinColumn(name = "doctor_id"),
           inverseJoinColumns = @JoinColumn(name = "patient_id"),
           uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "patient_id"})
   )
   private List<Patient> patients;

   @OneToOne(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   private Office office;

   public Doctor(){}
   public Doctor(String firstName, String lastName, String specialty, String email) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.specialty = specialty;
       this.email = email;
   }

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
