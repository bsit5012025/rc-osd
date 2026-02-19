package org.rocs.osd.model.disciplinaryStatus;

public class DisciplinaryStatus {

    private long disciplinaryStatusId;
    private String status;
    private String description;

    public DisciplinaryStatus() {
    }

    // Constructor with all values
    public DisciplinaryStatus(long disciplinaryStatusId, String status, String description) {
        this.disciplinaryStatusId = disciplinaryStatusId;
        this.status = status;
        this.description = description;
    }

    //This will get the Disciplinary Status ID
    public long getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    //This will set the Disciplinary Status ID
    public void setDisciplinaryStatusId(long disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    //This will get the Status
    public String getStatus() {
        return status;
    }

    //This will set the Status
    public void setStatus(String status) {
        this.status = status;
    }

    //This will get the Description
    public String getDescription() {
        return description;
    }

    //This will set the Description
    public void setDescription(String description) {
        this.description = description;
    }
}
