package org.rocs.osd.data.dao.appeal;

import org.rocs.osd.model.appeal.Appeal;
import java.util.List;

public interface AppealDao {

    Appeal saveAppeal(Appeal appeal);

    Appeal findByAppealId(Long appealId);

    List<Appeal> findByEnrollmentId(Long enrollmentId);

    void updateAppealStatus(Long appealId, String status);
}