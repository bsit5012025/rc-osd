package org.rocs.osd.data.dao.disciplinary.status;

import java.util.List;
import org.rocs.osd.model.disciplinary.status.DisciplinaryStatus;

public interface DisciplinaryStatusDao {

    /**
     * Retrieves all disciplinary statuses from the database.
     *
     * @return a list of all disciplinary statuses,
     *        returns an empty list if no records are
     *        found or if a database error occurs.
     */
    List<DisciplinaryStatus> getAllDisciplinaryStatus();
}
