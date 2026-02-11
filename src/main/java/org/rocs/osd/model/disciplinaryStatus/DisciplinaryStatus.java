package org.rocs.osd.model.disciplinaryStatus;

public class DisciplinaryStatus {

    private String disciplinaryStatusId;
    private String status;
    private String description;

    public DisciplinaryStatus() {
    }

    public DisciplinaryStatus(String disciplinaryStatusId, String status, String description) {
        this.disciplinaryStatusId = disciplinaryStatusId;
        this.status = status;
        this.description = description;
    }

    public String getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    public void setDisciplinaryStatusId(String disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
