package org.rocs.osd.model.enrollment;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryStatus.DisciplinaryStatus;
import org.rocs.osd.model.person.student.Student;

/**
 * Represents an Enrollment in the OSD system.
 * Stores enrollment ID, student info, school year, level, section, department,
 * and disciplinary status.
 */
public class Enrollment {

    /** Unique ID of the enrollment. */
    private long enrollmentId;

    /** Student associated with this enrollment. */
    private Student student;

    /** School year of the enrollment. */
    private String schoolYear;

    /** Level or grade of the student. */
    private String studentLevel;

    /** Section of the student. */
    private String section;

    /** Department the student belongs to. */
    private Department department;

    /** Disciplinary status of the student. */
    private DisciplinaryStatus disciplinaryStatus;

    /** Default constructor, creates an empty Enrollment object. */
    public Enrollment() {
    }

    /**
     * Constructor to create an Enrollment with all values.
     * @param enrollmentId unique ID of the enrollment.
     * @param student student associated with this enrollment.
     * @param schoolYear school year of the enrollment.
     * @param studentLevel level/grade of the student.
     * @param section section of the student.
     * @param department department the student belongs to.
     * @param disciplinaryStatus disciplinary status of the student.
     */
    public Enrollment(long enrollmentId, Student student, String schoolYear,
                      String studentLevel, String section,
                      Department department,
                      DisciplinaryStatus disciplinaryStatus) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.schoolYear = schoolYear;
        this.studentLevel = studentLevel;
        this.section = section;
        this.department = department;
        this.disciplinaryStatus = disciplinaryStatus;
    }

    /** @return the unique ID of the enrollment */
    public long getEnrollmentId() {
        return enrollmentId;
    }

    /** @param enrollmentId sets the unique ID of the enrollment */
    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    /** @return the student associated with the enrollment */
    public Student getStudent() {
        return student;
    }

    /** @param student sets the student associated with the enrollment */
    public void setStudent(Student student) {
        this.student = student;
    }

    /** @return the school year of the enrollment */
    public String getSchoolYear() {
        return schoolYear;
    }

    /** @param schoolYear sets the school year of the enrollment */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    /** @return the level or grade of the student */
    public String getStudentLevel() {
        return studentLevel;
    }

    /** @param studentLevel sets the level or grade of the student */
    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    /** @return the section of the student */
    public String getSection() {
        return section;
    }

    /** @param section sets the section of the student */
    public void setSection(String section) {
        this.section = section;
    }

    /** @return the department of the student */
    public Department getDepartment() {
        return department;
    }

    /** @param department sets the department of the student */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /** @return the disciplinary status of the student */
    public DisciplinaryStatus getDisciplinaryStatus() {
        return disciplinaryStatus;
    }

    /** @param disciplinaryStatus sets the disciplinary status of the student */
    public void setDisciplinaryStatus(DisciplinaryStatus disciplinaryStatus) {
        this.disciplinaryStatus = disciplinaryStatus;
    }
}