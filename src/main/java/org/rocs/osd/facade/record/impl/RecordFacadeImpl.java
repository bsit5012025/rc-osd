package org.rocs.osd.facade.record.impl;

import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.record.RecordFacade;

public class RecordFacadeImpl implements RecordFacade {

    private final RecordDao recordDao;

    public RecordFacadeImpl(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @Override
    public boolean resolveRecord(long recordID) {
        if (recordID <= 0) {
            return false;
        }

        return recordDao.updateStudentRecordStatusById(recordID, "RESOLVED");
    }
}
