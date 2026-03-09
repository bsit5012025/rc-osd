package org.rocs.osd.model.record;

import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;

import java.util.Date;

public class Record {
    private long recordId;
    private Enrollment enrollment;
    private Employee employee;
    private Offense offense;
    private Date dateOfViolation;
    private DisciplinaryAction action;
    private Date dateOfResolution;
    private String remarks;
    private RecordStatus status;

    public Record() {
    }

    public Record(long recordId, Enrollment enrollment, Employee employee, Offense offense, Date dateOfViolation, DisciplinaryAction action, Date dateOfResolution, String remarks, RecordStatus status) {
        this.recordId = recordId;
        this.enrollment = enrollment;
        this.employee = employee;
        this.offense = offense;
        this.dateOfViolation = dateOfViolation;
        this.action = action;
        this.dateOfResolution = dateOfResolution;
        this.remarks = remarks;
        this.status = status;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Offense getOffense() {
        return offense;
    }

    public void setOffense(Offense offense) {
        this.offense = offense;
    }

    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }

    public DisciplinaryAction getAction() {
        return action;
    }

    public void setAction(DisciplinaryAction action) {
        this.action = action;
    }

    public Date getDateOfResolution() {
        return dateOfResolution;
    }

    public void setDateOfResolution(Date dateOfResolution) {
        this.dateOfResolution = dateOfResolution;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }
}