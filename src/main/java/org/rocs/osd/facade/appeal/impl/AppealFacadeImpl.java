package org.rocs.osd.facade.appeal.impl;

import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.data.dao.appeal.impl.AppealDaoImpl;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

/**
 * Facade implementation for managing Appeal records in the
 * Office of Student Discipline System.
 */
public class AppealFacadeImpl implements AppealFacade {
    /** DAO for handling appeal data operations. */
    private final AppealDao appealDao;

    /**
     * Constructs a facade with a custom AppealDao implementation.
     * @param pAppealDao the AppealDao to use for database operations
     */
    public AppealFacadeImpl(AppealDao pAppealDao) {
        this.appealDao = pAppealDao;
    }

    /**
     * Constructs a facade with the default AppealDaoImpl.
     */
    public AppealFacadeImpl() {
        this.appealDao = new AppealDaoImpl();
    }

    /**
     * Retrieves appeals by status.
     */
    @Override
    public List<Appeal> getAppealsByStatus(String status) {
        return appealDao.findAppealsByStatus(status);
    }

    /**
     * Approves the appeal with the given ID by
     * updating its status to "APPROVED".
     *
     * @param appealId the ID of the appeal to approve.
     */
    @Override
    public void approveAppeal(long appealId) {
        appealDao.updateAppealStatus(appealId, "APPROVED");
    }

    /**
     * Denies the appeal and saves the corresponding remarks.
     *
     * @param appealId the ID of the appeal
     * @param remarks the remarks
     */
    @Override
    public void denyAppeal(long appealId, String remarks) {
        if (remarks == null || remarks.trim().isEmpty()) {
            throw new IllegalArgumentException("Denial remarks is required.");
        }
        appealDao.updateAppealStatus(appealId, "DENIED");
        appealDao.saveRemarks(appealId, remarks);
    }
}
