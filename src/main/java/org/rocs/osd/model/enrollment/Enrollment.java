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

    public Enrollment(long enrollmentId, String studentId, String schoolYear, String studentLevel, String section, String departmentId, String disciplinaryStatusId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.schoolYear = schoolYear;
        this.studentLevel = studentLevel;
        this.section = section;
        this.departmentId = departmentId;
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    public long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    public void setDisciplinaryStatusId(String disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }
}
