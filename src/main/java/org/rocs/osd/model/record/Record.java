package org.rocs.osd.model.record;

import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;

import java.util.Date;

/**
 * Represents a Record entity in the Office of Student Discipline System.
 * Contains details about a student record, including enrollment, employee recording the offense, offense, disciplinary action, dates of violation/resolution, remarks, and status.
 */
public class Record {
    private long recordId;
    private Enrollment enrollment;
    private Employee employee;
    private Offense offense;
    private Date dateOfViolation;
    private DisciplinaryAction action;
    private Date dateOfResolution;
    private String remarks;
    private RecordStatus status;

    /**
     * Default constructor.
     * Initializes an empty Record object.
     */
    public Record() {
    }

    /**
     * Constructor to create a Record with all values.
     * @param recordId the unique ID of the record.
     * @param enrollment the enrollment associated with this record.
     * @param employee the employee who recorded the offense.
     * @param offense the offense committed.
     * @param dateOfViolation  the date the violation occurred.
     * @param action the disciplinary action taken.
     * @param dateOfResolution the date the record was resolved.
     * @param remarks additional remarks (optional).
     * @param status the current status of the record.
     */
    public Record(long recordId, Enrollment enrollment, Employee employee, Offense offense, Date dateOfViolation, DisciplinaryAction action, Date dateOfResolution, String remarks, RecordStatus status) {
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

    /**
     * @return the unique ID of the record.
     */
    public long getRecordId() {
        return recordId;
    }

    /**
     * @param recordId sets the unique ID of the record.
     */
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the enrollment associated with this record.
     */
    public Enrollment getEnrollment() {
        return enrollment;
    }

    /**
     * @param enrollment sets the enrollment associated with this record.
     */
    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    /**
     * @return the employee who recorded the offense.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee sets the employee who recorded the offense.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the offense associated with this record.
     */
    public Offense getOffense() {
        return offense;
    }

    /**
     * @param offense sets the offense associated with this record.
     */
    public void setOffense(Offense offense) {
        this.offense = offense;
    }

    /**
     * @return the date the violation occurred.
     */
    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    /**
     * @param dateOfViolation sets the date the violation occurred.
     */
    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }

    /**
     * @return the disciplinary action taken.
     */
    public DisciplinaryAction getAction() {
        return action;
    }

    /**
     * @param action sets the disciplinary action taken.
     */
    public void setAction(DisciplinaryAction action) {
        this.action = action;
    }

    /**
     * @return the date the record was resolved.
     */
    public Date getDateOfResolution() {
        return dateOfResolution;
    }

    /**
     * @param dateOfResolution sets the date the record was resolved.
     */
    public void setDateOfResolution(Date dateOfResolution) {
        this.dateOfResolution = dateOfResolution;
    }

    /**
     * @return any additional remarks about the record.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks sets additional remarks about the record.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the current status of the record.
     */
    public RecordStatus getStatus() {
        return status;
    }


    /**
     * @param status sets the current status of the record.
     */
    public void setStatus(RecordStatus status) {
        this.status = status;
    }
}