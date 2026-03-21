package org.rocs.osd.model.record;

import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;

import java.util.Date;

/**
 * Represents a student's disciplinary record
 * in the Office of Student Discipline.
 *
 * Contains enrollment, offense details,
 * employee who recorded it, action, dates,
 * remarks, and status.
 */
public class Record {

    /** Unique identifier for the record. */
    private long recordId;

    /** Enrollment associated with the student. */
    private Enrollment enrollment;

    /** Employee who recorded the offense. */
    private Employee employee;

    /** Offense committed by the student. */
    private Offense offense;

    /** Date when the violation occurred. */
    private Date dateOfViolation;

    /** Disciplinary action applied to the offense. */
    private DisciplinaryAction action;

    /** Date when the record was resolved. */
    private Date dateOfResolution;

    /** Additional remarks or comments for the record. */
    private String remarks;

    /** Current status of the record. */
    private RecordStatus status;

    /**
     * Default constructor.
     * Initializes an empty Record object.
     */
    public Record() {
    }

    /**
     * Constructor with all fields to create a complete Record object.
     *
     * @param recordId unique ID of the record.
     * @param enrollment enrollment associated with the student.
     * @param employee employee who recorded the offense.
     * @param offense offense committed by the student.
     * @param dateOfViolation date when the violation occurred.
     * @param action disciplinary action applied.
     * @param dateOfResolution date when the record was resolved.
     * @param remarks additional remarks about the record.
     * @param status current status of the record.
     */
    public Record(long recordId, Enrollment enrollment,
                  Employee employee, Offense offense,
                  Date dateOfViolation, DisciplinaryAction action,
                  Date dateOfResolution, String remarks,
                  RecordStatus status) {
        this.recordId = recordId;
        this.enrollment = enrollment;
        this.employee = employee;
        this.offense = offense;
        this.dateOfViolation = dateOfViolation;
        this.action = action;
        this.dateOfResolution = dateOfResolution;
        this.remarks = remarks;
        this.status = status;
    }

    /** @return the unique record ID */
    public long getRecordId() {
        return recordId;
    }

    /** @param recordId sets the unique record ID */
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    /** @return the enrollment associated with the student */
    public Enrollment getEnrollment() {
        return enrollment;
    }

    /** @param enrollment sets the enrollment associated with the student */
    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    /** @return the employee who recorded the offense */
    public Employee getEmployee() {
        return employee;
    }

    /** @param employee sets the employee who recorded the offense */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /** @return the offense committed by the student */
    public Offense getOffense() {
        return offense;
    }

    /** @param offense sets the offense committed by the student */
    public void setOffense(Offense offense) {
        this.offense = offense;
    }

    /** @return the date of the violation */
    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    /** @param dateOfViolation sets the date of the violation */
    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }

    /** @return the disciplinary action applied */
    public DisciplinaryAction getAction() {
        return action;
    }

    /** @param action sets the disciplinary action applied */
    public void setAction(DisciplinaryAction action) {
        this.action = action;
    }

    /** @return the date when the record was resolved */
    public Date getDateOfResolution() {
        return dateOfResolution;
    }

    /** @param dateOfResolution sets the date when the record was resolved */
    public void setDateOfResolution(Date dateOfResolution) {
        this.dateOfResolution = dateOfResolution;
    }

    /** @return remarks or comments about the record */
    public String getRemarks() {
        return remarks;
    }

    /** @param remarks sets remarks or comments about the record */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** @return the current status of the record */
    public RecordStatus getStatus() {
        return status;
    }

    /** @param status sets the current status of the record */
    public void setStatus(RecordStatus status) {
        this.status = status;
    }
}