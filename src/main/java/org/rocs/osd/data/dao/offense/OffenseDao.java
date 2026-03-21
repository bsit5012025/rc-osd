package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;

import java.util.List;

/**
 * Interface for managing offense data in the database.
 * Provides methods to retrieve, search, and add offenses.
 */
public interface OffenseDao {

    /**
     * Finds an offense by its unique ID.
     * @param offenseID the ID of the offense to find.
     * @return an Offense object with details, or an empty object if not found.
     */
    Offense findOffenseById(String offenseID);

    /**
     * Retrieves all offense names in alphabetical order.
     * @return a list of offense names.
     */
    List<String> findAllOffenseName();

    /**
     * Finds an offense by its name.
     * @param offenseName the name of the offense to find.
     * @return an Offense object with details, or null if not found.
     */
    Offense findByName(String offenseName);

    /**
     * Adds a new offense to the database.
     * @param offense the Offense object to add.
     * @return true if the offense was successfully added, false otherwise.
     */
    boolean addNewOffense(Offense offense);
}
