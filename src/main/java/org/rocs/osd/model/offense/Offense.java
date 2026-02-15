package org.rocs.osd.model.offense;

public class Offense {

    private long offenseId;
    private String offense;
    private String type;
    private String description;

    public Offense() {
    }

    public Offense(long offenseId, String offense, String type, String description) {
        this.offenseId = offenseId;
        this.offense = offense;
        this.type = type;
        this.description = description;
    }

    public long getOffenseId() {
        return offenseId;
    }

    public void setOffenseId(long offenseId) {
        this.offenseId = offenseId;
    }

    public String getOffense() {
        return offense;
    }

    public void setOffense(String offense) {
        this.offense = offense;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
