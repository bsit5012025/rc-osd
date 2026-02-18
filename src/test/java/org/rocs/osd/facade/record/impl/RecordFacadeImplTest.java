package org.rocs.osd.facade.record.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.record.RecordDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordFacadeImplTest {
    @Mock
    private RecordDao recordDao;

    @InjectMocks
    private RecordFacadeImpl recordFacade;

    @Test
    void resolveRecordShouldCallDaoAndReturnTrue() {
        when(recordDao.updateStudentRecordStatusById(5L, "RESOLVED"))
                .thenReturn(true);

        boolean result = recordFacade.resolveRecord(5L);

        assertTrue(result);
        verify(recordDao).updateStudentRecordStatusById(5L, "RESOLVED");
    }
}