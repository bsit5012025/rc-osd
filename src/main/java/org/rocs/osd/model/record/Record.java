package org.rocs.osd.model.record;

import org.rocs.osd.model.disciplinaryaction.DisciplinaryAction;
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
        //Intentionally empty constructor for record model
    }

    /**
     * Constructor with all fields to create a complete Record object.
     *
     * @param pRecordId unique ID of the record.
     * @param pEnrollment pEnrollment associated with the student.
     * @param pEmployee pEmployee who recorded the pOffense.
     * @param pOffense pOffense committed by the student.
     * @param pDateOfViolation date when the violation occurred.
     * @param pAction disciplinary pAction applied.
     * @param pDateOfResolution date when the record was resolved.
     * @param pRemarks additional pRemarks about the record.
     * @param pStatus current pStatus of the record.
     */
    public Record(long pRecordId, Enrollment pEnrollment,
                  Employee pEmployee, Offense pOffense,
                  Date pDateOfViolation, DisciplinaryAction pAction,
                  Date pDateOfResolution, String pRemarks,
                  RecordStatus pStatus) {
        this.recordId = pRecordId;
        this.enrollment = pEnrollment;
        this.employee = pEmployee;
        this.offense = pOffense;
        this.dateOfViolation = pDateOfViolation;
        this.action = pAction;
        this.dateOfResolution = pDateOfResolution;
        this.remarks = pRemarks;
        this.status = pStatus;
    }

    /** @return the unique record ID */
    public long getRecordId() {
        return recordId;
    }

    /** @param pRecordId sets the unique record ID */
    public void setRecordId(long pRecordId) {
        this.recordId = pRecordId;
    }

    /** @return the enrollment associated with the student */
    public Enrollment getEnrollment() {
        return enrollment;
    }

    /** @param pEnrollment sets the pEnrollment associated with the student */
    public void setEnrollment(Enrollment pEnrollment) {
        this.enrollment = pEnrollment;
    }

    /** @return the employee who recorded the offense */
    public Employee getEmployee() {
        return employee;
    }

    /** @param pEmployee sets the pEmployee who recorded the offense */
    public void setEmployee(Employee pEmployee) {
        this.employee = pEmployee;
    }

    /** @return the offense committed by the student */
    public Offense getOffense() {
        return offense;
    }

    /** @param pOffense sets the pOffense committed by the student */
    public void setOffense(Offense pOffense) {
        this.offense = pOffense;
    }

    /** @return the date of the violation */
    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    /** @param pDateOfViolation sets the date of the violation */
    public void setDateOfViolation(Date pDateOfViolation) {
        this.dateOfViolation = pDateOfViolation;
    }

    /** @return the disciplinary action applied */
    public DisciplinaryAction getAction() {
        return action;
    }

    /** @param pAction sets the disciplinary pAction applied */
    public void setAction(DisciplinaryAction pAction) {
        this.action = pAction;
    }

    /** @return the date when the record was resolved */
    public Date getDateOfResolution() {
        return dateOfResolution;
    }

    /** @param pDateOfResolution sets the date when the record was resolved */
    public void setDateOfResolution(Date pDateOfResolution) {
        this.dateOfResolution = pDateOfResolution;
    }

    /** @return remarks or comments about the record */
    public String getRemarks() {
        return remarks;
    }

    /** @param pRemarks sets pRemarks or comments about the record */
    public void setRemarks(String pRemarks) {
        this.remarks = pRemarks;
    }

    /** @return the current status of the record */
    public RecordStatus getStatus() {
        return status;
    }

    /** @param pStatus sets the current pStatus of the record */
    public void setStatus(RecordStatus pStatus) {
        this.status = pStatus;
    }
}
