package org.rocs.osd.model.disciplinarystatus;

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
        // Intentionally empty constructor for disciplinary status model
    }

    /**
     * Constructor to initialize all fields.
     * @param pDisciplinaryStatusId unique ID of the status.
     * @param pStatus name of the status.
     * @param pDescription description of the status.
     */
    public DisciplinaryStatus(long pDisciplinaryStatusId,
                              String pStatus,
                              String pDescription) {
        this.disciplinaryStatusId = pDisciplinaryStatusId;
        this.status = pStatus;
        this.description = pDescription;
    }

    /** @return the unique ID of the disciplinary status. */
    public long getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    /** @param pDisciplinaryStatusId sets the unique ID of the status. */
    public void setDisciplinaryStatusId(long pDisciplinaryStatusId) {
        this.disciplinaryStatusId = pDisciplinaryStatusId;
    }

    /** @return the name of the disciplinary status. */
    public String getStatus() {
        return status;
    }

    /** @param pStatus sets the name of the disciplinary status. */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /** @return the description of the disciplinary status. */
    public String getDescription() {
        return description;
    }

    /** @param pDescription sets the description of the status. */
    public void setDescription(String pDescription) {
        this.description = pDescription;
    }
}
