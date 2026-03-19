package org.rocs.osd.model.person.studentGuardian;

import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.student.Student;

/**
 * Represents the association between a student and their guardian.
 */
public class StudentGuardian {

    private Student student;
    private Guardian guardian;

    /**
     * Returns the student associated with this StudentGuardian.
     *
     * @return the student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student for this StudentGuardian.
     *
     * @param student the student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Returns the guardian associated with this StudentGuardian.
     *
     * @return the guardian.
     */
    public Guardian getGuardian() {
        return guardian;
    }

    /**
     * Sets the guardian for this StudentGuardian.
     *
     * @param guardian the guardian to set.
     */
    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }
}