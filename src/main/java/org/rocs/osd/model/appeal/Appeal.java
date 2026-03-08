package org.rocs.osd.model.appeal;

import java.util.Date;

public class Appeal {

    private long appealID;
    private long recordID;
    private long enrollmentID;
    private String message;
    private Date dateFiled;
    private String status;

    public Appeal() {
    }

    public Appeal(long appealID, long recordID, long enrollmentID, String message, Date dateFiled, String status) {
        this.appealID = appealID;
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.dateFiled = dateFiled;
        this.status = status;
    }

    public Appeal(long recordID, long enrollmentID, String message, String status) {
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

    public long getRecordID() {
        return recordID;
    }

    public void setRecordID(Long recordID) {
        this.recordID = recordID;
    }

    public long getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Long enrollmentID) {
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