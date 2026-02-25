package org.rocs.osd.model.disciplinaryAction;

/**
 * Represents a Disciplinary Action entity.
 * Contains the action ID, name, and description.
 */
public class DisciplinaryAction {

    /** Unique identifier of the disciplinary action */
    private long actionId;
    /** Name of the disciplinary action */
    private String actionName;
    /** Description of the disciplinary action */
    private String description;


    public DisciplinaryAction() {
    }

    /**
     * Constructor with all values
      */
    public DisciplinaryAction(long actionId, String actionName, String description) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.description = description;
    }

    /**
     *This will get the action  ID
     */
    public long getActionId() {
        return actionId;
    }

    /**
     * This will set the Action ID
      */
    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    /**
     *    This will get the Action Name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     *  This will set the Action Name
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * This will get the Description
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
