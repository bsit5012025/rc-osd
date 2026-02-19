package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.Date;
import java.util.List;

public interface OffenseDao
{
    Offense findOffenseById(String offenseID);
    List<String> findAllOffense();
}
