package org.rocs.osd.model.disciplinaryAction;

/**
 * Represents a Disciplinary Action entity in the Office of Student Discipline System.
 * Contains the action ID, name, and description.
 */
public class DisciplinaryAction {


    private long actionId;
    private String actionName;
    private String description;

    /**
     * Default constructor.
     * Initializes an empty DisciplinaryAction object.
     */
    public DisciplinaryAction() {
    }

    /**
     * Constructor to create a DisciplinaryAction with all values.
     *	@param actionId   the unique identifier of the disciplinary action
     *	@param actionName the name of the disciplinary action
     *	@param description the description of the disciplinary action
     */
    public DisciplinaryAction(long actionId, String actionName, String description) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.description = description;
    }

    /**
     * @return the unique ID of the disciplinary action
     */
    public long getActionId() {
        return actionId;
    }

    /**
     *	@param actionId sets the unique ID of the disciplinary action
     */
    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    /**
     * @return the name of the disciplinary action
     */
    public String getActionName() {
        return actionName;
    }

    /**
     *	@param actionName sets the name of the disciplinary action
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * @return the description of the disciplinary action
     */
    public String getDescription() {
        return description;
    }

    /**
     *	@param description sets the description of the disciplinary action
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
