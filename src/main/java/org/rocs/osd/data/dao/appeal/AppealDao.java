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
     * Retrieves appeals based on their status (PENDING, APPROVED, DENIED).
     *
     * @param status the status to filter appeals
     * @return list of appeals with full details
     */
    List<Appeal> findAppealsByStatus(String status);
    /**
     * Updates the status of an appeal.
     *
     * @param appealId the ID of the appeal
     * @param status the new status of the appeal
     */
    void updateAppealStatus(long appealId, String status);
    /**
     * Saves the remarks for a specific appeal.
     *
     * @param appealId the ID of the appeal
     * @param remarks the remarks
     */
    void saveRemarks(long appealId, String remarks);
}

