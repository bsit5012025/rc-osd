package org.rocs.osd.model.record;

import java.util.Date;

public class Record {
    private String recordId;
    private String enrollmentId;
    private String employeeId;
    private String offenseId;
    private Date dateOfViolation;
    private String actionId;
    private Date dateOfResolution;
    private String remarks;
    private String status;

    public Record() {
    }

    public Record(String recordId, String enrollmentId, String employeeId, String offenseId, Date dateOfViolation, String actionId, Date dateOfResolution, String remarks, String status) {
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOffenseId() {
        return offenseId;
    }

    public void setOffenseId(String offenseId) {
        this.offenseId = offenseId;
    }

    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
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