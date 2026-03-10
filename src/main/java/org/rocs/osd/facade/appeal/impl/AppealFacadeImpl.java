package org.rocs.osd.facade.appeal.impl;

import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.data.dao.appeal.impl.AppealDaoImpl;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

/**
 * Facade implementation for managing Appeal records in the Office of Student Discipline System.
 * Provides a simplified interface for managing appeals, handling database operations via AppealDao.
 */
public class AppealFacadeImpl implements AppealFacade {

    private AppealDao appealDao;

    public AppealFacadeImpl(AppealDao appealDao) {
        this.appealDao = appealDao;
    }

    public AppealFacadeImpl() {
        this.appealDao = new AppealDaoImpl();
    }

    /**
     * Retrieves all appeal records with associated student and offense details.
     *
     * @return a List of Appeal objects
     */
    @Override
    public List<Appeal> getPendingAppeals() {
        return appealDao.findPendingAppealsWithDetails();
    }

    /**
     * Approves the appeal with the given ID by updating its status.
     *
     * @param appealId the ID of the appeal to approve.
     */
    @Override
    public void approveAppeal(long appealId) {
        appealDao.updateAppealStatus(appealId, "APPROVED");
    }

    /**
     * Rejects the appeal with the given ID by updating its status.
     *
     * @param appealId the ID of the appeal to reject.
     */
    @Override
    public void deniedAppeal(long appealId) {
        appealDao.updateAppealStatus(appealId, "DENIED");
    }
}
