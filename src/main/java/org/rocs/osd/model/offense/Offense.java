package org.rocs.osd.model.offense;

/**
 * Represents an offense record in the Office of Student Discipline System.
 * Stores offense details including ID, name, type, and description.
 */
public class Offense {


    private long offenseId;
    private String offense;
    private String type;
    private String description;

    /**
     * Default constructor.
     * Initializes an empty Offense object.
     */
    public Offense() {
    }

    /**
     * Constructor to create an Offense with all fields.
     * @param offenseId the unique offense ID.
     * @param offense name of the offense.
     * @param type type of offense.
     * @param description description of the offense.
     */
    public Offense(long offenseId, String offense, String type, String description) {
        this.offenseId = offenseId;
        this.offense = offense;
        this.type = type;
        this.description = description;
    }


    /**
     * @return the unique offense ID.
     */
    public long getOffenseId() {
        return offenseId;
    }


    /**
     *	@param offenseId sets the unique offense ID.
     */
    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    /**
     * @return the name of the offense.
     */
    public String getOffense() {
        return offense;
    }

    /**
     *	@param offense sets the name of the offense.
     */
    public void setOffense(String offense) {
        this.offense = offense;
    }

    /**
     * @return the type of the offense.
     */
    public String getType() {
        return type;
    }

    /**
     *	@param type sets the type of the offense.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description of the offense.
     */
    public String getDescription() {
        return description;
    }

    /**
     *	@param description sets the description of the offense.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
