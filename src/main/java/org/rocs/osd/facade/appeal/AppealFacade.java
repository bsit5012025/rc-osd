package org.rocs.osd.facade.appeal;

import org.rocs.osd.model.appeal.Appeal;
import java.util.List;

public interface AppealFacade {

    List<Appeal> getAllAppeals();

    void approveAppeal(Long appealId);

    void rejectAppeal(Long appealId);
}
