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
     * Approves an appeal and optionally saves remarks.
     *
     * @param appealId ID of the appeal to approve
     * @param remarks optional remarks for approval
     */
    @Override
    public void approveAppeal(long appealId, String remarks) {
        appealDao.processAppeal(appealId, "APPROVED", remarks);
    }

    /**
     * Denies an appeal and requires remarks.
     *
     * @param appealId ID of the appeal to deny
     * @param remarks required remarks explaining denial
     * @throws IllegalArgumentException if remarks is empty
     */
    @Override
    public void denyAppeal(long appealId, String remarks) {
        if (remarks == null || remarks.trim().isEmpty()) {
            throw new IllegalArgumentException("Denial remarks is required.");
        }
        appealDao.processAppeal(appealId, "DENIED", remarks);
    }
}
