package org.rocs.osd.model.enrollment;

public class Enrollment {
    private long enrollmentId;
    private String studentId;
    private String schoolYear;
    private String studentLevel;
    private String section;
    private String departmentId;
    private String disciplinaryStatusId;

    public Enrollment() {
    }

    // Constructor with all values
    public Enrollment(long enrollmentId, String studentId, String schoolYear, String studentLevel, String section, String departmentId, String disciplinaryStatusId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.schoolYear = schoolYear;
        this.studentLevel = studentLevel;
        this.section = section;
        this.departmentId = departmentId;
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    //This will get the Enrollment ID
    public long getEnrollmentId() {
        return enrollmentId;
    }

    //This will set the Enrollment ID
    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    //This will get the Student ID
    public String getStudentId() {
        return studentId;
    }

    //This will set the Student ID
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    //This will get the School Year
    public String getSchoolYear() {
        return schoolYear;
    }

    //This will set the School Year
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    //This will get the Student Level
    public String getStudentLevel() {
        return studentLevel;
    }

    //This will set the Student Level
    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    //This will get the Section
    public String getSection() {
        return section;
    }

    //This will set the Section
    public void setSection(String section) {
        this.section = section;
    }

    //This will get the Department ID
    public String getDepartmentId() {
        return departmentId;
    }

    //This will set the Department ID
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    //This will get the Disciplinary Status ID
    public String getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    //This will set the Disciplinary Status ID
    public void setDisciplinaryStatusId(String disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }
}
