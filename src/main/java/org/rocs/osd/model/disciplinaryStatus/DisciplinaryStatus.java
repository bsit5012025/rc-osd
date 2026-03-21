package org.rocs.osd.model.disciplinaryStatus;

/**
 * Represents a disciplinary status in the system.
 * Stores status name, ID, and description for a record.
 */
public class DisciplinaryStatus {

    /** Unique ID of the disciplinary status. */
    private long disciplinaryStatusId;

    /** Name of the disciplinary status. */
    private String status;

    /** Description of the disciplinary status. */
    private String description;

    /** Default constructor, creates an empty object. */
    public DisciplinaryStatus() {
    }

    /**
     * Constructor to initialize all fields.
     * @param disciplinaryStatusId unique ID of the status.
     * @param status name of the status.
     * @param description description of the status.
     */
    public DisciplinaryStatus(long disciplinaryStatusId,
                              String status,
                              String description) {
        this.disciplinaryStatusId = disciplinaryStatusId;
        this.status = status;
        this.description = description;
    }

    /** @return the unique ID of the disciplinary status. */
    public long getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    /** @param disciplinaryStatusId sets the unique ID of the status. */
    public void setDisciplinaryStatusId(long disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    /** @return the name of the disciplinary status. */
    public String getStatus() {
        return status;
    }

    /** @param status sets the name of the disciplinary status. */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return the description of the disciplinary status. */
    public String getDescription() {
        return description;
    }

    /** @param description sets the description of the status. */
    public void setDescription(String description) {
        this.description = description;
    }
}