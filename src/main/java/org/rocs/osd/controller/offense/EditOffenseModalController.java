package org.rocs.osd.controller.offense;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditOffenseModalController
{
    @FXML private ComboBox<String> offenseTypeComboBox;
    @FXML private ComboBox<String> levelOfOffenseComboBox;
    @FXML private TextField studentIdTextField;
    @FXML private TextField studentNameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField remarksTextArea;

    public void initialize()
    {

    }

    @FXML
    private void onCancel()
    {

    }

    @FXML
    private void onSubmit()
    {

    }
}
