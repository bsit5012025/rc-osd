package org.rocs.osd.model.appeal;

import java.util.Date;


/**
 * Represents an appeal filed by a student in the Office of Student Discipline System.
 * This class contains details such as the related record, enrollment, message, filing date, status, student information, and associated offense.
 */
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

    /**
     * Default constructor for Appeal.
     * Initializes an empty Appeal object.
     */
    public Appeal() {
    }

    /**
     * Constructor to create an Appeal with full details including ID and date filed.
     * @param appealID     unique identifier of the appeal.
     * @param recordID     ID of the related record.
     * @param enrollmentID ID of the student's enrollment.
     * @param message      message or reason for the appeal.
     * @param dateFiled    date when the appeal was filed.
     * @param status       current status of the appeal.
     */
    public Appeal(Long appealID, Long recordID, Long enrollmentID, String message, Date dateFiled, String status) {
        this.appealID = appealID;
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.dateFiled = dateFiled;
        this.status = status;
    }

    /**
     * Constructor to create an Appeal without appeal ID or date filed.
     * @param recordID     ID of the related record.
     * @param enrollmentID ID of the student's enrollment.
     * @param message      message or reason for the appeal.
     * @param status       current status of the appeal.
     */
    public Appeal(Long recordID, Long enrollmentID, String message, String status) {
        this.recordID = recordID;
        this.enrollmentID = enrollmentID;
        this.message = message;
        this.status = status;
    }
    /**
     * @return the unique appeal ID.
     */
    public Long getAppealID() {
        return appealID;
    }

    /**
     *	@param appealID sets the unique appeal ID.
     */
    public void setAppealID(Long appealID) {
        this.appealID = appealID;
    }

    /**
     * @return the related record ID.
     */
    public Long getRecordID() {
        return recordID;
    }

    /**
     *	@param recordID sets the related record ID
     */
    public void setRecordID(Long recordID) {
        this.recordID = recordID;
    }

    /**
     * @return the enrollment ID of the student.
     */
    public Long getEnrollmentID() {
        return enrollmentID;
    }

    /**
     *	@param enrollmentID sets the enrollment ID of the student.
     */
    public void setEnrollmentID(Long enrollmentID) {
        this.enrollmentID = enrollmentID;
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

    /**
     * @return the student's ID.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     *	@param studentId sets the student's ID.
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the student's full name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     *	@param studentName sets the student's full name.
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @return the offense associated with this appeal.
     */
    public String getOffense() {
        return offense;
    }

    /**
     *	@param offense sets the offense associated with this appeal.
     */
    public void setOffense(String offense) {
        this.offense = offense;
    }
}