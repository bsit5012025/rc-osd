package org.rocs.osd.facade.appeal;

import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

public interface AppealFacade {

    /**
     * Retrieves appeals by status.
     *
     * @param status the appeal status
     * @return list of appeals
     */
    List<Appeal> getAppealsByStatus(String status);

    /**
     * Approves the appeal with the given ID.
     * @param appealId the ID of the appeal to approve
     * @param remarks the remarks for approve
     */
    void approveAppeal(long appealId, String remarks);

    /**
     * Denies the appeal with a corresponding remarks.
     *
     * @param appealId the ID of the appeal to deny
     * @param remarks the remarks for denial
     */
    void denyAppeal(long appealId, String remarks);
}
