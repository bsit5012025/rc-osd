package org.rocs.osd.model.appeal;

import java.util.Date;

public class Appeal {

    private Long appealID;
    private Long recordID;
    private Long enrollmentID;
    private String message;
    private Date dateFiled;
    private String status;
    private String studentId;
    private String studentName;
    private String offense;

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
    public Long getAppealID() {
        return appealID;
    }

    public void setAppealID(Long appealID) {
        this.appealID = appealID;
    }

    public Long getRecordID() {
        return recordID;
    }

    public void setRecordID(Long recordID) {
        this.recordID = recordID;
    }

    public Long getEnrollmentID() {
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getOffense() {
        return offense;
    }

    public void setOffense(String offense) {
        this.offense = offense;
    }
}