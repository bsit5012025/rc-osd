package org.rocs.osd.facade.Record.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.Record.RecordFacade;
import org.rocs.osd.model.record.Record;

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
        when(recordDao.addStudentRecord(Long.valueOf(1), Long.valueOf(1),
                "EMP-002", Long.valueOf(1), Date.valueOf("2024-09-15"),
                Long.valueOf(1), Date.valueOf("2025-04-15"),
                "Student caught vaping in school", "Pending")).thenReturn(true);



        verify(recordDao, times(1)).addStudentRecord(anyLong(), anyLong(),
                anyString(), anyLong(), anyString(), anyLong(), anyString(),  anyString(),  anyString());
    }
}