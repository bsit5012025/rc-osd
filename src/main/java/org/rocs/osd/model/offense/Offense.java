package org.rocs.osd.model.offense;

/**
 * Represents an offense record in the Office of Student Discipline System
 * Stores offense details including ID, name, type, and description
 */
public class Offense {

    /**
     * Unique identifier for the offense
     */
    private long offenseId;

    /**
     * Name of the offense
     */
    private String offense;
    /**
     * Type of offense
     */
    private String type;
    /**
     * Description of the offense
     */
    private String description;

    /**
     * Default constructor.
     */
    public Offense() {
    }

    /**
     * @param offense name of the offense
     * @param type type of offense
     * @param description description of the offense
     */
    public Offense(long offenseId, String offense, String type, String description) {
        this.offenseId = offenseId;
        this.offense = offense;
        this.type = type;
        this.description = description;
    }


    /**
     * Gets the offense ID
     * @return the offenseId
     */
    public long getOffenseId() {
        return offenseId;
    }


    /**
     * Sets the offense ID
     * @param offenseId the offenseId to set
     */
    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    /**
     * Gets the offense name
     * @return the offense
     */
    public String getOffense() {
        return offense;
    }

    /**
     * Sets the offense name
     * @param offense the offense to set
     */
    public void setOffense(String offense) {
        this.offense = offense;
    }

    /**
     * Gets the type offense
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of offense
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the description of the offense
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the offense
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
