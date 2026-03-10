package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.data.dao.offense.impl.OffenseDaoImpl;
import org.rocs.osd.model.offense.Offense;

/**
 * Controller for the "Add Offense" modal in the Office of Student Discipline System.
 * This class handles the population of offense type and level ComboBoxes and automatically selects the level of offense based on the selected offense type.
 */
public class AddOffenseModalController {

    @FXML
    private ComboBox<String> offenseTypeComboBox;
    @FXML
    private ComboBox<String> levelOfOffenseComboBox;

    private OffenseDao offenseDao;

    /**
     * Controller for the "Add Offense" modal.
     * Handles the population of offense type and level ComboBoxes and automatically selects the level of offense based on the selected offense type.
     */
    public void initialize(){
        loadComboBoxData();
        autoSelectLevelOfOffense();
    }

    /**
     * Loads all offense names from the database into the offense type ComboBox.
     * Prints an error message if the database fetch fails.
     */

    public void loadComboBoxData(){
        try {
            offenseDao = new OffenseDaoImpl();
            var data = offenseDao.findAllOffenseName();
            if (data != null) {
                offenseTypeComboBox.setItems(FXCollections.observableArrayList(data));
            }
        } catch (Exception e) {
            System.err.println("Database Error: Could not fetch offense names from the database.");
        }

    }
    /**
     * Automatically selects the level of offense based on the offense type chosen by the user.
     * When a user selects an offense type, the corresponding level is displayed in the level ComboBox.
     */
    public void autoSelectLevelOfOffense() {

        offenseTypeComboBox.setOnAction(event -> {

            String selected = offenseTypeComboBox.getValue();

            if (selected != null) {

                Offense offense = offenseDao.findByName(selected);

                if (offense != null) {
                    levelOfOffenseComboBox.setValue(offense.getType());
                }
            }
        });
    }
}
