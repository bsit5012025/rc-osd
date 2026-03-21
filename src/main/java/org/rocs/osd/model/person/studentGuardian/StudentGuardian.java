package org.rocs.osd.model.person.studentGuardian;

import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.student.Student;

/**
 * Represents the association between a Student and their Guardian.
 * Contains references to both the student and the guardian.
 */
public class StudentGuardian {

    /** The student associated with this guardian. */
    private Student student;
    /** The guardian associated with this student. */
    private Guardian guardian;

    /** @return the associated Student object. */
    public Student getStudent() {
        return student;
    }

    /** @param student sets the associated Student object. */
    public void setStudent(Student student) {
        this.student = student;
    }

    /** @return the associated Guardian object. */
    public Guardian getGuardian() {
        return guardian;
    }

    /** @param guardian sets the associated Guardian object. */
    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }
}