package org.rocs.osd.facade.appeal.impl;

import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.data.dao.appeal.impl.AppealDaoImpl;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.model.appeal.Appeal;

import java.util.List;

public class AppealFacadeImpl implements AppealFacade {

    private AppealDao appealDao = new AppealDaoImpl();

    @Override
    public List<Appeal> getAllAppeals() {
        return appealDao.findAllAppealDetails();
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
