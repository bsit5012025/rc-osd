package org.rocs.osd.facade.appeal;

import java.util.List;

public interface AppealFacade {

    List<Object[]> getPendingAppeals();

    void approveAppeal(long appealId);

    void deniedAppeal(long appealId);
}
