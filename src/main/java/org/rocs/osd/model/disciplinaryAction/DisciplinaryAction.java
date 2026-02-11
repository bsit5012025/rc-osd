package org.rocs.osd.model.disciplinaryAction;

public class DisciplinaryAction {
    private String actionId;
    private String actionName;
    private String description;

    public DisciplinaryAction() {
    }

    public DisciplinaryAction(String actionId, String actionName, String description) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.description = description;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
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
