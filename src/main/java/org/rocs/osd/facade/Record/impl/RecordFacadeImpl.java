package org.rocs.osd.facade.Record.impl;

import org.rocs.osd.data.dao.record.RecordDao;

public class RecordFacadeImpl
{
    private RecordDao recordDao;

    public RecordFacadeImpl(RecordDao recordDao)
    {
        this.recordDao = recordDao;
    }
}
