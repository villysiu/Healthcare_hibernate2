package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name= "Office")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        Office office = (Office) o;
//        return officeId == office.officeId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(officeId);
//    }
}
