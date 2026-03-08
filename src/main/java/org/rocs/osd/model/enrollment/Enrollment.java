package org.rocs.osd.model.enrollment;

/**
 * Represents a student's Enrollment record in the system.
 * Stores information such as student ID, school year, level, section, department, and disciplinary status.
 */

public class Enrollment {
    private long enrollmentId;
    private String studentId;
    private String schoolYear;
    private String studentLevel;
    private String section;
    private String departmentId;
    private String disciplinaryStatusId;

    /**
     * Default constructor.
     * Initializes an empty Enrollment object.
     */

    public Enrollment() {
    }

    /**
     * Constructor to create an Enrollment with all required fields.
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
     * @return the unique enrollment ID.
     */
    public long getEnrollmentId() {
        return enrollmentId;
    }

    /**
     *	@param enrollmentId sets the unique enrollment ID.
     */
    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    /**
     * @return the student ID.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     *	@param studentId sets the student ID.
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the school year.
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     *	@param schoolYear sets the school year.
     */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * @return the student level.
     */
    public String getStudentLevel() {
        return studentLevel;
    }

    /**
     *	@param studentLevel sets the student level.
     */
    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    /**
     * @return the section.
     */
    public String getSection() {
        return section;
    }

    /**
     *	@param section sets the section.
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the department ID.
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     *	@param departmentId sets the department ID.
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the disciplinary status ID.
     */
    public String getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    /**
     *	@param disciplinaryStatusId sets the disciplinary status ID.
     */
    public void setDisciplinaryStatusId(String disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }
}
