package com.healthcaremanagement.repository;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Patient;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class DoctorRepository {
    private final SessionFactory sessionFactory;
    public DoctorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createDoctor(Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(doctor);
            transaction.commit();
        }
    }

    public Doctor getDoctorById(int doctorId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Doctor.class, doctorId);
        }
    }
    public List<Doctor> getAllDoctors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Doctor", Doctor.class).list();
        }
    }

    public void updateDoctor(Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(doctor);
            transaction.commit();
        }
    }

    public void deleteDoctor(int doctorId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null) {
                session.remove(doctor);
            }
            transaction.commit();
        }
    }


    public void addPatientToDoctor(int doctorId, Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);

            if (doctor != null && !doctor.getPatients().contains(patient)) {
                doctor.getPatients().add(patient);
                session.merge(doctor);
            }
            transaction.commit();
        }
    }
    public void removePatientFromDoctor(int doctorId, Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null && doctor.getPatients().contains(patient)) {
                boolean removed = doctor.getPatients().remove(patient);
                session.merge(doctor);
            }
            transaction.commit();

        }
    }
//    private Boolean patientExistsInDoctor(Doctor doctor, Patient patient){
//        for(Patient p: doctor.getPatients()) {
//            if(p.getPatientId() == patient.getPatientId()) {
//                return true;
//            }
//        }
//        return false;
//    }
}
