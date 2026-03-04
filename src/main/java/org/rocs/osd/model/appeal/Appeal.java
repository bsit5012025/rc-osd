package org.rocs.osd.model.appeal;

import java.sql.Date;

public class Appeal {

    private Long appealID;
    private Long recordID;
    private Long enrollmentID;
    private String message;
    private Date dateFiled;
    private String status;

    public Appeal() {
    }

    public Appeal(Long appealID, Long recordID, Long enrollmentID, String message, Date dateFiled, String status) {
        this.appealID = appealID;
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.dateFiled = dateFiled;
        this.status = status;
    }

    public Appeal(Long recordID, Long enrollmentID, String message, String status) {
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.status = status;
    }

    public Long getAppealID() { return appealID; }
    public void setAppealID(Long appealID) { this.appealID = appealID; }

    public Long getRecordID() { return recordID; }
    public void setRecordID(Long recordID) { this.recordID = recordID; }

    public Long getEnrollmentID() { return enrollmentID; }
    public void setEnrollmentID(Long enrollmentID) { this.enrollmentID = enrollmentID; }

    public String getAppealMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getDateFiled() { return dateFiled; }
    public void setDateFiled(Date dateFiled) { this.dateFiled = dateFiled; }

    public String getAppealStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}