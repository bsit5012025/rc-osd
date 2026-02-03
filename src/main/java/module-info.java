module org.rocs.osd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;

    opens org.rocs.osd.controller.login to javafx.fxml;

    exports org.rocs.osd;
}


