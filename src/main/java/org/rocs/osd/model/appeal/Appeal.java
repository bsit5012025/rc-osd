package org.rocs.osd.model.appeal;

import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.record.Record;

import java.util.Date;


/**
 * Represents an appeal filed by a student in the Office of Student Discipline System.
 * This class contains details such as the related record, enrollment, message, filing date, status, student information, and associated offense.
 */
public class Appeal {

    private long appealID;
    private Record record;
    private Enrollment enrollment;
    private String message;
    private Date dateFiled;
    private String status;

    /**
     * Default constructor for Appeal.
     * Initializes an empty Appeal object.
     */
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

    /**
     *	@param appealID sets the unique appeal ID.
     */
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

    /**
     * @return the message or reason of the appeal.
     */
    public String getMessage() {
        return message;
    }

    /**
     *	@param message sets the message or reason of the appeal.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the date the appeal was filed.
     */
    public Date getDateFiled() {
        return dateFiled;
    }

    /**
     *	@param dateFiled sets the date the appeal was filed.
     */
    public void setDateFiled(Date dateFiled) {
        this.dateFiled = dateFiled;
    }

    /**
     * @return the current status of the appeal.
     */
    public String getStatus() {
        return status;
    }

    /**
     *	@param status sets the current status of the appeal.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}