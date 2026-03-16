package org.rocs.osd.data.dao.record;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RecordDao
{
    List<Record> findAllBySchoolYear(String schoolYear);
    boolean addStudentRecord(long enrollmentID, String employeeID,
                             long offenseID, Date dateOfViolation, long actionID,
                             String remarks, RecordStatus status);
    boolean updateRecord(Record record);
    List<Record> findRecordListByDepartment(Department department, String schoolYear);
    int findTotalViolations(String schoolYear);
    int findTodayViolations();
    Map<String, Integer> findMostFrequentOffenses(String schoolYear);
}
