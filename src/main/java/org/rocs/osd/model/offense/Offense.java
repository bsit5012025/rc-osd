package org.rocs.osd.model.offense;

/**
 * Represents an offense record in the OSD System.
 * Stores offense ID, name, type, and description.
 */
public class Offense {

    /** Unique ID of the offense. */
    private long offenseId;

    /** Name of the offense. */
    private String offense;

    /** Type of the offense. */
    private String type;

    /** Description of the offense. */
    private String description;

    /** Default constructor. */
    public Offense() {
    }

    /**
     * Constructor with all fields.
     * @param offenseId unique offense ID.
     * @param offense name of the offense.
     * @param type type of offense.
     * @param description description of the offense.
     */
    public Offense(long offenseId, String offense,
                   String type, String description) {
        this.offenseId = offenseId;
        this.offense = offense;
        this.type = type;
        this.description = description;
    }

    /** @return unique offense ID. */
    public long getOffenseId() {
        return offenseId;
    }

    /** @param offenseId sets the unique offense ID. */
    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    /** @return the name of the offense. */
    public String getOffense() {
        return offense;
    }

    /** @param offense sets the name of the offense. */
    public void setOffense(String offense) {
        this.offense = offense;
    }

    /** @return the type of the offense. */
    public String getType() {
        return type;
    }

    /** @param type sets the type of the offense. */
    public void setType(String type) {
        this.type = type;
    }

    /** @return the description of the offense. */
    public String getDescription() {
        return description;
    }

    /** @param description sets the description of the offense. */
    public void setDescription(String description) {
        this.description = description;
    }
}