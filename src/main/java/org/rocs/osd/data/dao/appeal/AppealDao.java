package org.rocs.osd.data.dao.appeal;

import org.rocs.osd.model.appeal.Appeal;
import java.util.List;

public interface AppealDao {

    void saveAppeal(Appeal appeal);

    List<Appeal> findAllAppealDetails();

    void updateAppealStatus(Long appealId, String status);
}
