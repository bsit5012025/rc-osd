/**
 * Provides data access interfaces for managing appeal-related operations.
 */
package org.rocs.osd.data.dao.appeal;

import org.rocs.osd.model.appeal.Appeal;

import java.util.List;
/**
 * DAO for handling appeal-related operations.
 */
public interface AppealDao {
    /**
     * Retrieves all pending appeals with full details.
     *
     * @return list of pending appeals.
     */
    List<Appeal> findPendingAppealsWithDetails();
    /**
     * Updates the status of an appeal.
     *
     * @param appealId the ID of the appeal
     * @param status   the new status of the appeal
     */
    void updateAppealStatus(long appealId, String status);
}
