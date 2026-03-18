package org.rocs.osd.facade.enrollment.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.model.enrollment.Enrollment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class EnrollmentFacadeImplTest {

    @Mock
    private EnrollmentDao enrollmentDao;

    private EnrollmentFacade enrollmentFacade;

    @BeforeEach
    void setUp() {
        enrollmentFacade = new EnrollmentFacadeImpl(enrollmentDao);
    }

    @Test
    void testGetAllLatestEnrollments() {
        List<Enrollment> list = new ArrayList<>();
        list.add(new Enrollment());
        list.add(new Enrollment());

        when(enrollmentDao.findAllLatestEnrollments()).thenReturn(list);
        List<Enrollment> result = enrollmentFacade.getAllLatestEnrollments();
        assertEquals(2, result.size());
        verify(enrollmentDao).findAllLatestEnrollments();
    }

    @Test
    void testGetEnrollmentsByStudentId() {
        List<Enrollment> list = new ArrayList<>();
        list.add(new Enrollment());
        list.add(new Enrollment());

        when(enrollmentDao.findEnrollmentsByStudentId("JHS-0001")).thenReturn(list);
        List<Enrollment> result = enrollmentFacade.getEnrollmentsByStudentId("JHS-0001");
        assertEquals(2, result.size());
        verify(enrollmentDao).findEnrollmentsByStudentId("JHS-0001");
    }

    @Test
    void testGetLatestEnrollmentByStudentId() {
        List<Enrollment> list = new ArrayList<>();
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        list.add(enrollment1);
        list.add(enrollment2);

        when(enrollmentDao.findEnrollmentsByStudentId("JHS-0001")).thenReturn(list);
        Enrollment result = enrollmentFacade.getLatestEnrollmentByStudentId("JHS-0001");
        assertEquals(enrollment1, result);
        verify(enrollmentDao).findEnrollmentsByStudentId("JHS-0001");
    }

}