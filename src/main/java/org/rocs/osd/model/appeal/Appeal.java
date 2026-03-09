package org.rocs.osd.model.appeal;

import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.record.Record;

import java.util.Date;

public class Appeal {

    private long appealID;
    private Record record;
    private Enrollment enrollment;
    private String message;
    private Date dateFiled;
    private String status;

    public Appeal() {
    }

    public Appeal(long appealID, Record record, Enrollment enrollment, String message, Date dateFiled, String status) {
        this.appealID = appealID;
        this.record = record;
        this.enrollment = enrollment;
        this.message = message;
        this.dateFiled = dateFiled;
        this.status = status;
    }

    public Appeal(Record record, Enrollment enrollment, String message, String status) {
        this.record = record;
        this.enrollment = enrollment;
        this.message = message;
        this.status = status;
    }
    public long getAppealID() {
        return appealID;
    }

    public void setAppealID(Long appealID) {
        this.appealID = appealID;
    }

    public Record getRecord() {return record;}

    public void setRecord(Record record) {
        this.record = record;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
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