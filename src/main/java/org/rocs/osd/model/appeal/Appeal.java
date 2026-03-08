package org.rocs.osd.model.appeal;

import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.record.Record;

import java.util.Date;

public class Appeal {

    private long appealID;
    private Record recordID;
    private Enrollment enrollmentID;
    private String message;
    private Date dateFiled;
    private String status;

    public Appeal() {
    }

    public Appeal(long appealID, Record recordID, Enrollment enrollmentID, String message, Date dateFiled, String status) {
        this.appealID = appealID;
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.dateFiled = dateFiled;
        this.status = status;
    }

    public Appeal(Record recordID, Enrollment enrollmentID, String message, String status) {
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.status = status;
    }
    public long getAppealID() {
        return appealID;
    }

    public void setAppealID(Long appealID) {
        this.appealID = appealID;
    }

    public Record getRecordID() {
        return recordID;
    }

    public void setRecordID(Record recordID) {
        this.recordID = recordID;
    }

    public Enrollment getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Enrollment enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateFiled() {
        return dateFiled;
    }

    public void setDateFiled(Date dateFiled) {
        this.dateFiled = dateFiled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}