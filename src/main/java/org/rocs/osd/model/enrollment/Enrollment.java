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
     * @param pEnrollmentId unique ID of the enrollment.
     * @param pStudent student associated with this enrollment.
     * @param pSchoolYear school year of the enrollment.
     * @param pStudentLevel level/grade of the student.
     * @param pSection section of the student.
     * @param pDepartment department the student belongs to.
     * @param pDisciplinaryStatus disciplinary status of the student.
     */
    public Enrollment(long pEnrollmentId, Student pStudent, String pSchoolYear,
                      String pStudentLevel, String pSection,
                      Department pDepartment,
                      DisciplinaryStatus pDisciplinaryStatus) {
        this.enrollmentId = pEnrollmentId;
        this.student = pStudent;
        this.schoolYear = pSchoolYear;
        this.studentLevel = pStudentLevel;
        this.section = pSection;
        this.department = pDepartment;
        this.disciplinaryStatus = pDisciplinaryStatus;
    }

    /** @return the unique ID of the enrollment */
    public long getEnrollmentId() {
        return enrollmentId;
    }

    /** @param pEnrollmentId sets the unique ID of the enrollment */
    public void setEnrollmentId(long pEnrollmentId) {
        this.enrollmentId = pEnrollmentId;
    }

    /** @return the student associated with the enrollment */
    public Student getStudent() {
        return student;
    }

    /** @param pStudent sets the student associated with the enrollment */
    public void setStudent(Student pStudent) {
        this.student = pStudent;
    }

    /** @return the school year of the enrollment */
    public String getSchoolYear() {
        return schoolYear;
    }

    /** @param pSchoolYear sets the school year of the enrollment */
    public void setSchoolYear(String pSchoolYear) {
        this.schoolYear = pSchoolYear;
    }

    /** @return the level or grade of the student */
    public String getStudentLevel() {
        return studentLevel;
    }

    /** @param pStudentLevel sets the level or grade of the student */
    public void setStudentLevel(String pStudentLevel) {
        this.studentLevel = pStudentLevel;
    }

    /** @return the section of the student */
    public String getSection() {
        return section;
    }

    /** @param pSection sets the section of the student */
    public void setSection(String pSection) {
        this.section = pSection;
    }

    /** @return the department of the student */
    public Department getDepartment() {
        return department;
    }

    /** @param pDepartment sets the department of the student */
    public void setDepartment(Department pDepartment) {
        this.department = pDepartment;
    }

    /** @return the disciplinary status of the student */
    public DisciplinaryStatus getDisciplinaryStatus() {
        return disciplinaryStatus;
    }

    /** @param pDisciplinaryStatus
     * sets the disciplinary status of the student */
    public void setDisciplinaryStatus(DisciplinaryStatus pDisciplinaryStatus) {
        this.disciplinaryStatus = pDisciplinaryStatus;
    }
}
