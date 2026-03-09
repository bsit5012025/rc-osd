package org.rocs.osd.model.enrollment;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryStatus.DisciplinaryStatus;
import org.rocs.osd.model.person.student.Student;

public class Enrollment {
    private long enrollmentId;
    private Student student;
    private String schoolYear;
    private String studentLevel;
    private String section;
    private Department department;
    private DisciplinaryStatus disciplinaryStatus;

    public Enrollment() {
    }

    public Enrollment(long enrollmentId, Student student, String schoolYear, String studentLevel, String section, Department department, DisciplinaryStatus disciplinaryStatus) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.schoolYear = schoolYear;
        this.studentLevel = studentLevel;
        this.section = section;
        this.department = department;
        this.disciplinaryStatus = disciplinaryStatus;
    }

    public long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DisciplinaryStatus getDisciplinaryStatus() {
        return disciplinaryStatus;
    }

    public void setDisciplinaryStatus(DisciplinaryStatus disciplinaryStatus) {
        this.disciplinaryStatus = disciplinaryStatus;
    }
}
