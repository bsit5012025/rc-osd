package org.rocs.osd.model.person.studentGuardian;

import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.student.Student;

public class StudentGuardian {

    private Student student;
    private Guardian guardian;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }
}