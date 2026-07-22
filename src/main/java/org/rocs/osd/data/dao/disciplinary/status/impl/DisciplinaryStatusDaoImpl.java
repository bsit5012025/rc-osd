package org.rocs.osd.data.dao.disciplinary.status.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.disciplinary.status.DisciplinaryStatusDao;
import org.rocs.osd.model.disciplinary.status.DisciplinaryStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaryStatusDaoImpl implements DisciplinaryStatusDao {

    /**
     * Retrieves all disciplinary statuses from the database.
     *
     * @return a list of all disciplinary statuses,
     *        returns an empty list if no records are
     *        found or if a database error occurs.
     */
    @Override
    public List<DisciplinaryStatus> getAllDisciplinaryStatus() {
        List<DisciplinaryStatus> status = new ArrayList<>();
        DisciplinaryStatus disciplinaryStatus;

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "Select "
                     + "DisciplinaryStatusID,"
                     + "Status, "
                     + "Description "
                     + "from disciplinarystatus"
             )) {
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    disciplinaryStatus =
                            new DisciplinaryStatus();

                    disciplinaryStatus.
                            setDisciplinaryStatusId(rs.getLong(
                                    "DisciplinaryStatusID"));
                    disciplinaryStatus.
                            setStatus(rs.getString(
                                    "Status"));
                    disciplinaryStatus.
                            setDescription(rs.getString(
                                    "Description"));

                    status.add(disciplinaryStatus);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }
}
