package org.rocs.osd.facade.appeal;

import org.rocs.osd.model.appeal.Appeal;
import java.util.List;

public interface AppealFacade {

    Appeal fileAppeal(Appeal appeal);

    Appeal getAppealById(Long appealId);

    List<Appeal> getAppealsByEnrollmentId(Long enrollmentId);

    void approveAppeal(Long appealId);

    void rejectAppeal(Long appealId);
}