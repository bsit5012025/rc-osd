package org.rocs.osd.model.offense;

public class Offense {

    private long offenseId;
    private String offense;
    private String type;
    private String description;

    public Offense() {
    }

    // Constructor with all values
    public Offense(long offenseId, String offense, String type, String description) {
        this.offenseId = offenseId;
        this.offense = offense;
        this.type = type;
        this.description = description;
    }

    //This will get the Offense ID
    public long getOffenseId() {
        return offenseId;
    }

    //This will set the Offense ID
    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    //This will get the Offense
    public String getOffense() {
        return offense;
    }

    //This will set the Offense
    public void setOffense(String offense) {
        this.offense = offense;
    }

    //This will get the Type
    public String getType() {
        return type;
    }

    //This will set the Type
    public void setType(String type) {
        this.type = type;
    }

    //This will get the Description
    public String getDescription() {
        return description;
    }

    //This will set the Description
    public void setDescription(String description) {
        this.description = description;
    }
}
