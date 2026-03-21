package org.rocs.osd.data.dao.disciplinaryAction;

import java.util.List;

/**
 * DAO interface for managing disciplinary actions in the Office of Student
 * Discipline System. Provides methods to retrieve action names and IDs.
 */
public interface DisciplinaryActionDao {

    /**
     * Finds the name of a disciplinary action by its unique ID.
     * @param actionId the unique ID of the disciplinary action.
     * @return the action name if found; null if not found.
     */
    String findActionById(long actionId);

    /**
     * Retrieves all disciplinary action names from the database,
     * sorted alphabetically.
     *
     * @return a list of all action names
     */
    List<String> findAllAction();

    /**
     * Finds the ID of a disciplinary action by its name.
     *
     * @param action the name of the disciplinary action
     * @return the action ID if found; -1 if not found
     */
    long findActionIdByName(String action);
}