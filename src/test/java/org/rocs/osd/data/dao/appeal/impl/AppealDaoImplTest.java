package org.rocs.osd.data.dao.appeal.impl;

import org.junit.jupiter.api.*;
import org.rocs.osd.model.appeal.Appeal;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppealDaoImplTest {

    private static AppealDaoImpl appealDao;

    @BeforeAll
    static void setup() {
        appealDao = new AppealDaoImpl();
    }

    @Test
    @Order(1)
    void testSaveAppeal() {
        Appeal appeal = new Appeal();
        appeal.setRecordID(1L);
        appeal.setEnrollmentID(1L);
        appeal.setMessage("Test appeal message");
        appeal.setDateFiled(new Date());
        appeal.setStatus("PENDING");

        assertDoesNotThrow(() -> appealDao.saveAppeal(appeal), "Saving appeal should not throw exception");
    }

    @Test
    @Order(2)
    void testFindAllAppealDetails() {
        List<Appeal> appeals = appealDao.findAllAppealDetails();
        assertNotNull(appeals, "Appeal list should not be null");
        assertTrue(appeals.size() > 0, "Appeals list should have at least one record");

        for (Appeal a : appeals) {
            System.out.println("ID: " + a.getAppealID() +
                    ", Student: " + a.getStudentName() +
                    ", Offense: " + a.getOffense() +
                    ", Status: " + a.getStatus());
        }
    }

    @Test
    @Order(3)
    void testUpdateAppealStatus() {
        List<Appeal> appeals = appealDao.findAllAppealDetails();
        assertFalse(appeals.isEmpty(), "Appeals list should not be empty for status update");

        Long appealId = appeals.get(0).getAppealID();
        assertDoesNotThrow(() -> appealDao.updateAppealStatus(appealId, "APPROVED"));

        Appeal updatedAppeal = appealDao.findAllAppealDetails()
                .stream()
                .filter(a -> a.getAppealID().equals(appealId))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedAppeal, "Updated appeal should exist");
        assertEquals("APPROVED", updatedAppeal.getStatus(), "Appeal status should be APPROVED");

        assertDoesNotThrow(() -> appealDao.updateAppealStatus(appealId, "REJECTED"));
        updatedAppeal = appealDao.findAllAppealDetails()
                .stream()
                .filter(a -> a.getAppealID().equals(appealId))
                .findFirst()
                .orElse(null);
        assertEquals("REJECTED", updatedAppeal.getStatus(), "Appeal status should be REJECTED");
    }
}
