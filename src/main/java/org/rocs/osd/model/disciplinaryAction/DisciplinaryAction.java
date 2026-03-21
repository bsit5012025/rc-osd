package org.rocs.osd.model.disciplinaryAction;

/**
 * Represents a disciplinary action in the Office
 * of Student Discipline System.
 * Holds ID, name, and description of the action.
 */
public class DisciplinaryAction {

    /** Unique ID of the disciplinary action. */
    private long actionId;

    /** Name of the disciplinary action. */
    private String actionName;

    /** Description of the disciplinary action. */
    private String description;

    /** Default constructor, creates an empty DisciplinaryAction object. */
    public DisciplinaryAction() {
    }

    /**
     * Constructor to initialize all fields of a DisciplinaryAction.
     * @param actionId the unique ID of the action.
     * @param actionName the name of the action.
     * @param description the description of the action.
     */
    public DisciplinaryAction(long actionId,
        String actionName, String description) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.description = description;
    }

    /** @return the unique ID of the disciplinary action. */
    public long getActionId() {
        return actionId;
    }

    /** @param actionId sets the unique ID of the disciplinary action. */
    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    /** @return the name of the disciplinary action. */
    public String getActionName() {
        return actionName;
    }

    /** @param actionName sets the name of the disciplinary action. */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /** @return the description of the disciplinary action. */
    public String getDescription() {
        return description;
    }

    /** @param description sets the description of the disciplinary action. */
    public void setDescription(String description) {
        this.description = description;
    }
}