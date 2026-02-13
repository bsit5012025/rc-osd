package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.person.student.Student;

import java.util.ArrayList;

public interface OffenseDao
{
    boolean editExistingViolationStatus();
    boolean editExistingViolationRemarks();
    boolean editExistingViolationDateOfResolution();
}
