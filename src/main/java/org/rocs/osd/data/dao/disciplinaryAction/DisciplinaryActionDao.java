package org.rocs.osd.data.dao.disciplinaryAction;

import java.util.List;

public interface DisciplinaryActionDao {
    String findActionById (long actionId);
    List<String> findAllAction();
    long findActionIdByName(String action);
}
