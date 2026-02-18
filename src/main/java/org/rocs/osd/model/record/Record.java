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

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public long getOffenseId() {
        return offenseId;
    }

    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public Date getDateOfResolution() {
        return dateOfResolution;
    }

    public void setDateOfResolution(Date dateOfResolution) {
        this.dateOfResolution = dateOfResolution;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}