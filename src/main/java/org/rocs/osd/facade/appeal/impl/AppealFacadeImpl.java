package org.rocs.osd.facade.appeal.impl;

import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.data.dao.appeal.impl.AppealDaoImpl;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

public class AppealFacadeImpl implements AppealFacade {

    private final AppealDao appealDao;

    public AppealFacadeImpl() {
        this.appealDao = new AppealDaoImpl();
    }

    public AppealFacadeImpl(AppealDao appealDao) {
        this.appealDao = appealDao;
    }

    @Override
    public Appeal fileAppeal(Appeal appeal) {
        appeal.setStatus("PENDING");
        return appealDao.saveAppeal(appeal);
    }

    @Override
    public Appeal getAppealById(Long appealId) {
        return appealDao.findByAppealId(appealId);
    }

    @Override
    public List<Appeal> getAppealsByEnrollmentId(Long enrollmentId) {
        return appealDao.findByEnrollmentId(enrollmentId);
    }

    @Override
    public void approveAppeal(Long appealId) {
        appealDao.updateAppealStatus(appealId, "APPROVED");
    }

    @Override
    public void rejectAppeal(Long appealId) {
        appealDao.updateAppealStatus(appealId, "REJECTED");
    }
}
