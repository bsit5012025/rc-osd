package org.rocs.osd.model.appeal;

import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.record.Record;
import java.util.Date;

/**
 * Represents a student appeal in the Office of Student Discipline System.
 * Contains the related student record, enrollment information, message,
 * date filed, and current status of the appeal.
 */
public class Appeal {

    /** Unique identifier for the appeal. */
    private long appealID;

    /** Student record associated with this appeal. */
    private Record record;

    /** Enrollment information of the student filing the appeal. */
    private Enrollment enrollment;

    /** Message or reason for the appeal. */
    private String message;

    /** Date when the appeal was filed.*/
    private Date dateFiled;

    /** Current status of the appeal. */
    private String status;

    /** Default constructor to initialize an empty Appeal object. */
    public Appeal() {
    }

    /**
     * Constructor to create a complete Appeal object with all fields.
     *
     * @param appealID unique identifier for the appeal.
     * @param record student record associated with this appeal.
     * @param enrollment enrollment information of the
     *                   student filing the appeal.
     * @param message message or reason for the appeal.
     * @param dateFiled date when the appeal was filed.
     * @param status current status of the appeal.
     */
    public Appeal(long appealID, Record record, Enrollment enrollment,
                  String message, Date dateFiled, String status) {
        this.appealID = appealID;
        this.record = record;
        this.enrollment = enrollment;
        this.message = message;
        this.dateFiled = dateFiled;
        this.status = status;
    }

    /**
     * Constructor to create an Appeal without appealID or dateFiled.
     *
     * @param record student record associated with this appeal.
     * @param enrollment enrollment information of the
     *                   student filing the appeal.
     * @param message message or reason for the appeal.
     * @param status current status of the appeal.
     */
    public Appeal(Record record, Enrollment enrollment,
                  String message, String status) {
        this.record = record;
        this.enrollment = enrollment;
        this.message = message;
        this.status = status;
    }

    /** @return the unique ID of this appeal. */
    public long getAppealID() {
        return appealID;
    }

    /** @param appealID sets the unique ID of this appeal. */
    public void setAppealID(Long appealID) {
        this.appealID = appealID;
    }

    /** @return the related student record. */
    public Record getRecord() {
        return record;
    }

    /** @param record sets the related student record. */
    public void setRecord(Record record) {
        this.record = record;
    }

    /** @return the enrollment associated with the appeal. */
    public Enrollment getEnrollment() {
        return enrollment;
    }

    /** @param enrollment sets the enrollment associated with the appeal. */
    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    /** @return the message or reason for the appeal. */
    public String getMessage() {
        return message;
    }

    /** @param message sets the message or reason for the appeal. */
    public void setMessage(String message) {
        this.message = message;
    }

    /** @return the date this appeal was filed. */
    public Date getDateFiled() {
        return dateFiled;
    }

    /** @param dateFiled sets the date this appeal was filed. */
    public void setDateFiled(Date dateFiled) {
        this.dateFiled = dateFiled;
    }

    /** @return the current status of the appeal. */
    public String getStatus() {
        return status;
    }

    /** @param status sets the current status of the appeal. */
    public void setStatus(String status) {
        this.status = status;
    }
}