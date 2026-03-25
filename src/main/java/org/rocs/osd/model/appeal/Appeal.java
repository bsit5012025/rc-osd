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

    /** Date when the appeal was processed.*/
    private Date dateProcessed;

    /** Message or remarks for the denying the appeal. */
    private String remarks;

    /** Default constructor to initialize an empty Appeal object. */
    public Appeal() {
    }

    /**
     * Constructor to create a complete Appeal object with all fields.
     *
     * @param pAppealID unique identifier for the appeal.
     * @param pRecord student record associated with this appeal.
     * @param pEnrollment enrollment information of the
     *                   student filing the appeal.
     * @param pMessage message or reason for the appeal.
     * @param pDateFiled date when the appeal was filed.
     * @param pStatus current status of the appeal.
     * @param pDateProcessed date when the appeal was processed.
     * @param pRemarks message or remarks for the denying the appeal.
     */
    public Appeal(long pAppealID, Record pRecord, Enrollment pEnrollment,
                  String pMessage, Date pDateFiled, String pStatus,
                  Date pDateProcessed, String pRemarks) {
        this.appealID = pAppealID;
        this.record = pRecord;
        this.enrollment = pEnrollment;
        this.message = pMessage;
        this.dateFiled = pDateFiled;
        this.status = pStatus;
        this.dateProcessed = pDateProcessed;
        this.remarks = pRemarks;
    }

    /**
     * Constructor to create an Appeal without appealID or dateFiled.
     *
     * @param pRecord student record associated with this appeal.
     * @param pEnrollment enrollment information of the
     *                   student filing the appeal.
     * @param pMessage message or reason for the appeal.
     * @param pStatus current status of the appeal.
     */
    public Appeal(Record pRecord, Enrollment pEnrollment,
                  String pMessage, String pStatus) {
        this.record = pRecord;
        this.enrollment = pEnrollment;
        this.message = pMessage;
        this.status = pStatus;
    }

    /** @return the unique ID of this appeal. */
    public long getAppealID() {
        return appealID;
    }

    /** @param pAppealID sets the unique ID of this appeal. */
    public void setAppealID(Long pAppealID) {
        this.appealID = pAppealID;
    }

    /** @return the related student record. */
    public Record getRecord() {
        return record;
    }

    /** @param pRecord sets the related student record. */
    public void setRecord(Record pRecord) {
        this.record = pRecord;
    }

    /** @return the enrollment associated with the appeal. */
    public Enrollment getEnrollment() {
        return enrollment;
    }

    /** @param pEnrollment sets the enrollment associated with the appeal. */
    public void setEnrollment(Enrollment pEnrollment) {
        this.enrollment = pEnrollment;
    }

    /** @return the message or reason for the appeal. */
    public String getMessage() {
        return message;
    }

    /** @param pMessage sets the message or reason for the appeal. */
    public void setMessage(String pMessage) {
        this.message = pMessage;
    }

    /** @return the date this appeal was filed. */
    public Date getDateFiled() {
        return dateFiled;
    }

    /** @param mDateFiled sets the date this appeal was filed. */
    public void setDateFiled(Date mDateFiled) {
        this.dateFiled = mDateFiled;
    }

    /** @return the current status of the appeal. */
    public String getStatus() {
        return status;
    }

    /** @param pStatus sets the current status of the appeal. */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /** @return the date when appeal is processed. */
    public Date getDateProcessed() {
        return dateProcessed;
    }

    /** @param pDateProcessed sets the date when appeal is processed */
    public void setDateProcessed(Date pDateProcessed) {
        this.dateProcessed = pDateProcessed;
    }

    /** @return the current remark message for deniedAppeal tab. */
    public String getRemarks() {
        return remarks;
    }

    /** @param pRemarks sets the remark message for deny button. */
    public void setRemarks(String pRemarks) {
        this.remarks = pRemarks;
    }
}
