package org.rocs.osd.facade.appeal;

import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

public interface AppealFacade {

    /**
     * Retrieves all pending appeals with associated
     * student and offense details.
     * @return a List of pending Appeal objects
     */
    List<Appeal> getPendingAppeals();

    /**
     * Approves the appeal with the given ID.
     * @param appealId the ID of the appeal to approve
     */
    void approveAppeal(long appealId);

    /**
     * Denies the appeal with the given ID.
     * @param appealId the ID of the appeal to deny
     */
    void denyAppeal(long appealId);
}
