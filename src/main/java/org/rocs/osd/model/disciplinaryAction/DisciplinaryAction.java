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
     * @param pActionId the unique ID of the action.
     * @param pActionName the name of the action.
     * @param pDescription the description of the action.
     */
    public DisciplinaryAction(long pActionId,
        String pActionName, String pDescription) {
        this.actionId = pActionId;
        this.actionName = pActionName;
        this.description = pDescription;
    }

    /** @return the unique ID of the disciplinary action. */
    public long getActionId() {
        return actionId;
    }

    /** @param pActionId sets the unique ID of the disciplinary action. */
    public void setActionId(long pActionId) {
        this.actionId = pActionId;
    }

    /** @return the name of the disciplinary action. */
    public String getActionName() {
        return actionName;
    }

    /** @param pActionName sets the name of the disciplinary action. */
    public void setActionName(String pActionName) {
        this.actionName = pActionName;
    }

    /** @return the description of the disciplinary action. */
    public String getDescription() {
        return description;
    }

    /** @param pDescription sets the description of the disciplinary action. */
    public void setDescription(String pDescription) {
        this.description = pDescription;
    }
}
