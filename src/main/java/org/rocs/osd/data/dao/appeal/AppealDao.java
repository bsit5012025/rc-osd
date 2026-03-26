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
     * Updates the status of an appeal in the database.
     * Also saves remarks and sets the processed date.
     *
     * @param appealId ID of the appeal to update
     * @param status new status (APPROVED / DENIED)
     * @param remarks optional remarks for the appeal
     */
    void processAppeal(long appealId, String status, String remarks);
}
