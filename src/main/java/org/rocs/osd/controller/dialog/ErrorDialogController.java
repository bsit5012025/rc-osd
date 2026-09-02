package org.rocs.osd.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller for handling dynamic error banners and popups.
 */
public class ErrorDialogController {

    /**
     * Container box layout for the error message display.
     */
    @FXML
    private VBox errorBanner;

    /**
     * Label element displaying the raw warning text string.
     */
    @FXML
    private Label errorText;

    /**
     * Sets the error message text displayed within the banner layout.
     * Used by the dynamic inline card verification routines.
     *
     * @param message the validation error text to display.
     */
    public void setErrorMessage(String message) {
        if (errorText != null) {
            errorText.setText(message);
        }
    }

    /**
     * Alias messaging method implemented to guarantee structural compatibility
     * with historical popup loader references without breaking compilation.
     *
     * @param message the popup alert text string to apply.
     */
    public void setMessage(String message) {
        setErrorMessage(message);
    }
}
