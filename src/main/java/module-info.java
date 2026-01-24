module org.rocs.osd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.rocs.osd to javafx.fxml; // added opens and exports org.rocs.osd to fix javaFX issue
    exports org.rocs.osd;
}