package org.rocs.osd.data.dao.appeal;

import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

public interface AppealDao {

    List<Appeal> findPendingAppealsWithDetails();

    void updateAppealStatus(long appealId, String status);
}
