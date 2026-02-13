package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.person.student.Student;

import java.util.ArrayList;

public interface OffenseDao
{
    boolean editExistingViolation(String recordID, String status, String OffenseID,
                                  String remarks, String DateOfResolution);

    boolean editExistingDateOfViolation(String recordID, String DateOfResolution);
}
