package org.rocs.osd.model.disciplinaryAction;

public class DisciplinaryAction {
    private long actionId;
    private String actionName;
    private String description;

    public DisciplinaryAction() {
    }

    public DisciplinaryAction(long actionId, String actionName, String description) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.description = description;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
