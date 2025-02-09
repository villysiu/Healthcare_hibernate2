package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.repository.DoctorRepository;
import com.healthcaremanagement.repository.OfficeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OfficeServiceTest {

    private  SessionFactory sessionFactory;
    private  Session session;
    private Transaction transaction;
    private  DoctorService doctorService;
    private  OfficeService officeService;




    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        DoctorRepository doctorRepository = new DoctorRepository(sessionFactory);
        doctorService = new DoctorService(doctorRepository);
        OfficeRepository officeRepository = new OfficeRepository(sessionFactory);
        officeService = new OfficeService(officeRepository);

    }

    @AfterEach
    void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
//    @ParameterizedTest
    void testCreateOffice() {
        Doctor doctor = new Doctor("John", "doe", "hand", "john@doe.com");
        doctorService.createDoctor(doctor);
        Office office = new Office("123 Main St", "222-222-2222", doctor);
        officeService.createOffice(office);

        Office fetchedOffice = officeService.getOfficeById(office.getOfficeId());
        String expectedLocation = "123 Main St";
        String expectedPhone = "123-456-7890";

        assertNotNull(fetchedOffice, "Office should be retrievable.");
        assertEquals(expectedLocation, fetchedOffice.getLocation(), "Location should be correct.");
        assertEquals(expectedPhone, fetchedOffice.getPhone(), "Phone number should match.");
    }

    @Test
    void testGetOfficeById() {
        Doctor doctor = new Doctor("John", "doe", "hand", "john@doe.com");
        doctorService.createDoctor(doctor);
        Office office = new Office("456 Maple St", "555-123-4567", doctor);
        officeService.createOffice(office);

        Office fetchedOffice = officeService.getOfficeById(office.getOfficeId());
        String expectedLocation = "456 Maple St";
        String expectedPhone = "555-123-4567";

        assertNotNull(fetchedOffice, "Office should be retrievable.");
        assertEquals(expectedLocation, fetchedOffice.getLocation(), "Location should be correct.");
        assertEquals(expectedPhone, fetchedOffice.getPhone(), "Phone number should match.");
    }

    @Test
    void testUpdateOffice() {
        Doctor doctor = new Doctor("John", "doe", "hand", "john@doe.com");
        doctorService.createDoctor(doctor);
        Office office = new Office("123 Main St", "123-456-7890", doctor);
        officeService.createOffice(office);

        office.setLocation("456 Maple St");
        office.setPhone("555-123-4567");
        officeService.updateOffice(office);

        Office updatedOffice = officeService.getOfficeById(office.getOfficeId());
        assertEquals("456 Maple St", updatedOffice.getLocation(), "Location should be updated.");
        assertEquals("555-123-4567", updatedOffice.getPhone(), "Phone number should be updated.");
    }

    @Test
    void testDeleteOffice() {
        Doctor doctor = new Doctor("John", "doe", "hand", "john@doe.com");
        doctorService.createDoctor(doctor);
        Office office = new Office("123 Main St", "123-456-7890", doctor);
        officeService.createOffice(office);

        int id = officeService.getOfficeById(office.getOfficeId()).getOfficeId();
        officeService.deleteOffice(id);

        Office deletedOffice = officeService.getOfficeById(id);

        assertNull(deletedOffice, "Office should be deleted and no longer retrievable.");

    }

    @ParameterizedTest
    @ValueSource(strings = {"New York", "Los Angeles", "Chicago", "San Francisco"})
    public void testCreateOfficeWithDifferentLocations(String location) {
        Doctor doctor = new Doctor("Alex", "Karev", "Surgery", "alex.karev@example.com");
        doctorService.createDoctor(doctor);

        Office office = new Office();
        office.setLocation(location);
        office.setPhone("123-456-7890");
        office.setDoctor(doctor);

        officeService.createOffice(office);

        assertNotNull(office.getOfficeId(), "Office ID should be generated.");
        assertEquals(location, office.getLocation(), "Location should match the assigned value.");
    }
}