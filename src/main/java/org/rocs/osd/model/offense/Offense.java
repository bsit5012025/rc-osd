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
        // Default constructor
    }

    /**
     * Constructor with all fields.
     * @param pOffenseId unique offense ID.
     * @param pOffense name of the offense.
     * @param pType type of offense.
     * @param pDescription description of the offense.
     */
    public Offense(long pOffenseId, String pOffense,
                   String pType, String pDescription) {
        this.offenseId = pOffenseId;
        this.offense = pOffense;
        this.type = pType;
        this.description = pDescription;
    }

    /** @return unique offense ID. */
    public long getOffenseId() {
        return offenseId;
    }

    /** @param pOffenseId sets the unique offense ID. */
    public void setOffenseId(long pOffenseId) {
        this.offenseId = pOffenseId;
    }

    /** @return the name of the offense. */
    public String getOffense() {
        return offense;
    }

    /** @param pOffense sets the name of the offense. */
    public void setOffense(String pOffense) {
        this.offense = pOffense;
    }

    /** @return the type of the offense. */
    public String getType() {
        return type;
    }

    /** @param pType sets the type of the offense. */
    public void setType(String pType) {
        this.type = pType;
    }

    /** @return the description of the offense. */
    public String getDescription() {
        return description;
    }

    /** @param pDescription sets the description of the offense. */
    public void setDescription(String pDescription) {
        this.description = pDescription;
    }
}
