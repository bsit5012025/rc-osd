package org.rocs.osd.data.dao.appeal;

import java.util.List;

public interface AppealDao {

    List<Object[]> findPendingAppealsWithDetails();

    void updateAppealStatus(long appealId, String status);
}
