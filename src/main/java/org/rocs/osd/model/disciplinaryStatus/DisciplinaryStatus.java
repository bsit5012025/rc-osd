package org.rocs.osd.model.disciplinaryStatus;

public class DisciplinaryStatus {

    private long disciplinaryStatusId;
    private String status;
    private String description;

    public DisciplinaryStatus() {
    }

    public DisciplinaryStatus(long disciplinaryStatusId, String status, String description) {
        this.disciplinaryStatusId = disciplinaryStatusId;
        this.status = status;
        this.description = description;
    }

    public long getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    public void setDisciplinaryStatusId(long disciplinaryStatusId) {
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
