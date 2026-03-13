package org.rocs.osd.facade.appeal;

import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

public interface AppealFacade {

    List<Appeal> getPendingAppeals();

    void approveAppeal(long appealId);

    void deniedAppeal(long appealId);
}
