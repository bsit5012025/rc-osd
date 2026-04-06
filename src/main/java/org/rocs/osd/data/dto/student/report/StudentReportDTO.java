package org.rocs.osd.data.dto.student.report;

/**
 * Data Transfer Object representing a comprehensive student report.
 * This class encapsulates student demographics, guardian information,
 * and disciplinary record details for data transmission across layers.
 */
public class StudentReportDTO {

    /** The full name of the student. */
    private String studentName;

    /** The grade level of the student. */
    private String grade;

    /** The class section assignment. */
    private String section;

    /** The current academic year. */
    private String academicYear;

    /** The student's residential address. */
    private String studentAddress;

    /** The name of the student's guardian. */
    private String guardianName;

    /** The guardian's residential address. */
    private String guardianAddress;

    /** The primary contact number. */
    private String contactNumber;

    /** The current status of the report. */
    private String status;

    /** Internal check/validation flag. */
    private String internCheck;

    /** External check/validation flag. */
    private String externCheck;

    /** The category or type of offense committed. */
    private String offenseType;

    /** The severity level of the offense. */
    private String levelOfOffense;

    /** The date the incident occurred or was recorded. */
    private String date;

    /**
     * Gets the student's home address.
     * @return the student address
     */
    public String getStudentAddress() {
        return studentAddress;
    }

    /**
     * Sets the student's home address.
     * @param pStudentAddress the student address to set
     */
    public void setStudentAddress(String pStudentAddress) {
        this.studentAddress = pStudentAddress;
    }

    /**
     * Gets the full name of the student.
     * @return the student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the full name of the student.
     * @param pStudentName the student name to set
     */
    public void setStudentName(String pStudentName) {
        this.studentName = pStudentName;
    }

    /**
     * Gets the grade level of the student.
     * @return the grade level
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets the grade level of the student.
     * @param pGrade the grade level to set
     */
    public void setGrade(String pGrade) {
        this.grade = pGrade;
    }

    /**
     * Gets the class section assignment.
     * @return the section name
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the class section assignment.
     * @param pSection the section name to set
     */
    public void setSection(String pSection) {
        this.section = pSection;
    }

    /**
     * Gets the current academic year for the report.
     * @return the academic year (e.g., "2025-2026")
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * Sets the current academic year for the report.
     * @param pAcademicYear the academic year to set
     */
    public void setAcademicYear(String pAcademicYear) {
        this.academicYear = pAcademicYear;
    }

    /**
     * Gets the name of the student's guardian.
     * @return the guardian name
     */
    public String getGuardianName() {
        return guardianName;
    }

    /**
     * Sets the name of the student's guardian.
     * @param pGuardianName the guardian name to set
     */
    public void setGuardianName(String pGuardianName) {
        this.guardianName = pGuardianName;
    }

    /**
     * Gets the guardian's residential address.
     * @return the guardian address
     */
    public String getGuardianAddress() {
        return guardianAddress;
    }

    /**
     * Sets the guardian's residential address.
     * @param pGuardianAddress the guardian address to set
     */
    public void setGuardianAddress(String pGuardianAddress) {
        this.guardianAddress = pGuardianAddress;
    }

    /**
     * Gets the primary contact number for the student or guardian.
     * @return the contact number
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Sets the primary contact number for the student or guardian.
     * @param pContactNumber the contact number to set
     */
    public void setContactNumber(String pContactNumber) {
        this.contactNumber = pContactNumber;
    }

    /**
     * Gets the current status of the report or student.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the report or student.
     * @param pStatus the status to set
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     * Gets the internal validation/check status.
     * @return the intern check value
     */
    public String getInternCheck() {
        return internCheck;
    }

    /**
     * Sets the internal validation/check status.
     * @param pInternCheck the intern check value to set
     */
    public void setInternCheck(String pInternCheck) {
        this.internCheck = pInternCheck;
    }

    /**
     * Gets the external validation/check status.
     * @return the extern check value
     */
    public String getExternCheck() {
        return externCheck;
    }

    /**
     * Sets the external validation/check status.
     * @param pExternCheck the extern check value to set
     */
    public void setExternCheck(String pExternCheck) {
        this.externCheck = pExternCheck;
    }

    /**
     * Gets the category or type of offense committed.
     * @return the offense type
     */
    public String getOffenseType() {
        return offenseType;
    }

    /**
     * Sets the category or type of offense committed.
     * @param pOffenseType the offense type to set
     */
    public void setOffenseType(String pOffenseType) {
        this.offenseType = pOffenseType;
    }

    /**
     * Gets the severity level of the offense.
     * @return the level of offense
     */
    public String getLevelOfOffense() {
        return levelOfOffense;
    }

    /**
     * Sets the severity level of the offense.
     * @param pLevelOfOffense the level of offense to set
     */
    public void setLevelOfOffense(String pLevelOfOffense) {
        this.levelOfOffense = pLevelOfOffense;
    }

    /**
     * Gets the date the incident occurred or the report was filed.
     * @return the date string
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date the incident occurred or the report was filed.
     * @param pDate the date string to set
     */
    public void setDate(String pDate) {
        this.date = pDate;
    }
}
