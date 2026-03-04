package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.data.dao.offense.impl.OffenseDaoImpl;
import org.rocs.osd.model.offense.Offense;

public class AddOffenseModalController {

    @FXML
    private ComboBox<String> offenseTypeComboBox;
    @FXML
    private ComboBox<String> levelOfOffenseComboBox;

    private OffenseDao offenseDao;

    public void initialize(){
        loadComboBoxData();
        autoSelectLevelOfOffense();
    }

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
