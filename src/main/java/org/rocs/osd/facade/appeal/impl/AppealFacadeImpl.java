package org.rocs.osd.facade.appeal.impl;

import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.data.dao.appeal.impl.AppealDaoImpl;
import org.rocs.osd.facade.appeal.AppealFacade;

import java.util.List;

public class AppealFacadeImpl implements AppealFacade {

    private AppealDao appealDao;

    public AppealFacadeImpl(AppealDao appealDao) {
        this.appealDao = appealDao;
    }

    public AppealFacadeImpl() {
        this.appealDao = new AppealDaoImpl();
    }

    @Override
    public List<Object[]> getPendingAppeals() {
        return appealDao.findPendingAppealsWithDetails();
    }

    @Override
    public void approveAppeal(long appealId) {
        appealDao.updateAppealStatus(appealId, "APPROVED");
    }

    @Override
    public void deniedAppeal(long appealId) {
        appealDao.updateAppealStatus(appealId, "DENIED");
    }
}
