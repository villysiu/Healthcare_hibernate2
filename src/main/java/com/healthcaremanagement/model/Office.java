package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name= "Office")
@Data


public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OfficeId")
    private int officeId;

    private String location;
    private String phone;

    @OneToOne
    @JoinColumn(name="doctor_id")
    private Doctor doctor;

    public Office(){}
    public Office(String location, String phone, Doctor doctor) {
        this.location = location;
        this.phone = phone;
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Office{" +
                "officeId=" + officeId +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                ", doctorId=" + doctor.getDoctorId() +
                '}';
    }
}
