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
    public void setUp()
    {
        recordFacade = new RecordFacadeImpl(recordDao);
        record = new Record(Long.valueOf(1), Long.valueOf(1),
                "EMP-002", Long.valueOf(1), Date.valueOf("2024-09-15"),
                Long.valueOf(1), Date.valueOf("2025-04-15"),
                "Student caught vaping in school", "Pending");
    }

    @Test
    public void testCreateStudentRecord()
    {
        when(recordDao.addStudentRecord(Long.valueOf(1), "EMP-002",
                Long.valueOf(1), Date.valueOf("2024-09-15"), Long.valueOf(1),
                "Student caught vaping in school", RecordStatus.PENDING.getStatus())).thenReturn(true);

        boolean result = recordFacade.createStudentRecord(Long.valueOf(1),
                "EMP-002", Long.valueOf(1),  Date.valueOf("2024-09-15"),
                Long.valueOf(1), "Student caught vaping in school");

        assertTrue(result);
        verify(recordDao, times(1)).addStudentRecord(anyLong(),
                anyString(), anyLong(), any(Date.class), anyLong(), anyString(), anyString());
    }
}