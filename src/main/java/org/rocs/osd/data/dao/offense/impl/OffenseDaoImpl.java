package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OffenseDaoImpl implements OffenseDao
{


    @Override
    public boolean editExistingViolationStatus() {
        return false;
    }

    @Override
    public boolean editExistingViolationRemarks() {
        return false;
    }

    @Override
    public boolean editExistingViolationDateOfResolution() {
        return false;
    }
}
