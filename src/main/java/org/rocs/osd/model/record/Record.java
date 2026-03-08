package org.rocs.osd.model.record;

import java.util.Date;

/**
 * Represents a disciplinary record in the Office of Student Discipline System.
 * Stores information about the offense, responsible employee, action taken, and status.
 */
public class Record {

    private long recordId;
    private long enrollmentId;
    private String employeeId;
    private long offenseId;
    private Date dateOfViolation;
    private long actionId;
    private Date dateOfResolution;
    private String remarks;
    private RecordStatus status;


    /**
     * Default constructor
     */
    public Record() {
    }

    /**
     * Constructor to create a Record with all fields.
     * This constructor initializes a disciplinary record with all required details including student enrollment, employee, offense, action, and status information.
     * @param recordId unique record ID.
     * @param enrollmentId ID of the student enrollment.
     * @param employeeId ID of the employee.
     * @param offenseId ID of the offense.
     * @param dateOfViolation date of the violation.
     * @param actionId ID of the action taken.
     * @param dateOfResolution date of resolution.
     * @param remarks additional remarks for record.
     * @param status status of the record.
     */
    public Record(long recordId, long enrollmentId, String employeeId, long offenseId, Date dateOfViolation, long actionId, Date dateOfResolution, String remarks, RecordStatus status) {
        this.recordId = recordId;
        this.enrollmentId = enrollmentId;
        this.employeeId = employeeId;
        this.offenseId = offenseId;
        this.dateOfViolation = dateOfViolation;
        this.actionId = actionId;
        this.dateOfResolution = dateOfResolution;
        this.remarks = remarks;
        this.status = status;
    }


    /**
     * Gets the unique record ID.
     *
     * @return  recordId.
     */
    public long getRecordId() {
        return recordId;
    }

    /**
     * Sets the unique record ID.
     *
     * @param recordId  the recordId to set.
     */
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    /**
     * Gets the student enrollment ID associated with this record.
     *
     * @return  enrollmentId.
     */
    public long getEnrollmentId() {
        return enrollmentId;
    }

    /**
     * Sets the student enrollment ID associated with this record.
     *
     * @param enrollmentId  the enrollmentId to set.
     */
    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    /**
     * Gets the employee ID of the staff who recorded the offense.
     *
     * @return  employeeId.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID of the staff who recorded the offense.
     *
     * @param employeeId  the employeeId to set.
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the offense ID associated with this record.
     *
     * @return  offenseId.
     */
    public long getOffenseId() {
        return offenseId;
    }

    /**
     * Sets the offense ID associated with this record.
     *
     * @param offenseId  the offenseId to set.
     */
    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    /**
     * Gets the date of the violation.
     *
     * @return  dateOfViolation.
     */
    public Date getDateOfViolation() {
        return dateOfViolation;
    }


    /**
     * Sets the date of the violation.
     *
     * @param dateOfViolation  the dateOfViolation to set.
     */
    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }


    /**
     * Gets the action ID taken for this record.
     *
     * @return  actionId.
     */
    public long getActionId() {
        return actionId;
    }


    /**
     * Sets the action ID taken for this record.
     *
     * @param actionId  the actionId to set.
     */
    public void setActionId(long actionId) {
        this.actionId = actionId;
    }


    /**
     * Gets the date when the violation was resolved.
     *
     * @return  dateOfResolution.
     */
    public Date getDateOfResolution() {
        return dateOfResolution;
    }


    /**
     * Sets the date when the violation was resolved.
     *
     * @param dateOfResolution  the dateOfResolution to set.
     */
    public void setDateOfResolution(Date dateOfResolution) {
        this.dateOfResolution = dateOfResolution;
    }


    /**
     * Gets additional remarks for the record.
     *
     * @return  remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets additional remarks for the record
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    /**
     * Gets the status of the record.
     *
     * @return  status.
     */
    public RecordStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the record.
     *
     * @param status  the status to set.
     */
    public void setStatus(RecordStatus status) {
        this.status = status;
    }
}