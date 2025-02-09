package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.repository.DoctorRepository;
import com.healthcaremanagement.repository.OfficeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfficeServiceTestAll {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private static DoctorService doctorService;
    private static OfficeService officeService;

    private static int officeId;
    private static Office office;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        DoctorRepository doctorRepository = new DoctorRepository(sessionFactory);
        doctorService = new DoctorService(doctorRepository);
        OfficeRepository officeRepository = new OfficeRepository(sessionFactory);
        officeService = new OfficeService(officeRepository);
    }

    @AfterAll
    static void afterAll() {
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
    @Order(1)
    void testCreateOffice() {
        Doctor doctor = new Doctor("John", "doe", "hand", "john@doe.com");
        doctorService.createDoctor(doctor);
        office = new Office("New York", "123-456-7890", doctor);
        officeService.createOffice(office);

        //Shared variable
        officeId = office.getOfficeId();

        assertNotNull(office.getOfficeId(), "Office should be created with a valid ID.");
        assertTrue(office.getOfficeId() > 0, "Office ID should be a positive number.");
    }

    @Test
    @Order(2)
    void testGetOfficeById(){
//        System.out.println(office);
//        System.out.println(officeId);
        Office retrievedOffice = officeService.getOfficeById(officeId);
//        System.out.println(retrievedOffice);
        assertNotNull(retrievedOffice, "Office should be retrieved.");
        assertEquals("New York", retrievedOffice.getLocation(), "Location should match what was assigned.");
        assertEquals("123-456-7890", retrievedOffice.getPhone(), "Phone number should be correctly stored.");
    }

    @Test
    @Order(3)
    void testUpdateOffice() {
        Office retrievedOffice = officeService.getOfficeById(officeId);
        assertNotNull(retrievedOffice, "Office should be retrieved.");
//        System.out.println("before:" + office);
        retrievedOffice.setLocation("San Francisco");
        retrievedOffice.setPhone("555-789-1234");
        officeService.updateOffice(retrievedOffice);

//        System.out.println(office);

        Office updatedOffice = officeService.getOfficeById(officeId);
        assertNotNull(updatedOffice, "Office should exist.");
        assertEquals("San Francisco", updatedOffice.getLocation(), "Office location should be updated.");
        assertEquals("555-789-1234", updatedOffice.getPhone(), "Phone number should be updated.");
    }

    @Test
    @Order(4)
    void testDeleteOffice() {
        officeService.deleteOffice(officeId);
        Office deletedOffice = officeService.getOfficeById(officeId);

        assertNull(deletedOffice, "Office should be deleted and no longer retrievable.");
    }

    @Test
    @Order(5)
    public void testGetAllOffices() {
        String[] locations = {"New York", "Los Angeles", "Chicago", "San Francisco"};
        String[] emails = {"john@doe.com", "john2@doe.com", "john3@doe.com","john4@doe.com"};

        for(int i = 0; i < locations.length; i++) {
            Doctor doctor = new Doctor("John", "doe", "hand", emails[i]);
            doctorService.createDoctor(doctor);
            Office office = new Office(locations[i], "123-123-1234", doctor);
            officeService.createOffice(office);
        }

        List<Office> offices = officeService.getAllOffices();
        assertNotNull(offices, "Office list should not be null.");
        assertFalse(offices.isEmpty(), "Office list should not be empty.");
        assertTrue(offices.size() >= 2, "At least two offices should exist.");
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
        Office retrievedOffice = officeService.getOfficeById(office.getOfficeId());
        assertNotNull(retrievedOffice, "Office should be created.");
        assertEquals(location, office.getLocation(), "Office location should match.");
    }

}
