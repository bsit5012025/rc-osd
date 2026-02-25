package org.rocs.osd.model.record;

import java.util.Date;

public class Record {
    private long recordId;
    private long enrollmentId;
    private String employeeId;
    private long offenseId;
    private Date dateOfViolation;
    private long actionId;
    private Date dateOfResolution;
    private String remarks;
    private String status;

    public Record() {
    }

    public Record(long recordId, long enrollmentId, String employeeId, long offenseId, Date dateOfViolation, long actionId, Date dateOfResolution, String remarks, String status) {
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

    //This will get the Record ID
    public long getRecordId() {
        return recordId;
    }

    //This will set the Record ID
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    //This will get the Enrollment ID
    public long getEnrollmentId() {
        return enrollmentId;
    }

    //This will set the Enrollment ID
    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    //This will get the Employee ID
    public String getEmployeeId() {
        return employeeId;
    }

    //This will set the Employee ID
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    //This will get the Offense ID
    public long getOffenseId() {
        return offenseId;
    }

    //This will set the Offense ID
    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    //This will get the Date of Violation
    public Date getDateOfViolation() {
        return dateOfViolation;
    }


    //This will set the Date of Violation
    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }


    //This will get the Action ID
    public long getActionId() {
        return actionId;
    }


    //This will set the Action ID
    public void setActionId(long actionId) {
        this.actionId = actionId;
    }


    //This will get the Date of Resolution
    public Date getDateOfResolution() {
        return dateOfResolution;
    }


    //This will set the Date of Resolution
    public void setDateOfResolution(Date dateOfResolution) {
        this.dateOfResolution = dateOfResolution;
    }


    //This will get the Remarks
    public String getRemarks() {
        return remarks;
    }

    //This will set the Remarks
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    //This will get the Status
    public String getStatus() {
        return status;
    }

    //This will set the Status
    public void setStatus(String status) {
        this.status = status;
    }
}