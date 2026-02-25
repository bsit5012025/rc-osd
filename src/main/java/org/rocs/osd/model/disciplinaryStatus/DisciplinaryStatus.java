package org.rocs.osd.model.disciplinaryStatus;

/**
 * Represents a Disciplinary Status in the system.
 * This class stores information about the status of a disciplinary record.
 */
public class DisciplinaryStatus {

    /** Unique identifier of the disciplinary status */
    private long disciplinaryStatusId;
    /** Name of the disciplinary status */
    private String status;
    /** Description of the disciplinary status */
    private String description;

    public DisciplinaryStatus() {
    }

    /**
     *  Constructor with all values
      */
    public DisciplinaryStatus(long disciplinaryStatusId, String status, String description) {
        this.disciplinaryStatusId = disciplinaryStatusId;
        this.status = status;
        this.description = description;
    }

    /**
     * This will get the Disciplinary Status ID
     *  @return the disciplinary status ID
     */

    public long getDisciplinaryStatusId() {
        return disciplinaryStatusId;
    }

    /**
     *   This will set the Disciplinary Status ID
     */

    public void setDisciplinaryStatusId(long disciplinaryStatusId) {
        this.disciplinaryStatusId = disciplinaryStatusId;
    }

    /**
     * This will get the Status
     * @return the disciplinary status
     */
    public String getStatus() {
        return status;
    }

    /**
     *   This will set the Status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This will get the Description
     * @return the description
     */

    public String getDescription() {
        return description;
    }

    /**
     * This will set the Description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
