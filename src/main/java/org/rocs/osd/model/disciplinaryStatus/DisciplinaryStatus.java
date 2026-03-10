package org.rocs.osd.model.disciplinaryStatus;

/**
 * Represents a Disciplinary Status in the system.
 * This class stores information about the status of a disciplinary record.
 */
public class DisciplinaryStatus {


    private long disciplinaryStatusId;
    private String status;
    private String description;

    /**
     * Default constructor.
     * Initializes an empty DisciplinaryStatus object.
     */
    public DisciplinaryStatus() {
    }

    /**
     * Constructor to create a DisciplinaryStatus with all values.
     *	@param disciplinaryStatusId the unique ID of the disciplinary status.
     *	@param status the name of the disciplinary status.
     *	@param description the description of the disciplinary status.
     */
    public DisciplinaryStatus(long disciplinaryStatusId, String status, String description) {
        this.disciplinaryStatusId = disciplinaryStatusId;
        this.status = status;
        this.description = description;
    }


    /**
     * @return the unique ID of the disciplinary status.
     */
    public long getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    /**
     *	@param disciplinaryStatusId sets the unique ID of the disciplinary status.
     */
    public void setDisciplinaryStatusId(long disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    /**
     * @return the name of the disciplinary status.
     */
    public String getStatus() {
        return status;
    }

    /**
     *	@param status sets the name of the disciplinary status.
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * @return the description of the disciplinary status.
     */
    public String getDescription() {
        return description;
    }

    /**
     *	@param description sets the description of the disciplinary status.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
