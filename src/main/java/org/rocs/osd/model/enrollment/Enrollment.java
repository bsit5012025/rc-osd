package org.rocs.osd.model.enrollment;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryStatus.DisciplinaryStatus;
import org.rocs.osd.model.person.student.Student;

/**
 * Represents an Enrollment entity in the Office of Student Discipline System.
 * Contains the enrollment ID, student details, school year, level, section, department, and disciplinary status.
 */
public class Enrollment {
    private long enrollmentId;
    private Student student;
    private String schoolYear;
    private String studentLevel;
    private String section;
    private Department department;
    private DisciplinaryStatus disciplinaryStatus;

    /**
     * Default constructor.
     * Initializes an empty Enrollment object.
     */
    public Enrollment() {
    }

    /**
     * Constructor to create an Enrollment with all values.
     * @param enrollmentId the unique identifier of the enrollment.
     * @param student the student associated with this enrollment.
     * @param schoolYear the school year of the enrollment.
     * @param studentLevel the level or grade of the student.
     * @param section the section of the student.
     * @param department the department the student belongs to.
     * @param disciplinaryStatus the disciplinary status of the student.
     */
    public Enrollment(long enrollmentId, Student student, String schoolYear, String studentLevel, String section, Department department, DisciplinaryStatus disciplinaryStatus) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.schoolYear = schoolYear;
        this.studentLevel = studentLevel;
        this.section = section;
        this.department = department;
        this.disciplinaryStatus = disciplinaryStatus;
    }

    /**
     * @return the unique ID of the enrollment.
     */
    public long getEnrollmentId() {
        return enrollmentId;
    }

    /**
     * @param enrollmentId sets the unique ID of the enrollment.
     */
    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }


    /**
     * @return the student associated with the enrollment.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student sets the student associated with the enrollment.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the school year of the enrollment.
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     * @param schoolYear sets the school year of the enrollment.
     */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * @return the level or grade of the student.
     */
    public String getStudentLevel() {
        return studentLevel;
    }

    /**
     * @param studentLevel sets the level or grade of the student.
     */
    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    /**
     * @return the section of the student.
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section sets the section of the student.
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the department of the student.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department sets the department of the student.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * @return the disciplinary status of the student.
     */
    public DisciplinaryStatus getDisciplinaryStatus() {
        return disciplinaryStatus;
    }

    /**
     * @param disciplinaryStatus sets the disciplinary status of the student.
     */
    public void setDisciplinaryStatus(DisciplinaryStatus disciplinaryStatus) {
        this.disciplinaryStatus = disciplinaryStatus;
    }
}
