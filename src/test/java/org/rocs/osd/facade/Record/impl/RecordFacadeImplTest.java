package org.rocs.osd.facade.Record.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.Record.RecordFacade;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.sql.Date;

@ExtendWith(MockitoExtension.class)
class RecordFacadeImplTest
{
    @Mock
    private RecordDao recordDao;

    private Record record;

    private RecordFacade recordFacade;

    @BeforeEach
    public void setUp() {
      recordFacade = new RecordFacadeImpl(recordDao);
    }

        @Test
        void testCreateStudentRecord () {

            when(recordDao.addStudentRecord(
                    anyLong(),
                    anyString(),
                    anyLong(),
                    any(Date.class),
                    anyLong(),
                    anyString(),
                    eq(RecordStatus.PENDING)
            )).thenReturn(true);

            boolean result = recordFacade.createStudentRecord(
                    1L,
                    "EMP-001",
                    2L,
                    Date.valueOf("2025-03-08"),
                    3L,
                    "Bullying incident"
            );

            assertTrue(result);

            verify(recordDao, times(1)).addStudentRecord(
                    1L,
                    "EMP-001",
                    2L,
                    Date.valueOf("2025-03-08"),
                    3L,
                    "Bullying incident",
                    RecordStatus.PENDING
            );
        }

    @Test
    public void testUpdateStudentRecord()
    {
        when(recordDao.updateRecord(any(Record.class))).thenReturn(true);

        boolean result = recordFacade.updateStudentRecord(Long.valueOf(1),
                "EMP-002", Long.valueOf(1),  Date.valueOf("2024-09-15"),
                Long.valueOf(1), "Student caught vaping in school",
                RecordStatus.PENDING);

        assertTrue(result);
        verify(recordDao, times(1)).updateRecord(any(Record.class));
    }
}