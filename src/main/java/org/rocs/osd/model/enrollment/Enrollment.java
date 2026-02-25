package org.rocs.osd.model.enrollment;

/**
 * Represents Enrollment in the system.
 * This class stores information about a student's enrollment record.
 */
public class Enrollment {
    /** Unique identifier of the enrollment */
    private long enrollmentId;
    /** Unique identifier of the student */
    private String studentId;
    /** Academic school year of the enrollment */
    private String schoolYear;
    /** Student level */
    private String studentLevel;
    /** Section of the student */
    private String section;
    /** Department identifier */
    private String departmentId;
    /** Disciplinary status identifier */
    private String disciplinaryStatusId;

    /**
     * Default constructor.
     */
    public Enrollment() {
    }

    /**
     * Constructs an Enrollment with all required fields.
     * @param enrollmentId the unique enrollment ID.
     * @param studentId the student ID.
     * @param schoolYear the school year.
     * @param studentLevel the student level.
     * @param section the assigned section.
     * @param departmentId the department ID.
     * @param disciplinaryStatusId  the disciplinary status ID.
     */
    public Enrollment(long enrollmentId, String studentId, String schoolYear, String studentLevel, String section, String departmentId, String disciplinaryStatusId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.schoolYear = schoolYear;
        this.studentLevel = studentLevel;
        this.section = section;
        this.departmentId = departmentId;
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    /**
     * This will get the Enrollment ID
     * @return the enrollment ID
     */

    public long getEnrollmentId() {
        return enrollmentId;
    }

    /**
     * This will set the Enrollment ID
     */

    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    /**
     *  This will get the Student ID
     *   @return the student ID
     */

    public String getStudentId() {
        return studentId;
    }

    /**
     * This will set the Student ID
     */

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     *  This will get the School Year
     *  @return the school year
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     *  This will set the School Year
     */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     *  This will get the Student Level
     *  @return the student level
     */
    public String getStudentLevel() {
        return studentLevel;
    }

    /**
     *   This will set the Student Level
     */

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    /**
     * This will get the Section
     *  @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     *   This will set the Section
     */

    public void setSection(String section) {
        this.section = section;
    }

    /**
     *   This will get the Department ID
     * @return the department ID
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This will set the Department ID
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     *  This will get the Disciplinary Status ID
     * @return the Disciplinary Status ID
     */
    public String getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    /**
     * This will set the Disciplinary Status ID
     */
    public void setDisciplinaryStatusId(String disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }
}
